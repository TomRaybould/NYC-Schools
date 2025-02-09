package com.example.thomasraybould.nycschools.view.uiModels

import androidx.annotation.DrawableRes
import com.example.thomasraybould.nycschools.entities.Borough

sealed class NycListItem {

    data class BoroughItem(
        val borough: Borough,
        @DrawableRes
        val imageRes: Int,
        val isLoading: Boolean
    )

}
