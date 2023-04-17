package com.example.test.Presentation.Dashbroad_Map;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class Dashbroad_MapActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIcon;
    private RecyclerView recyclerViewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad_map_main);


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
                        Toast.makeText(Dashbroad_MapActivity.this,"icon 1",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(Dashbroad_MapActivity.this,"icon 2",Toast.LENGTH_SHORT).show();
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
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
        imageUrlList.add("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");


        MyAdapterImage adapterImage = new MyAdapterImage(imageUrlList, new MyAdapterImage.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi người dùng click vào một item
            }
        });
        recyclerViewImage.setAdapter(adapterImage);

    }







}
