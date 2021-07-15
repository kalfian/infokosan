package com.kalfian.infokosan.modules.property

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.SliderAdapter
import com.kalfian.infokosan.databinding.ActivityDetailPropertyBinding
import com.kalfian.infokosan.models.detail_property.DataProperty
import com.kalfian.infokosan.models.detail_property.DetailPropertyResponse
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.models.sliders.SliderItem
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.LoadingDialog
import com.kalfian.infokosan.utils.RetrofitClient
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailPropertyActivity : AppCompatActivity() {

    private var idProperty: Int = 1
    private lateinit var b: ActivityDetailPropertyBinding
    private lateinit var property: DataProperty
    private lateinit var adapter: SliderAdapter
    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        super.onCreate(savedInstanceState)
        b = ActivityDetailPropertyBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.mapKos.onCreate(savedInstanceState)

        dialog = LoadingDialog(this)
        dialog.start()

        idProperty = intent.getIntExtra(Constant.DETAIL_PROPERTY_INTENT, 0)
        getProperty(idProperty)

        b.backButton.setOnClickListener {
            finish()
        }

        b.btnSewa.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.Theme_Design_Light_BottomSheetDialog)
            val dialogView = LayoutInflater.from(applicationContext).inflate(
                R.layout.rent_bottom_sheet,
                findViewById<LinearLayout>(R.id.rent_bottom_sheet)
            )

            dialogView.findViewById<View>(R.id.bottom_close_btn).setOnClickListener {
                dialog.dismiss()
            }

            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            val formattedNumber: String = numberFormat.format(property.basicPrice).toString()
            dialogView.findViewById<EditText>(R.id.bottom_harga_kos).setText(formattedNumber)

            dialogView.findViewById<View>(R.id.bottom_tgl_masuk).setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    MotionToast.createColorToast(this, "Booking sukses!",
                        "$dayOfMonth $monthOfYear, $year",
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_TOP,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this, R.font.helvetica_regular)
                    )
                }, year, month, day)

                dpd.show()
            }

            dialogView.findViewById<View>(R.id.bottom_booking_btn).setOnClickListener {
                MotionToast.createColorToast(this,"Booking sukses!",
                    "silahkan ke menu booking untuk melakukan pembayaran",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_TOP,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )
                dialog.dismiss()
                finish()
            }

            dialog.setContentView(dialogView)
            dialog.show()

        }
    }

    private fun getProperty(idProperty: Int) {
        RetrofitClient.instance.getPropertyById(idProperty).enqueue(object: Callback<DetailPropertyResponse> {
            override fun onResponse(
                call: Call<DetailPropertyResponse>,
                response: Response<DetailPropertyResponse>
            ) {
                if (response.code() > 299) {
                    dialog.stop()
                    MotionToast.createColorToast(this@DetailPropertyActivity,"Kesalahan!",
                        "Gagal Mengambil data Kos",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_TOP,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@DetailPropertyActivity, R.font.helvetica_regular)
                    )
                    finish()
                }
                property = response.body()?.data!!

                addMap()
                settingSlider()
                addItemSlider()
                setDataProperty()
                dialog.stop()
            }

            override fun onFailure(call: Call<DetailPropertyResponse>, t: Throwable) {
                dialog.stop()
                MotionToast.createColorToast(this@DetailPropertyActivity,"Kesalahan!",
                    "Gagal Mengambil data Kos",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_TOP,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@DetailPropertyActivity, R.font.helvetica_regular)
                )
                finish()
            }

        })
    }


    private fun setDataProperty() {
        b.titleKos.text = "${property.title} (${property.squareMeter})"
        b.kotaKos.text = "${property.location.locationString.district} ${property.location.locationString.city}"
        b.deskripsiKos.text = if(property.desc != "") property.desc else "-"
        b.fasilitasKos.text = if(property.facilities != "") property.facilities else "-"
        b.poiKos.text = if(property.poi != "") property.poi else "-"
        b.peraturanKos.text = if(property.rules != "") property.rules else "-"
        b.alamatLengkapKos.text = "${property.location.address} ${property.location.locationString.district} ${property.location.locationString.city}"
        b.ownerKos.text = property.owner.name

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val formattedNumber: String = numberFormat.format(property.basicPrice).toString()
        b.hargaSewaKos.text = "${formattedNumber} / Bulan"
        b.hargaKos.text = "${formattedNumber} / Bulan"

    }

    private fun addMap() {
        var lat = property.location.lat
        var lng = property.location.long

        b.mapKos.getMapAsync { mapboxMap ->

            if (lat <= -90 || lat >= 90 ) {
                lat = 0.0
            }

            mapboxMap.setStyle(Style.MAPBOX_STREETS) {

                val symbolManager = SymbolManager(b.mapKos, mapboxMap, it)
                symbolManager.iconAllowOverlap = true
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_place_24, null)?.let { imgRes ->
                    it.addImage("myMarker",
                        imgRes
                    )
                }
                // Reset Marker
                symbolManager.deleteAll()
                symbolManager.create(
                    SymbolOptions()
                        .withLatLng(LatLng(lat, lng))
                        .withIconImage("myMarker")
                )
            }

            val position = CameraPosition.Builder()
                .target(LatLng(lat, lng))
                .zoom(18.0)
                .tilt(20.0)
                .build()
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)

            b.goToMaps.setOnClickListener {
                Log.d("LOCATION", "geo:${lat},${lng}")
                val gmmIntentUri = Uri.parse("geo:${lat},${lng}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                startActivity(mapIntent)
            }

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
        for (i in property.propertyImages) {
            var sliderItem = SliderItem("", "")

            sliderItem.url = i.image

            sliderItemList.add(sliderItem)
        }

        if (property.propertyImages.isEmpty()) {
            var sliderItem = SliderItem("", "")
            sliderItem.url = "-"
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