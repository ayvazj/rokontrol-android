package org.ray.upnp;

import java.util.ArrayList;
import java.util.List;

import org.ray.upnp.parser.Parser;
import org.ray.upnp.service.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Device {
    public static final String TAG = "device";
    public static final String TAG_DEVICE_TYPE = "deviceType";
    public static final String TAG_FRIENDLY_NAME = "friendlyName";
    public static final String TAG_MANUFACTURER = "manufacturer";
    public static final String TAG_MANUFACTURER_URL = "manufacturerURL";
    public static final String TAG_MODEL_DESCRIPTION = "modelDescription";
    public static final String TAG_MODEL_NAME = "modelName";
    public static final String TAG_MODEL_NUMBER = "modelNumber";
    public static final String TAG_MODEL_URL = "modelURL";
    public static final String TAG_SERIAL_NUMBER = "serialNumber";
    public static final String TAG_UDN = "UDN";
    public static final String TAG_UPC = "UPC";
    public static final String TAG_ICON_LIST = "iconList";
    public static final String TAG_SERVICE_LIST = "serviceList";

    /* Required. UPnP device type. */
    public String deviceType;
    /* Required. Short description for end user. */
    public String friendlyName;
    /* Required. Manufacturer's name. */
    public String manufacturer;
    /* Optional. Web site for manufacturer. */
    public String manufacturerURL;
    /* Recommended. Long description for end user. */
    public String modelDescription;
    /* Required. Model name. */
    public String modelName;
    /* Recommended. Model number. */
    public String modelNumber;
    /* Optional. Web site for model. */
    public String modelURL;
    /* Recommended. Serial number. */
    public String serialNumber;
    /* Required. Unique Device Name. */
    public String UDN;
    /* Optional. Universal Product Code. */
    public String UPC;
    /* Required. */
    List<Icon> iconList = new ArrayList<Icon>();
    /* Optional. */
    List<Service> serviceList = new ArrayList<Service>();

    public static Device createInstanceFromXML(String url) {
        final Device device = new Device();

        DefaultHandler dh = new DefaultHandler() {
            String currentValue = null;
            Icon currentIcon;
            Service currentService;

            @Override
            public void characters(char[] ch, int start, int length)
                    throws SAXException {
                currentValue = new String(ch, start, length);
            }

            @Override
            public void startElement(String uri, String localName,
                    String qName, Attributes attributes) throws SAXException {
                if (Icon.TAG.equals(qName)) {
                    currentIcon = new Icon();
                } else if (Service.TAG.equals(qName)) {
                    currentService = new Service();
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                /* Parse device-specific information */
                if (TAG_DEVICE_TYPE.equals(qName)) {
                    device.deviceType = currentValue;
                } else if (TAG_FRIENDLY_NAME.equals(qName)) {
                    device.friendlyName = currentValue;
                } else if (TAG_MANUFACTURER.equals(qName)) {
                    device.manufacturer = currentValue;
                } else if (TAG_MANUFACTURER_URL.equals(qName)) {
                    device.manufacturerURL = currentValue;
                } else if (TAG_MODEL_DESCRIPTION.equals(qName)) {
                    device.modelDescription = currentValue;
                } else if (TAG_MODEL_NAME.equals(qName)) {
                    device.modelName = currentValue;
                } else if (TAG_MODEL_NUMBER.equals(qName)) {
                    device.modelNumber = currentValue;
                } else if (TAG_MODEL_URL.equals(qName)) {
                    device.modelURL = currentValue;
                } else if (TAG_SERIAL_NUMBER.equals(qName)) {
                    device.serialNumber = currentValue;
                } else if (TAG_UDN.equals(qName)) {
                    device.UDN = currentValue;
                } else if (TAG_UPC.equals(qName)) {
                    device.UPC = currentValue;
                }
                /* Parse icon-list information */
                else if (Icon.TAG_MIME_TYPE.equals(qName)) {
                    currentIcon.mimetype = currentValue;
                } else if (Icon.TAG_WIDTH.equals(qName)) {
                    currentIcon.width = currentValue;
                } else if (Icon.TAG_HEIGHT.equals(qName)) {
                    currentIcon.height = currentValue;
                } else if (Icon.TAG_DEPTH.equals(qName)) {
                    currentIcon.depth = currentValue;
                } else if (Icon.TAG_URL.equals(qName)) {
                    currentIcon.url = currentValue;
                } else if (Icon.TAG.equals(qName)) {
                    device.iconList.add(currentIcon);
                }
                /* Parse service-list information */
                else if (Service.TAG_SERVICE_TYPE.equals(qName)) {
                    currentService.serviceType = currentValue;
                } else if (Service.TAG_SERVICE_ID.equals(qName)) {
                    currentService.serviceId = currentValue;
                } else if (Service.TAG_SCPD_URL.equals(qName)) {
                    currentService.SCPDURL = currentValue;
                } else if (Service.TAG_CONTROL_URL.equals(qName)) {
                    currentService.controlURL = currentValue;
                } else if (Service.TAG_EVENTSUB_URL.equals(qName)) {
                    currentService.eventSubURL = currentValue;
                } else if (Service.TAG.equals(qName)) {
                    device.serviceList.add(currentService);
                }
            }
        };

        Parser.getInstance().parse(url, dh);

        return device;
    }

    @Override
    public String toString() {
        return friendlyName;
    }

    static class Icon {
        static final String TAG = "icon";
        static final String TAG_MIME_TYPE = "mimetype";
        static final String TAG_WIDTH = "width";
        static final String TAG_HEIGHT = "height";
        static final String TAG_DEPTH = "depth";
        static final String TAG_URL = "url";

        /* Required. Icon's MIME type. */
        String mimetype;
        /* Required. Horizontal dimension of icon in pixels. */
        String width;
        /* Required. Vertical dimension of icon in pixels. */
        String height;
        /* Required. Number of color bits per pixel. */
        String depth;
        /* Required. Pointer to icon image. */
        String url;
    }
}
