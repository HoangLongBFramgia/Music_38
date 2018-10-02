package music_38.framgia.com.musicup.screen.search;


import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.RecentSearch;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.Callback;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private TrackRepository mTrackRepository;

    public SearchPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getTrackBySearch(String searchKey) {
        mTrackRepository.getGenre(searchKey, new Callback<Genre>() {
            @Override
            public void getDataSuccess(Genre data) {
                mView.hideProgress();
                mView.getDataTrackSuccess(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.hideProgress();
                mView.getDataError(e);
            }
        });
    }

    @Override
    public void getSuggests() {
        mTrackRepository.getSuggest(new Callback<ArrayList<String>>() {
            @Override
            public void getDataSuccess(ArrayList<String> data) {
                mView.onGetSuggestSuccess(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.onGetSuggestError(e);
            }
        });
    }

    @Override
    public void getHistorySearch() {
        mTrackRepository.getRecentSearch(new Callback<ArrayList<RecentSearch>>() {
            @Override
            public void getDataSuccess(ArrayList<RecentSearch> data) {
                mView.onGetHistorySearchSuccess(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.onGetSuggestError(e);
            }
        });
    }

    @Override
    public void deleteHistorySearch(RecentSearch recentSearch) {
        mTrackRepository.deleteRecentSearch(recentSearch);
    }

    @Override
    public void addHistorySearch(RecentSearch recentSearch) {
        mTrackRepository.addRecentSearchToRealm(recentSearch);
    }

    @Override
    public void setView(Object view) {
        mView = (SearchContract.View) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
