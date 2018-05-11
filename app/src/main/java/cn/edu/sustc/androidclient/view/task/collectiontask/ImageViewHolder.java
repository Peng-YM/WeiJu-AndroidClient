package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.impl.OnItemClickListener;

import cn.edu.sustc.androidclient.R;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final int itemSize;
    private final OnItemClickListener itemClickListener;

    private ImageView imageView;

    public ImageViewHolder(View itemView, int itemSize, OnItemClickListener itemClickListener) {
        super(itemView);
        itemView.getLayoutParams().height = itemSize;

        this.itemSize = itemSize;
        this.itemClickListener = itemClickListener;

        imageView = itemView.findViewById(R.id.iv_album_content_image);
        itemView.setOnClickListener(this);
    }

    public void setData(AlbumFile albumFile) {
        Album.getAlbumConfig()
                .getAlbumLoader()
                .loadAlbumFile(imageView, albumFile, itemSize, itemSize);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
