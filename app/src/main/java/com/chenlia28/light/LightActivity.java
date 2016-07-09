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
	public static boolean kaiguan = true; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态
	// public static boolean action = false;
	// //定义的状态，状态为false，当前界面不退出，状态为true，当前界面退出
	private int back = 0;// 判断按几次back
//	AdView adView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		adView = new AdView(this, AdSize.Banner,"2599460");
		// 全屏设置，隐藏窗口所有装饰

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置屏幕显示无标题，必须启动就要设置好，否则不能再次被设置
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.main);

		lightBtn = (Button) findViewById(R.id.btn_light);
		lightBtn.setOnClickListener(new Mybutton());
//		addContentView(adView, new ViewGroup.LayoutParams(-1, -2));
		// ============启动startapp=======================
		// AndroidSDKProvider.initSDK(this);
		// 初始化imadpush广告平台
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
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
				camera.setParameters(parameters);
				camera.startPreview();
				kaiguan = false;
			} else {
				// addContentView(adView, new ViewGroup.LayoutParams(-1, -2));
				lightBtn.setBackgroundResource(R.drawable.shou_off);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
				camera.setParameters(parameters);
				camera.stopPreview();
				kaiguan = true;
				camera.release();
			}

			// AdView构造函数可以接收三个参数：context(上下文), AdSize类型(广告样式),
			// 广告位ID(非高级广告位填null即可)
			// AdView adView = new AdView(this, AdSize.Square, null);
			// AdView adView = new AdView(this, AdSize.Banner, null);

			// 设置adView为当前Activity的View

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// menu.add(0, 2, 2, "退出");
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

	public void Myback() { // 关闭程序
		if (kaiguan) {// 开关关闭时
			LightActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
		} else if (!kaiguan) {// 开关打开时
			camera.release();
			LightActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
			kaiguan = true;// 避免，打开开关后退出程序，再次进入不打开开关直接退出时，程序错误
		}
	}
}