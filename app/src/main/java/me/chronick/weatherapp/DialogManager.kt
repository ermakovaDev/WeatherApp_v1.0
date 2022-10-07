package me.chronick.weatherapp

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {

    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enabled location?")
        dialog.setMessage("Location disabled, do you want enabled location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){_,_->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL"){_,_->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByCityDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialogCityName = EditText(context)
        builder.setView(dialogCityName)
        val dialog = builder.create()
        dialog.setTitle("City name:")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){_,_->
            listener.onClick(dialogCityName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL"){_,_->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener{
        fun onClick(nameCity: String?)
    }
}