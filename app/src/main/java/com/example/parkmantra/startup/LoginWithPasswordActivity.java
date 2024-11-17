package com.example.parkmantra.startup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.parkmantra.R;
import com.example.parkmantra.common.Common;
import com.example.parkmantra.databinding.ActivityLoginWithPasswordBinding;
import com.example.parkmantra.datamodal.LoginResponseModal;
import com.example.parkmantra.webservices.NKeys;
import com.example.parkmantra.webservices.NetworkRequest;
import com.example.parkmantra.webservices.ResponseDO;
import com.example.parkmantra.webservices.ResponseListner;
import com.example.parkmantra.webservices.ServiceMethods;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginWithPasswordActivity extends AppCompatActivity implements ResponseListner {
    private boolean passwordVisible = false;
    ActivityLoginWithPasswordBinding loginScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_with_password);
        loginScreenBinding = DataBindingUtil.setContentView(LoginWithPasswordActivity.this, R.layout.activity_login_with_password);
        View view = loginScreenBinding.getRoot();
//        loginScreenBinding.loginWithOtpTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resetVal();
//                Common.intent(LoginWithPasswordActivity.this, LoginWithOtpActivity.class);
//            }
//        });

        loginScreenBinding.passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_crossed,0);
        // Set onTouchListener to toggle password visibility
        loginScreenBinding.passwordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if touch is on the drawableRight icon
                Drawable drawableRight = loginScreenBinding.passwordEditText.getCompoundDrawables()[2];
                if (drawableRight != null && event.getRawX() >= (loginScreenBinding.passwordEditText.getRight() - drawableRight.getBounds().width())) {
                    togglePasswordVisibility();
                    return true; // Consume the touch event
                }
            }
            return false;
        });
        loginScreenBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.hideKeyboard(LoginWithPasswordActivity.this);
                String mobileNumber = loginScreenBinding.mobileEditText.getText().toString().trim();
                String password = loginScreenBinding.passwordEditText.getText().toString().trim();
                if(mobileNumber.equalsIgnoreCase("")||password.equalsIgnoreCase("")){
                    Toast.makeText(LoginWithPasswordActivity.this,getString(R.string.login_pass_error),Toast.LENGTH_SHORT).show();
                    return;
                } else if (!Common.pwdVsalid(password)){
                    Toast.makeText(LoginWithPasswordActivity.this,getString(R.string.login_pass_error),Toast.LENGTH_SHORT).show();
                    return;
                } else if (!(mobileNumber.length()==10)){
                    Toast.makeText(LoginWithPasswordActivity.this, getString(R.string.login_pass_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                loginWithMobileNumber(mobileNumber,password);
            }
        });

        verifyBtn();
    }
    private void verifyBtn() {
        loginScreenBinding.mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()<=9){
                    loginScreenBinding.loginBtn.setEnabled(false);
                    return;
                }
                if(loginScreenBinding.passwordEditText.getText().toString().length()>0){
                    loginScreenBinding.loginBtn.setEnabled(true);
                }else{
                    loginScreenBinding.loginBtn.setEnabled(false);
                }
            }
        });
        loginScreenBinding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0){
                    loginScreenBinding.loginBtn.setEnabled(false);
                    return;
                }
                if(loginScreenBinding.mobileEditText.getText().toString().length()==10){
                    loginScreenBinding.loginBtn.setEnabled(true);
                }else{
                    loginScreenBinding.loginBtn.setEnabled(false);
                }
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Password is currently visible, hide it
            loginScreenBinding.passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            loginScreenBinding.passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_crossed,0);
            passwordVisible = false;
        } else {
            // Password is currently hidden, show it
            loginScreenBinding.passwordEditText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginScreenBinding.passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye,0);
            passwordVisible = true;
        }

        // Move cursor to the end of the text
        loginScreenBinding.passwordEditText.setSelection(loginScreenBinding.passwordEditText.getText().length());
    }

    public void loginWithMobileNumber(String phone, String password){
//        Common.trackEvent(LoginWithPasswordActivity.this,"thisdevice-token",MyFirebaseInstanceIDService.getDeviceRefreshToken());
//
//        LoginRequestModal loginRequestModel=new LoginRequestModal();
//        loginRequestModel.setMobileNumer(email);
//        loginRequestModel.setPassword(password);
//        loginRequestModel.setLogin_type(login_type);
//        loginRequestModel.setDeviceToken(MyFirebaseInstanceIDService.getDeviceRefreshToken());
        HashMap<String,Object> map=new HashMap<>();
        HashMap<String,Object> map1=new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map1.put(NKeys.LOGIN,map);
        new NetworkRequest(this,this).callWebServices(ServiceMethods.WS_LOGIN,map1,true);
    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onResponseReceived(ResponseDO responseDO) {
        Common.showLog("RESPONSE==="+responseDO.getResponse());
        if (!responseDO.isError()) {
            if (responseDO.getServiceMethods() == ServiceMethods.WS_LOGIN) {
                LoginResponseModal loginResponse = new Gson().fromJson(responseDO.getResponse(), LoginResponseModal.class);
                Common.showToast(this, loginResponse.getMessage());
                if (loginResponse.getStatus() == 200) {
//                    utils.saveString(PreferenceUtils.TOKEN, loginResponse.getData().getToken());
//                    Common.showLog("TOKEN===" + loginResponse.getData().getToken());
//                    utils.saveString(PreferenceUtils.REFRESHTOKEN, loginResponse.getData().getRefershToken());
//                    utils.decodeAndSaveToken(loginResponse.getData().getToken());
//                    Common.userRoleId = new PreferenceUtils(this).getStringFromPreference(PreferenceUtils.ROLEID, "").toString();
                    Common.userId = loginResponse.getUserId();
//                    Common.intent(LoginWithPasswordActivity.this, DashboardActivity.class);
                    finish();
                } else {
                    loginScreenBinding.credentialErrorTxt.setText(loginResponse.getMessage());
                    loginScreenBinding.credentialErrorTxt.setVisibility(View.VISIBLE);
                }
            }
        } else {
            Common.showToast(this, responseDO.getResponse());
        }
    }
    private void resetVal(){
        loginScreenBinding.mobileEditText.setText("");
        loginScreenBinding.passwordEditText.setText("");
    }
}