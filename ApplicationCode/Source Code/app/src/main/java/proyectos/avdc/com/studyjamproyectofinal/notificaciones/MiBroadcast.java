package proyectos.avdc.com.studyjamproyectofinal.notificaciones;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import proyectos.avdc.com.studyjamproyectofinal.R;

public class MiBroadcast extends BroadcastReceiver
{
    SharedPreferences preferences;
	TimerTask scanTask;
	final Handler handler = new Handler();	
	Timer t = new Timer();

	//Se ejecuta cuando el tel�fono se enciende
	//Agregar final a context
	public void onReceive(final Context context, Intent intent) 
	{
		//Recrea el servicio cada 30 segundos
		scanTask = new TimerTask() 
		{
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						Intent service = new Intent(context, Servicio.class);
						context.startService(service);
					}
				});
			}};

            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            int intervalo=Integer.parseInt(preferences.getString(context.getString(R.string.pref_intervalo_key),context.getString(R.string.pref_intervalo_default)));
			t.schedule(scanTask, 1000,intervalo*1000);
			Intent service = new Intent(context, Servicio.class);
			context.startService(service);
	}

}
