package proyectos.avdc.com.studyjamproyectofinal.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class MatchItem {

    @SerializedName("time")
    public String time;

    @SerializedName("location")
    public String location;

    @SerializedName("city")
    public String city;

    @SerializedName("home_team")
    public String home_team;

    @SerializedName("home_badge")
    public String home_badge;

    @SerializedName("home_score")
    public String home_score;

    @SerializedName("away_team")
    public String away_team;

    @SerializedName("away_badge")
    public String away_badge;

    @SerializedName("away_score")
    public String away_score;

    @SerializedName("match_status")
    public String match_status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getHome_badge() {
        return home_badge;
    }

    public void setHome_badge(String home_badge) {
        this.home_badge = home_badge;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getAway_badge() {
        return away_badge;
    }

    public void setAway_badge(String away_badge) {
        this.away_badge = away_badge;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

}
