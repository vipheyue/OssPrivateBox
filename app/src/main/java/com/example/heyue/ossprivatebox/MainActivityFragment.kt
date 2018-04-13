package com.example.heyue.ossprivatebox

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heyue.ossprivatebox.browser.BrowserContract
import com.example.heyue.ossprivatebox.browser.MultipleItem
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), BrowserContract.View {
    lateinit var multipleItemAdapter: MultipleItemQuickAdapter
    override fun loadData(mData: List<MultipleItem>) {
        multipleItemAdapter.replaceData(mData)
    }

    override lateinit var presenter: BrowserContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
         multipleItemAdapter = MultipleItemQuickAdapter(activity, null)
        val manager = GridLayoutManager(activity, 4)
        recyclerView_history.setLayoutManager(manager)
        recyclerView_history.setAdapter(multipleItemAdapter)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}
