package com.casebre.newsreader;

import android.app.Application;
import android.arch.persistence.room.Room;



public class NewsReader extends Application {
    private NewsItemDatabase db;

    public NewsItemDatabase getDb() {
        if(db == null)
            db = Room.databaseBuilder(
                    getApplicationContext(),
                    NewsItemDatabase.class,
                    "NewsReader.db").build();

        return db;
    }
}
