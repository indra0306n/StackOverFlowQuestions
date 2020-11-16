package com.indra.stackoverflowquestions.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.indra.stackoverflowquestions.R
import com.indra.stackoverflowquestions.data.QuestionInfo
import com.indra.stackoverflowquestions.databinding.ActivityMainBinding
import com.indra.stackoverflowquestions.util.Constants.Companion.INTENT_BUNDLE_WEBVIEW
import com.indra.stackoverflowquestions.MyApplication
import com.indra.stackoverflowquestions.util.Status
import com.indra.stackoverflowquestions.util.Utils.Companion.isInternetConnected
import com.indra.stackoverflowquestions.view.adapters.QuestionsListAdapter
import com.indra.stackoverflowquestions.viewmodel.QuestionsListViewModel
import com.indra.stackoverflowquestions.viewmodel.RecyclerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class QuestionsListActivity : AppCompatActivity(), QuestionsListAdapter.IClickListener {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var questionsListViewModel: QuestionsListViewModel
    private var paginationCount = 1
    private val filteredList: ArrayList<QuestionInfo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateBinding()

        checkNetworkForAPI()    // Checks if network is available and hit API accordingly
    }

    private fun updateBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvQuestion.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.rvQuestion.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.HORIZONTAL
            )
        )
        binding.viewModel = questionsListViewModel
    }

    private fun checkNetworkForAPI() {
        if (isInternetConnected(this)) {
            getQuestionsList()
        } else {
            showNetworkAlert()
        }
    }

    private fun showNetworkAlert() {
        AlertDialog.Builder(this).setTitle(R.string.no_internet)
            .setMessage(R.string.message_no_internet)
            .setPositiveButton(R.string.button_retry) { _, _ ->
                checkNetworkForAPI()
            }
            .show()
    }

    private fun getQuestionsList() {
        questionsListViewModel.getQuestionsFromAPI(paginationCount).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    rv_Question.visibility = View.VISIBLE
                    it.data?.let { questionsList ->
                        filteredList.addAll(questionsListViewModel.applyFilter(questionsList))

                        if (filteredList.size < 20) {
                            paginationCount++
                            getQuestionsList()
                        } else {
                            pg_Loader.visibility = View.GONE
                            questionsListViewModel.notifyAdapter(
                                filteredList,
                                this
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, R.string.error_common, Toast.LENGTH_LONG).show()
                    rv_Question.visibility = View.VISIBLE
                    pg_Loader.visibility = View.GONE
                }
                Status.LOADING -> {
                    pg_Loader.visibility = View.VISIBLE
                    rv_Question.visibility = View.GONE
                }
            }

        })
    }

    override fun itemClickAction(recyclerViewModel: RecyclerViewModel) {
        if (isInternetConnected(this)) {
            val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
            intent.putExtra(INTENT_BUNDLE_WEBVIEW, recyclerViewModel.getQuestionURL().value)
            startActivity(intent)
        } else {
            showNetworkAlert()
        }
    }
}