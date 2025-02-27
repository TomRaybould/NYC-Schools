package com.example.thomasraybould.nycschools.entities

data class School (
    val dbn: String,
    val name: String,
    val borough: Borough,
    val webPageLink: String? = null
)
