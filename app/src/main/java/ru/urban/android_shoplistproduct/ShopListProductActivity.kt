package ru.urban.android_shoplistproduct

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.IOException

class ShopListProductActivity : AppCompatActivity(), Removable, Updateble {

    private val GALLERY_REQUEST = 302

    var photoUri: Uri? = null

    var item: Int? = null
    var isProductChange: Boolean = true

    private lateinit var toolbarMain: Toolbar

    private lateinit var mainImageViewIV: ImageView
    private lateinit var nameProductET: EditText
    private lateinit var priceProductET: EditText

    private lateinit var saveBTN: Button

    private lateinit var listViewLV: ListView
    private lateinit var adapter: ListAdapter

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_list_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)

        mainImageViewIV = findViewById(R.id.mainImageViewIV)
        nameProductET = findViewById(R.id.nameProductET)
        priceProductET = findViewById(R.id.priceProductET)

        saveBTN = findViewById(R.id.saveBTN)

        listViewLV = findViewById(R.id.listViewLV)

        mainImageViewIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {

            if (nameProductET.text.isEmpty() || priceProductET.text.isEmpty())
                return@setOnClickListener

            if (!priceProductET.text.isDigitsOnly()) {
                Toast.makeText(this, "Цена не в цифрах", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (photoUri == null) {

            }

            val product = Product(
                nameProductET.text.toString(),
                priceProductET.text.toString(),
                photoUri.toString()
            )

            products.add(product)
            photoUri = null

            adapter = ListAdapter(this@ShopListProductActivity, products)
            listViewLV.adapter = adapter
            adapter.notifyDataSetChanged()

            nameProductET.text.clear()
            priceProductET.text.clear()
            mainImageViewIV.setImageResource(R.drawable.ic_unknown_product)

        }

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val product = adapter.getItem(position)
                item = position
                val dialog = MyDialog()
                val args = Bundle()
                args.putParcelable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        mainImageViewIV = findViewById(R.id.mainImageViewIV)
        mainImageViewIV.setImageResource(R.drawable.ic_unknown_product)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data

                mainImageViewIV.setImageURI(photoUri)
            }

            else -> mainImageViewIV.setImageResource(R.drawable.ic_unknown_product)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        toolbarMain.menu.findItem(R.id.exitMenuMain).setIcon(R.drawable.ic_exit_to_app)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun remove(product: Product) {
        adapter.remove(product)
    }

    override fun update(product: Product) {
        val intent = Intent(this, DetailsProductActivity::class.java)
        intent.putExtra("product", product)
//        intent.putExtra("products", this.products as ArrayList<Product>)
//        intent.putExtra("position", item)
//        intent.putExtra("check", isProductChange)
        startActivity(intent)
    }
}