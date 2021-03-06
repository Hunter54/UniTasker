package com.ionutv.unitasker.dataClasses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Classes (
    @Json(name = "id") val id:Int,
    @Json(name = "week") val week:String,
    @Json(name = "teacher") val teacher:String="",
    @Json(name = "name") val name:String,
    @Json(name = "type") val type:Boolean,
    @Json(name = "time") val time:String,
    @Json(name = "day") val day:String,
    @Json(name = "room") val room:String
)