package com.unimagdalena.android.app.domiciliosmilcarnes.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Comentario;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Plate;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by receducativos on 03/11/2016.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Plate> plates;

    public OrderAdapter(Context context, ArrayList<Plate> plates) {
        this.context = context;
        this.plates = plates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plate plate = plates.get(position);

        holder.namePlate.setText(plate.getNombre());
        holder.amountPlate.setText(String.format(Locale.US, "$%d", plate.getPrecioUnitario()));
    }

    @Override
    public int getItemCount() {
        return plates.size();
    }

    public Plate plate(int index) {
        return plates.get(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.namePlate)
        TextView namePlate;

        @BindView(R.id.amountPlate)
        TextView amountPlate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
