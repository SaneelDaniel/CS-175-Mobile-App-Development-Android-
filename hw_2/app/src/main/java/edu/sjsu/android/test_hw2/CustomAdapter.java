package edu.sjsu.android.test_hw2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * CUSTOM ADAPTER CLASS THAT CREATES A 'VIEW HOLDER' FOR THE CUSTOM ROWS AND INFLATES THE
 * ARRAY LIST OF OBJECTS PASSED FROM THE MAIN ACTIVITY
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    //private variables
    private ArrayList<Animal> list;
    private AlertDialog.Builder builder;

    /**
     * the constructor for an adapter that takes the list of Animals to be inflated
     * @param animalList
     */
    public CustomAdapter(ArrayList<Animal> animalList)
    {
        this.list = animalList;
    }


    @Override
    /**
     * creates the viewholder and inflates it to the specific view
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    /**
     * binds the specific valies to the UI objects in the view holder
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getAnimalName());
        holder.imageView.setImageResource(list.get(position).getImageResID());
    }

    @Override
    /**
     * returns the list size
     */
    public int getItemCount() {
        return list.size();
    }

    /**
     * inner class that creates a view holder specific to the requirement
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public TextView descriptionTexttView;
        public int position;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.rowText);
            imageView = (ImageView) view.findViewById(R.id.rowImage);
            descriptionTexttView = view.findViewById(R.id.infoDetails_description_textView);
            position = getAdapterPosition();
            view.setOnClickListener(this);
        }

        @Override
        /**
         * on click for the elements in the list
         * when clicked, it initiates the information detail view activity
         */
        public void onClick(final View view) {
            final Intent intent = new Intent(view.getContext(), InformationDetail.class);
            int position = getAdapterPosition();

            Animal current = list.get(position);
            intent.putExtra("imageResID", current.getImageResID());
            intent.putExtra("animalName", current.getAnimalName());
            intent.putExtra("animalDesc", current.getAnimalDesc());
            if (position == list.size() - 1) {
                builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("This Animal might be fearful. Do you still want to proceed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(view.getContext(), intent, null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
        }
            else {
                startActivity(view.getContext(), intent, null);
            }
    }
}
}
