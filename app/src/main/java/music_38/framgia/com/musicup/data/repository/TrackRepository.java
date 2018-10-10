package music_38.framgia.com.musicup.data.repository;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.GenreDataSource;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;
import music_38.framgia.com.musicup.data.source.remote.GenreRemoteDataSource;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

public class TrackRepository implements
        GenreDataSource.LocalDataSource, GenreDataSource.RemoteDataSource
        ,TrackDataSource.LocalDataSource,TrackDataSource.RemoteDataSource {

    private GenreLocalDataSource mGenreLocalDataSource;
    private TrackRemoteDataSource mTrackRemoteDataSource;
    private GenreRemoteDataSource mGenreRemoteDataSource;

    public TrackRepository(GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
    }

    public TrackRepository(TrackRemoteDataSource trackRemoteDataSource) {
        mTrackRemoteDataSource = trackRemoteDataSource;
    }

    public TrackRepository(GenreLocalDataSource genreLocalDataSource, TrackRemoteDataSource trackRemoteDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
        mTrackRemoteDataSource = trackRemoteDataSource;
    }

    @Override
    public void getGenre(Callback<ArrayList<Genre>> callback) {
        mGenreLocalDataSource.getGenre(callback);
    }

    @Override
    public void getGenre(Genre genre, Callback<Genre> callback) {
        mGenreRemoteDataSource.getGenre(genre, callback);
    }

    @Override
    public void getTrackSearch(String searchKey, Callback<Genre> callback) {
        mTrackRemoteDataSource.getTrackSearch(searchKey,callback);
    }
}
