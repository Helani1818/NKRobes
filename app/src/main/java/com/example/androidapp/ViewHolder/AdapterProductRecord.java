package com.example.androidapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.example.androidapp.customer.ProductSingleViewActivity;

import java.util.ArrayList;

public class AdapterProductRecord extends RecyclerView.Adapter<AdapterProductRecord.HolderRecord> {

    private Context context;
    private ArrayList<ProductModelRecord> productList;

    public AdapterProductRecord(Context context, ArrayList<ProductModelRecord> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ProductModelRecord model = productList.get(position);
        String id = model.getId();
        String image = model.getImage();
        String name = model.getName();

        /*if (image.equals("null")){
            //no image in the record
            holder.imageProduct.setImageResource(R.drawable.profile);
        }
        else{
            holder.imageProduct.setImageURI(Uri.parse(image));
        }*/
        holder.imageProduct.setImageURI(Uri.parse(image));
        holder.txtProductName.setText(name);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductSingleViewActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        public TextView txtProductName;
        public ImageView imageProduct;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            imageProduct = itemView.findViewById(R.id.product_image);
            txtProductName = itemView.findViewById(R.id.product_name);
        }
    }
}
