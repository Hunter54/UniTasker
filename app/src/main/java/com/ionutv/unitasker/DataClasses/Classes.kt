package com.ionutv.unitasker.DataClasses

import com.google.gson.annotations.SerializedName

data class Classes (
    @SerializedName("name") val name:String,
    @SerializedName("professor") val professor:String,
    @SerializedName("type") val type:Boolean,
    @SerializedName("time") val Time:String,
    @SerializedName("room") val room:String
)