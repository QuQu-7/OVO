package cn.itcase.ovo.act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import cn.itcase.ovo.GuideActivity;
import cn.itcase.ovo.R;
import cn.itcase.ovo.ShowVideo;
import cn.itcase.ovo.utils.HttpConnectionUtils;
import cn.itcase.ovo.utils.StreamChangeStrUtils;

public class PostActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView exit;
    private EditText editText;
    private String tag;
    private String videointro;
    private String filePath;
    private String fileName;
    private Button label_1;
    private Button label_2;
    private Button label_3;
    private Button label_4;
    private Button label_5;
    private Button label_6;
    private Button label_7;
    private Button label_8;
    private Button label_9;
    private Button label_10;
    private Button label_11;
    private Button label_12;
    private Button post;
    private ImageView cover;

    private final int VIDEOSUCCESS=0;
    private final int VIDEONOTFOUND=1;
    private final int VIDEOEXCEPT=2;
    private final int REGISTERSUCCESS=3;
    private final int REGISTERNOTFOUND=4;
    private final int REGISTEREXCEPT=5;




    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case VIDEOSUCCESS:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case VIDEONOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case VIDEOEXCEPT:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERSUCCESS:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERNOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTEREXCEPT:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posttag);
        back=findViewById(R.id.post_back);
        exit=findViewById(R.id.post_exit);
        editText=findViewById(R.id.post_et);
        label_1=findViewById(R.id.label1);
        label_2=findViewById(R.id.label2);
        label_3=findViewById(R.id.label3);
        label_4=findViewById(R.id.label4);
        label_5=findViewById(R.id.label5);
        label_6=findViewById(R.id.label6);
        label_7=findViewById(R.id.label7);
        label_8=findViewById(R.id.label8);
        label_9=findViewById(R.id.label9);
        label_10=findViewById(R.id.label10);
        label_11=findViewById(R.id.label11);
        label_12=findViewById(R.id.label12);
        post=findViewById(R.id.post_btn_ok);
        cover=findViewById(R.id.post_cover);

//        Intent intent = getIntent();
//        if (intent.getByteArrayExtra("bitmap") != null) {
//            byte[] bis = intent.getByteArrayExtra("bitmap");
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
//            cover.setImageBitmap(bitmap);
//
//        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PostActivity.this, ShowVideo.class);
                startActivity(intent1);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PostActivity.this,GuideActivity.class);
                startActivity(intent2);
            }
        });

        label_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_1.getText().toString();
                editText.append(label_1.getText().toString());
            }
        });

        label_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_2.getText().toString();
                editText.append(label_2.getText().toString());
            }
        });

        label_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_3.getText().toString();
                editText.append(label_3.getText().toString());
            }
        });

        label_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_4.getText().toString();
                editText.append(label_4.getText().toString());
            }
        });

        label_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_5.getText().toString();
                editText.append(label_5.getText().toString());
            }
        });

        label_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_6.getText().toString();
                editText.append(label_6.getText().toString());
            }
        });

        label_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_7.getText().toString();
                editText.append(label_7.getText().toString());
            }
        });

        label_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_8.getText().toString();
                editText.append(label_8.getText().toString());
            }
        });

        label_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_9.getText().toString();
                editText.append(label_9.getText().toString());
            }
        });

        label_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_10.getText().toString();
                editText.append(label_10.getText().toString());
            }
        });

        label_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_11.getText().toString();
                editText.append(label_11.getText().toString());
            }
        });

        label_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = label_12.getText().toString();
                editText.append(label_12.getText().toString());
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //濡傛灉鍙戝竷鎴愬姛
                videointro = editText.getText().toString().trim();
                Intent intent = getIntent();
                final String filename = MainActivity.getusernameStr();
                final String videopath = intent.getStringExtra("videopath");
                final String videourl = "http://42.159.72.121/video/"+ filename+".mp4";
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    //toast("上传");
                    //String sdcardPath = Environment.getExternalStorageDirectory()
                           // .getAbsolutePath();
                    filePath = videopath;
                    File file = new File(filePath);
                    // 这里我选的是Abook文件夹下第五个文件上传，得根据自己的实际情况来，否则肯定出错。
                    //fileName = MainActivity.getusernameStr()+".mp4";
                    // int a = file.list().length;
                    fileName = file.list()[0];
                    filePath += fileName;
                    Log.i("file.size", "size=" + file.list().length + "filePath"
                            + filePath);
                } else {
                    //toast("没有内存卡");
                    return;
                }
                new Thread(){
                    private HttpURLConnection connection;
                    @Override
                    public void run() {
                        try {
                            //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                            // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                            String data2= "tag="+ URLEncoder.encode(tag,"utf-8")+"&like="+ URLEncoder.encode(String.valueOf(0),"utf-8")+"&address="+ URLEncoder.encode(videopath,"utf-8")+"&author="+ URLEncoder.encode(filename,"utf-8")+"&videourl="+ URLEncoder.encode(videourl,"utf-8")+"&sign="+URLEncoder.encode("1","utf-8");
                            connection= HttpConnectionUtils.getConnection(data2);
                            int code = connection.getResponseCode();
                            if(code == 200){
                                InputStream inputStream = connection.getInputStream();
                                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                                message.what=VIDEOSUCCESS;//用来标志是哪个消息
                                message.obj=str;//消息主体
                                handler.sendMessage(message);
                            }
                            else {
                                Message message = Message.obtain();
                                message.what=VIDEONOTFOUND;
                                message.obj="网络异常...请稍后再试";
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                            e.printStackTrace();
                           // Log.e(TAG, Log.getStackTraceString(e));
                            Message message = Message.obtain();
                            message.what=VIDEOEXCEPT;
                            message.obj="服务器异常...请稍后再试";
                            handler.sendMessage(message);
                        }
                    }
                }.start();//不要忘记开线程
                Toast.makeText(PostActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                Intent intent_ok = new Intent(PostActivity.this, GuideActivity.class);
                startActivity(intent_ok);
                //濡傛灉鍙戝竷澶辫触
                //Toast.makeText(PostActivity.this,"鍙戝竷澶辫触",Toast.LENGTH_SHORT).show();
            }
        });

    }


}