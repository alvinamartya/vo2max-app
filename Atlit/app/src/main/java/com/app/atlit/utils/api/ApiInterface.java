package com.app.atlit.utils.api;

import com.app.atlit.model.pojo.BalkeGetPojo;
import com.app.atlit.model.pojo.BeepGetPojo;
import com.app.atlit.model.pojo.CooperGetPojo;
import com.app.atlit.model.pojo.DataProcessPojo;
import com.app.atlit.model.pojo.GetBalkePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login/login")
    Call<com.app.atlit.model.pojo.retrofit.LoginRetrofit> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<com.app.atlit.model.pojo.retrofit.LoginRetrofit> postLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("akses") String akses
    );

    @FormUrlEncoded
    @POST("atlit")
    Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> postAtlit(
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
    Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> getPelatih(@Field("refid") int refid);

    @FormUrlEncoded
    @POST("pelatih")
    Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> postPelatih(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("cabang_olahraga") String cabang_olahraga
    );

    @FormUrlEncoded
    @POST("pelatih/updatedata")
    Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> updateData(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("cabang_olahraga") String cabang_olahraga
    );

    @GET("pelatih/getDisctint")
    Call<com.app.atlit.model.pojo.retrofit.CabangOlahragaRetrofit> getCabangOlahraga();

    @FormUrlEncoded
    @POST("cooper")
    Call<com.app.atlit.model.pojo.retrofit.CooperRetrofit> cooperPost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("waktu") int waktu,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("Cooper/getData")
    Call<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> cooperdata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("Cooper/getDataPelatih")
    Call<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> cooperDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("Cooper/deleteAllData")
    Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> cooperDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("atlit/getData")
    Call<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit> atlitgets(@Field("cabor") String cabor);

    @FormUrlEncoded
    @POST("balke")
    Call<com.app.atlit.model.pojo.retrofit.BalkeRetrofit> balkePost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("jarak_ditempuh") float jarak_ditempuh,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("balke/getData")
    Call<GetBalkePojo> balkedata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("balke/getDataPelatih")
    Call<GetBalkePojo> balkeDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("balke/deleteAllData")
    Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> balkeDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("atlit/getSingleData")
    Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> atlitGets(@Field("refid") int refid);

    @FormUrlEncoded
    @POST("beep")
    Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> beepPost(
            @Field("bulan") int bulan,
            @Field("minggu") int minggu,
            @Field("level") int level,
            @Field("shuttle") long shuttle,
            @Field("vo2max") float vo2max,
            @Field("tingkat_kebugaran") String tingkat_kebugaran,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("beep/getData")
    Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> beepdata(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("beep/getDataPelatih")
    Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> beepDataPelatih(
            @Field("bulan") int bulan
    );

    @FormUrlEncoded
    @POST("beep/deleteAllData")
    Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> beepDelete(
            @Field("bulan") int bulan,
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("login/changePassword")
    Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> changePassword(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("atlit/updatedata")
    Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> updateDataAtlit(
            @Field("refid") int refid,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("tinggi_badan") float tinggi_badan,
            @Field("berat_badan") float berat_badan,
            @Field("jenis_kelamin") String jenis_kelamin
    );

    @FormUrlEncoded
    @POST("balke/getListData")
    Call<List<BalkeGetPojo>> getListData(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("Cooper/getListData")
    Call<List<CooperGetPojo>> getListDataCooper(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("beep/getListData")
    Call<List<BeepGetPojo>> getListDataBeep(
            @Field("cabor") String cabor
    );

    @FormUrlEncoded
    @POST("balke/setSolusi")
    Call<DataProcessPojo> setSolusiBalke(@Field("id") int id, @Field("solusi") String solusi);

    @FormUrlEncoded
    @POST("beep/setSolusi")
    Call<DataProcessPojo> setSolusiBeep(@Field("id") int id, @Field("solusi") String solusi);

    @FormUrlEncoded
    @POST("Cooper/setSolusi")
    Call<DataProcessPojo> setSolusiCooper(@Field("id") int id, @Field("solusi") String solusi);
}
