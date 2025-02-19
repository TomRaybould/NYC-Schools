package com.example.thomasraybould.nycschools.adapters.school_list_adapter

import android.graphics.PorterDuff
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem


@BindingAdapter("setBoroughImageRes")
fun setBoroughImageRes(imageView: ImageView, boroughItemUiModel: NycListItem.BoroughItemUiModel?) {
    boroughItemUiModel?.let {
        Glide.with(imageView.context)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(boroughItemUiModel.imageRes)
                .into(imageView)
    }
}

@BindingAdapter("setScoreTextForSubject", "isDataAvailable")
fun setScoreTextForSubject(textView: TextView, score: Int, isDataAvailable: Boolean) {
    if (isDataAvailable) {
        val spanned = Html.fromHtml(addBlackStyleToString(score.toString()))
        textView.text = spanned
    } else {
        textView.text = "N/A"
    }
}

private fun addBlackStyleToString(string: String): String {
    return "<b><font color='black'>$string</font></b>/800"
}

@BindingAdapter("setScoreProgressForSubject", "isDataAvailable")
fun setScoreProgressForSubject(progressBar: ProgressBar, score: Int, isDataAvailable: Boolean) {
    progressBar.progress = if (isDataAvailable) {
        (100 * score / 800f).toInt()
    } else {
        0
    }
    setProgressBarColorDeterminate(progressBar)
}

private fun setProgressBarColorDeterminate(progressBar: ProgressBar) {
    progressBar.progressDrawable.setColorFilter(ContextCompat.getColor(progressBar.context, R.color.progress_bar_color), PorterDuff.Mode.SRC_IN)
}

@BindingAdapter("setWebsiteLinkText")
fun setWebsiteLinkText(textView: TextView, websiteLink: String?) {
    if (!websiteLink.isNullOrEmpty()) {
        val formattedWebPageLink = if (!websiteLink.contains("http")) {
            "http://$websiteLink"
        } else {
            websiteLink
        }

        val text = "<a href='$formattedWebPageLink'>Visit Website</a>"
        textView.text = Html.fromHtml(text)
        textView.visibility = View.VISIBLE
        textView.movementMethod = LinkMovementMethod.getInstance()
    } else {
        textView.visibility = View.GONE
    }
}
