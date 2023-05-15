package com.example.test.Presentation.DashbroadMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.test.Data.Vietnamese_Delicacies.VietnameseDelicaciesimpl_Firestore;
import com.example.test.Functionality.Functionality;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.example.test.Presentation.DashbroadShop.DashbroadShopActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.lang.reflect.Array;
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

    //Load the Stores in the Map
    private ArrayList<OverlayItem> arlItemStores = new ArrayList<>();


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

                //Used to add
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

        //Used to add
        //Đăng ký tài khoảng
        //accountDAOimplFirestore.addAccount(account);
        //Thêm vào Firestore Database
        //  com.example.test.Model.Location location1 = new com.example.test.Model.Location(10.3602029,106.6791972);
        // LocationDAOimpl_Firestore locationDAOimplFirestore = new LocationDAOimpl_Firestore(this);
        // locationDAOimplFirestore.addLocation(location1);


       /* VietnameseDelicacies Menu = new VietnameseDelicacies("Cafe","Cafe pha phin"
                ,"https://websitecukcukvn.misacdn.net/wp-content/uploads/2019/04/cafe-pha-phin-768x570.jpg"
                ,"Tiền Giang","Cafe – thứ thức uống vô cùng quan trọng và sẽ là món đồ uống chủ đạo đối với quán cafe truyền thống. Theo thống kê, cafe chiếm gần 50% tổng doanh thu của quán. Bạn có thể thấy trên thị trường có rất nhiều loại cafe từ truyền thống đến cafe phương tây nhưng phổ biến nhất là cafe đen, cafe sữa. Cafe được trồng trên mảnh đất Tây Nguyên – Quê hương của những hạt cafe nguyên chất."
        ,20.000);

        VietnameseDelicacies Menu1 = new VietnameseDelicacies("Cafe","Cafe pha máy"
                ,"https://websitecukcukvn.misacdn.net/wp-content/uploads/2019/04/cafe-pha-may-768x742.jpg"
                ,"Tiền Giang","Cafe máy (cafe Italia) là nhóm đồ uống đặc thù, có vị chua thanh, dịu nhẹ hơn so với café Việt. Việc pha chế cafe máy cần đến máy pha cafe và các dụng cụ chuyên biệt. Hơn nữa, người pha cafe máy (barista) cũng cần có một trình độ kỹ năng nhất định."
        ,25.000);

        VietnameseDelicacies Menu2 = new VietnameseDelicacies("Trà","Trà chanh lipton"
                ,"https://hc.com.vn/i/ecommerce/media/ckeditor_3328020.jpg"
                ,"Tiền Giang","Cho mật ong và đường vào, khuấy đều. Cho vài lát chanh thái mỏng vào, thêm đá viên và lắc đều. Trang trí thêm 1 lát chanh tươi lên miệng ly. Vậy là xong.\n" +
                "\n" +
                "Như vậy bạn đã hoàn thành một cốc lipton chanh chua nhẹ dịu, giải nhiệt, giảm căng thẳng những ngày hè oi bức rồi. Cùng thưởng thức với gia đình, bạn bè nhé."
        ,18.000);

        VietnameseDelicacies Menu3 = new VietnameseDelicacies("Nước ép rau","Rau má sữa tươi"
        ,"https://hc.com.vn/i/ecommerce/media/ckeditor_3230477.jpg","Tiền giang",
                " Bỏ nước ra ly, thêm đá vào và thưởng thức. Nếu bạn không thích đá thì hãy bỏ vào ngăn mát tủ lạnh 30 phút rồi đem ra thưởng thức nhé! Thức uống phân tầng 2 màu đẹp mắt, dùng muỗng khuấy đều và thưởng thức mùi thơm béo của sữa dừa hòa quyện cùng rau má đậu xanh tạo nên một hương vị khó cưỡng, vị mát lạnh xua tan cái nóng mùa hè."
        ,25.000);

        VietnameseDelicacies Menu4 = new VietnameseDelicacies("Nước ngọt","Nước tăng lực Sting hương dâu 330ml"
        ,"https://cdn.tgdd.vn/Products/Images/3226/76520/bhx/nuoc-tang-luc-sting-huong-dau-330ml-201909031559004919.jpg","Tiền giang"
        ,"Nước tăng lực Sting với mùi vị thơm ngon, sảng khoái, bổ sung hồng sâm chất lượng. Sting giúp cơ thể bù đắp nước, bổ sung năng lượng, vitamin C và E, giúp xua tan cơn khát và cảm giác mệt mỏi cùng dâu cho nhẹ nhàng và dễ chịu. Cam kết chính hãng, chất lượng và an toàn"
        ,20.000);

        VietnameseDelicacies Menu5 = new VietnameseDelicacies("Nước ngọt","Nước tăng lực Sting Gold 320ml",
                "https://cdn.tgdd.vn/Products/Images/3226/91595/bhx/nuoc-tang-luc-sting-gold-320ml-202211260911171930.jpg"
        ,"Tiền Giang","Với thành phần tự nhiên kết hợp với hương vị nhân sâm tạo nên sự kết hợp độc đáo với mùi vị thơm ngon, sảng khoái. Sản phẩm giúp cơ thể bù đắp nước, bổ sung năng lượng, vitamin C và E, giúp xua tan cơn khát và cảm giác mệt mỏi, cho bạn cảm giác cuộn trào hứng khởi"
        ,20.000);

        VietnameseDelicacies Menu6 = new VietnameseDelicacies("Nước ngọt","Nước ngọt Coca Cola chai 600ml"
        ,"https://cdn.tgdd.vn/Products/Images/2443/125397/bhx/nuoc-ngot-coca-cola-600ml-202109061127225690.jpg"
                ,"Tiền Giang","Từ thương hiệu loại nước ngọt giải khát được nhiều người yêu thích với hương vị thơm ngon, sảng khoái. Nước ngọt Coca Cola chai 600ml chính hãng nước ngọt Coca Cola với lượng gas lớn sẽ giúp bạn xua tan mọi cảm giác mệt mỏi, căng thẳng, đem lại cảm giác thoải mái sau khi hoạt động ngoài trời"
        ,20.000);

        LinkedList<VietnameseDelicacies> llMenuNuoc = new LinkedList<>();

        llMenuNuoc.add(Menu);
        llMenuNuoc.add(Menu1);
        llMenuNuoc.add(Menu2);
        llMenuNuoc.add(Menu3);
        llMenuNuoc.add(Menu4);
        llMenuNuoc.add(Menu5);
        llMenuNuoc.add(Menu6);


        com.example.test.Model.Location NuocLocation = new com.example.test.Model.Location(10.4246378,106.3290349);
        Store store = new Store("CH05","Nguyễn Văn B","Sun Coffee",NuocLocation,llMenuNuoc);

        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadMapActivity.this);
        //storeDAOimplFirestore.addStore(store,llMenuNuoc);

        */


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



        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadMapActivity.this);
        storeDAOimplFirestore.getDocumentIds().addOnSuccessListener(new OnSuccessListener<List<String>>() {
            @Override
            public void onSuccess(List<String> list_storeDocumentids) {
                for (String itemListDocumentIds : list_storeDocumentids) {
                    storeDAOimplFirestore.getStore(itemListDocumentIds).addOnSuccessListener(new OnSuccessListener<Store>() {
                        @Override
                        public void onSuccess(Store store) {
                            ArrayList<Functionality> tempOverlayItems = new ArrayList<>();
                            com.example.test.Model.Location locationStore = store.get_location();
                            CustomOverlayItem customOverlayItem = new CustomOverlayItem(store, store.getTenCH(), store.get_NguoiSoHu(), new GeoPoint(locationStore.getLatitude(), locationStore.getLongitude()));
                            arlItemStores.add(customOverlayItem);

                            ItemizedOverlayWithFocus<OverlayItem> mOverlayStores = new ItemizedOverlayWithFocus<>(getApplicationContext(), arlItemStores, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                                @Override
                                public boolean onItemSingleTapUp(int index, OverlayItem item) {
                                    CustomOverlayItem customOverlayItem = (CustomOverlayItem) item;
                                    Store storeData = customOverlayItem.getStoreData();


                                    if (storeData != null) {
                                        Intent intent = new Intent(DashbroadMapActivity.this, DashbroadShopActivity.class);
                                       intent.putExtra("storeData", storeData);
                                        startActivity(intent);
                                    }

                                    return true;
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

