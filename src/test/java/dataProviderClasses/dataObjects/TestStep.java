package dataProviderClasses.dataObjects;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import dataProviderClasses.MethodInvocation;

public class TestStep {

    private Integer stepId;
    private String methodName;
    private String[] paramTypes;
    private String[] params;

    public TestStep(Integer stepId, String methodName, String[] paramTypes, String ...params) throws WrongNumberArgsException {
        if(params.length != paramTypes.length){
            throw new WrongNumberArgsException("Number of paramTypes differs from number of parameters");
        }
        this.stepId = stepId;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
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
        try {
            MethodInvocation.callMethod("tests.importEditSearchTest.UserActions.java", methodName, paramTypes, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
