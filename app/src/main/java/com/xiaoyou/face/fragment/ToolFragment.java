package com.xiaoyou.face.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.xiaoyou.face.R;
import com.xiaoyou.face.activity.CameraActivity;
import com.xiaoyou.face.activity.RegisterAndRecognizeActivity;
import com.xiaoyou.face.activity.SearchActivity;
import com.xiaoyou.face.activity.StatisticsActivity;
import com.xiaoyou.face.adapter.FunctionAdapter;
import com.xiaoyou.face.databinding.FragmentIndexBinding;
import com.xiaoyou.face.databinding.FragmentToolBinding;
import com.xiaoyou.face.engine.GlideEngine;
import com.xiaoyou.face.model.Channel;
import com.xiaoyou.face.utils.FaceRead;
import com.xiaoyou.face.utils.ToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 *  工具界面点击效果
 * @author 小游
 */
public class ToolFragment extends Fragment {
    private FragmentToolBinding binding;

    private static final int REQUEST_CODE = 45;
    private SuperButton faceInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 这里我们使用bindview 来进行视图绑定
        binding = FragmentToolBinding.inflate(inflater, container, false);
        initView();
        // 返回视图view
        return binding.getRoot();
    }

    /**
     * 布局初始化
     */
    private void initView(){
        // 9宫格布局初始化
        ArrayList<Channel> channelList = new ArrayList<>();
        channelList.add(new Channel(R.mipmap.face,"人脸录入"));
        channelList.add(new Channel(R.mipmap.statistics,"考勤统计"));
        channelList.add(new Channel(R.mipmap.search,"数据查询"));
        binding.toolList.setAdapter(new FunctionAdapter(channelList,getContext()));
        // grad 布局点击事件监听
        binding.toolList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    startActivity(new Intent(getContext(), RegisterAndRecognizeActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(getContext(), StatisticsActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(getContext(), SearchActivity.class));
                    break;
                default:
                    break;
            }
        });
    }


    /**
     * 拍照或者选择照片的回调事件
     * @param requestCode  requestCode
     * @param resultCode  resultCode
     * @param data data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            ToastUtils.success("图片路径"+resultPhotos.get(0).path);
            // 禁用上传按钮
            faceInput.setEnabled(false);
            try {
                FaceRead.checkFace(resultPhotos.get(0).path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("xiaoyou",resultPhotos.toString());
        }
    }

}