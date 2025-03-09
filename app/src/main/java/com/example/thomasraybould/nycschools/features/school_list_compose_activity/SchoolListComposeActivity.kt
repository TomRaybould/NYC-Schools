package com.example.thomasraybould.nycschools.features.school_list_compose_activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel.ComposeSchoolListViewModel
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel.ComposeSchoolListViewModelImpl
import dagger.android.AndroidInjection
import javax.inject.Inject

class SchoolListComposeActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var schoolListViewModel: ComposeSchoolListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        AndroidInjection.inject(this)

        schoolListViewModel =
            ViewModelProvider(this, factory)[ComposeSchoolListViewModelImpl::class.java]

        schoolListViewModel.getSchoolList().observe(this) { schoolListUiModel ->
            schoolListUiModel.errorMessage?.let {
                toast(it)
            }
        }

        setContent {
            SchoolListScreen(schoolListViewModel)
        }

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}