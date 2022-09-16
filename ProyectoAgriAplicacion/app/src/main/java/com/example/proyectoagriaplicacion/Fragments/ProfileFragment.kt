package com.example.proyectoagriaplicacion.Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class ProfileFragment : Fragment() {

    private val IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private var uploadTask: UploadTask? = null

    var tvNombres: TextView? = null
    var tvApellidos:TextView? = null
    var tvEmail:TextView? = null
    var tvDepartamento:TextView? = null
    var tvTelefono:TextView? = null

    var profileImage: ImageView? = null
    var reference: DatabaseReference? = null
    var fuser: FirebaseUser? = null
    var storageReference: StorageReference? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        tvNombres = root.findViewById(R.id.tvNombres)
        tvApellidos = root.findViewById(R.id.tvApellidos)
        tvEmail = root.findViewById(R.id.tvEmail)
        tvDepartamento = root.findViewById(R.id.tvDepartamento)
        tvTelefono = root.findViewById(R.id.tvTelefono)
        val profileImage = root.findViewById<ImageView>(R.id.profile_image)

        fuser = FirebaseAuth.getInstance().currentUser

        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser!!.uid)
        storageReference = FirebaseStorage.getInstance().getReference("uploads")

        profileImage.setOnClickListener(View.OnClickListener { openGallery() })
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)!!
                user.id == fuser!!.uid
                var tvNombres1 = root.findViewById<TextView>(R.id.tvNombres)
                var tvApellidos1 = root.findViewById<TextView>(R.id.tvApellidos)
                var tvEmail1 = root.findViewById<TextView>(R.id.tvEmail)
                var tvDepartamento1 = root.findViewById<TextView>(R.id.tvDepartamento)
                var tvTelefono1 = root.findViewById<TextView>(R.id.tvTelefono)


                    tvNombres1.setText(user.nombres)
                    tvApellidos1.setText(user.apellidos)
                    tvEmail1.setText(user.email)
                    tvDepartamento1.setText(user.departamento)
                    tvTelefono1.setText(user.telefono)
                    Glide.with(requireContext()).load(user.urlImagen).into(profileImage)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return root
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }
    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun uploadImage() {
        val pd = ProgressDialog(context)
        pd.setMessage("Subiendo")
        pd.show()
        if (imageUri != null) {
            val fileReference = storageReference!!.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(imageUri!!)
            )
            uploadTask = fileReference.putFile(imageUri!!)
            (uploadTask as UploadTask).continueWithTask(Continuation { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                fileReference.downloadUrl
            }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val mUri = downloadUri.toString()
                    val reference = FirebaseDatabase.getInstance().getReference("users")
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (snapshot in dataSnapshot.children) {
                                val user = snapshot.getValue(User::class.java)!!
                                user.id = fuser!!.uid

                                    if (user.id.equals(fuser!!.uid)) {

                                            user.id = user.id

                                            user.tipoUsuario = "Vendedor"

                                            user.urlImagen = mUri
                                            reference.child(fuser!!.uid).setValue(user)
                                    }

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(activity, "Cancelado", Toast.LENGTH_SHORT).show()
                        }
                    })
                    pd.dismiss()
                } else {
                    Toast.makeText(context, "Fallo!", Toast.LENGTH_SHORT).show()
                    pd.dismiss()
                }
            }).addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                pd.dismiss()
            }
        } else {
            Toast.makeText(context, "Ninguna imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val updateUser = User()

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            if (uploadTask != null && uploadTask!!.isInProgress()) {
                Toast.makeText(context, "Subiendo", Toast.LENGTH_SHORT).show()
            } else {
                uploadImage()
            }
        }
    }



}