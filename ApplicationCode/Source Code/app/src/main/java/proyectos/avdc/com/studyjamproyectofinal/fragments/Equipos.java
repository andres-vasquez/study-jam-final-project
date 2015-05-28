package proyectos.avdc.com.studyjamproyectofinal.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.adapters.EquiposAdapter;
import proyectos.avdc.com.studyjamproyectofinal.async.TeamsAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;

public class Equipos extends Fragment {

    private EquiposAdapter adapter;
    final List<EquiposItem> items = new ArrayList<EquiposItem>();
    public RecyclerView recList;

    public Equipos() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_equipos, container, false);

        recList = (RecyclerView) vista.findViewById(R.id.lstEquipos);
        recList.setHasFixedSize(true);
        GridLayoutManager llm;

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                llm = new GridLayoutManager(getActivity(),5);
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

        adapter = new EquiposAdapter(items, getActivity());
        recList.setAdapter(adapter);

        if(adapter.getItemCount()==0) {
            List<EquiposItem> lstEquiposLocal = Funcionesdb.LlenarEquipos(getActivity());
            if (lstEquiposLocal.size() == 0)
                LlenarEquipos();
            else {
                for (EquiposItem item : lstEquiposLocal)
                    adapter.updateList(item);
            }
        }
        return vista;
    }

    private GridLayoutManager getCountByOrientation()
    {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            return new GridLayoutManager(getActivity(),4);
        else
            return new GridLayoutManager(getActivity(),3);
    }

    private void LlenarEquipos()
    {
        TeamsAsync teamsAsync=new TeamsAsync(getActivity(), new TeamsAsync.Receiver() {
            @Override
            public void onLoad(int totalMatches, List<EquiposItem> lstEquipos) {
                for(EquiposItem item : lstEquipos)
                    adapter.updateList(item);
            }
        });
        teamsAsync.execute();
    }
}
