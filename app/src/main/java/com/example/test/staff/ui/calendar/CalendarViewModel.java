package com.example.test.staff.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.FirebaseKey;
import com.example.test.model.Article;
import com.example.test.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>(new ArrayList<>());

    public void loadTasks(int dayOfMonth) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        Month currentMonth = Month.of(month);
        if (dayOfMonth < 1 || dayOfMonth > currentMonth.length(LocalDate.now().isLeapYear())) {
            return;
        }

        String tasksOfDay = year + "-" + month + "-" + dayOfMonth; // tasksOfDay is a string in the format "yyyy-MM-dd"

        databaseReference.child(FirebaseKey.TASKS).child(tasksOfDay).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Task temp = dataSnapshot.getValue(Task.class);
                    if (temp != null) tasks.add(temp);
                }
                this.tasks.postValue(tasks);
            }
        });
    }

    public void loadTasksOfToday() {
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        loadTasks(dayOfMonth);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    private final MutableLiveData<Article> article = new MutableLiveData<>();

    public LiveData<Article> getArticle() {
        return article;
    }

    public void loadArticle(String articleId) {
        databaseReference.child(FirebaseKey.ARTICLES).child(articleId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Article article = task.getResult().getValue(Article.class);
                if (article != null) this.article.postValue(article);
            }
        });
    }
}
