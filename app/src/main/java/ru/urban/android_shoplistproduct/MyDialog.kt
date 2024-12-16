package ru.urban.android_shoplistproduct

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialog: DialogFragment() {

    private var removable: Removable? = null
    private var updateble: Updateble? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        removable = context as Removable?
        updateble = context as Updateble?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val product = requireArguments().getParcelable<Product>("product")
        val builder = AlertDialog.Builder(requireActivity())
        return builder.setTitle("Внимание!")
            .setMessage("Выберите нужное действие:")
            .setNegativeButton("Нет",null)
            .setPositiveButton("Удалить продукт из списка"){ dialog, which ->
                removable?.remove(product as Product)
                Toast.makeText(context, "Продукт удален", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .setNeutralButton("Изменить описание продукта"){dialog, which ->
                updateble?.update(product as Product)
                Toast.makeText(context, "Информация о товаре", Toast.LENGTH_SHORT).show()
            }
            .create()
    }
}