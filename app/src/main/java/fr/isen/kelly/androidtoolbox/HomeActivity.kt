package fr.isen.kelly.androidtoolbox
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class HomeActivity : AppCompatActivity() {

    private lateinit var myButtonCycle:ImageButton
    private lateinit var myButtonSave:ImageButton
    private lateinit var myButtonDisconnect:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //bouton de cycle de vie
        myButtonCycle=findViewById(R.id.button_cycle)
        //bouton de sauvegarde
        myButtonSave=findViewById(R.id.button_save)
        //bouton de déconnexion
        myButtonDisconnect=findViewById(R.id.disconnectButton)

        //action qui s'exécute quand on clique sur le bouton "cycle de vie"
        myButtonCycle.setOnClickListener {
            //redirection vers l'activité CycleActivity
            val CycleActivity = Intent(this@HomeActivity, CycleActivity::class.java)
            startActivity(CycleActivity)
        }
        //action qui s'exécute quand on clique sur le bouton "Sauvegarde"
        myButtonSave.setOnClickListener {
            //redirection vers l'activité StorageActivity
            val StorageActivity = Intent(this@HomeActivity, StorageActivity::class.java)
            startActivity(StorageActivity)
        }


        myButtonDisconnect.setOnClickListener {
            //redirection vers la page de log
            val sharedPreferences=getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            var editor=sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))

        }

    }
}
