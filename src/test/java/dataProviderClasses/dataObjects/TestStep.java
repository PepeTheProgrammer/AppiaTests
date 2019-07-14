package dataProviderClasses.dataObjects;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import dataProviderClasses.MethodInvocation;

import java.util.List;

public class TestStep {

    private Integer stepId;
    private String methodName;
    private List<MethodParam> parameters;

    public TestStep(String methodName, String[] paramTypes, String ...params) throws WrongNumberArgsException {
        if(params.length != paramTypes.length){
            throw new WrongNumberArgsException("Number of paramTypes differs from number of parameters");
        }
        this.methodName = methodName;
    }

    public TestStep(String methodName, List<MethodParam> parameters){
        this.methodName = methodName;
        this.parameters = parameters;
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

    public List<MethodParam> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParam> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(MethodParam param){
        parameters.add(param);
    }

    public void execute(){
        try {
            MethodInvocation.callMethod("tests.importEditSearchTest.UserActions", methodName, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
