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

    private void removeWifiP2pGroup(final CallbackContext callbackContext) {
        wifiP2pManager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                callbackContext.success("DISCONNECTED");
            }
            
            @Override
            public void onFailure(int reasonCode) {
                if (reasonCode == WifiP2pManager.BUSY) {
                    callbackContext.success("NO_CONNECTION");
                } else {
                    callbackContext.error("ERROR_DISCONNECT_" + reasonCode);
                }
            }
        });
    }

    public void disconnectAllWifiP2pDevices(final CallbackContext callbackContext) {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                removeWifiP2pGroup(callbackContext);
            }

            @Override
            public void onFailure(int reasonCode) {
                callbackContext.error("ERROR_DISCOVERY_" + reasonCode);
            }
        });
    }
}
