package co.luthfillahmafazi.tugsacrudapi.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;
import co.luthfillahmafazi.tugsacrudapi.UI.Main.Login.LoginActivity;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;

        pref = context.getSharedPreferences(Constant.pref_name,0);

        editor = pref.edit();
    }
    public void createSession(LoginData loginData) {
        // Memasukan data user yang sidah login ke dalam shared preference
        editor.putBoolean(Constant.KEY_IS_LOGIN, true);
        // Memasukan data2 user yg terdiri dari user, id dll
        editor.putString(Constant.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_LEVEL, loginData.getLevel());

        // Mengeksekusi penyimpanan
        editor.commit();
    }
    public boolean isLogin(){
        // Mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constant.KEY_IS_LOGIN, false);
    }
    public void logout(){
        // Memanggil method clear untuk menhapus data sharedrederence
        editor.clear();
        // Mengeksekusi perintah clear
        editor.commit();
        // Membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
