package ifpes.gestionaudios;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class ReproductorActivity extends AppCompatActivity {

    private Button volver;

    private ImageButton boton1;
    private ImageButton boton2;
    private ImageButton boton3;

    private TextView text1;
    private TextView text2;
    private BaseDeDatos db;
    private MediaPlayer mp;
    private int longitud = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor);

        volver = findViewById(R.id.botonVolver);
        boton1 = findViewById(R.id.buttonRep);
        boton2 = findViewById(R.id.buttonPausa);
        boton3 = findViewById(R.id.buttonDet);
        text1 = findViewById(R.id.text_titulo);
        text2 = findViewById(R.id.text_url);
        db = new BaseDeDatos(this);
        ArrayList<String> audios = db.getAudios();
        Intent intent = getIntent();

        String contenido = intent.getStringExtra("contenido");

        String[] partes = contenido.split(" - "); // dividir el contenido en título y URL
        if (partes.length >= 2) {
            String titulo = partes[0];
            String url = partes[1];
            text1.setText(getString(R.string.Toast3) + titulo); //valores de los textView
            text2.setText("URL: " + url);
        }


        boton1.setEnabled(true); //empieza con a reproducir, y los demas estan deshabilitados.
        boton2.setEnabled(false);
        boton3.setEnabled(false);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reproducir
                if (longitud > 0) {
                    // ve al punto pausado.
                    mp.seekTo(longitud);
                    mp.start();
                } else {
                    try {
                        mp = new MediaPlayer();
                        Uri url = Uri.parse(partes[1]); //coge la url del contenido
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.setDataSource(ReproductorActivity.this, url);
                        mp.prepare();
                        mp.start();
                        boton1.setEnabled(false);
                        boton2.setEnabled(true);
                        boton3.setEnabled(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ReproductorActivity.this, getString(R.string.Toast4), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pausar
                if (mp != null && mp.isPlaying()) {
                    mp.pause();
                    longitud = mp.getCurrentPosition();
                    boton1.setEnabled(true);
                    boton2.setEnabled(false);
                    boton3.setEnabled(true);
                }
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Detener
                mp.stop();
                longitud = 0;
                mp.prepareAsync();
                boton1.setEnabled(true);
                boton2.setEnabled(false);
                boton3.setEnabled(false);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReproductorActivity.this, MainActivity.class);
                startActivity(intent);
                mp.stop();
            }
        });
    }
}
