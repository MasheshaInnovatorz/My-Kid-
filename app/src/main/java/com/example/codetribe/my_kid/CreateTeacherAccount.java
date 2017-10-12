package com.example.codetribe.my_kid;
        import android.content.Intent;
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java
        import android.support.annotation.NonNull;
        import android.support.design.widget.TextInputLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.Iterator;

public class CreateTeacherAccount extends AppCompatActivity {
=======
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Create_Teacher_Account extends AppCompatActivity {
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java

    TextInputLayout inputLayoutName, inputLayoutsurname, inputLayoutcontact, inputLayoutclassroom, inputLayoutidnumber, inputLayoutemail, inputLayoutpassword;
    String userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, useremailString, userpasswordString;
    private EditText name, surname, contact, classroom, idnumber, useremail, userpassword;
    RadioGroup gender;
    String keyTeacher;
    TextView createteacher;
    String role = "teacher";
    private FirebaseAuth auth;


    //Firebase
    private DatabaseReference teacherReference, mDatabaseRef, mCrecheRef;
    private RadioButton gnrteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__teacher__account);


        inputLayoutName = (TextInputLayout) findViewById(R.id.inputteacherfullname);
        inputLayoutsurname = (TextInputLayout) findViewById(R.id.inputteacherSurname);
        inputLayoutcontact = (TextInputLayout) findViewById(R.id.inputteacherconatct);
        inputLayoutclassroom = (TextInputLayout) findViewById(R.id.inputteacherclass);
        inputLayoutidnumber = (TextInputLayout) findViewById(R.id.inputteacheridnumber);
        inputLayoutemail = (TextInputLayout) findViewById(R.id.inputteacheremail);
        inputLayoutpassword = (TextInputLayout) findViewById(R.id.inputteacherPassword);


        name = (EditText) findViewById(R.id.teachername);
        surname = (EditText) findViewById(R.id.teachersurname);
        contact = (EditText) findViewById(R.id.teachercontact);
        classroom = (EditText) findViewById(R.id.teacherclass);
        idnumber = (EditText) findViewById(R.id.teacherid);
        useremail = (EditText) findViewById(R.id.teacheremail);
        userpassword = (EditText) findViewById(R.id.teacherpassword);
        gender = (RadioGroup) findViewById(R.id.teachergenders);
        createteacher = (TextView) findViewById(R.id.Create_Teacher_Account);

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java
=======

        //database
        Intent intent = getIntent();
        keyTeacher = intent.getStringExtra("User_KEY");


        // useremail = (EditText) findViewById(R.id.teacheremail);
        //  userpassword = (EditText) findViewById(R.id.teacherpassword);
        //createteacher = (TextView) findViewById(R.id.Create_Teacher_Account);
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java

        keyTeacher=auth.getCurrentUser().getUid();
        teacherReference = FirebaseDatabase.getInstance().getReference().child("Creche").child("Teacher").child(keyTeacher);
        mCrecheRef =  FirebaseDatabase.getInstance().getReference().child("Creche");

=======
        keyTeacher = auth.getCurrentUser().getUid();
        teacherReference = FirebaseDatabase.getInstance().getReference().child("Users");
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java


        createteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //adminId
                String AdminId = auth.getCurrentUser().getUid();
                final String adminEmail = auth.getCurrentUser().getEmail();


                teacherReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String email = useremail.getText().toString().trim();
                        final String password = userpassword.getText().toString().trim();
                        userNameString = name.getText().toString().trim();
                        userSurnameString = surname.getText().toString().trim();
                        usercontactString = contact.getText().toString().trim();
                        userclassroomString = classroom.getText().toString().trim();
                        useridnumberString = idnumber.getText().toString().trim();

