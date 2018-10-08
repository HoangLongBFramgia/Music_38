package music_38.framgia.com.musicup.data.source.local;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.GenreDataSource;

public class GenreLocalDataSource implements GenreDataSource.LocalDataSource {

    private void getAllGenre(Callback<ArrayList<Genre>> callback) {
        new GenreLocalAsyncTask(callback).execute();
    }

    @Override
    public void getGenre(Callback<ArrayList<Genre>> callback) {
        getAllGenre(callback);
    }
}
