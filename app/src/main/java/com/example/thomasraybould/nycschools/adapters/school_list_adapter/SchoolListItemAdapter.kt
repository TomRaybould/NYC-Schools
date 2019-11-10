package com.example.thomasraybould.nycschools.adapters.school_list_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("setBoroughImageRes")
fun setBoroughImageRes(imageView: ImageView, schoolListItemUiModel: SchoolListItemUiModel?) {
    schoolListItemUiModel?.let {
        Glide.with(imageView.context)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(schoolListItemUiModel.imageResId)
                .into(imageView)
    }
}