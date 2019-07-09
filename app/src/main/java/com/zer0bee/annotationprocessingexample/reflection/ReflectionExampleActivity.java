package com.zer0bee.annotationprocessingexample.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zer0bee.annotationprocessingexample.R;
import com.zer0bee.annotations.ReflectionBindView;
import com.zer0bee.binder.reflection.ViewBinder;

public class ReflectionExampleActivity extends AppCompatActivity {

  @ReflectionBindView(R.id.btHello)
  Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ViewBinder.bind(this);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(ReflectionExampleActivity.this, "Hello World!!", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
