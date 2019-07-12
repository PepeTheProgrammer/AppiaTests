package dataProviderClasses.dataObjects;

import java.util.List;

public class TestModel {

    private String name;
    private List<TestStep> steps;

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

    public void execute(){
        for (TestStep step: steps) {
            step.execute();
        }
    }
}
