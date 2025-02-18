package com.example.thomasraybould.nycschools.view.school_list_activity

import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

data class SchoolListUiModel(
    val schoolListItemUiModels: List<NycListItem>,
    val errorMessage: String? = null
) {
    override fun equals(other: Any?): Boolean {
        val otherModel = (other as? SchoolListUiModel) ?: return false

        if (otherModel.errorMessage != this.errorMessage) {
            return false
        } else if (otherModel.schoolListItemUiModels.size != this.schoolListItemUiModels.size) {
            return false
        }

        otherModel.schoolListItemUiModels.forEachIndexed { index, nycListItem ->
            val compareItem = this.schoolListItemUiModels[index]
            if (nycListItem != compareItem) {
                return false
            }
        }
        return true
    }
}