package com.example.test.Presentation.DashbroadMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
import com.example.test.Data.Vietnamese_Delicacies.VietnameseDelicaciesimpl_Firestore;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.example.test.Presentation.Dashbroad.SeachViewMonAn_VietnameseDilicacies_Store;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;
import androidx.core.view.GravityCompat;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private Button btnClick;


    private double Latitude, Longitude;
    private MapView map;

    private OverlayItem[] olItemStores = new OverlayItem[]{};

    private int i = 0;

    private ListView listViewSeach;


    //Account account = new Account("khanhyou2024@gmail.com","abc@123");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dashbroad_map);


        tvLocation = findViewById(R.id.tvLocation);
        tvAddress = findViewById(R.id.tvAddress);

        btnClick = findViewById(R.id.btnClick);


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

        btnClick.setOnClickListener(new View.OnClickListener() {

            VietnameseDelicaciesimpl_Firestore vietnameseDelicaciesimplFirestore = new VietnameseDelicaciesimpl_Firestore(DashbroadMapActivity.this);


            @Override
            public void onClick(View v) {
                StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadMapActivity.this);

                storeDAOimplFirestore.getStore("ftaKAKnAjX0oh3EAQ9DT").addOnCompleteListener(new OnCompleteListener<Store>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Store> task) {
                        if (task.isSuccessful()) {
                            Store store = task.getResult();
                            LinkedList<VietnameseDelicacies> llMenu = store.get_Menu();

                            for (VietnameseDelicacies menu : llMenu) {
                                Log.d(TAG, "_DiaPhuong: " + menu.get_DiaPhuong());
                                Log.d(TAG, "_HinhAnh: " + menu.get_HinhAnh());
                                Log.d(TAG, "_KieuMonAn: " + menu.get_KieuMonAn());
                                Log.d(TAG, "_MieuTa: " + menu.get_MieuTa());
                                Log.d(TAG, "_Price: " + menu.get_Price());
                                Log.d(TAG, "_TenMon: " + menu.get_TenMon());
                            }

                        } else {
                            Exception e = task.getException();
                            Log.e("Firestore Error", e.getMessage());
                        }
                    }
                });

                getLocation();


                //vietnameseDelicaciesimplFirestore.getDocumentCount();

                /*
                try {

                    AssetManager assetManagerSP = getAssets();
                    InputStream inputStreamSP = assetManagerSP.open("SP.txt");

                    AssetManager assetManagerDiaPhuong = getAssets();
                    InputStream inputStreamDiaPhuong = assetManagerDiaPhuong.open("DiaPhuong.txt");

                    AssetManager assetManagerHinhAnh = getAssets();
                    InputStream inputStreamHinhAnh = assetManagerDiaPhuong.open("HinhAnh.txt");

                    AssetManager assetManagerKieuMonAn = getAssets();
                    InputStream inputStreamKieuMonAn = assetManagerKieuMonAn.open("KieuMonAn.txt");

                    AssetManager assetManagerMieuTa = getAssets();
                    InputStream inputStreamMieuTa = assetManagerMieuTa.open("MieuTaSP.txt");

                    BufferedReader bufferedReaderSP = new BufferedReader(new InputStreamReader(inputStreamSP));
                    BufferedReader bufferedReaderDiaPhuong = new BufferedReader(new InputStreamReader(inputStreamDiaPhuong));
                    BufferedReader bufferedReaderHinhAnh = new BufferedReader(new InputStreamReader(inputStreamHinhAnh));
                    BufferedReader bufferedReaderKieuMonAN = new BufferedReader(new InputStreamReader(inputStreamKieuMonAn));
                    BufferedReader bufferedReaderMieuTa = new BufferedReader(new InputStreamReader(inputStreamMieuTa));

                    String lineSP,lineDiaPhuong,lineHinhAnh,lineKieuMonAn,lineMieuTa;

                    while ((lineSP = bufferedReaderSP.readLine()) != null && (lineDiaPhuong = bufferedReaderDiaPhuong.readLine()) != null && (lineHinhAnh = bufferedReaderHinhAnh.readLine()) != null
                    && (lineKieuMonAn = bufferedReaderKieuMonAN.readLine()) != null && (lineMieuTa = bufferedReaderMieuTa.readLine()) != null) {
                        vietnameseDelicaciesimplFirestore.addVietnameseDelicacies(new VietnameseDelicacies(lineKieuMonAn,lineSP,lineHinhAnh,lineDiaPhuong,lineMieuTa));
                    }

                    bufferedReaderSP.close();
                    inputStreamSP.close();

                    bufferedReaderDiaPhuong.close();
                    inputStreamDiaPhuong.close();

                    bufferedReaderHinhAnh.close();
                    inputStreamHinhAnh.close();

                    bufferedReaderKieuMonAN.close();
                    inputStreamKieuMonAn.close();

                    bufferedReaderMieuTa.close();
                    inputStreamMieuTa.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            */

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


        VietnameseDelicacies Menu = new VietnameseDelicacies("Bánh bột mì","Bánh mì thịt bò nướng"
                ,"https://cdn.tgdd.vn/2021/05/CookRecipe/Avatar/banh-mi-thit-bo-nuong-thumbnail-1.jpg"
                ,"Tiền Giang","Bánh mì thịt bò nướng có thịt bò được nướng thơm lừng, đậm vị bên trong bánh mì giòn tan, thêm chút rau và đồ chua ngon lành, là một món ăn sáng cực kì bổ dưỡng và thơm ngon."
        ,25.000);

        VietnameseDelicacies Menu1 = new VietnameseDelicacies("Bánh bột mì","Bánh mì thịt bò xào"
                ,"https://cdn.tgdd.vn/2021/05/CookRecipe/Avatar/banh-mi-thit-bo-xao-thumbnail-2.jpg"
                ,"Tiền Giang","Bánh mì thịt bò xào phô mai tuy đơn giản nhưng vô cùng hấp dẫn. Thịt bò mềm ngọt quyện với độ giòn giòn của hành tây kết hợp với vị phô mai thơm lừng béo ngậy, đây sẽ là 1 món ăn ngon bất ngờ khiến phải suýt xoa và muốn ăn thêm nữa đấy!"
        ,25.000);

        VietnameseDelicacies Menu2 = new VietnameseDelicacies("Bánh bột mì","Bánh mì thịt bò băm"
                ,"https://cdn.tgdd.vn/2021/05/CookRecipe/Avatar/banh-mi-thit-bo-xao-thumbnail-1.jpg"
                ,"Tiền Giang","Bánh mì thịt bò băm thơm lừng, có vị ngon ngọt của thịt bò băm và vị béo ngậy, tan chảy của phô mai, chắc chắn sẽ là một món ăn cực kì độc đáo bạn có thể dùng để thiết đãi gia đình và bạn bè."
        ,30.000);

        LinkedList<VietnameseDelicacies> llMenuBanhMi = new LinkedList<>();

        llMenuBanhMi.add(Menu);
        llMenuBanhMi.add(Menu1);
        llMenuBanhMi.add(Menu2);


        com.example.test.Model.Location banhmiLocation = new com.example.test.Model.Location(10.421457,106.3372856);
        Store store = new Store("CH01","Tuấn Kiệt","Bánh mì Tuấn kiệt",banhmiLocation,llMenuBanhMi);




    }

    public void OpenMap() {


        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(10.360326, 106.6705763);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        //Load the Stores in the Map
        ArrayList<OverlayItem> arlItemStores = new ArrayList<>();

        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadMapActivity.this);
        storeDAOimplFirestore.getDocumentIds()
                .addOnSuccessListener(new OnSuccessListener<List<String>>() {
                    @Override
                    public void onSuccess(List<String> list_storeDocumentids) {

                        for (String itemList : list_storeDocumentids) {

                            storeDAOimplFirestore.getStore(itemList).addOnSuccessListener(new OnSuccessListener<Store>() {
                                @Override
                                public void onSuccess(Store store) {
                                    com.example.test.Model.Location locationStore = store.get_location();
                                    arlItemStores.add(new OverlayItem(store.getTenCH(), store.get_NguoiSoHu()
                                            , new GeoPoint(locationStore.getLatitude(), locationStore.getLongitude())));

                                    ItemizedOverlayWithFocus<OverlayItem> mOverlayStores = new ItemizedOverlayWithFocus<>(getApplicationContext(),
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

                                    mOverlayStores.setFocusItemsOnTap(true);
                                    map.getOverlays().add(mOverlayStores);


                                }
                            });


                        }
                    }
                });


        //Add current my Location

        ArrayList<OverlayItem> arlOverlayMyLocation = new ArrayList<>();
        arlOverlayMyLocation.add(new OverlayItem("Cafe shop", "My location", new GeoPoint(10.3607416, 106.6777139)));

        ItemizedOverlayWithFocus<OverlayItem> myOverlay = new ItemizedOverlayWithFocus<>(getApplicationContext(),
                arlOverlayMyLocation, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        myOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(myOverlay);

        if (ContextCompat.checkSelfPermission(DashbroadMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashbroadMapActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }


        //setLocation(mapController);

        //Find Way


        storeDAOimplFirestore.getStore("ftaKAKnAjX0oh3EAQ9DT").addOnSuccessListener(new OnSuccessListener<Store>() {
            @Override
            public void onSuccess(Store store) {

                com.example.test.Model.Location LocationEnd = new com.example.test.Model.Location(store.get_location().getLatitude(),store.get_location().getLongitude());

                com.example.test.Model.Location locationStart = new com.example.test.Model.Location(10.3607416, 106.6777139);
                //com.example.test.Model.Location locationEnd = store.get_location();
                com.example.test.Model.Location locationEnd = new com.example.test.Model.Location(LocationEnd.getLatitude(), LocationEnd.getLongitude());
                Log.d(TAG, "" + locationEnd.getLatitude());
                Log.d(TAG, "" + locationEnd.getLongitude());
                MapDashbroad mapDashbroad = new MapDashbroad(locationStart, locationEnd, DashbroadMapActivity.this, map);
                //Find way
                mapDashbroad.Findway();


            }

        });






    }

    public void setLocation(IMapController mapController) {
        try {

            GeoPoint myLocation = new GeoPoint(Latitude, Longitude);
            mapController.setCenter(myLocation);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(DashbroadMapActivity.LOCATION_SERVICE);

// Kiểm tra xem quyền truy cập vị trí đã được cấp cho ứng dụng hay chưa
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa được cấp quyền, yêu cầu cấp quyền truy cập vị trí
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

// Lấy vị trí thông qua dịch vụ vị trí được cung cấp
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(this, "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

        this.Latitude = location.getLatitude();
        this.Longitude = location.getLongitude();

        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(DashbroadMapActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            tvAddress.setText(address);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("" + location.getLatitude() + "," + location.getLongitude());
        tvLocation.setText("" + location.getLatitude() + "," + location.getLongitude());
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO: handle status changes
    }


}

