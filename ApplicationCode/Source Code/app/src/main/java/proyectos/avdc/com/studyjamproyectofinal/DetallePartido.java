package proyectos.avdc.com.studyjamproyectofinal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.Goals;
import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItemDetalle;
import proyectos.avdc.com.studyjamproyectofinal.POJO.PartidosItem;
import proyectos.avdc.com.studyjamproyectofinal.async.DetalleMatchesAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;


public class DetallePartido extends ActionBarActivity {

    private static final String SHARE_HASHTAG = " #Golazo";
    private String mensaje="";
    private ShareActionProvider mShareActionProvider;

    private LinearLayout lyDerecha;
    private LinearLayout lyIzquierda;

    private TextView txtNombreEquipo1;
    private TextView txtNombreEquipo2;
    private TextView txtMarcador;
    private TextView txtCentral;
    private ImageView imgEquipo1;
    private ImageView imgEquipo2;

    private TextView txtLugar;
    private TextView txtHora;
    private ImageView imgLugar;
    private ImageView imgHora;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_partido);

        context=this;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lyDerecha=(LinearLayout)findViewById(R.id.lyDerecha);
        lyIzquierda=(LinearLayout)findViewById(R.id.lyIzquierda);

        txtNombreEquipo1=(TextView)findViewById(R.id.txtEquipo1);
        txtNombreEquipo2=(TextView)findViewById(R.id.txtEquipo2);
        txtMarcador=(TextView)findViewById(R.id.txtMarcador);
        txtCentral=(TextView)findViewById(R.id.txtCentral);
        imgEquipo1=(ImageView)findViewById(R.id.imgEquipo1);
        imgEquipo2=(ImageView)findViewById(R.id.imgEquipo2);

        txtLugar=(TextView)findViewById(R.id.txtLugar);
        txtHora=(TextView)findViewById(R.id.txtHora);
        imgLugar=(ImageView)findViewById(R.id.imgLugar);
        imgHora=(ImageView)findViewById(R.id.imgHora);

        Intent a=getIntent();
        String jsonPartido=a.getStringExtra("objPartido");
        if(jsonPartido.compareTo("")!=0)
        {
            PartidosItem exp=new Gson().fromJson(jsonPartido,PartidosItem.class);
            txtNombreEquipo1.setText(exp.getStrNombreEquipo1());
            txtNombreEquipo2.setText(exp.getStrNombreEquipo2());
            txtMarcador.setText(exp.getStrTextoMarcador());
            txtCentral.setText(exp.getStrTextoCentral());
            imgEquipo1.setImageResource(exp.getIntImgEquipo1());
            imgEquipo2.setImageResource(exp.getIntImgEquipo2());

            mensaje="Siguiendo el partido: "+exp.getStrNombreEquipo1()+ " "+exp.getStrTextoMarcador()+" "+exp.getStrNombreEquipo2();


            MatchItem match=exp.getMatch();
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(match.getTime());
                format = new SimpleDateFormat("hh:mm");
                String soloHora = format.format(newDate);
                txtHora.setText(soloHora);
            }
            catch (Exception e)
            {
                Log.e("Error", "" + e.getMessage());
            }
            txtLugar.setText(match.getCity()+", "+match.getLocation());

            DetalleMatchesAsync detalleMatchesAsync=new DetalleMatchesAsync(context, new DetalleMatchesAsync.Receiver() {
                @Override
                public void onLoad(int totalMatches, MatchItemDetalle partido) {
                    List<Goals> goles= partido.getGoals();
                    if(goles!=null)
                    for(Goals gol : goles)
                    {
                        EquiposItem equipo = Funcionesdb.ObtenerEquipoNombre(context,partido.getHome_team());
                        if(gol.getId_team()==equipo.getIntIdEquipo())
                            AgregarMensaje(lyIzquierda, gol.getTime()+"' "+gol.getPlayer_shortname());
                        else
                            AgregarMensaje(lyDerecha, gol.getTime()+"' "+gol.getPlayer_shortname());
                    }
                }
            });
            detalleMatchesAsync.execute(exp.getIntIdPartido());
        }
    }

    private void AgregarMensaje(LinearLayout ly, String mensaje)
    {
        TextView tv=new TextView(context);
        tv.setTextSize(12);
        tv.setText(mensaje);
        ly.addView(tv);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_partido,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareForecastIntent());
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mensaje + SHARE_HASHTAG);
        return shareIntent;
    }
}
