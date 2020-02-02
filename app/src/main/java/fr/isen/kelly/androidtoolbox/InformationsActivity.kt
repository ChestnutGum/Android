@file:Suppress("DEPRECATION")

package fr.isen.kelly.androidtoolbox
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_informations.*
import android.location.Location
import android.os.Looper
import android.view.View
import com.google.android.gms.location.*


class InformationsActivity : AppCompatActivity() {
    lateinit var mLocationRequest: LocationRequest
    private lateinit var myButtonPicture: ImageButton
    lateinit var mLastLocation: Location
    lateinit var mLatitudeText: TextView
    lateinit var mLongitudeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)
        mLocationRequest = LocationRequest()

        myButtonPicture = findViewById(R.id.buttonPicture)
        myButtonPicture.setOnClickListener {
            showPictureDialog()
        }
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this,arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS),2)
        }
        else {
                displayContact()
                startLocationUpdates()
            }
        }

    //------------------------------------PARTIE LOCALISATION -------------------------------------
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
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
            Looper.myLooper())
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }


    @SuppressLint("SetTextI18n")
    fun onLocationChanged(location: Location) {
        // la nouvelle localisation a été déterminée
        mLatitudeText = findViewById<View>(R.id.latitude) as TextView
        mLongitudeText = findViewById<View>(R.id.longitude) as TextView
        mLastLocation = location
        //mise à jour de l'interface utilisateur
        mLatitudeText.text = getString(R.string.text_latitude) + mLastLocation.latitude.toString()
        mLongitudeText.text = getString(R.string.text_longitude) + mLastLocation.longitude.toString()
    }


    //---------------------------------------------------------------------------------------------


    //-----------------------------------PARTIE CONTACTS-------------------------------------------

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
                //je m'étais servi du isOnline (booleen) pour pouvoir envoyer un message quand le contact est en ligne par exemple
                //j'ai gardé la mise en forme pour pouvoir éventuellement rajouter cette fonctionnalité à l'avenir
                contactNameList.add(Contact(name, true))
            }
            cursor.close()
        }
        return contactNameList
    }

    //---------------------------------------------------------------------------------------------

    //--------------------------------------PARTIE PHOTO-------------------------------------------

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
        pictureDialog.setTitle(getString(R.string.choose_action))
        val pictureDialogItems =
            arrayOf<String>(getString(R.string.gallery), getString(R.string.take_photo))
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> launchGallery()
                1 -> takePhotoFromCamera()//permissionCamera()
            }

        }
        pictureDialog.show()
    }

    /*private fun permissionCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf<String>(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }
        else {
            takePhotoFromCamera()
        }
    }*/

    //fonction pour sauvegarder la photo prise, dans la gallery
    private fun saveToInternalStorage(bitmapImage:Bitmap) {
        MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmapImage,
            getString(R.string.image),
            getString(R.string.image))
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
            saveToInternalStorage(thumbnail)
            Toast.makeText(this@InformationsActivity, getString(R.string.image_saved), Toast.LENGTH_SHORT).show()
        }
    }

    //--------------------------------------------------------------------------------------------

    //permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        //permissions pour l'accès aux contacts et la localisation en arrivant sur la page
        if(requestCode==2){
            if(grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                startLocationUpdates()
            }
            else {
                Toast.makeText(this, getString(R.string.permission_localisation_refused), Toast.LENGTH_LONG).show()
            }
            if(grantResult.isNotEmpty() && grantResult[1] == PackageManager.PERMISSION_GRANTED){
                displayContact()
            }
            else {
                Toast.makeText(this, getString(R.string.permission_contacts_refused), Toast.LENGTH_LONG).show()
            }
        }
        if(requestCode==1){
            mLatitudeText.text=grantResult[0].toString()
            if(grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                takePhotoFromCamera()
            }
            else {
                Toast.makeText(this, getString(R.string.permission_camera_refused), Toast.LENGTH_LONG).show()
            }
        }
    }

}




