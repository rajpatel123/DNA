package com.dnamedical.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {


    private static long millis;

    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static boolean hideKeyBoard(Activity activity) {
        try {

            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            return inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static ProgressDialog pDialog;

    public static void showProgressDialog(Context context) {
        if (pDialog != null) {
            pDialog.dismiss();
        }
        try {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissProgressDialog() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

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

    public static void displayToast(Context applicationContext, String s) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show();

    }

//    public static String tripDateFormat(String testDate) {
//        String myDate = "2014/10/29 18:10:45";
//        LocalDateTime localDateTime = LocalDateTime.parse(myDate,
//                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
///*
//  With this new Date/Time API, when using a date, you need to
//  specify the Zone where the date/time will be used. For your case,
//  seems that you want/need to use the default zone of your system.
//  Check which zone you need to use for specific behaviour e.g.
//  CET or America/Lima
//*/
//        long millis = localDateTime
//                .atZone(ZoneId.systemDefault())
//                .toInstant().toEpochMilli();
//
//
//        long testTime = getTime(testDate);
//        Log.d("Time:",""+testTime);
//        return dateFormat(testTime);
//    }


    private static long getTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date myDate = sdf.parse(date);
            return myDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTimeInHHMMSS(long millies) {

        SimpleDateFormat mdformat = new SimpleDateFormat("hh  : mm  :  ss");
        String strTime = mdformat.format(millies * 1000);
        return strTime;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
        return strDate;

    }

    public static String getDviceID(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static String dateFormat(long timeStamp) {


        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd MMM YYYY");
            Date dNow = new Date(timeStamp * 1000);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String testReviewTime(long timeStamp) {


        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd MMM YYYY hh:mm a");
            Date dNow = new Date(timeStamp * 1000);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static long getTimeinMillisecondsFromDate() {
        String myDate = "2020/11/31 12:07:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(myDate);
            millis = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("time",""+millis);
        return millis;
    }

    public static String dateFormatForPlan(long timeStamp) {

        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd MMM yyyy");
            Date dNow = new Date(timeStamp*1000);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String dateFormatForPlanCoupon(String time) {
        long timeStamp = getMilliesFromDate(time);

        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd MMM YYYY");
            Date dNow = new Date(timeStamp);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String startTimeFormat(long timeStamp) {


        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd MMM yyyy   hh:mm a");
            Date dNow = new Date(timeStamp);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String startTestTimeFormat(long timeStamp) {

        if (timeStamp <= 0) {
            return null;
        }
        Date date = new Date(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return formattedDate;// System.out.println(formattedDate);
    }


    public static String startTimeForTestFormat(long timeStamp) {


        if (timeStamp <= 0) {
            return null;
        }

        try {
            Log.d("date", "" + timeStamp);
            SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd : MM : YYYY");
            Date dNow = new Date(timeStamp * 1000);
            return tripDateFormat.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static long getMillies(String testDate) {

        Calendar calendar = Calendar.getInstance();
        String dates[] = testDate.split("/");
        int dt = Integer.parseInt(dates[0]);
        int mm = Integer.parseInt(dates[1]);
        int yy = Integer.parseInt(dates[2]);

        calendar.set(yy, mm - 1, dt);

        try {
            Date date = calendar.getTime();
            return date.getTime();
        } catch (Exception e) {
            // Log.e("Tag", "Wrong date Format");
        }
        return -1;
    }


    public static long getMilliesFromDate(String testDate) {

        Calendar calendar = Calendar.getInstance();
        String dates[] = testDate.split("-");
        int yy = Integer.parseInt(dates[0]);
        int mm = Integer.parseInt(dates[1]);
        int dt = Integer.parseInt(dates[2]);

        calendar.set(yy, mm - 1, dt);

        try {
            Date date = calendar.getTime();
            return date.getTime();
        } catch (Exception e) {
            // Log.e("Tag", "Wrong date Format");
        }
        return -1;
    }

    public static long getMilliesFromTime(String testDate) {

        Calendar calendar = Calendar.getInstance();
        String dates[] = testDate.split(":");
        int hh = Integer.parseInt(dates[0]);
        int mm = Integer.parseInt(dates[1]);
        int ss = Integer.parseInt(dates[2]);

        calendar.set(hh, mm, ss);

        try {
            Date date = calendar.getTime();
            return date.getTime();
        } catch (Exception e) {
            // Log.e("Tag", "Wrong date Format");
        }
        return -1;
    }


    public static long getMilliesOfTestTime(String time) {

        String source = "00:10:17";
        String[] tokens = source.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        return secondsToMs + minutesToMs + hoursToMs;
    }

    public static String getTestDurationDuration(int seconds) {
        int p1 = seconds % 60;
        int p2 = seconds / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;
        System.out.print(p2 + ":" + p3 + ":" + p1);
        System.out.print("\n");

        return p2 + "h " + p3 + "m";

    }

    public static String getTimeTakenInTestFormat(int seconds) {
        StringBuilder time = new StringBuilder();
        int p1 = seconds % 60;
        int p2 = seconds / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;
        System.out.print(p2 + ":" + p3 + ":" + p1);
        System.out.print("\n");


        if (String.valueOf(p2).length() == 1) {
            time.append("0" + p2);
        } else {
            time.append("" + p2);
        }
        time.append("  :  ");

        if (String.valueOf(p3).length() == 1) {
            time.append("0" + p3);
        } else {
            time.append("" + p3);
        }
        time.append("  :  ");

        if (String.valueOf(p1).length() == 1) {
            time.append("0" + p1);
        } else {
            time.append("" + p1);
        }
        return time.toString();

    }


    private static void getDateInddmmYYYY(long time) {
        // TODO Auto-generated method stub
        // Calendar calendar = new
        // GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");

        // Here you say to java the initial timezone. This is the secret
        sdf.setTimeZone(TimeZone.getDefault());//getTimeZone("UTC"));
        // Will print in UTC
        System.out.println("HH:MM:SS  " + sdf.format(calendar.getTime()));

    }

    private static void getDateIndMonthYYYY(long time) {
        // TODO Auto-generated method stub
        // Calendar calendar = new
        // GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM,yyyy");

        // Here you say to java the initial timezone. This is the secret
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Will print in UTC
        System.out.println("HH:MM:SS  " + sdf.format(calendar.getTime()));

    }

    private static void getDateIndateMonthYYYY(long time) {
        // TODO Auto-generated method stub
        // Calendar calendar = new
        // GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,yyyy");

        // Here you say to java the initial timezone. This is the secret
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Will print in UTC
        System.out.println("HH:MM:SS  " + sdf.format(calendar.getTime()));

    }

    public static void setTintForImage(Context context, ImageView imageView, int color){
        imageView.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}
