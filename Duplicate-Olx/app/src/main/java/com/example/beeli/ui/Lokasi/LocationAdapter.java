package com.example.beeli.ui.Lokasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeli.R;
import com.example.beeli.model.DistrictModel;
import com.example.beeli.model.ProvinceModel;
import com.example.beeli.model.RegenciesModel;
import com.example.beeli.model.VillageModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_PROVINCE = 0;
    private static final int VIEW_TYPE_REGENCY = 1;
    private static final int VIEW_TYPE_DISTRICT = 2;
    private static final int VIEW_TYPE_VILLAGE = 3;

    private List<ProvinceModel> provinceList;
    private List<RegenciesModel> regenciesList;
    private List<DistrictModel> districtList;
    private List<VillageModel> villageList;
    private OnItemClickListener onItemClickListener;

    private boolean isProvince;
    private boolean isRegency;
    private boolean isDistrict;
    private boolean isVillage;

    public LocationAdapter(List<ProvinceModel> provinceList, OnItemClickListener onItemClickListener) {
        this.provinceList = provinceList;
        this.onItemClickListener = onItemClickListener;
        this.isProvince = true;
        this.isRegency = false;
        this.isDistrict = false;
        this.isVillage = false;
    }

    public LocationAdapter(List<RegenciesModel> regenciesList, OnItemClickListener onItemClickListener, boolean isProvince) {
        this.regenciesList = regenciesList;
        this.onItemClickListener = onItemClickListener;
        this.isProvince = isProvince;
        this.isRegency = !isProvince;
        this.isDistrict = false;
        this.isVillage = false;
    }

    public LocationAdapter(List<DistrictModel> districtList, OnItemClickListener onItemClickListener, boolean isProvince, boolean isRegency) {
        this.districtList = districtList;
        this.onItemClickListener = onItemClickListener;
        this.isProvince = isProvince;
        this.isRegency = isRegency;
        this.isDistrict = !isProvince && !isRegency;
        this.isVillage = false;
    }

    public LocationAdapter(List<VillageModel> villageList, OnItemClickListener onItemClickListener, boolean isProvince, boolean isRegency, boolean isDistrict) {
        this.villageList = villageList;
        this.onItemClickListener = onItemClickListener;
        this.isProvince = isProvince;
        this.isRegency = isRegency;
        this.isDistrict = isDistrict;
        this.isVillage = !isProvince && !isRegency && !isDistrict;
    }

    @Override
    public int getItemViewType(int position) {
        if (isProvince) {
            return VIEW_TYPE_PROVINCE;
        } else if (isRegency) {
            return VIEW_TYPE_REGENCY;
        } else if (isDistrict) {
            return VIEW_TYPE_DISTRICT;
        } else {
            return VIEW_TYPE_VILLAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);

        if (viewType == VIEW_TYPE_PROVINCE) {
            return new ProvinceViewHolder(view, onItemClickListener);
        } else if (viewType == VIEW_TYPE_REGENCY) {
            return new RegencyViewHolder(view, onItemClickListener);
        } else if (viewType == VIEW_TYPE_DISTRICT) {
            return new DistrictViewHolder(view, onItemClickListener);
        } else {
            return new VillageViewHolder(view, onItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProvinceViewHolder) {
            ((ProvinceViewHolder) holder).bind(provinceList.get(position));
        } else if (holder instanceof RegencyViewHolder) {
            ((RegencyViewHolder) holder).bind(regenciesList.get(position));
        } else if (holder instanceof DistrictViewHolder) {
            ((DistrictViewHolder) holder).bind(districtList.get(position));
        } else if (holder instanceof VillageViewHolder) {
            ((VillageViewHolder) holder).bind(villageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (isProvince) {
            return provinceList.size();
        } else if (isRegency) {
            return regenciesList.size();
        } else if (isDistrict) {
            return districtList.size();
        } else {
            return villageList.size();
        }
    }

    public void setProvinces(List<ProvinceModel> provinces) {
        this.provinceList = provinces;
        this.isProvince = true;
        this.isRegency = false;
        this.isDistrict = false;
        this.isVillage = false;
        notifyDataSetChanged();
    }

    public void setRegencies(List<RegenciesModel> regencies) {
        this.regenciesList = regencies;
        this.isProvince = false;
        this.isRegency = true;
        this.isDistrict = false;
        this.isVillage = false;
        notifyDataSetChanged();
    }

    public void setDistricts(List<DistrictModel> districts) {
        this.districtList = districts;
        this.isProvince = false;
        this.isRegency = false;
        this.isDistrict = true;
        this.isVillage = false;
        notifyDataSetChanged();
    }

    public void setVillage(List<VillageModel> villages) {
        this.villageList = villages;
        this.isProvince = false;
        this.isRegency = false;
        this.isDistrict = false;
        this.isVillage = true;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClickProvince(ProvinceModel province);
        void onItemClickRegency(RegenciesModel regency);
        void onItemClickDistrict(DistrictModel district);
        void onItemClickVillage(VillageModel village);
    }

    static class ProvinceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView provinceName;
        OnItemClickListener onItemClickListener;
        ProvinceModel currentProvince;

        public ProvinceViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            provinceName = itemView.findViewById(R.id.lokasi1);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(ProvinceModel province) {
            currentProvince = province;
            provinceName.setText(province.getName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickProvince(currentProvince);
            }
        }
    }

    static class RegencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView regencyName;
        OnItemClickListener onItemClickListener;
        RegenciesModel currentRegency;

        public RegencyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            regencyName = itemView.findViewById(R.id.lokasi1);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(RegenciesModel regency) {
            currentRegency = regency;
            regencyName.setText(regency.getName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickRegency(currentRegency);
            }
        }
    }

    static class DistrictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView districtName;
        OnItemClickListener onItemClickListener;
        DistrictModel currentDistrict;

        public DistrictViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            districtName = itemView.findViewById(R.id.lokasi1);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(DistrictModel district) {
            currentDistrict = district;
            districtName.setText(district.getName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickDistrict(currentDistrict);
            }
        }
    }

    static class VillageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView villageName;
        OnItemClickListener onItemClickListener;
        VillageModel currentVillage;

        public VillageViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            villageName = itemView.findViewById(R.id.lokasi1);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(VillageModel village) {
            currentVillage = village;
            villageName.setText(village.getName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickVillage(currentVillage);
            }
        }
    }
}
