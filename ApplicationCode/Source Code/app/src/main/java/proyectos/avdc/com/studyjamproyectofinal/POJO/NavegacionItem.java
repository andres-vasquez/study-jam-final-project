package proyectos.avdc.com.studyjamproyectofinal.POJO;

/**
 * Created by andresvasquez on 2/21/15.
 */
public class NavegacionItem
{

    public NavegacionItem(long id, int icono, String texto) {
        this.id = id;
        this.icono = icono;
        this.texto = texto;
    }

    public long id;
    public int icono;
    public String texto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
