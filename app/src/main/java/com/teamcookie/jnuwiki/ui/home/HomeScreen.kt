package com.teamcookie.jnuwiki.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.teamcookie.jnuwiki.Const

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = {
            val mapview = MapView(it)
            mapview.start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    //맵뷰 제거됨.(라이프사이클)
                }

                override fun onMapError(error: Exception?) {
                    //에러, 인증 에러도 잡을 수 있음 (https://apis.map.kakao.com/android_v2/docs/getting-started/quickstart/)
                }

            }, object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    //맵뷰 준비됨
                }
            })
            mapview
        }, update = {
            //TODO: marker update
        })
    }
}