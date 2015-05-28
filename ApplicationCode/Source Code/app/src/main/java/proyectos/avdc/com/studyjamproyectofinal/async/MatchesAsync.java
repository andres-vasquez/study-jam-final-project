package proyectos.avdc.com.studyjamproyectofinal.async;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Config;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class MatchesAsync extends AsyncTask<Integer, Integer, List<MatchItem>> {
        static final String LOG = "MatchesAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        int TotalMatches = 0;

        public MatchesAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected List<MatchItem> doInBackground (Integer...args){

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_MATCHES;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("El resultado en JSON: ", json.toString());

            Resultado resultadoJson = new Gson().fromJson(json.toString(), Resultado.class);
            if (resultadoJson.isSuccess())
            {
                TotalMatches = resultadoJson.getResult().size();
                Vector<ContentValues> cVVector = new Vector<ContentValues>(resultadoJson.getResult().size());

                for(MatchItem item : resultadoJson.getResult()) {

                    ContentValues weatherValues = new ContentValues();
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_TIME, item.getTime());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_LOCATION, item.getLocation());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_CITY, item.getCity());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_HOME_TEAM, item.getHome_team());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_HOME_BADGE, item.getHome_badge());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_HOME_SCORE, item.getHome_score());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_AWAY_TEAM, item.getAway_team());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_AWAY_BADGE, item.getAway_badge());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_AWAY_SCORE, item.getAway_score());
                    weatherValues.put(GolazoContract.PartidosEntry.COLUMN_MATCH_STATUS, item.getMatch_status());

                    cVVector.add(weatherValues);
                }
                if ( cVVector.size() > 0 )
                {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);

                    context.getContentResolver().delete(GolazoContract.PartidosEntry.CONTENT_URI,null,null);
                    context.getContentResolver().bulkInsert(GolazoContract.PartidosEntry.CONTENT_URI, cvArray);
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
        protected void onPostExecute (List < MatchItem > lstMatches)
        {
            super.onPostExecute(lstMatches);
            if (lstMatches != null)
                receiver.onLoad(TotalMatches, lstMatches);
        }

        public interface Receiver {
            void onLoad(int totalMatches, List<MatchItem> lstMatches);
        }

        class Resultado
        {
            @SerializedName("success")
            public boolean success;

            @SerializedName("result")
            public List<MatchItem> result;

            public boolean isSuccess() {
                return success;
            }
            public List<MatchItem> getResult() {
                return result;
            }
        }
}
