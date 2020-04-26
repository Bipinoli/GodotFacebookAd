package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import com.godot.game.R;
import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;
import android.util.Log;



public class GodotFacebookAd extends Godot.SingletonBase {

    protected Activity appActivity;
    protected Context appContext;
    private InterstitialAd interstitialAd; //interstitial ad
    private RewardedVideoAd rewardedVideoAd; // rewarded video ad 

    //Ads Sinals
    private boolean interstitialAdLoadingStatus = false;
    private boolean rewardedVideoAdLoadingStatus = false;

    // gdscript instance id
    private int instance_id = 0;


    public void init(int instance_id, String interstitialAdId , String rewardedVideoAdId){
        this.instance_id = instance_id;
        
        AudienceNetworkAds.initialize(this.appContext);// Initializing the audience network
        interstitialAd = new InterstitialAd(this.appContext,interstitialAdId); //initialization of interstitial with placement id given from godot script
        rewardedVideoAd = new RewardedVideoAd(this.appContext,rewardedVideoAdId); // initialization of the rewarded video ad with placement id given from godot script
        interstitialAd.loadAd();
        rewardedVideoAd.loadAd();


        appActivity.runOnUiThread(new Runnable() {
            public void run() {

                // interstitial ad listener
                interstitialAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial ad displayed callback
                        Log.e("FAN", "Interstitial ad displayed.");
                        GodotLib.calldeferred(instance_id, "_on_interstitial_displayed", new Object[] { });
                    }
                    
                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.e("FAN", "Interstitial ad dismissed.");
                        interstitialAd.loadAd(); // load the interstitial , so we can use it again.
                        GodotLib.calldeferred(instance_id, "_on_interstitial_dismissed", new Object[] { });
                    }
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e("FAN", "Interstitial ad failed to load: " + adError.getErrorMessage());
                        interstitialAd.loadAd(); // in case of errors try to load again
                        GodotLib.calldeferred(instance_id, "_on_interstitial_error", new Object[] { adError });
                    }
        
                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d("FAN", "Interstitial ad is loaded and ready to be displayed!");
                        // switch lodaing signal to true
                        interstitialAdLoadingStatus = true;
                        GodotLib.calldeferred(instance_id, "_on_interstitial_loaded", new Object[] { });
                    }
                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                        Log.d("FAN", "Interstitial ad clicked!");
                        GodotLib.calldeferred(instance_id, "_on_interstitial_clicked", new Object[] { });
                    }
                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                        Log.d("FAN", "Interstitial ad impression logged!");
                        GodotLib.calldeferred(instance_id, "_on_interstitial_impression_logged", new Object[] { });
                    }
                }); 

                //Rewarded video ad listener
                rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onError(Ad ad, AdError error) {
                      // Rewarded video ad failed to load
                      Log.e("FAN", "Rewarded video ad failed to load: " + error.getErrorMessage());
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_error", new Object[] { error });
                      rewardedVideoAd.loadAd(); // in cqse of errors try to load again
                    }
                
                    @Override
                    public void onAdLoaded(Ad ad) {
                      // Rewarded video ad is loaded and ready to be displayed  
                      Log.d("FAN", "Rewarded video ad is loaded and ready to be displayed!");
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_loaded", new Object[] { });
                      // set loading signal to true
                      rewardedVideoAdLoadingStatus = true;
                    }
                
                    @Override
                    public void onAdClicked(Ad ad) {
                      // Rewarded video ad clicked
                      Log.d("FAN", "Rewarded video ad clicked!");
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_clicked", new Object[] { });
                    }
                
                    @Override
                    public void onLoggingImpression(Ad ad) {
                      // Rewarded Video ad impression - the event will fire when the 
                      // video starts playing
                      Log.d("FAN", "Rewarded video ad impression logged!");
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_impression_logged", new Object[] { });
                    }
                
                    @Override
                    public void onRewardedVideoCompleted() {
                      // Rewarded Video View Complete - the video has been played to the end.
                      // You can use this event to initialize your reward
                      Log.d("FAN", "Rewarded video completed!");
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_complete", new Object[] { });
                      // Call method to give reward
                      // giveReward();
                    }
                
                    @Override
                    public void onRewardedVideoClosed() {
                      // The Rewarded Video ad was closed - this can occur during the video
                      // by closing the app, or closing the end card.
                      Log.d("FAN", "Rewarded video ad closed!");
                      rewardedVideoAd.destroy(); // i couldn't load another rewardedVideo without destroying the old one 
                      rewardedVideoAd.loadAd(); // load the rewarded video , so we can use it again.
                      GodotLib.calldeferred(instance_id, "_on_rewarded_video_closed", new Object[] { });
                    }
                });
            }
        });
    }
    public void showInterstitial(){
        if(interstitialAdLoadingStatus){
            interstitialAd.show();
            interstitialAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else{
            Log.d("GodotFacebookAd","Interstitial is not Loaded yet !");
        }
    }

    public void showRewardedVideo(){
        if(rewardedVideoAdLoadingStatus){
            rewardedVideoAd.show();
            rewardedVideoAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else {
            Log.d("GodotFacebookAd","RewardedVideo is not Loaded yet !");
        }
    }

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new GodotFacebookAd(p_activity);
    }

    public GodotFacebookAd(Activity p_activity) {
        //register class name and functions to bind
        registerClass("GodotFacebookAd", new String[]{"init","showInterstitial","showRewardedVideo"});
        this.appActivity = p_activity;
        this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

}