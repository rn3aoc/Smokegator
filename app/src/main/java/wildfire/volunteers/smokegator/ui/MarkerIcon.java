package wildfire.volunteers.smokegator.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.preference.PreferenceManager;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class MarkerIcon {
    private SharedPreferences sharedPreferences;
    private Context context;

    private float mBearing = 0;
    private String mCallsign;
    private Color mColor;
    private String mTimestamp;

    private Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    private Bitmap mBitmap = Bitmap.createBitmap(240,80, conf);
    private Paint mPaint;

    public MarkerIcon(float bearing, Date timestamp, String callsign){

        mBearing = bearing;
        DateFormat dateFormat = new SimpleDateFormat("m-dd hh:mm:ss", Locale.US);
        mTimestamp = dateFormat.format(timestamp);
        //mCallsign = sharedPreferences.getString("callsign", "default_callsign");
        mCallsign = callsign;

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(26);

        mCanvas.drawCircle(13,40,12, mPaint);
        mCanvas.drawLine(25,40,220,40, mPaint);

        mPaint.setColor(Color.DKGRAY);
        mCanvas.drawText(mCallsign, 40, 30, mPaint);
        mCanvas.drawText(mTimestamp, 40, 65, mPaint);

        //mCanvas.rotate(-90, 0, mCanvas.getWidth()/2);

        mCanvas.drawARGB(20,255,0,0); // test filling

        //mCanvas.rotate(90, mCanvas.getWidth()/2, mCanvas.getHeight()/2);
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

}
