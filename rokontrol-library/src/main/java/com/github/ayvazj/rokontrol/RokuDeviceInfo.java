package com.github.ayvazj.rokontrol;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RokuDeviceInfo {
    public final String UDN;
    public final String serialNumber;
    public final String deviceId;
    public final String vendorName;
    public final String modelNumber;
    public final String modelName;
    public final String userDeviceName;
    public final String softwareVersion;
    public final String softwareBuild;
    public final boolean secureDevice;
    public final String language;
    public final String country;
    public final String locale;
    public final String powerMode;
    public final boolean developerEnabled;
    public final boolean searchEnabled;
    public final boolean voiceSearchEnabled;
    public final boolean notificationsEnabled;
    public final boolean notificationsFirstUse;
    public final boolean headphonesConnected;


    private RokuDeviceInfo(String UDN,
                           String serialNumber,
                           String deviceId,
                           String vendorName,
                           String modelNumber,
                           String modelName,
                           String userDeviceName,
                           String softwareVersion,
                           String softwareBuild,
                           boolean secureDevice,
                           String language,
                           String country,
                           String locale,
                           String powerMode,
                           boolean developerEnabled,
                           boolean searchEnabled,
                           boolean voiceSearchEnabled,
                           boolean notificationsEnabled,
                           boolean notificationsFirstUse,
                           boolean headphonesConnected) {
        this.UDN = UDN;
        this.serialNumber = serialNumber;
        this.deviceId = deviceId;
        this.vendorName = vendorName;
        this.modelNumber = modelNumber;
        this.modelName = modelName;
        this.userDeviceName = userDeviceName;
        this.softwareVersion = softwareVersion;
        this.softwareBuild = softwareBuild;
        this.secureDevice = secureDevice;
        this.language = language;
        this.country = country;
        this.locale = locale;
        this.powerMode = powerMode;
        this.developerEnabled = developerEnabled;
        this.searchEnabled = searchEnabled;
        this.voiceSearchEnabled = voiceSearchEnabled;
        this.notificationsEnabled = notificationsEnabled;
        this.notificationsFirstUse = notificationsFirstUse;
        this.headphonesConnected = headphonesConnected;
    }

    public static RokuDeviceInfo parseXml(String xmlstr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(new ByteArrayInputStream(xmlstr.getBytes()));
            Element root = dom.getDocumentElement();
            NodeList children = root.getChildNodes();

            String UDN = "";
            String serialNumber = "";
            String deviceId = "";
            String vendorName = "";
            String modelNumber = "";
            String modelName = "";
            String userDeviceName = "";
            String softwareVersion = "";
            String softwareBuild = "";
            boolean secureDevice = false;
            String language = "";
            String country = "";
            String locale = "";
            String powerMode = "";
            boolean developerEnabled = false;
            boolean searchEnabled = false;
            boolean voiceSearchEnabled = false;
            boolean notificationsEnabled = false;
            boolean notificationsFirstUse = false;
            boolean headphonesConnected = false;

            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element childElement = (Element) children.item(i);


                if ("udn".equals(childElement.getTagName())) {
                    UDN = childElement.getTextContent();
                } else if ("serial-number".equals(childElement.getTagName())) {
                    serialNumber = childElement.getTextContent();
                } else if ("device-id".equals(childElement.getTagName())) {
                    deviceId = childElement.getTextContent();
                } else if ("vendor-name".equals(childElement.getTagName())) {
                    vendorName = childElement.getTextContent();
                } else if ("model-number".equals(childElement.getTagName())) {
                    modelNumber = childElement.getTextContent();
                } else if ("model-name".equals(childElement.getTagName())) {
                    modelName = childElement.getTextContent();
                } else if ("user-device-name".equals(childElement.getTagName())) {
                    userDeviceName = childElement.getTextContent();
                } else if ("software-version".equals(childElement.getTagName())) {
                    softwareVersion = childElement.getTextContent();
                } else if ("software-build".equals(childElement.getTagName())) {
                    softwareBuild = childElement.getTextContent();
                } else if ("language".equals(childElement.getTagName())) {
                    language = childElement.getTextContent();
                } else if ("country".equals(childElement.getTagName())) {
                    country = childElement.getTextContent();
                } else if ("locale".equals(childElement.getTagName())) {
                    locale = childElement.getTextContent();
                } else if ("power-mode".equals(childElement.getTagName())) {
                    powerMode = childElement.getTextContent();
                } else if ("developer-enabled".equals(childElement.getTagName())) {
                    developerEnabled = Boolean.valueOf(childElement.getTextContent());
                } else if ("search-enabled".equals(childElement.getTagName())) {
                    searchEnabled = Boolean.valueOf(childElement.getTextContent());
                } else if ("voide-search-enabled".equals(childElement.getTagName())) {
                    voiceSearchEnabled = Boolean.valueOf(childElement.getTextContent());
                } else if ("notifications-enabled".equals(childElement.getTagName())) {
                    notificationsEnabled = Boolean.valueOf(childElement.getTextContent());
                } else if ("notifications-first-use".equals(childElement.getTagName())) {
                    notificationsFirstUse = Boolean.valueOf(childElement.getTextContent());
                } else if ("headphones-connected".equals(childElement.getTagName())) {
                    headphonesConnected = Boolean.valueOf(childElement.getTextContent());
                }
            }
            return new RokuDeviceInfo(UDN,
                    serialNumber,
                    deviceId,
                    vendorName,
                    modelNumber,
                    modelName,
                    userDeviceName,
                    softwareVersion,
                    softwareBuild,
                    secureDevice,
                    language,
                    country,
                    locale,
                    powerMode,
                    developerEnabled,
                    searchEnabled,
                    voiceSearchEnabled,
                    notificationsEnabled,
                    notificationsFirstUse,
                    headphonesConnected);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
