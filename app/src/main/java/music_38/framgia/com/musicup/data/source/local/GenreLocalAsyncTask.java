package music_38.framgia.com.musicup.data.source.local;

import android.os.AsyncTask;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.utils.Constants;

public class GenreLocalAsyncTask extends AsyncTask<String, String, ArrayList<Genre>> {

    private Callback mCallBack;

    public GenreLocalAsyncTask(Callback callBack) {
        mCallBack = callBack;
    }

    @Override
    protected ArrayList<Genre> doInBackground(String... strings) {
        ArrayList<Genre> genres = new ArrayList<>();
        Genre mAllMusic = new Genre(Constants.Genre.ALL_MUSIC,
                Constants.Genre.IMAGE_ALL_MUSIC, TypeMusic.ALL_MUSIC);
        Genre mAllAudio = new Genre(Constants.Genre.ALL_AUDIO,
                Constants.Genre.IMAGE_ALL_AUDIO, TypeMusic.ALL_AUDIO);
        Genre mAllAmbient = new Genre(Constants.Genre.AMBIENT,
                Constants.Genre.IMAGE_AMBIENT, TypeMusic.AMBIENT);
        Genre mAllCountry = new Genre(Constants.Genre.COUNTRY,
                Constants.Genre.IMAGE_COUNTRY, TypeMusic.COUNTRY);
        Genre mAllRock = new Genre(Constants.Genre.ALTERNATIVE_ROCK,
                Constants.Genre.IMAGE_ALTERNATIVE_ROCK, TypeMusic.ALTERNATIVE_ROCK);
        Genre mClassical = new Genre(Constants.Genre.CLASSICAL,
                Constants.Genre.IMAGE_CLASSICAL, TypeMusic.CLASSICAL);

        genres.add(mAllMusic);
        genres.add(mAllAudio);
        genres.add(mAllAmbient);
        genres.add(mAllCountry);
        genres.add(mAllRock);
        genres.add(mClassical);

        return genres;
    }

    @Override
    protected void onPostExecute(ArrayList<Genre> genres) {
        super.onPostExecute(genres);
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(genres);

    }
}
