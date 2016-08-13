package org.aleajactaest.rssreader.util;

import java.util.HashSet;
import java.util.Set;

/**
 * アプリケーションの定数
 *
 * @author Jean-Francois Yoteau
 */
public class Constants {

    /**
     * デフォルトのタイトルの最大桁数
     */
    public static int DEFAULT_TITLE_MAX_LENGTH = 10;

    /**
     * デフォルトの本文の最大桁数
     */
    public static int DEFAULT_BODY_MAX_LENGTH = 30;

    /**
     * デフォルトURL一覧
     */
    public static final Set<String> DEFAULT_URLS;

    static {
        // デフォルトURL一覧を設定する。
        DEFAULT_URLS = new HashSet<>(1);
        DEFAULT_URLS.add("http://tech.uzabase.com/rss");
    }

    /**
     * コンストラクタ
     *
     * クラスのインスタンスを不可能する為。
     */
    private Constants() {
        // 何もしない。
    }

}
