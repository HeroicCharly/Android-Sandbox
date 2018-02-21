package heroiccharly.helloworld;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Global variables
    private int ToastDuration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button acceptBtn = findViewById(R.id.btn_accept);


        acceptBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Context context = getApplicationContext();
                CharSequence text = ((EditText)findViewById(R.id.toastMessage)).getText();

                if(text.length() > 0) {
                    Toast toast = Toast.makeText(context, text, ToastDuration);
                    toast.show();
                }
            }
        });

    }
}
