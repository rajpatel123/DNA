package edu.com.medicalapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    /**
     * @param context Application Context
     * @return true if connected with active internet else false
     */
    public static boolean isInternetConnected(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
