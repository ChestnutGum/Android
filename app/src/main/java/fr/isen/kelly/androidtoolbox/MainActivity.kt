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




class MainActivity : AppCompatActivity() {
    private lateinit var my_Title:TextView
    private lateinit var my_Id:EditText
    private lateinit var myButtonValidate:Button
    private lateinit var myPass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_Title=findViewById(R.id.my_Title)
        my_Id=findViewById(R.id.my_Id)
        myButtonValidate=findViewById(R.id.button_validate)
        //myButtonValidate.isEnabled=false //désactiver le bouton au lancement de l'application
        //puis l'activer lorsqu'il a saisi ses infos
        myPass=findViewById(R.id.myPass)
        myButtonValidate.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            my_Id.setText("")
            myPass.setText("")
        }

        // set on-click listener
        myButtonValidate.setOnClickListener {
            val id = my_Id.text
            val password = myPass.text
            Toast.makeText(this@MainActivity, "you clicked", Toast.LENGTH_LONG).show()
            val homeActivity = Intent(this@MainActivity, activity_home::class.java)
            startActivity(homeActivity)
        }

        my_Id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // This is where we'll check the user input
                //myButtonValidate.isEnabled = s.toString().length() != 0

            }

            override fun afterTextChanged(s: Editable) {

            }

        })
        //myButtonValidate.setOnClickListener(object : View.OnClickListener() {
            //fun onClick(v: View) {
                // The user just clicked
           // }
        //})


    }
}
