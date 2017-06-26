package my.app.wifidetector;

import android.graphics.Color;
import android.net.wifi.ScanResult;

/**
 * Created by user on 6/26/2017.
 */

class Utils {
    public static int getSignalPercents(int db)
    {
        int percents = 0;
        if(db >= -35) db = -35;//100%
        if(db <= -95 ) db = -95;//0%
        percents = (int)(100 + 100 * (db + 35)/60.0);

        return percents;
    }

    public static int getColorByPercents(int level){
        int col = Color.WHITE;

        int percents = getSignalPercents(level);

        int r = 0;
        int g = 0;

        if(percents <= 50)
        {
            r = 255;
            g = (int)(255*percents/50.0);
        }
        else{
            r = (int)(255*(1-(percents-50)/50.0));
            g = 255;
        }

        col = Color.argb(255, r, g, 0);

        return  col;
    }

    public static String drawLevel(int percents) {
        String res = "";
        for(int i = 0; i<(int)(percents * 0.333); i++)
            res+="█";

        return res;
    }

    public static String prepareString(ScanResult[] rssi) {
        StringBuilder sb = new StringBuilder();

        for(ScanResult sr : rssi) {
            int percents = Utils.getSignalPercents(sr.level);
            sb.append("SSID:" + sr.SSID + "\n");
            sb.append("frequency:" + sr.frequency + "\n");
            sb.append("level:" + sr.level + " (" + percents + "%)\n");
            sb.append(drawLevel(percents) + "\n");
            //sb.append("timestamp:" + sr.timestamp + "\n");
            //sb.append("capabilities:" + sr.capabilities + "\n");
            //sb.append("centerFreq0:" + wifiList.get(i).centerFreq0 + "\n");
            //sb.append("centerFreq1:" + wifiList.get(i).centerFreq1 + "\n");
            //sb.append("----------------\n");
        }
        return sb.toString();
    }
}
