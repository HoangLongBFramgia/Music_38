package music_38.framgia.com.musicup.screen.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.PlayModeRepository;
import music_38.framgia.com.musicup.data.source.local.PlayMode;
import music_38.framgia.com.musicup.data.source.local.PlayModeLocalDataSource;
import music_38.framgia.com.musicup.screen.base.BaseActivity;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.service.SongServiceContract;
import music_38.framgia.com.musicup.utils.Utils;

import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ALL;
import static music_38.framgia.com.musicup.service.LoopMode.LOOP_ONE;
import static music_38.framgia.com.musicup.service.LoopMode.NO_LOOP;
import static music_38.framgia.com.musicup.service.ShuffleMode.NO_SHUFFLE;
import static music_38.framgia.com.musicup.service.ShuffleMode.SHUFFLE_ALL;

public class PlayActivity extends BaseActivity implements View.OnClickListener
        , SongServiceContract.OnMediaPlayerChangeListener, SeekBar.OnSeekBarChangeListener {

    private static final int DELAY_SEEK_BAR_UPDATE = 100;
    private SimpleDraweeView mImageBackGround;
    private SimpleDraweeView mImageSong;
    private TextView mTextNameSong;
    private TextView mTextAuthor;
    private TextView mTextCurrent;
    private TextView mTextDuration;
    private SeekBar mSeekBar;
    private ImageView mImageClose;
    private ImageView mImageTimer;
    private ImageView mImageDownload;
    private ImageView mImageFav;
    private ImageView mImageBottom;
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private ImageView mImagePrevious;
    private ImageView mImageLoop;
    private ImageView mImageShuffle;
    private SongServiceContract.ISongService mISongService;
    private boolean mMusicBound;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mPosition;
    private PlayPresenter mPlayPresenter;
    private PlayMode mPlayMode;

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            SongService songService = binder.getService();
            mISongService = songService.getISongService();
            Track track = songService.getTrackCurrent();
            songService.setOnMediaPlayerChangeListener(PlayActivity.this);
            if (track != null) {
                updateUI(track);
            }
            mHandler.postDelayed(mRunnable, DELAY_SEEK_BAR_UPDATE);
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play;
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent mPlayIntent = new Intent(this, SongService.class);
        bindService(mPlayIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (!mMusicBound) {
            unbindService(mMusicConnection);
            mMusicBound = false;
        }
        super.onStop();
    }

    @Override
    protected void initComponent() {
        mImageBackGround = findViewById(R.id.image_background);
        mImageSong = findViewById(R.id.image_song);
        mImageClose = findViewById(R.id.image_close);
        mImageTimer = findViewById(R.id.image_timer);
        mTextNameSong = findViewById(R.id.text_name_song);
        mTextAuthor = findViewById(R.id.text_author);
        mImageDownload = findViewById(R.id.icon_download);
        mImageFav = findViewById(R.id.icon_fav);
        mImageBottom = findViewById(R.id.icon_bottom);
        mImagePlay = findViewById(R.id.ic_audio_pause);
        mTextCurrent = findViewById(R.id.text_current);
        mTextDuration = findViewById(R.id.text_duration);
        mImagePrevious = findViewById(R.id.image_previous);
        mImageNext = findViewById(R.id.image_next);
        mSeekBar = findViewById(R.id.sb_player);
        mImageLoop = findViewById(R.id.image_loop);
        mImageShuffle = findViewById(R.id.ic_shuffle);

        initListener();
    }

    public static Intent getPlayerIntent(Context context) {
        return new Intent(context, PlayActivity.class);
    }

    private void initListener() {
        mImageClose.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImageTimer.setOnClickListener(this);
        mImageBottom.setOnClickListener(this);
        mImageFav.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        PlayModeLocalDataSource playModeLocalDataSource = new PlayModeLocalDataSource();
        PlayModeRepository playModeRepository = new PlayModeRepository(playModeLocalDataSource);
        mPlayPresenter = new PlayPresenter(playModeRepository);
        mPlayMode = mPlayPresenter.getPlayMode();
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        if (isPlaying) {
            mImagePlay.setImageResource(R.drawable.ic_pause_mini);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play_mini);
        }
    }

    @Override
    public void onTrackChange(Track track) {
        updateUI(track);
    }

    @Override
    public void onLoopChange(int state) {
        updateStateLoop(state);
    }

    @Override
    public void onShuffleChange(int state) {
        updateStateShuffle(state);
    }

    @Override
    public void onDownLoadChange(int state) {

    }

    @Override
    public void onDurationSong(int duration) {
        mTextDuration.setText(Utils.convertTime(duration));
    }

    @Override
    public void onCurrentDurationSong(int duration) {

    }

    @Override
    public void onFavoritesChange(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                onBackPressed();
                break;
            case R.id.image_timer:
                break;
            case R.id.icon_download:
                mISongService.downloadCurrentTrack();
                break;
            case R.id.icon_bottom:
                break;
            case R.id.icon_fav:
                mISongService.favoritesSong();
                break;
            case R.id.ic_audio_pause:
                mISongService.playPauseSong();
                break;
            case R.id.image_next:
                mISongService.nextSong();
                break;
            case R.id.image_previous:
                mISongService.previousSong();
                break;
            case R.id.image_loop:
                int loopType = NO_LOOP;
                switch (mPlayMode.getLoopMode()) {
                    case LOOP_ONE:
                        loopType = LOOP_ALL;
                        break;
                    case LOOP_ALL:
                        loopType = NO_LOOP;
                        break;
                    case NO_LOOP:
                        loopType = LOOP_ONE;
                        break;
                }
                mPlayMode.setLoopMode(loopType);
                mPlayPresenter.savePlayMode(mPlayMode);
                mISongService.loopSong(mPlayMode.getLoopMode());
                break;
            case R.id.ic_shuffle:
                int shuffleType = NO_SHUFFLE;
                switch (mPlayMode.getShuffleMode()) {
                    case SHUFFLE_ALL:
                        shuffleType = NO_SHUFFLE;
                        break;
                    case NO_SHUFFLE:
                        shuffleType = SHUFFLE_ALL;
                        break;
                }
                mPlayMode.setShuffleMode(shuffleType);
                mPlayPresenter.savePlayMode(mPlayMode);
                mISongService.shuffleSong(mPlayMode.getShuffleMode());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mPosition = i;
        mSeekBar.setProgress(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mISongService.seekTo(mPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void updateUI(Track track) {
        //UPDATE VIEW
        mTextAuthor.setText(track.getArtist());
        mTextNameSong.setText(track.getTitle());
        Uri uri = Uri.parse(track.getArtworkUrl());
        Utils.roundImageWithFresco(getApplicationContext(), mImageSong, uri);
        Utils.blurImageWithFresco(mImageBackGround, uri);
        //UPDATE DURATION
        mTextDuration.setText(Utils.convertTime(mISongService.getDurationSong()));
        mTextCurrent.setText(Utils.convertTime(mISongService.getCurrentDurationSong()));
        //UPDATE SEEK BAR
        if (track.getDuration() != 0) {
            mTextDuration.setText(Utils.convertTime(track.getDuration()));
            mSeekBar.setMax(track.getDuration());
        } else {
            mTextDuration.setText(Utils.convertTime(track.getDuration()));
            mSeekBar.setMax(mISongService.getDurationSong());
        }
        updateStateLoop(mPlayPresenter.getPlayMode().getLoopMode());
        mISongService.shuffleSong(mPlayPresenter.getPlayMode().getShuffleMode());
        updateSeekBar();
    }

    public void updateSeekBar() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentDuration = mISongService.getCurrentDurationSong();
                mSeekBar.setMax(mISongService.getDurationSong());
                mSeekBar.setProgress(currentDuration);
                mTextCurrent.setText(Utils.convertTime(mISongService.getCurrentDurationSong()));
                mHandler.postDelayed(mRunnable, DELAY_SEEK_BAR_UPDATE);
            }
        };
    }

    private void updateStateLoop(int state) {
        switch (state) {
            case LOOP_ONE:
                mImageLoop.setImageResource(R.drawable.ic_loop_one);
                break;
            case LOOP_ALL:
                mImageLoop.setImageResource(R.drawable.ic_loop_all);
                break;
            case NO_LOOP:
                mImageLoop.setImageResource(R.drawable.ic_loop);
                break;
        }
    }

    private void updateStateShuffle(int state) {
        switch (state) {
            case SHUFFLE_ALL:
                mImageShuffle.setImageResource(R.drawable.ic_shuffle_active);
                break;
            case NO_SHUFFLE:
                mImageShuffle.setImageResource(R.drawable.ic_shuffle);
                break;
        }
    }
}
