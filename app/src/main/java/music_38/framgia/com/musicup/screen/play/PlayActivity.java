package music_38.framgia.com.musicup.screen.play;

import android.os.Bundle;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BaseActivity;
import music_38.framgia.com.musicup.service.SongServiceContract;

public class PlayActivity extends BaseActivity implements SongServiceContract.OnMediaPlayerChangeListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {

    }

    @Override
    public void onTrackChange(Track track) {

    }

    @Override
    public void onLoopChange(int state) {

    }

    @Override
    public void onShuffleChange(int state) {

    }

    @Override
    public void onDownLoadChange(int state) {

    }

    @Override
    public void onDurationSong(int duration) {

    }

    @Override
    public void onCurrentDurationSong(int duration) {

    }

    @Override
    public void onFavoritesChange(int state) {

    }
}
