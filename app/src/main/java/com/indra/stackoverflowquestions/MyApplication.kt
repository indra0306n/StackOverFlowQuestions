package com.indra.stackoverflowquestions

import android.app.Application
import com.indra.stackoverflowquestions.dagger.AppComponent
import com.indra.stackoverflowquestions.dagger.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}