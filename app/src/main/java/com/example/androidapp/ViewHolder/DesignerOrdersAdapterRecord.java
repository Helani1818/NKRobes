package com.example.androidapp.ViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.R;
import com.example.androidapp.owner.DesignerOrderActivity;
import com.example.androidapp.owner.DesignerOrder_Details;

import java.util.ArrayList;

public class DesignerOrdersAdapterRecord extends RecyclerView.Adapter<DesignerOrdersAdapterRecord.HolderRecord>{

    private Context context;
    private ArrayList<DesignerOrderModelRecord> designerOrderList;

    DBHelper db;

    public DesignerOrdersAdapterRecord(Context context, ArrayList<DesignerOrderModelRecord> designerOrderList) {
        this.context = context;
        this.designerOrderList = designerOrderList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.designer_order_list, parent, false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {

        DesignerOrderModelRecord model = designerOrderList.get(position);
        String doId = model.getDoId();
        String doName = model.getDoName();
        String doAddress = model.getDoAddress();
        String doTelNumber = model.getDoTelNumber();
        String doHeight = model.getDoHeight();
        String doWaist = model.getDoWaist();
        String doBust = model.getDoBust();
        String doNeck = model.getDoNeck ();
        String doHip = model.getDoHip ();
        String doThigh = model.getDoThigh ();
        String doNeckToHip = model.getDoNeckToHip ();
        String doProductDescription = model.getDoProductDescription ();
        String doProductImage = model.getDoProductImage ();


        holder.txtId.setText(doId);
        holder.txtDOCustomer.setText(doName);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DesignerOrder_Details.class);
                intent.putExtra("DESIGN_ORDER_ID", doId);
                context.startActivity(intent);
            }
        });

        holder.do_moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(
                        ""+position,
                        ""+doId
                );
            }
        });

    }

    private void showMoreDialog(String position, final String doId) {
        //options to display in dialog
        String[] options = {"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle item clicks
                if (which==0){
                    //delete is clicked
                    db.deleteDesignerOrder (doId);
                    //refresh the record by calling activity's onResume method
                    ((DesignerOrderActivity)context).onResume();
                }
            }
        });
        //show dialog
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return designerOrderList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        public TextView txtDOCustomer, txtId;
        public ImageButton do_moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.designerOrder_id);
            txtDOCustomer = itemView.findViewById(R.id.designerOrder_cusName);
            do_moreBtn = itemView.findViewById(R.id.do_moreBtn);
        }
    }
}
