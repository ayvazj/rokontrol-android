package org.ray.upnp.ssdp;

import java.net.DatagramPacket;
import java.util.Scanner;

import org.ray.upnp.UPNP;

public class SSDP {
    /* New line definition */
    public static final String NEWLINE = "\r\n";
    
    public static final String ADDRESS = "239.255.255.250";
    public static final int PORT = 1900;
    
    public static final String ST = "ST";
    public static final String LOCATION = "LOCATION";
    public static final String NT = "NT";
    public static final String NTS = "NTS";

    /* Definitions of start line */
    public static final String SL_NOTIFY = "NOTIFY * HTTP/1.1";
    public static final String SL_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String SL_OK = "HTTP/1.1 200 OK";

    /* Definitions of search targets */
    public static final String ST_ContentDirectory = ST + ":" + UPNP.SERVICE_CONTENT_DIRECTORY_1;
    
    /* Definitions of notification sub type */
    public static final String NTS_ALIVE = "ssdp:alive";
    public static final String NTS_BYEBYE = "ssdp:byebye";
    public static final String NTS_UPDATE = "ssdp:update";
    
    /* Definitions of notification type */
    public static final String NT_CONTENT_DIRECTORY = UPNP.SERVICE_CONTENT_DIRECTORY_1;
    
    public static String parseHeaderValue(String content, String headerName) {
        Scanner s = new Scanner(content);
        s.nextLine(); // Skip the start line
        
        while (s.hasNextLine()) {
            String line = s.nextLine();
            int index = line.indexOf(':');
            String header = line.substring(0, index);
            if (headerName.equalsIgnoreCase(header.trim())) {
                return line.substring(index + 1).trim();
            }
        }

        return null;
    }
    
    public static String parseHeaderValue(DatagramPacket dp, String headerName) {
        return parseHeaderValue(new String(dp.getData()), headerName);
    }
    
    public static String parseStartLine(String content) {
        Scanner s = new Scanner(content);
        return s.nextLine();
    }
    
    public static String parseStartLine(DatagramPacket dp) {
        return parseStartLine(new String(dp.getData()));
    }
}
