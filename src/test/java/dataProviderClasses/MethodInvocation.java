package dataProviderClasses;

import dataProviderClasses.dataObjects.MethodParam;
import tests.importEditSearchTest.UserActions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MethodInvocation {


    //TODO: to tez na statyczna klase
    private Class loadedClass;

    public MethodInvocation(String className) {
        try {
            this.loadedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.err.println("Not able to find Class");
            e.printStackTrace();
        }
    }

    public MethodInvocation() {
    }

    public static void callMethod(String className, String methodName, String[] paramTypeNames, String... StringParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        for (int i = 0; i <paramTypeNames.length ; i++) {
            System.out.println(paramTypeNames[i]);
        }
        for (int i = 0; i <StringParams.length ; i++) {
            System.out.println(StringParams[i]);
        }
        Class methodClass = Class.forName(className);
        Class[] paramTypes = new Class[paramTypeNames.length];
        Object[] params = new Object[StringParams.length];
        for (int i = 0; i < paramTypeNames.length; i++) {
            paramTypes[i] = Class.forName(paramTypeNames[i]);
            params[i] = paramTypes[i].cast(StringParams[i]);
        }
        methodClass.getMethod(methodName, paramTypes).invoke(null, params);
    }



}