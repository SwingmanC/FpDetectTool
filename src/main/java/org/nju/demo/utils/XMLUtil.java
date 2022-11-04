package org.nju.demo.utils;

import org.nju.demo.entity.*;
import org.nju.demo.pojo.dto.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {

    public static List<BugInstance> getBugs(InputStream file) throws Exception {
        List<BugInstance> bugInstances = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

        Document document = builder.parse(file);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("BugInstance");

        for(int i=0;i<nodeList.getLength();++i){
            Element bug = (Element) nodeList.item(i);
            BugInstance bugInstance = new BugInstance();
            bugInstance.setType(bug.getAttribute("type"));
            bugInstance.setPriority(Integer.parseInt(bug.getAttribute("priority")));
            bugInstance.setCategory(bug.getAttribute("category"));

            Element classElement = (Element) bug.getElementsByTagName("Class").item(0);
            bugInstance.setClassname(classElement.getAttribute("classname"));
            Element sourceElement = (Element) classElement.getElementsByTagName("SourceLine").item(0);
            bugInstance.setSourcePath(sourceElement.getAttribute("sourcepath"));
            if (!sourceElement.getAttribute("start").equals("")) bugInstance.setStart(Integer.parseInt(sourceElement.getAttribute("start")));
            if (!sourceElement.getAttribute("end").equals("")) bugInstance.setEnd(Integer.parseInt(sourceElement.getAttribute("end")));

            NodeList methodList = bug.getElementsByTagName("Method");
            for(int j=0;j<methodList.getLength();++j){
                Element methodElement = (Element) methodList.item(j);
                Method method = new Method();
                method.setName(methodElement.getAttribute("name"));
                method.setSignature(methodElement.getAttribute("signature"));
                Element methodSource = (Element) methodElement.getElementsByTagName("SourceLine").item(0);
                if(methodSource == null) continue;
                if(!methodSource.getAttribute("start").equals("")) method.setStartLine(Integer.parseInt(methodSource.getAttribute("start")));
                if(!methodSource.getAttribute("end").equals("")) method.setEndLine(Integer.parseInt(methodSource.getAttribute("end")));
                bugInstance.add(method);
            }
            bugInstances.add(bugInstance);
        }

        return bugInstances;
    }

    public static List<Violation> getViolations(InputStream file,String versionId) throws Exception {
        List<Violation> violationList = new ArrayList<>();
        List<BugInstance> bugInstances = XMLUtil.getBugs(file);
//        System.out.println(bugInstances.size());

        for(BugInstance bugInstance:bugInstances){
            List<Method> methods = bugInstance.getMethodList();
            for(Method method:methods){
                Violation violation = new Violation();
                violation.setVersionId(versionId);
                violation.setType(bugInstance.getType());
                violation.setCategory(bugInstance.getCategory());
                violation.setPriority(bugInstance.getPriority());
                violation.setClassName(bugInstance.getClassname());
                violation.setSourcePath(bugInstance.getSourcePath());
                violation.setMethodName(method.getName());
                violation.setSignature(method.getSignature());
                violation.setStartLine(method.getStartLine());
                violation.setEndLine(method.getEndLine());
                violationList.add(violation);
            }
        }

        return violationList;
    }
}
