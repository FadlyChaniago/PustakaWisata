package com.example.perpusdesa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.perpusdesa.R;
import com.example.perpusdesa.model.PepusModel;
import com.example.perpusdesa.ui.detail.DetailBookActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private Context context;
    private List<PepusModel> perpusList;
    private ItemClickListener clickListener;
    private List<PepusModel> pepusModelList = new ArrayList<>();

    public void setFilteredList(List<PepusModel> filteredList){
        this.perpusList = filteredList;
        notifyDataSetChanged();
    }

    public HomeListAdapter(Context context, List<PepusModel> perpusList, ItemClickListener clickListener) {
        this.context = context;
        this.perpusList = perpusList;
        this.clickListener = clickListener;

    }

    public void setPerpusList(List<PepusModel> perpusList) {
        this.perpusList = perpusList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(this.perpusList.get(position).getTitle().toString());
        holder.tvId.setText(this.perpusList.get(position).getId().toString());
        holder.tvAuthor.setText(this.perpusList.get(position).getAuthor().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBookActivity.class);
                intent.putExtra("id", perpusList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("pdf", perpusList.get(holder.getAdapterPosition()).getPdfUrl());
                intent.putExtra("img", perpusList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("desc", perpusList.get(holder.getAdapterPosition()).getDesciption());
                intent.putExtra("title", perpusList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("penulis",perpusList.get(holder.getAdapterPosition()).getAuthor());
                intent.putExtra("category",perpusList.get(holder.getAdapterPosition()).getCategory());
                context.startActivity(intent);
                clickListener.onPerpusClick(perpusList.get(position));
            }

        });
        ViewTarget<ImageView, Drawable> into = Glide.with(context)
                .load(this.perpusList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        if(this.perpusList != null) {
            return this.perpusList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView tvTitle, tvId, tvDes, tvAuthor;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.titleView);
            tvId = (TextView)itemView.findViewById(R.id.id);
            tvDes = (TextView)itemView.findViewById(R.id.deskripsi);
            tvAuthor = (TextView)itemView.findViewById(R.id.penulis);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);

        }
    }


    public interface ItemClickListener{
        public void onPerpusClick(PepusModel pepusModel);
    }
}
