package bupt.liao.fred.flipview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bupt.liao.fred.flipviewlibrary.FlipView;

public class MainActivity extends AppCompatActivity {

    FlipView flipView;
    Button btnStart;
    Button btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipView = (FlipView)findViewById(R.id.flip);
        btnStart = (Button)findViewById(R.id.start);
        btnStop = (Button)findViewById(R.id.stop);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                flipView.startFlip();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flipView.startFlip();
                    }
                });
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipView.stopFlip();
            }
        });
    }
}
