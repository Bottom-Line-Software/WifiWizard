package com.pylonproducts.wifiwizard;

import android.net.wifi.p2p.*;
import android.net.wifi.p2p.WifiP2pManager.*;
import android.util.Log;

import org.apache.cordova.*;

import java.util.Collection;


public class WifiDirectController {
    private static final String TAG = "WifiWizard";
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;

    public WifiDirectController(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        super();
        this.wifiP2pManager = manager;
        this.channel = channel;
    }

    private void removeWifiP2PGroup() {
        wifiP2pManager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Do nothing
            }
            
            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "Disconnect failed. Reason:" + reasonCode);
            }
        });
    }

    public void disconnectAllWifiP2pDevices(CallbackContext callbackContext) {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Do nothing
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.i(TAG, "Peer discovery error: " + reasonCode);
            }
        });

        wifiP2pManager.requestPeers(channel, new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {

                Collection<WifiP2pDevice> peers = peerList.getDeviceList();

                if (peers.size() == 0) {
                    Log.d(TAG, "No devices found");
                    return;
                }

                for (WifiP2pDevice wifiP2pDevice : peers) {
                    if (wifiP2pDevice.status == WifiP2pDevice.CONNECTED) {
                        removeWifiP2PGroup();
                    }
                }
            }
        });
    }
}


