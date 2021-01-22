package com.luciusblack.winchat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.luciusblack.winchat.R;
import com.luciusblack.winchat.providers.AuthProvider;


public class CodeVerificationActivity extends AppCompatActivity {

    Button mButtonCodeVerfication;
    EditText mEditTextCode;
    TextView mTextViewSMS;
    ProgressBar mProgressBar;

    String mExtraPhone;
    String mVerificationId;

    AuthProvider mAuthProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);

        mButtonCodeVerfication = findViewById(R.id.btnCodeVerification);
        mEditTextCode = findViewById(R.id.editTextCodeVerification);
        mTextViewSMS = findViewById(R.id.textViewSMS);
        mProgressBar = findViewById(R.id.progressBar);

        mAuthProvider = new AuthProvider();

        mExtraPhone = getIntent().getStringExtra("phone");

        mAuthProvider.sendCodeVerification(mExtraPhone, mCallbacks);

        mButtonCodeVerfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = mEditTextCode.getText().toString();
                if (!code.equals("") && code.length() >= 6) {

                }
                else {
                    Toast.makeText(CodeVerificationActivity.this, "Ingresa el codigo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            mProgressBar.setVisibility(View.GONE);
            mTextViewSMS.setVisibility(View.GONE);

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                mEditTextCode.setText(code);

            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            mProgressBar.setVisibility(View.GONE);
            mTextViewSMS.setVisibility(View.GONE);

            Toast.makeText(CodeVerificationActivity.this, "Se produjo un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            Toast.makeText(CodeVerificationActivity.this, "El codigo se envio", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
        }
    };





}