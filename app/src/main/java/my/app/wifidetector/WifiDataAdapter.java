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
        textView.setText(sr.SSID + "   (" + Utils.getSignalPercents(sr.level) + "%)");
        textView.setBackgroundColor(Utils.getColorByPercents(sr.level));

        return rowView;
    }
}
