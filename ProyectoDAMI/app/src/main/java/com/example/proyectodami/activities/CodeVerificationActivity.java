package com.example.proyectodami.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodami.R;
import com.example.proyectodami.providers.AuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class CodeVerificationActivity extends AppCompatActivity {

    Button  mbtnCodeV;
    EditText mEditTextCode;

    String mExtraCel;
    String mVerificationId;

    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);

        mbtnCodeV = findViewById(R.id.btnCodeVerification);
        mEditTextCode = findViewById(R.id.editTextCodeVerification);

        mAuthProvider = new AuthProvider();
        mExtraCel = getIntent().getStringExtra("cel");

        mAuthProvider.sendCodeVerification(mExtraCel, mCallBacks);

        mbtnCodeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mEditTextCode.getText().toString();
                if(!code.equals("")&& code.length()>=6){
                    signIn(code);
                }else{
                    Toast.makeText(CodeVerificationActivity.this, "Ingresa el codigo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null){
                mEditTextCode.setText(code);
                signIn(code);
            }


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            Toast.makeText(CodeVerificationActivity.this, "El codigo se envió", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
        }
    };

    private void signIn(String code) {
        mAuthProvider.signInPhone(mVerificationId, code).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CodeVerificationActivity.this, "La autenticacion fue exitosa", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CodeVerificationActivity.this, "No se pudo autenticar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}