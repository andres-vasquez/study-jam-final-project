package proyectos.avdc.com.studyjamproyectofinal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.DetallePartido;
import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.PartidosItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.adapters.PartidosAdapter;
import proyectos.avdc.com.studyjamproyectofinal.async.MatchesAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

public class Home extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private PartidosAdapter adapter;
    final List<PartidosItem> items = new ArrayList<PartidosItem>();
    public RecyclerView recList;

    private static final int LOADER = 0;

    private static final String[] PARTIDOS_COLUMNS = {
            GolazoContract.PartidosEntry.TABLE_NAME + "." + GolazoContract.PartidosEntry._ID,
            GolazoContract.PartidosEntry.COLUMN_TIME,
            GolazoContract.PartidosEntry.COLUMN_CITY,
            GolazoContract.PartidosEntry.COLUMN_LOCATION,
            GolazoContract.PartidosEntry.COLUMN_HOME_TEAM,
            GolazoContract.PartidosEntry.COLUMN_HOME_BADGE,
            GolazoContract.PartidosEntry.COLUMN_HOME_SCORE,
            GolazoContract.PartidosEntry.COLUMN_AWAY_TEAM,
            GolazoContract.PartidosEntry.COLUMN_AWAY_BADGE,
            GolazoContract.PartidosEntry.COLUMN_AWAY_SCORE,
            GolazoContract.PartidosEntry.COLUMN_MATCH_STATUS,
    };

    static final int COL_PARTIDO_ID = 0;
    static final int COL_TIME = 1;
    static final int COL_CITY = 2;
    static final int COL_LOCATION = 3;
    static final int COL_HOME_TEAM = 4;
    static final int COL_HOME_BADGE = 5;
    static final int COL_HOME_SCORE = 6;
    static final int COL_AWAY_TEAM = 7;
    static final int COL_AWAY_BADGE = 8;
    static final int COL_AWAY_SCORE = 9;
    static final int COL_MATCH_STATUS = 10;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_home, container, false);

        recList = (RecyclerView) vista.findViewById(R.id.lstPartidos);
        recList.setHasFixedSize(true);

        GridLayoutManager llm;

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                llm = new GridLayoutManager(getActivity(),2);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                llm = getCountByOrientation();
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                llm = getCountByOrientation();
                break;
            default:
                llm = getCountByOrientation();
        }



        recList.setLayoutManager(llm);

        adapter = new PartidosAdapter(items, getActivity());
        recList.setAdapter(adapter);

        recList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), DetallePartido.class);
                intent.putExtra("objPartido",new Gson().toJson(adapter.itemsFiltrados.get(position)));
                getActivity().startActivity(intent);
            }
        }));

        MatchesAsync matchesAsync = new MatchesAsync(getActivity(), new MatchesAsync.Receiver() {
            @Override
            public void onLoad(int totalMatches, List<MatchItem> lstMatches) {
                adapter.clearList();
                LlenarPartidos(lstMatches);
            }
        });
        matchesAsync.execute();

        return vista;
    }


    private GridLayoutManager getCountByOrientation()
    {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            return new GridLayoutManager(getActivity(),2);
        else
            return new GridLayoutManager(getActivity(),1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    private void LlenarPartidos(List<MatchItem> lstMatches)
    {
        int contador=1;
        for(MatchItem match : lstMatches)
        {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(match.getTime());
                format = new SimpleDateFormat("dd-MM-yyyy");
                String soloFecha=format.format(newDate);
                format = new SimpleDateFormat("hh:mm");
                String soloHora=format.format(newDate);

                PartidosItem partido = new PartidosItem(contador, match.getHome_team(), match.getAway_team(), match.getHome_score() + " - " + match.getAway_score(),soloFecha+"\n"+soloHora , Media.ObtenerBanderas(match.getHome_badge()), Media.ObtenerBanderas(match.getAway_badge()),match);
                adapter.updateList(partido);
                contador++;
            }
            catch (Exception e)
            {
                Log.e("Tiempo", "" + e.getMessage());
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri golazoUri = GolazoContract.PartidosEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                golazoUri,
                PARTIDOS_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if(!data.isClosed()) {
            List<MatchItem> lstMatches = new ArrayList<MatchItem>();
            if (data.moveToFirst()) {
                do {
                    MatchItem matchItem = new MatchItem();
                    matchItem.setCity(data.getString(COL_CITY));
                    matchItem.setLocation(data.getString(COL_LOCATION));
                    matchItem.setTime(data.getString(COL_TIME));
                    matchItem.setHome_team(data.getString(COL_HOME_TEAM));
                    matchItem.setHome_badge(data.getString(COL_HOME_BADGE));
                    matchItem.setHome_score(data.getString(COL_HOME_SCORE));
                    matchItem.setAway_team(data.getString(COL_AWAY_TEAM));
                    matchItem.setAway_badge(data.getString(COL_AWAY_BADGE));
                    matchItem.setAway_score(data.getString(COL_AWAY_SCORE));
                    matchItem.setMatch_status(data.getString(COL_MATCH_STATUS));

                    lstMatches.add(matchItem);
                } while (data.moveToNext());
            }
            data.close();
            LlenarPartidos(lstMatches);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.updateList(null);
    }

    static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
                return true;
            }
            return false;
        }

        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }
    }
}
