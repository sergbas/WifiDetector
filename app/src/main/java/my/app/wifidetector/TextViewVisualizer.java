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
        StringBuilder sb = new StringBuilder();

        for(ScanResult sr : rssi) {
            int percents = Utils.getSignalPercents(sr.level);

            sb.append("----------------\n");
            sb.append("SSID:" + sr.SSID + "\n");
            sb.append("frequency:" + sr.frequency + "\n");
            sb.append("level:" + sr.level + " (" + percents + "%)\n");
            sb.append(drawLevel(percents) + "\n");
            sb.append("timestamp:" + sr.timestamp + "\n");
            sb.append("capabilities:" + sr.capabilities + "\n");
            //sb.append("centerFreq0:" + wifiList.get(i).centerFreq0 + "\n");
            //sb.append("centerFreq1:" + wifiList.get(i).centerFreq1 + "\n");
            //sb.append("----------------\n");
        }

        textView.setText(sb.toString());
    }

    private String drawLevel(int percents) {
        String res = "";
        for(int i = 0; i<(int)(percents * 0.333); i++)
            res+="█";

        return res;
    }

}
