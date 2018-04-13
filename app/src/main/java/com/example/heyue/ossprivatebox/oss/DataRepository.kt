package com.example.heyue.ossprivatebox.oss

import android.content.Context
import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider
import com.alibaba.sdk.android.oss.common.utils.OSSUtils
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult


class DataRepository(internal var ctx: Context, internal var endpoint: String, internal var bucketName: String) : DataSource {
    override fun downPic(fileName: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>) {
        // 构造图片下载请求
        val get = GetObjectRequest(bucketName, fileName)
// 图片处理
        get.setxOssProcess("image/resize,m_fixed,w_100,h_100")
        val task = client.asyncGetObject(get, callback)

    }

    override fun downFile(fileName: String, progressListener: OSSProgressCallback<GetObjectRequest>, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>) {

        val get = GetObjectRequest(bucketName, fileName)
//设置下载进度回调
        get.setProgressListener(progressListener)
        val task = client.asyncGetObject(get,callback)

    }

    override fun getCurrentFilesAndDirs(prefix: String, callback: OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>) {
        val listObjects = ListObjectsRequest(bucketName)
        // "/" 为文件夹的分隔符
        listObjects.delimiter = "/"
        // 设定前缀
        listObjects.prefix = prefix
        // 设置成功、失败回调，发送异步罗列请求
        val task = client.asyncListObjects(listObjects, callback)
        task.waitUntilFinished()
    }

    /*列出存储空间内所有文件*/
    override fun getAllFiles(): List<String> {
        val listObjects = ListObjectsRequest(bucketName)

// 设定前缀
//        listObjects.prefix = "file"

// 设置成功、失败回调，发送异步罗列请求
        val task = client.asyncListObjects(listObjects, object : OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> {
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
        task.waitUntilFinished()

        return ArrayList<String>()
    }


    override fun queryFileExist(fileName: String): Boolean? {

        var found = false
        try {
            found = client.doesObjectExist(bucketName, fileName)
        } catch (e: ClientException) {
            e.printStackTrace()
        } catch (e: ServiceException) {
            e.printStackTrace()
        }

        Log.d("TAG...", "customSign: $found")

        return found
    }


    private val client: OSSClient
        get() {
            val provider = object : OSSCustomSignerCredentialProvider() {
                override fun signContent(content: String): String {
                    return  null;
                }
            }

            return OSSClient(ctx, endpoint, provider)
        }
}
