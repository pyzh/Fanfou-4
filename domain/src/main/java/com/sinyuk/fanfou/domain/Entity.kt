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

package com.sinyuk.fanfou.domain

/**
 * Created by sinyuk on 2017/12/23.
 *
 */
const val STATUS_HOME_FLAG = 0x00000001
const val STATUS_PUBLIC_FLAG = 0x00000010
const val STATUS_CONTEXT_FLAG = 0x00000100
const val STATUS_USER_FLAG = 0x00001000
const val STATUS_MESSAGE_FLAG = 0x00010000
const val STATUS_FAVOR_FLAG = 0x00100000
const val STATUS_DRAFT_FLAG = 0x01000000
const val STATUS_PHOTO_FLAG = 0x10000000

fun convertPathToFlag(path: String): Int = when (path) {
    TIMELINE_HOME -> STATUS_HOME_FLAG
    TIMELINE_PUBLIC -> STATUS_PUBLIC_FLAG
    TIMELINE_CONTEXT -> STATUS_CONTEXT_FLAG
    TIMELINE_USER -> STATUS_USER_FLAG
    TIMELINE_REPLIES, TIMELINE_MENTIONS -> STATUS_MESSAGE_FLAG
    TIMELINE_FAVORITES -> STATUS_FAVOR_FLAG
    TIMELINE_PHOTO -> STATUS_PHOTO_FLAG
    TIMELINE_DRAFT -> STATUS_DRAFT_FLAG
    else -> NO_FLAG
}

const val NO_FLAG = 0x00000000

const val PLAYER_USER_FLAG = 0x00000001
const val PLAYER_FOLLOWER_FLAG = 0x00000010
const val PLAYER_FRIEND_FLAG = 0x00000100

fun convertPlayerPathToFlag(path: String): Int = when (path) {
    USERS_FRIENDS -> PLAYER_FRIEND_FLAG
    USERS_FOLLOWERS -> PLAYER_FOLLOWER_FLAG
    USERS_ADMIN -> PLAYER_USER_FLAG
    else -> NO_FLAG
}