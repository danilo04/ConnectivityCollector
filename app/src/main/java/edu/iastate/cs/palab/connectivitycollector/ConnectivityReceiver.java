package edu.iastate.cs.palab.connectivitycollector;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import edu.iastate.cs.palab.connectivitycollector.ConnectivityStatusContract.ConnectivityEntry;

/**
 * Receives a message on connectivity change and save it in the database
 *
 * @author Danilo Dominguez Perez
 *         Program Analysis Lab
 *         Deparment of Computer Science
 *         Iowa State University
 */
public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = "ConnectivityReceiver";

    public ConnectivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long time = System.currentTimeMillis();
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        ConnectivityDBOpenHelper mDbHelper = new ConnectivityDBOpenHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String currentTimeStamp = getCurrentTimestamp();
        ContentValues values = new ContentValues();
        values.put(ConnectivityEntry.COLUMN_NAME_TIME, currentTimeStamp);
        if (info != null) {
            // no  connectivity, both wifi and data are off
            values.put(ConnectivityEntry.COLUMN_NAME_DATA_STATUS,
                    getType(info, ConnectivityManager.TYPE_MOBILE));
            values.put(ConnectivityEntry.COLUMN_NAME_WIFI_STATUS,
                    getType(info, ConnectivityManager.TYPE_WIFI));
            values.put(ConnectivityEntry.COLUMN_NAME_ETHERNET_STATUS,
                    getType(info, ConnectivityManager.TYPE_ETHERNET));
            values.put(ConnectivityEntry.COLUMN_NAME_WIMAX_STATUS,
                    getType(info, ConnectivityManager.TYPE_WIMAX));
            values.put(ConnectivityEntry.COLUMN_NAME_BLUETOOTH_STATUS,
                    getType(info, ConnectivityManager.TYPE_BLUETOOTH));
            values.put(ConnectivityEntry.COLUMN_NAME_ROAMING, info.isRoaming());
            values.put(ConnectivityEntry.COLUMN_NAME_FAILOVER, info.isFailover() == true ? 1 : 0);
            values.put(ConnectivityEntry.COLUMN_NAME_STATE, info.getDetailedState().toString());
            values.put(ConnectivityEntry.COLUMN_NAME_SUBTYPE, info.getSubtypeName());
        } else {
            // we have connectivity, determine which one
            values.put(ConnectivityEntry.COLUMN_NAME_DATA_STATUS, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_WIFI_STATUS, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_ETHERNET_STATUS, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_WIMAX_STATUS, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_BLUETOOTH_STATUS, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_ROAMING, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_FAILOVER, 0);
            values.put(ConnectivityEntry.COLUMN_NAME_STATE, "DISCONNECTED");
            values.put(ConnectivityEntry.COLUMN_NAME_SUBTYPE, "");
        }

        // TODO: in case it fails, just have a error log
        long rowID = db.insert(
                ConnectivityEntry.CONNECTIVITY_TABLE_NAME,
                null,
                values);
        Log.d(TAG, "New record saved: " + rowID + " network: " + info);
    }

    private int getType(NetworkInfo info, int type) {
        if (info.getType() == type) {
            return 1;
        }

        return 0;
    }

    private String getCurrentTimestamp() {
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
}
