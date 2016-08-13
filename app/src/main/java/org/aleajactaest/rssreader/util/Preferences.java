package org.aleajactaest.rssreader.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.aleajactaest.rssreader.app.RssReaderApplication;

import java.util.Set;

/**
 * 環境設定
 *
 * @author Jean-Francois Yoteau
 */
public class Preferences {

    private static final String PREFERENCE_NAME = "rss_reader";

    /**
     * タイトルの最大桁数のキー
     */
    private static final String TITLE_MAX_LENGTH_KEY = "pref_title_max_length";

    /**
     * 本文の最大桁数のキー
     */
    private static final String BODY_MAX_LENGTH_KEY = "pref_body_max_length";

    /**
     * URL一覧のキー
     */
    private static final String URL_LIST_KEY = "pref_url_list";

    /**
     * コンストラクタ
     */
    private Preferences() {
        // 何もしない。
    }

    /**
     * 環境設定を取得する。
     *
     * @return 設定
     */
    private static SharedPreferences getPreferences() {
        return RssReaderApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * タイトルの最大桁数を取得する。
     *
     * @return タイトルの最大桁数
     */
    public static int getTitleMaxLength() {
        return getPreferences().getInt(TITLE_MAX_LENGTH_KEY, Constants.DEFAULT_TITLE_MAX_LENGTH);
    }

    /**
     * 本文の最大桁数を取得する。
     *
     * @return 本文の最大桁数
     */
    public static int getBodyMaxLength() {
        return getPreferences().getInt(BODY_MAX_LENGTH_KEY, Constants.DEFAULT_BODY_MAX_LENGTH);
    }

    /**
     * URL一覧を取得する。
     *
     * @return URL一覧
     */
    public static Set<String> getUrlList() {
        return getPreferences().getStringSet(URL_LIST_KEY, Constants.DEFAULT_URLS);
    }

    /**
     * 環境設定を保存する。
     *
     * @param titleMaxLength タイトルの最大桁数
     * @param bodyMaxLength  本文の最大桁数
     * @param urlList        URL一覧
     */
    public static void setPreferences(int titleMaxLength, int bodyMaxLength, Set<String> urlList) {
        getPreferences().edit()
                .putInt(TITLE_MAX_LENGTH_KEY, titleMaxLength)
                .putInt(BODY_MAX_LENGTH_KEY, bodyMaxLength)
                .putStringSet(URL_LIST_KEY, urlList)
                .apply();
    }

}
