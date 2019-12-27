package com.example.beer.filtersettings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.beer.R;

public class FilterSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.filter_settings);
    }
}
