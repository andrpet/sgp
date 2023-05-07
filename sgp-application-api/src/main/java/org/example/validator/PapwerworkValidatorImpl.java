package org.example.validator;

import org.example.model.PaperworkRequest;
import reactor.core.publisher.Mono;

public class PapwerworkValidatorImpl implements PapwerworkValidator {
    @Override
    public Mono<Boolean> validate(PaperworkRequest paperworkRequest) {
        // ADD SOME BUSINESS LOGIC HERE !
        return Mono.fromCallable(() -> {
            return true;
        });
    }
}
