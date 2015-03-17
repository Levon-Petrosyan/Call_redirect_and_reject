package com.example.mycallapk;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private OutgoingCall outgoingCallReceiver = null;
	private EditText numberField = null;
	private Button redirectButton = null;
	private Button cancelServiceButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numberField = (EditText) findViewById(R.id.redirectNumber);
		redirectButton = (Button) findViewById(R.id.redirectButton);
		cancelServiceButton = (Button) findViewById(R.id.cancel);
		outgoingCallReceiver = new OutgoingCall();

		registerRedirectButtonClick();
		registerCancelButtonClick();
	}

	private void registerRedirectButtonClick() {
		redirectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String redirectNumber = numberField.getText().toString();
				outgoingCallReceiver.setRegistered(true);
				registerOutGoingCalls(redirectNumber);
				Toast.makeText(MainActivity.this,
						"Redirecting calls to " + redirectNumber + "number",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void registerCancelButtonClick() {
		cancelServiceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (outgoingCallReceiver.isRegistered()) {
					unregisterReceiver(outgoingCallReceiver);
					outgoingCallReceiver.setRegistered(false);
				}
			}
		});
	}

	private void registerOutGoingCalls(String number) {
		outgoingCallReceiver.setNumber(number);
		IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(outgoingCallReceiver, filter);
	}

}