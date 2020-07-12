package com.example.mymvvmhabitdemo.ui.login;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.mymvvmhabitdemo.BR;
import com.example.mymvvmhabitdemo.R;
import com.example.mymvvmhabitdemo.app.AppViewModelFactory;
import com.example.mymvvmhabitdemo.databinding.ActivityLoginBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel>
{
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(LoginViewModel.class);
    }
}
