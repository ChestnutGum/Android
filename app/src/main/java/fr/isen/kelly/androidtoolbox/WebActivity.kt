package fr.isen.kelly.androidtoolbox

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_informations.*
import kotlinx.android.synthetic.main.activity_web.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray



@Suppress("DEPRECATION")
class WebActivity : AppCompatActivity() {
    var urlData:String="https://randomuser.me"
    val startTime=System.currentTimeMillis()
    var listUsers = ArrayList<User>()
    lateinit var myTextView:TextView
    //var recyclerView:RecyclerView=findViewById(R.id.listContactApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        myTextView=findViewById(R.id.textViewTest)
        //getUsers()

        listContactApi.setHasFixedSize(true)
        listContactApi.layoutManager = LinearLayoutManager(this)

        listContactApi.adapter = UsersAdapter(loadUrlData())
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listContactApi.addItemDecoration(itemDecoration)
        //var usersList:ArrayList<User>
    }

    private fun loadUrlData():List<User> {
        val stringRequest=StringRequest(Request.Method.GET,urlData,Response.Listener<String>{response ->
            fun onResponse(response:String){
                try {
                    var jsonObject:JSONObject= JSONObject(response)
                    var array: JSONArray=jsonObject.getJSONArray("items")
                    for(i in 0 until array.length()){
                        var jo:JSONObject=array.getJSONObject(i)
                        var myUser:User=User(jo.getString("name"),jo.getString("email"),jo.getString("location"))
                        listUsers.add(myUser)
                        //textViewTest.text=myUser.toString()
                    }
                    //listContactApi.adapter=UsersAdapter(listUsers)
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }
        },Response.ErrorListener(){
            fun onErrorResponse(error: VolleyError){
                Toast.makeText(this,"Error"+error.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        //val adapter=UsersAdapter(users))

        return listUsers
    }




    @SuppressLint("SetTextI18n")
    private fun getUsers(){
        val queue= Volley.newRequestQueue(this)
        val url:String="https://randomuser.me"
        val stringReq=StringRequest(Request.Method.GET,url,Response.Listener<String> { response ->
            val strResp = response.toString()
            val jsonObj= JSONObject(strResp)
            val jsonArray:JSONArray=jsonObj.getJSONArray("items")
            var strUser:String=""
            for(i in 0 until jsonArray.length()){
                val jsonInner:JSONObject=jsonArray.getJSONObject(i)
                strUser=strUser+"\n"+jsonInner.get("login")
            }
            textViewTest.text="response : $strUser"
        },
            Response.ErrorListener { textViewTest.text="That didn't work !" })
        queue.add(stringReq)
    }
















    private fun sendRequest(url:URL): InputStream? {
        try{
            myList.layoutManager = LinearLayoutManager(this)

            val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            myList.addItemDecoration(itemDecoration)
            val urlConnection=url.openConnection() as HttpURLConnection
            urlConnection.connect()
            if(urlConnection.responseCode===HttpURLConnection.HTTP_OK){
                val inputStream=urlConnection.inputStream
                //myList.adapter = UsersAdapter(readIt(inputStream))
                val data=readIt(inputStream)
            }
        }
        catch(e:Exception){
            throw Exception("")
        }
        return null
    }

    private fun readIt(stream:InputStream):String{
        val builder=StringBuilder()
        val reader=BufferedReader(InputStreamReader(stream))
        val line:String=reader.readLine()
        while(line!=null){
            builder.append(line+"\n")
        }
        return builder.toString()
    }


    private fun readStream(myVar:InputStream): ArrayList<User> {
        val userNameList = arrayListOf<User>()
        val sb=StringBuilder()
        val r=BufferedReader(InputStreamReader(myVar),1000)
        var line=r.readLine()
        while(line!=null){
            sb.append(line)
            line=r.readLine()
            userNameList.add(User(line,"null","null"))
        }
        myVar.close()
        return userNameList//sb.toString()
    }

    /*private fun displayUserApi() {
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter = UsersAdapter(loadUsersApi())
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        myList.addItemDecoration(itemDecoration)
    }*/
/*
    private fun loadUsersApi(): List<User> {
        val userNameList = arrayListOf<User>()
        val phoneCursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        phoneCursor?.let { cursor ->
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                //je m'étais servi du isOnline (booleen) pour pouvoir envoyer un message quand le contact est en ligne par exemple
                //j'ai gardé la mise en forme pour pouvoir éventuellement rajouter cette fonctionnalité à l'avenir
                userNameList.add(User(name, ))
            }
            cursor.close()
        }
        return userNameList
    }*/






}
