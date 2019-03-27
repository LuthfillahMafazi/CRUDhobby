package co.luthfillahmafazi.tugsacrudapi.Data.Remote;

import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp,
            @Field("level") String level);

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser") int iduser,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp);



}
