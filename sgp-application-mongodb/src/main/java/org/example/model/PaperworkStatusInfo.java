package org.example.model;

import java.time.Instant;

public class PaperworkStatusInfo {
    Instant date;
    PaperworkStatus paperworkStatus;

    public PaperworkStatusInfo(PaperworkStatus paperworkStatus) {
        this.date = Instant.now();
        this.paperworkStatus = paperworkStatus;
    }

    public PaperworkStatusInfo() {
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public PaperworkStatus getPaperworkStatus() {
        return paperworkStatus;
    }

    public void setPaperworkStatus(PaperworkStatus paperworkStatus) {
        this.paperworkStatus = paperworkStatus;
    }
}
