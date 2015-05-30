package proyectos.avdc.com.studyjamproyectofinal.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.JugadoresItem;
import proyectos.avdc.com.studyjamproyectofinal.R;
import proyectos.avdc.com.studyjamproyectofinal.adapters.EquiposAdapter;
import proyectos.avdc.com.studyjamproyectofinal.async.JugadoresAsync;
import proyectos.avdc.com.studyjamproyectofinal.async.JugadoresLocalAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

public class DetalleEquipo extends Fragment {

    private EquiposAdapter adapter;
    final List<EquiposItem> items = new ArrayList<EquiposItem>();
    public RecyclerView recList;

    private ImageView imgEquipo;
    private TextView txtNombreEquipo;
    private TextView txtPj;
    private TextView txtPg;
    private TextView txtPp;
    private TextView txtPe;
    private TextView txtGd;
    private TextView txtPts;

    private LinearLayout llJugadores;
    List<JugadoresItem> lstJugadores;
    private CheckBox chkNotificaciones;
    private ScrollView scroll;

    private SharedPreferences preferences;
    public int idEquipo;

    public DetalleEquipo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
        {
            try {
                String jsonJugadores = savedInstanceState.getString("jugadores");
                Type listType = new TypeToken<ArrayList<JugadoresItem>>() {
                }.getType();
                lstJugadores = new Gson().fromJson(jsonJugadores, listType);
            }catch (Exception e){}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle_equipo, container, false);
        imgEquipo = (ImageView) vista.findViewById(R.id.imgEquipo);
        txtNombreEquipo = (TextView) vista.findViewById(R.id.txtNombreEquipo);

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());

        Bundle bundle = getArguments();
        String jsonEquipo;

        if(bundle!=null)
        if ((jsonEquipo = bundle.getString("objEquipo")).compareTo("") != 0)
        {
            EquiposItem objEquipo = new Gson().fromJson(jsonEquipo, EquiposItem.class);
            imgEquipo.setImageResource(new Media(getActivity()).ObtenerBanderasEquipo(objEquipo.getStrNombreEquipo()));
            txtNombreEquipo.setText(objEquipo.getStrNombreEquipo());
            llJugadores=(LinearLayout)vista.findViewById(R.id.lstJugadores);
            scroll=(ScrollView)vista.findViewById(R.id.scroll);

            chkNotificaciones=(CheckBox)vista.findViewById(R.id.chkNotificaciones);
            ChkNoticicaciones(objEquipo.getIntIdEquipo());
            idEquipo=objEquipo.getIntIdEquipo();

            chkNotificaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("equipo_"+idEquipo,chkNotificaciones.isChecked());
                    editor.commit();
                }
            });

            if(lstJugadores==null)
            {
                //Fill Players from database
                JugadoresLocalAsync jugadoresLocalAsync = new JugadoresLocalAsync(getActivity(), new JugadoresLocalAsync.Receiver() {
                    @Override
                    public void onLoad(int totalMatches, List<JugadoresItem> lstJugadores) {
                        MostrarJugadores(lstJugadores);
                    }
                });
                jugadoresLocalAsync.execute(objEquipo.getIntIdEquipo());

                //Fill Players from database
                JugadoresAsync jugadoresAsync = new JugadoresAsync(getActivity(), new JugadoresAsync.Receiver() {
                    @Override
                    public void onLoad(int totalMatches, List<JugadoresItem> lstJugadores) {
                        MostrarJugadores(lstJugadores);
                    }
                });
                jugadoresAsync.execute(objEquipo.getIntIdEquipo());
            }
            else
            {
                MostrarJugadores(lstJugadores);
            }
        }

        txtPj = (TextView) vista.findViewById(R.id.txtPj);
        txtPg = (TextView) vista.findViewById(R.id.txtPg);
        txtPp = (TextView) vista.findViewById(R.id.txtPp);
        txtPe = (TextView) vista.findViewById(R.id.txtPe);
        txtGd = (TextView) vista.findViewById(R.id.txtGd);
        txtPts = (TextView) vista.findViewById(R.id.txtPts);

        txtPj.setText("1");
        txtPg.setText("1");
        txtPp.setText("0");
        txtPe.setText("0");
        txtGd.setText("+5");
        txtPts.setText("3");

        return vista;
    }

    public void MostrarJugadores(List<JugadoresItem> lstJugadores)
    {
        llJugadores.removeAllViews();
        for(JugadoresItem jugador : lstJugadores)
        {
            try {
                TextView tv = new TextView(getActivity());
                tv.setText(jugador.getFirstname() + " " + jugador.getLastname());
                llJugadores.addView(tv);
            }catch (NullPointerException e)
            {
            }
        }
    }

    private void ChkNoticicaciones(int idEquipo) {
        if(preferences.contains("equipo_"+idEquipo))
        {
            if(preferences.getBoolean("equipo_"+idEquipo,false))
             chkNotificaciones.setChecked(true);
            else
             chkNotificaciones.setChecked(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putString("jugadores", new Gson().toJson(lstJugadores));
    }
}
