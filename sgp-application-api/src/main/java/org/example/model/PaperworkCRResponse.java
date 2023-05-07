package org.example.model;

public class PaperworkCRResponse {

    private String paperworkID;

    private OperationStatus operationStatus;

    private PaperworkCRResponse(PaperworkSaveResponseBuilder paperworkSaveResponseBuilder) {
        this.paperworkID = paperworkSaveResponseBuilder.paperworkID;
        this.operationStatus = paperworkSaveResponseBuilder.operationStatus;
    }

    public String getPaperworkID() {
        return paperworkID;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public static class PaperworkSaveResponseBuilder {

        private String paperworkID;

        private OperationStatus operationStatus;

        public static PaperworkCRResponse success(String paperworkID){
            return create(OperationStatus.SUCCESS).paperworkID(paperworkID).build();
        }

        public static PaperworkCRResponse fail(){
            return create(OperationStatus.FAILURE).build();
        }

        public static PaperworkCRResponse wrongStatus(){
            return create(OperationStatus.WRONG_STATUS).build();
        }

        public static PaperworkCRResponse notValid(){
            return create(OperationStatus.NOT_VALID).build();
        }

        private PaperworkSaveResponseBuilder(OperationStatus operationStatus){
            this.operationStatus = operationStatus;
        }

        private PaperworkSaveResponseBuilder paperworkID(String paperworkID) {
            this.paperworkID = paperworkID;
            return this;
        }

        private PaperworkSaveResponseBuilder operationStatus(OperationStatus operationStatus) {
            this.operationStatus = operationStatus;
            return this;
        }

        private static PaperworkSaveResponseBuilder create(OperationStatus operationStatus){
            return new PaperworkSaveResponseBuilder(operationStatus);
        }

        private PaperworkCRResponse build(){
            return new PaperworkCRResponse(this);
        }
    }
}
