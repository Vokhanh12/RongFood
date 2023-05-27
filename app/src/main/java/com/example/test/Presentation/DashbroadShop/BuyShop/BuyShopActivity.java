package com.example.test.Presentation.DashbroadShop.BuyShop;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.test.Model.VietnameseDelicacies;
import com.example.test.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class BuyShopActivity extends AppCompatActivity {

    private String TAG="BuyShopActivity";
    private ImageView iv_pictureSP;
    private Button bt_Buy;
    private Button bt_addToCart;
    private TextView tv_nameSP;
    private TextView tv_priceSP;
    private TextView tv_Local,tv_Description,tv_cuisineType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_shop);

        iv_pictureSP = findViewById(R.id.ivPictureSP);

        bt_Buy = findViewById(R.id.btnMua);
        bt_addToCart = findViewById(R.id.btnGioHang);

        tv_Description =findViewById(R.id.tvMieuTaSP);
        tv_nameSP=findViewById(R.id.tvTenSP);
        tv_Local = findViewById(R.id.tvDiaPhuongSP);
        tv_cuisineType = findViewById(R.id.tvKieuSP);
        tv_priceSP = findViewById(R.id.tvGiaSP);



        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("storedata_ShowItemInShop"))
        {
            VietnameseDelicacies itemsVietnameseDelicacies = intent.getParcelableExtra("storedata_ShowItemInShop");

            tv_nameSP.setText(""+itemsVietnameseDelicacies.get_TenMon());
            tv_Description.setText("Miêu tả:"+itemsVietnameseDelicacies.get_MieuTa());
            tv_Local.setText("Địa phương:"+itemsVietnameseDelicacies.get_DiaPhuong());
            tv_cuisineType.setText("Kiểu:"+itemsVietnameseDelicacies.get_KieuMonAn());


            double price = itemsVietnameseDelicacies.get_Price() * 1000;
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(price);

            String priceText = formattedPrice + "VND";

            tv_priceSP.setText(priceText);

            Picasso.get()
                    .load(itemsVietnameseDelicacies.get_HinhAnh())
                    .into(iv_pictureSP);

        }else {
            Log.d(TAG,"Không tìm thấy storedata_ShowItemInShop");
        }




    }
}