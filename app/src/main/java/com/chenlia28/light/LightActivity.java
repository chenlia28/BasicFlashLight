package com.chenlia28.light;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LightActivity extends Activity {
	private Button lightBtn = null;
	private Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true; // ���忪��״̬��״̬Ϊfalse����״̬��״̬Ϊtrue���ر�״̬
	// public static boolean action = false;
	// //�����״̬��״̬Ϊfalse����ǰ���治�˳���״̬Ϊtrue����ǰ�����˳�
	private int back = 0;// �жϰ�����back
//	AdView adView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		adView = new AdView(this, AdSize.Banner,"2599460");
		// ȫ�����ã����ش�������װ��

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

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
//		addContentView(adView, new ViewGroup.LayoutParams(-1, -2));
		// ============����startapp=======================
		// AndroidSDKProvider.initSDK(this);
		// ��ʼ��imadpush���ƽ̨
		// new AppPosterManager(this);

		// PAM.getInstance(this).receivePushMessage(this, false);

		// adView = new AdView(this);

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
			android.os.Process.killProcess(android.os.Process.myPid());// �رս���
		} else if (!kaiguan) {// ���ش�ʱ
			camera.release();
			LightActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// �رս���
			kaiguan = true;// ���⣬�򿪿��غ��˳������ٴν��벻�򿪿���ֱ���˳�ʱ���������
		}
	}
}