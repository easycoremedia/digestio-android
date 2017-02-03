package com.easycore.digestio.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.easycore.digestio.App;
import com.easycore.digestio.R;
import com.easycore.digestio.data.model.AudioItem;
import com.easycore.digestio.presenters.impl.MainPresenter;
import com.easycore.digestio.view.MainView;
import com.easycore.digestio.view.adapters.AudioItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView, AudioItemAdapter.Callback {

    private static final String EXTRA_SEARCH_RESULT = "extra_search_result";

    public static void startActivity(Activity activity, String parameters) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(EXTRA_SEARCH_RESULT, parameters);
        activity.startActivity(intent);
    }


    private MainPresenter presenter;

    @BindView(R.id.refreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcv_items)
    protected RecyclerView rcvItems;

    private AudioItemAdapter adapter;
    private LinearLayoutManager rcvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        String searchResult = getIntent().getStringExtra(EXTRA_SEARCH_RESULT);
        if (searchResult == null || searchResult.isEmpty()) {
            searchResult = "sport";
        }


        presenter = new MainPresenter((App) getApplication());
        presenter.onEnterScope(savedInstanceState);
        presenter.onAttach(this);

        presenter.initContent(searchResult);


        final String finalSearchResult = searchResult;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.initContent(finalSearchResult);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.onExitScope(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopPlayback();
    }

    @Override
    public void fillAudioItems(ArrayList<AudioItem> items) {
        rcvLayoutManager = new LinearLayoutManager(this);
        adapter = new AudioItemAdapter(items, this, this);
        rcvItems.setLayoutManager(rcvLayoutManager);
        rcvItems.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void notifyItemsChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finishOnEmptyResponse() {
        Toast.makeText(this, "Nothing found... :-(", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onItemClick(AudioItem item, int position) {
        Timber.i("Item clicked %d - %s", position, item.getName());
        presenter.onItemClicked(item, position);
    }


}
