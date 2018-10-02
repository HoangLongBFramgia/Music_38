package music_38.framgia.com.musicup.data.repository;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

public class TrackRepository implements TrackDataSource.RemoteDataSource,
        TrackDataSource.LocalDataSource {

    private GenreLocalDataSource mGenreLocalDataSource;
    private TrackRemoteDataSource mTrackRemoteDataSource;

    public TrackRepository(GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
    }

    public TrackRepository(TrackRemoteDataSource trackRemoteDataSource) {
        mTrackRemoteDataSource = trackRemoteDataSource;
    }

    @Override
    public void getGenre(Callback<ArrayList<Genre>> callback) {
        mGenreLocalDataSource.getGenre(callback);
    }

    @Override
    public void getGenre(Genre genre, Callback<Genre> callback) {
        mTrackRemoteDataSource.getGenre(genre, callback);
    }

    @Override
    public void getSearchTrack(String searchKey, Callback<Genre> callback) {
        mTrackRemoteDataSource.getSearchTrack(searchKey, callback);
    }
}
