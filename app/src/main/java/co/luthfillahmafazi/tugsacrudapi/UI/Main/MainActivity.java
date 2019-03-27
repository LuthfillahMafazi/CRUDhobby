package co.luthfillahmafazi.tugsacrudapi.UI.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import co.luthfillahmafazi.tugsacrudapi.R;
import co.luthfillahmafazi.tugsacrudapi.UI.Main.Favorite.FavoriteFragment;
import co.luthfillahmafazi.tugsacrudapi.UI.Main.Hobby.HobbyFragment;
import co.luthfillahmafazi.tugsacrudapi.UI.Main.Profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView mTextMessage;
    private MainPresenter mainPresenter = new MainPresenter();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_hobby:
                    HobbyFragment hobbyFragment = new HobbyFragment();
                    loadFragment(hobbyFragment);
                    return true;
                case R.id.navigation_favorite:
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    loadFragment(favoriteFragment);
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    loadFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HobbyFragment hobbyFragment = new HobbyFragment();
        loadFragment(hobbyFragment);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                // Melakukan perintah logout ke presenter
                mainPresenter.logoutSession(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    // Membuat function load fragment
    private void loadFragment(Fragment fragment) {
        // Menampilkan fragment menggunakan fragmeny transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
