package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpAct extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    TextView btn_masuk,
            title_username,error_username,
            title_password,error_password,
            title_nama_lengkap,error_nama_lengkap,
            title_no_wa,error_no_wa,
            title_email,error_email,
            title_alamat,error_alamat,
            title_tgl_lahir,error_tgl_lahir,
            title_gender,error_gender;
    ImageView btn_back;
    Button btn_selanjutnya,btn_datepicker,btn_gender;
    EditText username, password, nama_lengkap, no_wa, email,akun_ig,alamat_rumah;

    DatabaseReference reference,reference_username;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_datepicker = findViewById(R.id.btn_datepicker);
        title_tgl_lahir = findViewById(R.id.title_tgl_lahir);
        error_tgl_lahir = findViewById(R.id.error_tgl_lahir);


        alamat_rumah = findViewById(R.id.alamat_rumah);
        title_alamat = findViewById(R.id.title_alamat);
        error_alamat = findViewById(R.id.error_alamat);

        akun_ig = findViewById(R.id.akun_ig);


        btn_gender = findViewById(R.id.btn_gender);
        title_gender = findViewById(R.id.title_gender);
        error_gender = findViewById(R.id.error_gender);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        title_username = findViewById(R.id.title_username);
        error_username = findViewById(R.id.error_username);

        title_password = findViewById(R.id.title_password);
        error_password = findViewById(R.id.error_password);

        title_nama_lengkap = findViewById(R.id.title_nama_lengkap);
        error_nama_lengkap = findViewById(R.id.error_nama_lengkap);

        title_no_wa = findViewById(R.id.title_no_wa);
        error_no_wa = findViewById(R.id.error_no_wa);

        title_email = findViewById(R.id.title_email);
        error_email = findViewById(R.id.error_email);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_wa = findViewById(R.id.no_wa);
        email = findViewById(R.id.email);

        btn_masuk = findViewById(R.id.btn_masukskrg);
        btn_back = findViewById(R.id.btn_back);
        btn_selanjutnya = findViewById(R.id.btn_selanjutnya);

        //set on focus to default
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    error_username.setText("");
                    title_username.setTextColor(Color.parseColor("#000000"));
                    username.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    error_password.setText("");
                    title_password.setTextColor(Color.parseColor("#000000"));
                    password.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        nama_lengkap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    error_nama_lengkap.setText("");
                    title_nama_lengkap.setTextColor(Color.parseColor("#000000"));
                    nama_lengkap.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        no_wa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    error_no_wa.setText("");
                    title_no_wa.setTextColor(Color.parseColor("#000000"));
                    no_wa.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    error_email.setText("");
                    title_email.setTextColor(Color.parseColor("#000000"));
                    email.setBackgroundResource(R.drawable.bg_input_form);
                }
            }
        });

        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
                error_tgl_lahir.setText("");
                title_tgl_lahir.setTextColor(Color.parseColor("#000000"));
                btn_datepicker.setBackgroundResource(R.drawable.bg_input_form);
            }
        });

        alamat_rumah.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    error_alamat.setText("");
                    title_alamat.setTextColor(Color.parseColor("#000000"));
                    alamat_rumah.setBackgroundResource(R.drawable.bg_input_form);

                }
            }
        });

        btn_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), btn_gender);
                dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_gender, dropDownMenu.getMenu());
                error_gender.setText("");
                title_gender.setTextColor(Color.parseColor("#000000"));
                btn_gender.setBackgroundResource(R.drawable.bg_input_form);
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        btn_gender.setText(item.getTitle().toString());
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomasuk =new Intent(SignUpAct.this, LoginAct.class);
                startActivity(gomasuk);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomasuk =new Intent(SignUpAct.this, LoginAct.class);
                startActivity(gomasuk);
            }
        });

        btn_selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //simpan data ke local storage (hp)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, username.getText().toString());
                editor.apply();

                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();
                final String xnama_lengkap = nama_lengkap.getText().toString();
                final String xno_wa = no_wa.getText().toString();
                final String xemail = email.getText().toString();
                final String xgender = btn_gender.getText().toString();
                final String xtgl_lahir = btn_datepicker.getText().toString();
                final String xalamat = alamat_rumah.getText().toString();



                //mengambil username pada firebase Database
                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       if(xusername.isEmpty()){
                           error_username.setText("Username wajib diisi");
                           title_username.setTextColor(Color.parseColor("#D25353"));
                           username.setBackgroundResource(R.drawable.bg_input_tiga);
                           getWindow().getDecorView().clearFocus();
                       }
                           if(dataSnapshot.exists()){
                            error_username.setText("Username sudah digunakan");
                            title_username.setTextColor(Color.parseColor("#D25353"));
                            username.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();

                        }
                        if (xpassword.isEmpty()) {
                            error_password.setText("Password wajib diisi");
                            title_password.setTextColor(Color.parseColor("#D25353"));
                            password.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }
                        if (xnama_lengkap.isEmpty()) {
                            error_nama_lengkap.setText("Nama lengkap wajib diisi");
                            title_nama_lengkap.setTextColor(Color.parseColor("#D25353"));
                            nama_lengkap.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }  if (xgender.isEmpty()) {
                            error_gender.setText("Gender wajib diisi");
                            title_gender.setTextColor(Color.parseColor("#D25353"));
                            btn_gender.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        } if (xtgl_lahir.isEmpty()) {
                            error_tgl_lahir.setText("Tanggal lahir wajib diisi");
                            title_tgl_lahir.setTextColor(Color.parseColor("#D25353"));
                            btn_datepicker.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }
                        if (xtgl_lahir.isEmpty()) {
                            error_tgl_lahir.setText("Tanggal lahir wajib diisi");
                            title_tgl_lahir.setTextColor(Color.parseColor("#D25353"));
                            btn_datepicker.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }
                        if (xno_wa.isEmpty()) {
                            error_no_wa.setText("Nomor whatsapp wajib diisi ");
                            title_no_wa.setTextColor(Color.parseColor("#D25353"));
                            no_wa.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }
                        if(xalamat.isEmpty()){
                            error_alamat.setText("Alamat rumah wajib diisi");
                            title_alamat.setTextColor(Color.parseColor("#D25353"));
                            alamat_rumah.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }
                        if (xemail.isEmpty()) {
                            error_email.setText("Email wajib diisi");
                            title_email.setTextColor(Color.parseColor("#D25353"));
                            email.setBackgroundResource(R.drawable.bg_input_tiga);
                            getWindow().getDecorView().clearFocus();
                        }else{
                        //simpan ke database
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot datasnapshot) {
                                datasnapshot.getRef().child("username").setValue(username.getText().toString());
                                datasnapshot.getRef().child("password").setValue(password.getText().toString());
                                datasnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                                datasnapshot.getRef().child("no_wa").setValue(no_wa.getText().toString());
                                datasnapshot.getRef().child("email").setValue(email.getText().toString());
                                datasnapshot.getRef().child("tgl_lahir").setValue(btn_datepicker.getText().toString());
                                datasnapshot.getRef().child("gender").setValue(btn_gender.getText().toString());
                                datasnapshot.getRef().child("alamat").setValue(alamat_rumah.getText().toString());
                                datasnapshot.getRef().child("akun_ig").setValue(akun_ig.getText().toString());
                                datasnapshot.getRef().child("user_poin").setValue(1000);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                        //pindah activity
                        Intent goupload = new Intent(SignUpAct.this, SignUpTwoAct.class);
                        startActivity(goupload);
                    }}

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        });
    }
    private void showDateDialog(){

        /* Calendar untuk mendapatkan tanggal sekarang*/
        Calendar newCalendar = Calendar.getInstance();

        /* Initiate DatePicker dialog*/
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /* Method ini dipanggil saat kita selesai memilih tanggal di DatePicker*/

                /* Set Calendar untuk menampung tanggal yang dipilih*/
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormatter.format(newDate.getTime());
//                String tess = dateFormatter.getDateInstance(newDate.LONG).format(newDate.getTime());

                /* Update TextView dengan tanggal yang kita pilih*/
                btn_datepicker.setText(date);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        // set maximum date to be selected as today
        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        /* Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }
}
