@file:Suppress("DEPRECATION")

package fr.isen.kelly.androidtoolbox
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Criteria
import android.location.LocationManager
import android.location.LocationProvider
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_informations.*
import kotlinx.android.synthetic.main.activity_permission.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class InformationsActivity : AppCompatActivity() {

    lateinit var mCurrentPhotoPath: String
    private lateinit var currentPhotoPath: String
    private lateinit var myButtonPicture: ImageButton

    //val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)

        myButtonPicture = findViewById(R.id.buttonPicture)
        myButtonPicture.setOnClickListener {
            //val photoPickerIntent = Intent(Intent.ACTION_PICK)
            //photoPickerIntent.type = "image/*"
            //startActivityForResult(photoPickerIntent, REQUEST_CODE)
            showPictureDialog()
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

        //----------------------------------------------------

        /*var locationManager: LocationManager= getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers=ArrayList<LocationProvider>()
        val names=locationManager.getProviders(true)
        for(name in names){
            providers.add(locationManager.getProvider(name))
        }
        val critere = Criteria()
        critere.setAccuracy(Criteria.ACCURACY_FINE)
        critere.setAltitudeRequired(true)
        critere.setBearingRequired(true)
        critere.setCostAllowed(false)
        critere.setPowerRequirement(Criteria.POWER_HIGH)
        critere.setSpeedRequired(true)*/

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

    //depuis la galerie
    private fun launchGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 1000)
    }

    //depuis la camera
    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2000)

    }
    //boite de dialogue qui propose à l'utilisateur de choisir entre : importer l'image depuis
    //la galerie ou de l'appareil photo
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Choisir une action :")
        val pictureDialogItems =
            arrayOf<String>("Selectionner depuis la galerie", "Prendre une photo")
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> launchGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    //fonction pour sauvegarder la photo prise, dans la gallery
    private fun saveToInternalStorage(context:Context, bitmapImage:Bitmap) {
        var savedImageURL: String = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmapImage,
            "Image",
            "Image")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val startTextView2: TextView = findViewById(R.id.latitude)
        startTextView2.text = requestCode.toString()
        //depuis la galerie
        if (requestCode == 1000) {
            val imageUri: Uri = data?.data!!
            val imageStream = contentResolver.openInputStream(imageUri)
            val selectImage = BitmapFactory.decodeStream(imageStream)
            myButtonPicture.setImageBitmap(selectImage)
        }
        //depuis l'appareil photo
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            val thumbnail = data?.extras?.get("data") as Bitmap
            myButtonPicture.setImageBitmap(thumbnail)
            saveToInternalStorage(this,thumbnail)
            Toast.makeText(this@InformationsActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

}


