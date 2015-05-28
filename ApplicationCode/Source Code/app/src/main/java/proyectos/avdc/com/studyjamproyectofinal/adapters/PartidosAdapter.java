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

import proyectos.avdc.com.studyjamproyectofinal.DetallePartido;
import proyectos.avdc.com.studyjamproyectofinal.POJO.PartidosItem;
import proyectos.avdc.com.studyjamproyectofinal.R;

/**
 * Created by andresvasquez on 4/25/15.
 */
public class PartidosAdapter extends RecyclerView.Adapter<PartidosViewHolder> {
    private List<PartidosItem> items;
    public List<PartidosItem> itemsFiltrados;
    private Context context;

    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public PartidosAdapter(List<PartidosItem> items, Context context) {
        this.items = items;
        this.itemsFiltrados=items;
        this.context = context;
    }

    public void updateList(PartidosItem data) {
        //this.items.add(data);
        this.itemsFiltrados.add(data);
        notifyDataSetChanged();
    }

    public void clearList() {
        //this.items.add(data);
        this.itemsFiltrados.clear();
        notifyDataSetChanged();
    }

    public long getItemId(int position) {
        return itemsFiltrados.get(position).getIntIdPartido();
    }

    public int getItemCount() {
        return itemsFiltrados.size();
    }

    @Override
    public void onBindViewHolder(final PartidosViewHolder eventosViewHolder, int i) {
        final PartidosItem exp = itemsFiltrados.get(i);
        eventosViewHolder.txtNombreEquipo1.setText(exp.getStrNombreEquipo1());
        eventosViewHolder.txtNombreEquipo2.setText(exp.getStrNombreEquipo2());
        eventosViewHolder.txtMarcador.setText(exp.getStrTextoMarcador());
        eventosViewHolder.txtCentral.setText(exp.getStrTextoCentral());
        eventosViewHolder.imgEquipo1.setImageResource(exp.getIntImgEquipo1());
        eventosViewHolder.imgEquipo2.setImageResource(exp.getIntImgEquipo2());
    }

    public PartidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_partidos, viewGroup, false);

        return new PartidosViewHolder(itemView);
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            List<PartidosItem> list = items;
            int count = list.size();
            final ArrayList<PartidosItem> nlist = new ArrayList<PartidosItem>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getStrNombreEquipo1();

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
            itemsFiltrados = (ArrayList<PartidosItem>) results.values;
            notifyDataSetChanged();
        }

    }
}
