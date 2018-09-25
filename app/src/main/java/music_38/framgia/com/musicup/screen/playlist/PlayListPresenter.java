package music_38.framgia.com.musicup.screen.playlist;

import music_38.framgia.com.musicup.data.repository.TrackRepository;

public class PlayListPresenter implements PlayListContract.Presenter {
    private PlayListContract.View mView;
    private TrackRepository mTrackRepository;

    public PlayListPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getTrackByGenre(String type) {
    }

    @Override
    public void setView(Object view) {
        mView = (PlayListContract.View) view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }
}
