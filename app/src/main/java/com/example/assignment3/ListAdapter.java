package com.example.assignment3;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private int resource;
    private Database database;

    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        database = new Database(context);
        TextView productName = view.findViewById(R.id.tvProductName);
        TextView productPrice = view.findViewById(R.id.tvProductPrice);
        ImageView imageView = view.findViewById(R.id.ivShip);
        ImageView ivEdit = view.findViewById(R.id.ivEdit);

        Product product = getItem(position);
        if (product.getStatus() == 3){
            imageView.setVisibility(View.INVISIBLE);
        }
        if (product.getStatus() >= 2){
            ivEdit.setVisibility(View.INVISIBLE);
        }

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("EDIT PRODUCT DETAILS");
                View v = LayoutInflater.from(context).inflate(R.layout.dialog, parent, false);
                dialog.setView(v);
                TextInputEditText productName = v.findViewById(R.id.tietProductName);
                TextInputEditText productPrice = v.findViewById(R.id.tietProductPrice);
                productName.setText(product.getName());
                productPrice.setText("" + product.getPrice());
                dialog.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = productName.getText().toString().trim().toUpperCase();
                        int price = Integer.parseInt(productPrice.getText().toString().trim());
                        if (name.isEmpty()){
                            Toast.makeText(context, "Kindly Enter Product Name", Toast.LENGTH_SHORT).show();
                        }
                        if (price <= 0){
                            Toast.makeText(context, "Kindly Enter Product Price", Toast.LENGTH_SHORT).show();
                        }
                        product.setName(name);
                        product.setPrice(price);
                        database.open();
                        database.updateProduct(product);
                        product.setName(name);
                        product.setPrice(price);
                        notifyDataSetChanged();
                        database.close();
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        productName.setText(product.getName());
        productPrice.setText("" + product.getPrice());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.open();
                product.setStatus(product.getStatus() + 1);
                database.updateProduct(product);
                database.close();
                remove(product);
                notifyDataSetChanged();
            }
        });

        return view;
    }


}
