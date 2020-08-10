package cn.itcase.ovo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import cn.itcase.ovo.fragment.FragmentTabAdapter;
import cn.itcase.ovo.fragment.TabFiveFragment;
import cn.itcase.ovo.fragment.TabFourFragment;
import cn.itcase.ovo.fragment.TabOneFragment;
import cn.itcase.ovo.fragment.TabThreeFragment;
import cn.itcase.ovo.fragment.TabTwoFragment;
import cn.itcase.ovo.utils.ACache;

public class GuideActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private FragmentTabAdapter tabAdapter;
    private TextView tvTabOne;
    private TextView tvTabTwo;
//    private TextView tvTabThree;
    private TextView tvTabFour;
    private TextView tvTabFive;
    private ImageView ivTabOne;
    private ImageView ivTabTwo;
    private ImageView ivTabThree;
    private ImageView ivTabFour;
    private ImageView ivTabFive;
    private LinearLayout llTabOne;
    private LinearLayout llTabTwo;
    private LinearLayout llTabThree;
    private LinearLayout llTabFour;
    private LinearLayout llTabFive;

    public static ACache aCache;
    public static File cache;
    private static ImageView mShowImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_guide);

        tvTabOne=findViewById(R.id.tv_tab_one);
        tvTabTwo=findViewById(R.id.tv_tab_two);
//        tvTabThree=findViewById(R.id.tv_tab_three);
        tvTabFour=findViewById(R.id.tv_tab_four);
        tvTabFive=findViewById(R.id.tv_tab_five);

        ivTabOne=findViewById(R.id.iv_tab_one);
        ivTabTwo=findViewById(R.id.iv_tab_two);
        ivTabThree=findViewById(R.id.iv_tab_three);
        ivTabFour=findViewById(R.id.iv_tab_four);
        ivTabFive=findViewById(R.id.iv_tab_five);

        llTabOne=findViewById(R.id.ll_tab_one);
        llTabTwo=findViewById(R.id.ll_tab_two);
        llTabThree=findViewById(R.id.ll_tab_three);
        llTabFour=findViewById(R.id.ll_tab_four);
        llTabFive=findViewById(R.id.ll_tab_five);



        fragments = new ArrayList<>();
        fragments.add(new TabOneFragment());
        fragments.add(new TabTwoFragment());
        fragments.add(new TabThreeFragment());
        fragments.add(new TabFourFragment());
        fragments.add(new TabFiveFragment());
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_layout);

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

        llTabThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tabAdapter.setCurrentFragment(2);
                Intent intent = new Intent(GuideActivity.this,RecordVideoActivity.class);
                startActivity(intent);
            }
        });

        llTabFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAdapter.setCurrentFragment(3);
            }
        });

        llTabFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAdapter.setCurrentFragment(4);

            }
        });


        open();
        init();

    }


    
    private void init() {
        aCache = ACache.get(this);
//        mShowImg = findViewById(R.id.show_img);
        cache = new File(getApplicationContext().getExternalCacheDir(), "video");
        if (!cache.exists()) {
            cache.mkdirs();
        }

    }

    private void open() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }


    protected void initListener() {
        tabAdapter.setOnTabChangeListener(new FragmentTabAdapter.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int index) {
                tvTabOne.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabTwo.setTextColor(getResources().getColor(R.color.color_gray));
//                tvTabThree.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabFour.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabFive.setTextColor(getResources().getColor(R.color.color_gray));

                ivTabOne.setImageResource(R.mipmap.index);
                ivTabTwo.setImageResource(R.mipmap.location);
                ivTabThree.setImageResource(R.mipmap.add_image);
                ivTabFour.setImageResource(R.mipmap.message);
                ivTabFive.setImageResource(R.mipmap.me);

                switch (index){
                    case 0:
                        tvTabOne.setTextColor(getResources().getColor(R.color.colorPrimary));
                        ivTabOne.setImageResource(R.mipmap.index);
                        break;
                    case 1:
                        tvTabTwo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        ivTabTwo.setImageResource(R.mipmap.location);
                        break;
                    case 2:
//                        tvTabThree.setTextColor(getResources().getColor(R.color.colorPrimary));
                        ivTabThree.setImageResource(R.mipmap.add_image);
                        break;
                    case 3:
                        tvTabFour.setTextColor(getResources().getColor(R.color.colorPrimary));
                        ivTabFour.setImageResource(R.mipmap.message);
                        break;
                    case 4:
                        tvTabFive.setTextColor(getResources().getColor(R.color.colorPrimary));
                        ivTabFive.setImageResource(R.mipmap.me);
                        break;
                }

            }
        });
    }



}
