package com.app.darren;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.darren.loglibs.ToolLog;
import com.darren.loglibs.utils.FileLogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_MSG = "KLog is a so cool Log Tool!";
    private static final String TAG = "KLog";
    private static final String URL_XML = "https://raw.githubusercontent.com/ZhaoKaiQiang/KLog/master/app/src/main/AndroidManifest.xml";
    private static String XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><!--  Copyright w3school.com.cn --><note><to>George</to><from>John</from><heading>Reminder</heading><body>Don't forget the meeting!</body></note>";
    private static String JSON;
    private static String JSON_LONG;
    private static String STRING_LONG;
    private AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        httpClient = new AsyncHttpClient();
        JSON_LONG = getResources().getString(R.string.json_long);
        JSON = getResources().getString(R.string.json);
        STRING_LONG = getString(R.string.string_long);
    }

    public void log(View view) {
        ToolLog.v();
        ToolLog.d();
        ToolLog.i();
        ToolLog.w();
        ToolLog.e();
        ToolLog.a();
    }

    public void logWithMsg(View view) {
        ToolLog.v(LOG_MSG);
        ToolLog.d(LOG_MSG);
        ToolLog.i(LOG_MSG);
        ToolLog.w(LOG_MSG);
        ToolLog.e(LOG_MSG);
        ToolLog.a(LOG_MSG);
    }

    public void logWithTag(View view) {
        ToolLog.v(TAG, LOG_MSG);
        ToolLog.d(TAG, LOG_MSG);
        ToolLog.i(TAG, LOG_MSG);
        ToolLog.w(TAG, LOG_MSG);
        ToolLog.e(TAG, LOG_MSG);
        ToolLog.a(TAG, LOG_MSG);
    }

    public void logWithLong(View view) {
        ToolLog.d(TAG, STRING_LONG);
    }

    public void logWithParams(View view) {
        ToolLog.v(TAG, LOG_MSG, "params1", "params2", this);
        ToolLog.d(TAG, LOG_MSG, "params1", "params2", this);
        ToolLog.i(TAG, LOG_MSG, "params1", "params2", this);
        ToolLog.w(TAG, LOG_MSG, "params1", "params2", this);
        ToolLog.e(TAG, LOG_MSG, "params1", "params2", this);
        ToolLog.a(TAG, LOG_MSG, "params1", "params2", this);
    }

    public void logWithNull(View view) {
        ToolLog.v(null);
        ToolLog.d(null);
        ToolLog.i(null);
        ToolLog.w(null);
        ToolLog.e(null);
        ToolLog.a(null);
    }

    public void logWithJson(View view) {
        ToolLog.json("12345");
        ToolLog.json(null);
        ToolLog.json(JSON);
    }

    public void logWithLongJson(View view) {
        ToolLog.json(JSON_LONG);
    }

    public void logWithJsonTag(View view) {
        ToolLog.json(TAG, JSON);
    }

    public void logWithFile(View view) {
//        ToolLog.file(Environment.getExternalStoragePublicDirectory("LogApp"), JSON_LONG);
//        ToolLog.file(TAG, Environment.getExternalStoragePublicDirectory("LogApp"), JSON_LONG);
//        ToolLog.file(TAG, Environment.getExternalStoragePublicDirectory("LogApp"), "test.txt", JSON_LONG);
        ToolLog.file(TAG, FileLogUtils.getExternalStoragePathDir(), JSON_LONG);
        ToolLog.file(TAG, FileLogUtils.getExternalStoragePathDir(), JSON_LONG);
        ToolLog.file(TAG, FileLogUtils.getExternalStoragePathDir(), "test.txt", JSON_LONG);
    }

    public void logWithXml(View view) {
        ToolLog.xml("12345");
        ToolLog.xml(null);
        ToolLog.xml(XML);
    }

    public void logWithXmlFromNet(View view) {
        httpClient.get(this, URL_XML, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToolLog.e(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ToolLog.xml(responseString);
            }
        });
    }

    public void onExceprionMethd(View view) {
        throw new RuntimeException("test throw Runtime Exception!");
    }

    ///////////////////////////////////////////////////////////////////////////
    // MENU
    ///////////////////////////////////////////////////////////////////////////
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_about, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_github:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZhaoKaiQiang/KLog")));
//                break;
//            case R.id.action_csdn:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/zhaokaiqiang1992")));
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
