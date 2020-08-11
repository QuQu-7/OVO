package cn.itcase.ovo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.itcase.ovo.R;
import cn.itcase.ovo.act.MainActivity;
import cn.itcase.ovo.act.SetupActivity;
import cn.itcase.ovo.utils.HttpConnectionUtils;
import cn.itcase.ovo.utils.MyImageView;
import cn.itcase.ovo.utils.StreamChangeStrUtils;

public class TabFiveFragment extends androidx.fragment.app.Fragment {

    private Button setup;

    private ArrayList<Fragment> fragments;
    private FragmentTabAdapter tabAdapter;
    private String sexstr ="男";
    private TextView codename;
    private TextView introduction;

    private TextView tvTabOne;
    private TextView tvTabTwo;
    private TextView tvTabThree;
    private ImageView imageView;
    private MyImageView mySexView;

    private LinearLayout llTabOne;
    private LinearLayout llTabTwo;
    private LinearLayout llTabThree;
    private ImageView mImageView;
    private String codenamestr;
    private String introductionstr;

    private final int CODENAMESUCCESS=1;
    private final int INFOSUCCESS=2;
    private final int SEXSUCCESS=3;
    private final int DATANOTFOUND=4;
    private final int DATAEXCEPT=5;




    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case CODENAMESUCCESS:
                    codenamestr = (String)msg.obj;
                    //Toast.makeText(getFragmentContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case INFOSUCCESS:
                    introductionstr = (String)msg.obj;
                    //Toast.makeText(getFragmentContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case SEXSUCCESS:
                    sexstr = (String)msg.obj;
                    //Toast.makeText(getFragmentContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case DATANOTFOUND:
                    //Toast.makeText(getFragmentManager(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case DATAEXCEPT:
                    //Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };








    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = this.getLayoutInflater().inflate(R.layout.center_guide, null);

        new Thread(network).start();
        setup=view.findViewById(R.id.me_setting);
        imageView=view.findViewById(R.id.me_touxiang);
        tvTabOne=view.findViewById(R.id.c_tv_tab_one);
        tvTabTwo=view.findViewById(R.id.c_tv_tab_two);
        tvTabThree=view.findViewById(R.id.c_tv_tab_three);

        llTabOne=view.findViewById(R.id.c_ll_tab_one);
        llTabTwo=view.findViewById(R.id.c_ll_tab_two);
        llTabThree=view.findViewById(R.id.c_ll_tab_three);

        final MyImageView myImageView = (MyImageView) view.findViewById(R.id.me_touxiang);
        myImageView.setImageURL("http://42.159.72.121/icon/84test.jpg");

        codename = view.findViewById(R.id.me_username);
        introduction = view.findViewById(R.id.me_introduce);
        mySexView = (MyImageView) view.findViewById(R.id.me_sex);

        return view;
    }
        Runnable network = new Runnable() {
    private HttpURLConnection connection;
    @Override
    public void run() {
            try {//获得用户昵称
                String data = "username=" + URLEncoder.encode(MainActivity.getusernameStr(), "utf-8") + "&sign=" + URLEncoder.encode("5", "utf-8");
                connection = HttpConnectionUtils.getPersonInfo(data);
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);
                    Log.i("codename",str);
                    Message message = Message.obtain();
                    message.obj = str;
                    message.what = CODENAMESUCCESS;
                    handler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = DATANOTFOUND;
                    message.obj = "网络连接失败...请稍后再试";
                    handler.sendMessage(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = DATAEXCEPT;
                message.obj = "服务器异常...请稍后再试";
                handler.sendMessage(message);
            }
            try {//获得用户简介
                String data = "username=" + URLEncoder.encode(MainActivity.getusernameStr(), "utf-8") + "&sign=" + URLEncoder.encode("6", "utf-8");
                connection = HttpConnectionUtils.getPersonInfo(data);
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);
                    Message message = Message.obtain();
                    Log.i("introduction",str);
                    message.obj = str;
                    message.what = INFOSUCCESS;
                    handler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = DATANOTFOUND;
                    message.obj = "网络连接失败...请稍后再试";
                    handler.sendMessage(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = DATAEXCEPT;
                message.obj = "服务器异常...请稍后再试";
                handler.sendMessage(message);
            }
            try {//获得用户性别
                String data = "username=" + URLEncoder.encode(MainActivity.getusernameStr(), "utf-8") + "&sign=" + URLEncoder.encode("7", "utf-8");
                connection = HttpConnectionUtils.getPersonInfo(data);
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);
                    Message message = Message.obtain();
                    Log.i("sex",str);
                    message.obj = str;
                    message.what = SEXSUCCESS;
                    handler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = DATANOTFOUND;
                    message.obj = "网络连接失败...请稍后再试";
                    handler.sendMessage(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = DATAEXCEPT;
                message.obj = "服务器异常...请稍后再试";
                handler.sendMessage(message);
            }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                codename.setText(codenamestr);
                introduction.setText(introductionstr);
                if(sexstr.equals("女")){
                    mySexView.setImageURL("http://42.159.72.121/item/woman.png");}
            }
        });

    }
};


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetupActivity.class);
                startActivity(intent);
            }
        });

        fragments = new ArrayList<>();
        fragments.add(new FragmentOneCenter());
        fragments.add(new FragmentTwoCenter());
        fragments.add(new FragmentThreeCenter());


        tabAdapter = new FragmentTabAdapter(getActivity(), fragments, R.id.c_fl_layout);

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
                tabAdapter.setCurrentFragment(2);
            }
        });


    }

    protected void initListener() {
        tabAdapter.setOnTabChangeListener(new FragmentTabAdapter.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int index) {
                tvTabOne.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabTwo.setTextColor(getResources().getColor(R.color.color_gray));
                tvTabThree.setTextColor(getResources().getColor(R.color.color_gray));

                switch (index){
                    case 0:
                        tvTabOne.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 1:
                        tvTabTwo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        tvTabThree.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;

                }

            }
        });

    }

}