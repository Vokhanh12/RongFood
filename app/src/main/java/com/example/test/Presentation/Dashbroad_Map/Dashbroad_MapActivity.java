package com.example.test.Presentation.Dashbroad_Map;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class Dashbroad_MapActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad_map_main);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        List<Icon> iconList = new ArrayList<>();
        iconList.add(Icon.createWithResource(this, R.drawable.home));
        //iconList.add(Icon.createWithResource(this, R.drawable.search));
       // iconList.add(Icon.createWithResource(this, R.drawable.settings));
        //iconList.add(Icon.createWithResource(this, R.drawable.profile));

        MyAdapter adapter = new MyAdapter(iconList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // xử lý sự kiện click trên item
            }
        });
        recyclerView.setAdapter(adapter);

    }


}
