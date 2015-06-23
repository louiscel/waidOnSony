package fr.ca.sa.waid;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonymobile.smartextension.advancedlayouts.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private Activity activity;
    //la liste de tous les projets
    //la liste de projets favoris
    private ArrayList<String> favoriteProjectsList=new ArrayList<String>();
    private static LayoutInflater inflater=null;
    private final File favoriteProjectFile;



    private final Context context;
    private final String[] projects;

    public CustomAdapter(Context context, String[] values) {
        super(context, R.layout.project_line_layout, values);
        this.context = context;
        this.projects = values;
        //récupération du fichier des projets favoris
        favoriteProjectFile = new File(context.getFilesDir(), "favoriteProjectFile.txt");
        try {
            FileReader reader = new FileReader(favoriteProjectFile);
            BufferedReader buf=new BufferedReader(reader);
            String favori=null;
            while ((favori=buf.readLine())!=null){
                favoriteProjectsList.add(favori);
            }
        }catch (IOException e){
            //si on n'a pas encore de favoris, affichage d'un message
            Toast ewceptionToast= Toast.makeText(context, R.string.no_favorites, Toast.LENGTH_SHORT);
            ewceptionToast.show();
            try {
                favoriteProjectFile.createNewFile();
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        final ViewGroup parentView=parent;
        TextView project =null;
        final ImageButton favoriteImageButton;
        if (vi==null){
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.project_line_layout, parent,false);

            project = (TextView)vi.findViewById(R.id.projectLabel);
            favoriteImageButton = (ImageButton)vi.findViewById(R.id.favorite);

        }else {
            project = (TextView)convertView.findViewById(R.id.projectLabel);
            favoriteImageButton = (ImageButton)convertView.findViewById(R.id.favorite);
        }

        final String projectName= projects[position];

        project.setText(projectName);
        //morceau de code qui bugue: l'étoile des favoris est affichée une fois toutes les 14 lignes
        /*if(favoriteProjectsList.contains(projectName)){
            Log.d(this.getClass().getName(),"trouvé dans la liste des favoris");
            favoriteImageButton.setImageResource(R.mipmap.yellow_star);
            //le listener exclut le projet des favoris
            favoriteImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //faire passer l'étoile de jaune à transparent
                    Toast toast = Toast.makeText(arg0.getContext(), R.string.favorite_removed, Toast.LENGTH_SHORT);
                    toast.show();
                    favoriteImageButton.setImageResource(R.mipmap.blank_star);

                    //supprimer du fichier des favoris
                    try {
                        //lecture de tout le fichier
                        FileReader reader = new FileReader(favoriteProjectFile);
                        BufferedReader buf=new BufferedReader(reader);
                        String favori=null;
                        while ((favori=buf.readLine())!=null){
                            favoriteProjectsList.add(favori);
                        }
                        FileWriter fw = new FileWriter(favoriteProjectFile);
                        //nettoyage complet du fichier
                        favoriteProjectFile.delete();
                        //suppression du projet de la liste
                        favoriteProjectsList.remove(projectName);
                        //recontruction du fichier sans le favori retiré
                        String favoriteProjectName=null;
                        while ((favoriteProjectName=favoriteProjectsList.iterator().next())!=null){
                            fw.write(favoriteProjectName);
                        }
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast exceptionToast = Toast.makeText(arg0.getContext(), "Impossible d'écrire dans les favoris", Toast.LENGTH_SHORT);
                        exceptionToast.show();
                    }

                }


            });
        }else {
            Log.d(this.getClass().getName(),"non trouvé dans la liste des favoris");
            favoriteImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //faire passer l'étoile de transparent à jaune
                    Toast toast = Toast.makeText(arg0.getContext(), R.string.favorite_added, Toast.LENGTH_SHORT);
                    toast.show();
                    favoriteImageButton.setImageResource(R.mipmap.yellow_star);

                    //sauvegarder dans le fichier des favoris
                    try {
                        FileWriter fw = new FileWriter(favoriteProjectFile);
                        fw.write(projectName);
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast exceptionToast = Toast.makeText(arg0.getContext(), "Impossible d'écrire dans les favoris", Toast.LENGTH_SHORT);
                        exceptionToast.show();
                    }

                }


            });
        }*/

        favoriteImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //faire passer l'étoile de transparent à jaune
                Toast toast = Toast.makeText(arg0.getContext(), R.string.favorite_added, Toast.LENGTH_SHORT);
                toast.show();

                ImageButton imageButton = (ImageButton) arg0.findViewById(R.id.favorite);
                imageButton.setImageResource(R.drawable.yellow_star);

                //sauvegarder dans le fichier des favoris
                try {
                    //File favoriteProjectFile = new File(arg0.getContext().getFilesDir(), "favoriteProjectFile.txt");
                    //File favoriteProjectFile = favoriteProjectFile=new File("/storage/sdcard0/WAID/favoriteProjectFile.txt");
                    FileWriter fw = new FileWriter(favoriteProjectFile);
                    Log.d(this.getClass().getName(), "Nom du projet sélectionné:" + projectName);
                    fw.write(projectName);
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast exceptionToast = Toast.makeText(arg0.getContext(), "Impossible d'écrire dans les favoris", Toast.LENGTH_SHORT);
                    exceptionToast.show();
                }

            }


        });

        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v.findViewById(R.id.text);
                textView.setText(projectName);
                Chronometer chrono = (Chronometer) v.findViewById(R.id.chronometer);
                //TODO:démarrer le chrono
            }
        });


        /*ImageButton projectDetailButton = (ImageButton)vi.findViewById(R.id.projectDetailImage);

        projectDetailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast toast = Toast.makeText(arg0.getContext(), "pas encore implémenté...", Toast.LENGTH_SHORT);
                toast.show();

            }

        });*/


        return vi;
}

}

