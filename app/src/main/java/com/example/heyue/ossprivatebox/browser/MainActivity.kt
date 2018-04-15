package com.example.heyue.ossprivatebox.browser

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.heyue.ossprivatebox.AppConfig
import com.example.heyue.ossprivatebox.MyApp
import com.example.heyue.ossprivatebox.R
import com.example.heyue.ossprivatebox.oss.DataRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MainActivityFragment.OnFragmentInteractionListener {


    override fun openNewDir(uri: String) {

        createFragment(uri)

    }

    override fun removeFragment(uri: String) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        if (fragmentArrayList.size > 1) {
            transaction.remove(fragmentArrayList.get(fragmentArrayList.size - 1)).commit()
            fragmentArrayList.removeAt(fragmentArrayList.size - 1)
        }
        thread {
            Glide.get(applicationContext).clearDiskCache();
        }


    }

    var fragmentArrayList = ArrayList<Fragment>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        createFragment("")
    }

    private fun createFragment(dir: String) {

        //创建 MVP 关系

        var dataBaseRepository = DataRepository(MyApp.instance, AppConfig.endpoint, AppConfig.bucketName)

        var universalFragment = MainActivityFragment.newInstance(dir)

        var universalPresent = BrowserPresent(dataBaseRepository, universalFragment)
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()

//        Toast.makeText(this, "xxxx:"+fragmentArrayList.size, Toast.LENGTH_SHORT).show();
        if (fragmentArrayList.size == 0) {
            supportFragmentManager.beginTransaction().add(R.id.fl_coninter, universalFragment)
                    .commit()
        } else {
            transaction
                    .hide(fragmentArrayList.get(fragmentArrayList.size - 1))
                    .add(R.id.fl_coninter, universalFragment)
                    .addToBackStack(dir)
                    .commit() //显示 fragment
        }
        fragmentArrayList.add(universalFragment)


    }

    fun switchContent(to: Fragment, dir: String) {
        val fm = supportFragmentManager
        fm.primaryNavigationFragment
        val transaction = fm.beginTransaction()
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(to).add(R.id.fl_coninter, to).addToBackStack(dir).commit() // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(to).show(to).commit() // 隐藏当前的fragment，显示下一个
        }
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

    override fun onBackPressed() {
        thread {
            Glide.get(applicationContext).clearDiskCache();
        }

        super.onBackPressed()
    }
}
