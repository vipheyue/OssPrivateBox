package com.example.heyue.ossprivatebox.oss

import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult

interface DataSource {

    fun queryFileExist(name: String): Boolean?
    fun getAllFiles(): List<String>
    fun getCurrentFilesAndDirs(prefix: String, callback: OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>)
    fun downFile(fileName: String, progressListener: OSSProgressCallback<GetObjectRequest>, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>)
    fun downPic(fileName: String,  callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>)


}
