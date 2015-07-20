package com.github.ayvazj.rokontrol;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import org.ray.upnp.ssdp.SSDP;
import org.ray.upnp.ssdp.SSDPSearchMsg;
import org.ray.upnp.ssdp.SSDPSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class RokuWifiDiscovery {

    private final Context context;

    public interface OnSearchForDevicesOnNetworkCompleted {
        void onSearchForDevicesOnNetworkComplete(List<RokuSearchResult> results);
    }

    public RokuWifiDiscovery(Context context) {
        this.context = context;
    }

    public void searchForDevicesOnNetwork(OnSearchForDevicesOnNetworkCompleted listener) {
        new SearchForDevicesOnNetworkTask(listener).execute(null, null, null);
    }

    private class SearchForDevicesOnNetworkTask extends AsyncTask<Void, Void, Void> {
        OnSearchForDevicesOnNetworkCompleted listener;
        List<RokuSearchResult> results;

        private SearchForDevicesOnNetworkTask(OnSearchForDevicesOnNetworkCompleted listener) {
            this.listener = listener;
            this.results = new ArrayList<RokuSearchResult>();
        }

        @Override
        protected Void doInBackground(Void... args) {

            List<DatagramPacket> resps = null;

            // Acquire multicast lock
            WifiManager wifi = (WifiManager) RokuWifiDiscovery.this.context.getSystemService(Context.WIFI_SERVICE);
            WifiManager.MulticastLock multicastLock = wifi.createMulticastLock("multicastLock");
            multicastLock.setReferenceCounted(true);
            multicastLock.acquire();

            try {
                SSDPSearchMsg searchProduct = new SSDPSearchMsg(String.format("%s:%s", SSDP.ST, "roku:ecp"));
                SSDPSocket sock = new SSDPSocket(RokuWifiDiscovery.this.context);
                sock.send(searchProduct.toString());
                resps = sock.receive();
                sock.close();

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (multicastLock != null) {
                multicastLock.release();
                multicastLock = null;
            }

            for (DatagramPacket resp : resps) {
                String str = new String(resp.getData(), 0, resp.getLength());
                this.results.add(new RokuSearchResult(str));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.listener != null) {
                this.listener.onSearchForDevicesOnNetworkComplete(this.results);
            }
        }
    }
}
