package ifpes.gestionaudios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ifpes.gestionaudios.R;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private ArrayList<String> listadoAudio = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Intent pasarPantalla;
    private BaseDeDatos db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listMain);
        db = new BaseDeDatos(this);
        listadoAudio = db.getAudios();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadoAudio);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                Intent pass = new Intent(MainActivity.this, ReproductorActivity.class);
                pass.putExtra("contenido", item);
                startActivity(pass);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int itemId = item.getItemId();
         if (itemId == R.id.mainCrear) {
             pasarPantalla = new Intent(MainActivity.this, CreateActivity.class);
             startActivity(pasarPantalla);
             return true;
         } else if (itemId == R.id.mainSalir) {
             finish(); // finalizar la actividad actual
             return true;

         } else {
             return super.onOptionsItemSelected(item);
         }
     }
}