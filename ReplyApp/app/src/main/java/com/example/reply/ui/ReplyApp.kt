/*
 * Copyright (C) 2023 The Android Open Source Project
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
package com.example.reply.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.ui.utils.ReplyNavigationType
import com.example.reply.ui.utils.rememberIsFoldableDevice
import com.example.reply.ui.utils.rememberIsUnfoldedFoldable

@Composable
fun ReplyApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {
    val viewModel: ReplyViewModel = viewModel()
    val replyUiState = viewModel.uiState.collectAsState().value
    val isFoldable = rememberIsFoldableDevice()
    val isUnfolded = rememberIsUnfoldedFoldable()
    val smallestDp = LocalConfiguration.current.smallestScreenWidthDp
    val isTablet = !isFoldable && smallestDp >= 600

    val navigationType: ReplyNavigationType = when {
        isFoldable && isUnfolded -> ReplyNavigationType.NAVIGATION_RAIL
        isTablet -> ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> ReplyNavigationType.BOTTOM_NAVIGATION
    }


    ReplyHomeScreen(
        navigationType = navigationType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}
