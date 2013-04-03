/**
 * 
 */
package org.usac.eco.student.model;

import org.usac.eco.student.Configure;
import org.usac.eco.student.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class EcoPreferenceActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.layout.settings);
	}

	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		Configure.load(PreferenceManager.getDefaultSharedPreferences(this));
	}

}
