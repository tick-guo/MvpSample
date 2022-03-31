package com.example.xw.mvpsample.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.BindViews;
import com.example.xw.mvpsample.R;
import com.example.xw.mvpsample.bean.User;
import com.example.xw.mvpsample.mvp.presenter.MainPresenter;
import com.example.xw.mvpsample.mvp.view.BaseView;

import butterknife.ButterKnife;
//import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.tv)
    TextView mTextView;
    @BindView(R.id.search_btn)
    Button mButton;
    @BindView(R.id.ed_text)
    EditText mEditText;

    private  ProgressDialog dialog;
    private MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mMainPresenter=new MainPresenter();
        mMainPresenter.attachView(this);
        //
        //mTextView.setText("结果:");

    }

    /**
     * 一些初始化，这里为ProgressDialog的初始化
     */
    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在搜索中");
    }

    @OnClick(R.id.search_btn)
    void search(View view){
        Log.v("","点击按钮");
        mMainPresenter.searchUser(mEditText.getText().toString());
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void showText(User userbean) {
        String temp=getResources().getString(R.string.user_format);
        String str=String.format(temp,userbean.getLogin(),userbean.getName(),userbean.getFollowers(),userbean.getFollowing());
        str += "\n" + userbean.getHtml_url();
        str += "\n" + userbean.getFollowers_url();
        mTextView.setText(str);
    }

    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMainPresenter!=null)
        mMainPresenter.detachView();
    }
}
