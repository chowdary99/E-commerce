package com.sriram.user.project1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class homeFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
//TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}