package org.aleajactaest.rssreader.model;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * RSSモデル
 *
 * @author Jean-Francois Yoteau
 */
@Root(name = "rss")
public class RssModel {

    /**
     * チャネル
     */
    @Element(name = "channel")
    private Channel channel;

    /**
     * チャネルを取得する。
     *
     * @return チャネル
     */
    @NonNull
    public Channel getChannel() {
        return channel;
    }

}
