package uk.mondosports.plugins.supersonic;

import java.util.HashMap;
import java.util.Map;

import com.supersonicads.sdk.*;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

public class SupersonicPlugin extends CordovaPlugin implements 
OnRewardedVideoListener,
OnInterstitialListener,
OnOfferWallListener {

    private static final String LOGTAG = "SupersonicPlugin";
    private static final String DEFAULT_APP_KEY = "32c9d67d";

    private static final String ACTION_INITIALIZE = "initialize";
    private static final String ACTION_SHOW_OFFERWALL = "showOfferWall";
    private static final String OPT_APPLICATION_KEY = "appKey";
    private static final String OPT_USER_ID = "userId";

    private String appKey = DEFAULT_APP_KEY;
    private String userId = "";
    private SSAPublisher ssaPub; 

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (ACTION_SET_OPTIONS.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            result = executeInitialize(options, callbackContext);
        } else if (ACTION_SHOW_OFFERWALL.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            result = executeShowOfferwall(options, callbackContext);
        }

        if (result != null) callbackContext.sendPluginResult( result );

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume(); 
        if (ssaPub != null) {
            ssaPub.onResume (this);
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
    }

    private PluginResult executeInitialize(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeInitialize");
        
        this.initialize( options );
        
        callbackContext.success();

        return null;
    }

    private void initialize( JSONObject options ) {
        if(options == null) return;
        
        if(options.has(OPT_APPLICATION_KEY)) this.appKey = options.optString( OPT_APPLICATION_KEY );
        if(options.has(OPT_USER_ID)) this.userId = options.optString( OPT_USER_ID );

        ssaPub = SSAFactory.getPublisherInstance(this);
    }

    private PluginResult executeShowOfferwall(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeShowOfferwall");
        
        this.showOfferWall( options );
        
        callbackContext.success();

        return null;
    }

    private void showOfferWall(JSONObject options) {
        Map<String, String> extraParams = new HashMap<String, String>();
        ssaPub.showOfferWall(this.appKey, this.userId, extraParams, this);
    }
}