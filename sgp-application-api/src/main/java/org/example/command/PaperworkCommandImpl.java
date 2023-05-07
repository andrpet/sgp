package org.example.command;

import org.example.config.PaperworkMapper;
import org.example.model.Paperwork;
import org.example.model.PaperworkRequest;
import org.example.model.PaperworkCRResponse;
import org.example.model.PaperworkStatus;
import org.example.service.PaperworkService;
import org.example.tools.RandomStringGenerator;
import org.example.validator.PapwerworkValidator;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.util.*;

public class PaperworkCommandImpl implements PaperworkCommand {

    @Value("${upload.directory}")
    private String uploadDirectory;

    private final PapwerworkValidator papwerworkValidator;
    private final PaperworkService paperworkService;
    private final PaperworkMapper paperworkMapper;

    public PaperworkCommandImpl(PapwerworkValidator papwerworkValidator, PaperworkService paperworkService, PaperworkMapper paperworkMapper) {
        this.papwerworkValidator = papwerworkValidator;
        this.paperworkService = paperworkService;
        this.paperworkMapper = paperworkMapper;
    }

    @Override
    public Mono<PaperworkCRResponse> editPaperwork(String paperworkId, PaperworkRequest paperworkRequest) {
        // QUI SI POTREBBE FARE MOOOOOOOOOLTO MEGLIO E IN AUTOMATICO USANDO UTILS COME BeanUtils.copyProperties(target, source)
        return paperworkService.findPaperwork(paperworkId).flatMap(paperwork -> {
            if(paperwork.getPaperworkStatusInfos().size() == 1){
                return paperworkService.editPaperwork(paperworkId, paperworkRequest.getName(),
                        paperworkRequest.getSurname(), paperworkRequest.getTaxCode(), paperworkRequest.getDateOfBirth())
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
    public Mono<PaperworkCRResponse> addPaperwork(PaperworkRequest paperworkRequest) {
        return papwerworkValidator.validate(paperworkRequest)
                .flatMap(valid -> valid ? savePaperwork(paperworkRequest) : Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.notValid()));
                //.flatMap(valid -> valid ? savePaperwork(paperworkRequest) : Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.notValid()));
    }

    @Override
    public Mono<PaperworkCRResponse> changePaperworkStatus(String paperworkId, PaperworkStatus status) {
        return paperworkService.findPaperwork(paperworkId).flatMap(paperwork -> {
            if(paperwork.getPaperworkStatusInfos().size() == 1){
                return paperworkService.changePaperworkStatus(paperworkId, status)
                        .flatMap(p -> Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.success(p.getIdentifier())));
            }
            return Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
        }).onErrorReturn(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
    }

    private Mono<PaperworkCRResponse> savePaperwork(PaperworkRequest paperworkRequest) {

        return paperworkRequest.getFilePartMono().flatMap(filePart -> {
            String paperworkID = RandomStringGenerator.random();
            String filename = String.format("%s.%s", paperworkID, filePart.filename().split("\\.")[1]);
            Path path = Path.of(uploadDirectory, filename);
            filePart.transferTo(path).subscribe();

            return Mono.just(Arrays.asList(paperworkID, path.toString()));
        }).flatMap(paperworkData -> {
            paperworkService
                    .createPaperwork(getPaperwork(paperworkRequest, paperworkData))
                    .subscribe();

            return Mono.just(PaperworkCRResponse.PaperworkSaveResponseBuilder.success(paperworkData.get(0)));
        }).onErrorReturn(PaperworkCRResponse.PaperworkSaveResponseBuilder.fail());
    }

    private Paperwork getPaperwork(PaperworkRequest paperworkRequest, List<String> paperworkData) {
        Paperwork paperwork = paperworkMapper.map(paperworkRequest);
        paperwork.setIdentifier(paperworkData.get(0));
        paperwork.setDocPath(paperworkData.get(1));
        return paperwork;
    }
}
