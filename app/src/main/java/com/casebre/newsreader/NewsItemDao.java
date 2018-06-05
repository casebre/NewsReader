package com.casebre.newsreader;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;



@Dao
public interface NewsItemDao {

    @Insert
    void insertAll(List<NewsItem> newsItems);

    @Insert(onConflict = REPLACE)
    void insert(NewsItem newsItem);

    @Update
    void update(NewsItem newsItem);

    @Query("SELECT * FROM newsItems")
    List<NewsItem> getAll();

    @Query("SELECT * FROM newsItems WHERE guid = :guid")
    NewsItem getItem(Double guid);

    @Query("UPDATE newsItems set read = :read WHERE guid = :guid")
    void updateRead(Double guid, boolean read);

}
