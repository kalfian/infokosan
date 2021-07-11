package com.kalfian.infokosan.modules.property

import android.graphics.Color.GRAY
import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.SliderAdapter
import com.kalfian.infokosan.databinding.ActivityDetailPropertyBinding
import com.kalfian.infokosan.models.sliders.SliderItem
import com.kalfian.infokosan.utils.Constant
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class DetailPropertyActivity : AppCompatActivity() {

    var idProperty: Int = 0
    private lateinit var b: ActivityDetailPropertyBinding
    private lateinit var adapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailPropertyBinding.inflate(layoutInflater)
        setContentView(b.root)

        idProperty = intent.getIntExtra(Constant.DETAIL_PROPERTY_INTENT, 0)

        b.backButton.setOnClickListener {
            finish()
        }

        settingSlider()
        addItemSlider()

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
}