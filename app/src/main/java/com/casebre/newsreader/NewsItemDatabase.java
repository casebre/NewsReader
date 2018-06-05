package com.casebre.newsreader;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {NewsItem.class}, version = 1)
abstract class NewsItemDatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();
}
