package com.ismail.cu.presentation.faculty.facultylist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismail.cu.databinding.ItemFacultyListBinding
import com.ismail.cu.model.FacultyData
import com.ismail.cu.presentation.faculty.facultylist.FacultyListAdapter.FacultyViewHolder
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast

class FacultyListAdapter(
    private val onCallNowClickListener: (String?) -> Unit,
    private val onUpdateClickListener: (FacultyData?) -> Unit
) : RecyclerView.Adapter<FacultyViewHolder>() {
    var isAdmin: Boolean? = null
    private var facultyList: List<FacultyData?>? = null

    fun submitList(facultyList: List<FacultyData?>?) {
        this.facultyList = facultyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        return FacultyViewHolder(
            ItemFacultyListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        holder.bind(facultyList?.get(position))
    }

    override fun getItemCount(): Int = facultyList?.size ?: 0

    inner class FacultyViewHolder(
        private val binding: ItemFacultyListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(facultyData: FacultyData?) {
            binding.facultyName.text = facultyData?.name ?: ""
            binding.facultyPhone.text = facultyData?.phone ?: ""
            binding.facultyDesignation.text = facultyData?.details ?: ""

            binding.callNowButton.setOnClickListener {
                onCallNowClickListener(facultyData?.phone)
            }

            binding.facultyPhone.setOnClickListener {
                (itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?)?.apply {
                    setPrimaryClip(ClipData.newPlainText(facultyData?.name, facultyData?.phone))
                    itemView.context.showToast("Phone number copied")
                }
            }

            if (isAdmin == true) {
                binding.updateDelete.root.makeVisible()
                binding.updateDelete.updateButton.setOnClickListener {
                    onUpdateClickListener(facultyData)
                }
            }
        }
    }
}
