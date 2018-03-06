package fr.mmg.mmquizz.IHM;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.mmg.mmquizz.Metier.Theme;
import fr.mmg.mmquizz.R;

/**
 * Created by marie on 07/02/2018.
 */

public class ThemesAdapter extends ArrayAdapter<Theme>
{
    private ArrayList<Theme> themes;
    private Context context;
    private int themeAdapterID;
    private Resources res;

    public ThemesAdapter(Context context, int themeAdapterID, ArrayList<Theme> themes)
    {
        super(context, themeAdapterID, themes);
        this.themes = themes;
        this.context = context;
        this.themeAdapterID = themeAdapterID;
        this.res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(themeAdapterID, parent, false);
        }

        final Theme theme = themes.get(position);
        if (theme != null)
        {
            TextView btnTheme = (TextView) view.findViewById(R.id.button);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

            try {
                int iconeID = view.getResources().getIdentifier(theme.getIcone(),"mipmap","fr.mmg.mmquizz");

                Drawable drawableIcone = ContextCompat.getDrawable(context, iconeID);
                imageView.setImageDrawable(drawableIcone);
                }
                catch (Exception e)
                {
                }

                btnTheme.setText(theme.getLibelle());
        }
        return view;
    }
}
