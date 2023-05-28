package com.example.test.Presentation.Dashbroad;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.Model.ShopView;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterIcon;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterImage;
import com.example.test.Presentation.Dashbroad.SeachView.Show_VietnameseDilicacies_Store;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class DashbroadActivity extends AppCompatActivity {

    private String TAG ="DashbroadActivity";
    private RecyclerView recyclerViewIcon;
    private RecyclerView recyclerViewImage;

    private ListView lvSearch;

    private SearchView etSearch;
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
                        Intent intent1 = new Intent(DashbroadActivity.this, MainActivity.class);
                        startActivity(intent1);
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
        List<ShopView> imageUrlList = new ArrayList<>();



        MyAdapterImage adapterImage = new MyAdapterImage(imageUrlList, new MyAdapterImage.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi người dùng click vào một item
            }
        });
        recyclerViewImage.scrollToPosition(1);
        recyclerViewImage.setAdapter(adapterImage);


        //Đưa danh sách VietnameseDelicacies dùng hiển thị dữ liệu ảo cho SearchView
        Show_VietnameseDilicacies_Store seachViewMonAn_vietnameseDilicacies_store = new Show_VietnameseDilicacies_Store(DashbroadActivity.this,lvSearch,etSearch);

        etSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                  lvSearch.setVisibility(View.VISIBLE);
                }
                else
                {
                    lvSearch.setVisibility(View.GONE);
                }
            }
        });


    }








}
