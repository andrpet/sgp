package org.example.repository;

import org.example.model.Paperwork;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PaperworkRepository extends ReactiveMongoRepository<Paperwork, String>  {

    Mono<Paperwork> findByIdentifier(String paperworkID);

}
