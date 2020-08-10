package cn.itcase.ovo.act;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import cn.itcase.ovo.R;
import cn.itcase.ovo.utils.HttpConnectionUtils;
import cn.itcase.ovo.utils.StreamChangeStrUtils;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = null ;
    private Button register;
    private Button logon;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repassword;
    public static String usernameStr = "84test";
    private String passwordStr;
    private String repasswordStr;
    private final int LOGINSUCCESS=0;
    private final int LOGINNOTFOUND=1;
    private final int LOGINEXCEPT=2;
    private final int REGISTERSUCCESS=3;
    private final int REGISTERNOTFOUND=4;
    private final int REGISTEREXCEPT=5;




    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case LOGINSUCCESS:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case LOGINNOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case LOGINEXCEPT:
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
        setContentView(R.layout.activity_login);


        register=(Button)findViewById(R.id.login_register);
        logon=findViewById(R.id.login_log);
        et_username=findViewById(R.id.login_username);
        et_password=findViewById(R.id.login_password);
        et_repassword=findViewById(R.id.login_repassword);

        logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameStr=et_username.getText().toString().trim();
                passwordStr=et_password.getText().toString().trim();
                repasswordStr=et_repassword.getText().toString().trim();
                //获取编辑框内的内容

                //判断是否输入为空（在这里就不再进行正则表达式判断了）
                if(usernameStr.equals("") || passwordStr.equals("")){
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
                }//进行登录操作(联网操作要添加权限)
                else if(repasswordStr.equals(passwordStr) != true ){
                    Toast.makeText(MainActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_LONG).show();
                } else{
                    //联网操作要开子线程，在主线程不能更新UI
                    new Thread(){
                        private HttpURLConnection connection;
                        @Override
                        public void run() {
                            try {
                                //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                                // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                                String data2= "username="+ URLEncoder.encode(usernameStr,"utf-8")+"&password="+ URLEncoder.encode(passwordStr,"utf-8")+"&sign="+URLEncoder.encode("1","utf-8");
                                connection= HttpConnectionUtils.getConnection(data2);
                                int code = connection.getResponseCode();
                                if(code == 200){
                                    InputStream inputStream = connection.getInputStream();
                                    String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                                    Message message = Message.obtain();//更新UI就要向消息机制发送消息
                                    message.what=LOGINSUCCESS;//用来标志是哪个消息
                                    message.obj=str;//消息主体
                                    handler.sendMessage(message);
                                    Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Message message = Message.obtain();
                                    message.what=LOGINNOTFOUND;
                                    message.obj="登录异常...请稍后再试";
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                                e.printStackTrace();
                                Log.e(TAG, Log.getStackTraceString(e));
                                Message message = Message.obtain();
                                message.what=LOGINEXCEPT;
                                message.obj="服务器异常...请稍后再试";
                                handler.sendMessage(message);
                            }
                        }
                    }.start();//不要忘记开线程
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameStr=et_username.getText().toString().trim();
                passwordStr=et_password.getText().toString().trim();
                repasswordStr=et_repassword.getText().toString().trim();
                //获取编辑框内的内容

                //判断是否输入为空（在这里就不再进行正则表达式判断了）
                if(usernameStr.equals("") || passwordStr.equals("")){
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
                }//进行登录操作(联网操作要添加权限)
                else if(repasswordStr.equals(passwordStr) != true ){
                    Toast.makeText(MainActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_LONG).show();
                } else{
                    //联网操作要开子线程，在主线程不能更新UI
                    new Thread(){
                        private HttpURLConnection connection;
                        @Override
                        public void run() {
                            try {
                                String data= "username="+ URLEncoder.encode(usernameStr,"utf-8")+"&password="+ URLEncoder.encode(passwordStr,"utf-8")+"&sign="+URLEncoder.encode("2","utf-8");
                                connection= HttpConnectionUtils.getConnection(data);
                                int code = connection.getResponseCode();
                                if(code == 200){
                                    InputStream inputStream = connection.getInputStream();
                                    String str = StreamChangeStrUtils.toChange(inputStream);
                                    Message message = Message.obtain();
                                    message.obj=str;
                                    message.what=REGISTERSUCCESS;
                                    handler.sendMessage(message);
                                }
                                else {
                                    Message message = Message.obtain();
                                    message.what=REGISTERNOTFOUND;
                                    message.obj="注册异常...请稍后再试";
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Message message = Message.obtain();
                                message.what=REGISTEREXCEPT;
                                message.obj="服务器异常...请稍后再试";
                                handler.sendMessage(message);
                            }
                        }
                    }.start();//不要忘记开线程
                }
            }
        });

    }

    public static String getusernameStr(){
        return usernameStr;
    }
}
