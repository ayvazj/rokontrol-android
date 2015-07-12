package com.github.ayvazj.rokontrol;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RokuDeviceData {
    public final String deviceType;
    public final String friendlyName;
    public final String manufacturer;
    public final String manufacturerURL;
    public final String modelDescription;
    public final String modelName;
    public final String modelNumber;
    public final String modelURL;
    public final String serialNumber;
    public final String UDN;
    public final List<RokuServiceInfo> serviceList;


    public RokuDeviceData(String deviceType, String friendlyName, String manufacturer, String manufacturerURL,
                          String modelDescription, String modelName, String modelNumber, String modelURL, String UDN, String serialNumber, List<RokuServiceInfo> serviceList) {
        this.deviceType = deviceType;
        this.friendlyName = friendlyName;
        this.manufacturer = manufacturer;
        this.manufacturerURL = manufacturerURL;
        this.modelDescription = modelDescription;
        this.modelName = modelName;
        this.modelNumber = modelNumber;
        this.modelURL = modelURL;
        this.serialNumber = serialNumber;
        this.UDN = UDN;
        this.serviceList = serviceList;
    }

    static RokuDeviceData parseXml(String xmlstr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(new ByteArrayInputStream(xmlstr.getBytes()));
            Element root = dom.getDocumentElement();
            NodeList devices = root.getElementsByTagName("device");
            for (int i = 0; i < devices.getLength(); i++) {
                if (devices.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element device = (Element) devices.item(i);

                String deviceType = "";
                String friendlyName = "";
                String manufacturer = "";
                String manufacturerURL = "";
                String modelDescription = "";
                String modelName = "";
                String modelNumber = "";
                String modelURL = "";
                String serialNumber = "";
                String UDN = "";
                List<RokuServiceInfo> serviceList = new ArrayList<RokuServiceInfo>();

                NodeList children = device.getChildNodes();
                for (int ci = 0; ci < children.getLength(); ci++) {
                    if (children.item(ci).getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    Element childElement = (Element) children.item(ci);
                    if ("deviceType".equals(childElement.getTagName())) {
                        deviceType = childElement.getTextContent();
                    } else if ("friendlyName".equals(childElement.getTagName())) {
                        friendlyName = childElement.getTextContent();
                    } else if ("manufacturer".equals(childElement.getTagName())) {
                        manufacturer = childElement.getTextContent();
                    } else if ("manufacturerURL".equals(childElement.getTagName())) {
                        manufacturerURL = childElement.getTextContent();
                    } else if ("modelDescription".equals(childElement.getTagName())) {
                        modelDescription = childElement.getTextContent();
                    } else if ("modelName".equals(childElement.getTagName())) {
                        modelName = childElement.getTextContent();
                    } else if ("modelNumber".equals(childElement.getTagName())) {
                        modelNumber = childElement.getTextContent();
                    } else if ("modelURL".equals(childElement.getTagName())) {
                        modelURL = childElement.getTextContent();
                    } else if ("serialNumber".equals(childElement.getTagName())) {
                        serialNumber = childElement.getTextContent();
                    } else if ("UDN".equals(childElement.getTagName())) {
                        UDN = childElement.getTextContent();
                    } else if ("serviceList".equals(childElement.getTagName())) {
                        serviceList = RokuServiceInfo.parseXml(childElement);
                    }
                }

                return new RokuDeviceData(deviceType, friendlyName, manufacturer, manufacturerURL,
                        modelDescription, modelName, modelNumber, modelURL, UDN, serialNumber, serviceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
