package proyectos.avdc.com.studyjamproyectofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.NavegacionItem;
import proyectos.avdc.com.studyjamproyectofinal.R;

/**
 * Created by andresvasquez on 2/21/15.
 */
public class NavegacionAdapter extends BaseAdapter {

    private List<NavegacionItem> items;
    private Context context;

    public NavegacionAdapter(List<NavegacionItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return items.get(position).getId();
    }

    static class ViewHolder {
        LinearLayout fondo;
        TextView texto;
        ImageView icono;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.layout_nagevacion, null);
            holder.fondo = (LinearLayout) convertView.findViewById(R.id.fondo);
            holder.texto = (TextView) convertView.findViewById(R.id.texto);
            holder.icono = (ImageView) convertView.findViewById(R.id.icono);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        NavegacionItem item = items.get(position);
        holder.texto.setText(item.getTexto());
        holder.icono.setImageResource(item.getIcono());

        if(item.isSeleccionado())
            holder.fondo.setBackgroundColor(context.getResources().getColor(R.color.event_color_03));
        else
            holder.fondo.setBackgroundColor(context.getResources().getColor(R.color.blanco));

        return convertView;
    }
}
