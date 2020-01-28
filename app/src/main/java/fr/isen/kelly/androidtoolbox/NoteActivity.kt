package fr.isen.kelly.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)}}
        //fun CameraGalleryManager(){
//val pictureDialog=AlertDialog.Builder(this)
//pictureDialog.setTitle("Que faire:")
//val pictureDialogItems=arrayOf("Prendre une photo","importer depuis la galerie")
//pictureDialog.setItems(
//    pictureDialogItems
//){
//    _,which ->
//    when(which){
//        0-> openCamera()
//        1-> openGalerie()

//private fun openCamera(){
//val values=ContentValues()
//image_uri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
//val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//cameraIntent.putExtra(MediaStore....

// override fun selection() {
// val items=arrayOf<CharSequence>("Take photo","Choose from gallery","Cancel")
// val builder=AlertDialog.Builder(this)
// builder.setItems(items) { dialog, item -> if(items[item] == "Take photo"){
// requestStoragePermission(true)
// } else if(items[item]=="Choose from gallery"){
// requestStoragePermission(false)
// } else if(items[item]=="Cancel"){
// dialog.dismiss()



