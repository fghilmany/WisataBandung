package com.example.wisatabandung.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.example.wisatabandung.R
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import android.webkit.MimeTypeMap
import android.content.ContentResolver
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso


class SignupActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var ref : DatabaseReference
    private lateinit var storage : StorageReference
    private lateinit var photoLocation : Uri

    private var photoMax = 0
    private lateinit var username : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        progress_bar.visibility = View.INVISIBLE
        photoLocation =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.default_uri)
                + '/' + getResources().getResourceTypeName(R.drawable.default_uri) + '/' + getResources().getResourceEntryName(R.drawable.default_uri) )

        btn_daftar_baru.setOnClickListener(this)
        fab_add_foto.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_daftar_baru ->{
                username  = edt_username.text.toString()
                progress_bar.visibility = View.VISIBLE
                btn_daftar_baru.setText("")

                ref = FirebaseDatabase.getInstance().getReference().child("user").child(username)
                ref.addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext,"DB Bermasalah",Toast.LENGTH_SHORT).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()){
                            Toast.makeText(applicationContext,"Username sudah ada",Toast.LENGTH_SHORT).show()
                            progress_bar.visibility = View.INVISIBLE
                            btn_daftar_baru.setText("Login")
                        }else{
                            initFirebase()


                        }
                    }

                })
            }

            R.id.fab_add_foto -> {
                initFindPhoto()
            }
        }
    }

    private fun initFindPhoto() {
        val pic = Intent()
        pic.setType("image/*")
        pic.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pic,photoMax)
    }

    private fun initFirebase() {

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(username)
        storage = FirebaseStorage.getInstance().getReference().child("user_pic").child(username)

        val storageReference1 = storage.child(System.currentTimeMillis().toString() +
                "." +
                getFileExtension(photoLocation))

        storageReference1.putFile(photoLocation)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot>{
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {

                    storageReference1.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri>{
                        override fun onSuccess(p0: Uri?) {
                            val uri_photo = p0.toString()
                            ref.child("url_foto").setValue(uri_photo)
                            ref.child("username").setValue(edt_username.text.toString())
                            ref.child("email").setValue(edt_email.text.toString())
                            ref.child("password").setValue(edt_password.text.toString())
                            ref.child("name").setValue(edt_nama.text.toString())
                            ref.child("balance").setValue(0)
                            ref.child("username").setValue(edt_username.text.toString())


                        }

                    }).addOnCompleteListener(object : OnCompleteListener<Uri>{
                        override fun onComplete(p0: Task<Uri>) {
                            startActivity<SuccessSignupActivity>(
                                "username" to username
                            )
                            finish()

                        }

                    })
                }

            }).addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot>{
                override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {

                }

            })
    }

    fun getFileExtension(uri: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == photoMax && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            photoLocation = data.data
            Picasso.get().load(photoLocation).centerCrop().fit().into(iv_signup)

        }
    }
}
