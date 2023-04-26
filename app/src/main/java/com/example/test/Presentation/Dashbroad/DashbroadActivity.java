package com.example.test.Presentation.Dashbroad;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.Data.StoreDAO.StoreDao;
import com.example.test.Data.Vietnamese_Delicacies.VietnameseDelicaciesimpl_Firestore;
import com.example.test.Model.Location;
import com.example.test.Model.Store;
import com.example.test.Presentation.DashbroadMap.DashbroadMapActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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


        SeachViewMonAn_VietnameseDilicacies_Store seachViewMonAn_vietnameseDilicacies_store = new SeachViewMonAn_VietnameseDilicacies_Store(DashbroadActivity.this,lvSearch,etSearch);

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
