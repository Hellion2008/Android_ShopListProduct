package ru.urban.android_shoplistproduct

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsProductActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar

    private lateinit var detailImageProductIV: ImageView
    private lateinit var detailNameProductET: EditText
    private lateinit var detailPriceProductET: EditText
    private lateinit var detailDescriptionProductET: EditText

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

        val someProduct = intent.getParcelableExtra("product") as Product?
        detailNameProductET.hint = someProduct?.name
        detailPriceProductET.hint = someProduct?.price
        detailImageProductIV.setImageURI(Uri.parse(someProduct?.image))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        toolbarMain.menu.findItem(R.id.exitMenuMain).setIcon(R.drawable.ic_exit_to_app)
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
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}