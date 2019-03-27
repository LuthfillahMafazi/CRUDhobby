package co.luthfillahmafazi.tugsacrudapi.UI.Main.Profile;

import android.content.Context;

import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;

public interface ProfileContract {
    interface View {
        void showProgress();

        void hideProgress();

        void showSuccessUpdateUser(String msg);

        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void updateDataUser(Context context, LoginData loginData);

        void getData(Context context);

        void logoutSession(Context context);

    }
}
