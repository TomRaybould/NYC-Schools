package com.example.thomasraybould.nycschools.features.selector_activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.features.school_list_activity.SchoolListActivity
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.SchoolListComposeActivity

class SelectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectableItems = listOf(
            SelectableItem(R.string.rx_constraint_layout) { SchoolListActivity.startActivity(this) },
            SelectableItem(R.string.rx_compose) { SchoolListComposeActivity.startActivity(this) },
        )

        val uiModel = SelectorActivityUiModel(selectableItems = selectableItems)

        setContent {
            SelectorViewScreen(uiModel)
        }

    }

}