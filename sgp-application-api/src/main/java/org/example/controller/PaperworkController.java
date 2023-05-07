package org.example.controller;

import com.mongodb.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.example.command.PaperworkCommand;
import org.example.command.PaperworkDocCommand;
import org.example.model.OperationStatus;
import org.example.model.Paperwork;
import org.example.model.PaperworkCRResponse;
import org.example.model.PaperworkRequest;
import org.example.repository.PaperworkRepository;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("paperwork")
public class PaperworkController {

    public static final String ERROR = "CUSTOM ERROR MESSAGE";
    public static final String USER_NOT_FOUND = "PAPERWORK NOT FOUND";
    private final PaperworkRepository paperworkRepository;
    private final PaperworkCommand papwerworkCommand;
    private final PaperworkDocCommand paperworkDocCommand;

    public PaperworkController(PaperworkRepository paperworkRepository, PaperworkCommand papwerworkCommand, PaperworkDocCommand paperworkDocCommand) {
        this.paperworkRepository = paperworkRepository;
        this.papwerworkCommand = papwerworkCommand;
        this.paperworkDocCommand = paperworkDocCommand;
    }

    @PostMapping()
    public Mono<ResponseEntity<PaperworkCRResponse>> addPaperwork(@RequestPart("doc") Mono<FilePart> filePartMono, @RequestPart("name") String name,
                                                                  @RequestPart("surname") String surname, @RequestPart("taxCode") String taxCode,
                                                                  @RequestPart("dateOfBirth") String dateOfBirth) {

        log.info("CREATE NEW PAPERWORK");

        return papwerworkCommand.addPaperwork(PaperworkRequest.PaperworkRequestBuilder
                .create().withFile(filePartMono)
                .withName(name)
                .withSurname(surname)
                .withTaxCode(taxCode)
                .withDateOfBirth(dateOfBirth)
                .build()
        ).map(res -> res.getOperationStatus().equals(OperationStatus.SUCCESS) ?
                ResponseEntity.ok().body(res) : ResponseEntity.internalServerError().body(res));
    }

    @GetMapping("/download/{paperworkId:.+}")
    public ResponseEntity<Flux<DataBuffer>> getFile(@PathVariable String paperworkId) {
        log.info("DOWNLOAD PERWORK DOC");

        Flux<DataBuffer> file = this.paperworkDocCommand.download(paperworkId);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paperworkId + ".pdf\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }

    @GetMapping("/info/{paperworkId:.+}")
    public Mono<Paperwork> getPaperworkInfo(@PathVariable String paperworkId) {
        log.info("GET PAPERWORK INFO");

        return papwerworkCommand.getPaperwork(paperworkId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND)));
    }

    @PutMapping()
    public Mono<ResponseEntity<PaperworkCRResponse>> updatePaperworkInfo(@RequestPart(value = "doc", required = false) Mono<FilePart> filePartMono, @RequestPart(value = "name", required = false) String name,
                                                                         @RequestPart(value = "surname", required = false) String surname, @Nullable @RequestPart(value = "taxCode", required = false) String taxCode,
                                                                         @Nullable @RequestPart(value = "dateOfBirth", required = false) String dateOfBirth, @RequestPart("paperworkId") String paperworkId) {

        log.info("UPDATE PAPERWORK");

        return papwerworkCommand.editPaperwork(paperworkId, PaperworkRequest.PaperworkRequestBuilder
                .create().withFile(filePartMono)
                .withName(name)
                .withSurname(surname)
                .withTaxCode(taxCode)
                .withDateOfBirth(dateOfBirth)
                .build()).map(res -> ResponseEntity.ok().body(res));
    }
}
