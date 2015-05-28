package proyectos.avdc.com.studyjamproyectofinal.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class RankingItem {

    @SerializedName("name")
    public String name;

    @SerializedName("played")
    public String played;

    @SerializedName("won")
    public String won;

    @SerializedName("drawn")
    public String drawn;

    @SerializedName("lost")
    public String lost;

    @SerializedName("goals_for")
    public String goals_for;

    @SerializedName("goals_against")
    public String goals_against;

    @SerializedName("goals_difference")
    public String goals_difference;

    @SerializedName("points")
    public String points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getDrawn() {
        return drawn;
    }

    public void setDrawn(String drawn) {
        this.drawn = drawn;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getGoals_for() {
        return goals_for;
    }

    public void setGoals_for(String goals_for) {
        this.goals_for = goals_for;
    }

    public String getGoals_against() {
        return goals_against;
    }

    public void setGoals_against(String goals_against) {
        this.goals_against = goals_against;
    }

    public String getGoals_difference() {
        return goals_difference;
    }

    public void setGoals_difference(String goals_difference) {
        this.goals_difference = goals_difference;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
