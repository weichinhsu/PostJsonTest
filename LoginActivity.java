package com.example.winnie.postjsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import Modules.HttpURLConnection_POST;

public class LoginActivity extends AppCompatActivity {

    EditText accountId,passwordId;
    Button loginBtn;
    private String path="http://163.17.135.185/testContent/api/values/Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        setListener();
    }
    private void findView() {
        accountId=(EditText)findViewById(R.id.accountId);
        passwordId=(EditText)findViewById(R.id.passwordId);
        loginBtn=(Button)findViewById(R.id.loginBtn);
    }
    private void setListener(){
        loginBtn.setOnClickListener(loginBtnListener);
    }
    //region 登入
    private View.OnClickListener loginBtnListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Thread thread = new Thread(mutiThread);
            thread.start();
            postJson();
        }
    };
    private Runnable mutiThread = new Runnable() {
        public void run() {
            // 運行網路連線的程式
            postJson();
        }
    };
    private void postJson(){

        Map<String,String> map=new HashMap<String, String>() ;
        map.put("Account",accountId.getText().toString());
        map.put("Password",passwordId.getText().toString());

        new HttpURLConnection_POST(path,map).sendHttpURLConnectionPOST();
    }
    //endregion
}
