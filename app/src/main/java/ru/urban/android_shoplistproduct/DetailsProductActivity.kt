package ru.urban.android_shoplistproduct

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class DetailsProductActivity : AppCompatActivity() {
    private val GALLERY_REQUEST = 402

    var photoUri: Uri? = null

    private lateinit var toolbarMain: Toolbar

    private lateinit var detailImageProductIV: ImageView
    private lateinit var detailNameProductET: EditText
    private lateinit var detailPriceProductET: EditText
    private lateinit var detailDescriptionProductET: EditText
    private lateinit var detailSaveBTN: Button

    @SuppressLint("MissingInflatedId", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)

        detailImageProductIV = findViewById(R.id.detailImageProductIV)
        detailNameProductET = findViewById(R.id.detailNameProductET)
        detailPriceProductET = findViewById(R.id.detailPriceProductET)
        detailDescriptionProductET = findViewById(R.id.detailDescriptionProductET)

        detailSaveBTN = findViewById(R.id.detailSaveBTN)

        val someProduct = intent.getParcelableExtra("product") as Product?
        detailNameProductET.setText(someProduct?.name)
        detailPriceProductET.setText(someProduct?.price)
        detailImageProductIV.setImageURI(Uri.parse(someProduct?.image))

        detailImageProductIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        detailSaveBTN.setOnClickListener{
            val products = intent.getSerializableExtra("products")
            val position = intent.extras?.getInt("position")
            var isProductChange: Boolean?

            val name = detailNameProductET.text
            val price = detailPriceProductET.text

            val product = Product(name.toString(), price.toString(), photoUri.toString())

            val list: MutableList<Product> = products as MutableList<Product>
            if(position != null){
                swap(position, product, list)
            }
            isProductChange = false
            val intent = Intent(this, ShopListProductActivity::class.java)
            intent.putExtra("list", list as ArrayList<Product>)
            intent.putExtra("newCheck", isProductChange)
            setResult(RESULT_OK, intent)
            finish()

        }
    }

    private fun swap(position: Int, product: Product, products: MutableList<Product>){
        products.add(position + 1, product)
        products.removeAt(position)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        detailImageProductIV = findViewById(R.id.detailImageProductIV)
        detailImageProductIV.setImageResource(R.drawable.ic_unknown_product)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data

                detailImageProductIV.setImageURI(photoUri)
            }

            else -> detailImageProductIV.setImageResource(R.drawable.ic_unknown_product)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                exitProcess(0)
            }

            R.id.backMenuMain -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}