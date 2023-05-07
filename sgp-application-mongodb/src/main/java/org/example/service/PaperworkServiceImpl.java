package org.example.service;

import org.example.model.Paperwork;
import org.example.model.PaperworkStatus;
import org.example.repository.PaperworkRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

public class PaperworkServiceImpl implements PaperworkService {
    private final PaperworkRepository paperworkRepository;

    public PaperworkServiceImpl(PaperworkRepository paperworkRepository) {
        this.paperworkRepository = paperworkRepository;
    }

    @Override
    public Mono<Paperwork> createPaperwork(Paperwork paperwork) {
        return paperworkRepository.save(paperwork);
    }

    @Override
    public Mono<Paperwork> findPaperwork(String paperworkId) {
        return paperworkRepository.findByIdentifier(paperworkId);
    }

    @Override
    public Mono<Paperwork> editPaperwork(String paperworkId, String name, String surname, String taxCode, Instant dateOfBirth) {
        return this.findPaperwork(paperworkId).flatMap(paperwork -> {
            Optional.ofNullable(name).ifPresent(paperwork::setName);
            Optional.ofNullable(surname).ifPresent(paperwork::setSurname);
            Optional.ofNullable(taxCode).ifPresent(paperwork::setTaxCode);
            Optional.ofNullable(dateOfBirth).ifPresent(paperwork::setDateOfBirth);
            return paperworkRepository.save(paperwork);
        });
    }

    @Override
    public Mono<Paperwork> changePaperworkStatus(String paperworkId, PaperworkStatus paperworkStatus) {
        return this.findPaperwork(paperworkId).flatMap(paperwork -> {
            paperwork.addPaperworkInfo(paperworkStatus);
            return paperworkRepository.save(paperwork);
        });
    }
}
