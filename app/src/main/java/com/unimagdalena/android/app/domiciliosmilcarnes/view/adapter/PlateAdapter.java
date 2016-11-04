package com.unimagdalena.android.app.domiciliosmilcarnes.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Plate;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(PlateAdapter.ViewHolder viewHolder, View itemView, int position);
        void OnLongItemClick(PlateAdapter.ViewHolder viewHolder, View itemView, int position);
    }

    private ArrayList<Plate> plateArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public PlateAdapter(ArrayList<Plate> plateArrayList, Context context) {
        this.plateArrayList = plateArrayList;
        this.context = context;
    }

    @Override
    public PlateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_item_layout, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(PlateAdapter.ViewHolder holder, int position) {
        Plate plate = plateArrayList.get(position);

        String path = "http://" + MilCarnesApp.milCarnesApp.getIP() + ":" + MilCarnesApp.milCarnesApp.getPORT() + "/DomiciliosMilCarnes/img/platos/" + plate.getIdplato() + ".png";

        Log.e(getClass().getSimpleName(), plate.getTipo());

        Glide.with(context).load(path).dontAnimate().into(holder.picture);

        holder.namePlate.setText(plate.getNombre());
        holder.pricePlate.setText(String.format(Locale.ENGLISH, "$%d", plate.getPrecioUnitario()));
        holder.typePlate.setText(plate.getTipo());
    }

    @Override
    public int getItemCount() {
        return plateArrayList.size();
    }

    public Plate getPlate(int position){
        return plateArrayList.get(position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.picture)
        ImageView picture;

        @BindView(R.id.namePlate)
        TextView namePlate;

        @BindView(R.id.typePlate)
        TextView typePlate;

        @BindView(R.id.pricePlate)
        TextView pricePlate;

        private OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.onItemClickListener = onItemClickListener;
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemClick(this, itemView, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.OnLongItemClick(this, itemView, getAdapterPosition());
            }
            return false;
        }
    }
}
