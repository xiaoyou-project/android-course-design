package com.xiaoyou.face.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyou.face.R;
import com.xiaoyou.face.model.Channel;

import java.util.ArrayList;

/**
 * 九宫格布局的适配器
 * @author 小游
 * @date 2020/12/15
 */
public class FunctionAdapter extends BaseAdapter {

    private final ArrayList<Channel> channelList;
    private final LayoutInflater layoutInflater;

    /**
     * adapter初始化，我们传入一个列表和context对象
     * @param list 数据
     * @param context context对象
     */
    public FunctionAdapter(ArrayList<Channel> list, Context context){
        channelList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置list的数目
     * @return 返回数目
     */
    @Override
    public int getCount() {
        return channelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        // 把view放到布局里面缓存
        if(convertView == null){
            //加载布局
            convertView =layoutInflater.inflate(R.layout.item_grid,null);
            // 缓存布局
            holder = new ViewHolder();
            holder.imgChannel =convertView.findViewById(R.id.img);
            holder.decChannel =convertView.findViewById(R.id.dec);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        //设置图标和文字
        Channel channel = channelList.get(position);
        // 设置图标和文字
        if(channel != null){
            holder.decChannel.setText(channel.getDec());
            holder.imgChannel.setImageResource(channel.getImgId());
        }
        return convertView;
    }

    /**
     * view holder布局
     */
    static class ViewHolder{
        ImageView imgChannel;
        TextView decChannel;
    }

//    /**
//     *
//     */
//    interface OnItemClickListener{
//        void onClick(int posttion);
//    }

}
