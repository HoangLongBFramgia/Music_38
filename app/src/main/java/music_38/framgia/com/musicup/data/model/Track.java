package music_38.framgia.com.musicup.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Track implements Parcelable {

    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtworkUrl;
    private String mGenre;
    private String mDownloadURL;
    private String mArtist;
    private boolean mDownloadable;

    public Track(JSONObject jsonObject) throws JSONException {
        this.mId = jsonObject.getInt(TrackJSON.ID);
        this.mTitle = jsonObject.getString(TrackJSON.TITLE);
        this.mArtworkUrl = jsonObject.getString(TrackJSON.ARTWORK_URL);
        this.mDuration = jsonObject.getInt(TrackJSON.DURATION);
        this.mGenre = jsonObject.getString(TrackJSON.GENRE);
        this.mDownloadable = jsonObject.getBoolean(TrackJSON.DOWNLOADABLE);
        this.mDownloadURL = jsonObject.getString(TrackJSON.DOWNLOAD_URL);
        this.mArtist = jsonObject.getJSONObject(TrackJSON.USER)
                .getString(TrackJSON.USER_NAME);
    }

    private Track(TrackBuilder trackBuilder) {
        this.mId = trackBuilder.mId;
        this.mTitle = trackBuilder.mTitle;
        this.mArtworkUrl = trackBuilder.mArtworkUrl;
        this.mDuration = trackBuilder.mDuration;
        this.mGenre = trackBuilder.mGenre;
        this.mDownloadable = trackBuilder.mDownloadable;
        this.mDownloadURL = trackBuilder.mDownloadURL;
        this.mArtist = trackBuilder.mArtist;
    }

    private Track(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mArtworkUrl = in.readString();
        mDuration = in.readInt();
        mGenre = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadURL = in.readString();
        mArtist = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mArtworkUrl);
        parcel.writeInt(mDuration);
        parcel.writeString(mGenre);
        parcel.writeByte((byte) (mDownloadable ? 1 : 0));
        parcel.writeString(mDownloadURL);
        parcel.writeString(mArtist);
    }

    public static class TrackBuilder {
        private int mId;
        private String mTitle;
        private String mArtworkUrl;
        private int mDuration;
        private String mGenre;
        private boolean mDownloadable;
        private String mDownloadURL;
        private String mArtist;

        public TrackBuilder setId(int id) {
            mId = id;
            return this;
        }

        public TrackBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public TrackBuilder setArtworkUrl(String artworkUrl) {
            mArtworkUrl = artworkUrl;
            return this;
        }

        public TrackBuilder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public TrackBuilder setGenre(String genre) {
            mGenre = genre;
            return this;
        }

        public TrackBuilder setDownloadable(boolean downloadable) {
            mDownloadable = downloadable;
            return this;
        }

        public TrackBuilder setDownloadURL(String downloadURL) {
            mDownloadURL = downloadURL;
            return this;
        }

        public TrackBuilder setArtist(String artist) {
            mArtist = artist;
            return this;
        }

        public Track build() {
            return new Track(this);
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadURL() {
        return mDownloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        mDownloadURL = downloadURL;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public class TrackJSON {
        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String URI = "url";
        private static final String ARTWORK_URL = "artwork_url";
        private static final String DURATION = "duration";
        private static final String GENRE = "genre";
        private static final String DOWNLOADABLE = "downloadable";
        private static final String DOWNLOAD_URL = "download_url";
        private static final String USER_NAME = "username";
        private static final String USER = "user";
        public static final String COLLECTION = "collection";
        public static final String TRACK = "track";
    }
}
