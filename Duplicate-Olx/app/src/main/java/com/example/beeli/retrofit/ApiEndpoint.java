package com.example.beeli.retrofit;

import com.example.beeli.model.IklanRekomendasiFavoriteModel;
import com.example.beeli.model.IklanJasaModel;
import com.example.beeli.model.IklanMerkModel;
import com.example.beeli.model.IklanMobilMotorModel;
import com.example.beeli.model.IklanModel;
import com.example.beeli.model.IklanPropertiModel;
import com.example.beeli.model.IklanSayaModel;
import com.example.beeli.model.IklanTipeModel;
import com.example.beeli.model.LoginModel;
import com.example.beeli.model.ProfileModel;
import com.example.beeli.model.DistrictModel;
import com.example.beeli.model.ProvinceModel;
import com.example.beeli.model.RegenciesModel;
import com.example.beeli.model.VillageModel;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndpoint {

//    Post API
    @POST("register")
    Call<ResponseBody> register(@Body RequestBody body);
    @POST("verify")
    Call<ResponseBody> verifyOTP(@Body RequestBody body);
    @POST("resendotp")
    Call<ResponseBody> resendOTP(@Body RequestBody body);
    @POST("editprofile")
    Call<ProfileModel.User> editProfile(@Body RequestBody body);
    @POST("login")
    Call<LoginModel> login(@Body RequestBody body);
    @POST("postuserfavorites/{kodeIklan}")
    Call<ResponseBody> postFavorite(@Path("kodeIklan") String kodeIklan);

    @POST("postiklanperlengkapanbayidananak/{subKategori}")
    Call<ResponseBody> postIAnak(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanelektronik/{subKategori}")
    Call<ResponseBody> postIElektronik(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanhobidanolahraga/{subKategori}")
    Call<ResponseBody> postIHobi(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanjasadanlowongankerja/{subKategori}")
    Call<ResponseBody> postIJasa(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklankantordanindustri/{subKategori}")
    Call<ResponseBody> postIKantor(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanmobil/{subKategori}")
    Call<ResponseBody> postIMobil(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanmotor/{subKategori}")
    Call<ResponseBody> postIMotor(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklankeperluanpribadi/{subKategori}")
    Call<ResponseBody> postIPribadi(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanproperti/{subKategori}")
    Call<ResponseBody> postIProperti(@Path("subKategori") String subKategori, @Body RequestBody body);
    @POST("postiklanrumahtangga/{subKategori}")
    Call<ResponseBody> postIRumah(@Path("subKategori") String subKategori, @Body RequestBody body);

//    Get API
    @GET("profile")
    Call<ProfileModel> getProfile();
    @GET("getallcategoryiklan")
    Call<IklanSayaModel> getIklanSaya();
    @GET("getuserfavorites")
    Call<IklanRekomendasiFavoriteModel> getIklanFavorites();

    @GET("getiklanperlengkapanbayidananak/{subKategori}")
    Call<IklanModel> getIAnak(@Path("subKategori") String subKategori);
    @GET("getiklanrumahtangga/{subKategori}")
    Call<IklanModel> getIRumah(@Path("subKategori") String subKategori);
    @GET("getiklanelektronikdangadget/{subKategori}")
    Call<IklanMerkModel> getIElektronik(@Path("subKategori") String subKategori);
    @GET("getiklankantordanindustri/{subKategori}")
    Call<IklanTipeModel> getIKantor(@Path("subKategori") String subKategori);
    @GET("getiklanhobidanolahraga/{subKategori}")
    Call<IklanTipeModel> getIHobi(@Path("subKategori") String subKategori);
    @GET("getiklankeperluanpribadi/{subKategori}")
    Call<IklanTipeModel> getIPribadi(@Path("subKategori") String subKategori);
    @GET("getiklanmobil/{subKategori}")
    Call<IklanMobilMotorModel> getIMobil(@Path("subKategori") String subKategori);
    @GET("getiklanmotor/{subKategori}")
    Call<IklanMobilMotorModel> getIMotor(@Path("subKategori") String subKategori);
    @GET("getiklanjasadanlowongan/{subKategori}")
    Call<IklanJasaModel> getIJasa(@Path("subKategori") String subKategori);
    @GET("getiklanproperti/{subKategori}")
    Call<IklanPropertiModel> getIProperti(@Path("subKategori") String subKategori);
    @GET("provinces")
    Call<List<ProvinceModel>> getProvince();
    @GET("regencies/{province_id}")
    Call<List<RegenciesModel>> getRegencies(@Path("province_id") String provinceId);
    @GET("districts/{regency_id}")
    Call<List<DistrictModel>> getDistricts(@Path("regency_id") String regencyId);
    @GET("villages/{village_id}")
    Call<List<VillageModel>> getVillage(@Path("village_id") String districtId);
    @GET("getrecomendedbyfavoritekategori")
    Call<IklanRekomendasiFavoriteModel> getRekomendasi();

//    Delete API
    @DELETE("deleteuserfavorite/{kodeIklan}")
    Call<Void> deleteFavorite(@Path("kodeIklan") String kodeIklan);
}