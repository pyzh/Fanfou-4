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

package com.sinyuk.fanfou.domain.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by sinyuk on 2017/12/2.
 */
@Entity(tableName = "player_like",
        indices = arrayOf(Index("playerId"),Index("statusId")),
        foreignKeys = arrayOf(
                ForeignKey(onDelete = ForeignKey.CASCADE, entity = Player::class, parentColumns = arrayOf("uniqueId"), childColumns = arrayOf("playerId")),
                ForeignKey(onDelete = ForeignKey.CASCADE, entity = Status::class, parentColumns = arrayOf("id"), childColumns = arrayOf("statusId"))))
data class PlayerAndLike constructor(
        var playerId: String = "",
        var statusId: String = "",
        @NonNull @PrimaryKey var pk: String = "")