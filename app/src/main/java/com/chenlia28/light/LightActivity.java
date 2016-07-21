package com.chenlia28.light;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

//import com.baidu.mobads.AdSize;
//import com.baidu.mobads.AdView;

public class LightActivity extends Activity {
	private Button lightBtn = null;
	private Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true; // ���忪��״̬��״̬Ϊfalse����״̬��״̬Ϊtrue���ر�״̬
	// public static boolean action = false;
	// //�����״̬��״̬Ϊfalse����ǰ���治�˳���״̬Ϊtrue����ǰ�����˳�
	private int back = 0;// �жϰ�����back
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;
//	AdView adView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		adView = new AdView(this, AdSize.Banner,"2599460");
		// ȫ�����ã����ش�������װ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ������Ļ��ʾ�ޱ��⣬����������Ҫ���úã��������ٴα�����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.main);

		lightBtn = (Button) findViewById(R.id.btn_light);
		lightBtn.setOnClickListener(new Mybutton());

		//���� AdRequest �� AdView ��,��ӽ��ڲ������ҵ� AdView �Ĵ��룬������ AdRequest��Ȼ��ʹ�����������ص� AdView ��
		AdView mAdView = (AdView) findViewById(R.id.adView);
//		AdRequest.Builder.addTestDevice("976DEB44FBEB127CCCC2F4FBD0885779");
//		AdRequest.Builder.addTestDevice("D7E6960733E0A35DDDB2EC39B31D21FA");
//		AdRequest.Builder.addTestDevice("AdRequest.DEVICE_ID_EMULATOR");
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

//		addContentView(adView, new ViewGroup.LayoutParams(-1, -2));
		// ============����startapp=======================
		// AndroidSDKProvider.initSDK(this);
		// ��ʼ��imadpush���ƽ̨
		// new AppPosterManager(this);

		// PAM.getInstance(this).receivePushMessage(this, false);

		// adView = new AdView(this);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Light Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.chenlia28.light/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Light Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.chenlia28.light/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	class Mybutton implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (kaiguan) {

				lightBtn.setBackgroundResource(R.drawable.shou_on);
				camera = Camera.open();
				parameters = camera.getParameters();
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// ����
				camera.setParameters(parameters);
				camera.startPreview();
				kaiguan = false;
			} else {
				// addContentView(adView, new ViewGroup.LayoutParams(-1, -2));
				lightBtn.setBackgroundResource(R.drawable.shou_off);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// �ر�
				camera.setParameters(parameters);
				camera.stopPreview();
				kaiguan = true;
				camera.release();
			}

			// AdView���캯�����Խ�������������context(������), AdSize����(�����ʽ),
			// ���λID(�Ǹ߼����λ��null����)
			// AdView adView = new AdView(this, AdSize.Square, null);
			// AdView adView = new AdView(this, AdSize.Banner, null);

			// ����adViewΪ��ǰActivity��View

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// menu.add(0, 2, 2, "�˳�");
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case 2:
	// Myback();
	// break;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back++;
			switch (back) {
				case 1:
					Toast.makeText(LightActivity.this,
							getString(R.string.again_exit), Toast.LENGTH_SHORT)
							.show();
					break;
				case 2:
					back = 0;
					Myback();
					break;
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void Myback() { // �رճ���
		if (kaiguan) {// ���عر�ʱ
			LightActivity.this.finish();
			Process.killProcess(Process.myPid());// �رս���
		} else if (!kaiguan) {// ���ش�ʱ
			camera.release();
			LightActivity.this.finish();
			Process.killProcess(Process.myPid());// �رս���
			kaiguan = true;// ���⣬�򿪿��غ��˳������ٴν��벻�򿪿���ֱ���˳�ʱ���������
		}
	}
}