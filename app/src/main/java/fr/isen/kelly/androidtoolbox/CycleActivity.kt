package fr.isen.kelly.androidtoolbox
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.widget.Button

class CycleActivity : AppCompatActivity(){

    var TAG:String="CycleActivity"
    lateinit var startTextView:TextView
    lateinit var button1:Button

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle)
        button1=findViewById<Button>(R.id.button1) as Button

        // Définir un click listener pour le widget du bouton
        button1.setOnClickListener {
            // Obtenir l'instance de fragment de texte
            val textFragment = FragmentCycle()
            // Obtenir l'instance du gestionnaire de fragment
            val manager = supportFragmentManager
            // Commencer la transition du fragment à l'aide du gestionnaire de fragments
            val transaction = manager.beginTransaction()//add(R.id.fragment,activitéFragment)

            // Remplacer le fragment dans le conteneur
            transaction.replace(R.id.myFragment,textFragment)
            transaction.addToBackStack(null)
            // Finaliser la transition
            transaction.commit()
        }

        //Log.i(TAG, "Owner onCreate")
        lifecycle.addObserver(ObserverCycle())
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.logView)
        startTextView.text="Etat actuel de l'activité : Owner onCreate"
    }

    //état onStart
    override fun onStart() {
        super.onStart()
        //Log.i(TAG, "Owner onStart")
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.logView)
        startTextView.text="Etat actuel de l'activité : Owner onStart"
    }
    //état onPause
    override fun onPause() {
        super.onPause()
        //logs
        Log.i(TAG, "Owner onPause")
    }
    //état onResume
    override fun onResume() {
        super.onResume()
        //Log.i(TAG, "Owner onResume")
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.logView)
        startTextView.text="Etat actuel de l'activité : Owner onResume"
    }
    //état onStop
    override fun onStop() {
        super.onStop()
        //logs
        Log.i(TAG, "Owner onStop")
    }
    //état onDestroy
    override fun onDestroy() {
        super.onDestroy()
        //logs
        Log.i(TAG, "Owner onDestroy")
        //un toast quand l'activité est complètement détruite
        Toast.makeText(this@CycleActivity, "Destroy !", Toast.LENGTH_LONG).show()
    }
}
