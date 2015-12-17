package edu.iastate.cs.palab.connectivitycollector;

import android.provider.BaseColumns;

/**
 * Connectivity status entry database
 *
 * @author Danilo Dominguez Perez
 *         Program Analysis Lab
 *         Deparment of Computer Science
 *         Iowa State University
 */
public final class ConnectivityStatusContract {

    public ConnectivityStatusContract() {}

    // defines the table contents
    public static abstract class ConnectivityEntry implements BaseColumns {

        public static final String CONNECTIVITY_TABLE_NAME = "connectivity_status";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATA_STATUS = "data_status";
        public static final String COLUMN_NAME_WIFI_STATUS = "wifi_status";
        public static final String COLUMN_NAME_ETHERNET_STATUS = "ethernet_status";
        public static final String COLUMN_NAME_WIMAX_STATUS = "wimax_status";
        public static final String COLUMN_NAME_BLUETOOTH_STATUS = "bluetooth_status";
        public static final String COLUMN_NAME_ROAMING = "roaming";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_SUBTYPE = "subtype";
        public static final String COLUMN_NAME_FAILOVER = "failover";
        public static final String COLUMN_NAME_TIME = "time";

    }
}
