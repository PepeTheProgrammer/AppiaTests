package dataProviderClasses.dataObjects;

public class TestStep {

    private Integer stepId;
    private String methodName;
    private String[] params;

    public TestStep(Integer stepId, String methodName, String ...params) {
        this.stepId = stepId;
        this.methodName = methodName;
        this.params = params;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void execute(){

    }
}
