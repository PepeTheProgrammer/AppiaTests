package dataProviderClasses.dataObjects;

public class Result {
    private boolean passed;
    private Exception exception;
    private String objectName;

    public Result(String objectName, boolean passed, Exception exception) {
        this.passed = passed;
        this.exception = exception;
        this.objectName = objectName;
    }

    public Result(String objectName, boolean passed) {
        this.objectName = objectName;
        this.passed = passed;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getResult(){
        return passed ? "Passed" : "Failed";
    }


}
