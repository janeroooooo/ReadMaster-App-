package com.example.readmaster;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.readmaster.Adapters.CategoryAdapter;
import com.example.readmaster.Models.CategoryModels;
import com.example.readmaster.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;

    ArrayList<CategoryModels> list;
    CategoryAdapter adapter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
        }
        dialog.show();

        list = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.rvCategory.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter(this,list);
        binding.rvCategory.setAdapter(adapter);

        database.getReference().child("categories").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    list.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CategoryModels model = dataSnapshot.getValue(CategoryModels.class);
                        model.setKey(dataSnapshot.getKey());
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {

                    Toast.makeText(MainActivity.this, "Category does not exist", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}