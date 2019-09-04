package dataProviderClasses;

import dataProviderClasses.converters.GenericObjectConverter;
import dataProviderClasses.dataObjects.MethodParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodInvocation {


    public MethodInvocation() {
    }

    public static void callMethod(String className, String methodName, String[] paramTypeNames, String... StringParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {


        Class methodClass = Class.forName(className);
        Class[] paramTypes = new Class[paramTypeNames.length];
        Object[] params = new Object[StringParams.length];
        for (int i = 0; i < paramTypeNames.length; i++) {
            paramTypes[i] = Class.forName(paramTypeNames[i]);
            params[i] = GenericObjectConverter.convert(params[i], paramTypes[i]);
        }
        Method method = methodClass.getMethod(methodName, paramTypes);
        method.invoke(null, params);
    }

    public static void callMethod(String className, String methodName, List<MethodParam> parameters) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class methodClass = Class.forName(className);
        Class[] paramTypes = new Class[parameters.size()];
        Object[] params = new Object[parameters.size()];
        for (int i = 0; i <parameters.size() ; i++) {
            paramTypes[i] = parameters.get(i).getParamType();
            params[i] = parameters.get(i).getConvertedParamValue();
        }
        Method method = methodClass.getMethod(methodName, paramTypes);
        method.invoke(null, params);
    }



}