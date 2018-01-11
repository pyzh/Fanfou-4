/*
 *
 *  * Apache License
 *  *
 *  * Copyright [2017] Sinyuk
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.sinyuk.fanfou.ui.timeline

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.sinyuk.fanfou.R
import com.sinyuk.fanfou.base.AbstractFragment
import com.sinyuk.fanfou.di.Injectable
import com.sinyuk.fanfou.domain.DO.Status
import com.sinyuk.fanfou.domain.NetworkState
import com.sinyuk.fanfou.domain.PAGE_SIZE
import com.sinyuk.fanfou.domain.TIMELINE_PUBLIC
import com.sinyuk.fanfou.ui.MarginDecoration
import com.sinyuk.fanfou.util.obtainViewModel
import com.sinyuk.fanfou.util.obtainViewModelFromActivity
import com.sinyuk.fanfou.viewmodel.AccountViewModel
import com.sinyuk.fanfou.viewmodel.FanfouViewModelFactory
import com.sinyuk.fanfou.viewmodel.TimelineViewModel
import com.sinyuk.myutils.system.ToastUtils
import kotlinx.android.synthetic.main.timeline_view.*
import kotlinx.android.synthetic.main.timeline_view_list_header_public.view.*
import javax.inject.Inject


/**
 * Created by sinyuk on 2017/11/30.
 *
 */
class TimelineView : AbstractFragment(), Injectable {

    companion object {
        fun newInstance(path: String, uniqueId: String? = null) = TimelineView().apply {
            arguments = Bundle().apply {
                putString("path", path)
                putString("uniqueId", uniqueId)
            }
        }
    }

    override fun layoutId(): Int? = R.layout.timeline_view

    @Inject lateinit var factory: FanfouViewModelFactory

    private val timelineViewModel by lazy { obtainViewModel(factory, TimelineViewModel::class.java) }

    private val accountViewModel by lazy { obtainViewModelFromActivity(factory, AccountViewModel::class.java) }

    @Inject lateinit var toast: ToastUtils

    private lateinit var timelinePath: String
    private var uniqueId: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            timelinePath = it.getString("path")
            uniqueId = it.getString("uniqueId")
        }.run {
            timelineViewModel.setParams(TimelineViewModel.PathAndPlayer(path = timelinePath, uniqueId = uniqueId))
        }

        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        timelineViewModel.refreshState.observe(this@TimelineView, Observer {
            setRefresh(it?.status == com.sinyuk.fanfou.domain.Status.RUNNING)
            if (it?.status == com.sinyuk.fanfou.domain.Status.FAILED) {
                it.msg?.let { toast.toastShort(it) }
            }
        })
    }


    private fun setRefresh(constraint: Boolean) {
        when (timelinePath) {
            TIMELINE_PUBLIC -> {
                if (constraint) {
                    publicHeader.loadingLayout.show()
                    publicHeader.viewAnimator.displayedChildId = R.id.loadingLayout
                } else {
                    publicHeader.viewAnimator.displayedChildId = R.id.refreshLayout
                    publicHeader.loadingLayout.hide()
                }
            }
            else -> {
            }
        }
    }


    private lateinit var adapter: StatusPagedListAdapter

    private lateinit var publicHeader: View

    private fun setupRecyclerView() {
        LinearLayoutManager(context).apply {
            isItemPrefetchEnabled = true
            initialPrefetchItemCount = PAGE_SIZE
            isAutoMeasureEnabled = true
            recyclerView.layoutManager = this
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(MarginDecoration(R.dimen.divider_size, false, context!!))

        adapter = StatusPagedListAdapter(this@TimelineView, { timelineViewModel.retry() }, uniqueId)

        val imageWidthPixels = resources.getDimensionPixelSize(R.dimen.timeline_illustration_size)
        val modelPreloader = StatusPagedListAdapter.StatusPreloadProvider(adapter, this, imageWidthPixels)
        val sizePreloader = FixedPreloadSizeProvider<Status>(imageWidthPixels, imageWidthPixels)
        val preloader = RecyclerViewPreloader<Status>(Glide.with(this@TimelineView), modelPreloader, sizePreloader, 10)
        recyclerView.addOnScrollListener(preloader)

        recyclerView.adapter = adapter

        when (timelinePath) {
            TIMELINE_PUBLIC -> {
                publicHeader = LayoutInflater.from(context).inflate(R.layout.timeline_view_list_header_public, recyclerView, false)
                publicHeader.title.setOnClickListener {
                    if (publicHeader.viewAnimator.displayedChildId == R.id.loadingLayout) {
                        setRefresh(false)
                    } else {
                        setRefresh(true)
                    }
                }
            }
        }

        timelineViewModel.statuses.observe(this, pagedListConsumer)
        timelineViewModel.networkState.observe(this, networkConsumer)
    }


    private val pagedListConsumer = Observer<PagedList<Status>> {
        adapter.setList(it)
    }

    private val networkConsumer = Observer<NetworkState> {
        adapter.setNetworkState(it)
    }


}