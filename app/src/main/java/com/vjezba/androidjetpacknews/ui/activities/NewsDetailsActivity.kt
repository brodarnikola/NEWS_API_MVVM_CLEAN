package com.vjezba.androidjetpacknews.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.vjezba.androidjetpacknews.R
import com.vjezba.androidjetpacknews.ui.adapters.NewsDetailsRecyclerViewAdapter
import com.vjezba.domain.model.Articles
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity(){

    var position = 0

    private var dataFetched = false

    private var newDetailsRecyclerViewAdapter: NewsDetailsRecyclerViewAdapter =
        NewsDetailsRecyclerViewAdapter(
            mutableListOf(),
        this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
    }

    override fun onStart() {
        super.onStart()

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        position = intent.getIntExtra("listPosition", 0)

        setupRecyclerViewProperties()
        setupFragmentProperties()

        radioGroupViewSelected.setOnCheckedChangeListener { radioGroup, checkedId ->
            val radioButton: View = radioGroup.findViewById(checkedId)
            val index: Int = radioGroup.indexOfChild(radioButton)
            when (index) {
                0 -> {
                    fragmentData.visibility = View.VISIBLE
                    recylcerViewData.visibility = View.GONE
                }
                1 -> {
                    fragmentData.visibility = View.GONE
                    recylcerViewData.visibility = View.VISIBLE
                    getDataOnlyOnce()
                }
            }
        }

    }

    private fun setupRecyclerViewProperties() {
        recylcerViewData.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recylcerViewData.adapter = newDetailsRecyclerViewAdapter

        PagerSnapHelper().attachToRecyclerView(recylcerViewData)
        //recylcerViewData.addItemDecoration(PagerNewsDetailsDecorator())
    }

    private fun setupFragmentProperties() {
        /*val fragment =
            IntroViewPagerFragment()
        val bundle = Bundle()
        bundle.putInt("listPosition",  position)
        fragment.setArguments(bundle)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentData, fragment).commit()

        fragmentData.visibility = View.GONE*/
    }

    private fun getDataOnlyOnce() {
        //if( !dataFetched )
            //newsPresenter.loadNewsFromRoom()
    }


    /*override fun displayNewsDetails(newsDetails: List<Articles>) {
        dataFetched = true

        newDetailsRecyclerViewAdapter.updateDevices(newsDetails.toMutableList())
        recylcerViewData.scrollToPosition(position)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }*/



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}