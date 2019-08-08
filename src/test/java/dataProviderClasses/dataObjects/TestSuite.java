package dataProviderClasses.dataObjects;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class TestSuite {

    private String name;
    private List<TestModel> tests;
    private List<Result> testsResults;
    private Result result;

    public TestSuite(String name, List<TestModel> tests) {
        this.name = name;
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestModel> getTests() {
        return tests;
    }

    public void setTests(List<TestModel> tests) {
        this.tests = tests;
    }

    public Result execute() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (TestModel test: tests) {
            try {
                testsResults.add(test.execute());
            } catch (Exception e){
                result = new Result(name, false, e);
            }
        }
        if(result == null){
            result = new Result(name, true);
        }
        return result;
    }

    @Override
    public String toString() {
        return "TestSuite{" +
                "name='" + name + '\'' +
                ", tests=" + tests +
                ", testsResults=" + testsResults +
                ", result=" + result +
                '}';
    }
}
