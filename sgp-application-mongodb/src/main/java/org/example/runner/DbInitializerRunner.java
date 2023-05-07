package org.example.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;

@Component
public class DbInitializerRunner implements ApplicationRunner {

  ReactiveMongoOperations reactiveMongoOperations;

  public DbInitializerRunner(ReactiveMongoOperations reactiveMongoOperations) {
    this.reactiveMongoOperations = reactiveMongoOperations;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    System.out.println("INIT THE DB !");

    /*reactiveMongoOperations.collectionExists(Paperwork.class)
      .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Paperwork.class) : Mono.just(false))
      .flatMap(v -> reactiveMongoOperations.createCollection(Paperwork.class))
      .subscribe();*/
  }
}
