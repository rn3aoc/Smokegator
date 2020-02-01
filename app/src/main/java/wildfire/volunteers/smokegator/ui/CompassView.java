package wildfire.volunteers.smokegator.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import wildfire.volunteers.smokegator.R;

import java.util.Locale;

/**
 * Custom {@link View} drawing a compass.<br>
 *
 * @author Alexandre Louisnard
 */

public class CompassView extends View {

    // Compass
    private float mAzimuthDegrees = 0;

    // Drawing
    private final Paint mPaint = new Paint();
    private Bitmap mCachedBitmap;
    private int mX;
    private int mY;
    private float mRadius;

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Get attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CompassView, 0, 0);
        int color;
        try {
            color = a.getColor(R.styleable.CompassView_color, Color.BLACK);
        } finally {
            a.recycle();
        }

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(26);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw compass structure on a cached bitmap
        if (mCachedBitmap == null) {
            // Prepare cached bitmap & canvas
            mCachedBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888); //Change to lower bitmap config if possible.
            Canvas cachedCanvas = new Canvas(mCachedBitmap);

            // Sizing
            mX = getMeasuredWidth() / 2;
            mY = getMeasuredHeight() / 2;
            mRadius = (float) (Math.max(mX, mY) * 0.8);

            // Draw compass structure on the cached canvas
            mPaint.setStrokeWidth(2);
            cachedCanvas.drawCircle(mX, mY, mRadius, mPaint);
            cachedCanvas.drawCircle(mX, mY, mRadius*0.7f, mPaint);
            cachedCanvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        }

        // Set cached bitmap on the canvas
        canvas.drawBitmap(mCachedBitmap, 0, 0, mPaint);

        // Draw compass line
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        canvas.drawLine(
                (float) (mX + mRadius * 0.7f * Math.sin((double) (mAzimuthDegrees) / 180 * Math.PI)),
                (float) (mY - mRadius * 0.7f * Math.cos((double) (mAzimuthDegrees) / 180 * Math.PI)),
                (float) (mX + mRadius * Math.sin((double) (mAzimuthDegrees) / 180 * Math.PI)),
                (float) (mY - mRadius * Math.cos((double) (mAzimuthDegrees) / 180 * Math.PI)),
                mPaint);

        // Set text
        canvas.drawText(String.format(Locale.getDefault(), "%.0f °", mAzimuthDegrees), mX - 22, mY +10, mPaint);
    }

    /**
     * Updates the azimuth of the {@link CompassView}.
     * @param azimuthDegrees the azimuth in degrees.
     */
    public void updateAzimuth(float azimuthDegrees) {
        mAzimuthDegrees = azimuthDegrees;
        invalidate();
    }
}
