package com.example.heyue.ossprivatebox.browser

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.heyue.ossprivatebox.R
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A placeholder fragment containing a simple view.
 */
private const val ARG_PARAM1 = "param1"

class MainActivityFragment : Fragment(), BrowserContract.View {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var multipleItemAdapter: MultipleItemQuickAdapter
    override fun loadData(mData: List<MultipleItem>) {
        multipleItemAdapter.replaceData(mData)
    }

    override lateinit var presenter: BrowserContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        param1?.let { presenter.getCurrentFilesAndDirs(it) }
    }

    private fun initView() {
        multipleItemAdapter = MultipleItemQuickAdapter(activity as Context, ArrayList<MultipleItem>())
        val manager = GridLayoutManager(activity, 1)
        recyclerView_history.setLayoutManager(manager)
        recyclerView_history.setAdapter(multipleItemAdapter)
        multipleItemAdapter.setOnItemClickListener { adapter, view, position ->
            var item = adapter.getItem(position) as MultipleItem
            when (item.itemType) {
                MultipleItem.DIR -> {
                    //   传递参数过去
//                    newInstance(item.fileUrl)
                    listener?.openNewDir(item.fileUrl)
                }
                else -> {
                    var constrainedUrl = presenter.getConstrainedUrl(item.fileUrl)

                    val cm = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val mClipData = ClipData.newPlainText("", constrainedUrl)
                    cm.primaryClip = mClipData
                    Toast.makeText( activity, "复制成功", Toast.LENGTH_SHORT).show();

                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    val content_uri = Uri.parse(constrainedUrl)
                    intent.data = content_uri
                    startActivity(intent)
                }
            }
        }
    }
    fun onButtonPressed(uri: String) {
        listener?.openNewDir(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        param1?.let { listener?.removeFragment(it) }
        super.onDetach()
        listener = null

    }
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun openNewDir(uri: String)
        fun removeFragment(uri: String)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                MainActivityFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}
