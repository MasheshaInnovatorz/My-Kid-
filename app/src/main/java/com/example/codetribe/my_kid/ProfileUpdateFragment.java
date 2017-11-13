/*
package com.example.codetribe.my_kid;
/*
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class ProfileUpdateFragment extends Fragment {
    TextView signUpButton;
    private TextView editprofile;

    private EditText inputName,
            inputSurname,
            inputIdnumber,
            inputAddress,
            inputCity,
            inputCellphoneNumber;


    //validdation
    private TextInputLayout inputLayoutName,
            inputLayoutSurname,
            inputLayoutAddress,
            inputLayoutCity,
            inputLayoutIdNumber,
            inputLayoutNumber;

    //private Button signUpButton;
    RadioGroup radGender;

    //Firebase
    private DatabaseReference databaseReference;

    String keyUser;
    String userNameString, inputSurnameString, inputCityString, inputAddressString, inputIdnumberString, userContactString, genderString;
    private RadioButton rdGenders;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.update_profile_fragment, container, false);
        Intent intent = getActivity().getIntent();
        keyUser = intent.getStringExtra("User_KEY");


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        signUpButton = (TextView) rootView.findViewById(R.id.btnRegister);

        //Edit lText
        inputName = (EditText) rootView.findViewById(R.id.reg_fullname);
        inputSurname = (EditText) rootView.findViewById(R.id.reg_Surname);
        inputAddress = (EditText) rootView.findViewById(R.id.reg_address);
        inputCity = (EditText) rootView.findViewById(R.id.reg_city);
        inputCellphoneNumber = (EditText) rootView.findViewById(R.id.reg_phone);
        radGender = (RadioGroup) rootView.findViewById(gender);
        inputIdnumber = (EditText) rootView.findViewById(R.id.reg_idnumber);

        //TextLayout
        inputLayoutName = (TextInputLayout) rootView.findViewById(R.id.input_reg_fullname);
        inputLayoutSurname = (TextInputLayout) rootView.findViewById(R.id.input_reg_Surname);
        inputLayoutAddress = (TextInputLayout) rootView.findViewById(R.id.input_reg_address);
        inputLayoutCity = (TextInputLayout) rootView.findViewById(R.id.input_reg_city);
        inputLayoutIdNumber = (TextInputLayout) rootView.findViewById(R.id.input_reg_idNumber);
        inputLayoutNumber = (TextInputLayout) rootView.findViewById(R.id.input_reg_phoneNo);

        //watcher
        inputName.addTextChangedListener(new MyInputWatcher(inputName));
        inputSurname.addTextChangedListener(new MyInputWatcher(inputSurname));
        inputAddress.addTextChangedListener(new MyInputWatcher(inputAddress));
        inputCity.addTextChangedListener(new MyInputWatcher(inputCity));
        inputIdnumber.addTextChangedListener(new MyInputWatcher(inputIdnumber));
        inputCellphoneNumber.addTextChangedListener(new MyInputWatcher(inputCellphoneNumber));

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //submitForm();

                saveProfile();


            }
        });

        return rootView;
    }

    public void saveProfile() {

        submitForm();

        userNameString = inputName.getText().toString().trim();
        userContactString = inputCellphoneNumber.getText().toString().trim();
        inputSurnameString = inputSurname.getText().toString().trim();
        inputIdnumberString = inputIdnumber.getText().toString().trim();
        inputAddressString = inputAddress.getText().toString().trim();
        inputCityString = inputCity.getText().toString().trim();
        int selectedId = radGender.getCheckedRadioButtonId();
        //rdGenders =(RadioButton) rootView.findViewById(selectedId);
        genderString = rdGenders.getText().toString();


        //databaseKids.child(id).setValue(kids);

        if (!TextUtils.isEmpty(inputIdnumberString)) {


            String isVerified = "verified";


            UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, inputCityString, userContactString, genderString, isVerified);

            databaseReference.child("userName").setValue(profile.getUserName());
            databaseReference.child("userSurname").setValue(profile.getUserSurname());
            databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
            databaseReference.child("userContact").setValue(profile.getUserContact());
            databaseReference.child("userAddress").setValue(profile.getUserAddress());
            databaseReference.child("userCity").setValue(profile.getUserCity());
            databaseReference.child("userGender").setValue(profile.getUserGender());
            databaseReference.child("isVerified").setValue(profile.getIsVerified());


            //databaseReference.setValue(ProfileUpdate);

            Toast.makeText(getActivity().getApplicationContext(), "User ProfileUpdate Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity().getApplicationContext(), ParentTabbedActivity.class));

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "User Failed to add ProfileUpdate", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitForm() {
        if (!validateFullName()) {
            return;
        }
        if (!validateSurname()) {
            return;
        }
        if (!validateIdNumber()) {
            return;
        }


        Toast.makeText(getActivity().getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFullName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            if (inputName.getText().toString().length() <= 6) {

                inputLayoutName.setError("Your name is too short atleast >6 letters will do");
                return false;
            } else {
                inputLayoutName.setErrorEnabled(false);
            }
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateSurname() {
        if (inputSurname.getText().toString().trim().isEmpty()) {
            inputLayoutSurname.setError(getString(R.string.err_msg_surname));
            requestFocus(inputSurname);
            return false;
        } else {
            inputLayoutSurname.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateIdNumber() {
        if (inputIdnumber.getText().toString().trim().isEmpty()) {
            if (inputIdnumber.getText().toString().length() < 13 || inputIdnumber.getText().toString().length() > 13) {
                inputLayoutIdNumber.setError("Identity Number Should be 13 digits");
            }
            inputLayoutIdNumber.setError(getString(R.string.err_msg_idnumber));
            requestFocus(inputIdnumber);
            return false;
        } else {
            inputLayoutIdNumber.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyInputWatcher implements TextWatcher {
        private View view;

        private MyInputWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_reg_fullname:
                    validateFullName();
                    break;
                case R.id.input_reg_Surname:
                    validateSurname();
                    break;

                //    case R.id.input_reg_idParents:
                // validateIdNumber();
                // break;

                //case R.id.input_reg_idParents:
                // validateIdNumber();
                //  break;

               /* case R.id.input_reg_email:
                    validateEmail();
                    break;*/
               /* case R.id.input_reg_password:
                    validatePassword();
                    break;

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
*/