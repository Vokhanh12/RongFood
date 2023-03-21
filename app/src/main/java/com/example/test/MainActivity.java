package com.example.test;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_main);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(10.3596633,106.6773834);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> item = new ArrayList<>();
        OverlayItem home = new OverlayItem("Cafe","myCafe",new GeoPoint(10.360512,106.677119));
        OverlayItem storeClick = new OverlayItem("Cua hang","my Cua hang",new GeoPoint(10.360512,106.677119));

        TextView tvHello = findViewById(R.id.tvHello);

        item.add(home);
        item.add(new OverlayItem("Tiem tap hoa","tiem tap hoa ba on",new GeoPoint(10.3602029,106.6791972)));
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getApplicationContext(),
                item, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                if(item==home){
                    tvHello.setText("Hell khanh");
                    return true;
                }

                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }


        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);




    }

    @Override
    public void onPause() {

        super.onPause();
        map.onPause();;
    }

    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }

}