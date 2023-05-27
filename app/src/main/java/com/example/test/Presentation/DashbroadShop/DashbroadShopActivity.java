package com.example.test.Presentation.DashbroadShop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Data.StoreDAO.DocumentIdCallback;
import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.MainActivity;
import com.example.test.Model.ShopView;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterIcon;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterImage;
import com.example.test.Presentation.Dashbroad.SeachView.SeachViewMonAn_VietnameseDilicacies_Store;
import com.example.test.Presentation.DashbroadMap.DashbroadMapActivity;
import com.example.test.Presentation.DashbroadShop.BuyShop.BuyShopActivity;
import com.example.test.Presentation.DashbroadShop.RecycleView.GridSpacingItemDecoration;
import com.example.test.Presentation.DashbroadShop.RecycleView.VerticalSpaceItemDecoration;
import com.example.test.Presentation.Store.BuyFood.BuyFoodActivity;
import com.example.test.Presentation.Store.MenuActivity;
import com.example.test.Presentation.Store.RecyclerView.NotificationActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DashbroadShopActivity extends AppCompatActivity {

    private String TAG = "DashbroadShopActivity";
    private RecyclerView recyclerViewIcon;
    private RecyclerView recyclerViewImage;

    private ListView lvSearch;

    private SearchView etSearch;

    private StoreDAOimpl_Firestore storeDAOimplFirestore;

    //private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad_main);








        lvSearch = findViewById(R.id.listnameFood);
        etSearch = findViewById(R.id.search);

        //searchView = findViewById(R.id.search);

        //Khu khởi tạo
        recyclerViewIcon = findViewById(R.id.recycler_viewIcon);
        recyclerViewIcon.setLayoutManager(new GridLayoutManager(this, 4));

        recyclerViewImage = findViewById(R.id.recycler_viewImage);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));


        //Chỉnh giao diện thành màu trắng
        recyclerViewIcon.setBackgroundColor(Color.WHITE);
        List<Icon> iconList = new ArrayList<>();
        iconList.add(Icon.createWithResource(this, R.drawable.home));
        iconList.add(Icon.createWithResource(this, R.drawable.hot));
        iconList.add(Icon.createWithResource(this, R.drawable.buy));
        iconList.add(Icon.createWithResource(this, R.drawable.notification));


        //Xử lý Cick Icon
        MyAdapterIcon adapterIcon = new MyAdapterIcon(iconList, new MyAdapterIcon.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // xử lý sự kiện click trên item

                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(DashbroadShopActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Toast.makeText(DashbroadShopActivity.this, "icon 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent3 = new Intent(DashbroadShopActivity.this, BuyFoodActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(DashbroadShopActivity.this, NotificationActivity.class);
                        startActivity(intent4);
                        break;

                }

            }
        });
        recyclerViewIcon.setAdapter(adapterIcon);


        //Lấy dữ liệu từ Store đưa vào RecycleView
        storeDAOimplFirestore = new StoreDAOimpl_Firestore(DashbroadShopActivity.this);
        //Chuyển Dữ liệu từ DashbroadMapActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("storeData_ShowInShop")) {
            Store storeData = intent.getParcelableExtra("storeData_ShowInShop");
            Log.d(TAG,"TEST:"+storeData.get_TenCH()+"&"+storeData.get_TenCH());
            // Sử dụng dữ liệu storeData ở đây

            StoreDAOimpl_Firestore storeDao = new StoreDAOimpl_Firestore(getApplicationContext());
            storeDao.getDocumentIdByTenCHMaCH(storeData.get_MaCH(), storeData.get_TenCH(), new DocumentIdCallback() {
                @Override
                public void onDocumentIdReceived(String documentId) {
                    Log.d(TAG,"Tìm Menu Cửa hàng ");
                    storeDAOimplFirestore.getStore(documentId)
                            .addOnSuccessListener(new OnSuccessListener<Store>() {
                                @Override
                                public void onSuccess(Store store) {
                                    LinkedList<VietnameseDelicacies> llMenu = store.get_llMenu();

                                    // Khởi tạo MyAdapterImage và gán cho RecyclerView
                                    List<ShopView> ShopViewList_ShowInShop = new ArrayList<>();
                                    List<ShopView> ShopViewList_ShowInDescriptionShop =new ArrayList<>();

                                    for (VietnameseDelicacies delicacy : llMenu) {
                                        String tenMon = delicacy.get_TenMon();
                                        String kieumonan = delicacy.get_KieuMonAn();
                                        String diaphuong = delicacy.get_DiaPhuong();
                                        String mieuta = delicacy.get_MieuTa();
                                        String hinhAnh = delicacy.get_HinhAnh();
                                        double price = delicacy.get_Price();

                                        // Tạo đối tượng ShopView từ thông tin lấy được và thêm vào ShopViewList_ShowInShop
                                        ShopView shopView = new ShopView(tenMon, price, hinhAnh);
                                        ShopViewList_ShowInShop.add(shopView);

                                        // Tạo đối tượng ShopView từ thông tin lấy được và thêm vào ShopViewList_ShowInDescriptionShop
                                        ShopView shopView1 = new ShopView(tenMon,hinhAnh,diaphuong,kieumonan,mieuta,price);
                                        ShopViewList_ShowInDescriptionShop.add(shopView1);

                                    }


                                    MyAdapterImage adapterImage = new MyAdapterImage(ShopViewList_ShowInShop , new MyAdapterImage.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            // Xử lý khi người dùng click vào một item
                                            for(int i = 0; i < llMenu.size(); i++){
                                                if(position == i){

                                                    VietnameseDelicacies vietnameseDelicacies =llMenu.get(i);

                                                    Intent intent_toBuyShop = new Intent(DashbroadShopActivity.this, BuyShopActivity.class);
                                                    intent_toBuyShop.putExtra("storedata_ShowItemInShop",vietnameseDelicacies);
                                                    startActivity(intent_toBuyShop);

                                                }

                                            }
                                        }
                                    });
                                    recyclerViewImage.scrollToPosition(1);
                                    recyclerViewImage.setAdapter(adapterImage);
                                }
                            });


                }
            });


        }


        SeachViewMonAn_VietnameseDilicacies_Store seachViewMonAn_vietnameseDilicacies_store = new SeachViewMonAn_VietnameseDilicacies_Store(DashbroadShopActivity.this, lvSearch, etSearch);

        etSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lvSearch.setVisibility(View.VISIBLE);
                } else {
                    lvSearch.setVisibility(View.GONE);
                }
            }
        });


    }


}
