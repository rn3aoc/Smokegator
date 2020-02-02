package wildfire.volunteers.smokegator.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private Bitmap mBitmap = Bitmap.createBitmap(300,80, conf), mOutput;
    private Paint mPaint;
    Matrix matrix = new Matrix();

    public MarkerIcon(float bearing, Date timestamp, String callsign){

        mBearing = bearing;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US); //ToDo date
        mTimestamp = dateFormat.format(timestamp);
        //mCallsign = sharedPreferences.getString("callsign", "default_callsign");
        mCallsign = callsign;

        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();


        matrix.setRotate(-90);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED); //ToDo set color from call
        mPaint.setTextSize(26);

        mCanvas.drawCircle(13,40,10, mPaint);
        mCanvas.drawLine(25,40,280,40, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mCanvas.drawText(mCallsign, 80, 30, mPaint);
        mCanvas.drawText(mTimestamp + ", (" + mBearing + "°)", 80, 65, mPaint); //ToDo round bearing to Int?

        // mCanvas.drawARGB(20,255,0,0); // test filling

        //mCanvas.rotate(90, mCanvas.getWidth()/2, mCanvas.getHeight()/2);
        //mCanvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    public Bitmap getBitmap(){
        return Bitmap.createBitmap(mBitmap, 0, 0, 300, 80, matrix, true);
    }

}
