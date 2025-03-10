package com.example.thomasraybould.nycschools.entities

data class SatScoreData(
    val dbn: String,
    val dataAvailable: Boolean,
    val math: Int,
    val reading: Int,
    val writing:Int
)