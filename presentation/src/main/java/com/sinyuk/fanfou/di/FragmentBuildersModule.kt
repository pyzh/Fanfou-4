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

package com.sinyuk.fanfou.di

import com.sinyuk.fanfou.ui.account.AccountListView
import com.sinyuk.fanfou.ui.account.AccountManageView
import com.sinyuk.fanfou.ui.account.SignInView
import com.sinyuk.fanfou.ui.drawer.DrawerView
import com.sinyuk.fanfou.ui.editor.EditorView
import com.sinyuk.fanfou.ui.editor.MentionListView
import com.sinyuk.fanfou.ui.home.IndexView
import com.sinyuk.fanfou.ui.message.MessageView
import com.sinyuk.fanfou.ui.photo.PhotoDetailsView
import com.sinyuk.fanfou.ui.photo.PhotoGridView
import com.sinyuk.fanfou.ui.player.*
import com.sinyuk.fanfou.ui.search.*
import com.sinyuk.fanfou.ui.status.StatusView
import com.sinyuk.fanfou.ui.timeline.TimelineView
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by sinyuk on 2017/11/28.
 *
 */
@Module
abstract class FragmentBuildersModule {

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun signInView(): SignInView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun playerView(): PlayerView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun messageView(): MessageView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun seachView(): SearchView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun timelineView(): TimelineView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun navigationView(): NavigationView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun suggestionView(): SuggestionView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun historyView(): HistoryView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun historyManagerView(): HistoryManagerView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun searchResultView(): SearchResultView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun trendingView(): TrendingView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun playerListView(): PlayerListView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun followerView(): FriendsView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun followingView(): FollowingView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun statusView(): StatusView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun editorView(): EditorView


    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun mentionListView(): MentionListView


    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun indexView(): IndexView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun drawerView(): DrawerView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun accountListView(): AccountListView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun accountManageView(): AccountManageView


    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun photoGridView(): PhotoGridView

    @Suppress("unused")
    @ContributesAndroidInjector
    abstract fun photoDetailsView(): PhotoDetailsView

}