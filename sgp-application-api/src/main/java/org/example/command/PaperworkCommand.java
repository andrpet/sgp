package org.example.command;

import org.example.model.Paperwork;
import org.example.model.PaperworkRequest;
import org.example.model.PaperworkCRResponse;
import org.example.model.PaperworkStatus;
import reactor.core.publisher.Mono;

public interface PaperworkCommand {
    Mono<PaperworkCRResponse> editPaperwork(String paperworkId, PaperworkRequest paperworkRequest);

    Mono<Paperwork> getPaperwork(String paperworkId);

    Mono<PaperworkCRResponse> addPaperwork(PaperworkRequest paperworkRequest);

    Mono<PaperworkCRResponse> changePaperworkStatus(String paperworkId, PaperworkStatus status);
}
