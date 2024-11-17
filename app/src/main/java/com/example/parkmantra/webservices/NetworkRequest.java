package com.example.parkmantra.webservices;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.parkmantra.R;
import com.example.parkmantra.common.Common;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;

import org.json.JSONObject;

import java.util.HashMap;

public class NetworkRequest {
    private static LoadBuilder<Builders.Any.B> loadBuilder;
    Context context;
    ResponseListner responseListner;
    private DProgressbar loaderUtils;
//    private PreferenceUtils utils;
    Dialog dialog;

    public NetworkRequest(Context context, ResponseListner responseListner) {
        this.context = context;
        this.responseListner = responseListner;
//        utils = new PreferenceUtils(context);
        dialog = new Dialog(context);
        //init loader
        initLoader(context);
    }

    private void initLoader(Context context) {
        loaderUtils = new DProgressbar(context);
    }


    //For all webservices
    public void callWebServices(final ServiceMethods serviceMethods, HashMap<String, Object> map) {
        callWebServices(serviceMethods, map, true);
    }
    public void callWebServices(final ServiceMethods serviceMethods, HashMap<String, Object> map, boolean showLoader) {
        final ResponseDO responseDO = new ResponseDO();
        responseDO.setServiceMethods(serviceMethods);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!NetworkUtils.isNetworkConnectionAvailable(context)) {
                Toast.makeText(context, "INTERNET ERROR", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Common.showLog("SERVICEMETHOD NAME===" + serviceMethods);
        if (showLoader)
            loaderUtils.show();

        loadBuilder = Ion.with(context);

//        String auth = "Bearer " + utils.getStringFromPreference(PreferenceUtils.TOKEN, "");
//        Common.showLog("AUTH===" + auth);

        Builders.Any.B b = null;

        //GetObservable based on methods
        switch (serviceMethods) {
            case WS_LOGIN:
                b = loadBuilder.load("POST", new ServiceUrls().getMETHODNAME(serviceMethods));
                b.addHeader("Accept", "application/json");
                b.addHeader("Content-Type", "application/json");
                b.setStringBody((String) map.get(NKeys.LOGIN));
                break;
        }

        Future<Response<String>> future = b.asString().withResponse();
        future.setCallback(new FutureCallback<Response<String>>() {

            @Override
            public void onCompleted(Exception e, Response<String> result) {
                try {
                    if (loaderUtils != null)
                        loaderUtils.dismiss();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (result == null) return;
                try {
                    responseDO.setCode(result.getHeaders().code());
                    Common.showLog("STATUS CODE : " + result.getHeaders().code());
                    JSONObject jsonObject = new JSONObject(result.getResult().toString());
                    Common.showLog("STATUS CODE : ================" + responseDO.getServiceMethods() + result.getResult());
                    if (Integer.parseInt(jsonObject.optString("status")) >= 200 && Integer.parseInt(jsonObject.optString("status")) <= 299) {
                        responseDO.setError(false);
                    } else {

                        responseDO.setError(true);
                        responseDO.setResponse(jsonObject.optString("message"));
                        responseListner.onResponseReceived(responseDO);
                        return;
                    }
                    responseDO.setResponse(result.getResult().toString());
                    responseListner.onResponseReceived(responseDO);
                } catch (Exception e2) {
                    Log.e("Error", e2.getMessage().toString());
                    ResponseDO responseDO = new ResponseDO();
                    responseDO.setError(true);
                    responseDO.setResponse("Please try again");
                    responseListner.onResponseReceived(responseDO);
                }

            }
        });

    }

    public static class DProgressbar {
        Dialog progressDialog;

        public DProgressbar(Context context) {
            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.dialog_api_loading);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setCancelable(false);
        }

        public void show() {
            progressDialog.show();
        }

        public void dismiss() {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
