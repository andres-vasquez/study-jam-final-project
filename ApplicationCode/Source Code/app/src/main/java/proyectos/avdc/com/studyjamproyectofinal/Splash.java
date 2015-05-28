package proyectos.avdc.com.studyjamproyectofinal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.async.TeamsAsync;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;


public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar barra = getSupportActionBar();
        barra.hide();

        if(Funcionesdb.LlenarEquipos(this).size()==0)
        {
            TeamsAsync llenarEquipo=new TeamsAsync(this, new TeamsAsync.Receiver() {
                @Override
                public void onLoad(int totalMatches, List<EquiposItem> lstRanki) {
                    TimeOut();
                }
            });
            llenarEquipo.execute();
        }
        else
        {
            Log.e("Equipos", "Ya hay en db˚");
            TimeOut();
        }


    }

    private void TimeOut() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, ParametrosConfig.TIEMPO_ESPERA);
    }

    class Resultado
    {
        @SerializedName("success")
        public boolean success;

        @SerializedName("result")
        public List<EquiposItem> result;

        public boolean isSuccess() {
            return success;
        }
        public List<EquiposItem> getResult() {
            return result;
        }
    }
}
