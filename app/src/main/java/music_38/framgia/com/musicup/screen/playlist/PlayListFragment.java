package music_38.framgia.com.musicup.screen.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;

public class PlayListFragment extends BaseFragment implements PlayListContract.View, AppBarLayout.OnOffsetChangedListener {

    public static final String TAG = PlayListFragment.class.getName();
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mTextTitleToolbar;
    private PlayListPresenter mDetailPresenter;
    private String mType;
    private String mTitle;
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
    protected int getLayoutResource() {
        return R.layout.fragment_play_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        mGenre = bundle.getParcelable(BUNDLE_GENRE);
    }

    @Override
    protected void initComponent(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mAppBar = view.findViewById(R.id.app_bar);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        mTextTitleToolbar = view.findViewById(R.id.tv_toolbar_playlist);
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
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void displayPlaylistBanner(String urlImage) {

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
}
