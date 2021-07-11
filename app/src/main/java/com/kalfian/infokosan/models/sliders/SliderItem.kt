package com.kalfian.infokosan.models.sliders

import com.google.gson.annotations.SerializedName

data class SliderItem (
    @SerializedName("description")
    var description: String,

    @SerializedName("url")
    var url: String,
)