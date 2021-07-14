package com.kalfian.infokosan.modules.property

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.SliderAdapter
import com.kalfian.infokosan.databinding.ActivityDetailPropertyBinding
import com.kalfian.infokosan.models.sliders.SliderItem
import com.kalfian.infokosan.utils.Constant
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class DetailPropertyActivity : AppCompatActivity() {

    var idProperty: Int = 0
    private lateinit var b: ActivityDetailPropertyBinding
    private lateinit var adapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        super.onCreate(savedInstanceState)
        b = ActivityDetailPropertyBinding.inflate(layoutInflater)
        setContentView(b.root)

        idProperty = intent.getIntExtra(Constant.DETAIL_PROPERTY_INTENT, 0)

        b.backButton.setOnClickListener {
            finish()
        }

        settingSlider()
        addItemSlider()
        addMap(savedInstanceState)

    }

    private fun addMap(savedInstanceState: Bundle?) {
        b.mapKos.onCreate(savedInstanceState)
        b.mapKos.getMapAsync { mapboxMap ->

            mapboxMap.setStyle(Style.MAPBOX_STREETS) {

                val symbolManager = SymbolManager(b.mapKos, mapboxMap, it)
                symbolManager.iconAllowOverlap = true
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_place_24, null)?.let { imgRes ->
                    it.addImage("myMarker",
                        imgRes
                    )
                }
                symbolManager.create(
                    SymbolOptions()
                    .withLatLng(LatLng(-7.9696, 112.6160))
                    .withIconImage("myMarker")
                )
            }

            val position = CameraPosition.Builder()
                .target(LatLng(-7.9696, 112.6160))
                .zoom(18.0)
                .tilt(20.0)
                .build()
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)


        }
    }

    private fun settingSlider() {
        adapter = SliderAdapter(this)

        b.imageSlider.setSliderAdapter(adapter)
        b.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        b.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        b.imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
    }

    private fun addItemSlider() {
        var sliderItemList = ArrayList<SliderItem>()
        for (i in 0..4) {
            var sliderItem = SliderItem("", "")

            if (i % 2 == 0) {
                sliderItem.url = "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            } else {
                sliderItem.url = "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"
            }
            sliderItemList.add(sliderItem)
        }
        adapter.renewItems(sliderItemList)
    }

    override fun onStart() {
        super.onStart()
        b.mapKos.onStart()
    }

    override fun onResume() {
        super.onResume()
        b.mapKos.onResume()
    }

    override fun onPause() {
        super.onPause()
        b.mapKos.onPause()
    }

    override fun onStop() {
        super.onStop()
        b.mapKos.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        b.mapKos.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        b.mapKos.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        b.mapKos.onDestroy()
    }
}