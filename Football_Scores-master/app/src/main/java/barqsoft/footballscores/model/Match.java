package barqsoft.footballscores.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josiah Hadley on 12/3/2015.
 */
public class Match implements Parcelable{

    private String homeTeam;
    private String awayTeam;
    private String score;

    public Match(String homeTeam, String awayTeam, String score){
        setHomeTeam(homeTeam);
        setAwayTeam(awayTeam);
        setScore(score);
    }

    public Match(Parcel in){
        setHomeTeam(in.readString());
        setAwayTeam(in.readString());
        setScore(in.readString());
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getHomeTeam());
        dest.writeString(getAwayTeam());
        dest.writeString(getScore());
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>(){
        public Match createFromParcel(Parcel in){
            return new Match(in);
        }

        public Match[] newArray(int size){
            return new Match[size];
        }
    };
}
