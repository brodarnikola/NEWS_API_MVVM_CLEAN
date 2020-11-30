package com.vjezba.androidjetpacknews.ui.activities

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vjezba.androidjetpacknews.R
import com.vjezba.androidjetpacknews.di.ViewModelFactory
import com.vjezba.androidjetpacknews.di.injectViewModel
import com.vjezba.androidjetpacknews.ui.adapters.NewsAdapter
import com.vjezba.androidjetpacknews.viewmodels.LanguagesActivityViewModel
import com.vjezba.domain.model.Articles
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_news.*
import javax.inject.Inject


class NewsActivity : AppCompatActivity(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidActivityInjector


    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var languagesActivityViewModel: LanguagesActivityViewModel

    private lateinit var newsAdapter: NewsAdapter
    val newsLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        languagesActivityViewModel = injectViewModel(viewModelFactory)
    }

    override fun onStart() {
        super.onStart()

        newsAdapter = NewsAdapter( mutableListOf<Articles>(),
            { position: Int -> setArticlesClickListener( position ) }  )

        news_list.apply {
            layoutManager = newsLayoutManager
            adapter = newsAdapter
        }

        news_list.adapter = newsAdapter

        languagesActivityViewModel.newsList.observe(this@NewsActivity, Observer { repos ->
            Log.d(ContentValues.TAG, "Da li ce uci sim uuuuuu: ${repos.articles.joinToString { "-" }}")
            newsAdapter.setItems(repos.articles)
//            hideOrShowRecyclerViewAndProgressBar(
//                showRecyclerView = true,
//                showProgressBar = false
//            )
            //adapter.notifyItemRangeRemoved(0, adapter.itemCount)
            //adapter.setRepos(repos.items.toMutableList())
        })

        //lifecycleScope.launch(Dispatchers.IO) {
            languagesActivityViewModel.deleteAllSavedProgrammingLanguagesOfUser()
        //}
        //io.reactivex.Observable.just(languagesActivityViewModel.deleteAllSavedProgrammingLanguagesOfUser())

    }

    private fun setArticlesClickListener( position: Int) {
//        val intent = Intent( this, NewsDetailsActivity::class.java )
//        intent.putExtra("listPosition", position)
//        startActivity(intent)
    }

}