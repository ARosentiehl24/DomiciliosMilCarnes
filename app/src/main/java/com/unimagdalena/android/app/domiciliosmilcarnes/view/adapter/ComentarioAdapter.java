package com.unimagdalena.android.app.domiciliosmilcarnes.view.adapter;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
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
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Comentario;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by receducativos on 17/11/2016.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private ArrayList<Comentario> comentarios;
    private Context context;

    public ComentarioAdapter(ArrayList<Comentario> comentarios, Context context) {
        this.comentarios = comentarios;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComentarioAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comentario_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            String data[] = comentarios.get(position).getComentario().split(":");

            String path = "http://" + MilCarnesApp.milCarnesApp.getIP() + ":" + MilCarnesApp.milCarnesApp.getPORT() + "/DomiciliosMilCarnes/img/usuarios/" + data[0] + ".jpg";

            Log.e(getClass().getSimpleName(), path);

            Glide.with(context).load(path).into(holder.foto);
            holder.nombreUsuario.setText(data[1]);
            holder.comentario.setText(data[2]);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);

        notifyItemInserted(comentarios.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comentario)
        TextView comentario;

        @BindView(R.id.nombreUsuario)
        TextView nombreUsuario;

        @BindView(R.id.fotoUsuario)
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
