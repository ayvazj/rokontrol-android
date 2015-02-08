package org.ray.upnp.ssdp;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class SSDPSocket {
    private static final int TIMEOUT_MS = 2000;
    private final Context context;
    SocketAddress mSSDPMulticastGroup;
    MulticastSocket mLocalSocket;
    NetworkInterface mNetIf;

    InetAddress broadcastAddress;

    public SSDPSocket(Context context) throws IOException {
        this.context = context;

        InetAddress localInAddress = InetAddress.getLocalHost();

        mSSDPMulticastGroup = new InetSocketAddress(SSDP.ADDRESS, SSDP.PORT);
        mLocalSocket = new MulticastSocket();
        mNetIf = NetworkInterface.getByInetAddress(localInAddress);

        mLocalSocket.joinGroup(mSSDPMulticastGroup, mNetIf);

//        mLocalSocket = new DatagramSocket(SSDP.PORT);
//        mLocalSocket.setBroadcast(true);
        mLocalSocket.setSoTimeout(TIMEOUT_MS);
    }

    /**
     * Used to send SSDP packet
     */
    public void send(String data) throws IOException {
        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.length(),
                getBroadcastAddress(), SSDP.PORT);

        mLocalSocket.send(dp);
    }

    /**
     * Used to receive SSDP packet
     */
    public List<DatagramPacket> receive() throws IOException {
        long start = System.currentTimeMillis();
        byte[] buf = new byte[1024];

        List<DatagramPacket> packets = new ArrayList<DatagramPacket>();
        // Loop and try to receive responses until the timeout elapses. We'll get
        // back the packet we just sent out, which isn't terribly helpful, but we'll
        // discard it in parseResponse because the cmd is wrong.
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                mLocalSocket.receive(packet);
                packets.add(packet);
                String s = new String(packet.getData(), 0, packet.getLength());
            }
        } catch (SocketTimeoutException e) {
            Log.d("TAG", "Receive timed out");
        }
        return packets;
    }

    /**
     * Close the socket
     */
    public void close() {
        if (mLocalSocket != null) {

            try {
                mLocalSocket.leaveGroup(mSSDPMulticastGroup, mNetIf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mLocalSocket.close();
        }
    }

    private InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }
}
