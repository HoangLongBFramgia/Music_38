package music_38.framgia.com.musicup.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import music_38.framgia.com.musicup.R;

import static com.facebook.common.internal.Preconditions.checkNotNull;

public class ScreenManager {
    public static void startFragment(FragmentManager fragmentManager, Fragment fragment, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);

        Fragment newFragment = fragmentManager.findFragmentByTag(tag);
        if (newFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .show(newFragment)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }
}
