package proyectos.avdc.com.studyjamproyectofinal.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class EquiposItem {

    @SerializedName("id")
    public int intIdEquipo;

    @SerializedName("name")
    public String strNombreEquipo;

    @SerializedName("badge")
    public String strBadge;
    public int intImgEquipo;

    public EquiposItem() {
    }

    public EquiposItem(int intIdEquipo, String strNombreEquipo, String strBadge) {
        this.intIdEquipo = intIdEquipo;
        this.strNombreEquipo = strNombreEquipo;
        this.strBadge = strBadge;
    }

    public EquiposItem(int intIdEquipo, String strNombreEquipo, String strBadge, int intImgEquipo) {
        this.intIdEquipo = intIdEquipo;
        this.strNombreEquipo = strNombreEquipo;
        this.strBadge = strBadge;
        this.intImgEquipo = intImgEquipo;
    }

    public int getIntIdEquipo() {
        return intIdEquipo;
    }

    public void setIntIdEquipo(int intIdEquipo) {
        this.intIdEquipo = intIdEquipo;
    }

    public String getStrNombreEquipo() {
        return strNombreEquipo;
    }

    public void setStrNombreEquipo(String strNombreEquipo) {
        this.strNombreEquipo = strNombreEquipo;
    }

    public int getIntImgEquipo() {
        return intImgEquipo;
    }

    public void setIntImgEquipo(int intImgEquipo) {
        this.intImgEquipo = intImgEquipo;
    }

    public String getStrBadge() {
        return strBadge;
    }

    public void setStrBadge(String strBadge) {
        this.strBadge = strBadge;
    }
}
