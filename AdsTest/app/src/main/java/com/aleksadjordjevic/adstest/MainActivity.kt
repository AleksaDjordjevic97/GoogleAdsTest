package com.aleksadjordjevic.adstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aleksadjordjevic.adstest.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBannerContinue.setOnClickListener { goToNextActivity() }

        initializeBannerAd()
    }

    private fun initializeBannerAd()
    {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adViewBanner.loadAd(adRequest)
    }

    private fun goToNextActivity()
    {
        val nextActivityIntent = Intent(this,MainActivity2::class.java)
        startActivity(nextActivityIntent)
    }
}