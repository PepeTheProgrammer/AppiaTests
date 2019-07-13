package dataProviderClasses.dataObjects;

public class MethodParam {

    private Class paramType;
    private String paramValue;

    public MethodParam(Class paramType ,String paramValue) {
        this.paramType = paramType;
        this.paramValue = paramValue;
    }

    public MethodParam(String paramType, String paramValue) throws ClassNotFoundException {
        this.paramType = Class.forName(paramType);
        this.paramValue = paramValue;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String param) {
        this.paramValue = param;
    }

    public Class getParamType() {
        return paramType;
    }

    public void setParamType(Class paramType) {
        this.paramType = paramType;
    }
}
