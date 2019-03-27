package co.luthfillahmafazi.tugsacrudapi.UI.Main;

import android.content.Context;

import co.luthfillahmafazi.tugsacrudapi.Utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        SessionManager mSesssionManager = new SessionManager(context);
        mSesssionManager.logout();
    }
}
