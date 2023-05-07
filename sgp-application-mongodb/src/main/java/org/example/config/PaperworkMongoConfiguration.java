package org.example.config;

import org.example.repository.PaperworkRepository;
import org.example.service.PaperworkService;
import org.example.service.PaperworkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaperworkMongoConfiguration {

    @Autowired
    private PaperworkRepository paperworkRepository;

    @Bean
    public PaperworkService paperworkService(){
        return new PaperworkServiceImpl(paperworkRepository);
    }

}
