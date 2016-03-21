package chess.of.porpoisegui.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartUpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
    }

    public void selOnePlayer(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), OnePlayerActivity.class);

        startActivity(startIntent);
    }

    public void selTwoPlayer(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), TwoPlayerActivity.class);

        startActivity(startIntent);
    }

    public void selTutorial(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), TutorialActivity.class);

        startActivity(startIntent);
    }

    public void selOptions(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), OptionsActivity.class);

        startActivity(startIntent);
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
