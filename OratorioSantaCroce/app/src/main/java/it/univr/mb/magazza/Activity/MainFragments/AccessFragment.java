package it.univr.mb.magazza.Activity.MainFragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.univr.mb.magazza.Activity.MainActivity;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.User;
import it.univr.mb.magazza.R;


public class AccessFragment extends Fragment {
    private final static String TAG = "Access Fragment:";

    private TextView imeiTextView;
    private Button loginButton;
    private TextView unregistered;
    private String imeiCode;


    public AccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_access, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imeiTextView = view.findViewById(R.id.imei);
        unregistered = view.findViewById(R.id.unregistered_label);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v-> loginButtonOnClick());


        if(!((MainActivity)getActivity()).checkPhonePermission())
            ((MainActivity)getActivity()).requestPhonePermissions();
        getImei();


    }

    public void getImei(){
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(((MainActivity)getActivity()).checkPhonePermission())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                imeiCode = tm.getImei();
            else
                imeiCode =tm.getDeviceId();
        imeiTextView.setText(imeiCode);
    }

    private void loginButtonOnClick() {
        ObjectBuilder.getInstance().checkLogin(getContext(), imeiCode, this);
    }

    public void loginAccepted(){
        Log.d(TAG,"login accettato");
        User user = ObjectBuilder.getInstance().getCurrentUser();
        Toast.makeText(getContext(), "Benvenuto " + user.getName() + " " + user.getSurname(), Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).launchPrendiLasciaFragment();
    }

    public void loginFailed() {
        Toast.makeText(getContext(), "User non registrato", Toast.LENGTH_SHORT).show();
        unregistered.setVisibility(View.VISIBLE);
    }
}
