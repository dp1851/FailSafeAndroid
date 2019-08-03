package com.yogan.failsafe;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GoogleSignIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_Section;
    private Button SignOut, NFC_Scan;
    private SignInButton SignIn;
    private TextView Name, UserID;
    private GoogleApiClient googleApiClient;
    private String user_Name, user_ID;
    NfcAdapter nfcAdapter;
    private static final int REQ_CODE = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        Prof_Section = (LinearLayout)findViewById(R.id.prof_sect);
        SignOut = (Button)findViewById(R.id.bn_logout);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        Name = (TextView)findViewById(R.id.name);
        UserID = (TextView) findViewById(R.id.UserID);
        NFC_Scan = (Button)findViewById(R.id.NFC_Scan);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        NFC_Scan.setOnClickListener(this);
        Prof_Section.setVisibility(View.GONE);
        Prof_Section.setBackgroundResource(0);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this). enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_login:
                signIn();
                break;

            case R.id.bn_logout:
                signOut();
                break;

            case R.id.NFC_Scan:
                nfcScan();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void nfcScan(){
        user_ID = UserID.getText().toString();
        Intent goToScan = new Intent(this, MainActivity.class);
        goToScan.putExtra("UserName", user_Name);
        goToScan.putExtra("UserID", user_ID);
        startActivity(goToScan);
    }

    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            Name.setText(name);
            user_Name = name;
            switch (name){
                case "Yoganand Pathak":
                    user_ID = "1";
                    break;
                case "Dev Patel":
                    user_ID = "2";
                    break;
                case "Vineeth Vajipey":
                    user_ID = "3";
                    break;
                case "Parth Savla":
                    user_ID = "4";
                    break;
                default:
                    user_ID = "Not a valid user";
                    break;
            }
            UserID.setText(user_ID);
            updateUI(true);

        }

        else{
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin){
        if (isLogin){
            Prof_Section.setVisibility(View.VISIBLE);
            Prof_Section.setBackgroundResource(R.drawable.id_card_template);
            SignIn.setVisibility(View.GONE);
        }
        else{
            Prof_Section.setVisibility(View.GONE);
            Prof_Section.setBackgroundResource(0);
            SignIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(result);
            }
        }

    }
}
