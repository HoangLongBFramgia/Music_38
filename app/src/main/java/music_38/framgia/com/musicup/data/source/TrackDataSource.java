package music_38.framgia.com.musicup.data.source;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;

public interface TrackDataSource {

    interface RemoteDataSource {
        void getGenre(Genre genre, Callback<Genre> callback);

        void getSearchTrack(String searchKey, Callback<Genre> callback);
    }

    interface LocalDataSource {
        void getGenre(Callback<ArrayList<Genre>> callback);
    }
}
