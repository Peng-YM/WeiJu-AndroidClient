package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityCollectionTaskBinding;
import cn.edu.sustc.androidclient.model.data.Task;

public class CollectionTaskActivity extends BaseActivity<CollectionTaskViewModel, ActivityCollectionTaskBinding> {
    private CollectionTaskViewModel viewModel;
    private ActivityCollectionTaskBinding binding;
    private Task task;

    private AlbumAdapter adapter;
    private ArrayList<AlbumFile> albumFiles;

    public static void start(Context context, Task task){
//        if (task.type == COLLECTION){
//            Logger.e("Task type incorrect!");
//        }
        Intent intent = new Intent(context, CollectionTaskActivity.class);
        intent.putExtra("task", task);
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

        this.task = (Task) getIntent().getSerializableExtra("task");

        RecyclerView recyclerView = binding.albumView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        binding.taskTitleTv.setText(task.title);
        binding.fab.setOnClickListener(view -> selectAlbum());
        binding.commitBtn.setOnClickListener(view -> {
            if (albumFiles != null) {
                viewModel.uploadImages(albumFiles).observe(this, resource -> {
                    switch (resource.status){
                        case LOADING:
                            binding.commitBtn.setText(String.format("%.1f%%", resource.data));
                            break;
                        case SUCCESS:
                            binding.commitBtn.setText(getString(R.string.commit_success));
                            break;
                        case ERROR:
                            showAlertDialog(resource.message);
                        default:
                            break;
                    }
                });
            } else {
                showAlertDialog(getString(R.string.alert_nothing_selected));
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collection_task;
    }

    private void selectAlbum() {
        if (adapter == null) {
            int itemSize = (binding.albumView.getWidth()) / 3;
            adapter = new AlbumAdapter(this, itemSize, (view, position) -> previewAlbum(position));
            binding.albumView.setAdapter(adapter);
        }
        Logger.d("Select Images from album");
        Album.image(this)
                .multipleChoice()
                .columnCount(3)
                .selectCount(6)
                .camera(true)
                .checkedList(albumFiles)
                .onResult((requestCode, result) -> {
                    albumFiles = result;
                    adapter.notifyDataSetChanged(albumFiles);
                })
                .start();
    }

    private void previewAlbum(int position) {
        if (albumFiles == null || albumFiles.size() == 0) {
            Toast.makeText(this, "You selected nothing", Toast.LENGTH_LONG).show();
        } else {
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
