package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.util.AlbumUtils;

public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final OnItemClickListener itemClickListener;

    private ImageView imageView;
    private TextView durationTextView;
    private int itemSize;

    public VideoViewHolder(View itemView, int itemSize, OnItemClickListener itemClickListener) {
        super(itemView);
        this.itemSize = itemSize;
        itemView.getLayoutParams().height = itemSize;
        this.itemClickListener = itemClickListener;
        this.imageView = itemView.findViewById(com.yanzhenjie.album.R.id.iv_album_content_image);
        this.durationTextView = itemView.findViewById(com.yanzhenjie.album.R.id.tv_duration);
        itemView.setOnClickListener(this);
    }

    public void setData(AlbumFile albumFile){
        Album.getAlbumConfig().
                getAlbumLoader().
                loadAlbumFile(imageView, albumFile, itemSize, itemSize);
        durationTextView.setText(AlbumUtils.convertDuration(albumFile.getDuration()));
    }

    @Override
    public void onClick(View view) {

    }
}
