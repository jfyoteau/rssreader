package org.aleajactaest.rssreader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * RSSのアイテム
 *
 * @author Jean-Francois Yoteau
 */
public class Item {

    /**
     * タイトル
     */
    @Element(name = "title", required = false)
    private String title;

    /**
     * 本文
     */
    @Element(name = "description", required = false)
    private String description;

    /**
     * メディア
     */
    @Element(name = "enclosure", required = false)
    private Enclosure enclosure;

    /**
     * タイトルを取得する。
     *
     * @return タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * 本文を取得する。
     *
     * @return 本文
     */
    public String getDescription() {
        return description;
    }

    /**
     * メディアを取得する。
     *
     * @return
     */
    public Enclosure getEnclosure() {
        return enclosure;
    }

}
