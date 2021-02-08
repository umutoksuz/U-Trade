package com.example.u_trade.ui.signUp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.u_trade.Models.Request.RegisterRequest;
import com.example.u_trade.Models.Response.RegisterResponse;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.example.u_trade.ui.home.HomeFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SignUpFragment extends Fragment {

    EditText usernameBox, passwordBox, firstNameBox, lastNameBox;
    Button submitButton;
    TextView statusText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);

        usernameBox = (EditText) root.findViewById(R.id.signup_username);
        passwordBox = (EditText) root.findViewById(R.id.signup_password);
        firstNameBox = (EditText) root.findViewById(R.id.signup_firstname);
        lastNameBox = (EditText) root.findViewById(R.id.signup_lastname);
        submitButton = (Button) root.findViewById(R.id.signup_button);
        statusText = (TextView) root.findViewById(R.id.signup_status);

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
                                    RegisterRequest requestBody = new RegisterRequest(usernameBox.getText().toString(),
                                            passwordBox.getText().toString(), firstNameBox.getText().toString(), lastNameBox.getText().toString());
                                    final BjpResponse response = client.sendRequest("signup", "token", gsonBuilder.toJson(requestBody));

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                                RegisterResponse responseBody = gsonBuilder.fromJson(response.getData(), RegisterResponse.class);
                                                StaticData.mapFromRegisterResponse(responseBody);
                                                setStateSignIn(root);
                                                setHomeFragment();
                                            } else {
                                                statusText.setText(response.getMessage());
                                            }
                                        }
                                    });

                                } catch (Exception e) {
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