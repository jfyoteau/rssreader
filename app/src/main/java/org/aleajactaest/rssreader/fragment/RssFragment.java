package org.aleajactaest.rssreader.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.aleajactaest.rssreader.R;
import org.aleajactaest.rssreader.manager.RssManager;
import org.aleajactaest.rssreader.model.RssModel;
import org.aleajactaest.rssreader.util.Preferences;
import org.aleajactaest.rssreader.view.RssRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * RSSフラグメント。
 *
 * RSSのアイテム一覧を表示する。
 */
public class RssFragment extends Fragment implements TopLevelFragment, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 初めてフラグ
     */
    private boolean firstTime = true;

    /**
     * RecyclerViewの為、RSSのアダプタ
     */
    private final RssRecyclerViewAdapter rssAdapter = new RssRecyclerViewAdapter();

    /**
     * Swipe Refresh Layout
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_rss, container, false);
        this.swipeRefreshLayout.setOnRefreshListener(this);

        final RecyclerView recyclerView = (RecyclerView) this.swipeRefreshLayout.findViewById(R.id.recycler_view);
        setRecyclerView(recyclerView);

        return this.swipeRefreshLayout;
    }

    /**
     * RecyclerViewを設定する。
     *
     * @param recyclerView RecyclerView
     */
    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.rssAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (firstTime) {
            // 初めて
            firstTime = false;

            this.refresh();
        } else {
            this.rssAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 更新する。
     */
    public void refresh() {
        // ローディングマークを表示する。
        this.showLoadingMark();

        // 更新する。
        this.onRefresh();
    }

    /**
     * SwipeRefreshLayoutが更新される時にRSSフィードを更新する。
     */
    @Override
    public void onRefresh() {
        final Set<String> urlList = Preferences.getUrlList();
        final String[] urls = new String[urlList.size()];
        int i = 0;
        for (final String url : urlList) {
            urls[i++] = url;
        }

        new RefreshAsyncTask().execute(urls);
    }

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
    }

    /**
     * 画面を更新する。
     *
     * @param rssModels RSSモデール一覧
     */
    private void updateScreen(List<RssModel> rssModels) {
        this.rssAdapter.clear();

        final int n = rssModels.size();
        for (int i = 0; i < n; i++) {
            final RssModel model = rssModels.get(i);

            this.rssAdapter.addRssItems(model.getChannel().getItems());
        }
    }

    /**
     * ローディングマークを表示する。
     */
    private void showLoadingMark() {
        this.swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * ローディングマークを非表示する。
     */
    private void hideLoadingMark() {
        RssFragment.this.swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * RSSフィードの更新タスク
     * <p/>
     * 入力：RSSのURL一覧
     */
    private class RefreshAsyncTask extends AsyncTask<String, String, List<RssModel>> {

        @Override
        protected List<RssModel> doInBackground(String... urls) {
            final ExecutorService executorService = Executors.newFixedThreadPool(3);

            // 各URLにRSSモデルを取得する。
            final List<Future<RssModel>> tasks = new ArrayList<>(urls.length);
            for (final String url : urls) {
                final Future<RssModel> task = executorService.submit(new Callable<RssModel>() {

                    @Override
                    public RssModel call() throws Exception {

                        return RssManager.getRss(url);
                    }
                });
                tasks.add(task);
            }

            executorService.shutdown();

            final List<RssModel> models = new ArrayList<>(tasks.size());
            final int n = tasks.size();
            for (int i = 0; i < n; i++) { // パフォーマンスの為
                final Future<RssModel> task = tasks.get(i);

                try {
                    // RSSモデルを取得する。
                    final RssModel model = task.get();

                    // RSSモデル一覧にモデルを追加する。
                    models.add(model);
                } catch (InterruptedException e) {
                    // 何もしない。
                } catch (ExecutionException e) {
                    final String errorUrl = urls[i];
                    publishProgress(errorUrl);
                }
            }

            return models;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            final String errorMessage = getString(R.string.error_connection, values[0]);
            final AlertDialogFragment alert = AlertDialogFragment.newInstance(R.string.error_title, errorMessage);
            alert.show(getFragmentManager(), "ALERT");
        }

        @Override
        protected void onPostExecute(List<RssModel> rssModels) {
            // 画面を更新する。
            updateScreen(rssModels);

            // ローディングマークを非表示する。
            hideLoadingMark();
        }
    }

}
