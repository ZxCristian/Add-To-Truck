package com.example.addtotruck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;

public class SubAdapter extends FirebaseRecyclerAdapter<Orders,SubAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SubAdapter(@NonNull FirebaseRecyclerOptions<Orders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Orders model) {
        holder.Name.setText(model.getName());
        holder.Price.setText(model.getPrice());
        holder.Quantity.setText(model.getQuantity());

        Glide.with(holder.imageView.getContext())
                .load(model.getImageurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.imageView);

        holder.itemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.imageView.getContext())
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_item))
                        .setExpanded(true,800)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                TextView name = view.findViewById(R.id.ItemName);
                TextView price = view.findViewById(R.id.ItemPrice);
                EditText quantity = view.findViewById(R.id.Quantity);

                Button Update = view.findViewById(R.id.Update);

                name.setText(model.getName());
                price.setText(model.getPrice());
                quantity.setText(model.getQuantity());

                dialogPlus.show();

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("quantity",quantity.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Orders")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.Quantity.getContext(), "Quantity has been modified Sucessfully.",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.Quantity.getContext(), "Error, Quantity has not changed.",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Orders")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.Name.getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(holder.Name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView Name, Price, Quantity;
        Button itemUpdate,itemDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.imageName);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Price = (TextView) itemView.findViewById(R.id.Price);
            Quantity = (TextView) itemView.findViewById(R.id.Quantity);

            itemUpdate = (Button) itemView.findViewById(R.id.itemUpdate);
            itemDelete = (Button) itemView.findViewById(R.id.itemDelete);
        }
    }

}
