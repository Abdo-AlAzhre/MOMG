package com.example.xmltools.AppADS

import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.startapp.sdk.ads.banner.Banner
import com.startapp.sdk.ads.banner.BannerListener
import com.startapp.sdk.adsbase.StartAppAd

@Composable
fun BannerADS(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.5f)
            .height(50.dp),
        factory = { context ->
            val banner = Banner(context).apply {
                setBannerListener(object : BannerListener {
                    override fun onReceiveAd(p0: View?) {}

                    override fun onFailedToReceiveAd(p0: View?) {}

                    override fun onImpression(p0: View?) {}

                    override fun onClick(p0: View?) {}

                })
            }
            banner
        }
    )
}