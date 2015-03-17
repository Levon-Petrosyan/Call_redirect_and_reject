package com.example.mycallapk;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class PhoneCall extends BroadcastReceiver {

	private Context context;
	private ITelephony telephonyService = null;

	@Override
	public void onReceive(Context c, Intent intent) {
		context = c;
		if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_RINGING)) {
			getTeleService();
			setTimer();
		}
	}

	private void setTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				rejectCall();
			}
		}, 5000);
	}

	private void getTeleService() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class c = Class.forName(tm.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(tm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rejectCall() {
		try {
			telephonyService.endCall();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
