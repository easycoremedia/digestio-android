package com.easycore.digestio.view.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        presenter = new MainPresenter((App) getApplication());
        presenter.onEnterScope(savedInstanceState);
        presenter.onAttach(this);

        presenter.initContent();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.initContent();
            }
        });
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
    public void onItemClick(AudioItem item, int position) {
        Timber.i("Item clicked %d - %s", position, item.getName());
    }
}
