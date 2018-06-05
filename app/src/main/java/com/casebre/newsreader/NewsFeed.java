package com.casebre.newsreader;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;



@Root(name="rss", strict=false)
public class NewsFeed {

    @Element(name="title")
    @Path("channel")
    private String title;

    @Element(name="link")
    @Path("channel")
    private String link;

    @Element(name="description")
    @Path("channel")
    private String description;

    @ElementList(name="item", inline=true)
    @Path("channel")
    private List<NewsItem> newsItemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }


}
