package com.exercice.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnRecherche;
    private TextView tvNbresultat;
    private TextView tvTempsexec;
    private ListView lvListeresultat;
    private ArrayAdapter<Integer> primeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        btnRecherche = findViewById(R.id.btnRecherche);
        tvNbresultat = findViewById(R.id.tvNbresultat);
        tvTempsexec = findViewById(R.id.tvTempsexec);
        lvListeresultat = findViewById(R.id.lvListeresultat);

        // Initialise l'adaptateur pour la ListView
        primeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvListeresultat.setAdapter(primeAdapter);

        btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNombre.getText().toString();
                if (!input.isEmpty()) {
                    int nombre = Integer.parseInt(input);
                    new NombresPremierTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, nombre);
                }
            }
        });
    }

    private class NombresPremierTask extends AsyncTask<Integer, Integer, Long> {

        private long debutCalcul;

        @Override
        protected void onPreExecute() {
            // Enregistre le temps de début du calcul
            debutCalcul = System.currentTimeMillis();
            primeAdapter.clear(); // Efface la liste des nombres premiers
        }

        @Override
        protected Long doInBackground(Integer... params) {
            int number = params[0];
            long finCalcul;

            for (int i = 2; i <= number; i++) {
                if (isPrime(i)) {
                    // Publie le nombre premier trouvé pour la mise à jour en temps réel
                    publishProgress(i);
                }
            }

            // Calcul du temps total de calcul
            finCalcul = System.currentTimeMillis();
            return finCalcul - debutCalcul;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Met à jour l'UI avec le nombre en cours de test en temps réel
            int nbTest = values[0];
            tvTempsexec.setText("Nombre en cours de test : " + nbTest);

            // Ajoute le nombre premier trouvé à la ListView en temps réel
            primeAdapter.add(nbTest);
        }

        @Override
        protected void onPostExecute(Long tpsTotal) {
            // Met à jour l'UI avec le temps total de calcul à la fin
            updateTotalTime(tpsTotal);
        }

        private boolean isPrime(int nombre) {
            // Implémente algorithme de vérification de nombre premier
            if (nombre <= 1) {
                return false; // Les nombres <= 1 ne sont pas premiers
            }

            // Vérifie si le nombre est divisible par un autre nombre que 1 et lui-même
            for (int i = 2; i <= Math.sqrt(nombre); i++) {
                if (nombre % i == 0) {
                    return false; // Le nombre est divisible, donc il n'est pas premier
                }
            }

            return true; // Si le nombre n'est divisible par aucun autre nombre, il est premier
        }

        // Méthode à appeler pour mettre à jour l'UI avec le temps total de calcul
        private void updateTotalTime(long tpsTotal) {
            tvNbresultat.setText("Nbre de résultats :\n" + primeAdapter.getCount());
            tvTempsexec.setText("Temps total de calcul : " + tpsTotal + " ms");
        }
    }
}

