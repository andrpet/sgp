package org.example.config;

import org.example.command.PaperworkCommand;
import org.example.command.PaperworkCommandImpl;
import org.example.command.PaperworkDocCommand;
import org.example.command.PaperworkDocCommandImpl;
import org.example.service.PaperworkService;
import org.example.validator.PapwerworkValidator;
import org.example.validator.PapwerworkValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaperworkConfig {

    @Autowired
    public PaperworkService paperworkService;

    @Bean
    public PaperworkCommand papwerworkCommand() {
        return new PaperworkCommandImpl(papwerworkValidator(), paperworkService, paperworkMapper());
    }

    @Bean
    public PaperworkDocCommand paperworkDocCommand() {
        return new PaperworkDocCommandImpl();
    }

    public PapwerworkValidator papwerworkValidator() {
        return new PapwerworkValidatorImpl();
    }

    public PaperworkMapper paperworkMapper() {
        return new PaperworkMapperImpl();
    }

}
