package fr.isen.kelly.androidtoolbox
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class MainActivity : AppCompatActivity() {
    private lateinit var my_Title:TextView
    private lateinit var my_Id:EditText
    private lateinit var myButtonValidate:Button
    private lateinit var myPass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //titre
        my_Title=findViewById(R.id.my_Title)
        //on récupère l'identifiant
        my_Id=findViewById(R.id.my_Id)
        //bouton valider
        myButtonValidate=findViewById(R.id.button_validate) as Button
        //myButtonValidate.isEnabled=false //désactiver le bouton au lancement de l'application
        //puis l'activer lorsqu'il a saisi ses infos
        //on récupère le mot de passe
        myPass=findViewById(R.id.myPass)



        // set on-click listener
        myButtonValidate.setOnClickListener {

            //afficher un message
            if (my_Id.getText().toString().equals("admin") && myPass.getText().toString().equals("admin"))
            {
                //correct password
                Toast.makeText(this@MainActivity, "Bienvenue !", Toast.LENGTH_LONG).show()
                //passage à la page home
                val homeActivity = Intent(this@MainActivity, activity_home::class.java)
                startActivity(homeActivity)
            }
            else
            {
                //wrong password
                Toast.makeText(this@MainActivity, "Try Again...", Toast.LENGTH_LONG).show()
            }

        }


    }
}
