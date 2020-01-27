package fr.isen.kelly.androidtoolbox
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //on récupère l'identifiant
        my_Id=findViewById(R.id.my_Id)
        //bouton valider
        myButtonValidate=findViewById(R.id.button_validate) as Button
        //myButtonValidate.isEnabled=false //désactiver le bouton au lancement de l'application
        //puis l'activer lorsqu'il a saisi ses infos
        //on récupère le mot de passe
        myPass=findViewById(R.id.myPass)
//saveUserCredential(identifiant:String,mdp:String)
        //.apply et pas commit()
        //val savedIdentifiant=
        //supprimer la sauvegarde
        //editor.clear
        //intent.addFlags(intent.
        //gotoHome(savedIdentifiant,true) redirection/connexion automatique
        //action qui s'exécute quand on clique sur le bouton "valider"
        myButtonValidate.setOnClickListener {
            //afficher un message
            if (my_Id.getText().toString().equals("admin") && myPass.getText().toString().equals("admin"))
            {
                //password correct
                Toast.makeText(this@LoginActivity, "Bienvenue !", Toast.LENGTH_LONG).show()
                //passage à la page home
                val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(homeActivity)
            }
            else
            {
                //password incorrect
                //Apparition d'un toast qui indique de réessayer
                Toast.makeText(this@LoginActivity, "Try Again...", Toast.LENGTH_LONG).show()
            }
        }
    }
}
