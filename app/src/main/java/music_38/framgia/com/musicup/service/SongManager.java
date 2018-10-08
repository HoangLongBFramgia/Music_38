package music_38.framgia.com.musicup.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ALL;
import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ONE;
import static music_38.framgia.com.musicup.service.LoopMode.NO_LOOP;
import static music_38.framgia.com.musicup.service.ShuffleMode.NO_SHUFFLE;
import static music_38.framgia.com.musicup.service.ShuffleMode.SHUFFLE_ALL;

public class SongManager implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private SongServiceContract.OnMediaPlayerChangeListener mMediaPlayerChangeListener;
    private List<Track> mTracks;
    private List<Track> mUnShuffleTracks;
    private int mSongPosition;
    private boolean isPlaying;
    private int mLoopType;
    private int mShuffle;

    public SongManager(Context context, List<Track> tracks, int position) {
        this.mContext = context;
        this.mSongPosition = position;
        this.mTracks = tracks;
        mUnShuffleTracks = new ArrayList<>();
        mUnShuffleTracks.addAll(mTracks);
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

    public void shuffleTrack(int shuffleType) {
        mMediaPlayerChangeListener.onShuffleChange(shuffleType);
        checkShuffleMode(shuffleType);
    }

    private void checkShuffleMode(int shuffleType) {
        mShuffle = shuffleType;
        switch (shuffleType) {
            case SHUFFLE_ALL:
                shuffle();
                break;
            case NO_SHUFFLE:
                unShuffle();
                break;
        }
        mMediaPlayerChangeListener.onShuffleChange(mShuffle);
    }

    private void unShuffle() {
        mTracks.clear();
        mTracks.addAll(mUnShuffleTracks);
        mSongPosition = mTracks.indexOf(mTracks.get(mSongPosition));
    }

    private void shuffle() {
        Track track = mTracks.get(mSongPosition);
        mTracks.remove(mSongPosition);
        shuffleList(mTracks, new Random());
        mTracks.add(mSongPosition, track);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(this);
    }

    private void shuffleList(List<Track> list, Random random) {
        @SuppressWarnings("unchecked") final List<Track> trackSwap = list;
        if (list instanceof RandomAccess) {
            for (int i = trackSwap.size() - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                trackSwap.set(index, trackSwap.set(i, trackSwap.get(index)));
            }
        } else {
            Track[] array = (Track[]) trackSwap.toArray();
            for (int i = array.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                Track temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
            int i = 0;
            ListIterator<Track> it = trackSwap.listIterator();
            while (it.hasNext()) {
                it.next();
                it.set(array[i++]);
            }
        }
        mTracks = new ArrayList<>();
        mTracks.addAll(trackSwap);
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

    boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    void pauseTrack() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onMediaStateChange(false);
        }
        mMediaPlayer.pause();
        isPlaying = true;
    }

    void resumeTrack() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onMediaStateChange(true);
        }
        mMediaPlayer.start();
        isPlaying = false;
    }
}
