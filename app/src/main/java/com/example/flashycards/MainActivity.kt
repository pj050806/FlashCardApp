package com.example.flashycards

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.CardViewModel
import com.example.database.CardPile
import com.example.database.FlashCard
import com.example.flashycards.databinding.AddCardBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            newCardAlert()
        }

        viewModel = ViewModelProvider(this)[CardViewModel::class.java]
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

    fun insertNewCard(front: String, back: String, label: String, color: Int) {
        val newCard = FlashCard(front, back, label, color)
        viewModel.insert(newCard)
    }

    fun insertNewPile(label: String) {
        val pile = CardPile(label)
        viewModel.insert(pile)
    }

    private fun newCardAlert() {
        val builder = AlertDialog.Builder(this)
        val addCardLayout = layoutInflater.inflate(R.layout.add_card, null)
        val bind = AddCardBinding.bind(addCardLayout)

        val viewModel = ViewModelProvider(this)[CardViewModel::class.java]
        viewModel.allLabels.observe(this, Observer { labelList ->
                bind.spinnerLabel.adapter = ArrayAdapter(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    labelList)
            bind.spinnerColor.adapter = ColorAdapter(this,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getIntArray(R.array.cardColors).asList())
        })

        builder.setMessage("Enter new card!")
            .setTitle("New Card")
            .setPositiveButton("Add") { _, _ ->
                println("Dialog works!!!")
                println(bind.editTextCardFront.text)
                println(bind.editTextCardBack.text)
                println(bind.spinnerLabel.selectedItem)
                insertNewCard(bind.editTextCardFront.text.toString(),
                    bind.editTextCardBack.text.toString(),
                    bind.spinnerLabel.selectedItem.toString(),
                    bind.spinnerColor.selectedItem as Int)
            }.setNegativeButton("Cancel") {
                    dialog, _ ->  dialog.cancel()
                    viewModel.allLabels.removeObservers(this)
                }
            .setView(addCardLayout)
            .create().show()
    }

    inner class ColorAdapter(context: Context, layout: Int, values: List<Int>) : ArrayAdapter<Int>(context, layout, values) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val colorValue = super.getItem(position)
            val view =  super.getView(position, convertView, parent)
            view.setBackgroundColor(colorValue ?: Color.WHITE)
            (view as TextView).text = Integer.toHexString(colorValue ?: Color.WHITE)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val colorValue = super.getItem(position)
            view.setBackgroundColor(colorValue ?: android.R.color.white)
            (view as TextView).text = Integer.toHexString(colorValue ?: Color.WHITE)
         return view
        }
    }
}
