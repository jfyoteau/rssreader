package org.aleajactaest.rssreader.util;

/**
 * 文字列のユーティリティ
 *
 * @author Jean-Francois Yoteau
 */
public final class StringUtils {

    /**
     * コンストラクタ
     */
    private StringUtils() {
        // 何もしない。
    }

    public static String elipsize(String string, int maxLength) {
        if (string.length() > maxLength) {
            return string.substring(0, maxLength);
        }

        return string;
    }

    /**
     * HTMLタグを削除する。
     *
     * @param string 入力文字列
     * @return HTMLタグなし文字列
     */
    public static String removeHtmlTags(String string) {
        return string.replaceAll("<(.*?)>", "");
    }

    /**
     * キャリッジリターンを削除する。
     *
     * @param string 文字列
     * @return キャリッジリターンなし文字列
     */
    public static String removeCarriageReturn(String string) {
        return string.replace("\n", "").replace("\r", "");
    }

}
