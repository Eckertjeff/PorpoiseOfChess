package chess.of.porpoisegui.gui;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Gallery;

public class TutorialActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new TutorialAdapter(this));
    }
}
