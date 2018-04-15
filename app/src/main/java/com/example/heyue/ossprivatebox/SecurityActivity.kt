package com.example.heyue.ossprivatebox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.heyue.ossprivatebox.AppConfig.appPwd
import com.example.heyue.ossprivatebox.browser.MainActivity
import kotlinx.android.synthetic.main.activity_security.*



class SecurityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)
        initView()
    }

    private fun initView() {
        et_bucketName.setText(AppConfig.bucketName)
        et_endpoint.setText(AppConfig.endpoint)
        et_asscessKey.setText(AppConfig.asscessKey)
        et_screctKey.setText(AppConfig.screctKey)
//        et_appPwd.setText(bucketName)
        btn_start.setOnClickListener {
            startBrowse()
        }

        btn_rest.setOnClickListener {
            AppConfig.bucketName = ""
            AppConfig.endpoint = ""
            AppConfig.asscessKey = ""
            AppConfig.screctKey = ""
            AppConfig.firSetPwd = true
            initView()
        }
        btn_browse_endpoint.setOnClickListener {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            val content_uri = Uri.parse("https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.5.mttgjt")
            intent.data = content_uri
            startActivity(intent)
        }

        et_appPwd.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                startBrowse()
            }
            false
        }


    }

    private fun startBrowse() {
        AppConfig.bucketName = et_bucketName.text.toString().trim()
        AppConfig.endpoint = et_endpoint.text.toString().trim()
        AppConfig.asscessKey = et_asscessKey.text.toString().trim()
        AppConfig.screctKey = et_screctKey.text.toString().trim()
        if (AppConfig.firSetPwd) {
            val inputPwd = et_appPwd.text.toString().trim()
            appPwd = inputPwd
            Toast.makeText(this, "设置成功,密码为:" + inputPwd, Toast.LENGTH_SHORT).show();
            AppConfig.firSetPwd = false
        }

        val inputPwd = et_appPwd.text.toString().trim()
        if (inputPwd.equals(appPwd)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "密码错误,确保安全性如果忘记密码请点击 清空配置", Toast.LENGTH_SHORT).show();
        }
    }
}
