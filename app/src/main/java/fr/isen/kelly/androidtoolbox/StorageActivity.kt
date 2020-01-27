package fr.isen.kelly.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject
import java.io.File
import java.lang.StringBuilder
import java.util.*

class StorageActivity : AppCompatActivity() {
    //déclaration des éléments
    //bouton de sauvegarde
    lateinit var mySave: Button// = findViewById(R.id.save)
    //bouton de lecture des informations
    lateinit var myShow: Button// = findViewById(R.id.show)
    //nom de famille
    lateinit var myLastName: EditText// = findViewById(R.id.lastName)
    //prénom
    lateinit var myFirstName: EditText// = findViewById(R.id.firstName)
    //date de naissance
    lateinit var myDate: TextView //= findViewById(R.id.date)
    //titre
    lateinit var myDateTitle: TextView //= findViewById(R.id.dateTitle)

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

//id : loginTitle
        //var:loginTitle
        //constante: LOGIN_TITLE
        //instance de calendrier
        val cal: Calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            var mois:Int=month+1
            val newDate = "$year/$mois/$dayOfMonth"
            myDate.text=newDate
            /*myDate.setText(
                StringBuilder().append(cal.get(Calendar.YEAR)).append("/").append(
                    cal.get(
                        Calendar.MONTH
                    ) + 1
                ).append("/").append(cal.get(Calendar.DAY_OF_MONTH))
            )*/
        }
        //action du bouton de sauvegarde
        mySave.setOnClickListener {
            saveDataToFile(
                myLastName.text.toString(),
                myFirstName.text.toString(),
                myDate.text.toString()
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

    //fonction pour sauvegarder les données dans le fichier json
    private fun saveDataToFile(lastName: String, firstname: String, date: String) {
        if (firstname.isNotEmpty() && lastName.isNotEmpty()) {// && date != getString(R.string.storage_date_value)){
            val data = "{'lastName':'$lastName','firstName':'$firstname','date':'$date'}"
            val dataJson: JSONObject = JSONObject().put("lastName", lastName)
            File(cacheDir.absolutePath + JSON_FILE).writeText(data)
            Toast.makeText(
                this@StorageActivity,
                "Sauvegarde des informations de l'utilisateur",
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
            //affichage d'une fenetre de dialogue
            AlertDialog.Builder(this@StorageActivity).setTitle("Lecture du fichier")
                .setMessage("Nom:$strLastName\n Prenom:$strFirstName\n Date:$strDate\nAge:0")
                .create().show()

        } else {
            Toast.makeText(this@StorageActivity, "Aucune information fournie", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun getAge(year:Int,month:Int,day:Int){
        val cal: Calendar = Calendar.getInstance()
        var yearNow:Int=cal.get(Calendar.YEAR)
        var monthNow:Int=cal.get(Calendar.MONTH)
        var dayNow:Int=cal.get(Calendar.DAY_OF_MONTH)
        


    }

    companion object {
        private const val JSON_FILE = "data_user_toolbox.json"
        private const val LAST_NAME_KEY = "LastName"
        private const val FIRST_NAME_KEY = "fisrtname"
        private const val DATE_KEY = "date"
    }
}
