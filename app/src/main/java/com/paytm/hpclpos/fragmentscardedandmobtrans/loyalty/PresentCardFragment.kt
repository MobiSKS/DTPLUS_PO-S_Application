package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import butterknife.BindView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.databinding.FragmentPresentCardBinding

class PresentCardFragment : Fragment(), View.OnClickListener {
    private var mParam1: String? = null
    private var mParam2: String? = null

    @JvmField
    @BindView(R.id.lLcardlesstransaction)
    var lLcardlesstransaction: LinearLayout? = null

    @JvmField
    @BindView(R.id.cardSwipeTransaction)
    var cardSwipeTransaction: LinearLayout? = null
    private val rcvAmount: String? = null


    lateinit var binding: FragmentPresentCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments()!!.getString(ARG_PARAM1)
            mParam2 = requireArguments()!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_present_card, container, false)
        //ButterKnife.bind(this, view)
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_present_card,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        binding.lLcardlesstransaction!!.setOnClickListener(this)
        binding.cardSwipeTransaction!!.setOnClickListener(this)
        val rcvAmountText = view.findViewById<EditText>(R.id.rcvAmountText)

        //Intent intent = getIntent();
        //rcvAmount = intent.getStringExtra("sendingAmount");
        Log.e("get_amount", "" + rcvAmount)
        rcvAmountText.setText(rcvAmount)
    }

    private fun callEnterMobNoActivity() {
        val fragment: Fragment = EnterMobileNoFragment()
        requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
    }

    private fun callDriverLoyaltyFragment() {
        val fragment: Fragment = DriverLoyaltyFragment()
        requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cardSwipeTransaction -> callDriverLoyaltyFragment()
            R.id.lLcardlesstransaction -> callEnterMobNoActivity()
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): PresentCardFragment {
            val fragment = PresentCardFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}