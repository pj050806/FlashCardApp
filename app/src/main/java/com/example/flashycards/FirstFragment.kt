package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashycards.databinding.FragmentFirstBinding
import com.example.flashycards.databinding.LabelListItemBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding
    get() = _binding!!

    private var allLabels: MutableList<String> = emptyList<String>().toMutableList()
    private val viewAdapter: RecyclerView.Adapter<*> = MyAdapter(allLabels)
    private val myObserver = Observer<List<String>> {
        println("observer triggered labellist: $it")
        allLabels.clear()
        allLabels.addAll(it)
        viewAdapter.notifyDataSetChanged()
        println("set labellist: $allLabels")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("Testwörter1")
            findNavController().navigate(action)
        }


        (activity as MainActivity).viewModel.allLabels.observe(viewLifecycleOwner, myObserver)
        val viewManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.recyclerviewCardpile.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).viewModel.allLabels.removeObserver(myObserver)
    }


    private inner class MyAdapter(private val myDataset: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

       inner class MyViewHolder(val binding: LabelListItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
           //val view = LayoutInflater.from(parent.context).inflate(R.layout.label_list_item, parent, false)
           //val tex = view.findViewById<TextView>(R.id.textView_Label)
            val binding = LabelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           return  MyViewHolder(binding)
        }

        override fun getItemCount(): Int = myDataset.size + 1

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if(position < myDataset.size) {

                holder.binding.textViewLabel.text = myDataset[position]

                holder.binding.cardViewLabel.setOnClickListener {
                    val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(myDataset[position])
                    it.findNavController().navigate(action)
                }
            } else {
                holder.binding.textViewLabel.text = getString(R.string.newLabel)
                holder.binding.cardViewLabel.setOnClickListener{
                    println("TODO add new Label")
                    addLabelAlert()
                }
            }
        }
    }

    private fun addLabelAlert(){
       val textEdit = EditText(this@FirstFragment.requireContext())
       textEdit.hint = "Name of Pile"
       AlertDialog.Builder(this@FirstFragment.requireContext()).also {
            it.setMessage("Enter new Label!")
                .setTitle("New Card Pile")
                .setView(textEdit)
                .setPositiveButton("Add") { _, _ ->
                    println("Dialog works!!!")
                    val text = textEdit.text.toString()
                    (activity as MainActivity).insertNewCard(text, text, text)
                }
                .setNegativeButton("Cancel") {dialog, _ ->
                    dialog.cancel()
                }
                .create().show()
       }
    }
}
