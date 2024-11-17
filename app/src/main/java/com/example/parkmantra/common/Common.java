package com.example.parkmantra.common;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {
    public static long TIMER_TIME = 60000;
    private static boolean showlog = true;
    private static String TAG = "ParkMantra==";
    public static String dateType = "";
    public static String startDate = "";
    public static String endDate = "";
    public static int userId;
    public static long phoneNumber = 0;
    private static Handler mHandler;

    public static void intent(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(context, intent, null);
    }

    public static void showLog(String msg) {
        if (showlog)
            Log.e(TAG, msg);
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static String convertDateTimeFormat(String inputDate) {
        try {
            // Parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);

            // Format the output date
            SimpleDateFormat outputFormat = new SimpleDateFormat("d' 'MMM yyyy h:mm a", Locale.getDefault());
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your requirements
            return null;
        }
    }

    public interface TimerCallback {
        void onTimerFinish();
    }

//    @SuppressLint("InvalidAnalyticsName")
//    public static void trackEvent(Context mContext, String key, String val) {
//        Bundle bundle = new Bundle();
//        bundle.putString(key, val);
//        FirebaseAnalytics.getInstance(mContext).logEvent("Park_Mantra", bundle);
//    }

    public static boolean pwdVsalid(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!containsUpperCase(password)) {
            return false;
        }
        if (!containsLowerCase(password)) {
            return false;
        }
        if (!containsSpecialCharacter(password)) {
            return false;
        }
        if (!containsNumericValue(password)) {
            return false;
        }
        if (!containsAlphabet(password)) {
            return false;
        }
        if (password.trim().length() != password.length()) {
            return false;
        }
        return true;
    }

    private static boolean containsUpperCase(String password) {
        return !password.equals(password.toLowerCase());
    }

    private static boolean containsLowerCase(String password) {
        return !password.equals(password.toUpperCase());
    }

    private static boolean containsSpecialCharacter(String password) {
        Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = specialCharacterPattern.matcher(password);
        return matcher.find();
    }

    private static boolean containsNumericValue(String password) {
        return password.matches(".*\\d.*");
    }

    private static boolean containsAlphabet(String password) {
        return password.matches(".*[a-zA-Z].*");
    }

//    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int containerId) {
//        fragmentManager
//                .beginTransaction()
//                .replace(containerId, fragment)
//                .commit();
//    }
//
//    public static void addtoBackStackFragment(FragmentManager fragmentManager, Fragment fragment, int containerId,String backstackstring) {
//        fragmentManager
//                .beginTransaction()
//                .addToBackStack(backstackstring)
//                .add(containerId,fragment)
//                .commit();
//    }

//    public static void showlogoutDialog(Context context) {
//        Dialog dialog = new Dialog(context);
//        ChangeTaskStatusDialogBinding changeTaskStatusDialogBinding = DataBindingUtil.inflate(((Activity) context).getLayoutInflater(), R.layout.change_task_status_dialog, null, false);
//        dialog.setContentView(changeTaskStatusDialogBinding.getRoot());
//        changeTaskStatusDialogBinding.headerTv.setText("Log Out");
//        changeTaskStatusDialogBinding.dialogTitleTv.setText("Are you sure you want to logout.");
//        dialog.setCancelable(false);
//        changeTaskStatusDialogBinding.noTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        changeTaskStatusDialogBinding.yesTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("users_device_token", MyFirebaseInstanceIDService.getDeviceRefreshToken());
//                logout(context, dialog, new Gson().toJson(map));
//
//            }
//        });
//        Window window = dialog.getWindow();
//        window.setBackgroundDrawableResource(R.drawable.white_round_10bg);
//        dialog.show();
//
//    }

//    public static void logout(Context context, Dialog dialog, String data) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(NKeys.DATA, data);
//        new NetworkRequest(context, new ResponseListner() {
//            @Override
//            public void onResponseReceived(ResponseDO responseDO) {
//                if (dialog != null)
//                    dialog.dismiss();
//                new PreferenceUtils(context).clearAll();
//                clearFilter();
//                ((Activity) context).finishAffinity();
//                Common.clearFilter();
//                context.startActivity(new Intent(context, LoginWithPasswordActivity.class));
//            }
//        }).callWebServices(ServiceMethods.WS_LOGOUT, map, false);
//    }

    public static String convertYYYYMMMdd(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String date2 = new SimpleDateFormat("yyyy MMM dd").format(date1);
            return date2.toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String taskDateConvert(String date) {
        String outputDateString = null;
        try {
            Date inputDate = new SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.US).parse(date);
            outputDateString = new SimpleDateFormat("d'th' MMM yy hh:mm a", Locale.US).format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    public static String historyDateConvert(String date) {
        String outputDateString = null;
        try {
            Date inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            outputDateString = new SimpleDateFormat("MMM d, yyyy").format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    public static boolean dateValidator(String initialDateString, String finalDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date initialDate = dateFormat.parse(initialDateString);
            Date finalDate = dateFormat.parse(finalDateString);

            // Compare the dates
            return !initialDate.after(finalDate);
        } catch (ParseException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            return false;
        }
    }

    public static void openCalenderForUpcomingDates(Activity activity, EditText text_view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        text_view.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                    }
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static String getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", day);
    }

    public static void openCalender(Activity activity, EditText text_view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        text_view.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    public static void openCalenderForUptoPresentDate(Activity activity, EditText text_view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        text_view.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                    }
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();


    }

    public static void openTimePicker(Activity activity, EditText text_view) {
        final Calendar calendar = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // on below line we are creating a variable for date picker dialog.
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // on below line we are setting selected time
                        // in our text view.
                        NumberFormat f = new DecimalFormat("00");

                        text_view.setText(f.format(hourOfDay) + ":" + f.format(minute));
                    }
                }, hour, minute, false);
        // at last we are calling show to
        // display our time picker dialog.
        timePickerDialog.show();

    }

    public static String getData(String val) {
        if (val == null) return "";
        if (val.isEmpty()) return "";
        return val;
    }

    public static String getProperCase(String val) {
        if (val == null) return "";
        if (val.isEmpty()) return "";
        String s = val.substring(0, 1).toUpperCase();
        val = s + val.substring(1);
        return val;
    }

