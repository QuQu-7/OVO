package cn.itcase.ovo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List_Video_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Video_Bean> list;

    public static final String TAG = "ListNormalAdapter22";

    private GSYVideoOptionBuilder gsyVideoOptionBuilder;


    public List_Video_Adapter(Context context,List<Video_Bean> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_video_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final ViewHolder vh = (ViewHolder) holder;

        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");
        //配置视频播放器参数
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(list.get(position).getUrl())
                .setVideoTitle(list.get(position).getTitle())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setReleaseWhenLossAudio(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!vh.standardGSYVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            //GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        //GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        vh.standardGSYVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                    }
                }).build(vh.standardGSYVideoPlayer);

        //设置返回键
        vh.standardGSYVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        vh.standardGSYVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.standardGSYVideoPlayer.startWindowFullscreen(context, false, true);
            }
        });
//实现第一个视频自动播放
        if(position==0){
            vh.standardGSYVideoPlayer.startPlayLogic();
        }

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private StandardGSYVideoPlayer standardGSYVideoPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            standardGSYVideoPlayer = itemView.findViewById(R.id.list_video_player);

        }


    }

}