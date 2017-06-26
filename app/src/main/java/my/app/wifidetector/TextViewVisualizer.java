package my.app.wifidetector;

import android.net.wifi.ScanResult;
import android.widget.TextView;

/**
 * Created by user on 6/26/2017.
 */

class TextViewVisualizer implements IWifiVisualizer {
    private final TextView textView;

    public TextViewVisualizer(TextView tv) {
        textView = tv;
    }

    @Override
    public void show(ScanResult[] rssi) {
        String res = Utils.prepareString(rssi);
        textView.setText(res);
    }
}
