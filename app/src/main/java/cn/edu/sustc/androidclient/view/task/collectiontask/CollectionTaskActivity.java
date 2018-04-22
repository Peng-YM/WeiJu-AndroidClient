package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.util.AlbumUtils;
import com.yanzhenjie.album.util.DisplayUtils;
import com.yanzhenjie.album.widget.divider.Divider;

import java.util.ArrayList;
import java.util.Collections;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityCollectionTaskBinding;

public class CollectionTaskActivity extends BaseActivity<CollectionTaskViewModel, ActivityCollectionTaskBinding> {
    private CollectionTaskViewModel viewModel;
    private ActivityCollectionTaskBinding binding;

    private AlbumAdapter adapter;
    private ArrayList<AlbumFile> albumFiles;

    public static void start(Context context){
        Intent intent = new Intent(context, CollectionTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<CollectionTaskViewModel> getViewModel() {
        return CollectionTaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, CollectionTaskViewModel viewModel, ActivityCollectionTaskBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;

        RecyclerView recyclerView = binding.albumView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Divider divider = AlbumUtils.getDivider(Color.WHITE);
        int itemSize =(DisplayUtils.sScreenWidth - (divider.getWidth() * 4)) / 3;
        adapter = new AlbumAdapter(this, itemSize, (view, position) -> previewAlbum(position));

        recyclerView.setAdapter(adapter);

        binding.startCollectionTask.setOnClickListener(view -> selectAlbum());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collection_task;
    }

    private void selectAlbum(){
        Album.album(this)
                .multipleChoice()
                .requestCode(200)
                .columnCount(3)
                .selectCount(6)
                .camera(true)
                .cameraVideoLimitBytes(20 * 10 ^ 6) //20 mb
                .checkedList(albumFiles)
                .onResult((requestCode, result) -> {
                    albumFiles = result;
                    adapter.notifyDataSetChanged(albumFiles);
                })
                .start();
    }

    private void previewAlbum(int position){
        if (albumFiles == null || albumFiles.size() == 0){
            Toast.makeText(this, "You selected nothing", Toast.LENGTH_LONG).show();
        }else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(albumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("选择图片")
                                    .build()
                    )
                    .onResult((requestCode, result) -> {
                        albumFiles = result;
                        adapter.notifyDataSetChanged(albumFiles);
                    })
                    .start();
        }
    }
}
