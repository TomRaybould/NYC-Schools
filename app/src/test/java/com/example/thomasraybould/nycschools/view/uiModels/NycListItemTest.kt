package com.example.thomasraybould.nycschools.view.uiModels

import com.example.thomasraybould.nycschools.entities.Borough
import org.junit.Assert
import org.junit.Test

class NycListItemTest {

    @Test
    fun `data class equals sanity check`() {

        val boroughItemUiModel1: NycListItem = NycListItem.BoroughItemUiModel(
            borough = Borough.QUEENS,
            imageRes = 1,
            isLoading = false,
            isSelected = false
        )

        val boroughItemUiModel2: NycListItem = NycListItem.BoroughItemUiModel(
            borough = Borough.QUEENS,
            imageRes = 1,
            isLoading = false,
            isSelected = false
        )

        val boroughItemUiModel3: NycListItem = NycListItem.BoroughItemUiModel(
            borough = Borough.QUEENS,
            imageRes = 1,
            isLoading = true,
            isSelected = false
        )

        Assert.assertTrue(boroughItemUiModel1 == boroughItemUiModel2)
        Assert.assertTrue(boroughItemUiModel1 != boroughItemUiModel3)

    }


}