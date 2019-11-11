package com.example.thomasraybould.nycschools.adapters.school_list_adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.*
import com.example.thomasraybould.nycschools.databinding.BoroughListItemBinding
import com.example.thomasraybould.nycschools.databinding.SatScoreListItemBinding
import com.example.thomasraybould.nycschools.databinding.SchoolListItemBinding
import com.example.thomasraybould.nycschools.entities.Borough
import java.util.*

class SchoolListAdapter constructor(private val listener: OnSchoolListItemSelectedListener, private val context: Context) : RecyclerView.Adapter<SchoolListAdapter.ViewHolder>() {
    private val schoolListItemUiModels: MutableList<SchoolListItemUiModel> = ArrayList()

    val currentList: List<SchoolListItemUiModel>
        get() = schoolListItemUiModels

    private enum class LoadingPayLoad {
        LOADING_PAY_LOAD
    }

    fun updateList(newSchoolListItems: List<SchoolListItemUiModel>) {
        if (this.schoolListItemUiModels.isEmpty()) {
            this.schoolListItemUiModels.addAll(newSchoolListItems)
            this.notifyDataSetChanged()
            return
        }
        removeItemsAsNeeded(newSchoolListItems)
        addItemsAsNeeded(newSchoolListItems)
    }

    private fun addItemsAsNeeded(newSchoolListItems: List<SchoolListItemUiModel>) {
        val sizeDiff = newSchoolListItems.size - schoolListItemUiModels.size
        if (sizeDiff <= 0) return

        newSchoolListItems.forEachIndexed { index, schoolListItemUiModel ->
            if (index > schoolListItemUiModels.lastIndex || schoolListItemUiModels[index] != schoolListItemUiModel) {
                schoolListItemUiModels.clear()
                schoolListItemUiModels.addAll(newSchoolListItems)
                notifyItemRangeInserted(index, sizeDiff)
                return
            }
        }
    }

    private fun removeItemsAsNeeded(schoolListItems: List<SchoolListItemUiModel>) {
        for (i in this.schoolListItemUiModels.size - 1 downTo 0) {
            if (!schoolListItems.contains(schoolListItemUiModels[i])) {
                schoolListItemUiModels.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }


    fun changeLoadingStatusOfBorough(borough: Borough, isLoading: Boolean) {
        for (i in schoolListItemUiModels.indices) {
            val schoolListItemUiModel = schoolListItemUiModels[i]
            if (schoolListItemUiModel.type == BOROUGH_TITLE && schoolListItemUiModel.borough == borough) {
                schoolListItemUiModel.isLoading = isLoading
                notifyItemChanged(i, LoadingPayLoad.LOADING_PAY_LOAD)
                break
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            BOROUGH_TITLE.ordinal -> {
                val itemBinding = BoroughListItemBinding.inflate(layoutInflater, parent, false)
                ViewHolder(itemBinding)
            }
            SAT_SCORE_ITEM.ordinal -> {
                val itemBinding = SatScoreListItemBinding.inflate(layoutInflater, parent, false)
                ViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = SchoolListItemBinding.inflate(layoutInflater, parent, false)
                ViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (!payloads.isEmpty() && payloads[0] === LoadingPayLoad.LOADING_PAY_LOAD) {
            val schoolListItemUiModel = schoolListItemUiModels[position]
            if (schoolListItemUiModel.type == BOROUGH_TITLE) {
                holder.boroughListItemBinding?.uiModel = schoolListItemUiModel
            }
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schoolListItemUiModel = schoolListItemUiModels[position]
        when {
            schoolListItemUiModel.type == BOROUGH_TITLE -> holder.bindBorough(schoolListItemUiModel, context, listener)
            schoolListItemUiModel.type == SAT_SCORE_ITEM -> holder.satScoreListItemBinding?.uiModel = schoolListItemUiModel
            else -> holder.bindSchool(schoolListItemUiModel, listener)
        }
    }

    override fun getItemCount(): Int {
        return schoolListItemUiModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return schoolListItemUiModels[position].type.ordinal
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var boroughListItemBinding: BoroughListItemBinding? = null
        var schoolListItemBinding: SchoolListItemBinding? = null
        var satScoreListItemBinding: SatScoreListItemBinding? = null

        constructor(boroughListItemBinding: BoroughListItemBinding) : super(boroughListItemBinding.root) {
            this.boroughListItemBinding = boroughListItemBinding
        }

        constructor(schoolListItemBinding: SchoolListItemBinding) : super(schoolListItemBinding.root) {
            this.schoolListItemBinding = schoolListItemBinding
        }

        constructor(satScoreListItemBinding: SatScoreListItemBinding) : super(satScoreListItemBinding.root) {
            this.satScoreListItemBinding = satScoreListItemBinding
        }

        fun bindSchool(schoolListItemUiModel: SchoolListItemUiModel, listener: OnSchoolListItemSelectedListener) {
            schoolListItemBinding?.uiModel = schoolListItemUiModel
            itemView.setOnClickListener { v ->
                listener.onSchoolListItemSelected(schoolListItemUiModel)
                schoolListItemUiModel.isSelected = !schoolListItemUiModel.isSelected
            }
        }

        fun bindBorough(schoolListItemUiModel: SchoolListItemUiModel, context: Context, listener: OnSchoolListItemSelectedListener) {
            boroughListItemBinding?.uiModel = schoolListItemUiModel

            boroughListItemBinding?.root?.setOnClickListener { v ->
                listener.onSchoolListItemSelected(schoolListItemUiModel)
                schoolListItemUiModel.isSelected = !schoolListItemUiModel.isSelected
            }

            boroughListItemBinding?.progressBar?.indeterminateDrawable?.setColorFilter(ContextCompat.getColor(context, R.color.progress_bar_color), PorterDuff.Mode.SRC_IN)
        }

    }

    companion object {

        fun createSchoolListAdapter(listener: OnSchoolListItemSelectedListener, context: Context): SchoolListAdapter {
            return SchoolListAdapter(listener, context)
        }
    }

}
