package music_38.framgia.com.musicup.utils;

import music_38.framgia.com.musicup.R;

public class Constants {

    public class Genre {
        public static final String BUNDLE_GENRE = "TYPE_GENRE";
        public static final String ALL_MUSIC = "All Music";
        public static final String ALL_AUDIO = "All Audio";
        public static final String ALTERNATIVE_ROCK = "Alternative Rock";
        public static final String AMBIENT = "Ambient";
        public static final String CLASSICAL = "Classical";
        public static final String COUNTRY = "Country";
        public static final String MINUS = "-";
        public static final String SPACE = " ";
        public static final int IMAGE_ALL_MUSIC
                = R.drawable.image_all_music;
        public static final int IMAGE_ALL_AUDIO
                = R.drawable.image_all_audio;
        public static final int IMAGE_ALTERNATIVE_ROCK
                = R.drawable.image_top_rock;
        public static final int IMAGE_COUNTRY
                = R.drawable.image_top_country;
        public static final int IMAGE_AMBIENT
                = R.drawable.image_ambient;
        public static final int IMAGE_CLASSICAL
                = R.drawable.image_all_music;
    }

    public class SoundCloud {
        public static final String BASE_URL = "https://api-v2.soundcloud.com/";
        public static final String PARAM_KIND = "charts?kind=top";
        public static final String PARAM_GENRE = "&genre=soundcloud";
        public static final String PARAM_CLIENT_ID = "&client_id=";
        public static final String PARAM_TYPE = "%3Agenres%3A";
        public static final String PARAM_LIMIT = "&limit=";
        public static final String LIMIT = "20";
        public static final String METHOD_GET = "GET";
        public static final String SEARCH = "search";
        public static final String QUERY_SEARCH = "/tracks?q=";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
    }
}
