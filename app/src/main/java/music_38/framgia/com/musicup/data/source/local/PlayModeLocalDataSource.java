package music_38.framgia.com.musicup.data.source.local;

import music_38.framgia.com.musicup.data.source.PlayModeDataSource;
import music_38.framgia.com.musicup.utils.SharedPrefs;

public class PlayModeLocalDataSource implements PlayModeDataSource {
    private static final String PREF_IS_SHUFFLE = "PREF_IS_SHUFFLE";
    private static final String PREF_LOOP_MODE = "PREF_PLAY_MODE";
    private static PlayModeLocalDataSource sInstance;
    private SharedPrefs mSharedPrefs;

    public PlayModeLocalDataSource() {
        mSharedPrefs = SharedPrefs.getInstance();
    }

    public static synchronized PlayModeLocalDataSource getInstance() {
        if (sInstance == null) {
            synchronized (PlayModeLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new PlayModeLocalDataSource();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void savePlayMode(PlayMode mode) {
        if (mode == null) {
            return;
        }
        mSharedPrefs.put(PREF_LOOP_MODE, mode.getLoopMode());
    }

    @Override
    public PlayMode getPlayMode() {
        PlayMode mode = new PlayMode();
        mode.setLoopMode(mSharedPrefs.get(PREF_LOOP_MODE, Integer.class));
        return mode;
    }
}
