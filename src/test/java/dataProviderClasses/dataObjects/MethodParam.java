package dataProviderClasses.dataObjects;

import dataProviderClasses.converters.GenericObjectConverter;

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

    public String getStringParamValue() {
        return paramValue;
    }

    public void setStringParamValue(String param) {
        this.paramValue = param;
    }

    public Class getParamType() {
        return paramType;
    }

    public void setParamType(Class paramType) {
        this.paramType = paramType;
    }

    public <T> T getConvertedParamValue(){
        return (T)GenericObjectConverter.convert(paramValue, paramType);
    }

    @Override
    public String toString() {
        return "ParamType= "+paramType.toString() + "\n value= "+paramValue;
    }
}
