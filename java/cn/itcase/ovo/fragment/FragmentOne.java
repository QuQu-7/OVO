package cn.itcase.ovo_ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.jaeger.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;


public class FragmentOne extends androidx.fragment.app.Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private final String mp4_a = "http://42.159.72.121/video/test1.mp4";
    private final String mp4_b = "http://42.159.72.121/video/cat.mp4";
    private List<Video_Bean> list;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private VideoAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    //控制滚动播放
    ScrollCalculatorHelper scrollCalculatorHelper;

//    private ImageView iv_like;
//    private TextView tv_like;
//    private ImageView iv_comment;
//    int flag = 0;
//    int int_like=0;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null);

        recyclerView = view.findViewById(R.id.video_list1);

//        iv_like=view.findViewById(R.id.home1_iv_like);
//        iv_comment=view.findViewById(R.id.home1_iv_message);
//        tv_like=view.findViewById(R.id.home1_tv_like);

        refreshLayout = view.findViewById(R.id.refreshLayout1);
        recyclerView = view.findViewById(R.id.video_list1);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.colorAccent));
//        iv_like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch(flag){
//                    case 0:
//                        v.setActivated(true);
//                        flag=1;
//                        int_like=Integer.valueOf(tv_like.getText().toString()) + 1;
//                        tv_like.setText(new String(String.valueOf(int_like)));
//                        break;
//                    case 1:
//                        v.setActivated(false);
//                        flag=0;
//                        int_like=Integer.valueOf(tv_like.getText().toString()) - 1;
//                        tv_like.setText(new String(String.valueOf(int_like)));
//                        break;
//                }
//
//            }
//        });
//
//        iv_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),MainActivity.class);
//                startActivity(intent);
//            }
//        });

        initData();
        init();
        initRefreshLayout();
        initRecyclerView();

    }

    //上拉加载更多的东西写这里
    //现在是总共有40行，每10行刷新一次，一直到40行结束会显示“没有更多数据了”
    //40也可以改，改成多少都可以
    //建议把url（网页链接）设置成“test1""test2"等等，这样比较好写for循环
    private void initData() {
        //视频数据
        list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
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

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new VideoAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private List<Video_Bean> getDatas(final int firstIndex, final int lastIndex) {
        List<Video_Bean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < list.size()) {
                resList.add(list.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<Video_Bean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    //下拉刷新的东西写这里
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
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
