package com.example.test.admin.article;

import androidx.lifecycle.ViewModel;

import com.example.test.model.Article;
import com.example.test.model.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticleViewModel extends ViewModel {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private final List<Article> articles = new ArrayList<>();

    public void getArticles(OnSuccessListener<List<Article>> listener) {
        databaseReference.child("articles").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Article> articles = new ArrayList<>();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Article article = dataSnapshot.getValue(Article.class);
                    if (article != null) articles.add(article);
                }
                this.articles.clear();
                this.articles.addAll(articles);
                listener.onSuccess(articles);
            }
        });
    }

    public void createArticle(String title, String articleId, Boolean isRandom, OnSuccessListener<String> listener) {
        if (title.isEmpty() || articleId == null || articleId.isEmpty()) {
            return;
        }
        Article article = articles.stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) return;

        Task task = Task.fromArticle(article, title, isRandom);

        LocalDate localDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);


        databaseReference.child("tasks").child(formattedDate).child(task.getId()).setValue(task).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) listener.onSuccess("Nhiệm vụ đã tạo thành công");
        });
    }

    public void loadArticle(String articleId, OnSuccessListener<Article> listener) {
        if (articleId == null || articleId.isEmpty()) {
            listener.onSuccess(null);
            return;
        }
        Article article = articles.stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        listener.onSuccess(article);
    }
}
