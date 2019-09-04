package dataProviderClasses.fileGenerators;

import dataProviderClasses.dataObjects.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ResultsXmlGenerator {
    private String parseTestStep(TestStep step){
        StringBuilder builder = new StringBuilder();
        builder.append("<step>\n");
        builder.append("<calledMethod>").append(step.getMethodName()).append("(");
        for (int i = 0;  i<step.getParameters().size(); i++) {
            builder.append(step.getParameters().get(i).getStringParamValue());
            if(i<step.getParameters().size()-1){
                builder.append(",");
            }
        }
        builder.append(")").append("</calledMethod>\n");
        builder.append(parseResult(step.getResult())).append("</step>\n");
        return builder.toString();
    }

    private String parseResult(Result result){
        StringBuilder builder = new StringBuilder();
        builder.append("<result>").append(result.getResult()).append("</result>\n");
        if(!result.isPassed()){
            builder.append("<exception>").append(result.getException().toString()).append("</exception>\n");
        }
        return builder.toString();
    }

    private String parseTestModel(TestModel model){
        StringBuilder builder = new StringBuilder();
        builder.append("<test name=").append("\"").append(model.getName()).append("\">\n").append(parseResult(model.getResult()));
        for (TestStep step:model.getSteps()) {
            builder.append(parseTestStep(step));
        }
        builder.append("</test>");
        return builder.toString();
    }

    private String parseTestSuite(TestSuite suite){
        StringBuilder builder = new StringBuilder();
        builder.append("<suite>\n");
        for (TestModel model: suite.getTests()) {
            builder.append(parseTestModel(model));
        }
        builder.append("</suite>\n");
        return builder.toString();
    }

    public File generateResultsXmlFile(List<TestSuite> suites) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (TestSuite suite: suites) {
            builder.append(parseTestSuite(suite));
        }
        File results = new File("C:\\Users\\user\\IdeaProjects\\AppiaTests\\src\\test\\results\\result"
                +new Date().getDay()+".xml");
        FileWriter writer = new FileWriter(results);
        writer.write(builder.toString());
        return  results;
    }
}
