package proyectos.avdc.com.studyjamproyectofinal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.PosicionesItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.RankingItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.adapters.PosicionesAdapter;
import proyectos.avdc.com.studyjamproyectofinal.async.RankingAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;

public class Posiciones extends Fragment {

    private List<PosicionesItem> items = new ArrayList<PosicionesItem>();
    private PosicionesAdapter adapter;
    private ListView lstPosiciones;

    public Posiciones() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_posiciones, container, false);
        lstPosiciones = (ListView) vista.findViewById(R.id.lstPosiciones);

        adapter = new PosicionesAdapter(items, getActivity());
        lstPosiciones.setAdapter(adapter);

        RankingAsync rankingAsync = new RankingAsync(getActivity(), new RankingAsync.Receiver() {
            @Override
            public void onLoad(int totalMatches, List<RankingItem> lstRanking) {
                LlenarPosiciones(lstRanking);
            }
        });
        rankingAsync.execute();
        return vista;
    }

    private void LlenarPosiciones(List<RankingItem> lstRanking) {
        int contador = 1;
        for (RankingItem rankingItem : lstRanking) {
            items.add(new PosicionesItem(contador,
                    Funcionesdb.ObtenerEquipoNombre(getActivity(),rankingItem.getName()),
                    Integer.parseInt(rankingItem.getPlayed()),
                    Integer.parseInt(rankingItem.getWon()),
                    Integer.parseInt(rankingItem.getLost()),
                    Integer.parseInt(rankingItem.getDrawn()),
                    Integer.parseInt(rankingItem.getGoals_difference()),
                    Integer.parseInt(rankingItem.getPoints())));
            adapter.notifyDataSetChanged();
        }
    }
}
