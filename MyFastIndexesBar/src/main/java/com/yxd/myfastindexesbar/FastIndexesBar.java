package com.yxd.myfastindexesbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 创建时间：2020/7/22
 * 编写人：czy_yangxudong
 * 功能描述：快速索引工具栏
 */
public class FastIndexesBar extends View {

    private String words="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //默认文字画笔
    private Paint defaultPaint;
    //选中文字的画笔
    private Paint selectPaint;
    private Paint myPaint;
    //平均高度  每个字母平分的高度
    private float averageHeight;

    private Rect rect = new Rect();
    //手指划过的字母的角标
    private int moveIndex=-1;

    //字体大小
    private float TEXT_SIZE=20;
    //标记#号是在首位还是在末尾 默认在末尾
    private boolean IS_SPECIAL_IN_END=true;
    //背景色
    private int BACKGROUND_COLOR;
    //文字颜色
    private int TEXT_COLOR;
    //选中文字颜色
    private int SELECT_TEXT_COLOR;

    public FastIndexesBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context,@Nullable AttributeSet attrs) {

        //创建对象
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FastIndexesBar);
        //获取用户设置的属性
        TEXT_SIZE=ta.getDimension(R.styleable.FastIndexesBar_TEXT_SIZE,10);
        IS_SPECIAL_IN_END=ta.getBoolean(R.styleable.FastIndexesBar_IS_SPECIAL_IN_END,true);

        if (IS_SPECIAL_IN_END){
            words=words+"#";
        }else{
            words="#"+words;
        }

        if (ta.getString(R.styleable.FastIndexesBar_BACKGROUND_COLOR)!=null){
            BACKGROUND_COLOR=ta.getColor(R.styleable.FastIndexesBar_BACKGROUND_COLOR,context.getResources().getColor(android.R.color.holo_red_dark));
        }
        if (ta.getString(R.styleable.FastIndexesBar_TEXT_COLOR)!=null){
            TEXT_COLOR=ta.getColor(R.styleable.FastIndexesBar_TEXT_COLOR,context.getResources().getColor(android.R.color.white));
        }
        if (ta.getString(R.styleable.FastIndexesBar_SELECT_TEXT_COLOR)!=null){
            SELECT_TEXT_COLOR=ta.getColor(R.styleable.FastIndexesBar_SELECT_TEXT_COLOR,context.getResources().getColor(android.R.color.darker_gray));
        }

        defaultPaint=getPaint(TEXT_COLOR);
        selectPaint=getPaint(SELECT_TEXT_COLOR);
    }

    private Paint getPaint(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);//抗锯齿

        //字体大小 sp转px  适配屏幕
        /*float textSizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE, getResources()
                .getDisplayMetrics());*/

        paint.setTextSize(TEXT_SIZE);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(BACKGROUND_COLOR);

        averageHeight=1.0f*getHeight()/words.length();
        Log.i("test","getMeasuredHeight()="+getMeasuredHeight());
        Log.i("test","getHeight()="+getHeight());
        Log.i("test","averageHeight="+averageHeight);

        for (int i = 0; i < 27; i++) {

            if (i==moveIndex){
                myPaint=selectPaint;
            }else{
                myPaint=defaultPaint;
            }

            // 计算文字所在矩形，可以得到宽高
            myPaint.getTextBounds(words.charAt(i)+"", 0, (words.charAt(i)+"").length(), rect);
            //文字的高度
            int textHeight = rect.height();
            int textWidth = rect.width();

            //Log.i("test","第"+i+"个点="+(averageHeight/2+textHeight/2+i*averageHeight));
            //Log.i("test","第"+i+"个点="+(i*1.00f*averageHeight));
            canvas.drawText(words.charAt(i)+"",getWidth()/2-textWidth/2,averageHeight/2+textHeight/2+i*averageHeight,myPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                refreshBar((int) (event.getY()/averageHeight));
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("test","event.getY()="+event.getY());
                Log.i("test","平均值="+(int) (event.getY()/averageHeight));
                refreshBar((int) (event.getY()/averageHeight));
                break;
            case MotionEvent.ACTION_UP:
                refreshBar(-1);
                break;
        }
        return true;
    }

    //重绘  并 避免无用的重绘
    private void refreshBar(int index){
        if (index==moveIndex){
            return;
        }else{
            moveIndex=index;
            invalidate();
            if (mOnCheckWordListening!=null&&moveIndex!=-1){
                if (moveIndex>=27||moveIndex<0){
                    return;
                }
                mOnCheckWordListening.OnCheckWord(words.charAt(moveIndex)+"");
            }
        }
    }


    private OnCheckWordListening mOnCheckWordListening;
    public  interface  OnCheckWordListening{
        void OnCheckWord(String c);
    }
    public void setmOnCheckWordListening(OnCheckWordListening mOnCheckWordListening) {
        this.mOnCheckWordListening = mOnCheckWordListening;
    }

}
