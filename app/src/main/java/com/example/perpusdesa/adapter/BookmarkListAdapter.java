package com.example.perpusdesa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.perpusdesa.R;
import com.example.perpusdesa.model.Bookmark;
import com.example.perpusdesa.ui.detail.DetailBookActivity;

import java.util.ArrayList;
import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.BookmarkViewHolder> {
    private Context context;
    private List<Bookmark> bookmarkList;
    private OnBookmarkClickListener onBookmarkClickListener;

    SharedPreferences sharedPreferences;

    public interface OnBookmarkClickListener {
        void onDeleteBookmark(Bookmark bookmark);
    }

    public BookmarkListAdapter(Context context) {
        this.context = context;
        this.bookmarkList = new ArrayList<>();
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        bookmarkList = bookmarks;
        notifyDataSetChanged();
    }

    public void setOnBookmarkClickListener(OnBookmarkClickListener listener) {
        this.onBookmarkClickListener = listener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.titleTextView.setText(bookmark.getTitle());
        String urlImage = bookmark.getUrl();
//        holder.urlTextView.setText(urlImage);

        ViewTarget<ImageView, Drawable> into = Glide.with(context)
                .load(urlImage)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.urlImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookmark bookmark = bookmarkList.get(position);
                String desc = bookmark.getDescription();
                Intent intent = new Intent(context, DetailBookActivity.class);
                intent.putExtra("title", bookmark.getTitle());
                intent.putExtra("img", bookmark.getUrl());
                intent.putExtra("pdf", bookmark.getUrl_image());
                intent.putExtra("desc", desc);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, urlTextView, descriptionTextView;
        ImageView urlImageView;
        ImageView deleteImageView;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
//            urlTextView = itemView.findViewById(R.id.urlTextView);
            urlImageView = itemView.findViewById(R.id.urlImageView);
//            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Bookmark bookmark = bookmarkList.get(position);
                        String title = bookmark.getTitle();
                        sharedPreferences = context.getSharedPreferences("SavedIds", Context.MODE_PRIVATE);
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(title).apply();
                        if (onBookmarkClickListener != null) {
                            onBookmarkClickListener.onDeleteBookmark(bookmark);
                        }
                    }
                }
            });
        }
    }

}
