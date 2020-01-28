package fr.isen.kelly.androidtoolbox
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_informations.*
import java.io.FileNotFoundException

class InformationsActivity : AppCompatActivity() {
    private lateinit var myButtonPicture: ImageButton
    val REQUEST_CODE:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)

        myButtonPicture=findViewById(R.id.buttonPicture)
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter = ContactsAdapter(listOf(Contact("Kelly", true), Contact("Melissa", false)))

        val itemDecoration=DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        myList.addItemDecoration(itemDecoration)
        myButtonPicture.setOnClickListener {
            val photoPickerIntent=Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            try {
                val imageUri: Uri = data?.data!!
                val imageStream=contentResolver.openInputStream(imageUri)
                val selectImage=BitmapFactory.decodeStream(imageStream)
                myButtonPicture.setImageBitmap(selectImage)
            }
            catch (e:FileNotFoundException){
                e.printStackTrace()
                Toast.makeText(applicationContext,"Une erreur s'est produite",Toast.LENGTH_LONG).show()
            }

        }
        else{
            Toast.makeText(applicationContext,"Vous n'avez pas choisi d'image",Toast.LENGTH_LONG).show()
        }
    }
}
