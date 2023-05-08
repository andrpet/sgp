package org.example.command;

import java.nio.file.Path;
import org.example.config.PaperworkMapper;
import org.example.model.Paperwork;
import org.example.model.PaperworkCRResponse;
import org.example.model.PaperworkCRResponse.PaperworkSaveResponseBuilder;
import org.example.model.PaperworkRequest;
import org.example.model.PaperworkStatus;
import org.example.service.PaperworkService;
import org.example.tools.RandomStringGenerator;
import org.example.validator.PapwerworkValidator;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class PaperworkCommandImpl implements PaperworkCommand {

  @Value("${upload.directory}")
  private String uploadDirectory;

  private final PapwerworkValidator papwerworkValidator;
  private final PaperworkService paperworkService;
  private final PaperworkMapper paperworkMapper;

  public PaperworkCommandImpl(PapwerworkValidator papwerworkValidator, PaperworkService paperworkService,
    PaperworkMapper paperworkMapper) {
    this.papwerworkValidator = papwerworkValidator;
    this.paperworkService = paperworkService;
    this.paperworkMapper = paperworkMapper;
  }

  @Override
  public Mono<PaperworkCRResponse> editPaperwork(String paperworkId, PaperworkRequest paperworkRequest) {
    // QUI SI POTREBBE FARE MOOOOOOOOOLTO MEGLIO E IN AUTOMATICO USANDO UTILS COME BeanUtils.copyProperties(target, source)
    return paperworkService.findPaperwork(paperworkId).flatMap(paperwork -> {
      if (paperwork.getPaperworkStatusInfos().size() == 1) {
        return paperworkService.editPaperwork(paperworkId, paperworkRequest.getName(), paperworkRequest.getSurname(),
            paperworkRequest.getTaxCode(), paperworkRequest.getDateOfBirth())
                 .flatMap(p -> Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.success(p.getIdentifier())));
      }
      return Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.wrongStatus());
    }).onErrorReturn(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
  }

  @Override
  public Mono<Paperwork> getPaperwork(String paperworkId) {
    return paperworkService.findPaperwork(paperworkId);
  }

  @Override
  public Mono<PaperworkCRResponse> changePaperworkStatus(String paperworkId, PaperworkStatus status) {
    return paperworkService.findPaperwork(paperworkId).flatMap(paperwork -> {
      if (paperwork.getPaperworkStatusInfos().size() == 1) {
        return paperworkService.changePaperworkStatus(paperworkId, status)
                 .flatMap(p -> Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.success(p.getIdentifier())));
      }
      return Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
    }).onErrorReturn(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
  }

  @Override
  public Mono<PaperworkCRResponse> addPaperwork(PaperworkRequest paperworkRequest) {
    return papwerworkValidator.validate(paperworkRequest)
             .flatMap(valid -> savePaperworkDb(paperworkRequest))
             .flatMap(rs -> savePaperworkUpload(paperworkRequest, rs))
             .map(res -> PaperworkCRResponse.PaperworkSaveResponseBuilder.success(res.getPaperworkID()));
  }

  private Mono<PaperworkCRResponse> savePaperworkUpload(PaperworkRequest paperworkRequest, PaperworkCRResponse rs) {
    return paperworkRequest.getFilePartMono().flatMap(filePart -> {
      String filename = String.format("%s.%s", rs.getPaperworkID(), "pdf");
      Path path = Path.of(uploadDirectory, filename);

      return filePart.transferTo(path)
               .map(unused -> PaperworkSaveResponseBuilder.success(rs.getPaperworkID()))
               .onErrorReturn(PaperworkSaveResponseBuilder.fail());
    });
  }

  private Mono<PaperworkCRResponse> savePaperworkDb(PaperworkRequest paperworkRequest) {
    String paperworkID = RandomStringGenerator.random();
    String filename = String.format("%s.%s", paperworkID, "pdf");

    return paperworkService.createPaperwork(getPaperwork(paperworkRequest, paperworkID, filename))
             .map(k -> PaperworkCRResponse.PaperworkSaveResponseBuilder.success(paperworkID))
             .onErrorReturn(PaperworkSaveResponseBuilder.fail());
  }

  private Paperwork getPaperwork(PaperworkRequest paperworkRequest, String paperworkIdentifier, String docPath) {
    Paperwork paperwork = paperworkMapper.map(paperworkRequest);
    paperwork.setIdentifier(paperworkIdentifier);
    paperwork.setDocPath(docPath);
    return paperwork;
  }
}
