package com.example.mypc.donasiid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypc.donasiid._api.ApiService;
import com.example.mypc.donasiid._api.ApiUrl;
import com.example.mypc.donasiid._modul.ItemUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_password);
        Button btnlogin = (Button) findViewById(R.id.Btn_Login);

        btnlogin.setOnClickListener(this);
    }



    public void pindah(View view) {
        Intent intent =  new Intent(LoginActivity.this,RegistrasiActivity.class);
        startActivity(intent);
    }




    public void sendTokenToServer(String email, String pass) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_HEAD)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemUser> call = service.login(email, pass);

        call.enqueue(new Callback<ItemUser>() {
            @Override
            public void onResponse(Call<ItemUser> call, Response<ItemUser> response) {
                if (response.isSuccessful()){


                    Boolean status = Boolean.valueOf(response.body().getStatus());

                    if (status){

                        String email_user = response.body().getEmail();
                        String password = response.body().getPassword();
                        
                        String sukses = "sukses" + email_user;
                        Toast.makeText(LoginActivity.this,sukses,Toast.LENGTH_SHORT).show();
                        
                        finish();
                        Intent gomain = new Intent(LoginActivity.this , MainActivity.class);
                        gomain.putExtra("Email" , email_user);
                        gomain.putExtra("password" , password);
                        startActivity(gomain);
                    }else{
                        String message = response.body().getMessage();
                        
                        String erorr = "gagal" + message;
                        Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String error = "Eroor amnbil data dari server" ;
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemUser> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        String username = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());

        if (username.equals("")){
            etEmail.setError("Email Anda Kosong");
        }else if (password.equals("")){
            etPassword.setError("Password Anda kosong");
        }else {
            sendTokenToServer(username,password);
        }
    }
}
