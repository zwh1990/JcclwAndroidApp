package com.zwh.jcclwapplication.acts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zwh.jcclwapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btOne;
    private Button btTwo;
    private Button btThree;
    private Button btFour;
    private Button btFive;
    private Button btGetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        btOne = findViewById(R.id.bt_one);
        btTwo = findViewById(R.id.bt_two);
        btThree = findViewById(R.id.bt_three);
        btFour = findViewById(R.id.bt_four);
        btFive = findViewById(R.id.bt_five);
        btGetImage = findViewById(R.id.bt_get_image);

        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        btThree.setOnClickListener(this);
        btFour.setOnClickListener(this);
        btFive.setOnClickListener(this);
        btGetImage.setOnClickListener(this);

//        Glide.with(this)
//                .load("http://192.168.8.91:8081/learnspringboot/images?name=1-3.jpg")
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_one:
                AddPictureTwoActivity.launchSelf(this,"1");
                break;

            case R.id.bt_two:
                AddPictureTwoActivity.launchSelf(this,"2");
                break;

            case R.id.bt_three:
                AddPictureTwoActivity.launchSelf(this,"3");
                break;

            case R.id.bt_four:
                AddPictureTwoActivity.launchSelf(this,"4");
                break;

            case R.id.bt_five:
                AddPictureTwoActivity.launchSelf(this,"5");
                break;

            case R.id.bt_get_image:
                GetImageActivity.launchSelf(this);
                break;
            default:
                break;

        }
    }
}