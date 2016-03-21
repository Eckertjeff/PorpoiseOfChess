package chess.of.porpoisegui.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OptionsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void selAI(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), AIActivity.class);

        startActivity(startIntent);
    }
}
