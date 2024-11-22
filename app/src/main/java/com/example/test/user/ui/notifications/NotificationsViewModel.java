package com.example.test.user.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.Article;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<Article>> articles = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Article>> observeArticle() {
        return articles;
    }

    public void loadData(String userId) {
        if (userId == null) {
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("articles").get().addOnSuccessListener(dataSnapshot -> {
            List<Article> articleList = new ArrayList<>();
            for (var snapshot : dataSnapshot.getChildren()) {
                Article article = snapshot.getValue(Article.class);
                if (article != null && article.getUserId().equals(userId)) {
                    articleList.add(article);
                    articles.setValue(articleList);
                }
            }
        });
    }
}
