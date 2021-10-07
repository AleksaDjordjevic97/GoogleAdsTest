package com.aleksadjordjevic.adstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.aleksadjordjevic.adstest.databinding.ActivityMain2Binding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

private const val TAG = "INTERSTITIAL_ADS"

class MainActivity2 : AppCompatActivity()
{
    private lateinit var binding: ActivityMain2Binding
    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInitialUI()

        initializeBannerAd()

        loadInterstitialAd()
        addInterstitialAdsFullscreenCallback()

        loadRewardedAd()
        addRewardedAdsFullscreenCallback()
        binding.btnRewardAd.setOnClickListener { showRewardedAd() }
    }

    private fun setupInitialUI()
    {
        binding.btnRewardAd.setImageResource(R.drawable.treasure_closed)
        binding.txtClickForReward.visibility = View.VISIBLE
        binding.txtReward.visibility = View.INVISIBLE
        binding.surpriseCat.visibility = View.INVISIBLE
    }

    private fun initializeBannerAd()
    {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adViewBanner2.loadAd(adRequest)
    }

    private fun loadInterstitialAd()
    {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback()
        {
            override fun onAdFailedToLoad(adError: LoadAdError)
            {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd)
            {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
                showInterstitialAd()
            }
        })
    }

    private fun addInterstitialAdsFullscreenCallback()
    {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback()
        {
            override fun onAdDismissedFullScreenContent()
            {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?)
            {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent()
            {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
    }

    private fun showInterstitialAd()
    {
        if (mInterstitialAd != null)
            mInterstitialAd?.show(this)
        else
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
    }

    private fun loadRewardedAd()
    {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError)
            {
                Log.d(TAG, adError?.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd)
            {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })
    }

    private fun addRewardedAdsFullscreenCallback()
    {
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent()
            {
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?)
            {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent()
            {
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }
    }

    private fun showRewardedAd()
    {
        if (mRewardedAd != null)
        {
            mRewardedAd?.show(this, OnUserEarnedRewardListener { rewardItem ->
                binding.btnRewardAd.setImageResource(R.drawable.treasure_opened)
                binding.txtClickForReward.visibility = View.INVISIBLE
                binding.txtReward.visibility = View.VISIBLE
                binding.surpriseCat.visibility = View.VISIBLE
                Log.d(TAG, "User earned the reward.")
            })

        }
        else
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
    }

}