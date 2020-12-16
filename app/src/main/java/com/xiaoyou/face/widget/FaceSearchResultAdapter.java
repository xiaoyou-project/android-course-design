package com.xiaoyou.face.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xiaoyou.face.R;
import com.xiaoyou.face.faceserver.CompareResult;
import com.xiaoyou.face.faceserver.FaceServer;

import java.io.File;
import java.util.List;

/**
 * 人脸搜索显示的adapter
 * @author 小游
 * @date 2020/11/16
 */
public class FaceSearchResultAdapter extends RecyclerView.Adapter<FaceSearchResultAdapter.CompareResultHolder> {
    private final List<CompareResult> compareResultList;
    private final LayoutInflater inflater;

    public FaceSearchResultAdapter(List<CompareResult> compareResultList, Context context) {
        inflater = LayoutInflater.from(context);
        this.compareResultList = compareResultList;
    }


    @NonNull
    @Override
    public CompareResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 这里我们查找控件并注册
        View itemView = inflater.inflate(R.layout.recycler_item_search_result, null, false);
        CompareResultHolder compareResultHolder = new CompareResultHolder(itemView);
        compareResultHolder.textView = itemView.findViewById(R.id.tv_item_name);
        compareResultHolder.textNo = itemView.findViewById(R.id.tv_item_no);
        compareResultHolder.textBelieve = itemView.findViewById(R.id.tv_item_believe);
        compareResultHolder.imageView = itemView.findViewById(R.id.iv_item_head_img);
        return compareResultHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CompareResultHolder holder, int position) {
        if (compareResultList == null) {
            return;
        }

        // 这里我们加载图片并显示(我们这里使用学号来存储图片)
        File imgFile = new File(FaceServer.ROOT_PATH + File.separator + FaceServer.SAVE_IMG_DIR + File.separator + compareResultList.get(position).getId() + FaceServer.IMG_SUFFIX);
        Glide.with(holder.imageView)
                .load(imgFile)
                .into(holder.imageView);

        // 这里我们显示学号和姓名
        holder.textView.setText("姓名:"+compareResultList.get(position).getUserName());
        holder.textNo.setText("学号:"+compareResultList.get(position).getUserNo());
        holder.textBelieve.setText("相似度:"+compareResultList.get(position).getSimilar());
    }

    @Override
    public int getItemCount() {
        return compareResultList == null ? 0 : compareResultList.size();
    }

    static class CompareResultHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textNo;
        TextView textBelieve;
        ImageView imageView;
        CompareResultHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
