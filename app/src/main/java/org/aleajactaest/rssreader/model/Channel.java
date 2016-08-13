package org.aleajactaest.rssreader.model;

import android.support.annotation.NonNull;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * RSSのチャネル
 *
 * @author Jean-Francois Yoteau
 */
public class Channel {

    /**
     * アイテム一覧
     */
    @ElementList(name = "item", inline = true, required = false)
    List<Item> items = new ArrayList<>();

    /**
     * アイテム一覧を取得する。
     *
     * @return アイテム一覧
     */
    @NonNull
    public List<Item> getItems() {
        return items;
    }

}
