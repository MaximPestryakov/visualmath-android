package com.example.android.paint;

import com.example.android.paint.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        paintView = (PaintView) findViewById(R.id.paintView);

        final Button button = (Button) findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                String texcode = "\\sin{x}";
                resultIntent.setData(Uri.parse(texcode));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.normal) {
            paintView.normal();
            return true;
        } else if (item.getItemId() == R.id.normal) {
            paintView.emboss();
            return true;
        } else if (item.getItemId() == R.id.blur) {
            paintView.blur();
            return true;
        } else if (item.getItemId() == R.id.clear) {
            paintView.clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
