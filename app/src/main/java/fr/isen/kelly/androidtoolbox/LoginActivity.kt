package fr.isen.kelly.androidtoolbox
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class LoginActivity : AppCompatActivity() {
    private lateinit var myId:EditText
    private lateinit var myButtonValidate:Button
    private lateinit var myPasswd:EditText
    val MY_KEY_PASS="Password"
    val MY_KEY_ID="Identifiant"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences=getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)

        val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
        //si les informations ont déjà été sauvegardées
        if(sharedPreferences.getString(MY_KEY_ID,"")=="admin" && sharedPreferences.getString(MY_KEY_PASS,"")=="123"){
            //logger l'utilisateur directement
            startActivity(homeActivity)
        }


        //bouton valider
        myButtonValidate=findViewById<Button>(R.id.buttonVal) as Button
        myId=findViewById(R.id.myIdent)
        myPasswd=findViewById(R.id.myPass)


        //action qui s'exécute quand on clique sur le bouton "valider"
        myButtonValidate.setOnClickListener {
            val myIdentifiant=myId.text.toString()
            val myPassword=myPasswd.text.toString()
            //afficher un message
            if (myIdentifiant == "admin" && myPassword == "123") {
                //password correct
                Toast.makeText(this@LoginActivity, getString(R.string.welcome), Toast.LENGTH_LONG).show()
                //fonction de sauvegarde
                saveInformations(myIdentifiant, myPassword)
                //passage à la page home
                //val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(homeActivity)


            } else {
                //password incorrect
                //Apparition d'un toast qui indique de réessayer
                Toast.makeText(this@LoginActivity, getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun saveInformations(identifiant:String,mdp:String){
        val sharedPreferences=getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        //sauvegarde des données
        editor.putString(MY_KEY_ID,identifiant)
        editor.putString(MY_KEY_PASS,mdp)
        editor.apply()
    }

}
