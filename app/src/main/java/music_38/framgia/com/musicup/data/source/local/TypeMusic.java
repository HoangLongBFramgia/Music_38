package music_38.framgia.com.musicup.data.source.local;

import android.support.annotation.StringDef;

import static music_38.framgia.com.musicup.data.source.local.TypeMusic.ALL_AUDIO;
import static music_38.framgia.com.musicup.data.source.local.TypeMusic.ALL_MUSIC;
import static music_38.framgia.com.musicup.data.source.local.TypeMusic.ALTERNATIVE_ROCK;
import static music_38.framgia.com.musicup.data.source.local.TypeMusic.AMBIENT;
import static music_38.framgia.com.musicup.data.source.local.TypeMusic.CLASSICAL;
import static music_38.framgia.com.musicup.data.source.local.TypeMusic.COUNTRY;

@StringDef({ALL_MUSIC, ALL_AUDIO, AMBIENT, COUNTRY, ALTERNATIVE_ROCK, CLASSICAL})
public @interface TypeMusic {
    String ALL_MUSIC = "all-music";
    String ALL_AUDIO = "all-audio";
    String AMBIENT = "ambient";
    String COUNTRY = "country";
    String ALTERNATIVE_ROCK = "alternative-rock";
    String CLASSICAL = "classical";
}
