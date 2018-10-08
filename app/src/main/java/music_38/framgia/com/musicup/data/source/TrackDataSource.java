package music_38.framgia.com.musicup.data.source;

import music_38.framgia.com.musicup.data.model.Genre;

public interface TrackDataSource {

    interface RemoteDataSource {
        void getTrackSearch(String searchKey, Callback<Genre> callback);
    }

    interface LocalDataSource {

    }
}
