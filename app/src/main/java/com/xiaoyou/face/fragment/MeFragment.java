package com.xiaoyou.face.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.xiaoyou.face.R;
import com.xiaoyou.face.databinding.FragmentMeBinding;
import com.xiaoyou.face.model.Login;
import com.xiaoyou.face.service.SQLiteHelper;
import com.xiaoyou.face.service.Service;
import com.xiaoyou.face.service.StudentInfoTO;
import com.xiaoyou.face.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 查询界面
 * @author 小游
 */
public class MeFragment extends Fragment {

    private FragmentMeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 这里我们使用bindview 来进行视图绑定
        binding = FragmentMeBinding.inflate(inflater, container, false);
        initView();
        // 返回视图view
        return binding.getRoot();
    }

    /**
     * 视图初始化
     */
    private void initView(){
        // 搜索按钮点击事件
        binding.search.setOnClickListener(v->{
            int type = binding.searchType.getSelectedIndex();
            String key =binding.editKey.getEditValue();
            // todo 点击搜索把键盘隐藏还有监听键盘回车事件
            Service service = new SQLiteHelper(getContext());
            List<StudentInfoTO> list = new ArrayList<>();
            try {
                list = service.queryStudentInfo(type==0?key:"", type==1?key:"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Login> lists = new ArrayList<>();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (StudentInfoTO studentInfo : list) {
                lists.add(new Login(studentInfo.getStuId(),studentInfo.getName(),sdf.format(studentInfo.getDateTime())));
            }
            // 表格显示数据
            showDetail(lists);
            ToastUtils.success(type+key);
        });
    }


    /**
     * 显示签到的详情情况
     */
    private void showDetail(List<Login> list){
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
//        list.add(new Login("1806040103","小游","2020/12/16 20:21:20"));
        // 设置缩放
//        binding.table.setZoom(true);
        // 显示数据
        binding.table.setData(list);
        // 统计行
        binding.table.getConfig().setFixedTitle(true);
        // 格式化内容背景(这里我们设置背景交替)
        binding.table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                int color = R.color.white;
                if (cellInfo.row%2 ==0){
                    color = R.color.tab_background;
                }
                return ContextCompat.getColor(Objects.requireNonNull(getContext()), color);
            }
        });
        // 隐藏顶部的序号列
        binding.table.getConfig().setShowXSequence(false);
//                setContentBackgroundFormat(new BaseBackgroundFormat<CellInfo>() {
//            @Override
//            public int getBackGroundColor() {
//                return ContextCompat.getColor(AnnotationModeActivity.this,R.color.content_bg);
//            }
//            @Override
//            public boolean isDraw(CellInfo cellInfo) {
//                return cellInfo.position%2 ==0;
//            }
//        });

    }
}