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

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.RankingItem;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;
import proyectos.avdc.com.studyjamproyectofinal.data.GolazoContract;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class TeamsAsync extends AsyncTask<Integer, Integer, List<EquiposItem>> {
        static final String LOG = "TeamsAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        int TotalMatches = 0;

        public TeamsAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected List<EquiposItem> doInBackground (Integer...args){

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_EQUIPOS;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("El resultado en JSON: ", json.toString());

            Resultado resultadoJson = new Gson().fromJson(json.toString(), Resultado.class);
            if (resultadoJson.isSuccess())
            {
                TotalMatches = resultadoJson.getResult().size();
                Vector<ContentValues> cVVector = new Vector<ContentValues>(resultadoJson.getResult().size());

                for(EquiposItem item : resultadoJson.getResult()) {

                    ContentValues weatherValues = new ContentValues();
                    weatherValues.put(GolazoContract.EquiposEntry.COLUMN_ID_EQUIPO, item.getIntIdEquipo());
                    weatherValues.put(GolazoContract.EquiposEntry.COLUMN_NAME, item.getStrNombreEquipo());
                    weatherValues.put(GolazoContract.EquiposEntry.COLUMN_BADGE, item.getStrBadge());
                    weatherValues.put(GolazoContract.EquiposEntry.COLUMN_IMG, item.getIntImgEquipo());
                    cVVector.add(weatherValues);
                }
                if ( cVVector.size() > 0 )
                {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);

                    context.getContentResolver().delete(GolazoContract.EquiposEntry.CONTENT_URI,null,null);
                    context.getContentResolver().bulkInsert(GolazoContract.EquiposEntry.CONTENT_URI, cvArray);
                }
                return resultadoJson.getResult();
            }
        } catch (NullPointerException e) {
            Log.e(LOG, "Error con WS" + e.toString());
        }
        return null;
    }

        @Override
        protected void onPostExecute (List < EquiposItem > lstEquipos)
        {
            super.onPostExecute(lstEquipos);
            if (lstEquipos != null)
            {
                List<EquiposItem> resultado=new ArrayList<EquiposItem>();
                for(EquiposItem item : lstEquipos)
                {
                    item.setIntImgEquipo(Media.ObtenerBanderasEquipo(item.getStrNombreEquipo()));
                    resultado.add(item);
                }
                receiver.onLoad(TotalMatches, resultado);
            }

        }

        public interface Receiver {
            void onLoad(int totalMatches, List<EquiposItem> lstRanki);
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
