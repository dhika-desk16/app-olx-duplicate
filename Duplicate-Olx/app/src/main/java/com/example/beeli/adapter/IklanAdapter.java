package com.example.beeli.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.Helper;
import com.example.beeli.databinding.ItemKategoriBinding;
import com.example.beeli.model.IklanJasaModel;
import com.example.beeli.model.IklanMerkModel;
import com.example.beeli.model.IklanMobilMotorModel;
import com.example.beeli.model.IklanModel;
import com.example.beeli.model.IklanPropertiModel;
import com.example.beeli.model.IklanTipeModel;

import java.util.List;
import java.util.Map;

public class IklanAdapter extends RecyclerView.Adapter<IklanAdapter.ViewHolder> {
    private List<?> dataMap;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private String dataType;

    public interface OnItemClickListener {
        void onItemClick(Object data);
    }
    public void setOnClickItemListener(OnItemClickListener onClickItemListener) {
        this.onItemClickListener = onClickItemListener;
    }

    public IklanAdapter(Context context, List<?> dataMap, String dataType) {
        this.context = context;
        this.dataMap = dataMap;
        this.dataType = dataType;
    }

    @NonNull
    @Override
    public IklanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemKategoriBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IklanAdapter.ViewHolder holder, int position) {
        if ("Iklan".equals(dataType)) {
            IklanModel.Iklan data = getIklanDataAtPosition(position);
            bindIklanData(holder, data);
        } else if ("Tipe".equals(dataType)) {
            IklanTipeModel.Iklan data = getTipeDataAtPosition(position);
            bindIklanData(holder, data);
        } else if ("Merk".equals(dataType)) {
            IklanMerkModel.Iklan data = getMerkDataAtPosition(position);
            bindIklanData(holder, data);
        } else if ("MobilMotor".equals(dataType)) {
            IklanMobilMotorModel.Iklan data = getMobilMotorDataAtPosition(position);
            bindIklanData(holder, data);
        } else if ("Properti".equals(dataType)) {
            IklanPropertiModel.Iklan data = getPropertiDataAtPosition(position);
            bindIklanData(holder, data);
        } else if ("Jasa".equals(dataType)) {
            IklanJasaModel.Iklan data = getJasaDataAtPosition(position);
            bindIklanData(holder, data);
        }
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
    private IklanModel.Iklan getIklanDataAtPosition(int position) {
        return (IklanModel.Iklan) ((Map<String, IklanModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private IklanTipeModel.Iklan getTipeDataAtPosition(int position) {
        return (IklanTipeModel.Iklan) ((Map<String, IklanTipeModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private IklanMerkModel.Iklan getMerkDataAtPosition(int position) {
        return (IklanMerkModel.Iklan) ((Map<String, IklanMerkModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private IklanMobilMotorModel.Iklan getMobilMotorDataAtPosition(int position) {
        return (IklanMobilMotorModel.Iklan) ((Map<String, IklanMobilMotorModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private IklanPropertiModel.Iklan getPropertiDataAtPosition(int position) {
        return (IklanPropertiModel.Iklan) ((Map<String, IklanPropertiModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private IklanJasaModel.Iklan getJasaDataAtPosition(int position) {
        return (IklanJasaModel.Iklan) ((Map<String, IklanJasaModel.Iklan>) dataMap.get(position)).values().iterator().next();
    }

    private void bindIklanData(ViewHolder holder, Object data) {
        if (data instanceof IklanJasaModel.Iklan) {
            IklanJasaModel.Iklan jasaData = (IklanJasaModel.Iklan) data;
            String gaji_dari = Helper.getCurrencyFormat(Integer.parseInt(jasaData.getDataIklan().getGaji_dari()));
            String gaji_sampai = Helper.getCurrencyFormat(Integer.parseInt(jasaData.getDataIklan().getGaji_sampai()));

            holder.binding.harga.setText(gaji_dari +" - "+ gaji_sampai);
            holder.binding.judulIklan.setText(jasaData.getDataIklan().getJudul_iklan());
            holder.binding.image.setImageBitmap(decodeBase64Image(jasaData.getDataGambar().getGambar1()));
            holder.binding.lokasi.setText(jasaData.getDataIklan().getAlamat());
            holder.binding.optional.setVisibility(View.GONE);

            holder.binding.harga.setEllipsize(TextUtils.TruncateAt.END);
            holder.binding.judulIklan.setEllipsize(TextUtils.TruncateAt.END);

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(jasaData);
                }
            });
        } else if (data instanceof IklanMobilMotorModel.Iklan) {
                IklanMobilMotorModel.Iklan mobilMototrData = (IklanMobilMotorModel.Iklan) data;
                holder.binding.harga.setText(Helper.getCurrencyFormat(mobilMototrData.getDataIklan().getHarga()));
                holder.binding.judulIklan.setText(mobilMototrData.getDataIklan().getJudul_iklan());
                holder.binding.image.setImageBitmap(decodeBase64Image(mobilMototrData.getDataGambar().getGambar1()));
                holder.binding.lokasi.setText(mobilMototrData.getDataIklan().getAlamat());
                holder.binding.optional.setText(mobilMototrData.getDataIklan().getTahun());

                holder.binding.harga.setEllipsize(TextUtils.TruncateAt.END);
                holder.binding.judulIklan.setEllipsize(TextUtils.TruncateAt.END);

                holder.itemView.setOnClickListener(v -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(mobilMototrData);
                    }
                });

        } else if (data instanceof IklanModel.Iklan) {
            IklanModel.Iklan iklanData = (IklanModel.Iklan) data;
            holder.binding.harga.setText(Helper.getCurrencyFormat(iklanData.getDataIklan().getHarga()));
            holder.binding.judulIklan.setText(iklanData.getDataIklan().getJudul_iklan());
            holder.binding.image.setImageBitmap(decodeBase64Image(iklanData.getDataGambar().getGambar1()));
            holder.binding.lokasi.setText(iklanData.getDataIklan().getAlamat());
            holder.binding.optional.setVisibility(View.GONE);

            holder.binding.harga.setEllipsize(TextUtils.TruncateAt.END);
            holder.binding.judulIklan.setEllipsize(TextUtils.TruncateAt.END);

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(iklanData);
                }
            });
    }}

    private Bitmap decodeBase64Image(String base64Image) {
        if (base64Image == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}