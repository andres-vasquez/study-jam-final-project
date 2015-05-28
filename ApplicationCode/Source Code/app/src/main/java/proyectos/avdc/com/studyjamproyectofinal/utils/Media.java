package proyectos.avdc.com.studyjamproyectofinal.utils;

import android.util.Log;

import proyectos.avdc.com.studyjamproyectofinal.R;

/**
 * Created by andresvasquez on 5/3/15.
 */
public class Media {

    public static int ObtenerBanderas(String nombrePNG)
    {
        switch (nombrePNG)
        {
            case "the_strongest.png": return R.drawable.the_strongest;
            case "bolivar.png": return R.drawable.bolivar;
            case "oriente_petrolero.png": return R.drawable.oriente_petrolero;
            case "blooming.png": return R.drawable.blooming;
            case "wilstermann.png": return R.drawable.wilstermann;
            case "petrolero.png": return R.drawable.petrolero;
            case "san_jose.png": return R.drawable.san_jose;
            case "real_potosi.png": return R.drawable.real_potosi;
            case "nacional_potosi.png": return R.drawable.nacional_potosi;
            case "universitario_sfx.png": return R.drawable.universitario;
            case "sport_boys.png": return R.drawable.sport_boys;
            case "universitario_uap.png": return R.drawable.universitario_2;
            default:return android.R.drawable.btn_star_big_off;
        }
    }

    public static int ObtenerBanderasEquipo(String nombreEquipo)
    {
        Log.e("Nombre equipo", nombreEquipo);
        switch (nombreEquipo)
        {
            case "The Strongest": return R.drawable.the_strongest;
            case "Bolivar": return R.drawable.bolivar;
            case "Oriente Petrolero": return R.drawable.oriente_petrolero;
            case "Blooming": return R.drawable.blooming;
            case "Wilstermann": return R.drawable.wilstermann;
            case "Petrolero": return R.drawable.petrolero;
            case "San Jose": return R.drawable.san_jose;
            case "Real Potosi": return R.drawable.real_potosi;
            case "Nacional Potosi": return R.drawable.nacional_potosi;
            case "Universitario SFX": return R.drawable.universitario;
            case "Sport Boys": return R.drawable.sport_boys;
            case "Universitario UAP": return R.drawable.universitario_2;
            default:return android.R.drawable.btn_star_big_off;
        }
    }
}
