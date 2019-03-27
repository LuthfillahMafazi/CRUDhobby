package co.luthfillahmafazi.tugsacrudapi.UI.Main.Login;

import android.content.Context;

import co.luthfillahmafazi.tugsacrudapi.Data.Remote.ApiClient;
import co.luthfillahmafazi.tugsacrudapi.Data.Remote.ApiInterface;
import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;
import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginResponse;
import co.luthfillahmafazi.tugsacrudapi.Utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        if (username.isEmpty()){
            view.usernameError("Username tidak boleh kosong !");
            return;
        }

        if (password.isEmpty()){
            view.passwordError("Password tidak boleh kosong !");
            return;
        }
        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hidePorgress();
                // Mencek apkaah response body nya ada isinya atau tidak
                if (response.body() != null){
                    // mencek data jika berhasil maka resultnya 1
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            view.loginSucces(response.body().getMessage(), response.body().getData());
                        }else {
                            view.loginFailure("Data tidak ada");
                        }
                    }else {
                        view.loginFailure(response.message());
                    }
                }else {
                    view.loginFailure("Data tidak ada");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hidePorgress();
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        mSessionManager = new SessionManager(context);
        // Mensave data dengan mengguanakan merhod dari xlass Session Manager
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        mSessionManager = new SessionManager(context);
        // Kerena method isLogin merukana function return maka kita gunakan tipe datanya dan membuat variable
        // Mengambil data KEY_IS_LOGIN lalu memasukan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        // Mengecek apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            view.isLogin();
        }
    }
}
