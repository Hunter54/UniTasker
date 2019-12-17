package com.ionutv.unitasker.dataClasses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data (
    @Json(name = "odd") var oddClasses: ArrayList<Classes>,
    @Json(name = "even") var evenClasses: ArrayList<Classes>
)