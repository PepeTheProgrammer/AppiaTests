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
                    List<TestModel> testsList = new ArrayList<>();
                    Element eElement = (Element) suiteNode;
                    String suiteName = eElement.getAttribute("name");
                    System.out.println(suiteName);

                    NodeList testsNodeList = eElement.getElementsByTagName("test");

                    for (int i = 0; i <testsNodeList.getLength() ; i++) {
                        Node testNode = testsNodeList.item(i);

                        if(testNode.getNodeType() == Node.ELEMENT_NODE) {
                            List<TestStep> stepsList = new ArrayList<>();
                            Element testElement = (Element) testNode;
                            String testName = testElement.getAttribute("name");
                            System.out.println(testName);
                            NodeList stepsNodeList = testElement.getElementsByTagName("step");

                            for (int j = 0; j <stepsNodeList.getLength() ; j++) {

                                Node stepNode = stepsNodeList.item(j);
                                if(stepNode.getNodeType() == Node.ELEMENT_NODE){
                                    Element stepElement = (Element) stepNode;

                                    String methodName = stepElement.getElementsByTagName("method").item(0).getTextContent();
                                    String[] params = new String[stepElement.getElementsByTagName("methodParam").getLength()];
                                    String[] paramTypes = new String[params.length];
                                    List<MethodParam> parameters = new ArrayList<>();
                                    for (int k = 0; k < stepElement.getElementsByTagName("methodParam").getLength(); k++) {
                                        Element paramElement = (Element) eElement.getElementsByTagName("methodParam").item(k);
                                        String value = paramElement.getTextContent();
                                        String type = paramElement.getAttribute("type");
                                        parameters.add(new MethodParam(type, value));
                                    }
                                    stepsList.add(new TestStep(methodName, parameters));
                                }
                            }
                         testsList.add(new TestModel(testName, stepsList));
                        }
                    }

                    suitesList.add(new TestSuite(suiteName, testsList));
                }
            }
        return suitesList;
    }



}
