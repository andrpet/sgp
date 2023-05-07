package org.example.service;

import org.example.model.Paperwork;
import org.example.model.PaperworkStatus;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface PaperworkService {
    Mono<Paperwork> createPaperwork(Paperwork paperwork);

    Mono<Paperwork> findPaperwork(String paperworkId);

    Mono<Paperwork> editPaperwork(String paperworkId, String name, String surname, String taxCode, Instant dateOfBirth);

    Mono<Paperwork> changePaperworkStatus(String paperworkId, PaperworkStatus paperworkStatus);
}
