package proyectos.avdc.com.studyjamproyectofinal.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 5/9/15.
 */
public class Goals {

    @SerializedName("id_goal")
    public int id_goal;

    @SerializedName("player_name")
    public String player_name;

    @SerializedName("player_shortname")
    public String player_shortname;

    @SerializedName("id_team")
    public int id_team;

    @SerializedName("goal_type")
    public int goal_type;

    @SerializedName("time")
    public int time;

    @SerializedName("id")
    public int id;

    public int getId_goal() {
        return id_goal;
    }

    public void setId_goal(int id_goal) {
        this.id_goal = id_goal;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_shortname() {
        return player_shortname;
    }

    public void setPlayer_shortname(String player_shortname) {
        this.player_shortname = player_shortname;
    }

    public int getId_team() {
        return id_team;
    }

    public void setId_team(int id_team) {
        this.id_team = id_team;
    }

    public int getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(int goal_type) {
        this.goal_type = goal_type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
