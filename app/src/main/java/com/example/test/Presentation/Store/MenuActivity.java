package com.example.test.Presentation.Store;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.Model.Menu;
import com.example.test.Presentation.Dashbroad.ListView.ListViewApdapterSeach;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterIcon;
import com.example.test.Presentation.Dashbroad.RecycleView.MyAdapterImage;
import com.example.test.Presentation.Dashbroad.SeachView.SeachViewMonAn_VietnameseDilicacies_Store;
import com.example.test.Presentation.Store.BuyFood.BuyFoodActivity;
import com.example.test.Presentation.Store.ListView.ListViewAdapterSearchMenu;
import com.example.test.Presentation.Store.RecyclerView.MyAdapterImageMenu;
import com.example.test.Presentation.Store.RecyclerView.NotificationActivity;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private String TAG ="DashbroadActivity";
    private RecyclerView recyclerViewIcon;
    private RecyclerView recyclerViewImage;
    private ListViewAdapterSearchMenu searchadapter;
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
                        Intent intent1 = new Intent(MenuActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Toast.makeText(MenuActivity.this,"icon 2",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent3 = new Intent(MenuActivity.this, BuyFoodActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(MenuActivity.this, NotificationActivity.class);
                        startActivity(intent4);
                        break;

                }

            }
        });
        recyclerViewIcon.setAdapter(adapterIcon);
        // Khởi tạo RecyclerView
        recyclerViewImage = findViewById(R.id.recycler_viewImage);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));

        // Khởi tạo MyAdapterImage và gán cho RecyclerView
        List<Menu> imageUrlList = new ArrayList<>();
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));
        imageUrlList.add(new Menu("Sản phẩm 1", 10.0, "https://vegafood.vn/storage/2022/02/939/mceu-67729456761643952103913.png", 12));



        MyAdapterImageMenu adapterImage = new MyAdapterImageMenu(imageUrlList, new MyAdapterImageMenu.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi người dùng click vào một item
            }
        });
        recyclerViewImage.scrollToPosition(1);
        recyclerViewImage.setAdapter(adapterImage);


        SeachViewMonAn_VietnameseDilicacies_Store seachViewMonAn_vietnameseDilicacies_store = new SeachViewMonAn_VietnameseDilicacies_Store(MenuActivity.this,lvSearch,etSearch);
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchadapter.getFilter().filter(newText);
                return false;
            }
        });
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