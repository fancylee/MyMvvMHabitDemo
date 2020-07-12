package com.example.mymvvmhabitdemo.data;

import androidx.annotation.NonNull;

import com.example.mymvvmhabitdemo.data.source.HttpDataSource;
import com.example.mymvvmhabitdemo.data.source.LocalDataSource;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

public class DemoRepository extends BaseModel implements HttpDataSource, LocalDataSource {

    private volatile static DemoRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private DemoRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static DemoRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public Observable<Object> login() {
        return mHttpDataSource.login();
    }

    @Override
    public void saveUserName(String userName) {

        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {

        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }
}
