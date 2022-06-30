package com.example.androidapp.customer;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Database.DBHelper;
import com.example.androidapp.R;
import com.example.androidapp.ViewHolder.AdapterProductRecord;
import com.pixplicity.easyprefs.library.Prefs;

public class ProductsFragment extends Fragment
{
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView;
    String username;
    private DBHelper db;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        setHasOptionsMenu(true);
        recyclerView = root.findViewById(R.id.product_recycleView);
        db = new DBHelper(getActivity());
        loadRecords();


        return root;
    }

    private void loadRecords() {
        AdapterProductRecord adapterProductRecord =
                new AdapterProductRecord(getActivity(), db.viewProducts(DBHelper.PRODUCT_COL1 + " DESC"));

        recyclerView.setAdapter(adapterProductRecord);
    }
//
    @Override
    public void onResume() {
        super.onResume();
        loadRecords();
    }

    /*private void searchRecords(String query) {
        AdapterProductRecord adapterProductRecord = new AdapterProductRecord(getActivity(), db.searchProducts(query));
        recyclerView.setAdapter(adapterProductRecord);
    }

   @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*int cid = item.getItemId();
        if(cid == R.id.action_delete){
            db.deleteAllComments();
            onResume();
        }
        return super.onOptionsItemSelected(item);
    }*/

}
