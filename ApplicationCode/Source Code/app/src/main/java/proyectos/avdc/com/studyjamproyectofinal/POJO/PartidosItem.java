package proyectos.avdc.com.studyjamproyectofinal.POJO;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class PartidosItem {

    public int intIdPartido;
    public String strNombreEquipo1;
    public String strNombreEquipo2;
    public String strTextoMarcador;
    public String strTextoCentral;
    public int intImgEquipo1;
    public int intImgEquipo2;

    public MatchItem match;

    public PartidosItem() {
    }

    public PartidosItem(int intIdPartido, String strNombreEquipo1, String strNombreEquipo2, String strTextoMarcador, String strTextoCentral, int intImgEquipo1, int intImgEquipo2, MatchItem match) {
        this.intIdPartido = intIdPartido;
        this.strNombreEquipo1 = strNombreEquipo1;
        this.strNombreEquipo2 = strNombreEquipo2;
        this.strTextoMarcador = strTextoMarcador;
        this.strTextoCentral = strTextoCentral;
        this.intImgEquipo1 = intImgEquipo1;
        this.intImgEquipo2 = intImgEquipo2;
        this.match=match;
    }

    public int getIntIdPartido() {
        return intIdPartido;
    }

    public void setIntIdPartido(int intIdPartido) {
        this.intIdPartido = intIdPartido;
    }

    public String getStrNombreEquipo1() {
        return strNombreEquipo1;
    }

    public void setStrNombreEquipo1(String strNombreEquipo1) {
        this.strNombreEquipo1 = strNombreEquipo1;
    }

    public String getStrNombreEquipo2() {
        return strNombreEquipo2;
    }

    public void setStrNombreEquipo2(String strNombreEquipo2) {
        this.strNombreEquipo2 = strNombreEquipo2;
    }

    public String getStrTextoMarcador() {
        return strTextoMarcador;
    }

    public void setStrTextoMarcador(String strTextoMarcador) {
        this.strTextoMarcador = strTextoMarcador;
    }

    public String getStrTextoCentral() {
        return strTextoCentral;
    }

    public void setStrTextoCentral(String strTextoCentral) {
        this.strTextoCentral = strTextoCentral;
    }

    public int getIntImgEquipo1() {
        return intImgEquipo1;
    }

    public void setIntImgEquipo1(int intImgEquipo1) {
        this.intImgEquipo1 = intImgEquipo1;
    }

    public int getIntImgEquipo2() {
        return intImgEquipo2;
    }

    public void setIntImgEquipo2(int intImgEquipo2) {
        this.intImgEquipo2 = intImgEquipo2;
    }

    public MatchItem getMatch() {
        return match;
    }

    public void setMatch(MatchItem match) {
        this.match = match;
    }
}
