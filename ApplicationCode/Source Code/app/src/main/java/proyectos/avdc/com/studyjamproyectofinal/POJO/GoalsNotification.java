package proyectos.avdc.com.studyjamproyectofinal.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 5/9/15.
 */
public class GoalsNotification {
    @SerializedName("id_goal")
    private int id_goal;

    @SerializedName("player_name")
    private String player_name;

    @SerializedName("player_shortname")
    private String player_shortname;

    @SerializedName("id_team")
    private int id_team;

    @SerializedName("goal_type")
    private int goal_type;

    @SerializedName("time")
    private int time;

    @SerializedName("reg_time")
    private String reg_time;

    @SerializedName("id_home")
    private int id_home;

    @SerializedName("id_away")
    private int id_away;

    @SerializedName("id")
    private int id;

    public GoalsNotification() {
    }

    public GoalsNotification(int id_goal, String player_name, String player_shortname, int id_team, int goal_type, int time, String reg_time, int id_home, int id_away, int id) {
        this.id_goal = id_goal;
        this.player_name = player_name;
        this.player_shortname = player_shortname;
        this.id_team = id_team;
        this.goal_type = goal_type;
        this.time = time;
        this.reg_time = reg_time;
        this.id_home = id_home;
        this.id_away = id_away;
        this.id = id;
    }

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

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public int getId_home() {
        return id_home;
    }

    public void setId_home(int id_home) {
        this.id_home = id_home;
    }

    public int getId_away() {
        return id_away;
    }

    public void setId_away(int id_away) {
        this.id_away = id_away;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
