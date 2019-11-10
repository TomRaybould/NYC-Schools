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
    private val schoolListItemUiModels : MutableList<SchoolListItemUiModel> = ArrayList()

    val currentList: List<SchoolListItemUiModel>
        get() = schoolListItemUiModels

    private enum class LoadingPayLoad {
        LOADING_PAY_LOAD
    }

    fun updateList(schoolListItems: List<SchoolListItemUiModel>) {
        if (this.schoolListItemUiModels.isEmpty()) {
            this.schoolListItemUiModels.addAll(schoolListItems)
            this.notifyDataSetChanged()
            return
        }
        removeItemsAsNeeded(schoolListItems)
    }

    private fun removeItemsAsNeeded(schoolListItems: List<SchoolListItemUiModel>) {
        for(i in this.schoolListItemUiModels.size - 1 downTo  0){
            if(!schoolListItems.contains(schoolListItemUiModels[i])){
                schoolListItemUiModels.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun addSchoolItemsForBorough(newItems: List<SchoolListItemUiModel>, borough: Borough): Int {

        //find the title for the borough and add new items underneath
        var insertTarget = -1
        for (i in schoolListItemUiModels.indices) {
            val schoolListItemUiModel = schoolListItemUiModels[i]
            if (schoolListItemUiModel.type == BOROUGH_TITLE && schoolListItemUiModel.borough == borough) {
                schoolListItemUiModel.isSelected = true
                insertTarget = i + 1
            }
        }

        if (insertTarget > -1) {
            schoolListItemUiModels.addAll(insertTarget, newItems)
            notifyItemRangeInserted(insertTarget, newItems.size)
        }

        return insertTarget
    }


    /**
     * Searching for the target school that was selected and then
     * adding a score item underneath and then returning the position
     * of the school.
     *
     * @param scoreItem
     * @return
     */
    fun addScoreItemForSchool(scoreItem: SchoolListItemUiModel): Int {
        val targetDbn = scoreItem.school.dbn

        var insertTarget = -1
        for (i in schoolListItemUiModels.indices) {
            val schoolListItemUiModel = schoolListItemUiModels[i]
            if (schoolListItemUiModel.type != SCHOOL_ITEM) {
                continue
            }
            if (targetDbn == schoolListItemUiModel.school.dbn) {
                insertTarget = i + 1
            }
        }

        if (insertTarget > -1) {
            schoolListItemUiModels.add(insertTarget, scoreItem)
            notifyItemInserted(insertTarget)
        }

        return insertTarget
    }

//    fun removeScoreItem(targetDbn: String) {
//
//        for (i in schoolListItemUiModels.indices) {
//            val schoolListItemUiModel = schoolListItemUiModels[i]
//            val school = schoolListItemUiModel.school
//
//            val schoolItemDbn: String?
//
//            schoolItemDbn = school?.dbn
//
//            if (targetDbn == schoolItemDbn) {
//                if (schoolListItemUiModel.type == SAT_SCORE_ITEM) {
//                    schoolListItemUiModels.removeAt(i)
//                    notifyItemRemoved(i)
//                    break
//                }
//
//            }
//        }
//
//    }

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

    fun removeItemsForBorough(borough: Borough) {
        //go through list in reverse to remove items
        for (i in schoolListItemUiModels.indices.reversed()) {
            val schoolListItemUiModel = schoolListItemUiModels[i]

            if (schoolListItemUiModel.type != BOROUGH_TITLE && schoolListItemUiModel.borough == borough) {
                schoolListItemUiModels.removeAt(i)
                notifyItemRemoved(i)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)

        if (viewType == BOROUGH_TITLE.ordinal) {
            val itemBinding = BoroughListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(itemBinding)
        } else if (viewType == SAT_SCORE_ITEM.ordinal) {
            val itemBinding = SatScoreListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(itemBinding)
        } else {
            val itemBinding = SchoolListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(itemBinding)
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
        if (schoolListItemUiModel.type == BOROUGH_TITLE) {
            holder.bindBorough(schoolListItemUiModel, context, listener)
        } else if (schoolListItemUiModel.type == SAT_SCORE_ITEM) {
            holder.satScoreListItemBinding?.uiModel = schoolListItemUiModel
        } else {
            holder.bindSchool(schoolListItemUiModel, listener)
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
