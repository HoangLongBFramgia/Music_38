package music_38.framgia.com.musicup.service;

import music_38.framgia.com.musicup.data.model.Track;

public class SongServiceContract {

    public interface ISongService {

        void playPauseSong();

        void play();

        void previousSong();

        void nextSong();

        void nextSongMini();

        void shuffleSong(int shuffleType);

        void loopSong(int loopType);

        void downloadCurrentTrack();

        void seekTo(int seekTo);

        int getDurationSong();

        int getCurrentDurationSong();

        Track getTrackCurrent();

        void favoritesSong();
    }

    public interface OnMediaPlayerChangeListener {

        void onMediaStateChange(boolean isPlaying);

        void onTrackChange(Track track);

        void onLoopChange(int state);

        void onShuffleChange(int state);

        void onDownLoadChange(int state);

        void onDurationSong(int duration);

        void onCurrentDurationSong(int duration);

        void onFavoritesChange(int state);
    }
}
