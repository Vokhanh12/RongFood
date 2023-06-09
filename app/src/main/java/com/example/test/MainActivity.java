package com.example.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import com.example.test.Data.AccountDAO.AccountDAO;
import com.example.test.Data.AccountDAO.AccountDAOImpl_SQLite;
import com.example.test.Model.Account;
import com.example.test.Presentation.Dashbroad.DashbroadActivity;
import com.example.test.Presentation.DashbroadMap.DashbroadMapActivity;
import com.example.test.Presentation.DashbroadShop.BuyShop.BuyShopActivity;
import com.example.test.Presentation.DashbroadShop.DashbroadShopActivity;
import com.example.test.SystemConfig.storeFirebase;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.*;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.views.overlay.Polyline;

import static com.example.test.SystemConfig.storeFirebase.getAllDocumentID;
import static com.example.test.SystemConfig.storeFirebase.list_storeDocumentIds;


public class MainActivity extends AppCompatActivity implements LocationListener{

    MapView map;
    TextView tvHello,tvLocation,tvData;

    Button btnMap1;
    LocationManager locationManager;

    AccountDAO accountDAO;

    Account account1 = new Account("Admin","Admin@123");
    Account account2 = new Account("khanhyou2018","123456");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvHello =(TextView) findViewById(R.id.tvHello);
        btnMap1 =(Button) findViewById(R.id.btnMap);
        tvLocation=(TextView)findViewById(R.id.tvLocation);
        tvData=(TextView)findViewById(R.id.tvData);

        accountDAO = new AccountDAOImpl_SQLite(this);

        //accountDAO.addAccount(account1);
        //accountDAO.addAccount(account2);

        btnMap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DashbroadMapActivity.class);
                startActivity(intent);
                //Kết thúc form cũ
                finish();
              // getLocation();

                //Account account1View = accountDAO.getAcccountByUsername("Admin");

                //tvData.setText(""+account1View.getPassword().toString());
            }
        });



        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        //Điểm start Point
        GeoPoint startPoint = new GeoPoint(10.3596633, 106.6773834);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> item = new ArrayList<>();

        OverlayItem home = new OverlayItem("Cafe", "myCafe", new GeoPoint(10.360512, 106.677119));
        OverlayItem storeClick = new OverlayItem("Cua hang", "my Cua hang", new GeoPoint(10.360512, 106.677119));

        item.add(home);
        item.add(new OverlayItem("Tiem tap hoa","tiem tap hoa ba on",new GeoPoint(10.3602029,106.6791972)));
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getApplicationContext(),
                item, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {return false;}

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }


        });

       mOverlay.setFocusItemsOnTap(true);
       map.getOverlays().add(mOverlay);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        //firebase
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(MainActivity.this);


        // Usage
        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        waypoints.add(new GeoPoint(10.360512, 106.677119)); // Start point
        waypoints.add(new GeoPoint(10.3602029,106.6791972)); // End point

        RoadManagerTask roadManagerTask = new RoadManagerTask();
        roadManagerTask.execute(waypoints);






    }

    @SuppressLint("MissingPermission")
    private void getLocation(){

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            long startTime = System.currentTimeMillis();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainActivity.this);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            Toast.makeText(this, "Time taken to get location: " + duration + "ms", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this,""+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            tvHello.setText(address);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println(""+location.getLatitude()+","+location.getLongitude());
        tvLocation.setText(""+location.getLatitude()+","+location.getLongitude());
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO: handle status changes
    }

    private class RoadManagerTask extends AsyncTask<ArrayList<GeoPoint>, Void, Road> {

        @Override
        protected Road doInBackground(ArrayList<GeoPoint>... params) {


            String userAgent = System.getProperty("http.agent");
            ArrayList<GeoPoint> waypoints = params[0];
            RoadManager roadManager = new OSRMRoadManager(MainActivity.this,userAgent);

            return roadManager.getRoad(waypoints);


        }

        @Override
        protected void onPostExecute(Road road) {
            // Update UI with road data
            // Draw the road on the map
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
            roadOverlay.setWidth(20f);
            map.getOverlays().add(roadOverlay);
            map.invalidate();


            double lengthInMeters = roadOverlay.getDistance();

            tvHello.setText((int)lengthInMeters+" Mét");


        }
    }

}