package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.grafikastudio.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfileAct extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    ImageView btn_back,photo_user;
    EditText nama_lengkap,password,no_wa,email,akun_ig,alamat_rumah;
    Button btn_simpan,btn_hapus_foto,btn_datepicker,btn_gender;
    Navigation nav;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference,reference2;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getUsernameLocal();

        photo_user = findViewById(R.id.photo_user);
        btn_hapus_foto = findViewById(R.id.btn_hapus_foto);


        btn_simpan = findViewById(R.id.btn_simpan);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        password = findViewById(R.id.password);
        no_wa = findViewById(R.id.no_wa);
        email = findViewById(R.id.email);
        akun_ig = findViewById(R.id.akun_ig);
        alamat_rumah = findViewById(R.id.alamat_rumah);
        btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_gender = findViewById(R.id.btn_gender);

        btn_back = findViewById(R.id.btn_back);

        //mengambil data yg saat user itu coba login
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                password.setText(datasnapshot.child("password").getValue().toString());
                no_wa.setText(datasnapshot.child("no_wa").getValue().toString());
                email.setText(datasnapshot.child("email").getValue().toString());
                alamat_rumah.setText(datasnapshot.child("alamat").getValue().toString());
                akun_ig.setText(datasnapshot.child("akun_ig").getValue().toString());
                btn_gender.setText(datasnapshot.child("gender").getValue().toString());
                btn_datepicker.setText(datasnapshot.child("tgl_lahir").getValue().toString());
                String url_photo =datasnapshot.child("url_photo_profile").getValue().toString();
                if(url_photo.matches("")){

                }else{
                    Picasso.get().load(datasnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
                }
                //Picasso.get().load(datasnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_hapus_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("url_photo_profile");
                reference2.setValue("");
                Toast.makeText(getApplicationContext(), "Foto Terhapus", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(EditProfileAct.this, EditProfileAct.class);
                startActivity(refresh);
            }
        });

        btn_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), btn_gender);
                dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_gender, dropDownMenu.getMenu());
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

        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
                btn_datepicker.setBackgroundResource(R.drawable.bg_input_form);
            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state mnjdi loading
                btn_simpan.setEnabled(false);
                btn_simpan.setText("Loading ...");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("no_wa").setValue(no_wa.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                        dataSnapshot.getRef().child("alamat").setValue(alamat_rumah.getText().toString());
                        dataSnapshot.getRef().child("akun_ig").setValue(akun_ig.getText().toString());
                        dataSnapshot.getRef().child("gender").setValue(btn_gender.getText().toString());
                        dataSnapshot.getRef().child("tgl_lahir").setValue(btn_datepicker.getText().toString());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //validasi untuk file (apakah ada?)
                if(photo_location != null){
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));

                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            //tampilin toast
                                            Toast.makeText(getApplicationContext(), "Berhasil !", Toast.LENGTH_SHORT).show();
                                            btn_simpan.setEnabled(true);
                                            btn_simpan.setText("Simpan");
                                            finish();


                                        }
                                    });

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    btn_simpan.setEnabled(true);
                    btn_simpan.setText("Simpan");
                    finish();
                }
            }
        });

        photo_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){

            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(photo_user);

        }

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
