package com.example.mymvvmhabitdemo.ui.login;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.mymvvmhabitdemo.data.DemoRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;
import io.reactivex.subjects.PublishSubject;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends BaseViewModel<DemoRepository> {

    public ObservableField<String> userName = new ObservableField<>("");

    public ObservableField<String> password = new ObservableField<>("");

    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();


    public LoginViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
        //从本地取得数据绑定到View层
        userName.set(model.getUserName());
        password.set(model.getPassword());

//        Observable.interval(1,TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                userName.set("ddd"+Math.random()*10);
//
//            }
//        });


    }

    public BindingCommand  clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           userName.set("");
        }
    });

    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            login();

        }
    });



    private void login(){
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        addSubscribe(model.login()
                    .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        showDialog();
                    }
                }).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dismissDialog();
                        model.saveUserName(userName.get());
                        model.savePassword(password.get());
                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
