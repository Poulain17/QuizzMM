package fr.mmg.mmquizz.IHM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import fr.mmg.mmquizz.Metier.Reponse;
import fr.mmg.mmquizz.R;

/**
 * Created by marie on 09/02/2018.
 */

public class ReponsesAdapter extends ArrayAdapter<Reponse>
{
    private ArrayList<Reponse> reponses;
    private Context context;
    private int reponseAdapterID;


    public ReponsesAdapter(Context context, int reponseAdapterID, ArrayList<Reponse> reponses)
    {
        super(context, reponseAdapterID, reponses);
        this.reponses = reponses;
        this.context = context;
        this.reponseAdapterID = reponseAdapterID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(reponseAdapterID, parent, false);
        }

        final Reponse reponse = reponses.get(position);

        if (reponse != null)
        {
            TextView textReponses = (TextView) view.findViewById(R.id.reponse);
            textReponses.setText(reponse.getLibelle());
        }
        return view;
    }
}

