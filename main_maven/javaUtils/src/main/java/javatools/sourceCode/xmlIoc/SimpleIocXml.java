package javatools.sourceCode.xmlIoc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleIocXml {

    Map<String, Object> iocContainerMap = new HashMap<>();

    public SimpleIocXml(String xmlUrl) throws Exception {
        beanLoad(xmlUrl);
    }

    public Object getBean(String name) {
        Object bean = iocContainerMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }
        return bean;
    }

    private void beanLoad(String xmlUrl) throws Exception {
        InputStream is = new FileInputStream(xmlUrl);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        Element rootElement = document.getDocumentElement();
        NodeList beanNodes = rootElement.getChildNodes();
        for (int i = 0; i < beanNodes.getLength(); i++) {
            Node beanNode = beanNodes.item(i);
            if (beanNode instanceof Element) {
                Element ele = (Element)beanNode;
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");
                Class clz;
                try {
                    clz = Class.forName(className);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                Object beanObj = clz.newInstance();
                NodeList propertyNodes = ele.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element)propertyNode;
                        String proName = propertyElement.getAttribute("name");
                        String proValue = propertyElement.getAttribute("value");
                        Field declaredField = beanObj.getClass().getDeclaredField(proName);
                        declaredField.setAccessible(true);
                        if (proValue != null && proValue.length() > 0) {
                            declaredField.set(beanObj, proValue);
                        } else {
                            String proRef = propertyElement.getAttribute("ref");
                            if (proRef == null || proRef.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            } else {
                                declaredField.set(beanObj, getBean(proRef));
                            }
                        }
                        iocContainerMap.put(id, beanObj);
                    }
                }
            }
        }
    }
}
