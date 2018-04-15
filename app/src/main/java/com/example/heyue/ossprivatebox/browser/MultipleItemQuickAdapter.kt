package com.example.heyue.ossprivatebox.browser

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.heyue.ossprivatebox.AppConfig
import com.example.heyue.ossprivatebox.MyApp
import com.example.heyue.ossprivatebox.R
import com.example.heyue.ossprivatebox.oss.DataRepository

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
class MultipleItemQuickAdapter(internal var context: Context, data: List<*>) : BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(data as MutableList<MultipleItem>?) {
    init {
        addItemType(MultipleItem.IMG, R.layout.item_img)
        addItemType(MultipleItem.MOVIE, R.layout.item_movie)
        addItemType(MultipleItem.MUSIC, R.layout.item_music)
        addItemType(MultipleItem.OTHER, R.layout.item_other)
        addItemType(MultipleItem.DIR, R.layout.item_dir)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (helper.itemViewType) {
            MultipleItem.IMG -> {
                //加载数据
                var dataBaseRepository = DataRepository(MyApp.instance, AppConfig.endpoint, AppConfig.bucketName)
                dataBaseRepository.downPic(item.fileUrl, object : OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
                    override fun onSuccess(request: GetObjectRequest, result: GetObjectResult) {

                        // 请求成功
                        result.contentLength
                        val inputStream = result.objectContent
                        var decodeStream = BitmapFactory.decodeStream(inputStream)
                        val view = helper.getView<ImageView>(R.id.imageView)
                        view.post {
                            helper.setText(R.id.tv_name, item.fileUrl)
                            Glide.with(view.context)
                                    .load(decodeStream)
                                    .into(view)
//                            Glide.with(context).load("http://heyue.oss-cn-hangzhou.aliyuncs.com/AppData/Calculator/FBACE59E-276A-4CE7-A397-E9100297A2B2.png").into(view)
                        }
                    }

                    override fun onFailure(request: GetObjectRequest, clientExcepion: ClientException, serviceException: ServiceException) {
                        // 处理异常
                    }
                })

            }
            MultipleItem.OTHER, MultipleItem.DIR, MultipleItem.MOVIE, MultipleItem.MUSIC -> {
                helper.setText(R.id.textView, item.fileUrl)
            }
        }
    }

}
