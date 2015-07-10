package com.github.ayvazj.rokontrol;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class RokuServiceInfo {
    public final String serviceType;
    public final String serviceId;
    public final String controlURL;
    public final String eventSubURL;
    public final String SCPDURL;

    public RokuServiceInfo(String serviceType, String serviceId, String controlURL, String eventSubURL, String SCPDURL) {
        this.serviceType = serviceType;
        this.serviceId = serviceId;
        this.controlURL = controlURL;
        this.eventSubURL = eventSubURL;
        this.SCPDURL = SCPDURL;
    }

    public static List<RokuServiceInfo> parseXml(Element element) {
        List<RokuServiceInfo> result = new ArrayList<RokuServiceInfo>();
        NodeList serviceElements = element.getElementsByTagName("service");
        for (int i = 0; i < serviceElements.getLength(); i++) {
            NodeList children = serviceElements.item(i).getChildNodes();
            String serviceType = "";
            String serviceId = "";
            String controlURL = "";
            String eventSubURL = "";
            String SCPDURL = "";
            for (int ci = 0; ci < children.getLength(); ci++) {
                if (children.item(ci).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element childElement = (Element) children.item(ci);
                if ("serviceType".equals(childElement.getTagName())) {
                    serviceType = childElement.getTextContent();
                } else if ("serviceId".equals(childElement.getTagName())) {
                    serviceId = childElement.getTextContent();
                } else if ("controlURL".equals(childElement.getTagName())) {
                    controlURL = childElement.getTextContent();
                } else if ("eventSubURL".equals(childElement.getTagName())) {
                    eventSubURL = childElement.getTextContent();
                } else if ("SCPDURL".equals(childElement.getTagName())) {
                    SCPDURL = childElement.getTextContent();
                }
            }
            result.add(new RokuServiceInfo(serviceType, serviceId, controlURL, eventSubURL, SCPDURL));
        }
        return result;
    }
}
