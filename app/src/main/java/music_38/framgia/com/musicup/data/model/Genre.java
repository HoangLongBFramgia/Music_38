package music_38.framgia.com.musicup.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {

    private String mTitle;
    private int mImage;

    public Genre() {
    }

    public Genre(String title, int image) {
        mTitle = title;
        mImage = image;
    }

    public Genre(Parcel in) {
        mTitle = in.readString();
        mImage = in.readInt();
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
        parcel.writeInt(mImage);
    }

    public static class GenreBuilder {
        private String mTitle;
        private int mImage;

        public GenreBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public GenreBuilder setImage(int image) {
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

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}

