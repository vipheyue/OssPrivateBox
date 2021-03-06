package com.example.heyue.ossprivatebox.browser

import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult
import com.example.heyue.ossprivatebox.oss.DataSource

class BrowserPresent(var dataRepository: DataSource, val universalView: BrowserContract.View) : BrowserContract.Presenter {
    override fun getConstrainedUrl(fileName: String): String {
        return dataRepository.getConstrainedUrl(fileName)
    }

    override fun getCurrentFilesAndDirs(prefix: String) {
        dataRepository.getCurrentFilesAndDirs(prefix, object : OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> {
            override fun onSuccess(request: ListObjectsRequest, result: ListObjectsResult) {
                Log.d("AyncListObjects", "Success!")
                var arrayList = ArrayList<MultipleItem>()

                for (objectSummary in result.objectSummaries) {
                    var pngFilter = objectSummary.key.endsWith("png",true)
                    var jpgFilter = objectSummary.key.endsWith("jpg",true)
                    var jpegFilter = objectSummary.key.endsWith("jpeg",true)
                    if (pngFilter||jpgFilter||jpegFilter) {
                        arrayList.add(MultipleItem(objectSummary.key, MultipleItem.IMG))
                        continue
                    }

                    var mp4Filter = objectSummary.key.endsWith("mp4",true)
                    var movieFilter = objectSummary.key.endsWith("MOV",true)
                    if (mp4Filter || movieFilter) {
                        arrayList.add(MultipleItem(objectSummary.key, MultipleItem.MOVIE))
                        continue
                    }

                    var mp3Filter = objectSummary.key.endsWith("mp3",true)
                    var m4aFilter = objectSummary.key.endsWith("m4a",true)
                    if (mp3Filter || m4aFilter) {
                        arrayList.add(MultipleItem(objectSummary.key, MultipleItem.MUSIC))
                        continue
                    }

                    arrayList.add(MultipleItem(objectSummary.key, MultipleItem.OTHER))
                }
                for (commonPrefix in result.commonPrefixes) {
                    arrayList.add(MultipleItem(commonPrefix, MultipleItem.DIR))
                }
                universalView.loadData(arrayList)
            }

            override fun onFailure(request: ListObjectsRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // 请求异常
                clientExcepion?.printStackTrace()
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.errorCode)
                    Log.e("RequestId", serviceException.requestId)
                    Log.e("HostId", serviceException.hostId)
                    Log.e("RawMessage", serviceException.rawMessage)
                }
            }
        })
    }

    override fun downFile(fileName: String, progressListener: OSSProgressCallback<GetObjectRequest>, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>) {
        dataRepository.downFile(fileName, progressListener, callback)
    }

    override fun downPic(fileName: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>) {
        dataRepository.downPic(fileName, callback)
    }

    init {
        universalView.presenter = this
    }

    override fun start() {
    }


}
