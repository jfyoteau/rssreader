package org.aleajactaest.rssreader.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.aleajactaest.rssreader.R;

import java.util.List;

/**
 * UrlのRecyclerViewのホルダー
 *
 * @author Jean-Francois Yoteau
 */
public class UrlRecyclerViewAdapter extends RecyclerView.Adapter<UrlRecyclerViewAdapter.UrlRecyclerViewHolder> {

    private final List<String> urls;

    /**
     * コンストラクタ
     *
     * @param urls URL一覧
     */
    public UrlRecyclerViewAdapter(List<String> urls) {
        this.urls = urls;
    }

    /**
     * Urlを追加する。
     *
     * @param url url
     */
    public void addUrl(String url) {
        this.urls.add(url);
        this.notifyItemInserted(this.urls.size() - 1);
    }

    /**
     * Urlを削除する。
     *
     * @param position 削除するurlの位置
     */
    public void deleteUrl(int position) {
        this.urls.remove(position);
        this.notifyItemRemoved(position);
    }

    /**
     * Url一覧を取得する。
     *
     * @return Url一覧
     */
    public List<String> getUrls() {
        return this.urls;
    }

    @Override
    public UrlRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_url, parent, false);
        return new UrlRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UrlRecyclerViewHolder holder, int position) {
        final String url = this.urls.get(position);

        holder.setUrl(url);
    }

    @Override
    public int getItemCount() {
        return this.urls.size();
    }

    /**
     * UrlのRecyclerViewのホルダー
     *
     * @author Jean-Francois Yoteau
     */
    public class UrlRecyclerViewHolder extends RecyclerView.ViewHolder {

        /**
         * Urlのビュー
         */
        private TextView urlView;

        /**
         * 削除ボタン
         */
        private Button deleteButton;

        /**
         * コンストラクタ
         *
         * @param itemView アイテムのビュー
         */
        public UrlRecyclerViewHolder(View itemView) {
            super(itemView);

            this.bindView();
        }

        /**
         * ビューをバインドする。
         */
        private void bindView() {
            this.urlView = (TextView) this.itemView.findViewById(R.id.url);
            this.deleteButton = (Button) this.itemView.findViewById(R.id.delete_button);
            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    deleteUrl(position);
                }
            });
        }

        /**
         * Urlを設定する。
         *
         * @param url Url
         */
        public void setUrl(String url) {
            this.urlView.setText(url);
        }

    }

}
