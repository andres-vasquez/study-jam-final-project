package proyectos.avdc.com.studyjamproyectofinal.async;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import proyectos.avdc.com.studyjamproyectofinal.POJO.JugadoresItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
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
                Vector<ContentValues> cVVector = new Vector<ContentValues>(resultadoJson.getResult().size());

                for(JugadoresItem item : resultadoJson.getResult()) {

                    ContentValues values = new ContentValues();
                    values.put(GolazoContract.JugadoresEntry.COLUMN_FIRST_NAME, item.getFirstname());
                    values.put(GolazoContract.JugadoresEntry.COLUMN_LAST_NAME, item.getLastname());
                    values.put(GolazoContract.JugadoresEntry.COLUMN_ID_TEAM, args[0]);
                    cVVector.add(values);
                }
                if ( cVVector.size() > 0 )
                {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);

                    context.getContentResolver().delete(GolazoContract.JugadoresEntry.CONTENT_URI,null,null);
                    context.getContentResolver().bulkInsert(GolazoContract.JugadoresEntry.CONTENT_URI, cvArray);
                }
                Log.d(LOG, "Sync Complete. " + cVVector.size() + " Inserted");

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
