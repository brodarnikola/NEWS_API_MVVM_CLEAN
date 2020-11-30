/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vjezba.androidjetpacknews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vjezba.androidjetpacknews.databinding.FragmentRepositoriesDetailsBinding


class RepositoriesRxJava2DetailsFragment : Fragment() {

    private val args: RepositoriesRxJava2DetailsFragmentArgs by navArgs()

    private var repositoryName: TextView? = null
    private var lastUpdateTime: TextView? = null
    private var ownerNameValue: TextView? = null
    private var repositoryDescription: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRepositoriesDetailsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        repositoryName = binding.repositoryName
        lastUpdateTime = binding.lastUpdateTimeValue
        ownerNameValue = binding.ownerNameValue
        repositoryDescription = binding.repositoryDescription

        setDetailsAboutLanguage()

        return binding.root
    }

    private fun setDetailsAboutLanguage() {
        repositoryName?.text = "" + args.repositoryName
        lastUpdateTime?.text = "" + args.lastUpdateTime
        ownerNameValue?.text = "" + args.ownerName
        if( args.description != "null" )
            repositoryDescription?.text = "" + args.description
        else
            repositoryDescription?.text = "This repository does not have any description."
    }

}
