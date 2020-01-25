package fr.isen.kelly.androidtoolbox


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.support.v4.app.*
import android.view.View
import android.widget.Button


class activity_cycle : AppCompatActivity(){

    var TAG:String="activity_cycle"
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
            val transaction = manager.beginTransaction()

            // Remplacer le fragment dans le conteneur
            transaction.replace(R.id.myFragment,textFragment)
            transaction.addToBackStack(null)

            // Finaliser la transition
            transaction.commit()
        }

        //Log.i(TAG, "Owner onCreate")
        lifecycle.addObserver(activity_cycleObserver())
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.log_view)
        startTextView.text="Etat actuel de l'activité : Owner onCreate"



    }


    override fun onStart() {
        super.onStart()
        //Log.i(TAG, "Owner onStart")
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.log_view)
        startTextView.text="Etat actuel de l'activité : Owner onStart"
    }

    override fun onPause() {
        super.onPause()
        //logs
        Log.i(TAG, "Owner onPause")
    }

    override fun onResume() {
        super.onResume()
        //Log.i(TAG, "Owner onResume")
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.log_view)
        startTextView.text="Etat actuel de l'activité : Owner onResume"
    }

    override fun onStop() {
        super.onStop()
        //logs
        Log.i(TAG, "Owner onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        //logs
        Log.i(TAG, "Owner onDestroy")
        //un toast quand l'activité est complètement détruite
        Toast.makeText(this@activity_cycle, "Destroy !", Toast.LENGTH_LONG).show()
    }

}
