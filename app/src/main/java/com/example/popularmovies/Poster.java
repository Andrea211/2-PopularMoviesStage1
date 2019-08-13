package com.example.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Poster implements Parcelable {

    public static final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>() {
        public Poster createFromParcel(Parcel src) {
            return new Poster(src);
        }

        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };
    private String originalTitle;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private Double voterAverage;
    private Double voteCount;
    private String originalLanguage;

    public Poster() {
    }

    public Poster(Parcel parcel) {
        originalTitle = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        releaseDate = parcel.readString();
        voterAverage = parcel.readDouble();
        voteCount = parcel.readDouble();
        originalLanguage = parcel.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
        return POSTER_BASE_URL + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoterAverage() {
        return voterAverage;
    }

    public void setVoterAverage(Double voterAverage) {
        this.voterAverage = voterAverage;
    }

    public Double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeDouble(voterAverage);
        dest.writeDouble(voteCount);
        dest.writeString(originalLanguage);
    }
}
