package dataProviderClasses;

import tests.importEditSearchTest.UserActions;

import java.lang.reflect.InvocationTargetException;

public class MethodInvocation {


    //TODO: to tez na statyczna klase
    private Class loadedClass;
    private static UserActions actions = new UserActions();

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

    public void callMethod(String methodName, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        object.getClass().getMethod(methodName).invoke(object);
    }

    public static void callMethod(String methodName, Object ...args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        object.getClass().getMethod(methodName).invoke(object, args);
    }


}