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
import com.example.test.Presentation.DashbroadMap.MapView.CustomOverlayItem;
import com.example.test.Presentation.DashbroadMap.MapView.HomeFragment;
import com.example.test.Model.Model_DashbroadMap.useMapView.dbMap;
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

        /*
        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(getApplicationContext());


        LinkedList<VietnameseDelicacies> llMenu5 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation5 = new com.example.test.Model.Location(10.3604928, 106.3407063);
        llMenu5.add(new VietnameseDelicacies("loai","Cháo lòng - mắt má heo","https://upload.wikimedia.org/wikipedia/commons/8/82/Ch%C3%A1o_l%C3%B2ng.jpg","Tiền Giang","là một quán bình dân",50.0));
        llMenu5.add(new VietnameseDelicacies("Cháo","Cháo nấm chay","https://toplist.vn/images/800px/chao-nam-chay-41498.jpg","Tiền Giang","Cũng có công dụng như cháo nấm chay vì có nấm nhưng cháo gà nấm hương còn bổ sung thêm gà nên rất giàu dinh dưỡng.",10.0));
        llMenu5.add(new VietnameseDelicacies("Cháo","Cháo hột vịt lộn","https://toplist.vn/images/800px/chao-hot-vit-lon-444417.jpg","Tiền Giang","Cũng có công dụng như cháo nấm chay vì có nấm nhưng cháo gà nấm hương còn bổ sung thêm gà nên rất giàu dinh dưỡng.",15.0));
        llMenu5.add(new VietnameseDelicacies("Cháo","Cháo gà nấm hương","https://toplist.vn/images/800px/chao-ga-nam-huong-41529.jpg","Tiền Giang","Cũng có công dụng như cháo nấm chay vì có nấm nhưng cháo gà nấm hương còn bổ sung thêm gà nên rất giàu dinh dưỡng.",20.0));
        llMenu5.add(new VietnameseDelicacies("Cháo","Cháo lươn","https://toplist.vn/images/800px/chao-luon-41531.jpg","Tiền Giang","Cũng có công dụng như cháo nấm chay vì có nấm nhưng cháo gà nấm hương còn bổ sung thêm gà nên rất giàu dinh dưỡng.",25.0));
        llMenu5.add(new VietnameseDelicacies("Cháo","Cháo cá chép","https://toplist.vn/images/800px/chao-ca-chep-41544.jpg","Tiền Giang","Cũng có công dụng như cháo nấm chay vì có nấm nhưng cháo gà nấm hương còn bổ sung thêm gà nên rất giàu dinh dưỡng.",30.0));
        com.example.test.Model.Store store5 = new com.example.test.Model.Store( "CH05", "chưa rõ thông tin", "Cháo lòng - mắt má heo",huTieuLocation5, llMenu5);

        storeDAOimplFirestore.addStore(store5, llMenu5);

        LinkedList<VietnameseDelicacies> llMenu6 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation6 = new com.example.test.Model.Location(10.3679513, 106.343512);
        llMenu6.add(new VietnameseDelicacies("Mì nước","Hủ tiếu Tuyết Ngân","https://toplist.vn/images/800px/hu-tieu-tuyet-ngan-902089.jpg","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt. Hủ tiếu ở đây có hương vị đậm đà chuẩn vị miền Tây khiến cho thực khách ăn một lần là nhớ mãi. Đặc biệt hủ tiếu hải sản được cho khá nhiều tôm, mực ăn vô cùng xứng đáng với số tiền mà các bạn đã bỏ ra.",25.0));
        llMenu6.add(new VietnameseDelicacies("Mì Khô","Hủ tiếu khô","https://cdn.tgdd.vn/Files/2017/05/06/980028/cach-lam-hu-tieu-kho-don-gian-ma-ngon-mieng-202205271008255122.jpg","Tiền Giang","Thịt gà chín mềm, thơm béo ăn cùng với sợi hủ tiếu dai dai, nước dùng nêm nếm vừa miệng, đầm đà. Với món này, bạn có thể dọn kèm 1 ít rau sống và chén nước mắm ớt để cảm nhận trọn vẹn hương vị nhé!",15.0));
        llMenu6.add(new VietnameseDelicacies("Mì Nước","Hủ tiếu xương","https://cdn.tgdd.vn/Files/2017/05/06/980028/cach-lam-hu-tieu-kho-don-gian-ma-ngon-mieng-202205271009121812.jpg","Tiền Giang","Thịt gà chín mềm, thơm béo ăn cùng với sợi hủ tiếu dai dai, nước dùng nêm nếm vừa miệng, đầm đà. Với món này, bạn có thể dọn kèm 1 ít rau sống và chén nước mắm ớt để cảm nhận trọn vẹn hương vị nhé!",20.0));
        llMenu6.add(new VietnameseDelicacies("Mì Nước","Hủ tiếu bò viên","https://cdn.tgdd.vn/Files/2017/05/06/980028/cach-lam-hu-tieu-kho-don-gian-ma-ngon-mieng-202205271009383288.jpg","Tiền Giang","Thịt gà chín mềm, thơm béo ăn cùng với sợi hủ tiếu dai dai, nước dùng nêm nếm vừa miệng, đầm đà. Với món này, bạn có thể dọn kèm 1 ít rau sống và chén nước mắm ớt để cảm nhận trọn vẹn hương vị nhé!",35.0));
        llMenu6.add(new VietnameseDelicacies("Mì Nước","Hủ tiếu gà","https://cdn.tgdd.vn/Files/2017/05/06/980028/cach-lam-hu-tieu-kho-don-gian-ma-ngon-mieng-202203051534430474.jpg","Tiền Giang","Thịt gà chín mềm, thơm béo ăn cùng với sợi hủ tiếu dai dai, nước dùng nêm nếm vừa miệng, đầm đà. Với món này, bạn có thể dọn kèm 1 ít rau sống và chén nước mắm ớt để cảm nhận trọn vẹn hương vị nhé!",17.0));
        llMenu6.add(new VietnameseDelicacies("Mì Nước","Hủ tiếu chay","https://cdn.tgdd.vn/Files/2017/05/06/980028/cach-lam-hu-tieu-kho-don-gian-ma-ngon-mieng-202203051542367289.jpg","Tiền Giang","Thịt gà chín mềm, thơm béo ăn cùng với sợi hủ tiếu dai dai, nước dùng nêm nếm vừa miệng, đầm đà. Với món này, bạn có thể dọn kèm 1 ít rau sống và chén nước mắm ớt để cảm nhận trọn vẹn hương vị nhé!",27.0));
        com.example.test.Model.Store store6 = new com.example.test.Model.Store( "CH06", "Tuyết Ngân", "Hủ tiếu Tuyết Ngân",huTieuLocation6, llMenu6);

        storeDAOimplFirestore.addStore(store6, llMenu6);

        LinkedList<VietnameseDelicacies> llMenu7 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation7 = new com.example.test.Model.Location(10.367951, 106.343512);
        llMenu7.add(new VietnameseDelicacies("Mì nước","Mì Cay Gangnam","https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/M%C3%AC_cay_c%E1%BA%A5p_%C4%91%E1%BB%99_2_-_Mirace.jpg/788px-M%C3%AC_cay_c%E1%BA%A5p_%C4%91%E1%BB%99_2_-_Mirace.jpg","Tiền Giang","Đặc biệt hủ tiếu hải sản được cho khá nhiều tôm, mực ăn vô cùng xứng đáng với số tiền mà các bạn đã bỏ ra",25.0));
        llMenu7.add(new VietnameseDelicacies("Mỳ Cay","Mỳ gà cay","https://afamilycdn.com/150157425591193600/2020/5/14/samyang-8-15894314786831491602266.jpg","Tiền Giang","Tất cả mọi người sau khi thử hương vị mới này đều cảm thấy khá ngon miệng. Hương mỳ Jja Jang gây nghiện. Sợi mỳ có độ giòn dai vừa phải, khi nhai giúp bạn cảm thấy hạnh phúc, vui vẻ. Thậm chí có người còn nói rằng đây sẽ trở thành hương vị yêu thích mới ngay lập tức",20.0));
        llMenu7.add(new VietnameseDelicacies("Mỳ Cay","Mỳ gà double cay","https://afamilycdn.com/150157425591193600/2020/5/14/samyang-12-15894319767641612158820.jpg","Tiền Giang","Tất cả mọi người sau khi thử hương vị mới này đều cảm thấy khá ngon miệng. Hương mỳ Jja Jang gây nghiện. Sợi mỳ có độ giòn dai vừa phải, khi nhai giúp bạn cảm thấy hạnh phúc, vui vẻ. Thậm chí có người còn nói rằng đây sẽ trở thành hương vị yêu thích mới ngay lập tức",20.0));
        llMenu7.add(new VietnameseDelicacies("Mỳ Cay","Mỳ gà Jja Jang Myeon","https://afamilycdn.com/150157425591193600/2020/5/14/samyang-11-1589432319083740828062.jpg","Tiền Giang","Tất cả mọi người sau khi thử hương vị mới này đều cảm thấy khá ngon miệng. Hương mỳ Jja Jang gây nghiện. Sợi mỳ có độ giòn dai vừa phải, khi nhai giúp bạn cảm thấy hạnh phúc, vui vẻ. Thậm chí có người còn nói rằng đây sẽ trở thành hương vị yêu thích mới ngay lập tức",20.0));
        llMenu7.add(new VietnameseDelicacies("Mỳ Cay","Mỳ cay băng giá (mỳ lạnh)","https://afamilycdn.com/150157425591193600/2020/5/14/samyang-6-1589432523359550059911.jpg","Tiền Giang","Tất cả mọi người sau khi thử hương vị mới này đều cảm thấy khá ngon miệng. Hương mỳ Jja Jang gây nghiện. Sợi mỳ có độ giòn dai vừa phải, khi nhai giúp bạn cảm thấy hạnh phúc, vui vẻ. Thậm chí có người còn nói rằng đây sẽ trở thành hương vị yêu thích mới ngay lập tức",20.0));
        llMenu7.add(new VietnameseDelicacies("Mỳ Cay","Mỳ gà cay Carbonara","https://afamilycdn.com/150157425591193600/2020/5/14/samyang-5-158943273523798814157.jpg","Tiền Giang","Tất cả mọi người sau khi thử hương vị mới này đều cảm thấy khá ngon miệng. Hương mỳ Jja Jang gây nghiện. Sợi mỳ có độ giòn dai vừa phải, khi nhai giúp bạn cảm thấy hạnh phúc, vui vẻ. Thậm chí có người còn nói rằng đây sẽ trở thành hương vị yêu thích mới ngay lập tức",20.0));
        com.example.test.Model.Store store7 = new com.example.test.Model.Store( "CH07", "chưa rõ thông tin", "Mì Cay Gangnam",huTieuLocation7, llMenu7);

        storeDAOimplFirestore.addStore(store7, llMenu7);


        LinkedList<VietnameseDelicacies> llMenu8 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation8 = new com.example.test.Model.Location(10.3679507, 106.343512);
        llMenu8.add(new VietnameseDelicacies("Cơm","Cơm Tấm Bún Thịt Nướng Út Tươi","https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG/1200px-C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG?20160123134734","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt",30.0));
        llMenu8.add(new VietnameseDelicacies("Cơm Tấm","CƠM LAM TÂY BẮC","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu3.jpg","Tiền Giang","Đơn giản, dễ làm, rẻ tiền, chắc bụng, để được lâu là những ưu điểm vượt trội của món cơm nắm muối vừng. Món ăn này ngày xưa được các gia đình nông dân làm từ buổi tối để dành cho bữa sáng hôm sau hoặc để ăn đường khi phải đi xa",20.0));
        llMenu8.add(new VietnameseDelicacies("Cơm Tấm","CƠM NẮM MUỐI VỪNG","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu5-1024x768.jpg","Tiền Giang","Đơn giản, dễ làm, rẻ tiền, chắc bụng, để được lâu là những ưu điểm vượt trội của món cơm nắm muối vừng. Món ăn này ngày xưa được các gia đình nông dân làm từ buổi tối để dành cho bữa sáng hôm sau hoặc để ăn đường khi phải đi xa",30.0));
        llMenu8.add(new VietnameseDelicacies("Cơm Tấm","CƠM CHÁY NINH BÌNH","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu8.jpg","Tiền Giang","Đơn giản, dễ làm, rẻ tiền, chắc bụng, để được lâu là những ưu điểm vượt trội của món cơm nắm muối vừng. Món ăn này ngày xưa được các gia đình nông dân làm từ buổi tối để dành cho bữa sáng hôm sau hoặc để ăn đường khi phải đi xa",40.0));
        llMenu8.add(new VietnameseDelicacies("Cơm Tấm","CƠM HẾN HUẾ","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu11.jpg","Tiền Giang","Đơn giản, dễ làm, rẻ tiền, chắc bụng, để được lâu là những ưu điểm vượt trội của món cơm nắm muối vừng. Món ăn này ngày xưa được các gia đình nông dân làm từ buổi tối để dành cho bữa sáng hôm sau hoặc để ăn đường khi phải đi xa",50.0));
        llMenu8.add(new VietnameseDelicacies("Cơm Tấm"," CƠM ÂM PHỦ HUẾ","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu14.jpg","Tiền Giang","Đơn giản, dễ làm, rẻ tiền, chắc bụng, để được lâu là những ưu điểm vượt trội của món cơm nắm muối vừng. Món ăn này ngày xưa được các gia đình nông dân làm từ buổi tối để dành cho bữa sáng hôm sau hoặc để ăn đường khi phải đi xa",60.0));
        com.example.test.Model.Store store8 = new com.example.test.Model.Store( "CH08", "chưa rõ thông tin", "Cơm Tấm Bún Thịt Nướng Út Tươi",huTieuLocation8, llMenu8);

        storeDAOimplFirestore.addStore(store8, llMenu8);

        LinkedList<VietnameseDelicacies> llMenu9 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation9 = new com.example.test.Model.Location(10.3794156, 106.3334195);
        llMenu9.add(new VietnameseDelicacies("Cơm","Cơm Tấm Cầu Đôi","https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG/1200px-C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG?20160123134734","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt",35.0));
        llMenu9.add(new VietnameseDelicacies("Cơm"," CƠM GÀ TAM KỲ","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu16.jpg","Tiền Giang","Cơm gà thì nhiều vô số nhưng cơm gà Tam Kỳ mang đến sự khác biệt tinh tế từ nguyên liệu cho đến cách chế biến. Thịt gà bắt buộc phải là gà ta thả vườn, tuy con nhỏ nhưng cho thịt chắc, mềm, da mỏng. Gạo phải là loại thơm, nấu với nước luộc gà pha ít bột nghệ tạo màu vàng hấp dẫn, dẻo nhưng không bở. Khi nấu chín gia, da gà bên ngoài căng bóng, thịt bên trong chín mềm nhưng không bở.",20.0));
        llMenu9.add(new VietnameseDelicacies("Cơm","CƠM NIÊU ĐÀ NẴNG","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu18.jpg","Tiền Giang","Cơm gà thì nhiều vô số nhưng cơm gà Tam Kỳ mang đến sự khác biệt tinh tế từ nguyên liệu cho đến cách chế biến. Thịt gà bắt buộc phải là gà ta thả vườn, tuy con nhỏ nhưng cho thịt chắc, mềm, da mỏng. Gạo phải là loại thơm, nấu với nước luộc gà pha ít bột nghệ tạo màu vàng hấp dẫn, dẻo nhưng không bở. Khi nấu chín gia, da gà bên ngoài căng bóng, thịt bên trong chín mềm nhưng không bở.",22.0));
        llMenu9.add(new VietnameseDelicacies("Cơm","CƠM TẤM SÀI GÒN","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu21.jpg","Tiền Giang","Cơm gà thì nhiều vô số nhưng cơm gà Tam Kỳ mang đến sự khác biệt tinh tế từ nguyên liệu cho đến cách chế biến. Thịt gà bắt buộc phải là gà ta thả vườn, tuy con nhỏ nhưng cho thịt chắc, mềm, da mỏng. Gạo phải là loại thơm, nấu với nước luộc gà pha ít bột nghệ tạo màu vàng hấp dẫn, dẻo nhưng không bở. Khi nấu chín gia, da gà bên ngoài căng bóng, thịt bên trong chín mềm nhưng không bở.",30.0));
        llMenu9.add(new VietnameseDelicacies("Cơm","CƠM GHẸ PHÚ QUỐC","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu23.jpg","Tiền Giang","Cơm gà thì nhiều vô số nhưng cơm gà Tam Kỳ mang đến sự khác biệt tinh tế từ nguyên liệu cho đến cách chế biến. Thịt gà bắt buộc phải là gà ta thả vườn, tuy con nhỏ nhưng cho thịt chắc, mềm, da mỏng. Gạo phải là loại thơm, nấu với nước luộc gà pha ít bột nghệ tạo màu vàng hấp dẫn, dẻo nhưng không bở. Khi nấu chín gia, da gà bên ngoài căng bóng, thịt bên trong chín mềm nhưng không bở.",45.0));
        llMenu9.add(new VietnameseDelicacies("_Cơm","CƠM DỪA BẾN TRE","https://cdn3.ivivu.com/2014/11/di-doc-Viet-Nam-thuong-thuc-12-mon-com-ngon-tuyet-ivivu.jpg","Tiền Giang","Cơm gà thì nhiều vô số nhưng cơm gà Tam Kỳ mang đến sự khác biệt tinh tế từ nguyên liệu cho đến cách chế biến. Thịt gà bắt buộc phải là gà ta thả vườn, tuy con nhỏ nhưng cho thịt chắc, mềm, da mỏng. Gạo phải là loại thơm, nấu với nước luộc gà pha ít bột nghệ tạo màu vàng hấp dẫn, dẻo nhưng không bở. Khi nấu chín gia, da gà bên ngoài căng bóng, thịt bên trong chín mềm nhưng không bở.",15.0));
        com.example.test.Model.Store store9 = new com.example.test.Model.Store( "CH09", "chưa rõ thông tin", "Cơm Tấm Cầu Đôi",huTieuLocation9, llMenu9);

        storeDAOimplFirestore.addStore(store9, llMenu9);

        LinkedList<VietnameseDelicacies> llMenu10 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation10 = new com.example.test.Model.Location(10.3794153, 106.3334195);
        llMenu10.add(new VietnameseDelicacies("Nhiều loại","Cơm tấm. Phở. Bún bò huế. Hủ tiếu","https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG/1200px-C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_c%C3%A2y.JPG?20160123134734","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt",20.0));
        llMenu10.add(new VietnameseDelicacies("Phở","Phở bò","https://cdn.tgdd.vn/2021/06/content/phobo-800x450.jpg","Tiền Giang","Món phở làm nên tên tuổi của phở Việt Nam nhất định phải kể đến phở bò. Tô phở với hương thơm đặc trưng, cực hấp dẫn, những miếng nạm bò to, dày, thịt bò tái hồng hồng, thêm chút màu xanh và hương thơm từ hành và các loại rau ăn kèm khác, nhìn thôi là đã thấy ngon rồi.",10.0));
        llMenu10.add(new VietnameseDelicacies("Phở","Phở cuốn","https://cdn.tgdd.vn/2021/06/content/phocuon-800x450.jpg","Tiền Giang","Món phở làm nên tên tuổi của phở Việt Nam nhất định phải kể đến phở bò. Tô phở với hương thơm đặc trưng, cực hấp dẫn, những miếng nạm bò to, dày, thịt bò tái hồng hồng, thêm chút màu xanh và hương thơm từ hành và các loại rau ăn kèm khác, nhìn thôi là đã thấy ngon rồi.",15.0));
        llMenu10.add(new VietnameseDelicacies("Phở","Phở gà","https://cdn.tgdd.vn/2021/06/content/phoga-800x450.jpg","Tiền Giang","Món phở làm nên tên tuổi của phở Việt Nam nhất định phải kể đến phở bò. Tô phở với hương thơm đặc trưng, cực hấp dẫn, những miếng nạm bò to, dày, thịt bò tái hồng hồng, thêm chút màu xanh và hương thơm từ hành và các loại rau ăn kèm khác, nhìn thôi là đã thấy ngon rồi.",20.0));
        llMenu10.add(new VietnameseDelicacies("Phở","Phở xào","https://cdn.tgdd.vn/2021/06/content/phoxao-800x450.jpg","Tiền Giang","Món phở làm nên tên tuổi của phở Việt Nam nhất định phải kể đến phở bò. Tô phở với hương thơm đặc trưng, cực hấp dẫn, những miếng nạm bò to, dày, thịt bò tái hồng hồng, thêm chút màu xanh và hương thơm từ hành và các loại rau ăn kèm khác, nhìn thôi là đã thấy ngon rồi.",25.0));
        llMenu10.add(new VietnameseDelicacies("Phở","Phở khô Gia Lai","https://cdn.tgdd.vn/2021/06/content/phokhoGL-800x450.jpg","Tiền Giang","Món phở làm nên tên tuổi của phở Việt Nam nhất định phải kể đến phở bò. Tô phở với hương thơm đặc trưng, cực hấp dẫn, những miếng nạm bò to, dày, thịt bò tái hồng hồng, thêm chút màu xanh và hương thơm từ hành và các loại rau ăn kèm khác, nhìn thôi là đã thấy ngon rồi.",30.0));
        com.example.test.Model.Store store10 = new com.example.test.Model.Store( "CH10", "chưa rõ thông tin", "Cơm tấm. Phở. Bún bò huế. Hủ tiếu",huTieuLocation10, llMenu10);

        storeDAOimplFirestore.addStore(store10, llMenu10);

        LinkedList<VietnameseDelicacies> llMenu11 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation11 = new com.example.test.Model.Location(10.3690989, 106.3170601);
        llMenu11.add(new VietnameseDelicacies("Bánh Canh","Bún Bò Huế - Bún Rêu Cua - Hủ Tiếu Sa Tế","https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/B%C3%BAn_b%C3%B2_Hu%E1%BA%BF_%2820201109%29.jpg/1200px-B%C3%BAn_b%C3%B2_Hu%E1%BA%BF_%2820201109%29.jpg","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt",25.0));
        llMenu11.add(new VietnameseDelicacies("Bún","Bún riêu cua đồng","https://media.cooky.vn/recipe/g2/19394/s800x500/recipe19394-636263144069761726.jpg","Tiền Giang","Tô bún thơm, nước dùng ngọt từ củ quả và phần riêu làm bằng đậu hũ ăn kèm với các loại rau rất thanh mát. Đây sẽ là thực đơn cho những ngày rằm đấy, cùng vào bếp trổ tài món ăn nào.",10.0));
        llMenu11.add(new VietnameseDelicacies("Bún","Bún riêu cua xí quách","https://media.cooky.vn/recipe/g2/13695/s800x500/recipe-635579302768883999.jpg","Tiền Giang","_noidung_",20.0));
        llMenu11.add(new VietnameseDelicacies("Bún","Bún riêu thịt","https://media.cooky.vn/recipe/g2/17661/s640x400/recipe17661-prepare-step5-636052315857671284.jpg","Tiền Giang","Tô bún thơm, nước dùng ngọt từ củ quả và phần riêu làm bằng đậu hũ ăn kèm với các loại rau rất thanh mát. Đây sẽ là thực đơn cho những ngày rằm đấy, cùng vào bếp trổ tài món ăn nào.15",25.0));
        llMenu11.add(new VietnameseDelicacies("Bún","Bún riêu cua giò heo","https://media.cooky.vn/recipe/g2/15246/s800x500/recipe15246-635900199015245855.jpg","Tiền Giang","Tô bún thơm, nước dùng ngọt từ củ quả và phần riêu làm bằng đậu hũ ăn kèm với các loại rau rất thanh mát. Đây sẽ là thực đơn cho những ngày rằm đấy, cùng vào bếp trổ tài món ăn nào.",30.0));
        llMenu11.add(new VietnameseDelicacies("Bún","Bún riêu chay","https://media.cooky.vn/recipe/g1/1137/s800x500/recipe1137-635562431792273215.jpg","Tiền Giang","Tô bún thơm, nước dùng ngọt từ củ quả và phần riêu làm bằng đậu hũ ăn kèm với các loại rau rất thanh mát. Đây sẽ là thực đơn cho những ngày rằm đấy, cùng vào bếp trổ tài món ăn nào.",45.0));
        com.example.test.Model.Store store11 = new com.example.test.Model.Store( "CH11", "chưa rõ thông tin", "Bún Bò Huế - Bún Rêu Cua - Hủ Tiếu Sa Tế",huTieuLocation11, llMenu11);

        storeDAOimplFirestore.addStore(store11, llMenu11);

        LinkedList<VietnameseDelicacies> llMenu12 = new LinkedList<>();
        com.example.test.Model.Location huTieuLocation12 = new com.example.test.Model.Location(10.3794153, 106.3334195);
        llMenu12.add(new VietnameseDelicacies("Nhiều loại","Cơm gà - Bánh cuốn nóng - Bún thịt nướng","https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/B%C3%BAn_b%C3%B2_Hu%E1%BA%BF_%2820201109%29.jpg/1200px-B%C3%BAn_b%C3%B2_Hu%E1%BA%BF_%2820201109%29.jpg","Tiền Giang","là một quán bình dân nhưng mà thực đơn tại quán rất đa dạng với các loại hủ tiếu từ hủ tiếu sườn cho đến hủ tiếu mực và cả hủ tiếu hải sản đặc biệt",25.0));
        llMenu12.add(new VietnameseDelicacies("Cơm","Cơm gà Hải Nam","https://cdn.tgdd.vn/2021/05/content/comga1-800x450.jpg","Tiền Giang","Cơm mềm dẻo và mang trong mình màu đỏ bắt mắt từ thịt quả gấc, thịt gà kho cùng nước cốt dừa nên đậm đà, beo béo vô cùng. Ăn kèm 1 ít rau răm và hành lá nữa thì món cơm gà ",20.0));
        llMenu12.add(new VietnameseDelicacies("Cơm","Cơm gà Tam Kỳ","https://cdn.tgdd.vn/2021/05/content/comga2-800x450.jpg","Tiền Giang","Cơm mềm dẻo và mang trong mình màu đỏ bắt mắt từ thịt quả gấc, thịt gà kho cùng nước cốt dừa nên đậm đà, beo béo vô cùng. Ăn kèm 1 ít rau răm và hành lá nữa thì món cơm gà ",30.0));
        llMenu12.add(new VietnameseDelicacies("Cơm","Cơm gà xối mỡ","https://cdn.tgdd.vn/2021/05/content/comga3-800x450.jpg","Tiền Giang","Cơm mềm dẻo và mang trong mình màu đỏ bắt mắt từ thịt quả gấc, thịt gà kho cùng nước cốt dừa nên đậm đà, beo béo vô cùng. Ăn kèm 1 ít rau răm và hành lá nữa thì món cơm gà ",40.0));
        llMenu12.add(new VietnameseDelicacies("Cơm","Cơm gà Hội An","https://cdn.tgdd.vn/2021/05/content/comga4-800x450.jpg","Tiền Giang","Cơm mềm dẻo và mang trong mình màu đỏ bắt mắt từ thịt quả gấc, thịt gà kho cùng nước cốt dừa nên đậm đà, beo béo vô cùng. Ăn kèm 1 ít rau răm và hành lá nữa thì món cơm gà ",25.0));
        llMenu12.add(new VietnameseDelicacies("Cơm","Cơm gà đỏ Cần Giờ","https://cdn.tgdd.vn/2021/05/content/comga5-800x450.jpg","Tiền Giang","Cơm mềm dẻo và mang trong mình màu đỏ bắt mắt từ thịt quả gấc, thịt gà kho cùng nước cốt dừa nên đậm đà, beo béo vô cùng. Ăn kèm 1 ít rau răm và hành lá nữa thì món cơm gà ",35.0));
        com.example.test.Model.Store store12 = new com.example.test.Model.Store( "CH12", "chưa rõ thông tin", "Cơm gà - Bánh cuốn nóng - Bún thịt nướng",huTieuLocation12, llMenu12);

        storeDAOimplFirestore.addStore(store12, llMenu12);



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
                                        intent.putExtra("storeData_ShowInShop", storeData);
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

                com.example.test.Model.Location LocationEnd = new com.example.test.Model.Location(store.get_location().getLatitude(), store.get_location().getLongitude());

                com.example.test.Model.Location locationStart = new com.example.test.Model.Location(10.3607416, 106.6777139);
                //com.example.test.Model.Location locationEnd = store.get_location();
                com.example.test.Model.Location locationEnd = new com.example.test.Model.Location(LocationEnd.getLatitude(), LocationEnd.getLongitude());
                Log.d(TAG, "" + locationEnd.getLatitude());
                Log.d(TAG, "" + locationEnd.getLongitude());
                dbMap mapDashbroad = new dbMap(locationStart, locationEnd, DashbroadMapActivity.this, map);
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

