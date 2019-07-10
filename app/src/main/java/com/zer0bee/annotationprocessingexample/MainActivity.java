package com.zer0bee.annotationprocessingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zer0bee.annotations.BindView;
import com.zer0bee.annotations.OnClick;
import com.zer0bee.binder.Binding;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btHello)
  Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Binding.bind(this);
  }

  @OnClick(R.id.btHello)
  void btClick(View v) {
    Toast.makeText(v.getContext(), "Hello World!!", Toast.LENGTH_SHORT).show();
  }
}
