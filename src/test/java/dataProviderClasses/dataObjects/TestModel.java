package dataProviderClasses.dataObjects;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class TestModel {

    private String name;
    private List<TestStep> steps;
    private Result result;
    private List<Result> stepsResults;

    public TestModel(String name, List<TestStep> steps) {
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestStep> getSteps() {
        return steps;
    }

    public void setSteps(List<TestStep> steps) {
        this.steps = steps;
    }


    public Result execute() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (TestStep step: steps) {
            try {
                stepsResults.add(step.execute());
            } catch (Exception e) {
                result = new Result(name, false, e);
            }
        }
        if(result == null){
            result = new Result(name, true);
        }
        return result;
    }
}
