package com.example.androidapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Interface.ItemClickListner;
import com.example.androidapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription;
    public ImageView imageProduct;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageProduct = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
