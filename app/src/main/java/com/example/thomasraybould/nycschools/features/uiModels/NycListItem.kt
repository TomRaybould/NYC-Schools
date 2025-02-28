package com.example.thomasraybould.nycschools.features.uiModels

import androidx.annotation.DrawableRes
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School

sealed class NycListItem(open val borough: Borough) {

    data class BoroughItemUiModel(
        override val borough: Borough,
        @DrawableRes
        val imageRes: Int = getImageForBorough(borough),
        var isLoading: Boolean = false,
        var isSelected: Boolean = false
    ) : NycListItem(borough)

    data class SchoolItemUiModel(
        override val borough: Borough,
        val school: School,
        var isLoading: Boolean = false,
        var isSelected: Boolean = false,
        val satScoreData: SatScoreData? = null
    ) : NycListItem(borough)

    companion object {
        fun getImageForBorough(borough: Borough): Int {
            return when (borough.code) {
                "M" -> R.drawable.manhattan
                "K" -> R.drawable.brooklyn
                "Q" -> R.drawable.queens
                "X" -> R.drawable.bronx
                "R" -> R.drawable.statenisland
                else -> R.drawable.manhattan
            }
        }
    }

}
