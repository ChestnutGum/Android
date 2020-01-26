package fr.isen.kelly.androidtoolbox
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class activity_login : AppCompatActivity() {
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

        //action qui s'exécute quand on clique sur le bouton "valider"
        myButtonValidate.setOnClickListener {
            //afficher un message
            if (my_Id.getText().toString().equals("admin") && myPass.getText().toString().equals("admin"))
            {
                //password correct
                Toast.makeText(this@activity_login, "Bienvenue !", Toast.LENGTH_LONG).show()
                //passage à la page home
                val homeActivity = Intent(this@activity_login, activity_home::class.java)
                startActivity(homeActivity)
            }
            else
            {
                //password incorrect
                //Apparition d'un toast qui indique de réessayer
                Toast.makeText(this@activity_login, "Try Again...", Toast.LENGTH_LONG).show()
            }
        }
    }
}
