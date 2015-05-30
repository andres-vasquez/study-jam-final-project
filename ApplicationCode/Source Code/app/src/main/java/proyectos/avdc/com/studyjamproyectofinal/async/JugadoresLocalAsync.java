package proyectos.avdc.com.studyjamproyectofinal.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.JugadoresItem;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class JugadoresLocalAsync extends AsyncTask<Integer, Integer, List<JugadoresItem>> {
    static final String LOG = "JugadoresLocalAsync";
    Context context;
    Receiver receiver;
    JSONParser jParser = new JSONParser();
    int TotalMatches = 0;

    public JugadoresLocalAsync(Context context, Receiver receiver) {
        this.context = context;
        this.receiver = receiver;
    }

    @Override
    protected List<JugadoresItem> doInBackground(Integer... params) {

        List<JugadoresItem> lstJugadores = Funcionesdb.LlenarJugadores(context, params[0]);
        if (lstJugadores.size() > 0) {
            TotalMatches = lstJugadores.size();
            return lstJugadores;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<JugadoresItem> lstJugadores) {
        super.onPostExecute(lstJugadores);
        if (lstJugadores != null) {
            receiver.onLoad(TotalMatches, lstJugadores);
        }
    }

    public interface Receiver {
        void onLoad(int totalMatches, List<JugadoresItem> lstJugadores);
    }
}