                        // useremailString = useremail.getText().toString().trim();
                        // userpasswordString = userpassword.getText().toString().trim();
                        //=========================================================
                        int selectedId = gender.getCheckedRadioButtonId();
                        gnrteacher = (RadioButton) findViewById(selectedId);
                        usergenderString = gnrteacher.getText().toString().trim();

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (password.length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //        progressBar.setVisibility(View.VISIBLE);
                        //create user
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Create_Teacher_Account.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        String user_id = task.getResult().getUser().getUid();

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java
                Toast.makeText(this, adminOrg, Toast.LENGTH_SHORT).show();
            if(orgname != null){

                String email = useremail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                userNameString = name.getText().toString().trim();
                userSurnameString = surname.getText().toString().trim();
                usercontactString = contact.getText().toString().trim();
                userclassroomString = classroom.getText().toString().trim();
                useridnumberString = idnumber.getText().toString().trim();

                // useremailString = useremail.getText().toString().trim();
                // userpasswordString = userpassword.getText().toString().trim();
                //=========================================================
                int selectedId = gender.getCheckedRadioButtonId();
                gnrteacher = (RadioButton) findViewById(selectedId);
                usergenderString = gnrteacher.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
=======
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java


                                        //firebase teacher database table
                                        DatabaseReference mChildDatabase = mDatabaseRef.child("Users").child(user_id);

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java
                //        progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateTeacherAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
=======
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java

                                        //Storing Information
                                        Teacher_class_acc teacher = new Teacher_class_acc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, keyTeacher);

                                        mChildDatabase.child("userName").setValue(teacher.getTeacherName());
                                        mChildDatabase.child("userSurname").setValue(teacher.getTeacherSurname());
                                        mChildDatabase.child("userContact").setValue(teacher.getTeacherContact());
                                        mChildDatabase.child("teacherClassroom").setValue(teacher.getTeacherClassroom());
                                        mChildDatabase.child("userIdNumber").setValue(teacher.getTeacherIdnumber());
                                        mChildDatabase.child("userGender").setValue(teacher.getTeacherGender());
                                        mChildDatabase.child("role").setValue(role);
                                        mChildDatabase.child("isVerified").setValue("verified");
                                        mChildDatabase.child("userKey").setValue(task.getResult().getUser().getUid().toString().trim());
                                        //login
                                        mChildDatabase.child("emailUser").setValue(task.getResult().getUser().getEmail().toString().trim());
                                        mChildDatabase.child("passWordUser").setValue(password);

                                        Toast.makeText(Create_Teacher_Account.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java
                                //firebase teacher database table
                                DatabaseReference mChildDatabase = mDatabaseRef.child("teacher");

                                //Storing Information
                                TeacherClassAcc teacher = new TeacherClassAcc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, keyTeacher);

                                teacherReference.child("teacherName").setValue(teacher.getTeacherName());
                                teacherReference.child("teacherSurname").setValue(teacher.getTeacherSurname());
                                teacherReference.child("teacherContact").setValue(teacher.getTeacherContact());
                                teacherReference.child("teacherClassroom").setValue(teacher.getTeacherClassroom());
                                teacherReference.child("teacherIdnumber").setValue(teacher.getTeacherIdnumber());
                                teacherReference.child("teacherGender").setValue(teacher.getTeacherGender());
                                teacherReference.child("role").setValue(role);
                                teacherReference.child("isVerified").setValue("verified");



                                Toast.makeText(CreateTeacherAccount.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateTeacherAccount.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(CreateTeacherAccount.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }


=======
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(Create_Teacher_Account.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            startActivity(new Intent(Create_Teacher_Account.this, LoginActivity.class));
                                            finish();
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java

                    }
                });


            }
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateTeacherAccount.java



        }

    }
}






























































           /* while(iterator.hasNext()) {
                DataSnapshot dataUser = (DataSnapshot) iterator.next();

                if (dataUser.child("parentid").getValue().toString().equals(userId)) {

                    Toast.makeText(this, dataUser.child("parentid").getValue().toString(), Toast.LENGTH_SHORT).show();


                }
            }*/
=======
        });
    }

}

>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171:app/src/main/java/com/example/codetribe/my_kid/Create_Teacher_Account.java




//   String user_id = task.getResult().getUser().getUid();
              //  DatabaseReference mChildDatabase = mDatabaseRef.child("teacher");

                                // String key_user = mChildDatabase.getKey();

                            //    TeacherClassAcc teacher = new TeacherClassAcc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, keyTeacher);

                              /* teacherReference.child("teacherName").setValue(teacher.getTeacherName());
                               teacherReference.child("teacherSurname").setValue(teacher.getTeacherSurname());
                                teacherReference.child("teacherContact").setValue(teacher.getTeacherContact());
                                       teacherReference.child("teacherClassroom").setValue(teacher.getTeacherClassroom());
                                teacherReference.child("teacherIdnumber").setValue(teacher.getTeacherIdnumber());
                                teacherReference.child("teacherGender").setValue(teacher.getTeacherGender());
                               teacherReference.child("role").setValue(role);
                                teacherReference.child("isVerified").setValue("verified");
*/
                              //  mChildDatabase.child("Admin").child(user_id).child("adminName").setValue(adminNameOrg);
                             //   mChildDatabase.child("Admin").child(user_id).child("adminSurname").setValue(adminSurnameOrg);
                             //   mChildDatabase.child("Admin").child(user_id).child("adminIdNumber").setValue(adminIdNoOrg);



        /*
            private void saveTeacherProfile() {

                userNameString = name.getText().toString().trim();
                userSurnameString = surname.getText().toString().trim();
                usercontactString = contact.getText().toString().trim();
                userclassroomString = classroom.getText().toString().trim();
                useridnumberString = idnumber.getText().toString().trim();

                useremailString = email.getText().toString().trim();
                userpasswordString = password.getText().toString().trim();
                //=========================================================
                int selectedId = gender.getCheckedRadioButtonId();
                gnrteacher = (RadioButton) findViewById(selectedId);
                usergenderString = gnrteacher.getText().toString();


                TeacherClassAcc teacher = new TeacherClassAcc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, keyTeacher);
                databaseReference.child("teacherName").setValue(teacher.getTeacherName());
                databaseReference.child("teacherSurname").setValue(teacher.getTeacherSurname());
                databaseReference.child("teacherContact").setValue(teacher.getTeacherContact());
                databaseReference.child("teacherClassroom").setValue(teacher.getTeacherClassroom());
                databaseReference.child("teacherIdnumber").setValue(teacher.getTeacherIdnumber());
                databaseReference.child("teacherGender").setValue(teacher.getTeacherGender());
                databaseReference.child("role").setValue(role);
                databaseReference.child("isVerified").setValue("verified");


                //databaseReference.setValue(ProfileUpdate);
                Toast.makeText(getApplicationContext(), "Teacher Profile Create Added", Toast.LENGTH_SHORT).show();
                //Toast.makeText(CreateTeacherAccount.this, "Teacher Profile Create Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateTeacherAccount.this, AdminActivity.class));



            }
*/

