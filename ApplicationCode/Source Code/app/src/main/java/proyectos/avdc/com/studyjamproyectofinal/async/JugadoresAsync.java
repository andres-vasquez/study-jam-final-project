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
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class JugadoresAsync extends AsyncTask<Integer, Integer, List<JugadoresItem>> {
        static final String LOG = "JugadoresAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        int TotalMatches = 0;

        public JugadoresAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected List<JugadoresItem> doInBackground (Integer...args){

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_JUDADORES + args[0];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("El resultado en JSON: ", json.toString());

            Resultado resultadoJson = new Gson().fromJson(json.toString(), Resultado.class);
            if (resultadoJson.isSuccess())
            {
                TotalMatches = resultadoJson.getResult().size();
                return resultadoJson.getResult();
            }
        } catch (NullPointerException e) {
            Log.e(LOG, "Error con WS" + e.toString());
        }
        return null;
    }

        @Override
        protected void onPostExecute (List < JugadoresItem > lstJugadores)
        {
            super.onPostExecute(lstJugadores);
            if (lstJugadores != null)
            {
                receiver.onLoad(TotalMatches, lstJugadores);
            }

        }

        public interface Receiver {
            void onLoad(int totalMatches, List<JugadoresItem> lstJugadores);
        }

        class Resultado
        {
            @SerializedName("success")
            public boolean success;

            @SerializedName("result")
            public List<JugadoresItem> result;

            public boolean isSuccess() {
                return success;
            }
            public List<JugadoresItem> getResult() {
                return result;
            }
        }
}
