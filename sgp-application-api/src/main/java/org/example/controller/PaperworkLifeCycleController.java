package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.command.PaperworkCommand;
import org.example.model.PaperworkCRResponse;
import org.example.model.PaperworkStatus;
import org.example.model.SGPNofiticationMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import publisher.SGPPublisher;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("paperworklifecycle")
public class PaperworkLifeCycleController {

    private final PaperworkCommand papwerworkCommand;
    private final SGPPublisher sgpPublisher;

    public PaperworkLifeCycleController(PaperworkCommand papwerworkCommand, SGPPublisher sgpPublisher) {
        this.papwerworkCommand = papwerworkCommand;
        this.sgpPublisher = sgpPublisher;
    }

    @PostMapping("/working")
    public Mono<PaperworkCRResponse> working(@RequestPart("paperworkId") String paperworkId) {
        log.info("UPDATE PAPERWORK LIFECYCLE TO -> WORKING");

        this.sgpPublisher.publishStatusChange(paperworkId,
                SGPNofiticationMessage.SGPNofiticationMessageBuilder.create()
                        .withPaperworkId(paperworkId).withMessage(String.format("Paperwork to status %s", PaperworkStatus.WORKING)).build());

        return papwerworkCommand.changePaperworkStatus(paperworkId, PaperworkStatus.WORKING);
    }

    @PostMapping("/approved")
    public Mono<PaperworkCRResponse> approved(@RequestPart("paperworkId") String paperworkId) {
        log.info("UPDATE PAPERWORK LIFECYCLE TO -> APPROVED");

        this.sgpPublisher.publishStatusChange(paperworkId,
                SGPNofiticationMessage.SGPNofiticationMessageBuilder.create()
                        .withPaperworkId(paperworkId).withMessage(String.format("Paperwork to status %s", PaperworkStatus.APPROVED)).build());

        return papwerworkCommand.changePaperworkStatus(paperworkId, PaperworkStatus.APPROVED);
    }

    @PostMapping("/rejected")
    public Mono<PaperworkCRResponse> rejected(@RequestPart("paperworkId") String paperworkId) {
        log.info("UPDATE PAPERWORK LIFECYCLE TO -> REJECTED");

        this.sgpPublisher.publishStatusChange(paperworkId,
                SGPNofiticationMessage.SGPNofiticationMessageBuilder.create()
                        .withPaperworkId(paperworkId).withMessage(String.format("Paperwork to status %s", PaperworkStatus.REJECTED)).build());

        return papwerworkCommand.changePaperworkStatus(paperworkId, PaperworkStatus.REJECTED);
    }

}
