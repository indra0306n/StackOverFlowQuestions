package com.indra.stackoverflowquestions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.indra.stackoverflowquestions.data.QuestionInfo
import com.indra.stackoverflowquestions.util.Utils.Companion.getDate

class RecyclerViewModel : ViewModel() {
    private var stackoverflow_question = MutableLiveData<String>()
    private var stackoverflow_createdBy = MutableLiveData<String>()
    private var stackoverflow_questionURL = MutableLiveData<String>()
    private var stackoverflow_creationDate = MutableLiveData<Long>()
    var answersCount = MutableLiveData<Int>()

    fun bind(questionInfo: QuestionInfo) {
        stackoverflow_question.value = questionInfo.title
        stackoverflow_createdBy.value = questionInfo.owner.display_name
        stackoverflow_questionURL.value = questionInfo.link
        stackoverflow_creationDate.value = questionInfo.creation_date
        answersCount.value = questionInfo.answer_count
    }

    fun getQuestionTitle(): MutableLiveData<String> {
        return stackoverflow_question
    }

    fun getCreatedBy(): MutableLiveData<String> {
        return stackoverflow_createdBy
    }

    fun getCreatedDate(): String? {
        return stackoverflow_creationDate.value?.let { getDate(it, "MM/dd/yyyy") }
    }

    fun getQuestionURL(): MutableLiveData<String> {
        return stackoverflow_questionURL
    }
}