package com.exercice.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnRecherche;
    private TextView tvNbresultat;
    private ListView lvListeresultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        btnRecherche = findViewById(R.id.btnRecherche);
        tvNbresultat = findViewById(R.id.tvNbresultat);
        lvListeresultat = findViewById(R.id.lvListeresultat);

        btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNombre.getText().toString();
                if (!input.isEmpty()) {
                    int number = Integer.parseInt(input);
                    new NombresPremierTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, number);
                }
            }
        });
    }

    private class NombresPremierTask extends AsyncTask<Integer, Void, ArrayList<Integer>> {
        @Override
        protected ArrayList<Integer> doInBackground(Integer... params) {
            int number = params[0];
            return calculatePremiers(number);
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> primes) {
            // Mise à jour de la ListView avec les nombres premiers
            updateListView(primes);

            // Mise à jour de la TextView avec le nombre de résultats
            updateResultCount(primes.size());
        }
    }

    private ArrayList<Integer> calculatePremiers(int maxNumber) {
        ArrayList<Integer> primes = new ArrayList<>();
        // Implémentation de l'algorithme de génération de nombres premiers
        // (à adapter selon les besoins)
        for (int i = 2; i <= maxNumber; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int number) {
        // Implémentation de la vérification de nombre premier
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private void updateListView(ArrayList<Integer> primes) {
        // Adapter à utiliser pour la ListView (à adapter selon les besoins)
        PrimeListAdapter adapter = new PrimeListAdapter(MainActivity.this, primes);
        lvListeresultat.setAdapter(adapter);
    }

    private void updateResultCount(int count) {
        tvNbresultat.setText("Nb de résultats :\n" + count);
    }
}
