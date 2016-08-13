package org.aleajactaest.rssreader.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.aleajactaest.rssreader.R;
import org.aleajactaest.rssreader.model.Enclosure;
import org.aleajactaest.rssreader.model.Item;
import org.aleajactaest.rssreader.util.Preferences;
import org.aleajactaest.rssreader.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RSSのRecyclerViewアダプター
 *
 * @author Jean-Francois Yoteau
 */
public class RssRecyclerViewAdapter extends RecyclerView.Adapter<RssRecyclerViewAdapter.RssRecyclerViewHolder> {

    /**
     * RSSアイテム一覧
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * RSSアイテム一覧を追加する。
     *
     * @param items RSSアイテム一覧
     */
    public void addRssItems(List<Item> items) {
        final int startPosition = this.items.size();
        this.items.addAll(items);
        this.notifyItemRangeInserted(startPosition, items.size());
    }

    /**
     * RSSアイテム一覧をクリアする。
     */
    public void clear() {
        final int count = this.items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, count);
    }

    @Override
    public RssRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false);
        return new RssRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RssRecyclerViewHolder holder, int position) {
        final Item item = this.items.get(position);

        holder.setRssItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * RSSのRecyclerViewのホルダー
     *
     * @author Jean-Francois Yoteau
     */
    public static final class RssRecyclerViewHolder extends RecyclerView.ViewHolder {

        /**
         * 画像ビュー
         */
        private ImageView imageView;

        /**
         * タイトルビュー
         */
        private TextView titleView;

        /**
         * 本文ビュー
         */
        private TextView bodyView;

        /**
         * コンストラクタ
         *
         * @param itemView アイテムのビュー
         */
        public RssRecyclerViewHolder(View itemView) {
            super(itemView);

            this.bindView();
        }

        /**
         * ビューをバインドする。
         */
        private void bindView() {
            this.imageView = (ImageView) this.itemView.findViewById(R.id.image);
            this.titleView = (TextView) this.itemView.findViewById(R.id.title);
            this.bodyView = (TextView) this.itemView.findViewById(R.id.body);
        }

        /**
         * RSSアイテムを設定する。
         *
         * @param item RSSアイテム
         */
        public void setRssItem(Item item) {
            // タイトルを設定する。
            if (item.getTitle() == null) {
                this.titleView.setText("");
            } else {
                final int titleMaxLength = Preferences.getTitleMaxLength();
                final String title = StringUtils.elipsize(item.getTitle(), titleMaxLength);
                this.titleView.setText(title);
            }

            // 本文を設定する。
            if (item.getDescription() == null) {
                this.bodyView.setText("");
            } else {
                final int bodyMaxLength = Preferences.getBodyMaxLength();
                final String bodyWithoutHtmlTags = StringUtils.removeHtmlTags(item.getDescription());
                final String bodyWithoutCarriageReturn = StringUtils.removeCarriageReturn(bodyWithoutHtmlTags);
                final String body = StringUtils.elipsize(bodyWithoutCarriageReturn, bodyMaxLength);
                this.bodyView.setText(body);
            }

            // 画像を設定する。
            final Enclosure enclosure = item.getEnclosure();
            if (enclosure == null) {
                // 画像がない場合
                this.imageView.setVisibility(View.GONE);
            } else {
                // 画像がある場合

                this.imageView.setVisibility(View.VISIBLE);
                Picasso.with(this.itemView.getContext())
                        .load(enclosure.getUrl())
                        .into(this.imageView);
            }
        }

    }
}
