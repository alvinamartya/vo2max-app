package com.example.atlit.Utils;

import com.example.atlit.Model.BalkeGet;
import com.example.atlit.Model.Beep;
import com.example.atlit.Model.BeepGet;
import com.example.atlit.Model.CooperGet;
import com.example.atlit.Model.Process;
import com.example.atlit.Model.getBalkeModel;
import com.example.atlit.RetrofitModel.BalkeModel;
import com.example.atlit.RetrofitModel.beepModel;
import com.example.atlit.RetrofitModel.deleteDataModel;
import com.example.atlit.RetrofitModel.atlitModels;
import com.example.atlit.RetrofitModel.getBeepModel;
import com.example.atlit.RetrofitModel.getCooperModel;
import com.example.atlit.RetrofitModel.CooperModel;
import com.example.atlit.RetrofitModel.atlitModel;
import com.example.atlit.RetrofitModel.cabangOlahragaModel;
import com.example.atlit.RetrofitModel.loginModel;
import com.example.atlit.RetrofitModel.pelatihModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login/login")
    Call<loginModel> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<loginModel> postLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("akses") String akses
    );

    @FormUrlEncoded
    @POST("atlit")
    Call<atlitModel> postAtlit(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("tinggi_badan") String tinggi_badan,
            @Field("berat_badan") String berat_badan,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("atlit") int atlit,
            @Field("cabang_olahraga") String cabang_olahraga
    );

    @FormUrlEncoded
    @POST("pelatih/getSingleData")
    Call<pelatihModel> getPelatih(@Field("refid") int refid);

    @FormUrlEncoded
    @POST("pelatih")
    Call<pelatihModel> postPelatih(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("cabang_olahraga") String cabang_olahraga
    );

    @FormUrlEncoded
    @POST("pelatih/updatedata")
    Call<pelatihModel> updateData(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("cabang_olahraga") String cabang_olahraga
    );

    @GET("pelatih/getDisctint")
    Call<cabangOlahragaModel> getCabangOlahraga();

    @FormUrlEncoded
    @POST("cooper")
    Call<CooperModel> cooperPost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("waktu") int waktu,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("Cooper/getData")
    Call<getCooperModel> cooperdata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("Cooper/getDataPelatih")
    Call<getCooperModel> cooperDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("Cooper/deleteAllData")
    Call<deleteDataModel> cooperDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("atlit/getData")
    Call<atlitModels> atlitgets(@Field("cabor") String cabor);

    @FormUrlEncoded
    @POST("balke")
    Call<BalkeModel> balkePost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("jarak_ditempuh") float jarak_ditempuh,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("balke/getData")
    Call<getBalkeModel> balkedata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("balke/getDataPelatih")
    Call<getBalkeModel> balkeDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("balke/deleteAllData")
    Call<deleteDataModel> balkeDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("atlit/getSingleData")
    Call<atlitModel> atlitGets(@Field("refid") int refid);

    @FormUrlEncoded
    @POST("beep")
    Call<beepModel> beepPost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("level") int level,
            @Field("shuttle") int ahuttle,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("beep/getData")
    Call<getBeepModel> beepdata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("beep/getDataPelatih")
    Call<getBeepModel> beepDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("beep/deleteAllData")
    Call<deleteDataModel> beepDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("login/changePassword")
    Call<deleteDataModel> changePassword(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("atlit/updatedata")
    Call<atlitModel> updateDataAtlit(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("tinggi_badan") float tinggi_badan,
            @Field("berat_badan") float berat_badan,
            @Field("jenis_kelamin") String jenis_kelamin
    );

    @FormUrlEncoded
    @POST("balke/getListData")
    Call<List<BalkeGet>> getListData(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("Cooper/getListData")
    Call<List<CooperGet>> getListDataCooper(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("beep/getListData")
    Call<List<BeepGet>> getListDataBeep(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("balke/setSolusi")
    Call<Process> setSolusiBalke(@Field("id") int id, @Field("solusi") String solusi);

    @FormUrlEncoded
    @POST("beep/setSolusi")
    Call<Process> setSolusiBeep(@Field("id") int id, @Field("solusi") String solusi);

    @FormUrlEncoded
    @POST("Cooper/setSolusi")
    Call<Process> setSolusiCooper(@Field("id") int id, @Field("solusi") String solusi);
}
