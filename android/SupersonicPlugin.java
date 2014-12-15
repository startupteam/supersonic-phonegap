package uk.mondosports.plugins.supersonic;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.supersonicads.sdk.*;
import com.supersonicads.sdk.data.AdUnitsReady;
import com.supersonicads.sdk.listeners.OnInterstitialListener;
import com.supersonicads.sdk.listeners.OnOfferWallListener;
import com.supersonicads.sdk.listeners.OnRewardedVideoListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        PluginResult result = null;
        
        if (ACTION_INITIALIZE.equals(action)) {
            JSONObject options = args.optJSONObject(0);
            result = executeInitialize(options, callbackContext);
        } else if (ACTION_SHOW_OFFERWALL.equals(action)) {
            JSONObject options = args.optJSONObject(0);
            result = executeShowOfferwall(options, callbackContext);
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

        ssaPub = SSAFactory.getPublisherInstance(this.webView.getContext());
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

    @Override
    public void onGetOWCreditsFailed(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onOWAdClosed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onOWAdCredited(int arg0, int arg1, boolean arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onOWGeneric(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onOWShowFail(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onOWShowSuccess() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISAdClosed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISGeneric(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISInitFail(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISInitSuccess() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISLoaded() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onISLoadedFail(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVAdClosed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVAdCredited(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVGeneric(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVInitFail(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVInitSuccess(AdUnitsReady arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRVNoMoreOffers() {
        // TODO Auto-generated method stub
        
    }
}