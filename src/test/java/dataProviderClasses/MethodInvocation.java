package dataProviderClasses;

import java.lang.reflect.InvocationTargetException;

public class MethodInvocation {

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

    public void callMethod(String methodName, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        object.getClass().getMethod(methodName).invoke(object);
    }

    public void callMethod(String methodName, Object object, Object ...args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        object.getClass().getMethod(methodName).invoke(object, args);
    }


}