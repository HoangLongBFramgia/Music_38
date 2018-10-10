package music_38.framgia.com.musicup.data.source.remote;


import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.GenreDataSource;
import music_38.framgia.com.musicup.utils.Constants;

import static music_38.framgia.com.musicup.BuildConfig.API_KEY;

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {

    private void getGenreByTrackFromApi(Genre genre, Callback callBack) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.SoundCloud.BASE_URL)
                .append(Constants.SoundCloud.PARAM_KIND)
                .append(Constants.SoundCloud.PARAM_GENRE)
                .append(Constants.SoundCloud.PARAM_TYPE)
                .append(genre.getType())
                .append(Constants.SoundCloud.PARAM_CLIENT_ID)
                .append(API_KEY);
        String url = stringBuilder.toString();
        new GenreRemoteAsyncTask(callBack, genre).execute(url);
    }

    @Override
    public void getGenre(Genre genre, Callback<Genre> callback) {
        getGenreByTrackFromApi(genre, callback);
    }
}
