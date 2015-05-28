package proyectos.avdc.com.studyjamproyectofinal.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import proyectos.avdc.com.studyjamproyectofinal.R;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class PartidosViewHolder extends RecyclerView.ViewHolder{

    protected TextView txtNombreEquipo1;
    protected TextView txtNombreEquipo2;
    protected TextView txtMarcador;
    protected TextView txtCentral;
    protected ImageView imgEquipo1;
    protected ImageView imgEquipo2;

    public PartidosViewHolder(View itemView) {
        super(itemView);
        txtNombreEquipo1=(TextView)itemView.findViewById(R.id.txtEquipo1);
        txtNombreEquipo2=(TextView)itemView.findViewById(R.id.txtEquipo2);
        txtMarcador=(TextView)itemView.findViewById(R.id.txtMarcador);
        txtCentral=(TextView)itemView.findViewById(R.id.txtCentral);
        imgEquipo1=(ImageView)itemView.findViewById(R.id.imgEquipo1);
        imgEquipo2=(ImageView)itemView.findViewById(R.id.imgEquipo2);
    }
}
