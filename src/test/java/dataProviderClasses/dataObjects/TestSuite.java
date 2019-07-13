package dataProviderClasses.dataObjects;

import java.util.List;

public class TestSuite {

    private String name;
    private List<TestModel> tests;

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

    public void execute(){
        for (TestModel test: tests) {
            test.execute();
        }
    }
}
