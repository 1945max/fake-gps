package id.xxx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<ActivityBinding : ViewDataBinding> : Fragment() {

    protected abstract val layoutFragment: Int
    protected lateinit var binding: ActivityBinding

    override fun onCreateView(li: LayoutInflater, con: ViewGroup?, bundle: Bundle?): View? {
        binding = DataBindingUtil.inflate(li, layoutFragment, con, false)
        return binding.root
    }
}