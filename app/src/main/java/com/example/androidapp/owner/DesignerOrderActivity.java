package com.example.androidapp.owner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.R;
import com.example.androidapp.ViewHolder.AdapterProductRecord;
import com.example.androidapp.ViewHolder.DesignerOrdersAdapterRecord;
import com.example.androidapp.customer.ProductViewModel;

public class DesignerOrderActivity extends AppCompatActivity {

    private RecyclerView designerOrderRecycleView;

    private DBHelper db;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_order);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Designer Orders");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        designerOrderRecycleView = findViewById(R.id.designerOrder_recycleView);
        db = new DBHelper(this);

        loadDesignerOrders();
    }

    private void loadDesignerOrders() {
        DesignerOrdersAdapterRecord designerOrdersAdapterRecord =
                new DesignerOrdersAdapterRecord(this, db.viewDesignerOrders(DBHelper.D_ORDERS_COL1 + " DESC"));

        designerOrderRecycleView.setAdapter(designerOrdersAdapterRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDesignerOrders();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}