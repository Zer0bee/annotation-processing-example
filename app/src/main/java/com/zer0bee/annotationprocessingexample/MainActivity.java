package com.zer0bee.annotationprocessingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.zer0bee.annotations.BindView;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btHello)
  Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //button.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    Toast.makeText(ReflectionExampleActivity.this, "Hello World!!", Toast.LENGTH_SHORT).show();
    //  }
    //});
  }
}
