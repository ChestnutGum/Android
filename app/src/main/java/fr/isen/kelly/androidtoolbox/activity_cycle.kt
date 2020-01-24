package fr.isen.kelly.androidtoolbox


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast


class activity_cycle : AppCompatActivity() {

    var TAG:String="activity_cycle"
    lateinit var startTextView:TextView

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle)
        //Log.i(TAG, "Owner onCreate")
        lifecycle.addObserver(activity_cycleObserver())
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.log_view)
        startTextView.text="Owner onCreate"



    }
    override fun onStart() {
        super.onStart()
        //Log.i(TAG, "Owner onStart")
        //affichage à l'écran dans un TextView
        //modification du TextView
        startTextView= findViewById(R.id.log_view)
        startTextView.text="Owner onStart"
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
        startTextView.text="Owner onResume"
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
