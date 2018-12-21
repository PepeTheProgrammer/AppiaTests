package reusableElements;

public enum WindowButtons {
    CLOSE("closeButton"), MAXIMIZE("maximizeButton"), MINIMIZE("minimizeButton");

    private String value;

    WindowButtons(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
