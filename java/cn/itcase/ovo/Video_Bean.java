package cn.itcase.ovo;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Video_Bean implements Serializable {
    /**
     * 默认
     */
    public static final long serialVersionUID = 1L;

    private String url;
    private String title;
    private Drawable bitmap;

    public Video_Bean() {

    }

    public Video_Bean(String url, String title, Drawable bitmap) {

        this.url = url;
        this.bitmap = bitmap;
        this.title = title;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getBitmap() {
        return bitmap;
    }

    public void setBitmap(Drawable bitmap) {
        this.bitmap = bitmap;
    }
}
