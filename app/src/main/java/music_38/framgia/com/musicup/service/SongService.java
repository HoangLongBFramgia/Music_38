package music_38.framgia.com.musicup.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;

public class SongService extends Service implements SongServiceContract.ISongService {

    public static final String EXTRA_LIST = "EXTRA_LIST";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    private final IBinder mSongBind = new SongBinder();
    private SongServiceContract.OnMediaPlayerChangeListener mOnMediaPlayerChangeListener;

    public static Intent getIntentService(Context context, List<Track> tracks, int position) {
        if (context != null) {
            Intent intent = new Intent(context, SongService.class);
            intent.putParcelableArrayListExtra(EXTRA_LIST, (ArrayList<? extends Parcelable>) tracks);
            intent.putExtra(EXTRA_POSITION, position);
            return intent;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int getDurationSong() {
        return 0;
    }

    @Override
    public int getCurrentDurationSong() {
        return 0;
    }

    @Override
    public void playPauseSong() {
    }

    @Override
    public void play() {
    }

    @Override
    public void previousSong() {
    }

    @Override
    public void nextSong() {
    }

    @Override
    public void nextSongMini() {
    }

    @Override
    public void shuffleSong() {
    }

    @Override
    public void loopSong() {
    }

    @Override
    public void downloadCurrentTrack() {
    }

    @Override
    public void seekTo(int seekTo) {
    }

    @Override
    public Track getTrackCurrent() {
        return null;
    }

    @Override
    public void favoritesSong() {
    }

    //binder
    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSongBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
