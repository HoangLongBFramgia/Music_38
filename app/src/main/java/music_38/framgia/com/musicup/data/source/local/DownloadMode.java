package music_38.framgia.com.musicup.data.source.local;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.source.local.DownloadMode.DOWNLOAD_ABLE;
import static music_38.framgia.com.musicup.data.source.local.DownloadMode.DOWNLOAD_DISABLE;

@IntDef({DOWNLOAD_ABLE, DOWNLOAD_DISABLE})
public @interface DownloadMode {
    int DOWNLOAD_ABLE = 1;
    int DOWNLOAD_DISABLE = 0;
}
