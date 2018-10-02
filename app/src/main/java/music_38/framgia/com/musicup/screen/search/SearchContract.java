package music_38.framgia.com.musicup.screen.search;


import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

public class SearchContract {
    interface View {

        void getDataTrackSuccess(Genre genre);

        void hideProgress();

        void getDataError(Exception exception);
    }

    interface Presenter extends BasePresenter {
        void getTrackBySearch(String searchKey);
    }
}
