package dataProviderClasses;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import dataProviderClasses.dataObjects.MethodParam;
import dataProviderClasses.dataObjects.TestModel;
import dataProviderClasses.dataObjects.TestStep;
import dataProviderClasses.dataObjects.TestSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadXmlFile {

    public static List<TestSuite> readTestSuite(File fXmlFile) throws ParserConfigurationException, IOException, SAXException, WrongNumberArgsException, ClassNotFoundException {
            List<TestSuite> suitesList = new ArrayList<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList suitesNodeList = doc.getElementsByTagName("Suite");


            for (int temp = 0; temp < suitesNodeList.getLength(); temp++) {

                Node suiteNode = suitesNodeList.item(temp);

                if (suiteNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element suiteElement = (Element) suiteNode;

                    String suiteName = suiteElement.getAttribute("name");
                    List<TestModel> testsList = readTestsFromSuite(suiteElement);

                    suitesList.add(new TestSuite(suiteName, testsList));
                }
            }
        return suitesList;
    }

    private static List<MethodParam> readMethodParamsFromStep(Element stepElement) throws ClassNotFoundException {
        List<MethodParam> parameters = new ArrayList<>();

        for (int k = 0; k < stepElement.getElementsByTagName("methodParam").getLength(); k++) {
            Element paramElement = (Element) stepElement.getElementsByTagName("methodParam").item(k);
            String value = paramElement.getTextContent();
            String type = paramElement.getAttribute("type");
            System.out.println(value + "\n" + type);
            parameters.add(new MethodParam(type, value));
        }
        return parameters;
    }

    private static List<TestStep> readStepsFromTest(Element testElement) throws ClassNotFoundException {
        List<TestStep> stepsList = new ArrayList<>();
        NodeList stepsNodeList = testElement.getElementsByTagName("step");

        for (int j = 0; j <stepsNodeList.getLength() ; j++) {

            Node stepNode = stepsNodeList.item(j);
            if(stepNode.getNodeType() == Node.ELEMENT_NODE){
                Element stepElement = (Element) stepNode;

                String methodName = stepElement.getElementsByTagName("method").item(0).getTextContent();
                System.out.println(methodName);
                List<MethodParam> parameters = readMethodParamsFromStep(stepElement);
                stepsList.add(new TestStep(methodName, parameters));
            }
        }
        return stepsList;
    }

    private static List<TestModel> readTestsFromSuite(Element suiteElement) throws ClassNotFoundException {
        List<TestModel> testsList = new ArrayList<>();

        NodeList testsNodeList = suiteElement.getElementsByTagName("test");

        for (int i = 0; i <testsNodeList.getLength() ; i++) {
            Node testNode = testsNodeList.item(i);

            if(testNode.getNodeType() == Node.ELEMENT_NODE) {
                Element testElement = (Element) testNode;
                String testName = testElement.getAttribute("name");

                List<TestStep> stepsList = readStepsFromTest(testElement);

                testsList.add(new TestModel(testName, stepsList));
            }
        }
        return testsList;
    }

}
