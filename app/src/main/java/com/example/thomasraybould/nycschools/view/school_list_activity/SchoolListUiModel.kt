package com.example.thomasraybould.nycschools.view.school_list_activity

import android.util.Log
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

data class SchoolListUiModel(
    val schoolListItemUiModels: List<NycListItem>,
    val errorMessage: String? = null
){
    override fun equals(other: Any?): Boolean {
        val o1 = ((other as? SchoolListUiModel)?.schoolListItemUiModels?.size
            ?: 1)
        val o2 = this.schoolListItemUiModels.size ?: 1
        val equal = o1 == o2
        Log.v("equals= $equal" , "o1 $o1 \no2 $o2")
        return equal
    }
}