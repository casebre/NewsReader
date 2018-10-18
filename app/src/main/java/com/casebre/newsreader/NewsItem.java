package com.casebre.newsreader;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jsoup.Jsoup;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;



@Entity(tableName = "newsItems")
@Root(name = "item", strict = false)
public class NewsItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "guid")
    @Element(name = "guid")
    private Double guid;

    @Element(name = "title")
    private String title;

    @ColumnInfo(name = "link")
    @Element(name = "link")
    private String link;

    @ColumnInfo(name = "description")
    @Element(name = "description")
    private String description;

    @Element(name = "pubDate")
    private String publishedDate;

    @Attribute(name = "url")
    @Path("enclosure")
    private String url;

    @ColumnInfo(name = "read")
    private Boolean read = false;

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

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
        return Jsoup.parse(description).select("p").text();
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUrl() {
        return Jsoup.parse(description).select("img").attr("src");
    }
    public void setUrl(String url) { this.url = url; }

    public Double getGuid() {
        return guid;
    }
    public void setGuid(Double guid) {
        this.guid = guid;
    }

    public Boolean getRead() {
        return read;
    }
    public void setRead(Boolean read) {
        this.read = read;
    }
}
