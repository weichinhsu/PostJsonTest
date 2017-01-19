package com.example.winnie.postjsontest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

        import Modules.*;

public class MainActivity extends AppCompatActivity {

    private EditText titleId,contentText;
    private Button InsertBtn,loginBtn;
    private String path="http://163.17.135.185/testContent/api/values/Post";

    private ViewPager mViewPager;

    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setListener();
        setViewPager();
    }

    //region 設定介面
    private void findView(){
//        titleId=(EditText)findViewById(R.id.titleId);
//        contentText=(EditText)findViewById(R.id.contentText);
//        InsertBtn=(Button)findViewById(R.id.insertBtn);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        loginBtn=(Button)findViewById(R.id.loginBtn);
    }
    //endregion

    //region 設定監聽Listener
    private void setListener(){
//        InsertBtn.setOnClickListener(insertBtnListener);
        loginBtn.setOnClickListener(loginBtnListener);

    }
    //endregion

    private View.OnClickListener loginBtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    };

    //region 實作監聽Listener
    private View.OnClickListener insertBtnListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Thread thread = new Thread(mutiThread);
            thread.start();
            postJson();
        }
    };
    //endregion

    //region Json post
    private Runnable mutiThread = new Runnable() {
        public void run() {
            // 運行網路連線的程式
            postJson();
        }
    };
    private void postJson(){

        Map<String,String> map=new HashMap<String, String>() ;
        map.put("Title",titleId.getText().toString());
        map.put("Content1",contentText.getText().toString());

        new HttpURLConnection_POST(path,map).sendHttpURLConnectionPOST();

    }
    //endregion

    //region設定ViewPager滑頁
    private void setViewPager(){
        //FragmentStatePagerAdapter設定頁面
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        mFragmentCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mFragmentCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mFragmentCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mFragmentCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));

        //設定陰影
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mFragmentCardShadowTransformer.enableScaling();

        mViewPager.setAdapter(mFragmentCardAdapter);//設定控制器
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);//設定切換動畫
        mViewPager.setOffscreenPageLimit(3);//在螢幕外暫存頁面數量
    }
    //dp轉Pixel
    public static float dpToPixels(int dp, Context context) {
        //取得手機密度Pixels
        return dp * (context.getResources().getDisplayMetrics().density);
    }
    //endregion

}























