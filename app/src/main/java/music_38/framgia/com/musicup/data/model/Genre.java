package music_38.framgia.com.musicup.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {

    private String mTitle;
    private String mImage;

    public Genre() {
    }

    public Genre(String title, String image) {
        mTitle = title;
        mImage = image;
    }

    public Genre(Parcel in) {
        mTitle = in.readString();
        mImage = in.readString();
    }

    public Genre(GenreBuilder genreBuilder) {
        mTitle = genreBuilder.mTitle;
        mImage = genreBuilder.mImage;
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mImage);
    }

    public static class GenreBuilder {
        private String mTitle;
        private String mImage;

        public GenreBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public GenreBuilder setImage(String image) {
            mImage = image;
            return this;
        }

        public Genre build() {
            return new Genre(this);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}

