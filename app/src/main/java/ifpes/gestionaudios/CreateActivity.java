package ifpes.gestionaudios;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextUrl;
    private Button botonGuardar;
    private BaseDeDatos bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);

        editTextTitulo = findViewById(R.id.intrTitulo);
        editTextUrl = findViewById(R.id.intrUrl);
        botonGuardar = findViewById(R.id.botonCrear);

        bd = new BaseDeDatos(this);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAudio();
            }

        });
    }
    private void guardarAudio() {

        String titulo = editTextTitulo.getText().toString().trim();
        String url = editTextUrl.getText().toString().trim();


       if (esURLValidaConMP3(url)){
            if (titulo.isEmpty() || url.isEmpty()) {
                // Mostrar Toast indicando que todos los campos son obligatorios
                Toast.makeText(this, getString(R.string.Toast1), Toast.LENGTH_SHORT).show();
            } else {
                // Insertar los datos en la base de datos
                bd.insertarAudio(titulo, url);
                // Mostrar Toast indicando que el audio ha sido guardado correctamente
                Toast.makeText(this, getString(R.string.Toast2), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
             Toast.makeText(CreateActivity.this, "URL no valido", Toast.LENGTH_SHORT).show();
    }

    }
    public static boolean esURLValidaConMP3(String url) {
        // Improved regular expression for URL ending in .mp3
        String regex = "^(http|https)://([\\w-]+(?:(?:\\.[\\w-]+)+))([\\w.,@?^=%&:/~\\+#-]*[\\w@?^=%&/~\\+#-])?\\.mp3$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}