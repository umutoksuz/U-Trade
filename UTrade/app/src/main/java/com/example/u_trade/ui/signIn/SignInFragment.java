package com.example.u_trade.ui.signIn;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Request.LoginRequest;
import com.example.u_trade.Models.Response.LoginResponse;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.example.u_trade.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SignInFragment extends Fragment {

    Button submitButton;
    EditText usernameBox, passwordBox;
    TextView signInStatusText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_login, container, false);

        submitButton = root.findViewById(R.id.signin_button);
        usernameBox = root.findViewById(R.id.signin_username);
        passwordBox = root.findViewById(R.id.signin_password);
        signInStatusText = root.findViewById(R.id.signin_status);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    BJPClient client = new BJPClient();
                                    final Gson gsonBuilder = new GsonBuilder().create();
                                    LoginRequest requestBody = new LoginRequest(usernameBox.getText().toString(), passwordBox.getText().toString());
                                    final BjpResponse response = client.sendRequest("signin", "token", gsonBuilder.toJson(requestBody));

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()){
                                                LoginResponse loginResponse = gsonBuilder.fromJson(response.getData(), LoginResponse.class);
                                                StaticData.mapFromLoginResponse(loginResponse);
                                                setStateSignIn(root);
                                                setHomeFragment();
                                            }
                                            else {
                                                signInStatusText.setText(response.getMessage());
                                            }
                                        }
                                    });
                                } catch (Exception e){
                                    Log.e("EXCEPTION",e.getMessage());
                                }
                            }
                        }
                );
                thread.start();
            }
        });

        return root;
    }

    public void setHomeFragment(){
        Fragment home = new HomeFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, home);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void setStateSignIn(View root) {
        //TODO: Implement State Change
        /*NavigationView navigationView = root.findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.nav_home).setVisible(false);*/
    }
}