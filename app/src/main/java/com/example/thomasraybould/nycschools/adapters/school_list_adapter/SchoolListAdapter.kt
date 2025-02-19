package com.example.thomasraybould.nycschools.adapters.school_list_adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.databinding.BoroughListItemBinding
import com.example.thomasraybould.nycschools.databinding.SatScoreListItemBinding
import com.example.thomasraybould.nycschools.databinding.SchoolListItemBinding
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

class SchoolListAdapter(
    private val listener: OnNycListItemSelectedListener,
    private val context: Context,
    private val linearLayoutManager: LinearLayoutManager
) : RecyclerView.Adapter<SchoolListAdapter.ViewHolder>() {
    private val nycListItems: MutableList<NycListItem> = mutableListOf()

    private enum class LoadingPayLoad {
        LOADING_PAY_LOAD
    }

    fun updateList(newSchoolListItems: List<NycListItem>) {
        if (this.nycListItems.isEmpty()) {
            this.nycListItems.addAll(newSchoolListItems)
            this.notifyDataSetChanged()
            return
        }
        removeItemsAsNeeded(newSchoolListItems)
        addItemsAsNeeded(newSchoolListItems)

        nycListItems.forEachIndexed { index, schoolListItemUiModel ->
            if (schoolListItemUiModel is NycListItem.BoroughItemUiModel) {
                newSchoolListItems.forEach { newSchoolListItem ->
                    if (schoolListItemUiModel == newSchoolListItem) {
                        (newSchoolListItem as? NycListItem.SchoolItemUiModel)?.let {
                            schoolListItemUiModel.isLoading = it.isLoading
                            notifyItemChanged(index, LoadingPayLoad.LOADING_PAY_LOAD)
                        }
                    }
                }
            }
        }
    }

    private fun addItemsAsNeeded(newSchoolListItems: List<NycListItem>) {
        val sizeDiff = newSchoolListItems.size - nycListItems.size
        if (sizeDiff <= 0) return

        newSchoolListItems.forEachIndexed { index, schoolListItemUiModel ->
            if (index > nycListItems.lastIndex || nycListItems[index] != schoolListItemUiModel) {
                nycListItems.clear()
                nycListItems.addAll(newSchoolListItems)
                notifyItemRangeInserted(index, sizeDiff)
                linearLayoutManager.scrollToPositionWithOffset(index - 1, 0)
                return
            }
        }

    }

    private fun removeItemsAsNeeded(schoolListItems: List<NycListItem>) {
        for (i in this.nycListItems.size - 1 downTo 0) {
            if (!schoolListItems.contains(nycListItems[i])) {
                nycListItems.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            1 -> {
                val itemBinding = BoroughListItemBinding.inflate(layoutInflater, parent, false)
                ViewHolder(itemBinding)
            }

            2 -> {
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
            val schoolListItemUiModel = nycListItems[position]
            if (schoolListItemUiModel is NycListItem.BoroughItemUiModel) {
                holder.boroughListItemBinding?.uiModel = schoolListItemUiModel
            }
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schoolListItemUiModel = nycListItems[position]
        when {
            schoolListItemUiModel is NycListItem.BoroughItemUiModel -> holder.bindBorough(
                schoolListItemUiModel,
                context,
                listener
            )

            schoolListItemUiModel is NycListItem.SatScoreDataUiModel -> holder.satScoreListItemBinding?.uiModel =
                schoolListItemUiModel

            schoolListItemUiModel is NycListItem.SchoolItemUiModel -> holder.bindSchool(
                schoolListItemUiModel,
                listener
            )
        }
    }

    override fun getItemCount(): Int {
        return nycListItems.size
    }

    override fun getItemViewType(position: Int): Int {
        val nycListItem = nycListItems[position]
        return if (nycListItem is NycListItem.BoroughItemUiModel) {
            1
        } else if (nycListItem is NycListItem.SatScoreDataUiModel) {
            2
        } else {
            3
        }
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

        constructor(satScoreListItemBinding: SatScoreListItemBinding) : super(
            satScoreListItemBinding.root
        ) {
            this.satScoreListItemBinding = satScoreListItemBinding
        }

        fun bindSchool(
            schoolListItemUiModel: NycListItem.SchoolItemUiModel,
            listener: OnNycListItemSelectedListener
        ) {
            schoolListItemBinding?.uiModel = schoolListItemUiModel
            itemView.setOnClickListener { v ->
                listener.onNycListItemSelected(schoolListItemUiModel)
                schoolListItemUiModel.isSelected = !schoolListItemUiModel.isSelected
            }
        }

        fun bindBorough(
            schoolListItemUiModel: NycListItem.BoroughItemUiModel,
            context: Context,
            listener: OnNycListItemSelectedListener
        ) {
            boroughListItemBinding?.uiModel = schoolListItemUiModel

            boroughListItemBinding?.root?.setOnClickListener { v ->
                listener.onNycListItemSelected(schoolListItemUiModel)
                schoolListItemUiModel.isSelected = !schoolListItemUiModel.isSelected
            }

            boroughListItemBinding?.progressBar?.indeterminateDrawable?.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.progress_bar_color
                ), PorterDuff.Mode.SRC_IN
            )
        }

    }

}
