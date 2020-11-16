package com.indra.stackoverflowquestions.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.indra.stackoverflowquestions.R
import com.indra.stackoverflowquestions.data.QuestionInfo
import com.indra.stackoverflowquestions.databinding.ItemQuestionsBinding
import com.indra.stackoverflowquestions.viewmodel.RecyclerViewModel


class QuestionsListAdapter : RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    private lateinit var mListener: IClickListener
    private lateinit var listOfQuestions: List<QuestionInfo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemQuestionsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_questions,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfQuestions[position], mListener)
    }

    override fun getItemCount(): Int {
        return if (::listOfQuestions.isInitialized) listOfQuestions.size else 0
    }

    fun updateAdapter(
        listOfQuestions: ArrayList<QuestionInfo>,
        mListener: IClickListener
    ) {
        this.listOfQuestions = listOfQuestions
        this.mListener = mListener
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = RecyclerViewModel()

        fun bind(questionInfo: QuestionInfo, listener: IClickListener) {
            viewModel.bind(questionInfo)
            binding.itemClick = listener
            binding.viewModel = viewModel
        }
    }

    interface IClickListener {
        fun itemClickAction(recyclerViewModel: RecyclerViewModel)
    }
}
