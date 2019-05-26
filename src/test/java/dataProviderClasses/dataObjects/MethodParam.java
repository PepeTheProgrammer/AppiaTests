package dataProviderClasses.dataObjects;

public class MethodParam<T> {
    private T param;

    public MethodParam(T param) {
        this.param = param;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
