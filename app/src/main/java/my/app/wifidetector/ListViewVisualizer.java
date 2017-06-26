package my.app.wifidetector;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by user on 6/26/2017.
 */

class ListViewVisualizer implements IWifiVisualizer {
    private final ListView listView;
    private final Context context;

    public ListViewVisualizer(Context ctx, ListView lv) {
        context = ctx;
        listView = lv;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void show(ScanResult[] rssi) {
        final WifiDataAdapter adapter = new WifiDataAdapter(context, R.layout.row, rssi);
        listView.setAdapter(adapter);
    }
}
