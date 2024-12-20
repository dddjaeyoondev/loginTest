package com.Refee.RefeeDB

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class PostViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    fun fetchPosts() {
        val postList = mutableListOf<Post>()
        db.collection("posts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val post = document.toObject<Post>().copy(id = document.id)
                    postList.add(post)
                }
                _posts.value = postList
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching posts", e)
            }
    }
}
