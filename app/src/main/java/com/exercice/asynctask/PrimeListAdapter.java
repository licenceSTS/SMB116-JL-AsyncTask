package com.exercice.asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class PrimeListAdapter extends ArrayAdapter<Integer> {

    private final Context context;
    private final ArrayList<Integer> nombresPremier;

    public PrimeListAdapter(Context context, ArrayList<Integer> nombresPremier) {
        super(context, R.layout.list_item_premier, nombresPremier);
        this.context = context;
        this.nombresPremier = nombresPremier;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Nouvelle vue à inflater
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_premier, parent, false);

            // Initialisation du ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvNombresPremier = convertView.findViewById(R.id.tvNombresPremier);

            // Stockage du ViewHolder avec la vue
            convertView.setTag(viewHolder);
        } else {
            // Réutilisation du ViewHolder existant
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Mise à jour de la vue avec les données
        int primeNumber = nombresPremier.get(position);
        viewHolder.tvNombresPremier.setText(String.valueOf(primeNumber));

        return convertView;
    }

    // Classe ViewHolder pour optimiser les performances de la ListView
    private static class ViewHolder {
        TextView tvNombresPremier;
    }
}

