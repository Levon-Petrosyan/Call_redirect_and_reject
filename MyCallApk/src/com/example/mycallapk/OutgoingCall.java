package com.example.mycallapk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutgoingCall extends BroadcastReceiver {
	
	private boolean isRegistered = false;
	private String number = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		if(isRegistered) {
			setResultData(number);
			getResultData();	
		}
	}
	
	public void setNumber(String n) {
		number = n;
	}
	
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
	public boolean isRegistered() {
		return isRegistered;
	}
}
