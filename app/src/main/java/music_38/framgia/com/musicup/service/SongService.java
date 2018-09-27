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
    private SongManager mSongManager;
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
        return mSongManager.getDurationTrack();
    }

    @Override
    public int getCurrentDurationSong() {
        return mSongManager.getCurrentDurationTrack();
    }

    @Override
    public void playPauseSong() {
        mSongManager.playPauseSong();
    }

    @Override
    public void play() {
        mSongManager.play();
    }

    @Override
    public void previousSong() {
        mSongManager.previousSong();
    }

    @Override
    public void nextSong() {
        mSongManager.nextSong();
    }

    @Override
    public void nextSongMini() {
    }

    @Override
    public void shuffleSong(int shuffleType) {
        mSongManager.shuffleTrack(shuffleType);
    }

    @Override
    public void loopSong(int loopType) {
        mSongManager.loopTrack(loopType);
    }

    @Override
    public void downloadCurrentTrack() {
    }

    @Override
    public void seekTo(int seekTo) {
        mSongManager.seekTo(seekTo);
    }

    @Override
    public Track getTrackCurrent() {
        if (mSongManager == null)
            return null;
        return mSongManager.getTrackCurrent();
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
        if (intent != null) {
            List<Track> tracks = intent.getParcelableArrayListExtra(EXTRA_LIST);
            if (tracks != null) {
                int position = intent.getIntExtra(EXTRA_POSITION, 0);
                mSongManager = new SongManager(getApplicationContext(), tracks, position);
                mSongManager.play();
            }
        }
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

    public SongServiceContract.ISongService getISongService() {
        return this;
    }

    public void setOnMediaPlayerChangeListener(SongServiceContract.OnMediaPlayerChangeListener onMediaPlayerChangeListener) {
        mOnMediaPlayerChangeListener = onMediaPlayerChangeListener;
        mSongManager.setMediaPlayerChangeListener(onMediaPlayerChangeListener);
    }
}
