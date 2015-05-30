package proyectos.avdc.com.studyjamproyectofinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import proyectos.avdc.com.studyjamproyectofinal.adapters.TabsAdapter;
import proyectos.avdc.com.studyjamproyectofinal.fragments.Equipos;
import proyectos.avdc.com.studyjamproyectofinal.fragments.Home;
import proyectos.avdc.com.studyjamproyectofinal.fragments.Posiciones;
import proyectos.avdc.com.studyjamproyectofinal.notificaciones.Servicio;


public class MainActivity extends ActionBarActivity {

    private static Typeface fontAwesome;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fontAwesome = Typeface.createFromAsset(getAssets(), getString(R.string.font_awesome));

        Intent notificaciones = new Intent();
        notificaciones.setAction(getString(R.string.action_notification));
        sendBroadcast(notificaciones);

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec(getString(R.string.home)).setIndicator(getString(R.string.home)), Home.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec(getString(R.string.posiciones)).setIndicator(getString(R.string.posiciones)), Posiciones.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec(getString(R.string.equipos)).setIndicator(getString(R.string.equipos)), Equipos.class, null);

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            view.setBackgroundResource(R.drawable.tab_indicator);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTypeface(fontAwesome);
            tv.setTextColor(getResources().getColor(R.color.actionbar));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.app_info))
                    .setPositiveButton(getString(R.string.entendido), dialogClickListener);
            builder.show();
        } else if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, Preferencias.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}