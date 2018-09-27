package music_38.framgia.com.musicup.data.repository;


import music_38.framgia.com.musicup.data.source.PlayModeDataSource;
import music_38.framgia.com.musicup.data.source.local.PlayMode;
import music_38.framgia.com.musicup.data.source.local.PlayModeLocalDataSource;

public class PlayModeRepository implements PlayModeDataSource {

    private static PlayModeRepository sInstance;
    private PlayModeLocalDataSource mDataSource;

    public PlayModeRepository(PlayModeLocalDataSource dataSource) {
        mDataSource = dataSource;
    }

    public static PlayModeRepository getInstance(PlayModeLocalDataSource dataSource) {
        if (sInstance == null) {
            synchronized (PlayModeRepository.class) {
                if (sInstance == null) {
                    sInstance = new PlayModeRepository(dataSource);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void savePlayMode(PlayMode mode) {
        mDataSource.savePlayMode(mode);
    }

    @Override
    public PlayMode getPlayMode() {
        return mDataSource.getPlayMode();
    }
}
