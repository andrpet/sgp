package org.example.validator;

import org.example.model.PaperworkRequest;
import reactor.core.publisher.Mono;

public interface PapwerworkValidator {
    Mono<Boolean> validate(PaperworkRequest paperworkRequest);
}
