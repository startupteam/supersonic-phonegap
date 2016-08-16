package uk.mondosports.plugins.supersonic;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.supersonic.mediationsdk.logger.SupersonicError;
import com.supersonic.mediationsdk.sdk.OfferwallListener;
import com.supersonic.mediationsdk.sdk.Supersonic;
import com.supersonic.mediationsdk.sdk.SupersonicFactory;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SupersonicPlugin extends CordovaPlugin
{

	private static final String LOGTAG = "SupersonicPlugin";
	private static final String DEFAULT_APP_KEY = "32c9d67d";

	private static final String ACTION_INITIALIZE = "initialize";
	private static final String ACTION_SHOW_OFFERWALL = "showOfferwall";
	private static final String ACTION_CLOSE_OFFERWALL = "closeOfferwall";
	private static final String OPT_APPLICATION_KEY = "appKey";
	private static final String OPT_USER_ID = "userId";

	private String appKey = DEFAULT_APP_KEY;
	private String userId = "5043b715c3bd823b760000ff";
	private Supersonic mMediationAgent;
	private CallbackContext closeOfferwallCallback;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		PluginResult result = null;

		if (ACTION_INITIALIZE.equals(action)) {
			JSONObject options = args.optJSONObject(0);
			result = executeInitialize(options, callbackContext);
		} else if (ACTION_SHOW_OFFERWALL.equals(action)) {
			JSONObject options = args.optJSONObject(0);
			result = executeShowOfferwall(options, callbackContext);
		} else if (ACTION_CLOSE_OFFERWALL.equals(action)) {
            closeOfferwallCallback = callbackContext;
		}

		if (result != null) callbackContext.sendPluginResult( result );

		return true;
	}
/*
	@Override
	protected void onResume() {
		super.onResume(false);
		if (ssaPub != null) {
			ssaPub.onResume(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (ssaPub != null) {
			ssaPub.onPause(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (ssaPub != null) {
			ssaPub.release(this);
		}
	}*/

	private PluginResult executeInitialize(final JSONObject options, final CallbackContext callbackContext) {
		Log.w(LOGTAG, "executeInitialize");

		cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                initialize(options);

                callbackContext.success();
            }
        });

		return null;
	}

	private void initialize(JSONObject options) {

		if (options.has(OPT_APPLICATION_KEY)) {
			this.appKey = options.optString(OPT_APPLICATION_KEY);
		}

		if(options.has(OPT_USER_ID)) {
			this.userId = options.optString(OPT_USER_ID);
		}

		OfferwallListener mOfferwallListener = new OfferwallListener()
		{
			public void onOfferwallInitSuccess() {}

			public void onOfferwallInitFail(SupersonicError error) {}

			public void onOfferwallOpened() {}

			public void onOfferwallShowFail(SupersonicError supersonicError) {}

			public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
				return false;
			}

			public void onGetOfferwallCreditsFail(SupersonicError supersonicError) {}

			public void onOfferwallClosed() {
				Log.w(LOGTAG, "onOfferwallClosed");

				closeOfferwallCallback.success();
			}
		};

		mMediationAgent = SupersonicFactory.getInstance();

		mMediationAgent.setOfferwallListener(mOfferwallListener);

		mMediationAgent.initOfferwall(this.cordova.getActivity(), this.appKey, this.userId);
	}

	private PluginResult executeShowOfferwall(JSONObject options, CallbackContext callbackContext) {
		Log.w(LOGTAG, "executeShowOfferwall");

		showOfferWall(options);

		callbackContext.success();

		return null;
	}

	private void showOfferWall(JSONObject options) {
		Log.w(LOGTAG, "isOfferwallAvailable" + mMediationAgent.isOfferwallAvailable());

        if (mMediationAgent.isOfferwallAvailable()) {
            mMediationAgent.showOfferwall();
        }
	}
}