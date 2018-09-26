package music_38.framgia.com.musicup.screen.playlist;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.main.OnHideViewCallback;
import music_38.framgia.com.musicup.utils.Utils;

public class PlayListFragment extends BaseFragment implements PlayListContract.View,
        AppBarLayout.OnOffsetChangedListener, PlayListAdapter.OnItemPlaylistClickListener {

    public static final String TAG = PlayListFragment.class.getName();
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mTextTitleToolbar;
    private PlayListPresenter mDetailPresenter;
    private TextView mTextTitlePlaylist;
    private TextView mTextNumberPlaylist;
    private SimpleDraweeView mImageViewPlayList;
    private SimpleDraweeView mImageBehindPlaylist;
    private RecyclerView mRecyclerPlayList;
    private OnHideViewCallback mOnHideViewCallback;
    private Genre mGenre;
    /**
     * Check CollapsingToolbar expanded or closed
     **/
    private boolean mIsShow = true;
    /**
     * Scroll Range by appbar
     **/
    private int mScrollRange = -1;

    public static PlayListFragment newInstance(Genre genre) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_GENRE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlayListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHideViewCallback) {
            mOnHideViewCallback = (OnHideViewCallback) activity;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnHideViewCallback.onHideBottomBar(View.GONE);
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        mGenre = bundle.getParcelable(BUNDLE_GENRE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOnHideViewCallback.onHideBottomBar(View.VISIBLE);
    }

    @Override
    protected void initComponent(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mAppBar = view.findViewById(R.id.app_bar);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        mTextTitleToolbar = view.findViewById(R.id.tv_toolbar_playlist);
        mRecyclerPlayList = view.findViewById(R.id.recycler_play_list);
        mTextNumberPlaylist = view.findViewById(R.id.text_number_playlist);
        mTextTitlePlaylist = view.findViewById(R.id.title_playlist);
        mImageViewPlayList = view.findViewById(R.id.iv_playlist);
        mImageBehindPlaylist = view.findViewById(R.id.iv_behind_playlist);
        setUpToolbar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        TrackRemoteDataSource trackRemoteDataSource = new TrackRemoteDataSource();
        TrackRepository trackRepository = new TrackRepository(trackRemoteDataSource);
        mDetailPresenter = new PlayListPresenter(trackRepository);
        mDetailPresenter.setView(this);
        loadData();
    }

    @Override
    public void getDataTrackSuccess(Genre genre) {
        mTextTitleToolbar.setText(genre.getTitle());
        mTextTitlePlaylist.setText(genre.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genre.getTracks().size())
                .append(getString(R.string.title_blank))
                .append(getString(R.string.title_song));
        mTextNumberPlaylist.setText(stringBuilder);
        initRecyclerView(genre);
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void displayPlaylistBanner(String urlImage) {
        Utils.blurImageWithFresco(mImageBehindPlaylist, Uri.parse(urlImage));
        mImageViewPlayList.setImageURI(Uri.parse(urlImage));
    }

    @Override
    public void getDataError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mScrollRange == -1) {
            mScrollRange = appBarLayout.getTotalScrollRange();
        }
        if (mScrollRange + verticalOffset == 0) {
            mCollapsingToolbar.setTitle(getString(R.string.title_default));
            mTextTitleToolbar.setVisibility(View.VISIBLE);
            mIsShow = true;
        } else if (mIsShow) {
            mCollapsingToolbar.setTitle(getString(R.string.title_blank));
            mTextTitleToolbar.setVisibility(View.GONE);
            mIsShow = false;
        }
    }

    private void setUpToolbar() {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAppBar.setExpanded(false);
        mAppBar.addOnOffsetChangedListener(this);
    }

    private void loadData() {
        mDetailPresenter.getTrackByGenre(mGenre);
    }

    private void initRecyclerView(Genre genre) {
        PlayListAdapter playListAdapter = new PlayListAdapter(genre.getTracks(), this);
        mRecyclerPlayList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerPlayList.setHasFixedSize(true);
        mRecyclerPlayList.setAdapter(playListAdapter);
    }

    @Override
    public void onItemPlaylistClick(List<Track> tracks, int position) {
    }
}
