package wildfire.volunteers.smokegator.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class PelengProvider extends ContentProvider {
    private ArrayList<PelengData> mPelengs;
    static private String[] PELENG_FIELDS = {"timestamp", "callsign", "lat", "lng", "t_bearing","approved", "comment"};



    public PelengProvider() {
        mPelengs = new ArrayList<>();
        /* Набивка тестовыми данными */
        mPelengs.add(new PelengData() {{
            timestamp = new Date();
            callsign = "Kreg";
            latLng = new LatLng(56.723642, 37.770276);
            t_bearing = 70.5f;
            approved = false;
            comment = "Белый дым, из одной точки";
        }});
        mPelengs.add(new PelengData() {{
            timestamp = new Date();
            callsign = "Kreg";
            latLng = new LatLng(56.723642, 37.770276);
            t_bearing = 132f;
            approved = false;
            comment = "Белый дым, из одной точки";
        }});
        mPelengs.add(new PelengData() {{ //помойка в Кунилово
            timestamp = new Date();
            callsign = "Kreg";
            latLng = new LatLng(56.723642, 37.770276);
            t_bearing = 292f;
            approved = false;
            comment = "Вероятно, помойка в Кунилово";
        }});
        mPelengs.add(new PelengData() {{
            timestamp = new Date();
            callsign = "Нерпа";
            latLng = new LatLng(56.649173, 37.722923);
            t_bearing = 55f;
            approved = false;
            comment = "";
        }});
        mPelengs.add(new PelengData() {{
            timestamp = new Date();
            callsign = "Нерпа";
            latLng = new LatLng(56.787875, 37.821680);
            t_bearing = 163f;
            approved = false;
            comment = "Белый дым, из одной точки";
        }});

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        MatrixCursor cursor = new MatrixCursor(PELENG_FIELDS);

        int lastId = 0;
        for (PelengData data : mPelengs) {
            MatrixCursor.RowBuilder row = cursor.newRow();
            row.add("_ID", lastId++);
            row.add("timestamp", data.timestamp);
            row.add("callsign", data.callsign);
            row.add("lat", data.latLng.latitude);
            row.add("lng", data.latLng.longitude);
            row.add("t_bearing", data.t_bearing);
            row.add("approved", data.approved);
            row.add("comment", data.comment);
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
