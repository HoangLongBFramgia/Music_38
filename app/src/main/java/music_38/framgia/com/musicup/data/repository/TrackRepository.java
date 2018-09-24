package music_38.framgia.com.musicup.data.repository;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;

public class TrackRepository implements TrackDataSource.RemoteDataSource,
        TrackDataSource.LocalDataSource {

    private GenreLocalDataSource mGenreLocalDataSource;

    public TrackRepository(GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
    }

    @Override
    public void getGenre(Callback<ArrayList<Genre>> callback) {
        mGenreLocalDataSource.getGenre(callback);
    }
}
