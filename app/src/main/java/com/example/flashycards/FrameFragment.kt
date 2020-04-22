package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class FrameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        if(savedInstanceState == null) {
            childFragmentManager
                ?.beginTransaction()
                ?.add(R.id.frame, CardFragment())
                ?.commit();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frame, container, false)
    }

}
