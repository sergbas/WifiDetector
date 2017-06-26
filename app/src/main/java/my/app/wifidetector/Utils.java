package my.app.wifidetector;

import android.graphics.Color;

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

    public int getColorByPercents(int level){
        int col = Color.WHITE;

        return  col;
    }
}
