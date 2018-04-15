package com.example.heyue.ossprivatebox

object AppConfig {
//    val bucketName = "vipheyueprivetetest"
    var bucketName : String by Preference<String>(MyApp.instance,"bucketName", "")
    //https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.2.5.mttgjt
    var endpoint : String by Preference<String>(MyApp.instance,"endpoint", "")
    var asscessKey : String by Preference<String>(MyApp.instance,"asscessKey", "")
    var screctKey : String by Preference<String>(MyApp.instance,"screctKey", "")
    var appPwd : String by Preference<String>(MyApp.instance,"appPwd", "")
    var firSetPwd : Boolean by Preference<Boolean>(MyApp.instance,"HavedPwd", true)

}
