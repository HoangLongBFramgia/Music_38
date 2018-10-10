package music_38.framgia.com.musicup.data.source.remote;


import music_38.framgia.com.musicup.BuildConfig;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.utils.Constants;

import static music_38.framgia.com.musicup.BuildConfig.API_KEY;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {

    public static String getStreamUrl(int id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.Stream.STREAM_URL)
                .append(String.valueOf(id))
                .append(Constants.Stream.STREAM)
                .append(Constants.Stream.STREAM_CLIENT_ID)
                .append(API_KEY);
        return stringBuilder.toString();
    }

    @Override
    public void getTrackSearch(String searchKey, Callback<Genre> callback) {
        getTrackBySearch(searchKey, callback);
    }

    private void getTrackBySearch(String searchKey, Callback callBack) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.SoundCloud.BASE_URL)
                .append(Constants.SoundCloud.SEARCH)
                .append(Constants.SoundCloud.QUERY_SEARCH)
                .append(searchKey)
                .append(Constants.SoundCloud.PARAM_CLIENT_ID)
                .append(BuildConfig.API_KEY)
                .append(Constants.SoundCloud.PARAM_LIMIT)
                .append(Constants.SoundCloud.LIMIT);

        String url = stringBuilder.toString();

        new SearchTrackRemoteAsyncTask(callBack, searchKey).execute(url);
    }
}
