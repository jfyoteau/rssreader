package org.aleajactaest.rssreader.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.aleajactaest.rssreader.R;
import org.aleajactaest.rssreader.activity.MainActivity;
import org.aleajactaest.rssreader.util.Preferences;
import org.aleajactaest.rssreader.view.UrlRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 設定フラグメント
 *
 * @author Jean-Francois Yoteau
 */
public class SettingsFragment extends Fragment implements TopLevelFragment {

    /**
     * タイトルの最大桁数の入力ビュー
     */
    private EditText titleMaxLengthView;

    /**
     * 本文の最大桁数の入力ビュー
     */
    private EditText bodyMaxLengthView;

    /**
     * UrlのRecyclerViewアダプター
     */
    private UrlRecyclerViewAdapter urlAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        titleMaxLengthView = (EditText) view.findViewById(R.id.title_max_length);
        titleMaxLengthView.setText(String.valueOf(Preferences.getTitleMaxLength()));
        bodyMaxLengthView = (EditText) view.findViewById(R.id.body_max_length);
        bodyMaxLengthView.setText(String.valueOf(Preferences.getBodyMaxLength()));

        final Button applyButton = (Button) view.findViewById(R.id.save_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードを非表示する。
                hideKeyboard();

                // 設定を保存する。
                savePreferences();

                // 設定画面を閉じる
                ((MainActivity) getActivity()).onSaveSettings();
            }
        });

        final Button addUrlButton = (Button) view.findViewById(R.id.add_button);
        addUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUrlDialog();
            }
        });

        final RecyclerView urlList = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.setUrlList(urlList);

        return view;
    }

    /**
     * Url一覧を設定する。
     *
     * @param recyclerView RecyclerView
     */
    private void setUrlList(RecyclerView recyclerView) {
        final Set<String> urlList = Preferences.getUrlList();
        final List<String> urls = new ArrayList<>(urlList.size());
        for (final String url : urlList) {
            urls.add(url);
        }
        this.urlAdapter = new UrlRecyclerViewAdapter(urls);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.urlAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // メニューを表示しない。
        menu.clear();
    }

    @Override
    public String getTitle() {
        return getString(R.string.action_settings);
    }

    /**
     * 設定を保存する。
     */
    private void savePreferences() {
        // 設定を取得する。
        final String strTitleMaxLength = titleMaxLengthView.getText().toString();
        if (strTitleMaxLength.length() == 0) {
            // TODO エラー
            return;
        }
        final int titleMaxLength = Integer.parseInt(strTitleMaxLength);

        final String strBodyMaxLength = bodyMaxLengthView.getText().toString();
        if (strBodyMaxLength.length() == 0) {
            // TODO エラー
            return;
        }
        final int bodyMaxLength = Integer.parseInt(strBodyMaxLength);

        final List<String> urls = this.urlAdapter.getUrls();
        final Set<String> urlList = new LinkedHashSet<>();
        for (final String url : urls) {
            urlList.add(url);
        }

        // 設定を保存する。
        Preferences.setPreferences(titleMaxLength, bodyMaxLength, urlList);
    }

    /**
     * キーボードを非表示する。
     */
    private void hideKeyboard() {
        final FragmentActivity activity = getActivity();

        final View view = activity.getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * URL一覧にURLを追加する。
     *
     * @param url 追加するURL
     */
    private void addUrl(String url) {
        this.urlAdapter.addUrl(url);
    }

    /**
     * URL追加のダイアログを表示する。
     */
    private void showAddUrlDialog() {
        final Context context = getContext();

        final EditText urlEdit = new EditText(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.setting_add_url_title)
                .setView(urlEdit)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 入力したURLを取得する。
                        final String url = urlEdit.getText().toString();

                        if (url.isEmpty()) {
                            dialog.cancel();
                            return;
                        }

                        // TODO URLをチェックする

                        // URL一覧にURLを追加する。
                        addUrl(url);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        dialog.show();
    }

}
