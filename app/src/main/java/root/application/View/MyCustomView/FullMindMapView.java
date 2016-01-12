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

import java.util.ArrayList;
import java.util.List;

import root.application.Model.Item;

/**
 * Created by root on 10.01.16.
 */
public class FullMindMapView extends FrameLayout {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int Size;
    Context context;
    float scale = 0.3f;
    private List<Item> items = new ArrayList<>();
    private List<Item> buffer = null;
    /*//////////*/ float koef = Size / 499.100001f;////


    public FullMindMapView(Context context) {
        super(context);

        this.context = context;
        setWillNotDraw(false);
    }

    public FullMindMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.context = context;
    }

    public FullMindMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FullMindMapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mWidth = measure(widthMeasureSpec);
        int mHeight = measure(heightMeasureSpec);
        Size = Math.min(mWidth, mHeight);
        setMeasuredDimension(Size, Size);
        Log.e("VIEW", "Size:" + Size);

        for(int i=0;i<getChildCount();i++)
        {
            getChildAt(i).setScaleX(scale);
            getChildAt(i).setScaleY(scale);
            getChildAt(i).setClickable(false);
        }
        //////////////////////////////////////////////////////////И Как только это работает?
    /*//////////*/
        float koef = Size / 499.100001f;///////////Это магия не трогать !!!!!!!
        /////////////////////////////////////////////////////////

        List<List<Item>> result = new ArrayList<>();

        for(int z=0;z<items.size();z++) {
            List<Item> buff = new ArrayList<>();
            for (int i = items.size()-1; i >= 0; i--) {

                boolean isEnd = true;
                for (int j = 0; j <= i; j++) {
                    if (items.get(i).getItemId().equals(items.get(j).getParentItemId())) {
                        isEnd = false;
                    }
                }
                if (isEnd) {
                    buff.add(items.get(i));
                }

            }
            result.add(buff);
        }

        float angle = 360 * ((float)(Math.PI*2)/360); //И это магия !!!!


        for(int i=0;i<result.size();i++)
        {
            float k = angle/result.get(i).size();
            float currAngle = 0;
            for(int j=0;j<result.get(i).size();j++)
            {


                for(int z=0;z<getChildCount();z++)
                {

                    if(!getChildAt(z).isClickable())
                    {
                        if(((ItemView)getChildAt(z)).getViewId().equals(result.get(i).get(j).getItemId()))
                        {
                            if(z==0)
                            {
                                getChildAt(0).measure(Size / 5, Size / 5);
                                getChildAt(0).setX(Size / 2f-(Size / 5)/(koef*scale));
                                getChildAt(0).setY(Size / 2f-(Size / 5)/(koef*scale));
                                getChildAt(0).setClickable(true);
                                this.invalidate();
                            }
                            else
                            {
                                getChildAt(i).measure(Size / 6, Size / 6);
                                getChildAt(i).setX(Size / 2f - (Size / 5) / (koef*scale) + (float) Math.cos(currAngle) * (Size * (float) Math.sqrt(result.size() - i)) / 3f * scale);
                                getChildAt(i).setY(Size / 2f - (Size / 5) / (koef*scale) + (float) Math.sin(currAngle) * (Size * (float) Math.sqrt(result.size() - i)) / 3f * scale);
                                getChildAt(i).setClickable(true);
                                getChildAt(i).setFocusable(true);
                                this.invalidate();
                                currAngle += k;
                            }

                        }





                    }


                }



            }
        }




    }


























    private int measure(int measureSpec) {
        int result = 0;
        int specMoge = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMoge == MeasureSpec.UNSPECIFIED) result = 100;
        else result = specSize;
        return result;



    }



    @Override
    protected void onDraw(Canvas canvas) {

       super.onDraw(canvas);
       paint.setColor(Color.BLACK);


    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
