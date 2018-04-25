package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.impl.OnItemClickListener;

import java.util.List;

import cn.edu.sustc.androidclient.R;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private int itemSize;
    private OnItemClickListener itemClickListener;
    private List<AlbumFile> albumFiles;

    public AlbumAdapter(Context context, int itemSize, OnItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.itemSize = itemSize;
        this.itemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
        this.albumFiles = imagePathList;
        for (AlbumFile file : imagePathList) {
            Logger.d("Selected File: %s", file.getPath());
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        AlbumFile albumFile = albumFiles.get(position);
        return albumFile.getMediaType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case AlbumFile.TYPE_IMAGE: {
                return new ImageViewHolder(inflater.inflate(R.layout.item_content_image, parent, false), itemSize, itemClickListener);
            }
            case AlbumFile.TYPE_VIDEO: {
                // TODO
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case AlbumFile.TYPE_IMAGE: {
                ((ImageViewHolder) holder).setData(albumFiles.get(position));
                break;
            }
            case AlbumFile.TYPE_VIDEO: {
                // TODO
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return albumFiles == null ? 0 : albumFiles.size();
    }
}
