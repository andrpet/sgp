package org.example.config;

import org.example.model.Paperwork;
import org.example.model.PaperworkRequest;

public interface PaperworkMapper {
    Paperwork map(PaperworkRequest paperworkRequest);
}
