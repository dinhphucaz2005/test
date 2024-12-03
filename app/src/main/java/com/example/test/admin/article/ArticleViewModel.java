package com.example.test.admin.article;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.FirebaseKey;
import com.example.test.helper.DateFormatted;
import com.example.test.model.Article;
import com.example.test.model.ArticleStatus;
import com.example.test.model.Task;
import com.example.test.model.TaskNotification;
import com.example.test.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void createTask(String title, String articleId, Boolean isRandom, OnSuccessListener<String> listener, OnSuccessListener<String> errorListener) {
        if (title.isEmpty() || articleId == null || articleId.isEmpty()) {
            errorListener.onSuccess("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        Article article = articles.stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) return;

        if (collectingStaff == null) {
            errorListener.onSuccess("Vui lòng chọn nhân viên thu thập");
            return;
        }

        Task task = Task.fromArticle(article, title, isRandom, Objects.requireNonNull(collectingStaff.getValue()).getUid());

        String dateCollectFormatted = DateFormatted.get(article.getDateCollected());

        databaseReference.child(FirebaseKey.TASKS).child(dateCollectFormatted).child(task.getId()).setValue(task).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                listener.onSuccess("Nhiệm vụ đã tạo thành công");

                article.setArticleStatus(ArticleStatus.DONE);
                databaseReference.child(FirebaseKey.ARTICLES).child(articleId).setValue(article);

                // send notification for staff
                TaskNotification taskNotification = new TaskNotification(task.getId(), task.getArticleId());

                taskNotification.setTaskId(task.getId());
                taskNotification.setTitle("Nhiệm vụ mới vào ngày " + dateCollectFormatted);
                taskNotification.setDataCollectFormated(DateFormatted.get(task.getDateCollected()));

                databaseReference
                        .child(FirebaseKey.USERS)
                        .child(collectingStaff.getValue().getUid())
                        .child(FirebaseKey.NOTIFICATIONS)
                        .child(taskNotification.getId())
                        .setValue(taskNotification);
                // send notification for user

                TaskNotification taskNotificationUser = new TaskNotification(task.getId(), task.getArticleId());
                taskNotificationUser.setTitle("Bài đăng của bạn đã được duyệt và được thu thập vào ngày " + dateCollectFormatted);
                taskNotificationUser.setDataCollectFormated(DateFormatted.get(task.getDateCollected()));

                databaseReference
                        .child(FirebaseKey.USERS)
                        .child(article.getUserId())
                        .child(FirebaseKey.NOTIFICATIONS)
                        .child(taskNotificationUser.getId())
                        .setValue(taskNotificationUser);
            }
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

    private final MutableLiveData<User> collectingStaff = new MutableLiveData<>(null);

    public LiveData<User> observeCollectingStaff() {
        return collectingStaff;
    }

    public void setCollectingStaff(User user) {
        collectingStaff.postValue(user);
    }

    public void updateArticleStatus(String articleId, Integer done) {
        Article article = articles.stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) return;
        article.setArticleStatus(done);
        databaseReference.child(FirebaseKey.ARTICLES).child(articleId).setValue(article);
    }
}
