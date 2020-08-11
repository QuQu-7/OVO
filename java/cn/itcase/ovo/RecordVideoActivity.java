package cn.itcase.ovo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;

import cn.itcase.ovo.act.PostActivity;

public class RecordVideoActivity extends AppCompatActivity {

    private JCameraView mJcvVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        initView();
        initListener();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mJcvVideo.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mJcvVideo.onPause();
    }

    private void initView() {
        mJcvVideo = findViewById(R.id.jcv_video);
    }

    private void initListener() {
        mJcvVideo.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
                Log.i("CJT", "open camera error");
            }

            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                Log.i("CJT", "AudioPermissionError");
            }
        });
        mJcvVideo.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("CJT", "bitmap = " + bitmap);
//                MainActivity.update(bitmap);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
                Log.i("CJT", "url = " + url);
                Intent intent = new Intent(
                        RecordVideoActivity.this,
                        ShowVideo.class);
                intent.putExtra("path", url);
                startActivityForResult(intent, 0);
                finish();
            }
        });
        mJcvVideo.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mJcvVideo.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(RecordVideoActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
        //设置视频保存路径

        mJcvVideo.setSaveVideoPath(getApplicationContext().getExternalCacheDir()+"/video");
        String videopath = getApplicationContext().getExternalCacheDir()+"/video";
        Intent intent = new Intent();
        intent.setClass(RecordVideoActivity.this, PostActivity.class);
        intent.putExtra("videopath",videopath);
        startActivity(intent);
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        mJcvVideo.setFeatures(JCameraView.BUTTON_STATE_BOTH
        );
        //设置视频质量
        mJcvVideo.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);



    }

}
