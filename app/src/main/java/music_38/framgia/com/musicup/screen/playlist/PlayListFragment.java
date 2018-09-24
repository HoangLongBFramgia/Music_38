package music_38.framgia.com.musicup.screen.playlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {

    public static final String TAG = PlayListFragment.class.getName();
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";

    public static PlayListFragment newInstance(Genre genre) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_GENRE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlayListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

}
