package cn.itcase.ovo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import cn.itcase.ovo.act.PostActivity;

public class ShowVideo extends AppCompatActivity {

    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;
    private Button next;
//    ImageView imageView = new ImageView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        next=findViewById(R.id.showvideo_next);

        init();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowVideo.this, PostActivity.class);
                startActivity(intent);
            }
        });

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageView imageView = new ImageView(ShowVideo.this);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                videoPlayer.setThumbImageView(imageView);
//                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                Bitmap image2= ThumbnailUtils.extractThumbnail(image, 100, 141);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                if (image2 != null) {
//                        image2.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                        byte[] bitmapByte = baos.toByteArray();
//                        startActivity(new Intent(ShowVideo.this, PostActivity.class).putExtra("bitmap", bitmapByte));
//                }
//            }
//        });



    }

    public void init() {
        Intent intent = getIntent();
        videoPlayer =  this.findViewById(R.id.video_player);

        String source1 = (String) intent.getSerializableExtra("path");
        videoPlayer.setUp(source1, true, "预览");

        //澧炲姞灏侀潰
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        videoPlayer.setThumbImageView(imageView);
        //澧炲姞title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //璁剧疆杩斿洖閿�
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //璁剧疆鏃嬭浆
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //璁剧疆鍏ㄥ睆鎸夐敭鍔熻兘,杩欐槸浣跨敤鐨勬槸閫夋嫨灞忓箷锛岃�屼笉鏄叏灞�
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //鏄惁鍙互婊戝姩璋冩暣
        videoPlayer.setIsTouchWiget(true);
        //璁剧疆杩斿洖鎸夐敭鍔熻兘
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
