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

import proyectos.avdc.com.studyjamproyectofinal.POJO.MatchItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.RankingItem;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class RankingAsync extends AsyncTask<Integer, Integer, List<RankingItem>> {
        static final String LOG = "RankingAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        int TotalMatches = 0;

        public RankingAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected List<RankingItem> doInBackground (Integer...args){

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_RANKING;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("El resultado en JSON: ", json.toString());

            Resultado resultadoJson = new Gson().fromJson(json.toString(), Resultado.class);
            if (resultadoJson.isSuccess())
            {
                TotalMatches = resultadoJson.getResult().size();
                Vector<ContentValues> cVVector = new Vector<ContentValues>(resultadoJson.getResult().size());

                for(RankingItem item : resultadoJson.getResult()) {

                    ContentValues weatherValues = new ContentValues();
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_NAME, item.getName());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_PLAYED, item.getPlayed());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_WON, item.getWon());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_DRAWN, item.getDrawn());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_LOST, item.getLost());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_GOALS_FOR, item.getGoals_for());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_GOALS_AGAINST, item.getGoals_against());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_GOALS_DIFFERENCE, item.getGoals_difference());
                    weatherValues.put(GolazoContract.PosicionesEntry.COLUMN_POINTS, item.getPoints());
                    cVVector.add(weatherValues);
                }
                if ( cVVector.size() > 0 )
                {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);

                    context.getContentResolver().delete(GolazoContract.PosicionesEntry.CONTENT_URI,null,null);
                    context.getContentResolver().bulkInsert(GolazoContract.PosicionesEntry.CONTENT_URI, cvArray);
                }

                return resultadoJson.getResult();
            }
        } catch (NullPointerException e) {
            Log.e(LOG, "Error con WS" + e.toString());
        }
        return null;
    }

        @Override
        protected void onPostExecute (List < RankingItem > lstRanking)
        {
            super.onPostExecute(lstRanking);
            if (lstRanking != null)
                receiver.onLoad(TotalMatches, lstRanking);
        }

        public interface Receiver {
            void onLoad(int totalMatches, List<RankingItem> lstRanki);
        }

        class Resultado
        {
            @SerializedName("success")
            public boolean success;

            @SerializedName("result")
            public List<RankingItem> result;

            public boolean isSuccess() {
                return success;
            }
            public List<RankingItem> getResult() {
                return result;
            }
        }
}
