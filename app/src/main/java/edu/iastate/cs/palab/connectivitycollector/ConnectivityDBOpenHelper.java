package edu.iastate.cs.palab.connectivitycollector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.iastate.cs.palab.connectivitycollector.ConnectivityStatusContract.ConnectivityEntry;

/**
 * Creates a database to store connectivity status changes
 *
 * @author Danilo Dominguez Perez
 *         Program Analysis Lab
 *         Deparment of Computer Science
 *         Iowa State University
 */
public class ConnectivityDBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String CONNECTIVITY_TABLE_CREATE =
            "CREATE TABLE " + ConnectivityEntry.CONNECTIVITY_TABLE_NAME + " (" +
                    ConnectivityEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    ConnectivityEntry.COLUMN_NAME_DATA_STATUS + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_WIFI_STATUS + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_ETHERNET_STATUS + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_WIMAX_STATUS + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_BLUETOOTH_STATUS + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_ROAMING + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_STATE + " TEXT, " +
                    ConnectivityEntry.COLUMN_NAME_SUBTYPE + " TEXT, " +
                    ConnectivityEntry.COLUMN_NAME_FAILOVER + " INTEGER, " +
                    ConnectivityEntry.COLUMN_NAME_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ConnectivityEntry.CONNECTIVITY_TABLE_NAME;


    public ConnectivityDBOpenHelper(Context context) {
        super(context, ConnectivityEntry.CONNECTIVITY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONNECTIVITY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
