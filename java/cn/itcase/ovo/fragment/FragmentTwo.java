package cn.itcase.ovo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;



import com.jaeger.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import cn.itcase.ovo.utils.DpTools;
import cn.itcase.ovo.List_Video_Adapter;
import cn.itcase.ovo.R;
import cn.itcase.ovo.ScrollCalculatorHelper;
import cn.itcase.ovo.Video_Bean;
import cn.itcase.ovo.act.MainActivity;


public class FragmentTwo extends androidx.fragment.app.Fragment {

    private RecyclerView recyclerView;
    private final String mp4_a = "http://42.159.72.121/video/jsxh.mp4";
    private final String mp4_b = "http://42.159.72.121/video/miao.mp4";
    private List<Video_Bean> list;

    //控制滚动播放
    ScrollCalculatorHelper scrollCalculatorHelper;

    private ImageView iv_like;
    private TextView tv_like;
    private ImageView iv_comment;
    int flag = 0;
    int int_like=0;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);

        recyclerView = view.findViewById(R.id.video_list2);

        iv_like=view.findViewById(R.id.home2_iv_like);
        iv_comment=view.findViewById(R.id.home2_iv_message);
        tv_like=view.findViewById(R.id.home2_tv_like);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.colorAccent));

        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),MainActivity.class);
//                startActivity(intent);
                switch(flag) {
                    case 0:
                        v.setActivated(true);
                        flag = 1;
                        int_like=Integer.valueOf(tv_like.getText().toString()) + 1;
                        tv_like.setText(new String(String.valueOf(int_like)));
                        break;
                    case 1:
                        v.setActivated(false);
                        flag = 0;
                        int_like=Integer.valueOf(tv_like.getText().toString()) - 1;
                        tv_like.setText(new String(String.valueOf(int_like)));
                        break;
                }

            }
        });

        iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        initData();
        init();

    }

    private void initData() {
        //视频数据
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Video_Bean video_bean = new Video_Bean();
            if (i % 2 == 0) {
                video_bean.setUrl(mp4_a);
            } else {
                video_bean.setUrl(mp4_b);
            }
            video_bean.setBitmap(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_play));

            list.add(video_bean);
        }

    }

    private void init() {

        List_Video_Adapter list_video_adapter = new List_Video_Adapter(getActivity(), list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //自定播放帮助类 限定范围为屏幕一半的上下偏移180   括号里不用在意 因为是一个item一个屏幕
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.list_video_player
                , dm.heightPixels / 2 - DpTools.dip2px(getActivity(), 180)
                , dm.heightPixels / 2 + DpTools.dip2px(getActivity(), 180));

        //让RecyclerView有ViewPager的翻页效果
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        //设置LayoutManager和Adapter
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(list_video_adapter);
        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //第一个可见视图,最后一个可见视图
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //如果newState的状态==RecyclerView.SCROLL_STATE_IDLE;
                //播放对应的视频
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.e("有几个item", firstVisibleItem + "    " + lastVisibleItem);
                //一屏幕显示一个item 所以固定1
                //实时获取设置 当前显示的GSYBaseVideoPlayer的下标
                scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, 1);

            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        GSYVideoManager.onPause();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        GSYVideoManager.releaseAllVideos();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {


        Configuration mConfiguration = this.getResources().getConfiguration();
        int ori = mConfiguration.orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }

        super.onConfigurationChanged(newConfig);
    }


}
