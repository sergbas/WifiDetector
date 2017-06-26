package my.app.wifidetector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private IWifiVisualizer visualizer;

    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    private final Handler handler = new Handler();

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        //visualizer = new TextViewVisualizer((TextView)findViewById(R.id.textView1));
        visualizer = new ListViewVisualizer(this, (ListView)findViewById(R.id.listView));

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        Log.e(TAG, "mainWifi.isWifiEnabled():" + mainWifi.isWifiEnabled());
        if(!mainWifi.isWifiEnabled())
        {
            mainWifi.setWifiEnabled(true);
        }

        doInback();
    }

    public void doInback()
    {
        handler.postDelayed(new Runnable() {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                receiverWifi = new WifiReceiver();
                registerReceiver(receiverWifi, new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mainWifi.startScan();
                doInback();
            }
        }, 1000);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);}

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mainWifi.startScan();

        return super.onMenuItemSelected(featureId, item);}


    @Override
    protected void onPause()
    {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {
            ScanResult[] rssi = SortedWifiPoints();
            visualizer.show(rssi);
        }
    }

    private ScanResult[] SortedWifiPoints() {
        StringBuilder sb = new StringBuilder();
        List<ScanResult> wifiList;
        wifiList = mainWifi.getScanResults();

        ScanResult rssi[]=new ScanResult[wifiList.size()];
        for(int i = 0; i <wifiList.size(); i++){
            rssi[i]= wifiList.get(i);
        }
        Arrays.sort(rssi, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult r1, ScanResult r2) {
                if(r1.level < r2.level) return 1;
                if(r1.level > r2.level) return -1;
                return 0;
            }
        });

        return rssi;
    }

}
