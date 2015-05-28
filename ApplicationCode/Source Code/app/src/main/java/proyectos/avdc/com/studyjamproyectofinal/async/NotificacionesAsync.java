package proyectos.avdc.com.studyjamproyectofinal.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.GoalsNotification;
import proyectos.avdc.com.studyjamproyectofinal.config.Constantes;
import proyectos.avdc.com.studyjamproyectofinal.config.ParametrosConfig;
import proyectos.avdc.com.studyjamproyectofinal.utils.JSONParser;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class NotificacionesAsync extends AsyncTask<Integer, Integer, List<GoalsNotification>> {
        static final String LOG = "NotificacionesAsync";
        Context context;
        Receiver receiver;
        JSONParser jParser = new JSONParser();
        SharedPreferences preferences;

        public NotificacionesAsync(Context context, Receiver receiver)
        {
            this.context = context;
            this.receiver = receiver;
        }

        @Override
        protected List<GoalsNotification> doInBackground (Integer...args){

        preferences= PreferenceManager.getDefaultSharedPreferences(context);

        String url = ParametrosConfig.URL_BASE + ParametrosConfig.METODO_NOTIFICACIONES;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(preferences.contains(Constantes.fecha))
        {
            params.add(new BasicNameValuePair("dt", preferences.getString(Constantes.fecha,"")));
        }
        else
        {
            Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String localTime = date.format(currentLocalTime);
            params.add(new BasicNameValuePair("dt", localTime));
        }
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            Log.e("JSON Notificaciones:  ", json.toString());

            Notificacion resultadoJson = new Gson().fromJson(json.toString(), Notificacion.class);
            if (resultadoJson.isSuccess())
            {

                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(Constantes.fecha,resultadoJson.getUpdate_time());
                editor.commit();

                return resultadoJson.getUpdates().getGoals();
            }
        } catch (NullPointerException e) {
            Log.e(LOG, "Error con WS" + e.toString());
        }
        return null;
    }

        @Override
        protected void onPostExecute (List <GoalsNotification> lstGoles)
        {
            super.onPostExecute(lstGoles);
            if (lstGoles != null){
                receiver.onLoad(lstGoles);
            }
        }

        public interface Receiver {
            void onLoad(List<GoalsNotification> lstGoles);
        }

        class Notificacion
        {
            @SerializedName("success")
            public boolean success;

            @SerializedName("update_time")
            public String update_time;

            @SerializedName("updates")
            public Updates updates;

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public Updates getUpdates() {
                return updates;
            }

            public void setUpdates(Updates updates) {
                this.updates = updates;
            }
        }

    class Updates
    {
        @SerializedName("goals")
        List<GoalsNotification> goals;

        public List<GoalsNotification> getGoals() {
            return goals;
        }

        public void setGoals(List<GoalsNotification> goals) {
            this.goals = goals;
        }
    }
}
