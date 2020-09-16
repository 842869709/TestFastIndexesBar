package com.yxd.testfastindexesbar;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yxd.myfastindexesbar.FastIndexesBar;


public class MainActivity extends AppCompatActivity {
    private TextView tv_words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_words = findViewById(R.id.tv_words);
        FastIndexesBar fib=findViewById(R.id.fib);


        fib.setmOnCheckWordListening(new FastIndexesBar.OnCheckWordListening() {
            @Override
            public void OnCheckWord(String c) {
                Log.i("test","结果="+c);

                tv_words.setText(c);
                tv_words.setVisibility(View.VISIBLE);
                //防止有任务没执行完 先清除倒计时任务
                handler.removeCallbacks(myRunable);
                handler.postDelayed(myRunable,2000);
            }
        });

    }


    private Handler handler=new Handler();

    private Runnable myRunable=new Runnable() {
        @Override
        public void run() {
            tv_words.setVisibility(View.GONE);
        }
    };
}