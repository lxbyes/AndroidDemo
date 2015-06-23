package me.leckie.demo.bean;

import java.io.Serializable;

/**
 * @author leckie
 * @date 6/23/15
 */
public class NewsBean implements Serializable {

    private String newsIconUrl;

    private String newsTitle;

    private String newsContent;

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
