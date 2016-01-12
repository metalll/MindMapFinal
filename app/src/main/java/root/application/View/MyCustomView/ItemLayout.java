package root.application.View.MyCustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by root on 06.01.16.
 */
public class ItemLayout extends FrameLayout{
    int Size;
    Context mContext;
    private Paint       paint=new Paint(Paint.ANTI_ALIAS_FLAG);


    public ItemLayout(Context context) {
        super(context);
        this.mContext=context;
        setWillNotDraw(false);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        setWillNotDraw(false);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        setWillNotDraw(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext=context;
        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int mWidth = measure(widthMeasureSpec);
        int mHeight = measure(heightMeasureSpec);
        Size = Math.min(mWidth, mHeight);
        setMeasuredDimension(Size, Size);
        Log.e("VIEW","Size:" +Size);


    //////////////////////////////////////////////////////////И Как только это работает?
    /*//////////*/float koef = Size / 499.100001f;///////////Это магия не трогать !!!!!!!
    /////////////////////////////////////////////////////////


       if(getChildCount()>0) {
           getChildAt(0).measure(Size / 5, Size / 5);
           getChildAt(0).setX(Size / 2f-(Size / 5)/koef);
           getChildAt(0).setY(Size / 2f-(Size / 5)/koef);
           this.invalidate();
       }
        float angle = 360 * ((float)(Math.PI*2)/360); //И это магия !!!!
        float k = angle/(getChildCount()-1);
        float currAngle = 0;

        for(int i=1;i<getChildCount();i++) {
            getChildAt(i).measure(Size/6,Size/6);
            getChildAt(i).setX(Size / 2f - (Size / 5)/koef + (float)Math.cos(currAngle) * Size / 3f);
            getChildAt(i).setY(Size / 2f - (Size / 5)/koef + (float)Math.sin(currAngle) * Size / 3f);
            getChildAt(i).setClickable(true);
            getChildAt(i).setFocusable(true);

            this.invalidate();
            currAngle+=k;

        }




    }


    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {

        if(w<h) {
            super.onSizeChanged(w, w, oldw, oldh);
            Size = w;
        }
        else{
            super.onSizeChanged(h, h, oldw, oldh);
            Size = h;
        }

    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMoge = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMoge == MeasureSpec.UNSPECIFIED) result = 200;
        else result = specSize;
        return result;
    }



    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(Size / 2, Size / 2, Size / 3f, paint);
        Log.e("VIEW","Draw");

    }


}
