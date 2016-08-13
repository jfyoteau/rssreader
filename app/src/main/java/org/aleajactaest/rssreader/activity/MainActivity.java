package org.aleajactaest.rssreader.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.aleajactaest.rssreader.R;
import org.aleajactaest.rssreader.fragment.RssFragment;
import org.aleajactaest.rssreader.fragment.SettingsFragment;
import org.aleajactaest.rssreader.fragment.TopLevelFragment;

/**
 * メインアクティビティ
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                } else {
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.showSettings();

            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 設定画面を表示する。
     */
    private void showSettings() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .addToBackStack("settings")
                .commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TopLevelFragment) {
            setTitle(((TopLevelFragment) fragment).getTitle());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        final RssFragment rssFragment = (RssFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (rssFragment != null) {
            setTitle(rssFragment.getTitle());
        }
    }

    /**
     * 環境設定した後にRssフラグメントを更新する。
     */
    public void onSaveSettings() {
        super.onBackPressed();

        final RssFragment rssFragment = (RssFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (rssFragment != null) {
            setTitle(rssFragment.getTitle());
            rssFragment.refresh();
        }
    }

}
