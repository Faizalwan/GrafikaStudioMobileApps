package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grafikastudio.ui.booking.BookingFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class FormBookingAct extends AppCompatActivity {

    ImageView btn_back;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    TextView tgl_booking, jam_booking;
    TextView error_namalengkap, error_wa, error_email, error_alamat, error_tanggal, error_jam;
    TextView title_namalengkap, title_no_wa, title_email, title_alamat, title_tgl, title_jam;
    EditText nama_paket, nama_lengkap, no_wa, email, akun_ig, alamat_rumah,extras;
    Button btn_datepicker, btn_jam, btn_simpan;
    Dialog myDialog;
    LinearLayout radio_akun;
    DatabaseReference reference,reference1;
    Integer kode_trans = new Random().nextInt(999999);


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String validasi = "valid";
    String kode,kode_paket,namapaket,url_paket,jmlorg,hargapaket;
    String date,jam;
    String kodetransaksi;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_booking);
        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_jam = findViewById(R.id.btn_jam);
        btn_simpan = findViewById(R.id.btn_simpan);
        nama_paket=findViewById(R.id.paket);
        radio_akun=findViewById(R.id.radio_akun);

        myDialog = new Dialog(this);
        Bundle bundle = getIntent().getExtras();
        kode = bundle.getString("kode");
        String aksi = bundle.getString("aksi");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tgl_booking = findViewById(R.id.tgl_booking);
        jam_booking = findViewById(R.id.btn_jam);
        btn_datepicker = findViewById(R.id.btn_datepicker);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_wa = findViewById(R.id.no_wa);
        email = findViewById(R.id.email);
        akun_ig =findViewById(R.id.akun_ig);
        alamat_rumah = findViewById(R.id.alamat_rumah);
        extras =findViewById(R.id.extras);


        title_namalengkap = findViewById(R.id.title_namalengkap);
        title_no_wa = findViewById(R.id.title_no_wa);
        title_email = findViewById(R.id.title_email);
        title_alamat = findViewById(R.id.title_alamat);
        title_tgl = findViewById(R.id.title_tgl);
        title_jam = findViewById(R.id.title_jam);

        error_namalengkap = findViewById(R.id.error_namalengkap);
        error_wa = findViewById(R.id.error_wa);
        error_email = findViewById(R.id.error_email);
        error_alamat = findViewById(R.id.error_alamat);
        error_tanggal = findViewById(R.id.error_tanggal);
        error_jam = findViewById(R.id.error_jam);
        btn_jam.setEnabled(false);


        if(aksi.equals("reschedule")){
            btn_jam.setEnabled(true);
            radio_akun.setVisibility(View.GONE);
            //mengambil data dari firebase berdasarkan intent
            reference = FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new).child("detailbooking").child(kode);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {
                    kodetransaksi = datasnapshot.child("kode_transaksi").getValue().toString();
                    nama_paket.setText(datasnapshot.child("nama_paket").getValue().toString());
                    nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                    no_wa.setText(datasnapshot.child("no_wa").getValue().toString());
                    email.setText(datasnapshot.child("email").getValue().toString());
                    akun_ig.setText(datasnapshot.child("akun_ig").getValue().toString());
                    alamat_rumah.setText(datasnapshot.child("alamat_rumah").getValue().toString());
                    extras.setText(datasnapshot.child("extras").getValue().toString());
                    btn_datepicker.setText(datasnapshot.child("tgl_booking").getValue().toString());
                    btn_jam.setText(datasnapshot.child("jam_booking").getValue().toString());
                    kode_paket = datasnapshot.child("kode_paket").getValue().toString();

                    reference = FirebaseDatabase.getInstance().getReference().child("paket").child(datasnapshot.child("kode_paket").getValue().toString());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            //menimpa data yang ada dengan data yg baru
                            namapaket = datasnapshot.child("nama_paket").getValue().toString();
                            hargapaket = datasnapshot.child("harga").getValue().toString();
                            jmlorg = datasnapshot.child("jml_orang").getValue().toString();
                            url_paket = datasnapshot.child("url_pic").getValue().toString();
                            nama_paket.setText(namapaket);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });


        }else {
            kodetransaksi = "BSF" + kode_trans;
            kode_paket = kode;


            nama_lengkap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        error_namalengkap.setText("");
                        title_namalengkap.setTextColor(Color.parseColor("#000000"));
                        nama_lengkap.setBackgroundResource(R.drawable.bg_input_form);
                    }
                }
            });

            no_wa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        error_wa.setText("");
                        title_no_wa.setTextColor(Color.parseColor("#000000"));
                        no_wa.setBackgroundResource(R.drawable.bg_input_form);
                    }
                }
            });

            email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        error_email.setText("");
                        title_email.setTextColor(Color.parseColor("#000000"));
                        email.setBackgroundResource(R.drawable.bg_input_form);
                    }
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


            reference = FirebaseDatabase.getInstance().getReference().child("paket").child(kode);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {
                    //menimpa data yang ada dengan data yg baru
                    namapaket = datasnapshot.child("nama_paket").getValue().toString();
                    hargapaket = datasnapshot.child("harga").getValue().toString();
                    jmlorg = datasnapshot.child("jml_orang").getValue().toString();
                    url_paket = datasnapshot.child("url_pic").getValue().toString();
                    nama_paket.setText(namapaket);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn_lbooking;
                String xnama_lengkap = nama_lengkap.getText().toString();
                final String xno_wa = no_wa.getText().toString();
                final String xemail = email.getText().toString();
                final String xalamat = alamat_rumah.getText().toString();
                final String xjam = btn_datepicker.getText().toString();
                final String xtgl = btn_jam.getText().toString();

                myDialog.setContentView(R.layout.activitiy_pop_up_booking);
                btn_lbooking = (Button) myDialog.findViewById(R.id.btn_lbooking);
                if(xnama_lengkap.isEmpty()){
                    error_namalengkap.setText("Nama lengkap wajib diisi");
                    title_namalengkap.setTextColor(Color.parseColor("#D25353"));
                    nama_lengkap.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }

                if(xno_wa.isEmpty()){
                    error_wa.setText("Nomor whatsapp wajib diisi");
                    title_no_wa.setTextColor(Color.parseColor("#D25353"));
                    no_wa.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }

                if(xemail.isEmpty()){
                    error_email.setText("Email wajib diisi");
                    title_email.setTextColor(Color.parseColor("#D25353"));
                    email.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }

                if(xalamat.isEmpty()){
                    error_alamat.setText("Alamat rumah wajib diisi");
                    title_alamat.setTextColor(Color.parseColor("#D25353"));
                    alamat_rumah.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }

                if(xjam.isEmpty()){
                    error_jam.setText("Jam wajib diisi");
                    title_jam.setTextColor(Color.parseColor("#D25353"));
                    btn_jam.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }

                if(xtgl.isEmpty()){
                    error_tanggal.setText("Tanggal wajib diisi");
                    title_tgl.setTextColor(Color.parseColor("#D25353"));
                    btn_datepicker.setBackgroundResource(R.drawable.bg_input_tiga);
                    getWindow().getDecorView().clearFocus();
                }else{


                    reference1 = FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            datasnapshot.getRef().child("username").setValue(username_key_new);
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                    reference = FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new).child("detailbooking").child(kodetransaksi);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            datasnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                            datasnapshot.getRef().child("kode_transaksi").setValue(kodetransaksi);
                            datasnapshot.getRef().child("no_wa").setValue(no_wa.getText().toString());
                            datasnapshot.getRef().child("email").setValue(email.getText().toString());
                            datasnapshot.getRef().child("akun_ig").setValue(akun_ig.getText().toString());
                            datasnapshot.getRef().child("alamat_rumah").setValue(alamat_rumah.getText().toString());
                            datasnapshot.getRef().child("kode_paket").setValue(kode_paket);
                            datasnapshot.getRef().child("extras").setValue(extras.getText().toString());
                            datasnapshot.getRef().child("tgl_booking").setValue(btn_datepicker.getText().toString());
                            datasnapshot.getRef().child("jam_booking").setValue(btn_jam.getText().toString());
                            datasnapshot.getRef().child("nama_paket").setValue(namapaket);
                            datasnapshot.getRef().child("harga").setValue(hargapaket);
                            datasnapshot.getRef().child("url_pic").setValue(url_paket);
                            datasnapshot.getRef().child("jml_orang").setValue(jmlorg);
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });



                    btn_lbooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent gohome = new Intent(FormBookingAct.this, DeskripsiBookingAct.class);
                            gohome.putExtra("kode_transaksi", kodetransaksi);
                            startActivity(gohome);
                            finish();
                        }
                    });
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.setCancelable(false);
                    myDialog.show();
                }

            }
        });


        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
                error_tanggal.setText("");
                title_tgl.setTextColor(Color.parseColor("#000000"));
                btn_datepicker.setBackgroundResource(R.drawable.bg_input_form);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), btn_jam);
                dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_jam, dropDownMenu.getMenu());

                error_jam.setText("");
                title_jam.setTextColor(Color.parseColor("#000000"));
                btn_jam.setBackgroundResource(R.drawable.bg_input_form);

                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        String tempjam=menuitem.getTitle().toString();
                        String temptgl = btn_datepicker.getText().toString();

                        validasi = "valid";
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        if(temptgl.equals(date)){
                            String currentTime = new SimpleDateFormat("HHmm", Locale.getDefault()).format(new Date());
                            int s = Integer.parseInt(tempjam.replace(":",""));
                            int s2 = Integer.parseInt(currentTime);
                            if(s < s2){
                                Toast.makeText(getApplicationContext(), "JAM TELAH TERLEWAT !!", Toast.LENGTH_LONG).show();
                                validasi = "invalid";
                            }
                        }
                        reference = FirebaseDatabase.getInstance().getReference().child("transaksi");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot datasnapshot) {
                                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                    String tgl = snapshot.child("tgl_transaksi").getValue().toString();
                                    String jam = snapshot.child("jam").getValue().toString();

                                    if(tgl.equals(temptgl)){
                                        if(jam.equals(tempjam)){
                                            Toast.makeText(getApplicationContext(), "JAM TELAH TERIISI !!", Toast.LENGTH_LONG).show();
                                            validasi = "invalid";
                                            break;
                                        }
                                    }

                                }
                                if(validasi.equals("valid")) {
                                    jam_booking.setText(menuitem.getTitle());
                                    jam = String.valueOf(menuitem.getTitle());
                                }


                            }
                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

