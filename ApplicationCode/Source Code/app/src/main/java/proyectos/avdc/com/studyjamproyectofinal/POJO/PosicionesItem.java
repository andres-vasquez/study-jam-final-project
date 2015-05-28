package proyectos.avdc.com.studyjamproyectofinal.POJO;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class PosicionesItem {

    public int intIdPosicion;
    public EquiposItem equipo;
    public int intPj;
    public int intPg;
    public int intPp;
    public int intPe;
    public int intGd;
    public int intPts;

    public PosicionesItem(int intIdPosicion, EquiposItem equipo, int intPj, int intPg, int intPp, int intPe, int intGd, int intPts) {
        this.intIdPosicion = intIdPosicion;
        this.equipo = equipo;
        this.intPj = intPj;
        this.intPg = intPg;
        this.intPp = intPp;
        this.intPe = intPe;
        this.intGd = intGd;
        this.intPts = intPts;
    }

    public int getIntIdPosicion() {
        return intIdPosicion;
    }

    public void setIntIdPosicion(int intIdPosicion) {
        this.intIdPosicion = intIdPosicion;
    }

    public EquiposItem getEquipo() {
        return equipo;
    }

    public void setEquipo(EquiposItem equipo) {
        this.equipo = equipo;
    }

    public int getIntPj() {
        return intPj;
    }

    public void setIntPj(int intPj) {
        this.intPj = intPj;
    }

    public int getIntPg() {
        return intPg;
    }

    public void setIntPg(int intPg) {
        this.intPg = intPg;
    }

    public int getIntPp() {
        return intPp;
    }

    public void setIntPp(int intPp) {
        this.intPp = intPp;
    }

    public int getIntPe() {
        return intPe;
    }

    public void setIntPe(int intPe) {
        this.intPe = intPe;
    }

    public int getIntGd() {
        return intGd;
    }

    public void setIntGd(int intGd) {
        this.intGd = intGd;
    }

    public int getIntPts() {
        return intPts;
    }

    public void setIntPts(int intPts) {
        this.intPts = intPts;
    }
}
