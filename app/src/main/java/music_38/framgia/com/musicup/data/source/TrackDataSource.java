package music_38.framgia.com.musicup.data.source;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.RecentSearch;

public interface TrackDataSource {

    interface RemoteDataSource {
        void getGenre(Genre genre, Callback<Genre> callback);

        void getGenre(String searchKey, Callback<Genre> callback);
    }

    interface LocalDataSource {
        void getGenre(Callback<ArrayList<Genre>> callback);

        void getSuggest(Callback<ArrayList<String>> callback);

        void getRecentSearch(Callback<ArrayList<RecentSearch>> callback);

        void addRecentSearchToRealm(RecentSearch recentSearch);

        void deleteRecentSearch(RecentSearch recentSearch);
    }
}
