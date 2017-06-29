package com.app.darren.logapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.app.darren.logapp.utils.EmailUtils;
import com.darren.loglibs.utils.ExecutorCompat;
import com.github.clans.fab.FloatingActionButton;

import java.util.Date;

/**
 * Version 2.1.1
 * <p>
 * Date: 16/12/10 13:51
 * Author: darren1009@qq.com
 */

public class DebugFeedbackActivity extends AppCompatActivity {
    private static final String DEBUG_FEEDBACK_TIME_STAMP = "last_time";
    private SharedPreferences sp;
    private FloatingActionButton fabSend;
    private TextInputEditText textTitle, textContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_debug_feedback);
        setTitle("本地日志上报");
        fabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        textTitle = (TextInputEditText) findViewById(R.id.text_title);
        textContent = (TextInputEditText) findViewById(R.id.text_content);

        sp = getSharedPreferences("save.info", MODE_PRIVATE);
        Drawable icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_send).mutate();
        icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        fabSend.setImageDrawable(icon);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long currentTime = new Date().getTime();
                long preTime = sp.getLong(DEBUG_FEEDBACK_TIME_STAMP, 0);
                if (currentTime - preTime < 108000) {
                    Toast.makeText(getContext(), "上报间隔不能小于3分钟", Toast.LENGTH_LONG).show();
                }
                final String title = textTitle.getText().toString().trim();
                final String content = textContent.getText().toString().trim();
                fabSend.setIndeterminate(true);
                fabSend.setEnabled(false);
                ExecutorCompat.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final boolean flag = EmailUtils.sendLocalLogToDeveloper(title, content);
                            getContext().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (flag) {
                                        Toast.makeText(getContext(), "发送成功", Toast.LENGTH_LONG).show();
                                        sp.edit().putLong(DEBUG_FEEDBACK_TIME_STAMP, currentTime).commit();

                                    } else {
                                        Toast.makeText(getContext(), "发送失败", Toast.LENGTH_LONG).show();
                                    }
                                    fabSend.setIndeterminate(false);
                                    fabSend.setEnabled(true);
                                }
                            });
                        } catch (final Exception e) {
                            getContext().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "unknown error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    fabSend.setIndeterminate(false);
                                    fabSend.setEnabled(true);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public Activity getContext() {
        return this;
    }
}
