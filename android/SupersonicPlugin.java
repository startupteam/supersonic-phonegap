package uk.mondosports.plugins.supersonic;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SupersonicPlugin extends CordovaPlugin {

    private static final String LOGTAG = "SupersonicPlugin";
    private static final String DEFAULT_APP_KEY = "32c9d67d";

    private static final String ACTION_INITIALIZE = "initialize";
    private static final String ACTION_SHOW_OFFERWALL = "showOfferwall";
    private static final String ACTION_CLOSE_OFFERWALL = "closeOfferwall";
    private static final String OPT_APPLICATION_KEY = "appKey";
    private static final String OPT_USER_ID = "userId";

    private String appKey = DEFAULT_APP_KEY;
    private String userId = "5043b715c3bd823b760000ff";

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

        if (result != null) callbackContext.sendPluginResult(result);

        return true;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        IronSource.onResume(this.cordova.getActivity());
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        IronSource.onPause(this.cordova.getActivity());
    }

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

        if (options.has(OPT_USER_ID)) {
            this.userId = options.optString(OPT_USER_ID);
        }

        IronSource.setOfferwallListener(this.getOfferwallListener());
        IronSource.setUserId(this.userId);
        IronSource.init(this.cordova.getActivity(), this.appKey);
    }

    private PluginResult executeShowOfferwall(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeShowOfferwall");

        showOfferWall(options);

        callbackContext.success();

        return null;
    }

    private void showOfferWall(JSONObject options) {
        Log.w(LOGTAG, "isOfferwallAvailable " + IronSource.isOfferwallAvailable());

        if (IronSource.isOfferwallAvailable()) {
            IronSource.showOfferwall();
        }
    }

    public OfferwallListener getOfferwallListener() {
        OfferwallListener mOfferwallListener = new OfferwallListener() {
            @Override
            public void onOfferwallAvailable(boolean isAvailable) {
            }

            @Override
            public void onOfferwallOpened() {
            }

            @Override
            public void onOfferwallShowFailed(IronSourceError error) {
            }

            @Override
            public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
                return false;
            }

            @Override
            public void onGetOfferwallCreditsFailed(IronSourceError error) {
            }

            @Override
            public void onOfferwallClosed() {
                Log.w(LOGTAG, "onOfferwallClosed");

                closeOfferwallCallback.success();
            }
        };

        return mOfferwallListener;
    }
}