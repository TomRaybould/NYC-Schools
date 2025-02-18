package com.example.thomasraybould.nycschools.view.uiModels

import androidx.annotation.DrawableRes
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData

sealed class NycListItem {

    data class BoroughItemUiModel(
        val borough: Borough,
        @DrawableRes
        val imageRes: Int,
        val isLoading: Boolean
    ) : NycListItem()

    data class SchoolItemUiModel(
        val schoolName: String,
        val satScoreData: SatScoreData,
        val isLoading: Boolean,
        val isSelected: Boolean
    ) : NycListItem()

}
