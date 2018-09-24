package music_38.framgia.com.musicup.screen.home;

import music_38.framgia.com.musicup.data.repository.TrackRepository;


public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private TrackRepository mTrackRepository;

    HomePresenter(TrackRepository mTrackRepository) {
        this.mTrackRepository = mTrackRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getGenre() {
    }
}
