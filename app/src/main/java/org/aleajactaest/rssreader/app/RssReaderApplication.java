package org.aleajactaest.rssreader.app;

import android.app.Application;

/**
 * @author Jean-Francois Yoteau
 */
public class RssReaderApplication extends Application {

    private static RssReaderApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    /**
     * インスタンスを取得する。
     *
     * @return インスタンス
     */
    public static RssReaderApplication getInstance() {
        return instance;
    }

}
