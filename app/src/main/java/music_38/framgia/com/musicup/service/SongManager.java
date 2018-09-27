package music_38.framgia.com.musicup.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import java.io.IOException;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ALL;
import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ONE;
import static music_38.framgia.com.musicup.service.LoopMode.NO_LOOP;

public class SongManager implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    public static final String PREF_LOOP = "loop";
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private SongServiceContract.OnMediaPlayerChangeListener mMediaPlayerChangeListener;
    private List<Track> mTracks;
    private int mSongPosition;
    private boolean isPlaying;
    private int mLoopType;

    public SongManager(Context context, List<Track> tracks, int position) {
        this.mContext = context;
        this.mSongPosition = position;
        this.mTracks = tracks;
        this.mMediaPlayer = new MediaPlayer();
    }

    public void setMediaPlayerChangeListener(SongServiceContract.OnMediaPlayerChangeListener mediaPlayerChangeListener) {
        mMediaPlayerChangeListener = mediaPlayerChangeListener;
    }

    public void playPauseSong() {
        if (isPlaying) {
            mMediaPlayerChangeListener.onMediaStateChange(true);
            mMediaPlayer.start();
        } else {
            mMediaPlayerChangeListener.onMediaStateChange(false);
            mMediaPlayer.pause();
        }
        isPlaying = !isPlaying;
    }

    public void play() {
        Track track = mTracks.get(mSongPosition);
        if (track == null) {
            return;
        }
        String urlStream = TrackRemoteDataSource.getStreamUrl(track.getId());
        if (mMediaPlayer != null) {
            destroyMediaPlayer(mMediaPlayer);
        }
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(urlStream);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onTrackChange(mTracks.get(mSongPosition));
        }
    }

    private void destroyMediaPlayer(MediaPlayer mediaPlayer) {
        mediaPlayer.pause();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    public Track getTrackCurrent() {
        return mTracks.get(mSongPosition);
    }

    public int getCurrentDurationTrack() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDurationTrack() {
        return mMediaPlayer.getDuration();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void release() {
        mMediaPlayer.release();
    }

    public void seekTo(int seekTo) {
        mMediaPlayer.seekTo(seekTo);
    }

    public void nextSong() {
        mSongPosition++;
        if (mSongPosition >= mTracks.size()) {
            mSongPosition = 0;
        }
        play();
    }

    public void previousSong() {
        mSongPosition--;
        if (mSongPosition < 0) {
            mSongPosition = mTracks.size() - 1;
        }
        play();
    }

    public void loopTrack(int loopType) {
        mLoopType = loopType;
        mMediaPlayerChangeListener.onLoopChange(loopType);
    }

    private void checkLoopMode() {
        switch (mLoopType) {
            case LOOP_ALL:
                nextSong();
                break;
            case LOOP_ONE:
                seekTo(0);
                mMediaPlayer.start();
                break;
            case NO_LOOP:
                noLoopTrack();
                break;
        }
    }

    private void noLoopTrack() {
        if (mSongPosition < mTracks.size() - 1) {
            mSongPosition++;
            play();
        } else {
            mSongPosition = 0;
            seekTo(0);
            stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayerChangeListener.onDurationSong(mediaPlayer.getDuration());
        mediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        checkLoopMode();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }
}
