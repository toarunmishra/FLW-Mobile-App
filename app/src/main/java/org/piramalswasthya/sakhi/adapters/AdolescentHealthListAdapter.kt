package org.piramalswasthya.sakhi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.piramalswasthya.sakhi.databinding.RvItemAdolscentHealthListBinding
import org.piramalswasthya.sakhi.model.AdolescentHealthDomain


class AdolescentHealthListAdapter(
    private val clickListener: BenClickListener? = null,
    private val showBeneficiaries: Boolean = false,
    private val showRegistrationDate: Boolean = false,
    private val showSyncIcon: Boolean = false,
    private val showAbha: Boolean = false,
    private val role: Int? = 0
) :
    ListAdapter<AdolescentHealthDomain, AdolescentHealthListAdapter.BenViewHolder>(BenDiffUtilCallBack) {
    private object BenDiffUtilCallBack : DiffUtil.ItemCallback<AdolescentHealthDomain>() {
        override fun areItemsTheSame(
            oldItem: AdolescentHealthDomain, newItem: AdolescentHealthDomain
        ) = oldItem.benId == newItem.benId

        override fun areContentsTheSame(
            oldItem: AdolescentHealthDomain, newItem: AdolescentHealthDomain
        ) = oldItem == newItem

    }

    class BenViewHolder private constructor(private val binding: RvItemAdolscentHealthListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): BenViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvItemAdolscentHealthListBinding.inflate(layoutInflater, parent, false)
                return BenViewHolder(binding)
            }
        }

        fun bind(
            item: AdolescentHealthDomain,
            clickListener: BenClickListener?,
            showAbha: Boolean,
            showSyncIcon: Boolean,
            showRegistrationDate: Boolean,
            showBeneficiaries: Boolean, role: Int?
        ) {
            if (!showSyncIcon) item.syncState = null
            binding.ben = item
            binding.clickListener = clickListener
            binding.showAbha = showAbha


            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = BenViewHolder.from(parent)

    override fun onBindViewHolder(holder: BenViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            clickListener,
            showAbha,
            showSyncIcon,
            showRegistrationDate,
            showBeneficiaries,
            role
        )
    }


    class BenClickListener(
        private val clickedBen: (benId: Long) -> Unit,
    ) {
        fun onClickedBen(item: AdolescentHealthDomain) = clickedBen(
            item.benId!!.toLong(),
        )


    }

}