package proyectos.avdc.com.studyjamproyectofinal.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.PosicionesItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.RankingItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.adapters.PosicionesAdapter;
import proyectos.avdc.com.studyjamproyectofinal.async.RankingAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;

public class Posiciones extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private List<PosicionesItem> items = new ArrayList<PosicionesItem>();
    private PosicionesAdapter adapter;
    private ListView lstPosiciones;

    private static final int LOADER = 1;

    private static final String[] POSICIONES_COLUMNS = {
            GolazoContract.PosicionesEntry.TABLE_NAME + "." + GolazoContract.PosicionesEntry._ID,
            GolazoContract.PosicionesEntry.COLUMN_NAME,
            GolazoContract.PosicionesEntry.COLUMN_PLAYED,
            GolazoContract.PosicionesEntry.COLUMN_WON,
            GolazoContract.PosicionesEntry.COLUMN_POINTS,
            GolazoContract.PosicionesEntry.COLUMN_LOST,
            GolazoContract.PosicionesEntry.COLUMN_DRAWN,
            GolazoContract.PosicionesEntry.COLUMN_GOALS_AGAINST,
            GolazoContract.PosicionesEntry.COLUMN_GOALS_FOR,
            GolazoContract.PosicionesEntry.COLUMN_GOALS_DIFFERENCE,
    };

    static final int COL_POSICION_ID = 0;
    static final int COL_NAME = 1;
    static final int COL_PLAYED = 2;
    static final int COL_WON = 3;
    static final int COL_POINTS = 4;
    static final int COL_LOST = 5;
    static final int COL_DRAWN = 6;
    static final int COL_GOALS_AGAINST = 7;
    static final int COL_GOALS_FOR = 8;
    static final int COL_GOALS_DIFFERENCE = 9;

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
                items.clear();
                adapter.notifyDataSetChanged();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri golazoUri = GolazoContract.PosicionesEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                golazoUri,
                POSICIONES_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(!data.isClosed()) {
            List<RankingItem> lstRanking = new ArrayList<RankingItem>();
            if (data.moveToFirst()) {
                do {
                    RankingItem rankingItem = new RankingItem();
                    rankingItem.setName(data.getString(COL_NAME));
                    rankingItem.setPoints(data.getString(COL_POINTS));
                    rankingItem.setDrawn(data.getString(COL_DRAWN));
                    rankingItem.setGoals_against(data.getString(COL_GOALS_AGAINST));
                    rankingItem.setGoals_difference(data.getString(COL_GOALS_DIFFERENCE));
                    rankingItem.setGoals_for(data.getString(COL_GOALS_FOR));
                    rankingItem.setLost(data.getString(COL_LOST));
                    rankingItem.setPlayed(data.getString(COL_PLAYED));
                    rankingItem.setWon(data.getString(COL_WON));
                    lstRanking.add(rankingItem);
                } while (data.moveToNext());
            }
            data.close();
            LlenarPosiciones(lstRanking);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        items.clear();
        adapter.notifyDataSetChanged();
    }
}
