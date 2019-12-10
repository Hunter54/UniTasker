package com.ionutv.unitasker.DataClasses

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("odd") val oddClasses: ArrayList<Classes>,
    @SerializedName("even") val evenClasses: ArrayList<Classes>
)