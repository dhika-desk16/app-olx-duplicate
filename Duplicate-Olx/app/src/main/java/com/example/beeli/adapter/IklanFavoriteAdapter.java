package com.example.beeli.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.Helper;
import com.example.beeli.databinding.ItemKategoriBinding;
import com.example.beeli.model.IklanRekomendasiFavoriteModel;

import java.util.List;

public class IklanFavoriteAdapter extends RecyclerView.Adapter<IklanFavoriteAdapter.ViewHolder> {
    private List<IklanRekomendasiFavoriteModel.IklanFavorit> dataMap;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private String tahun, kamarT, kamarM, luasTanah, gajiD, gajiS;

    public interface OnItemClickListener {
        void onItemClick(IklanRekomendasiFavoriteModel.IklanFavorit data);
    }
    public void setOnClickItemListener(IklanFavoriteAdapter.OnItemClickListener onClickItemListener) {
        this.onItemClickListener = onClickItemListener;
    }

    public IklanFavoriteAdapter(Context context, List<IklanRekomendasiFavoriteModel.IklanFavorit> dataMap) {
        this.context = context;
        this.dataMap = dataMap;
    }

    @NonNull
    @Override
    public IklanFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IklanFavoriteAdapter.ViewHolder(ItemKategoriBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IklanFavoriteAdapter.ViewHolder holder, int position) {
        IklanRekomendasiFavoriteModel.IklanFavorit data = dataMap.get(position);
        tahun = data.getDataIklan().getTahun();
        kamarT= data.getDataIklan().getKamar_tidur();
        kamarM= data.getDataIklan().getKamar_mandi();
        luasTanah = data.getDataIklan().getLuas_tanah();
        gajiD = data.getDataIklan().getGaji_dari();
        gajiS = data.getDataIklan().getGaji_sampai();

        if (tahun != null){
            holder.binding.optional.setText(tahun);
        } else if (kamarT != null && kamarM != null && luasTanah != null) {
            holder.binding.optional.setText(kamarT+ " KT - "+ kamarM+ " KM - "+ luasTanah);
        } else {
            holder.binding.optional.setVisibility(View.GONE);
        }

        if (gajiD != null && gajiS != null){
            gajiD = Helper.getCurrencyFormat(Integer.parseInt(gajiD));
            gajiS = Helper.getCurrencyFormat(Integer.parseInt(gajiS));
            holder.binding.harga.setText(gajiD+ " - "+ gajiS);
        } else {
            holder.binding.harga.setText(Helper.getCurrencyFormat(data.getDataIklan().getHarga()));
        }

        holder.binding.judulIklan.setText(data.getDataIklan().getJudul_iklan());
        holder.binding.image.setImageBitmap(Helper.decodeBase64Image(data.getDataGambar().getGambar1()));
        holder.binding.lokasi.setText(data.getDataIklan().getAlamat());

        holder.binding.harga.setEllipsize(TextUtils.TruncateAt.END);
        holder.binding.judulIklan.setEllipsize(TextUtils.TruncateAt.END);
        holder.binding.lokasi.setEllipsize(TextUtils.TruncateAt.END);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemKategoriBinding binding;

        public ViewHolder(@NonNull ItemKategoriBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    //    func
}
