package dataProviderClasses.dataObjects;

public class Result {
    private boolean passed;
    private Exception exception;
    private String methodName;

    public Result(String methodName, boolean passed, Exception exception) {
        this.passed = passed;
        this.exception = exception;
        this.methodName = methodName;
    }

    public Result(String methodName, boolean passed) {
        this.methodName = methodName;
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
