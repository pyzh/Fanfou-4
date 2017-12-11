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

package com.sinyuk.fanfou.domain.repo

import android.app.Application
import android.util.Log
import com.sinyuk.fanfou.domain.AppExecutors
import com.sinyuk.fanfou.domain.DATABASE_IN_MEMORY
import com.sinyuk.fanfou.domain.api.Endpoint
import com.sinyuk.fanfou.domain.api.Oauth1SigningInterceptor
import com.sinyuk.fanfou.domain.db.LocalDatabase
import com.sinyuk.fanfou.domain.vo.Player
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by sinyuk on 2017/12/8.
 */
class PlayerRepository @Inject constructor(
        val application: Application,
        url: Endpoint,
        interceptor: Oauth1SigningInterceptor,
        private val appExecutors: AppExecutors,
        @Named(DATABASE_IN_MEMORY) private val memory: LocalDatabase) : AbstractRepository(url, interceptor) {

    fun profile(uniqueId: String, forcedUpdate: Boolean = false) = object : NetworkBoundResource<Player, Player>(appExecutors) {
        override fun onFetchFailed() {
        }

        override fun saveCallResult(item: Player?) {
            item?.let { savePlayer(item) }
        }

        override fun shouldFetch(data: Player?) = /*networkConnected(application) && rateLimiter.shouldFetch(KEY) &&*/ (forcedUpdate || data == null)


        override fun loadFromDb() = memory.playerDao().query(uniqueId)


        override fun createCall() = restAPI.show_user(uniqueId)
    }.asLiveData()

    private fun savePlayer(item: Player) {
        memory.beginTransaction()
        try {
            Log.d("savePlayer", "insert: " + memory.playerDao().insert(item))
            memory.setTransactionSuccessful()
        } finally {
            memory.endTransaction()
        }
    }

}