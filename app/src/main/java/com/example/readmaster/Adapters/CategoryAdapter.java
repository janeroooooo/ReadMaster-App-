package com.example.readmaster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readmaster.Models.CategoryModels;
import com.example.readmaster.R;
import com.example.readmaster.ShowPdfActivity;
import com.example.readmaster.databinding.RvCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{

    Context context;
    ArrayList<CategoryModels>list;

    public CategoryAdapter(Context context, ArrayList<CategoryModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_category,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CategoryModels model = list.get(position);
        holder.binding.categoryName.setText(model.getTitle());

        Picasso.get()
                .load(model.getImage())
                .placeholder(R.drawable.app_icon)
                .into(holder.binding.categoryimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowPdfActivity.class);
                intent.putExtra("url",model.getPdf());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        RvCategoryBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvCategoryBinding.bind(itemView);
        }

    }
}
