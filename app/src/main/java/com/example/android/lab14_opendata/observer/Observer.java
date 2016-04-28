package com.example.android.lab14_opendata.observer;

public interface Observer { // 觀察者 (監視、關注) 粉絲

    void OnCompleted();
    void OnError(String message);
}
