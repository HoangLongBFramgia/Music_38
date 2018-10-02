package music_38.framgia.com.musicup.screen.search;


import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.RecentSearch;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

public class SearchContract {
    interface View {

        void getDataTrackSuccess(Genre genre);

        void hideProgress();

        void getDataError(Exception exception);

        void onGetSuggestSuccess(ArrayList<String> suggest);

        void onGetSuggestError(Exception e);

        void onGetHistorySearchSuccess(List<RecentSearch> recentSearches);

        void onGetHistorySearchError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void getTrackBySearch(String searchKey);

        void getSuggests();

        void getHistorySearch();

        void deleteHistorySearch(RecentSearch recentSearch);

        void addHistorySearch(RecentSearch recentSearch);
    }
}
