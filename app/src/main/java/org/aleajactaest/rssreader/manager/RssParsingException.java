package org.aleajactaest.rssreader.manager;

/**
 * RSS構文解析例外
 *
 * @author Jean-Francois Yoteau
 */
public class RssParsingException extends Exception {

    /**
     * コンストラクタ。
     *
     * @param message メッセージ
     * @param cause 理由
     */
    public RssParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}
