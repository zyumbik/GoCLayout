package com.developer.zyumbik.goclayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
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

		    changeState(full1, brief1);
		    changeState(brief2, full2);
		    flip(arrow2);
		    changeState(brief3, full3);
		    flip(arrow3);

		    //Expand first
		    arrow1.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 1) {
					    //Collapse currently expanded card
					    if (expanded == 2) {
						    changeState(brief2, full2);
						    flip(arrow2);
					    } else {
						    changeState(brief3, full3);
						    flip(arrow3);
					    }
					    expanded = 1;
					    changeState(full1, brief1);
					    flip(arrow1);
				    }
			    }
		    });

		    //Expand second
		    arrow2.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 2) {
					    //Collapse currently expanded card
					    if (expanded == 1) {
						    changeState(brief1, full1);
						    flip(arrow1);
					    } else {
						    changeState(brief3, full3);
						    flip(arrow3);
					    }
					    expanded = 2;
					    changeState(full2, brief2);
					    flip(arrow2);
				    }
			    }
		    });

		    //Expand third
		    arrow3.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 3) {
					    //Collapse currently expanded card
					    if (expanded == 1) {
						    changeState(brief1, full1);
						    flip(arrow1);
					    } else {
						    changeState(brief2, full2);
						    flip(arrow2);
					    }
					    expanded = 3;
					    changeState(full3, brief3);
					    flip(arrow3);
				    }
			    }
		    });

	    }
    }

	public void changeState (final View expand, final View collapse) {
		expand.setVisibility(View.VISIBLE);
		collapse.setVisibility(View.GONE);
	}

	public void flip(final View arrow) {
		arrow.animate().rotationBy(180).start();
	}

}
