package proyectos.avdc.com.studyjamproyectofinal.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import proyectos.avdc.com.studyjamproyectofinal.R;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class EquiposViewHolder extends RecyclerView.ViewHolder{

    protected TextView txtNombreEquipo;
    protected ImageView imgEquipo;

    public EquiposViewHolder(View itemView) {
        super(itemView);
        txtNombreEquipo=(TextView)itemView.findViewById(R.id.txtNombreEquipo);
        imgEquipo=(ImageView)itemView.findViewById(R.id.imgEquipo);
    }
}
