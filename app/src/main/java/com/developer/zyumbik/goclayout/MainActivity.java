package com.developer.zyumbik.goclayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	ImageButton arrow1, arrow2, arrow3;
	TextView full1, full2, full3;
	TextView brief1, brief2, brief3;
	int expanded = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    if (!new View(this).isInEditMode()) {

		    arrow1 = (ImageButton) findViewById(R.id.arrow1);
		    arrow2 = (ImageButton) findViewById(R.id.arrow2);
		    arrow3 = (ImageButton) findViewById(R.id.arrow3);
		    full1 = (TextView) findViewById(R.id.full_description1);
		    full2 = (TextView) findViewById(R.id.full_description2);
		    full3 = (TextView) findViewById(R.id.full_description3);
		    brief1 = (TextView) findViewById(R.id.brief_description1);
		    brief2 = (TextView) findViewById(R.id.brief_description2);
		    brief3 = (TextView) findViewById(R.id.brief_description3);

		    //Default view
		    full1.setVisibility(View.VISIBLE);
		    full2.setVisibility(View.GONE);
		    full3.setVisibility(View.GONE);
		    brief1.setVisibility(View.GONE);
		    brief2.setVisibility(View.VISIBLE);
		    brief3.setVisibility(View.VISIBLE);

		    //Expand first
		    arrow1.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 1) {
					    expanded = 1;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    full1.setVisibility(View.VISIBLE);
					    full2.setVisibility(View.GONE);
					    full3.setVisibility(View.GONE);
					    brief1.setVisibility(View.GONE);
					    brief2.setVisibility(View.VISIBLE);
					    brief3.setVisibility(View.VISIBLE);
				    }
			    }
		    });

		    //Expand second
		    arrow2.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 2) {
					    expanded = 2;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    full1.setVisibility(View.GONE);
					    full2.setVisibility(View.VISIBLE);
					    full3.setVisibility(View.GONE);
					    brief1.setVisibility(View.VISIBLE);
					    brief2.setVisibility(View.GONE);
					    brief3.setVisibility(View.VISIBLE);
				    }
			    }
		    });

		    //Expand third
		    arrow3.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 3) {
					    expanded = 3;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    full1.setVisibility(View.GONE);
					    full2.setVisibility(View.GONE);
					    full3.setVisibility(View.VISIBLE);
					    brief1.setVisibility(View.VISIBLE);
					    brief2.setVisibility(View.VISIBLE);
					    brief3.setVisibility(View.GONE);
				    }
			    }
		    });

	    }

    }
}
