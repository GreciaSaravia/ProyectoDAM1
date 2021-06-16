package com.example.proyectodami.providers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthProvider {

    private FirebaseAuth mAuth;

    public AuthProvider(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void sendCodeVerification(String cel, PhoneAuthProvider.OnVerificationStateChangedCallbacks callback){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                cel,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                callback
        );

    }

    public Task<AuthResult> signInPhone(String verificationId, String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        return mAuth.signInWithCredential(credential);
    }
}
