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
        MediaPlayer.OnCompletionListener {

    private static MediaPlayer mMediaPlayer;
    private Context mContext;
    private SongServiceContract.OnMediaPlayerChangeListener mMediaPlayerChangeListener;
    private SongServiceContract.OnMiniPlayerChangeListener mMiniPlayerChangeListener;
    private static List<Track> mTracks;
    private List<Track> mUnShuffleTracks;
    private static int mSongPosition;
    private boolean isPlaying;
    private int mLoopType;
    private UpdateNotification mUpdateNotification;

    SongManager(Context context, List<Track> tracks, int position) {
        mContext = context;
        mSongPosition = position;
        mTracks = tracks;
        mUnShuffleTracks = new ArrayList<>();
        mUnShuffleTracks.addAll(mTracks);
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    int getSongPosition() {
        return mSongPosition;
    }

    void setMediaPlayerChangeListener(SongServiceContract.OnMediaPlayerChangeListener mediaPlayerChangeListener) {
        mMediaPlayerChangeListener = mediaPlayerChangeListener;
    }

    void setMiniPlayerChangeListener(SongServiceContract.OnMiniPlayerChangeListener miniPlayerChangeListener) {
        mMiniPlayerChangeListener = miniPlayerChangeListener;
    }

    void playPauseSong() {
        if (isPlaying) {
            if (mMediaPlayerChangeListener != null) {
                mMediaPlayerChangeListener.onMediaStateChange(true);
            }
            if (mMiniPlayerChangeListener != null) {
                mMiniPlayerChangeListener.onMediaStateChange(true);
            }
            mMediaPlayer.start();
        } else {
            if (mMediaPlayerChangeListener != null) {
                mMediaPlayerChangeListener.onMediaStateChange(false);
            }
            if (mMiniPlayerChangeListener != null) {
                mMiniPlayerChangeListener.onMediaStateChange(false);
            }
            mMediaPlayer.pause();
        }
        isPlaying = !isPlaying;
    }

    void resumeTrack() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onMediaStateChange(true);
        }
        if (mMiniPlayerChangeListener != null) {
            mMiniPlayerChangeListener.onMediaStateChange(true);
        }
        mMediaPlayer.start();
        isPlaying = false;
    }

    void pauseTrack() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onMediaStateChange(false);
        }
        if (mMiniPlayerChangeListener != null) {
            mMiniPlayerChangeListener.onMediaStateChange(false);
        }
        mMediaPlayer.pause();
        isPlaying = true;
    }

    public void play() {
        Track track = mTracks.get(mSongPosition);
        if (track == null) {
            return;
        }
        String urlStream = TrackRemoteDataSource.getStreamUrl(track.getId());
        if (mMediaPlayer != null) {
            destroyMediaPlayer();
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(urlStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onTrackChange(track);
        }
        if (mMiniPlayerChangeListener != null) {
            mMiniPlayerChangeListener.onTrackChange(track);
        }
    }

    private void destroyMediaPlayer() {
        mMediaPlayer.pause();
        mMediaPlayer.reset();
        mMediaPlayer.release();
    }

    boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    Track getTrackCurrent() {
        return mTracks.get(mSongPosition);
    }

    int getCurrentDurationTrack() {
        return mMediaPlayer.getCurrentPosition();
    }

    int getDurationTrack() {
        return mMediaPlayer.getDuration();
    }

    private void stop() {
        mMediaPlayer.stop();
    }

    void seekTo(int seekTo) {
        mMediaPlayer.seekTo(seekTo);
    }

    void nextSong() {
        mSongPosition++;
        if (mSongPosition >= mTracks.size()) {
            mSongPosition = 0;
        }
        play();
    }

    void previousSong() {
        mSongPosition--;
        if (mSongPosition < 0) {
            mSongPosition = mTracks.size() - 1;
        }
        play();
    }

    void loopTrack(int loopType) {
        mLoopType = loopType;
        if (mMediaPlayerChangeListener == null) {
            return;
        }
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

    void shuffleTrack(int shuffleType) {
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onShuffleChange(shuffleType);
        }
        checkShuffleMode(shuffleType);
    }

    private void checkShuffleMode(int shuffleType) {
        switch (shuffleType) {
            case SHUFFLE_ALL:
                shuffle();
                break;
            case NO_SHUFFLE:
                unShuffle();
                break;
        }
        if (mMediaPlayerChangeListener != null) {
            mMediaPlayerChangeListener.onShuffleChange(shuffleType);
        }
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

    void setUpdateNotification(UpdateNotification updateNotification) {
        mUpdateNotification = updateNotification;
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
        nextSong();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        checkLoopMode();
    }
}
