package my.app.wifidetector;

import android.net.wifi.ScanResult;

/**
 * Created by user on 6/26/2017.
 */

interface IWifiVisualizer {
    void show(ScanResult[] rssi);
}
