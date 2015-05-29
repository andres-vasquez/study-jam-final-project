package proyectos.avdc.com.studyjamproyectofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.PosicionesItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class PosicionesAdapter extends BaseAdapter{
    private List<PosicionesItem> items;
    private Context context;

    public PosicionesAdapter(List<PosicionesItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PosicionesItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getIntGd();
    }

    static class ViewHolder{
        TextView txtNombreEquipoSm;
        ImageView imImagenEquipoSm;
        TextView txtPj;
        TextView txtPg;
        TextView txtPp;
        TextView txtPe;
        TextView txtGd;
        TextView txtPts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder=new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_posiciones, null);

            holder.txtNombreEquipoSm = (TextView)convertView.findViewById(R.id.txtNombreEquipoSm);
            holder.imImagenEquipoSm = (ImageView)convertView.findViewById(R.id.imgEquipoSm);

            holder.txtPj = (TextView)convertView.findViewById(R.id.txtPj);
            holder.txtPg = (TextView)convertView.findViewById(R.id.txtPg);
            holder.txtPp = (TextView)convertView.findViewById(R.id.txtPp);
            holder.txtPe = (TextView)convertView.findViewById(R.id.txtPe);
            holder.txtGd = (TextView)convertView.findViewById(R.id.txtGd);
            holder.txtPts = (TextView)convertView.findViewById(R.id.txtPts);

            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }


        PosicionesItem item=items.get(position);
        EquiposItem equipo=item.getEquipo();
        holder.txtNombreEquipoSm.setText(equipo.getStrNombreEquipo());
        holder.imImagenEquipoSm.setImageResource(new Media(context).ObtenerBanderasEquipo(equipo.getStrNombreEquipo()));

        holder.txtPj.setText(""+item.getIntPj());
        holder.txtPg.setText(""+item.getIntPg());
        holder.txtPp.setText(""+item.getIntPp());
        holder.txtPe.setText(""+item.getIntPe());

        if(item.getIntGd()>0)
            holder.txtGd.setText("+"+item.getIntGd());

        holder.txtPts.setText(""+item.getIntPts());

        return convertView;
    }
}
