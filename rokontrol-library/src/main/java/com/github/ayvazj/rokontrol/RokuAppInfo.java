package com.github.ayvazj.rokontrol;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RokuAppInfo {
    public final String id;
    public final RokuAppType type;
    public final String version;
    public final String name;
    public final String appImageUrl;

    public RokuAppInfo(String id, RokuAppType type, String version, String name, String appImageUrl) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.name = name;
        this.appImageUrl = appImageUrl;
    }

    static List<RokuAppInfo> parseXml(final RokuExControlClient client, String xmlstr) {
        List<RokuAppInfo> result = new ArrayList<RokuAppInfo>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(new ByteArrayInputStream(xmlstr.getBytes()));
            Element root = dom.getDocumentElement();
            NodeList apps = root.getElementsByTagName("app");
            for (int i=0;i<apps.getLength();i++){
                Node app = apps.item(i);
                NamedNodeMap attrs = app.getAttributes();
                String id = attrs.getNamedItem("id").getNodeValue();
                RokuAppType type = RokuAppType.fromString(attrs.getNamedItem("type").getNodeValue());
                String version = attrs.getNamedItem("version").getNodeValue();
                RokuAppInfo newAppInfo = new RokuAppInfo(id, type, version, app.getTextContent(), client.getIconUrl(id));
                result.add(newAppInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
