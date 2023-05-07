package org.example.config;

import org.example.model.Paperwork;
import org.example.model.PaperworkRequest;
import org.example.model.PaperworkStatus;

public class PaperworkMapperImpl implements PaperworkMapper {

    @Override
    public Paperwork map(PaperworkRequest paperworkRequest) {
        // USE A SMART MAPPER (LIKE ORIKA MAPPER)
        // QUICK AND DIRTY :)
        return new Paperwork(null, paperworkRequest.getName(), paperworkRequest.getSurname(),
                paperworkRequest.getTaxCode(), paperworkRequest.getDateOfBirth(), null, PaperworkStatus.CREATED);
    }
}
