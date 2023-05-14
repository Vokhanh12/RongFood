package com.example.test.Presentation.Store.BuyFood;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

import java.util.Arrays;
import java.util.List;

public class BuyFoodActivity extends AppCompatActivity {
    private ListView cartListView;
    private TextView cartItemCount;
    private TextView cartTotalPrice;
    private Button cartClearButton;
    private Button cartCheckoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_food);
        cartListView = (ListView) findViewById(R.id.cart_list);
        cartItemCount = (TextView) findViewById(R.id.cart_item_count);
        cartTotalPrice = (TextView) findViewById(R.id.cart_total_price);
        cartClearButton = (Button) findViewById(R.id.cart_clear_button);
        cartCheckoutButton = (Button) findViewById(R.id.cart_checkout_button);

        // Thực hiện các thao tác tương ứng với giỏ hàng tại đây
        // Ví dụ:
        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);
        cartListView.setAdapter(adapter);
        cartItemCount.setText(items.size() + " sản phẩm");
        cartTotalPrice.setText("100,000đ");
        cartClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa toàn bộ sản phẩm khỏi giỏ hàng
            }
        });
        cartCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thanh toán giỏ hàng
            }
        });
    }
}