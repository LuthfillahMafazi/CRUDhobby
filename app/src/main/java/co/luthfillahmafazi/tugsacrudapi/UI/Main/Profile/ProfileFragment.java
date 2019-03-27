package co.luthfillahmafazi.tugsacrudapi.UI.Main.Profile;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.luthfillahmafazi.tugsacrudapi.Model.Login.LoginData;
import co.luthfillahmafazi.tugsacrudapi.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    private ProgressDialog progressDialog;
    private ProfilePresenter profilePresenter = new ProfilePresenter(this);

    private String idUser, name, alamat, noTelp;
    private int gender;
    private Menu action;

    private String mGander;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        setUpSpinner();

        profilePresenter.getData(getContext());

        return view;
    }

    private void setUpSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinGender.setAdapter(genderSpinnerAdapter);

        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGander = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGander = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessUpdateUser(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        profilePresenter.getData(getContext());
    }

    @Override
    public void showDataUser(LoginData loginData) {
        readMode();

        idUser = loginData.getId_user();
        name = loginData.getNama_user();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telp();

        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }
        if (!TextUtils.isEmpty(idUser)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile " + name);

            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);

            switch (gender) {
                case GENDER_MALE:
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile ");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        profilePresenter.logoutSession(getContext());

        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;

        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editMode();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                if (!TextUtils.isEmpty(idUser)) {
                    if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtAlamat.getText().toString())
                            || TextUtils.isEmpty(edtNoTelp.getText().toString())) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complite the field !");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {
                        LoginData loginData = new LoginData();

                        loginData.setId_user(idUser);
                        loginData.setNama_user(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGander);

                        profilePresenter.updateDataUser(getContext(), loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                } else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }
                readMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);

        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);
    }
}
