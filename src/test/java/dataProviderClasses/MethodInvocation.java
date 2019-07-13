package dataProviderClasses;

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

    public static void callMethod(String className, String methodName, List<String> paramTypeNames, Object params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class methodClass = Class.forName(className);
        Class[] paramTypes = new Class[paramTypeNames.size()];
        for (int i = 0; i < paramTypeNames.size(); i++) {
            paramTypes[i] = Class.forName(paramTypeNames.get(i));
        }
        methodClass.getMethod(methodName, paramTypes).invoke(null, params);
    }

    public static void callMethod(String methodName,Object object, Object ...args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        object.getClass().getMethod(methodName).invoke(args);
    }


}