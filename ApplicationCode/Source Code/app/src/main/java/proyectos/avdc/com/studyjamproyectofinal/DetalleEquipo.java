package proyectos.avdc.com.studyjamproyectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.NavegacionItem;
import proyectos.avdc.com.studyjamproyectofinal.adapters.NavegacionAdapter;
import proyectos.avdc.com.studyjamproyectofinal.data.Funcionesdb;
import proyectos.avdc.com.studyjamproyectofinal.utils.Media;


public class DetalleEquipo extends ActionBarActivity {

    Context context;
    private NavegacionAdapter adpater;
    private ListView drawer_list;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawer_toggle;
    private int seleccionado = 99;
    private List<EquiposItem> equipos = new ArrayList<EquiposItem>();
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_equipo);

        if(savedInstanceState!=null)
            seleccionado=savedInstanceState.getInt("posicion");

        context = this;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        } catch (Exception e) {
        }

        fragments = llenarFragments(equipos = Funcionesdb.LlenarEquipos(context));
        drawer_list = (ListView) findViewById(R.id.left_drawer);


        List<NavegacionItem> itemsMenu = llenarItemsMenu(equipos);
        adpater = new NavegacionAdapter(itemsMenu, DetalleEquipo.this);
        drawer_list.setAdapter(adpater);

        drawer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (drawer_layout != null)
                    if (drawer_layout.isDrawerOpen(drawer_list))
                        drawer_layout.closeDrawer(drawer_list);
                selectItem(position);
            }
        });

        String jsonEquipo="";
        if(seleccionado==99)//Primera vez
        {
            Intent a = getIntent();
            jsonEquipo = a.getStringExtra("objEquipo");
        }

        if (jsonEquipo.compareTo("") != 0) {
            int index = 0;
            for (EquiposItem equiposItem : equipos) {
                if (jsonEquipo.compareTo(new Gson().toJson(equiposItem)) == 0)
                    selectItem(index);
                index++;
            }
        } else {
            int index = seleccionado;
            selectItem(index);
        }

        if (drawer_layout != null) {
            drawer_toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View drawerView) {
                    supportInvalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                }
            };
            drawer_layout.setDrawerListener(drawer_toggle);
        }
    }


    private List<NavegacionItem> llenarItemsMenu(List<EquiposItem> lstEquiposItems) {
        List<NavegacionItem> itemsMenu = new ArrayList<NavegacionItem>();
        for (EquiposItem equiposItem : lstEquiposItems)
            itemsMenu.add(new NavegacionItem(equiposItem.getIntIdEquipo(),
                    Media.ObtenerBanderasEquipo(equiposItem.getStrNombreEquipo()),
                    equiposItem.getStrNombreEquipo()));
        return itemsMenu;
    }

    public void selectItem(int position)
    {
        String titulo = "";
        Fragment f = fragments.get(position);
        if (f.getArguments() == null) {
            Bundle bundle = new Bundle();
            bundle.putString("objEquipo", new Gson().toJson(equipos.get(position)));
            f.setArguments(bundle);
        }

        titulo = equipos.get(position).getStrNombreEquipo();
        if (seleccionado != position)
        {
            FragmentManager fm = getSupportFragmentManager();
            Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
            if (oldFragment != null)
                fm.beginTransaction()
                        .remove(oldFragment)
                        .addToBackStack("tag")
                        .replace(R.id.main_content, f)
                        .commit();
            else
                fm.beginTransaction()
                        .addToBackStack("tag")
                        .replace(R.id.main_content, f)
                        .commit();
        }
        else {
            Log.e("Diferentes", "Nop");
        }

        seleccionado = position;
        drawer_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        drawer_list.setItemChecked(position, true);
        setTitle(titulo);

        if (drawer_layout != null)
            drawer_layout.closeDrawer(drawer_list);
    }

    private List<Fragment> llenarFragments(List<EquiposItem> lstEquipos) {
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (EquiposItem equiposItem : lstEquipos)
            fragments.add(new proyectos.avdc.com.studyjamproyectofinal.fragments.DetalleEquipo());
        return fragments;
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawer_layout != null)
            drawer_toggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //drawer_toggle.onConfigurationChanged(newConfig);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawer_layout != null) {
                if (drawer_layout.isDrawerOpen(drawer_list)) {
                    drawer_layout.closeDrawer(drawer_list);
                } else {
                    drawer_layout.openDrawer(drawer_list);
                }
            } else
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt("posicion",seleccionado);
    }
}
