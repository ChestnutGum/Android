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
import android.location.*
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
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.view.View
import com.google.android.gms.location.*


class InformationsActivity : AppCompatActivity() {
    lateinit var mLocationRequest: LocationRequest
    private var locationManager : LocationManager? = null
    lateinit var mCurrentPhotoPath: String
    private lateinit var currentPhotoPath: String
    private lateinit var myButtonPicture: ImageButton
    lateinit var mLastLocation: Location


    lateinit var mLatitudeText: TextView
    lateinit var mLongitudeText: TextView
    var permissions= arrayOf<String>(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)


        mLocationRequest = LocationRequest()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
//        else{
//            startLocationUpdates()
//        }




        //--------------------------------------------------------------------------

        myButtonPicture = findViewById(R.id.buttonPicture)
        myButtonPicture.setOnClickListener {
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
    }

    private fun startLocationUpdates() {

        // Creation de la demande de localisation
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 2000
        mLocationRequest.fastestInterval=1000

        // Creation de LocationSettingsRequest à l'aide d'une demande d'emplacement
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest= builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        val mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //vérification des permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //mLongitudeText.text = "PAS DE PERMISSION !!"
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION
            )
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
            Looper.myLooper())
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }


    fun onLocationChanged(location: Location) {
        // la nouvelle localisation a été déterminée
        mLatitudeText = findViewById<View>(R.id.latitude) as TextView
        mLongitudeText = findViewById<View>(R.id.longitude) as TextView
        mLastLocation = location
        //mise à jour de l'interface utilisateur
        mLatitudeText.text = "Latitude : " + mLastLocation.latitude.toString()
        mLongitudeText.text = "Longitude : " + mLastLocation.longitude.toString()
    }


    //fonction pour vérifier que l'emplacement est activé
    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog  = builder.create()
        alert.show()
    }



    private fun displayContact() {
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
        private const val PERMISSIONS_REQUEST_READ_CONTACT = 20
        private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=10
        private const val PERMISSIONS_REQUEST_CAMERA=30
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=40
        private  const val PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION=50
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        /*if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSIONS_REQUEST_READ_CONTACT) {
            displayContact()
        } else {
            Toast.makeText(this, "Permission refusée par l'utilisateur", Toast.LENGTH_LONG).show()
        }*/
        if(requestCode==10){
            if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera()
            } else {
                Toast.makeText(this, "Permission de stockage refusée par l'utilisateur", Toast.LENGTH_LONG).show()
            }
        }
        if(requestCode==20){
            if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                displayContact()
            } else {
                Toast.makeText(this, "Permission de contacts refusée par l'utilisateur", Toast.LENGTH_LONG).show()
            }
        }
        if(requestCode==30){
            if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera()
            } else {
                Toast.makeText(this, "Permission d'accès à l'appareil photo refusée par l'utilisateur", Toast.LENGTH_LONG).show()
            }
        }
        if(requestCode==40){
            if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(this, "Permission de localisation refusée par l'utilisateur", Toast.LENGTH_LONG).show()
            }
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




