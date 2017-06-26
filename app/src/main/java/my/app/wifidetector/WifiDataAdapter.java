package my.app.wifidetector;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by user on 6/26/2017.
 */

class WifiDataAdapter extends ArrayAdapter<ScanResult>{

    private final ScanResult[] values;
    private final Context context;

    public WifiDataAdapter(Context ctx, int row, ScanResult[] rssi) {
        super(ctx, row, rssi);
        context = ctx;
        values = rssi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        ScanResult sr = values[position];
        TextView textView = (TextView) rowView.findViewById(R.id.rowtext);

        StringBuilder sb = new StringBuilder();
        int percents = Utils.getSignalPercents(sr.level);
        sb.append("SSID:" + sr.SSID + "\n");
        sb.append("frequency:" + sr.frequency + "\n");
        sb.append("level:" + sr.level + " (" + percents + "%)\n");
        sb.append(Utils.drawLevel(percents) + "\n");

        textView.setText(sb.toString());
        textView.setBackgroundColor(Utils.getColorByPercents(sr.level));

        return rowView;
    }
}
