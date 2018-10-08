package music_38.framgia.com.musicup.data.source.local;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.source.local.FavoritesMode.FAVORITES;
import static music_38.framgia.com.musicup.data.source.local.FavoritesMode.UN_FAVORITES;

@IntDef({FAVORITES, UN_FAVORITES})
public @interface FavoritesMode {
    int FAVORITES = 1;
    int UN_FAVORITES = 0;
}
