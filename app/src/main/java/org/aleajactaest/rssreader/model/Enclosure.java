package org.aleajactaest.rssreader.model;

import org.simpleframework.xml.Attribute;

/**
 * メディア情報
 *
 * @author Jean-Francois Yoteau
 */
public class Enclosure {

    /**
     * メディアのURL
     */
    @Attribute(name = "url")
    private String url;

    /**
     * メディアのタイプ
     */
    @Attribute(name = "type")
    private String type;

    /**
     * メディアのファイル長さ
     */
    @Attribute(name = "length")
    private long length;

    /**
     * メディアのファイル長さを取得する。
     *
     * @return メディアのファイル長さ
     */
    public long getLength() {
        return length;
    }

    /**
     * メディアのタイプを取得する。
     *
     * @return メディアのタイプ
     */
    public String getType() {
        return type;
    }

    /**
     * メディアのURLを取得する。
     *
     * @return メディアのURL
     */
    public String getUrl() {
        return url;
    }

}
