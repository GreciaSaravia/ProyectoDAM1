package com.example.proyectodami.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodami.R;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    Button mbtnSendCode;
    EditText medtCel;
    CountryCodePicker mccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnSendCode = findViewById(R.id.btnSendCode);
        medtCel = findViewById(R.id.edtTextCel);
        mccp = findViewById(R.id.ccp);


        mbtnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToCodeVerificationActivity();
                getData();
            }
        });

    }


    private void getData(){

        String code = mccp.getSelectedCountryCodeWithPlus();
        String cel = medtCel.getText().toString();

        if(cel.equals("")){
            Toast.makeText(this, "Ingresar número de celular", Toast.LENGTH_SHORT).show();
        }
        else {
            goToCodeVerificationActivity(code + cel);
        }

    }

    private void goToCodeVerificationActivity(String cel){
        Intent intent = new Intent(MainActivity.this, CodeVerificationActivity.class);
        intent.putExtra("cel", cel);
        startActivity(intent);

    }
}
