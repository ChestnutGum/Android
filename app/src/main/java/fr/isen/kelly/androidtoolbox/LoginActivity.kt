package fr.isen.kelly.androidtoolbox
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class LoginActivity : AppCompatActivity() {
    private lateinit var my_Id:EditText
    private lateinit var myButtonValidate:Button
    private lateinit var myPass:EditText
    val MY_KEY_PASS="Password"
    val MY_KEY_ID="Identifiant"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //.apply et pas commit()
        //supprimer la sauvegarde
        //editor.clear
        val sharedPreferences=getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        var editor=sharedPreferences.edit()

        val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
        //si les informations ont déjà été sauvegardées
        if(sharedPreferences.getString(MY_KEY_ID,"")=="admin" && sharedPreferences.getString(MY_KEY_PASS,"")=="123"){
            //logger l'utilisateur directement
            startActivity(homeActivity)
        }

        //bouton valider
        myButtonValidate=findViewById(R.id.button_validate) as Button


        //action qui s'exécute quand on clique sur le bouton "valider"
        myButtonValidate.setOnClickListener {
            val myIdentifiant=my_Id.text.toString()
            val myPasswd=myPass.text.toString()
            //afficher un message
            if (myIdentifiant == "admin" && myPasswd == "123") {
                //password correct
                Toast.makeText(this@LoginActivity, "Bienvenue !", Toast.LENGTH_LONG).show()
                //fonction de sauvegarde
                saveInformations(myIdentifiant, myPasswd)
                //passage à la page home
                //val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(homeActivity)


            } else {
                //password incorrect
                //Apparition d'un toast qui indique de réessayer
                Toast.makeText(this@LoginActivity, "Try Again...", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun saveInformations(identifiant:String,mdp:String){
        val sharedPreferences=getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        var editor=sharedPreferences.edit()
        //sauvegarde des données
        editor.putString(MY_KEY_ID,identifiant)
        editor.putString(MY_KEY_PASS,mdp)
        editor.apply()
    }

}
