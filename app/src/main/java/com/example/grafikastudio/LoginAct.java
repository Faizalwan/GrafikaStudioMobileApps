package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAct extends AppCompatActivity {

    TextView btn_daftar;
    Button btn_masuk;
    EditText xusername,xpassword;
    TextView title_username,error_username,title_password,error_password;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_daftar =findViewById(R.id.btn_daftarskrg);
        btn_masuk =findViewById(R.id.btn_masuk);

        xusername =findViewById(R.id.xusername);
        xpassword =findViewById(R.id.xpassword);

        title_username =findViewById(R.id.title_username);
        error_username =findViewById(R.id.error_username);
        title_password =findViewById(R.id.title_password);
        error_password =findViewById(R.id.error_password);

        xusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    error_username.setText("");
                    title_username.setTextColor(Color.parseColor("#000000"));
                    xusername.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });
        xpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    error_password.setText("");
                    title_password.setTextColor(Color.parseColor("#000000"));
                    xpassword.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godaftar =new Intent(LoginAct.this, SignUpAct.class);
                startActivity(godaftar);
            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state menjadi loading
                btn_masuk.setEnabled(false);
                btn_masuk.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(username.isEmpty()){
                    error_username.setText("Username kosong");
                    title_username.setTextColor(Color.parseColor("#D25353"));
                    xusername.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();

                    //ubah state
                    btn_masuk.setEnabled(true);
                    btn_masuk.setText("Masuk");

                }
                    if(password.isEmpty()){
                        error_password.setText("Password kosong");
                        title_password.setTextColor(Color.parseColor("#D25353"));
                        xpassword.setBackgroundResource(R.drawable.bg_input_tiga);
                        getWindow().getDecorView().clearFocus();

                        //ubah state
                        btn_masuk.setEnabled(true);
                        btn_masuk.setText("Masuk");
                    }else{
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot datasnapshot) {
                                if(datasnapshot.exists()){

                                    // ambil data password dari Firebase
                                    String passwordFromFirebase = datasnapshot.child("password").getValue().toString();

                                    //validasi password dengan pw firebase
                                    if(password.equals(passwordFromFirebase)){

                                        //simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //pindah activity
                                        Intent gohome = new Intent(LoginAct.this, MainActivity.class);
                                        gohome.putExtra("fragment", "home");
                                        startActivity(gohome);
                                        finish();

                                    }else{
                                        error_password.setText("Password salah");
                                        title_password.setTextColor(Color.parseColor("#D25353"));
                                        xpassword.setBackgroundResource(R.drawable.bg_input_tiga);
                                        getWindow().getDecorView().clearFocus();
                                        //Toast.makeText(getApplicationContext(), "Password Salah !", Toast.LENGTH_SHORT).show();

                                        //ubah state
                                        btn_masuk.setEnabled(true);
                                        btn_masuk.setText("Masuk");
                                    }

                                }else{
                                    error_username.setText("Username salah");
                                    title_username.setTextColor(Color.parseColor("#D25353"));
                                    xusername.setBackgroundResource(R.drawable.bg_input_tiga);
                                    getWindow().getDecorView().clearFocus();

                                    //ubah state
                                    btn_masuk.setEnabled(true);
                                    btn_masuk.setText("Masuk");
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }
            }
        });


    }
}
