package fr.isen.kelly.androidtoolbox

import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*



class activity_login : AppCompatActivity() {
    private lateinit var my_Title:TextView
    private lateinit var my_Id:EditText
    private lateinit var myButtonValidate:Button
    private lateinit var myPass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        my_Title=findViewById(R.id.my_Title);
        my_Id=findViewById(R.id.my_Id);
        myButtonValidate=findViewById(R.id.button_validate);
        myButtonValidate.isEnabled=false; //désactiver le bouton au lancement de l'application
        //puis l'activer lorsqu'il a saisi ses infos
        myPass=findViewById(R.id.myPass)
        myButtonValidate.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            my_Id.setText("")
            myPass.setText("")
        }
        // set on-click listener
        myButtonValidate.setOnClickListener {
            val id = my_Id.text;
            val password = myPass.text;
            Toast.makeText(this@activity_login, id, Toast.LENGTH_LONG).show()
        }
    }
}
