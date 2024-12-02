package com.example.test.staff.ui.calendar;

import static com.example.test.helper.DateFormatted.DAY_IN_WEEK;

import android.util.Log;

import androidx.collection.PackingUtilsKt;
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
import java.util.Objects;

import retrofit2.http.PUT;

public class CalendarViewModel extends ViewModel {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>(new ArrayList<>());


    public void loadTasks(String dateFormatted, String staffId) {
        databaseReference.child(FirebaseKey.TASKS).child(dateFormatted).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Task temp = dataSnapshot.getValue(Task.class);
                    if (temp != null && Objects.equals(temp.getCollectingStaffId(), staffId))
                        tasks.add(temp);
                }
                this.tasks.postValue(tasks);
            }
        });
    }

    public void loadTasks(int dayOfMonth, String staffId) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        Month currentMonth = Month.of(month);
        if (dayOfMonth < 1 || dayOfMonth > currentMonth.length(LocalDate.now().isLeapYear())) {
            return;
        }
        String tmp;
        if (dayOfMonth < 10) tmp = "0" + dayOfMonth;
        else tmp = String.valueOf(dayOfMonth);
        String tasksOfDay = year + "-" + month + "-" + tmp; // tasksOfDay is a string in the format "yyyy-MM-dd"
        Log.d("PHUC", "loadTasks: " + tasksOfDay);
        databaseReference.child(FirebaseKey.TASKS).child(tasksOfDay).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Task temp = dataSnapshot.getValue(Task.class);
                    if (temp != null && Objects.equals(temp.getCollectingStaffId(), staffId))
                        tasks.add(temp);
                }
                this.tasks.postValue(tasks);
            }
        });
    }

    public void loadTasksOfToday(String staffId) {
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        loadTasks(dayOfMonth, staffId);
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

    private List<LocalDate> getLocalDates(LocalDate today) {
        List<LocalDate> localDates = new ArrayList<>();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % DAY_IN_WEEK);
        for (int i = 0; i < DAY_IN_WEEK; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            localDates.add(day);
        }
        return localDates;
    }

    private final MutableLiveData<List<LocalDate>> localDates = new MutableLiveData<>(getLocalDates(LocalDate.now()));

    public LiveData<List<LocalDate>> getLocalDates() {
        return localDates;
    }

    public void previousWeek() {
        LocalDate today = Objects.requireNonNull(localDates.getValue()).get(0);
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % DAY_IN_WEEK);
        localDates.postValue(getLocalDates(startOfWeek.minusDays(DAY_IN_WEEK)));
        tasks.postValue(new ArrayList<>());
    }

    public void nextWeek() {
        LocalDate today = Objects.requireNonNull(localDates.getValue()).get(0);
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % DAY_IN_WEEK);
        localDates.postValue(getLocalDates(startOfWeek.plusDays(DAY_IN_WEEK)));
        tasks.postValue(new ArrayList<>());
    }



}
