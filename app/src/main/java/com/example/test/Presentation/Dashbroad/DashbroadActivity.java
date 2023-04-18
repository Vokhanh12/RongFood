package com.example.test.Presentation.Dashbroad;

import android.graphics.drawable.Icon;
import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad_main);


        recyclerViewIcon = findViewById(R.id.recycler_viewIcon);
        recyclerViewIcon.setLayoutManager(new GridLayoutManager(this, 4));

        recyclerViewImage = findViewById(R.id.recycler_viewImage);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this,4));

        List<Icon> iconList = new ArrayList<>();
        iconList.add(Icon.createWithResource(this, R.drawable.home));
        iconList.add(Icon.createWithResource(this, R.drawable.hot));
        iconList.add(Icon.createWithResource(this, R.drawable.buy));
        iconList.add(Icon.createWithResource(this, R.drawable.notification));

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
        recyclerViewImage.setAdapter(adapterImage);


        Store store = new Store("ST01","Nguyen Vo Khanh","RongFood",new Location(10.0001,12.0001));
        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(this);

        storeDAOimplFirestore.addStore(store);

    }








}
