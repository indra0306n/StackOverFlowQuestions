package com.indra.stackoverflowquestions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.indra.stackoverflowquestions.data.QuestionInfo
import com.indra.stackoverflowquestions.data.APIResponse
import com.indra.stackoverflowquestions.model.Repository
import com.indra.stackoverflowquestions.util.Constants
import com.indra.stackoverflowquestions.util.Resource
import com.indra.stackoverflowquestions.view.adapters.QuestionsListAdapter
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class QuestionsListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val questionsListAdapter: QuestionsListAdapter = QuestionsListAdapter()

    fun getQuestionsFromAPI(pageNumber: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository.getQuestions(
                        pageNumber, Constants.PAGESIZE, Constants.ORDER, Constants.SORT,
                        Constants.SITE
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun notifyAdapter(
        apiResponse: ArrayList<QuestionInfo>,
        mListener: QuestionsListAdapter.IClickListener
    ) {
        questionsListAdapter.updateAdapter(apiResponse, mListener)
    }


    fun applyFilter(apiResponse: APIResponse): ArrayList<QuestionInfo> {
        val newFilteredList: ArrayList<QuestionInfo> = arrayListOf()
        apiResponse.items.forEach {
            if (it.answer_count > 1 && it.accepted_answer_id != 0) {
                newFilteredList.add(it)
            }
        }
        return newFilteredList
    }

}