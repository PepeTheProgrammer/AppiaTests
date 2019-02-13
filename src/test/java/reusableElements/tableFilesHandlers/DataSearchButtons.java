package reusableElements.tableFilesHandlers;

public enum DataSearchButtons {
    FILTER("Filter");


    private String value;

    DataSearchButtons(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
