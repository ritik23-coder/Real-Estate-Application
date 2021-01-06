package com.SyracuseUniversity.jashwanthreddy.happynest; //change the package name to your project's package name

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity{

    EditText userNameET;
    EditText passwordET;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RC_SIGN_IN = 123;
    private static final String USER_CREATION_SUCCESS =  "Successfully created user";
    private static final String USER_CREATION_ERROR =  "User creation error";
    private static final String EMAIL_INVALID =  "email is invalid :";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Log.d("LoginActivity", "onAuthStateChanged: user is "+user);
                    Intent myIntent = new Intent(LoginActivity.this, NavigationDrawer.class);  //Replace MainActivity.class with your launcher class from previous assignments
                    LoginActivity.this.startActivity(myIntent);
                }
                else {
                }
            }
        };

        userNameET = (EditText)findViewById(R.id.edit_text_email);
        passwordET = (EditText)findViewById(R.id.edit_text_password);
        Button login = (Button) findViewById(R.id.loginbutton);
/*        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                               *//* .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                                        ))*//*
                                .build(),
                        RC_SIGN_IN);
               // startActivityForResult(AuthUI.getInstance().);
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = userNameET.getText().toString();
                final String password = passwordET.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), "Enter email address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                        LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful())
                                {
                                    Toast.makeText(LoginActivity.this, "User Authentication Failed",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    sharedPreferences = getSharedPreferences("HappyNestLoginDetails", Context.MODE_PRIVATE);
                                    //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    //getSharedPreferences("HappyNestLoginDetails", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("EmailAddress", email);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                });
            }
        });

        Button createButton = (Button) findViewById(R.id.createbutton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        Button differentLogin = (Button)findViewById(R.id.Differentlogin);
        differentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
//        Log.d("LoginActivity", "onActivityResult: Enter of  onActivityResult method");
//        Log.d("LoginActivity", "onActivityResult: resultcode is "+resultCode);
//        Log.d("LoginActivity", "onActivityResult: requestCode is "+requestCode);
//        Log.d("LoginActivity", "onActivityResult: data is "+data.getData());
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Log.d("LoginActivity", "onActivityResult: Loggedin succesfully");
                sharedPreferences = getSharedPreferences("HappyNestLoginDetails", Context.MODE_PRIVATE);


                Intent myIntent = new Intent(LoginActivity.this, NavigationDrawer.class); //Replace MainActivity.class with your launcher class from previous assignments
                LoginActivity.this.startActivity(myIntent);

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar("Sign in cancelled");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("No network connnection");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar("Error occured while signing in");
                    return;
                }
            }

            showSnackbar("Error occured while signing in");
        }
    }

    public void showSnackbar(String s){
        Snackbar snackbar = Snackbar.make(userNameET,s,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    // Validate email address for new accounts.
    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            userNameET.setError(EMAIL_INVALID + email);
            return false;
        }
        return true;
    }
//
//    // create a new user in Firebase
//    public void createUser() {
//        Log.d("LoginActivity", "createUser:userNameET is "+userNameET.getText().toString());
//        Log.d("LoginActivity", "createUser:passwordET is "+passwordET.getText().toString());
//        if(userNameET.getText() == null ||  !isEmailValid(userNameET.getText().toString())) {
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(userNameET.getText().toString(),passwordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (!task.isSuccessful()) {
//                       Exception exc = task.getException();
//
//                        Log.d("LoginActivity ", "onComplete: Exception is "+task.getResult().toString());
//
//                        if (exc.getMessage().contains("The email address is badly formatted")) {
//                            Snackbar snackbar = Snackbar.make(userNameET, "Invalid Email Address", Snackbar.LENGTH_SHORT);
//                        }
//                       else if (exc.getMessage().contains("The password is invalid or the user does not have a password")) {
//
//                          Snackbar snackbar = Snackbar.make(userNameET, "Invalid password entered", Snackbar.LENGTH_SHORT);
//                            snackbar.show();
//                       }
//                      else if (exc.getMessage().contains("Password should be at least 6 characters")) {
//                          Snackbar snackbar = Snackbar.make(userNameET, "Password should be at least 6 characters", Snackbar.LENGTH_SHORT);
//                          snackbar.show();
//                       }
//                    } else {
//                        Log.d("LoginActivity", "onComplete: Login is successfull");
//                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
//                        snackbar.show();
//
//                    }
//                }
//            });
//        }
//    }



    // create a new user in Firebase
    public void createUser() {
        Log.d("LoginActivity", "createUser:userNameET is "+userNameET.getText().toString());
        Log.d("LoginActivity", "createUser:passwordET is "+passwordET.getText().toString());
        if(userNameET.getText() == null ||  !isEmailValid(userNameET.getText().toString())) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(userNameET.getText().toString(),passwordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    try {
                        Log.d("LoginActivity", "onComplete: Exception occured "+task.getException().toString());
                        throw task.getException();
                    }
                    catch(FirebaseAuthWeakPasswordException e) {
                        Log.d("LoginActivity", "onComplete: FirebaseAuthWeakPasswordException "+e.getMessage());
                        Snackbar snackbar = Snackbar.make(userNameET, "Weak password", Snackbar.LENGTH_SHORT);
                        snackbar.show();


                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        Log.d("LoginActivity", "onComplete: FirebaseAuthInvalidCredentialsException "+e.getMessage());
                        Snackbar snackbar = Snackbar.make(userNameET, "Invalid credentials", Snackbar.LENGTH_SHORT);
                        snackbar.show();


                    }  catch(Exception e) {
                        Log.d("LoginActivity", "onComplete: Master Exception "+e.getMessage());
                        if(e.getMessage().contains("WEAK_PASSWORD")){
                            Snackbar snackbar = Snackbar.make(userNameET, "Weak password", Snackbar.LENGTH_SHORT);

                        }
                        Snackbar snackbar = Snackbar.make(userNameET, "Weak password", Snackbar.LENGTH_SHORT);
                        snackbar.show();


                    }
                }




                else {
                    Log.d("LoginActivity", "onComplete: Login is successfull");
                    Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
            }
        });
    }
}
