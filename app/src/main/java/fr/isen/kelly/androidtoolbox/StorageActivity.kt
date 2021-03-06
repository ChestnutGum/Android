package fr.isen.kelly.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject
import java.io.File
import java.util.*

class StorageActivity : AppCompatActivity() {
    //déclaration des éléments
    //bouton de sauvegarde
    lateinit var mySave: Button
    //bouton de lecture des informations
    lateinit var myShow: Button
    //nom de famille
    lateinit var myLastName: EditText
    //prénom
    lateinit var myFirstName: EditText
    //date de naissance
    lateinit var myDate: TextView
    //titre
    lateinit var myDateTitle: TextView
    lateinit var myAge:String

    //fichier json
    private val JSON_FILE = "data_user_toolbox.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        mySave = findViewById(R.id.save)
        myShow = findViewById(R.id.show)
        myLastName = findViewById(R.id.lastName)
        myFirstName = findViewById(R.id.firstName)
        myDate = findViewById(R.id.date) //../../..
        myDateTitle = findViewById(R.id.dateTitle) //date

        //instance de calendrier
        val cal: Calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val mois:Int=month+1
            val newDate = "$year/$mois/$dayOfMonth"
            myDate.text=newDate
            myAge=getAge(year,month,dayOfMonth)
        }
        //action du bouton de sauvegarde
        mySave.setOnClickListener {
            saveDataToFile(
                myLastName.text.toString(),
                myFirstName.text.toString(),
                myDate.text.toString(),
                myAge
            )
        }
        //action du bouton de lecture
        myShow.setOnClickListener {
            showDataFromFile()
        }
        //action de la date
        myDate.setOnClickListener {
            showDatePicker(cal, dateSetListener)
        }

    }

    //affiche le calendrier
    private fun showDatePicker(cal: Calendar, dateSetListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(
            this@StorageActivity,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)

        ).show()

    }
    //beug quand on fait une sauvegarde vide
    //fonction pour sauvegarder les données dans le fichier json
    private fun saveDataToFile(lastName: String, firstname: String, date: String, age: String) {
        if (firstname.isNotEmpty() && lastName.isNotEmpty() && date != getString(R.string.date_non_select)){
            val data = "{'lastName':'$lastName','firstName':'$firstname','date':'$date','age':'$age'}"
            val dataJson: JSONObject = JSONObject().put("lastName", lastName)
            File(cacheDir.absolutePath + JSON_FILE).writeText(data)
            Toast.makeText(
                this@StorageActivity,
                getString(R.string.info_save),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //affiche les information stockées dans le fichier json
    private fun showDataFromFile() {
        val dataJson: String = File(cacheDir.absolutePath + JSON_FILE).readText()
        if (dataJson.isNotEmpty()) {
            val jsonObject = JSONObject(dataJson)
            //optString : renvoie une chaine vide si la clé spécifiée n'existe pas
            val strDate: String = jsonObject.optString("date")
            val strLastName: String = jsonObject.optString("lastName")
            val strFirstName: String = jsonObject.optString("firstName")
            val strAge: String = jsonObject.optString("age")
            //affichage d'une fenetre de dialogue
            AlertDialog.Builder(this@StorageActivity).setTitle("Lecture du fichier")
                .setMessage("Nom:$strLastName\n Prenom:$strFirstName\n Date:$strDate\nAge:$strAge")
                .create().show()

        } else {
            Toast.makeText(this@StorageActivity, getString(R.string.text_aucune_info), Toast.LENGTH_LONG)
                .show()
        }
    }
    //fonction qui calcule l'age de l'utilisateur
    private fun getAge(year:Int,month:Int,day:Int):String{
        val today = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.set(year,month,day)
        //date du jour - date de naissance
        var age : Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if(today.get(Calendar.DAY_OF_YEAR)< dob.get(Calendar.DAY_OF_YEAR)){
            age--
        }
        val ageS=age.toString()
        return ageS
    }

    companion object {
        private const val JSON_FILE = "data_user_toolbox.json"
        private const val LAST_NAME_KEY = "LastName"
        private const val FIRST_NAME_KEY = "fisrtname"
        private const val DATE_KEY = "date"
    }
}
