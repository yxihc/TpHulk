package com.taopao.mvvmbase.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * des:实现图像根据按下抬起动作变化颜色  显示圆角
 */
public class RoundColorFilterImageView extends android.support.v7.widget.AppCompatImageView {

    private int roundWidth = 8;
    private int roundHeight = 8;

    public RoundColorFilterImageView(Context context) {
        this(context, null, 0);
    }

    public RoundColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        roundWidth = (int) (roundWidth * density);
        roundHeight = (int) (roundHeight * density);
    }

    /**
     * 重写draw()
     */
    @Override
    public void draw(Canvas canvas) {
        //实例化一个和ImageView一样大小的bitmap
        if (getHeight() < 1) {
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);

        //实例化一个canvas，这个canvas对应的内存为上面的bitmap
        Canvas canvas2 = new Canvas(bitmap);
        if (bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas2 = new Canvas(bitmap);
        }
        //将imageView自己绘制到canvas2上，这个导致bitmap里面存放了imageView
        super.draw(canvas2);

        //利用canvas画一个圆角矩形，这个会修改bitmap的数据
        drawRoundAngle(canvas2);

        //将裁剪好的bitmap绘制到系统当前canvas上，这样裁剪好的imageview就能显示到屏幕上
        Paint paint = new Paint();
        paint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();
    }

    public void setRoundWidth(int roundWidth, int roundHeight) {
        this.roundWidth = roundWidth;
        this.roundHeight = roundHeight;
    }

    private void drawRoundAngle(Canvas canvas) {
        Paint maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        Path maskPath = new Path();
        maskPath.addRoundRect(new RectF(0.0F, 0.0F, getWidth(), getHeight()), roundWidth, roundHeight, Path.Direction.CW);

        //这是设置了填充模式，非常关键
        maskPath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(maskPath, maskPaint);
    }
}
