package music_38.framgia.com.musicup.data.source.local;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.source.local.TypeTrack.TYPE_OFFLINE;
import static music_38.framgia.com.musicup.data.source.local.TypeTrack.TYPE_ONLINE;

@IntDef({TYPE_ONLINE, TYPE_OFFLINE})
public @interface TypeTrack {
    int TYPE_ONLINE = 1;
    int TYPE_OFFLINE = 0;
}
