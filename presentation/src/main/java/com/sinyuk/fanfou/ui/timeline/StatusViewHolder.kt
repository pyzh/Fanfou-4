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

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.chad.library.adapter.base.BaseViewHolder
import com.sinyuk.fanfou.R
import com.sinyuk.fanfou.base.AbstractActivity
import com.sinyuk.fanfou.domain.DO.Status
import com.sinyuk.fanfou.glide.GlideRequests
import com.sinyuk.fanfou.ui.player.PlayerView
import com.sinyuk.fanfou.util.FanfouFormatter
import kotlinx.android.synthetic.main.timeline_view_list_item.view.*

/**
 * Created by sinyuk on 2017/12/18.
 *
 * A RecyclerView ViewHolder that displays a status.
 */
class StatusViewHolder(private val view: View, private val glide: GlideRequests, private val uniqueId: String?) : BaseViewHolder(view) {
    fun bind(status: Status) {
        view.swipeLayout.isRightSwipeEnabled = true
        view.swipeLayout.isClickToClose = true
        glide.asDrawable().load(status.playerExtracts?.profileImageUrl).avatar().transition(withCrossFade()).into(view.avatar)

        when (status.playerExtracts?.uniqueId) {
            null -> view.avatar.setOnClickListener(null)
            uniqueId -> view.avatar.setOnClickListener { }
            else -> view.avatar.setOnClickListener { (view.context as AbstractActivity).start(PlayerView.newInstance(uniqueId = status.playerExtracts!!.uniqueId)) }
        }

        view.screenName.background = null
        view.createdAt.background = null
        view.content.background = null
        view.screenName.text = status.playerExtracts?.screenName
        if (status.createdAt == null) {
            view.createdAt.text = null
        } else {
            view.createdAt.text = FanfouFormatter.convertDateToStr(status.createdAt!!)
        }

        val url = when {
            status.photos?.largeurl != null -> status.photos?.largeurl
            status.photos?.thumburl != null -> status.photos?.thumburl
            else -> status.photos?.imageurl
        }

        if (url == null) {
            view.image.visibility = View.GONE
            glide.clear(view.image)
        } else {
            view.image.visibility = View.VISIBLE
            glide.load(url).illustrationThumb().into(view.image)
        }

        view.content.text = status.text
    }


    fun clear() {
        view.swipeLayout.isRightSwipeEnabled = false
        view.avatar.setOnClickListener(null)
        view.screenName.setBackgroundColor(ContextCompat.getColor(view.context, R.color.textColorHint))
        view.createdAt.setBackgroundColor(ContextCompat.getColor(view.context, R.color.textColorHint))
        view.content.setBackgroundColor(ContextCompat.getColor(view.context, R.color.textColorHint))
        view.screenName.text = null
        view.createdAt.text = null
        view.content.text = null
        glide.clear(view.image)
        glide.clear(view.avatar)
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests, uniqueId: String?): StatusViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.timeline_view_list_item, parent, false)
            return StatusViewHolder(view, glide, uniqueId)
        }
    }

}