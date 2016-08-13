package org.aleajactaest.rssreader.manager;

import org.aleajactaest.rssreader.model.RssModel;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * RSSの管理者
 *
 * @author Jean-Francois Yoteau
 */
public class RssManager {

    /**
     * HTTPクライアント
     */
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * コンストラクタ
     *
     * クラスのインスタンスを不可能する為。
     */
    private RssManager() {
        // 何もしない。
    }

    /**
     * RSSモデルを取得する。
     *
     * @param url URL
     * @return RSSモデル
     * @throws IOException
     * @throws RssParsingException
     */
    public static RssModel getRss(String url) throws IOException, RssParsingException {
        // URLの本文を取得する。
        final String body = getUrlBody(url);

        // RSSを構文解析する。
        final RssModel model = parseRss(body);

        return model;
    }

    /**
     * URLの本文を取得する。
     * @param url URL
     * @return URLの本文
     * @throws IOException エラーが発生される場合
     */
    private static String getUrlBody(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        final Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * RSSを構文解析する。
     *
     * @param data 構文解析するデータ
     * @return RSSモデル
     * @throws RssParsingException データが正しくない場合
     */
    private static RssModel parseRss(String data) throws RssParsingException {
        final Serializer serializer = new Persister();
        final RssModel rssModel;
        try {
            rssModel = serializer.read(RssModel.class, data, false);
        } catch (Exception e) {
            throw new RssParsingException("RSS構文解析が失敗です", e);
        }
        return rssModel;
    }

}
