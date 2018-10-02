package music_38.framgia.com.musicup.screen.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.RecentSearch;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.screen.search.adapter.HistorySearchAdapter;
import music_38.framgia.com.musicup.screen.search.adapter.SearchAdapter;
import music_38.framgia.com.musicup.screen.search.adapter.SuggestAdapter;
import music_38.framgia.com.musicup.service.SongService;

public class SearchFragment extends BaseFragment implements View.OnClickListener,
        SearchContract.View, TextWatcher, HistorySearchAdapter.OnItemClickListener,
        SearchAdapter.OnItemClickListener, SuggestAdapter.OnItemClickListener {

    public static final String TAG = SearchFragment.class.getName();
    private ProgressBar mProgressBar;
    private ImageView mImageSearch;
    private EditText mEditTextSearch;
    private RecyclerView mRecyclerSearch;
    private RecyclerView mRecyclerSuggest;
    private RecyclerView mRecyclerRecent;
    private TextView mTextNumberResult;
    private SearchPresenter mSearchPresenter;
    private Group mGroup;
    private Group mGroupResultSearch;
    private HistorySearchAdapter historySearchAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initComponent(View view) {
        mProgressBar = view.findViewById(R.id.progress_bar);
        mImageSearch = view.findViewById(R.id.image_search);
        mEditTextSearch = view.findViewById(R.id.edit_search);
        mRecyclerSearch = view.findViewById(R.id.recycler_search);
        mRecyclerSuggest = view.findViewById(R.id.recycler_suggest);
        mRecyclerRecent = view.findViewById(R.id.recycler_recent);
        mTextNumberResult = view.findViewById(R.id.text_number_result);
        mGroup = view.findViewById(R.id.constraint_group_search);
        mGroupResultSearch = view.findViewById(R.id.constraint_group_result_search);
        setListener();
    }

    private void setListener() {
        mImageSearch.setOnClickListener(this);
        mEditTextSearch.addTextChangedListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        GenreLocalDataSource genreLocalDataSource = new GenreLocalDataSource();
        TrackRemoteDataSource trackRemoteDataSource = new TrackRemoteDataSource();
        TrackRepository trackRepository
                = new TrackRepository(genreLocalDataSource, trackRemoteDataSource);
        mSearchPresenter = new SearchPresenter(trackRepository);
        mSearchPresenter.setView(this);
        loadDataSuggest();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().isEmpty()) {
            loadDataSearch(editable.toString());
        } else {
            visibilityView(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_search:
                loadDataSearch(mEditTextSearch.getText().toString());
                break;
        }
    }

    @Override
    public void getDataTrackSuccess(Genre genre) {
        if (genre == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genre.getTracks().size())
                .append(getString(R.string.msg_result_search))
                .append(mEditTextSearch.getText().toString())
                .append("\"");
        String numberResult = stringBuilder.toString();
        mTextNumberResult.setText(numberResult);
        initRecyclerViewResultSearch(genre);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getDataError(Exception exception) {
        mProgressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSuggestSuccess(ArrayList<String> suggest) {
        if (suggest == null) {
            return;
        }
        initRecyclerSuggest(suggest);
    }

    @Override
    public void onGetSuggestError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetHistorySearchSuccess(List<RecentSearch> recentSearches) {
        if (recentSearches == null) {
            return;
        }
        initRecyclerRecent(recentSearches);
    }

    @Override
    public void onGetHistorySearchError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickHistorySearchListener(RecentSearch historySearch) {
        String history = historySearch.getRecentSearch();
        visibilityView(true);
        mEditTextSearch.setText(history);
        loadDataSearch(history);
    }

    @Override
    public void onItemClickDeleteHistorySearchListener(RecentSearch recentSearch) {
        mSearchPresenter.deleteHistorySearch(recentSearch);
        historySearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickSuggestListener(String keySuggest) {
        visibilityView(true);
        mEditTextSearch.setText(keySuggest);
        loadDataSearch(keySuggest);
    }

    @Override
    public void onItemClickSearchListener(List<Track> tracks, int position) {
        Intent intentService = SongService.getIntentService(getContext(), tracks, position);
        if (getActivity() == null) {
            return;
        }
        getActivity().startService(intentService);
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        startActivity(intent);
    }

    private void initRecyclerSuggest(ArrayList<String> suggest) {
        SuggestAdapter suggestAdapter = new SuggestAdapter(suggest, this);
        mRecyclerSuggest.setHasFixedSize(true);
        mRecyclerSuggest.setAdapter(suggestAdapter);
    }

    private void initRecyclerViewResultSearch(Genre genre) {
        SearchAdapter searchAdapter = new SearchAdapter(genre.getTracks(), this);
        mRecyclerSearch.setHasFixedSize(true);
        mRecyclerSearch.setAdapter(searchAdapter);
    }

    private void loadDataSuggest() {
        mSearchPresenter.getSuggests();
    }

    private void loadDataSearch(String searchKey) {
        visibilityView(false);
        mSearchPresenter.getTrackBySearch(searchKey);
        mSearchPresenter.addHistorySearch(new RecentSearch(searchKey));
    }

    private void visibilityView(boolean isShow) {
        if (isShow) {
            mGroup.setVisibility(View.VISIBLE);
            mGroupResultSearch.setVisibility(View.GONE);
        } else {
            mGroup.setVisibility(View.GONE);
            mGroupResultSearch.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerRecent(List<RecentSearch> recentSearches) {
        if (recentSearches != null) {
            historySearchAdapter
                    = new HistorySearchAdapter(recentSearches, this);
            mRecyclerRecent.setAdapter(historySearchAdapter);
        }
    }
}
