package com.indra.stackoverflowquestions.dagger

import com.indra.stackoverflowquestions.model.RetrofitBuilder
import com.indra.stackoverflowquestions.view.QuestionsListActivity
import dagger.Component


@Component(modules = [RetrofitBuilder::class])
interface AppComponent {
    // Application component for Dagger. List of activities that would request for injection
    fun inject(activity: QuestionsListActivity)
}