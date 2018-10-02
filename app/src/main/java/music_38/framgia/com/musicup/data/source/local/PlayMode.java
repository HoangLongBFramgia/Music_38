package music_38.framgia.com.musicup.data.source.local;

import music_38.framgia.com.musicup.service.LoopMode;
import music_38.framgia.com.musicup.service.ShuffleMode;

public class PlayMode {
    private int mLoopMode;
    private int mShuffleMode;

    public int getLoopMode() {
        return mLoopMode;
    }

    public void setLoopMode(@LoopMode int loopMode) {
        mLoopMode = loopMode;
    }

    public int getShuffleMode() {
        return mShuffleMode;
    }

    public void setShuffleMode(@ShuffleMode int shuffleMode) {
        mShuffleMode = shuffleMode;
    }
}
