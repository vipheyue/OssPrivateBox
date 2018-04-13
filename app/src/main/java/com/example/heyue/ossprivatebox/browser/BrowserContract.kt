/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.heyue.ossprivatebox.browser

import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult
import com.example.android.architecture.blueprints.todoapp.BasePresenter
import com.example.android.architecture.blueprints.todoapp.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface BrowserContract {

    interface View : BaseView<Presenter> {
        fun loadData(mData: List<MultipleItem>)

    }

    interface Presenter : BasePresenter {
        fun getCurrentFilesAndDirs(prefix: String)
        fun downFile(fileName: String, progressListener: OSSProgressCallback<GetObjectRequest>, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>)
        fun downPic(fileName: String,  callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>)
    }

}
