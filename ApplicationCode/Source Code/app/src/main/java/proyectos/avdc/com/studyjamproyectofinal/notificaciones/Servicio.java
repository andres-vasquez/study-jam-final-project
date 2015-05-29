package proyectos.avdc.com.studyjamproyectofinal.notificaciones;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import proyectos.avdc.com.studyjamproyectofinal.MainActivity;
import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.GoalsNotification;
import proyectos.avdc.com.studyjamproyectofinal.async.NotificacionesAsync;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;

public class Servicio extends Service {
	private final IBinder mBinder = new MyBinder();
	private ArrayList<String> lista = new ArrayList<String>();
	private String link_recibir = "";
    private SharedPreferences preferences;
    private Context context;

	public int onStartCommand(Intent intent, int flags, int startId) 
	{
        context=this;
        NotificacionesAsync notificacionesAsync=new NotificacionesAsync(this, new NotificacionesAsync.Receiver() {
            @Override
            public void onLoad(List<GoalsNotification> lstGoles) {
                preferences= PreferenceManager.getDefaultSharedPreferences(context);

                Log.e("Estamos aca","Si");
                for(GoalsNotification goles : lstGoles)
                {
                    String equipo1="equipo_"+goles.getId_away();
                    String equipo2="equipo_"+goles.getId_home();

                    if(preferences.getBoolean(equipo1,false) || preferences.getBoolean(equipo2,false))
                    {
                        EquiposItem equiposItem=Funcionesdb.ObtenerEquipoId(context, "" + goles.getId_team());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                        Notification notificacion = new NotificationCompat.Builder(Servicio.this)
                                .setContentTitle("Goooooooooooool!!!")
                                .setContentText(equiposItem.getStrNombreEquipo() + ": " + goles.getPlayer_shortname() + " (" + goles.getTime() + "')")
                                .setSmallIcon(new Media(context).ObtenerBanderasEquipo(equiposItem.getStrNombreEquipo()))
                                .setContentIntent(pIntent)
                                .setDefaults(Notification.DEFAULT_SOUND)
                                .setVibrate(new long[]{1000, 1000})
                                .setAutoCancel(true)
                                .build();

                        notificacion.flags |= Notification.FLAG_AUTO_CANCEL;
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(0, notificacion);
                        break;
                    }
                }
            }
        });
        notificacionesAsync.execute();
		return Service.START_NOT_STICKY;
	}

	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		Servicio getService() {
			return Servicio.this;
		}
	}

	public List<String> getLista() {
		return lista;
	}


}
