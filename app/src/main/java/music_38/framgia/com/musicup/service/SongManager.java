package music_38.framgia.com.musicup.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import java.io.IOException;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

public class SongManager implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private SongServiceContract.OnMediaPlayerChangeListener mMediaPlayerChangeListener;
    private List<Track> mTracks;
    private int mSongPosition;

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
    }

    public void play() {
        Track track = mTracks.get(mSongPosition);
        if (track == null) {
            return;
        }
        String urlStream = TrackRemoteDataSource.getStreamUrl(track.getId());
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mMediaPlayer.reset();
        } else {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        try {
            mMediaPlayer.setDataSource(urlStream);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayerChangeListener.onDurationSong(mediaPlayer.getDuration());
        mediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }
}
