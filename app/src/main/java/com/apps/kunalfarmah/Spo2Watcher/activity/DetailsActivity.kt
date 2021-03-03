package com.apps.kunalfarmah.Spo2Watcher.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.apps.kunalfarmah.Spo2Watcher.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    lateinit var name:String
    lateinit var phone:String
    lateinit var eph1:String
    lateinit var eph2:String
    lateinit var userID:String
    lateinit var db:FirebaseDatabase
    lateinit var userRef:DatabaseReference
    lateinit var userEmergencyRef:DatabaseReference
    lateinit var mcuId:String
    lateinit var detailPrefs:SharedPreferences
    lateinit var sharedPreferences: SharedPreferences
    var isEdit:Boolean = false

     companion object {
         val DETAILS = "DETAILS"
         val NAME = "NAME"
         val CONTACT = "CONTACT"
         val UID = "UID"
         val EPH1 = "EPH1"
         val EPH2 = "EPH2"
         val NODE_MCU = "NODE_MCU"
         val MCUID = "MCU_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isEdit = intent.extras!!.getBoolean("editing",false)
        userID = FirebaseAuth.getInstance().uid!!
        db = FirebaseDatabase.getInstance()


        detailPrefs = getSharedPreferences(DETAILS, Context.MODE_PRIVATE)
        sharedPreferences = getSharedPreferences(NODE_MCU, MODE_PRIVATE)

        mcuId = sharedPreferences.getString(MCUID,"")!!
        if(!mcuId.isNullOrEmpty()){
            binding.mcuEt.setText(mcuId)
        }

        name = detailPrefs.getString(NAME,"")!!
        phone = detailPrefs.getString(CONTACT,"")!!
        eph1 = detailPrefs.getString(EPH1,"")!!
        eph2 = detailPrefs.getString(EPH2,"")!!

        if(!name.isNullOrEmpty())
            binding.nameEt.setText(name)

        if(!phone.isNullOrEmpty())
            binding.phoneEt.setText(phone)

        if(!eph1.isNullOrEmpty())
            binding.emergencyphone1Et.setText(eph1)

        if(!eph2.isNullOrEmpty())
            binding.emergencyphone2Et.setText(eph2)

        binding.submit.setOnClickListener {

            name = binding.nameEt.text.toString()
            phone = binding.phoneEt.text.toString()
            eph1 = binding.emergencyphone1Et.text.toString()
            eph2 = binding.emergencyphone2Et.text.toString()
            mcuId = binding.mcuEt.text.toString()

            if(mcuId.isEmpty()){
                Toast.makeText(applicationContext,"Please Enter NodeMCU id",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(name.isEmpty()){
                Toast.makeText(applicationContext,"Please Enter your Name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            detailPrefs.edit().putString(NAME,name).putString(CONTACT,phone)
                    .putString(EPH1,eph1).putString(EPH2,eph2).putString(UID,userID).apply()

            sharedPreferences.edit().putString(MCUID,mcuId).apply()

            var user = UserDetails(userID,name,phone)
            var emergency = Emergency(eph1,eph2)

            userRef = db.reference.child("sensors").child(mcuId).child("user")
            userEmergencyRef = db.reference.child("sensors").child(mcuId).child("contacts")

            userRef.setValue(user)
            userEmergencyRef.setValue(emergency)

            var intent = Intent(this,ArduinoActivity::class.java)
            intent.putExtra("id",getSharedPreferences(NODE_MCU, MODE_PRIVATE).getString(MCUID,""))
            this.finish()
            if(!isEdit)
                startActivity(intent)


        }


    }

    data class UserDetails(val id:String?, val name:String?, val contact:String?){
    }

    data class Emergency(val ph1:String? , val ph2:String?){
    }
}