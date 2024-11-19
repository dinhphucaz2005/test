package com.example.test.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.ui.dashboard.Article;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<Article> article = new MutableLiveData<>(new Article());

    public LiveData<Article> observeArticle() {
        return article;
    }

}
