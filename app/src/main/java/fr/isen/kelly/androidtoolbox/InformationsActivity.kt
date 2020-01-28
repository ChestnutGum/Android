package fr.isen.kelly.androidtoolbox
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_informations.*
import java.io.FileNotFoundException

class InformationsActivity : AppCompatActivity() {
    private lateinit var myButtonPicture: ImageButton
    val REQUEST_CODE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)

        myButtonPicture = findViewById(R.id.buttonPicture)

        //permissionRecyclerView.apply{
        //myList.layoutManager = LinearLayoutManager(this)
        //myList.adapter = ContactsAdapter(listOf(Contact("Kelly", true), Contact("Melissa", false)))
        //à enlever pour les contacts du tel


        //val itemDecoration=DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        //myList.addItemDecoration(itemDecoration)
        //}

        myButtonPicture.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, REQUEST_CODE)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACT
            )
        } else {
            displayContact()
        }

    }

    private fun displayContact() {
        val contacts = loadContacts()
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter = ContactsAdapter(loadContacts())
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        myList.addItemDecoration(itemDecoration)
    }

    private fun loadContacts(): List<Contact> {
        val contactNameList = arrayListOf<Contact>()
        val phoneCursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        phoneCursor?.let { cursor ->
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contactNameList.add(Contact(name, true))
            }
            cursor.close()
        }

        return contactNameList
    }

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACT = 22
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSIONS_REQUEST_READ_CONTACT) {
            displayContact()
        } else {
            Toast.makeText(this, "Permission refusée par l'utilisateur", Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri = data?.data!!
                val imageStream = contentResolver.openInputStream(imageUri)
                val selectImage = BitmapFactory.decodeStream(imageStream)
                myButtonPicture.setImageBitmap(selectImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Une erreur s'est produite", Toast.LENGTH_LONG)
                    .show()
            }

        } else {
            Toast.makeText(applicationContext, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG)
                .show()
        }

    }
}






