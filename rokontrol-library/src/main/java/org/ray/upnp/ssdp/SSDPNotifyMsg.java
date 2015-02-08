package org.ray.upnp.ssdp;

import java.net.DatagramPacket;

public class SSDPNotifyMsg {
    public static boolean isSSDPNotifyMsg(DatagramPacket dp) {
        String startLine = SSDP.parseStartLine(dp);
        if (SSDP.SL_NOTIFY.equals(startLine)) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isAlive(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        if (SSDP.NTS_ALIVE.equals(NTSValue)) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isByeByte(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        if (SSDP.NTS_BYEBYE.equals(NTSValue)) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isUpdate(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        if (SSDP.NTS_UPDATE.equals(NTSValue)) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isContentDirectory(DatagramPacket dp) {
        String NTValue = SSDP.parseHeaderValue(dp, SSDP.NT);
        if (SSDP.NT_CONTENT_DIRECTORY.equals(NTValue)) {
            return true;
        }
        
        return false;
    }
}
