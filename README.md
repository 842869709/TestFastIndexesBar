# FastIndexesBar
自定义快速索引拼音工具栏


## 示例图片
图片如果不展示请出国即可
图片做了帧率压缩，所以有卡顿，实际效果顺滑

![](https://github.com/842869709/FastIndexesBar/blob/master/test0.png)
![](https://github.com/842869709/FastIndexesBar/blob/master/test1.png)


## 1.用法
使用前，对于Android Studio的用户，可以选择添加:

方法一：Gradle： 在dependencies中添加引用：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.842869709:FastIndexesBar:Tag'
	}
```
方法二：Maven仓库
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```
<dependency>
	    <groupId>com.github.842869709</groupId>
	    <artifactId>FastIndexesBar</artifactId>
	    <version>Tag</version>
	</dependency>
```

## 2.功能参数与含义
配置参数|参数含义|参数类型|默认值
-|-|-|-
BACKGROUND_COLOR|	背景色|	string|	#ffcc0000
TEXT_SIZE|	字体大小|	sp| 	20sp
TEXT_COLOR|	字体颜色|	string| 	#ffffff
SELECT_TEXT_COLOR|	选中文字的字体颜色|	string| 	#aaa
IS_SPECIAL_IN_END|	标记#号显示在末尾还是首位|	boolean| 	true



## 3.代码参考
布局文件
直接放在任意布局内

```
<com.yxd.myfastindexesbar.FastIndexesBar
        android:id="@+id/fib"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        app:BACKGROUND_COLOR="@android:color/holo_blue_dark"
        app:TEXT_COLOR="@android:color/white"
        app:SELECT_TEXT_COLOR="@android:color/black"
        app:TEXT_SIZE="20sp"
        app:IS_SPECIAL_IN_END="false"
        />
```
示例
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <com.yxd.myfastindexesbar.FastIndexesBar
        android:id="@+id/fib"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        app:BACKGROUND_COLOR="@android:color/holo_blue_dark"
        app:TEXT_COLOR="@android:color/white"
        app:SELECT_TEXT_COLOR="@android:color/black"
        app:TEXT_SIZE="20sp"
        app:IS_SPECIAL_IN_END="false"
        />


    <TextView
        android:id="@+id/tv_words"
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:gravity="center"
        android:includeFontPadding="false"
        android:layout_centerInParent="true"
        android:background="#50B6B6B6"
        android:textColor="#fff"
        android:text="A"
        android:textSize="100sp"
        android:visibility="gone"
        />


</RelativeLayout>
```

配置及初始化
```
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

```
## v1.0.0 初始化提交
