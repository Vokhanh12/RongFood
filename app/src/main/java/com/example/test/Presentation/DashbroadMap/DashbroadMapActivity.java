package com.example.test.Presentation.DashbroadMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.*;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import com.example.test.Data.AccountDAO.AccountDAOimpl_FireAuth;
import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.Model.Store;
import com.example.test.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;
import androidx.core.view.GravityCompat;
import org.osmdroid.api.IMapController;
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






public class DashbroadMapActivity extends AppCompatActivity implements LocationListener {
    private String TAG = "DashbroadMapActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private TextView tvTest1;

    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView searchView;

    private TextView tvLocation;
    private TextView tvAddress;

    private LocationManager locationManager;


    private double Latitude,Longitude;
    private MapView map;

    private OverlayItem[] olItemStores = new OverlayItem[]{};

    private  int i = 0;


    //Account account = new Account("khanhyou2024@gmail.com","abc@123");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dashbroad_map);


        tvLocation = findViewById(R.id.tvLocation);
        tvAddress = findViewById(R.id.tvAddress);


        drawerLayout = findViewById(R.id.drawer_layout);

        // Khởi tạo NavigationView
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                // Xử lý sự kiện khi người dùng chọn một mục trong menu
                switch (item.getItemId()) {
                    case R.id.home:
                        // Mở fragment HomeFragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new HomeFragment()).commit();
                        break;
                   /* case R.id.settings:
                        // Mở fragment SettingsFragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new SettingsFragment()).commit();
                        break;
                    case R.id.help:
                        // Mở fragment HelpFragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new HelpFragment()).commit();
                        break;

                    */
                }

                // Đóng DrawerLayout
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }

        });


        // Tìm nút Button trong NavigationView
        Button homeButton = findViewById(R.id.btnMenu);


        frameLayout = findViewById(R.id.main_content);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Load map
        OpenMap();


        AccountDAOimpl_FireAuth accountDAOimplFirestore = new AccountDAOimpl_FireAuth(this);


        //Đăng ký tài khoảng
        //accountDAOimplFirestore.addAccount(account);
        //Thêm vào Firestore Database
      //  com.example.test.Model.Location location1 = new com.example.test.Model.Location(10.3602029,106.6791972);
       // LocationDAOimpl_Firestore locationDAOimplFirestore = new LocationDAOimpl_Firestore(this);
       // locationDAOimplFirestore.addLocation(location1);


    }

    public void OpenMap(){


        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(10.360326,106.6705763);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        //Seed the Stores in the Map
        ArrayList<OverlayItem> arlItemStores = new ArrayList<>();

        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadMapActivity.this);
        storeDAOimplFirestore.getDocumentIds()
                .addOnSuccessListener(new OnSuccessListener<List<String>>() {
            @Override
            public void onSuccess(List<String> list_storeDocumentids) {

                for(String itemList : list_storeDocumentids){

                    storeDAOimplFirestore.getStore(itemList).addOnSuccessListener(new OnSuccessListener<Store>() {
                        @Override
                        public void onSuccess(Store store) {
                            com.example.test.Model.Location locationStore = store.get_location();

                            Log.d(TAG,store.get_MaCH());
                            Log.d(TAG,store.get_NguoiSoHu());
                            Log.d(TAG,store.getTenCH());
                            Log.d(TAG,""+locationStore.getLatitude());
                            Log.d(TAG,""+locationStore.getLongitude());
                            Log.d(TAG,"TEST:"+itemList);

                            arlItemStores.add(new OverlayItem(store.getTenCH(),store.get_NguoiSoHu()
                                    ,new GeoPoint(locationStore.getLatitude(),locationStore.getLongitude())));

                            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getApplicationContext(),
                                    arlItemStores, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                                @Override
                                public boolean onItemSingleTapUp(int index, OverlayItem item) {

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
                    });



                }
            }
        });



        if(ContextCompat.checkSelfPermission(DashbroadMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DashbroadMapActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }




        //setLocation(mapController);




    }

    public void setLocation(IMapController mapController){
        try {

            GeoPoint myLocation = new GeoPoint(Latitude,Longitude);
            mapController.setCenter(myLocation);


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            long startTime = System.currentTimeMillis();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, DashbroadMapActivity.this);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            // Toast.makeText(this, "Time taken to get location: " + duration + "ms", Toast.LENGTH_SHORT).show();

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

        this.Latitude = location.getLatitude();
        this.Longitude= location.getLongitude();

        Toast.makeText(this,""+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(DashbroadMapActivity.this, Locale.getDefault());
            List<Address> addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            tvAddress.setText(address);

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

}