//    public static void showDialog(Context context) {
//        Dialog dialog = new Dialog(context);
//        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.setContentView(R.layout.dropdown_menu_dialog);
//
//        DropdownMenuAdapter adapter = new DropdownMenuAdapter();
//        RecyclerView recyclerView = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//            recyclerView = dialog.requireViewById(R.id.dropdown_menu_rv);
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(adapter);
//
//        dialog.show();
//    }


    public static void openWhatsApp(Context context, String mobno, String msg) {
        context.startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", "+91" + mobno, msg)
                        )
                )
        );
    }

    public static void openWhatsApp(Context context, String msg) {
        context.startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=&text=%s", msg)
                        )
                )
        );
    }

    public static void openDialerPad(Context context, String phoneno) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneno));
        context.startActivity(intent);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    public static void showStatusDialog(Context context, String message, boolean isLogout) {
//        Common.showToast(context, message);
//        if (isLogout) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("users_device_token", MyFirebaseInstanceIDService.getDeviceRefreshToken());
//            logout(context, null, new Gson().toJson(map));
//        } else {
//            ((Activity) context).finishAffinity();
//            context.startActivity(new Intent(context, DashboardActivity.class));
//        }
////        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        View myView = layoutInflater.inflate(R.layout.socket_status_dialog, null);
////        Dialog dialog=new Dialog(context);
////        AlertDialog.Builder builder = new AlertDialog.Builder(context);
////
////        builder.setView(myView);
////        AlertDialog dialog = builder.create();
////        dialog.setContentView(R.layout.socket_status_dialog);
////        dialog.setCancelable(false);
////        Window window = dialog.getWindow();
////        window.setBackgroundDrawableResource(R.drawable.white_round_10bg);
////        dialog.show();
////        Common.showToast(context,message);
////        ((TextView) dialog.findViewById(R.id.dialog_title_tv)).setText(message);
////
////        ((TextView) dialog.findViewById(R.id.ok_tv)).setText(isLogout ? "Logout" : "Ok");
////        ((TextView) dialog.findViewById(R.id.ok_tv)).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////            }
////        });
//
//    }

//    public static void receivedSocketMessage(Context mActivity) {
//        if (SocketManager.socket == null) return;
//        Common.showLog("SOCKET isConnected===" + SocketManager.isConnected());
//        mHandler = new Handler(Looper.getMainLooper());
//        SocketManager.socket.on("Permission_Check", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                String response = args[0].toString();
//                Common.showLog("===App===" + response);
//                Intent intent = new Intent();
//                intent.setAction("com.devstringx.mytylesstockcheck.SOCKET_NOTIFICATION");
//                intent.putExtra("data", response);
//                mActivity.sendBroadcast(intent);
//
//            }
//        });
//    }
}
