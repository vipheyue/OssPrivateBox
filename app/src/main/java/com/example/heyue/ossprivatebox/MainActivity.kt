package com.example.heyue.ossprivatebox

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.heyue.ossprivatebox.browser.AppConfig
import com.example.heyue.ossprivatebox.browser.BrowserPresent
import com.example.heyue.ossprivatebox.oss.DataRepository

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        initView()
    }

    private fun initView() {

        //创建 MVP 关系

        var dataBaseRepository = DataRepository(MyApp.instance, AppConfig.endpoint, AppConfig.bucketName)

        var universalFragment = MainActivityFragment()

        var universalPresent = BrowserPresent(dataBaseRepository, universalFragment)

        supportFragmentManager.beginTransaction().replace(R.id.fl_coninter, universalFragment).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
