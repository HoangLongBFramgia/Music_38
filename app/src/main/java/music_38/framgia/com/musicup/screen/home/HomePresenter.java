package music_38.framgia.com.musicup.screen.home;

import music_38.framgia.com.musicup.data.model.Genre;


public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

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

    @Override
    public void getItemGenre(Genre mItemGenre) {
    }
}
