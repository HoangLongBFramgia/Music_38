package music_38.framgia.com.musicup.screen.playlist;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

public class PlayListContract {
    interface View {

        void getDataTrackSuccess(Genre genre);

        void hideProgress();

        void displayPlaylistBanner(String urlImage);

        void getDataError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void getTrackByGenre(String type);
    }
}