//
                        //Toast.makeText(getApplicationContext(), "You have clicked " + menuitem.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });
    }

    //mengambil data dari intent


    public void PopUpBooking(View view){

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    private void showDateDialog(){

        /* Calendar untuk mendapatkan tanggal sekarang*/
        Calendar newCalendar = Calendar.getInstance();


        /* Initiate DatePicker dialog*/
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view,int year, int monthOfYear, int dayOfMonth) {

                /* Method ini dipanggil saat kita selesai memilih tanggal di DatePicker*/

                /* Set Calendar untuk menampung tanggal yang dipilih*/
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormatter.format(newDate.getTime());
//                String tess = dateFormatter.getDateInstance(newDate.LONG).format(newDate.getTime());


                /* Update TextView dengan tanggal yang kita pilih*/
                btn_datepicker.setText(date);
                btn_jam.setEnabled(true);

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // set minimum date to be selected as today
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());


        /* Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_ya:
                if (checked)
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot datasnapshot) {
                        nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                        no_wa.setText(datasnapshot.child("no_wa").getValue().toString());
                        email.setText(datasnapshot.child("email").getValue().toString());
                        akun_ig.setText(datasnapshot.child("akun_ig").getValue().toString());
                        alamat_rumah.setText(datasnapshot.child("alamat").getValue().toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
                break;
            case R.id.radio_tidak:
                if (checked)
                    nama_lengkap.setText("");
                    no_wa.setText("");
                    email.setText("");
                    akun_ig.setText("");
                    alamat_rumah.setText("");
                break;
        }
    }
}
