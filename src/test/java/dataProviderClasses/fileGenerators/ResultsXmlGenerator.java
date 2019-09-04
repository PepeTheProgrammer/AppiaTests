package dataProviderClasses.fileGenerators;

import dataProviderClasses.dataObjects.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

public class ResultsXmlGenerator {
    private String parseTestStep(TestStep step){
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t<step>\n");
        builder.append("\t\t\t<calledMethod>").append(step.getMethodName()).append("(");
        for (int i = 0;  i<step.getParameters().size(); i++) {
            builder.append(step.getParameters().get(i).getStringParamValue());
            if(i<step.getParameters().size()-1){
                builder.append(",");
            }
        }
        builder.append(")").append("</calledMethod>\n");
        builder.append("\t").append(parseResult(step.getResult(), true)).append("\t\t</step>\n");
        return builder.toString();
    }

    private String parseResult(Result result, boolean forStep){
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t<result>").append(result.getResult()).append("</result>\n");
        if(!result.isPassed()){
            if(forStep){
                builder.append("\t");
            }
            builder.append("\t\t<exception>").append(result.getException().toString()).append("</exception>\n");
        }
        return builder.toString();
    }

    private String parseTestModel(TestModel model){
        StringBuilder builder = new StringBuilder();
        builder.append("\t<test name=").append("\"").append(model.getName()).append("\">\n").append(parseResult(model.getResult(), false));
        for (TestStep step:model.getSteps()) {
            builder.append(parseTestStep(step));
        }
        builder.append("\t</test>\n");
        return builder.toString();
    }

    private String parseTestSuite(TestSuite suite){
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("\n\n<suite>\n");
        for (TestModel model: suite.getTests()) {
            builder.append(parseTestModel(model));
        }
        builder.append("</suite>");
        return builder.toString();
    }

    public File generateResultsXmlFile(List<TestSuite> suites) throws IOException, TransformerException {
        StringBuilder builder = new StringBuilder();
        for (TestSuite suite: suites) {
            builder.append(parseTestSuite(suite));
        }
        File results = new File("/home/applitopia/IdeaProjects/AppiaTests/src/test/results/result"
                +new Date()+".xml");

        Document document = convertStringToXMLDocument(builder.toString());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(results);


        transformer.transform(domSource, streamResult);
        return  results;
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
