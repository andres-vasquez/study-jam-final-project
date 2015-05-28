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

import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItemDetalle;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class DetalleMatchesAsync extends AsyncTask<Integer, Integer, MatchItemDetalle> {
        static final String LOG = "MatchesAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        int TotalMatches = 0;

        public DetalleMatchesAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected MatchItemDetalle doInBackground (Integer...args){

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_MATCHES+"/"+ args[0];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("El resultado en JSON: ", json.toString());

            Resultado resultadoJson = new Gson().fromJson(json.toString(), Resultado.class);
            if (resultadoJson.isSuccess())
            {
                TotalMatches = 1;
                return resultadoJson.getResult();
            }
        } catch (NullPointerException e) {
            Log.e(LOG, "Error con WS" + e.toString());
        }
        return null;
    }

        @Override
        protected void onPostExecute (MatchItemDetalle lstMatches)
        {
            super.onPostExecute(lstMatches);
            if (lstMatches != null)
                receiver.onLoad(TotalMatches, lstMatches);
        }

        public interface Receiver {
            void onLoad(int totalMatches, MatchItemDetalle lstMatches);
        }

        class Resultado
        {
            @SerializedName("success")
            public boolean success;

            @SerializedName("result")
            public MatchItemDetalle result;

            public boolean isSuccess() {
                return success;
            }
            public MatchItemDetalle getResult() {
                return result;
            }
        }
}
