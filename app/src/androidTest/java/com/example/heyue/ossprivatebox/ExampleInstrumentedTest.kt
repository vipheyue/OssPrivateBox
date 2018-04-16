package com.example.heyue.ossprivatebox

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult
import com.example.heyue.ossprivatebox.oss.DataRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.heyue.ossprivatebox", appContext.packageName)
    }

    @Test
    fun ossClient() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var dataRepository = DataRepository(appContext, AppConfig.endpoint, AppConfig.bucketName)
//        dataRepository.queryFileExist("tantan.sh")
//        dataRepository.getAllFiles()
        var prefix = ""
        dataRepository.getCurrentFilesAndDirs(prefix, object : OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> {
            override fun onSuccess(request: ListObjectsRequest, result: ListObjectsResult) {
                Log.d("AyncListObjects", "Success!")
                for (objectSummary in result.objectSummaries) {

                }
                for (commonPrefix in result.commonPrefixes) {

                }
                for (i in 0 until result.objectSummaries.size) {
                    Log.d("AyncListObjects", "object: " + result.objectSummaries[i].key + " "
                            + result.objectSummaries[i].eTag + " "
                            + result.objectSummaries[i].lastModified)
                }
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

    @Test
    fun downFileTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var dataRepository = DataRepository(appContext, AppConfig.endpoint, AppConfig.bucketName)
        dataRepository.downFile("temporaryAPK/test-insurWallet_v1.5.3_2018-04-11.apk", object : OSSProgressCallback<GetObjectRequest> {
            override fun onProgress(request: GetObjectRequest?, currentSize: Long, totalSize: Long) {

            }

        }, object : OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
            override fun onSuccess(request: GetObjectRequest, result: GetObjectResult) {
                // 请求成功
                val inputStream = result.objectContent
                val buffer = ByteArray(2048)
                var len: Int
                try {
//                    while ((len = inputStream.read(buffer)) != -1) {
                    // 处理下载的数据
//                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(request: GetObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
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

    @Test
    fun downImgTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var dataRepository = DataRepository(appContext, AppConfig.endpoint, AppConfig.bucketName)
        dataRepository.downPic("A097D8133FD08B843BD807B4524A5E22.jpg",object : OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
            override fun onSuccess(request: GetObjectRequest, result: GetObjectResult) {
                // 请求成功
                val inputStream = result.objectContent
                val buffer = ByteArray(2048)
                var len: Int
                try {
//                    while ((len = inputStream.read(buffer)) != -1) {
                    // 处理下载的数据
//                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(request: GetObjectRequest, clientExcepion: ClientException, serviceException: ServiceException) {
                // 处理异常
            }
        })
    }

}
