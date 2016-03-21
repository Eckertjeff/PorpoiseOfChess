package chess.of.porpoisegui.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OnePlayerActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);
    }

    public void selStart(View view)
    {
        Intent startIntent = new Intent(getApplicationContext(), MainBoard.class);

        EditText firstPlayer = (EditText) findViewById(R.id.txtFirstPlayer);

        String firstPlyStr = firstPlayer.getText().toString();
        String secondPlyStr = getString(R.string.computer_name);

        startIntent.putExtra("FIRST_PLAYER", firstPlyStr);
        startIntent.putExtra("SECOND_PLAYER", secondPlyStr);

        startActivity(startIntent);
    }
}
