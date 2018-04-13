package com.example.heyue.ossprivatebox.oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;

/**
 * Created by wangzheng on 2018/1/11.
 */

public class CustomSignSample extends BaseSamples {


    public OSSClient getClient(Context ctx, String endpoint) {
        OSSCustomSignerCredentialProvider provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {

                // 此处本应该是客户端将contentString发送到自己的业务服务器,然后由业务服务器返回签名后的content。关于在业务服务器实现签名算法
                // 详情请查看http://help.aliyun.com/document_detail/oss/api-reference/access-control/signature-header.html。客户端
                // 的签名算法实现请参考OSSUtils.sign(accessKey,screctKey,content)
                String signedString = OSSUtils.sign("ju2glP1ZJJjFblkU", "bjIRmszKS8Sxm6WoO6rop42EE2GghV", content);
                return signedString;
            }
        };

        OSSClient tClient = new OSSClient(ctx, endpoint, provider);
        return tClient;
    }

    public void customSign(Context ctx, String endpoint) {
        OSSCustomSignerCredentialProvider provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {

                // 此处本应该是客户端将contentString发送到自己的业务服务器,然后由业务服务器返回签名后的content。关于在业务服务器实现签名算法
                // 详情请查看http://help.aliyun.com/document_detail/oss/api-reference/access-control/signature-header.html。客户端
                // 的签名算法实现请参考OSSUtils.sign(accessKey,screctKey,content)
                String signedString = OSSUtils.sign("ju2glP1ZJJjFblkU", "bjIRmszKS8Sxm6WoO6rop42EE2GghV", content);
                return signedString;
            }
        };

        OSSClient tClient = new OSSClient(ctx, endpoint, provider);

//        GetObjectRequest get = new GetObjectRequest(mBucket, mObject);
//        get.setCRC64(OSSRequest.CRC64Config.YES);
//        get.setProgressListener(new OSSProgressCallback<GetObjectRequest>() {
//            @Override
//            public void onProgress(GetObjectRequest request, long currentSize, long totalSize) {
//                Log.d("GetObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//
//            }
//        });
//        OSSAsyncTask task = tClient.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
//            @Override
//            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
//                ObjectMetadata metadata = result.getMetadata();
//            }
//
//            @Override
//            public void onFailure(GetObjectRequest request, ClientException clientException, ServiceException serviceException) {
//
//            }
//        });


        try {
            boolean found = tClient.doesObjectExist("vipheyue", "tantan.sh");
            Log.d("TAG...", "customSign: " + found);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
