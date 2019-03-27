package co.luthfillahmafazi.tugsacrudapi.UI.Main.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import co.luthfillahmafazi.tugsacrudapi.Data.Remote.ApiClient;
import co.luthfillahmafazi.tugsacrudapi.Data.Remote.ApiInterface;
import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;
import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginResponse;
import co.luthfillahmafazi.tugsacrudapi.Utils.Constant;
import co.luthfillahmafazi.tugsacrudapi.Utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {
        view.showProgress();

        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(), loginData.getAlamat(), loginData.getJenkel(), loginData.getNo_telp());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult() == 1) {
                        pref = context.getSharedPreferences(Constant.pref_name, 0);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());

                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    } else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());

        editor.apply();
        view.showSuccessUpdateUser("Update Sukses");

    }

    @Override
    public void getData(Context context) {
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        LoginData loginData = new LoginData();

        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));

        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logout();
    }
}
