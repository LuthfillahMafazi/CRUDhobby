package co.luthfillahmafazi.tugsacrudapi.UI.Main.Register;

import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSucces(String message);
    }
    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
