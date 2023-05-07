package org.example.model;

public class SGPNofiticationMessage {

    private String paperworkId;

    private String message;

    private SGPNofiticationMessage(SGPNofiticationMessageBuilder sgpNofiticationMessageBuilder) {
        this.paperworkId = sgpNofiticationMessageBuilder.paperworkId;
        this.message = sgpNofiticationMessageBuilder.message;
    }

    public String getPaperworkId() {
        return paperworkId;
    }

    public void setPaperworkId(String paperworkId) {
        this.paperworkId = paperworkId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class SGPNofiticationMessageBuilder {
        private String paperworkId;

        private String message;

        public static SGPNofiticationMessageBuilder create() {
            return new SGPNofiticationMessageBuilder();
        }

        public SGPNofiticationMessageBuilder withPaperworkId(String paperworkId){
            this.paperworkId = paperworkId;
            return this;
        }

        public SGPNofiticationMessageBuilder withMessage(String message){
            this.message = message;
            return this;
        }

        public SGPNofiticationMessage build() {
            return  new SGPNofiticationMessage(this);
        }
    }
}
