package com.example.test.Presentation.Dashbroad;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.Model.Location;
import com.example.test.Model.Store;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class DashbroadActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIcon;
    private RecyclerView recyclerViewImage;

    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad_main);


        searchView = findViewById(R.id.search);

        //Khu khởi tạo
        recyclerViewIcon = findViewById(R.id.recycler_viewIcon);
        recyclerViewIcon.setLayoutManager(new GridLayoutManager(this, 4));

        recyclerViewImage = findViewById(R.id.recycler_viewImage);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this,4));

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

                switch (position){
                    case 0:
                        Toast.makeText(DashbroadActivity.this,"icon 1",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(DashbroadActivity.this,"icon 2",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }

            }
        });
        recyclerViewIcon.setAdapter(adapterIcon);



        // Khởi tạo RecyclerView
        recyclerViewImage = findViewById(R.id.recycler_viewImage);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo MyAdapterImage và gán cho RecyclerView
        List<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png");
        imageUrlList.add("https://capherangxay.vn/wp-content/uploads/2020/04/Co-nen-kinh-doanh-do-uong-online-3.jpg");
        imageUrlList.add("https://statics.vinpearl.com/quan-nuong-ngon-o-sai-gon-3%20(1)_1634616272.png");



        MyAdapterImage adapterImage = new MyAdapterImage(imageUrlList, new MyAdapterImage.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi người dùng click vào một item
            }
        });
        recyclerViewImage.scrollToPosition(1);
        recyclerViewImage.setAdapter(adapterImage);


        //Khu xử lý
        Store store = new Store("ST02","Nguyen Vo Khanh khanh 1","RongFood",new Location(10.0001,12.0001));
        Store store1 = new Store("ST03","Nguyễn Văn B","Quán Cơm - Hủ Tiếu Cà Râu",new Location(10.4238216,106.3292482));
        Store store2 = new Store("ST04","Nguyễn Văn C","Cafe võng KT",new Location(10.4236335,106.329828));
        Store store3 = new Store("ST05","Nguyễn Văn D","Sun Coffee",new Location(10.4239236,106.32756));
        Store store4 = new Store("ST06","Nguyễn Văn F","Cơm tấm Kiều Giang",new Location(10.4241077,106.3220485));
        Store store5 = new Store("ST07","Nguyễn Văn G","Quán Ăn Hùng Duy",new Location(10.4241077,106.3220485));
        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(this);

       // storeDAOimplFirestore.addStore(store1);
       // storeDAOimplFirestore.addStore(store2);
       // storeDAOimplFirestore.addStore(store3);
       // storeDAOimplFirestore.addStore(store4);
       // storeDAOimplFirestore.addStore(store5);
      //  storeDAOimplFirestore.updateStore(store,"VVGj9go0v9fgowP0Vaz9");





    }








}
