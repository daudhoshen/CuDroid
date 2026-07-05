package com.ismail.cu.presentation.notice.noticelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismail.cu.R
import com.ismail.cu.databinding.ItemNoticeListBinding
import com.ismail.cu.model.NoticeData
import com.ismail.cu.presentation.notice.noticelist.NoticeListAdapter.NoticeViewHolder
import com.ismail.cu.util.makeVisible

class NoticeListAdapter(
    private val onClickListener: ((NoticeData?) -> Unit)?,
    private val onUpdateClickListener: ((NoticeData?) -> Unit)?,
) : RecyclerView.Adapter<NoticeViewHolder>() {
    var isAdmin: Boolean? = null
    private var noticeList: List<NoticeData?>? = null

    fun submitList(noticeList: List<NoticeData?>?) {
        this.noticeList = noticeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            ItemNoticeListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList?.get(position))
    }

    override fun getItemCount(): Int = noticeList?.size ?: 0

    inner class NoticeViewHolder(
        private val binding: ItemNoticeListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noticeData: NoticeData?) {
            binding.noticeTitle.text = noticeData?.title ?: ""
            binding.noticeDetails.text = noticeData?.details ?: ""
            binding.createdAt.text = String.format("Created At: %s", noticeData?.createdAt ?: "")

            if (onClickListener != null) {
                itemView.setOnClickListener {
                    onClickListener.invoke(noticeData)
                }
            } else {
                binding.getRoot().isClickable = false
                binding.root.setBackgroundResource(R.drawable.edit_text_rounded_primary_border_bg)
            }

            if (isAdmin == true) {
                binding.updateDelete.root.makeVisible()
                binding.updateDelete.updateButton.setOnClickListener {
                    onUpdateClickListener?.invoke(noticeData)
                }
            }
        }
    }
}
