package dataProviderClasses;

import dataProviderClasses.dataObjects.MethodParam;
import dataProviderClasses.dataObjects.TestStep;
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

    public static List<TestStep> getStepsList(File fXmlFile) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException {
            List<TestStep> stepsList = new ArrayList<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("step");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    Integer id = Integer.parseInt(eElement.getAttribute("id"));
                    String methodName = eElement.getElementsByTagName("method").item(0).getTextContent();
                    String[] params = new String[eElement.getElementsByTagName("methodParam").getLength()];
                    List<MethodParam> parameters;
                    for (int i = 0; i < eElement.getElementsByTagName("methodParam").getLength(); i++) {
                        Element paramElement = (Element) eElement.getElementsByTagName("methodParam").item(i);
                        String paramType = paramElement.getAttribute("type");
                        Class c = Class.forName(paramType);
                        parameters.add(new MethodParam<>(c.cast()));
                    }

                    stepsList.add(new TestStep(id, methodName, params));
                }
            }
        return stepsList;
    }

}
