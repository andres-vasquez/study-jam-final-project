package proyectos.avdc.com.studyjamproyectofinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.DetalleEquipo;
import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class EquiposAdapter extends RecyclerView.Adapter<EquiposViewHolder> {
    private List<EquiposItem> items;
    private List<EquiposItem> itemsFiltrados;
    private Context context;

    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public EquiposAdapter(List<EquiposItem> items, Context context) {
        this.items = items;
        this.itemsFiltrados=items;
        this.context = context;
    }

    public void updateList(EquiposItem data) {
        //this.items.add(data);
        this.itemsFiltrados.add(data);
        notifyDataSetChanged();
    }

    public long getItemId(int position) {
        return itemsFiltrados.get(position).getIntIdEquipo();
    }

    public int getItemCount() {
        return itemsFiltrados.size();
    }

    @Override
    public void onBindViewHolder(final EquiposViewHolder eventosViewHolder, int i) {
        final EquiposItem exp = itemsFiltrados.get(i);
        eventosViewHolder.txtNombreEquipo.setText(exp.getStrNombreEquipo());
        eventosViewHolder.imgEquipo.setImageResource(new Media(context).ObtenerBanderasEquipo(exp.getStrNombreEquipo()));

        eventosViewHolder.imgEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DetalleEquipo.class);
                intent.putExtra("objEquipo", new Gson().toJson(exp));
                context.startActivity(intent);
            }
        });
    }

    public EquiposViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_equipos, viewGroup, false);

        return new EquiposViewHolder(itemView);
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            List<EquiposItem> list = items;
            int count = list.size();
            final ArrayList<EquiposItem> nlist = new ArrayList<EquiposItem>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getStrNombreEquipo();

                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemsFiltrados = (ArrayList<EquiposItem>) results.values;
            notifyDataSetChanged();
        }

    }
}
