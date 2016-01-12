package root.application.View.MyCustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by root on 29.12.15.
 */
public class ItemView extends ImageView {
    private Context     context;
    private int         Size;
    private String      Text="Идея";
    private int         ForegroundColor= Color.WHITE;
    private int         BackgroundColor= Color.RED;
    private Bitmap      bitmap = null;
    private Rect        mTextBoundRect = new Rect();
    private Paint       paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint       textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int         angle=0;
    private String      id;


    public ItemView(Context context,String Text,int Background,int Foreground,Bitmap icon) {
        super(context);
        this.context=context;
        this.Text=Text;
        this.BackgroundColor=Background;
        this.ForegroundColor=Foreground;
        if(icon!=null) {
            this.bitmap = icon;
        }
        else
        {
            this.bitmap = null;
        }
        this.invalidate();

    }

    public ItemView(Context context) {
        super(context);

    }

    public ItemView(Context context,Bitmap bitmap) {
        super(context);
        this.bitmap=bitmap;

    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        final int width = measure(widthMeasureSpec);
        setMeasuredDimension(width, width);

        Size = width;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        Size = w;
        super.onSizeChanged(w, w, oldw, oldh);

    }

    public void SetSize(int size)
    {
        this.setMeasuredDimension(size,size);
    }

    public int measure(int measureSpec) {
        int result = 0;
        int specMoge = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMoge == MeasureSpec.UNSPECIFIED) result = 200;
        else result = specSize;
        return result;
    }



    @Override
    protected void onDraw(Canvas canvas) {



            paint.setColor(BackgroundColor);

            canvas.drawCircle(Size / 2, Size / 2, Size / 2.5F, paint);

            textPaint.setColor(ForegroundColor);

            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setAntiAlias(true);

            textPaint.setTextSize(Size / 8F);

            textPaint.getTextBounds(Text, 0, Text.length(), mTextBoundRect);
            float mTextWidth = textPaint.measureText(Text);
            float mTextHeight = mTextBoundRect.height();

            canvas.drawText(Text,
                    Size / 2 - (mTextWidth / 2f),
                    Size / 2 + (mTextHeight / 2 / 2f),
                    textPaint
            );


        if(bitmap!=null)
        {
            paint = new Paint();



                Bitmap resized = getResizedBitmap(bitmap, Size * 0.7f, Size * 0.7f);
                Bitmap bmp = GetCroppedBitmap(resized);
                bmp.prepareToDraw();
                canvas.drawBitmap(bmp, (canvas.getWidth() - bmp.getWidth()) / 2f, (canvas.getHeight() - bmp.getHeight()) / 2f, new Paint());



            }




    }



    public void setText(String text) {
        Text = text;
        this.invalidate();
    }

    public String getText()
    {
        return Text;
    }


    public int getForegroundColor() {return ForegroundColor;}

    public void setForegroundColor(int foregroundColor)
    {
        ForegroundColor = foregroundColor;

    }

    public int getBackgroundColor(){return BackgroundColor;}

    public void setBackgroundColor(int backgroundColor){

        BackgroundColor = backgroundColor;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {


        this.bitmap = bitmap;
        this.invalidate();

    }

    public Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth =  newWidth / width;
        float scaleHeight = newHeight / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);


        // "RECREATE" THE NEW BITMAP


        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    public Bitmap GetCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f,
                bitmap.getWidth() / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }


    public String getViewId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}