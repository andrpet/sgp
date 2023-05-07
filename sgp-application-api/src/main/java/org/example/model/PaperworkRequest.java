package org.example.model;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

public class PaperworkRequest {
    private Mono<FilePart> filePartMono;
    private String name;
    private String surname;
    private String taxCode;
    private Instant dateOfBirth;

    private PaperworkRequest(Mono<FilePart> filePartMono, String name, String surname, String taxCode, Instant dateOfBirth) {
        this.filePartMono = filePartMono;
        this.name = name;
        this.surname = surname;
        this.taxCode = taxCode;
        this.dateOfBirth = dateOfBirth;
    }

    public Mono<FilePart> getFilePartMono() {
        return filePartMono;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public static class PaperworkRequestBuilder {
        private Mono<FilePart> filePartMono;
        private String name;
        private String surname;
        private String taxCode;
        private Instant dateOfBirth;

        public static PaperworkRequestBuilder create() {
            return new PaperworkRequestBuilder();
        }

        public PaperworkRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PaperworkRequestBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public PaperworkRequestBuilder withTaxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        public PaperworkRequestBuilder withDateOfBirth(String dateOfBirth) {
            Optional.ofNullable(dateOfBirth).ifPresent(v -> this.dateOfBirth = Instant.ofEpochSecond(Integer.parseInt(v)));
            return this;
        }

        public PaperworkRequestBuilder withFile(Mono<FilePart> filePartMono) {
            this.filePartMono = filePartMono;
            return this;
        }

        public PaperworkRequest build() {
            return new PaperworkRequest(filePartMono, name, surname, taxCode, dateOfBirth);
        }

    }
}
