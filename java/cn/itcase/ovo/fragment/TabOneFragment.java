package cn.itcase.ovo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cn.itcase.ovo.R;

public class TabOneFragment extends androidx.fragment.app.Fragment {

    private ArrayList<Fragment> fragments;
    private FragmentTabAdapter tabAdapter;
    private TextView tvTabOne;
    private TextView tvTabTwo;

    private LinearLayout llTabOne;
    private LinearLayout llTabTwo;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_guide, null);

        tvTabOne=view.findViewById(R.id.top_tv_tab_one);
        tvTabTwo=view.findViewById(R.id.top_tv_tab_two);

        llTabOne=view.findViewById(R.id.top_ll_tab_one);
        llTabTwo=view.findViewById(R.id.top_ll_tab_two);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragments = new ArrayList<>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());


        tabAdapter = new FragmentTabAdapter(getActivity(), fragments, R.id.top_fl_layout);

        /**
         *  底部导航栏的点击事件
         * @param view
         */

        llTabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAdapter.setCurrentFragment(0);
            }
        });

        llTabTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAdapter.setCurrentFragment(1);
            }
        });

    }

    protected void initListener() {
        tabAdapter.setOnTabChangeListener(new FragmentTabAdapter.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int index) {
                tvTabOne.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabTwo.setTextColor(getResources().getColor(R.color.color_gray));

                switch (index){
                    case 0:
                        tvTabOne.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 1:
                        tvTabTwo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;

                }

            }
        });

    }
}