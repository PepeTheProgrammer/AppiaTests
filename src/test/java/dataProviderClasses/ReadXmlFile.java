package dataProviderClasses;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.Map;

public class ReadXmlFile {

    private List<Integer> stepId;
    private String methodName;
    private List<String> params;
    private Map<String, List> methodMap;

    public ReadXmlFile(File fXmlFile){

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("step");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("step id : " + eElement.getAttribute("id"));
                    System.out.println("Method name : " + eElement.getElementsByTagName("method").item(0).getTextContent());
                    if(eElement.getElementsByTagName("methodParams").item(0)!=null) {
                        System.out.println("Method params : " + eElement.getElementsByTagName("methodParams").item(0).getTextContent());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
