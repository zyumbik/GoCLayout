package com.developer.zyumbik.goclayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

	ImageButton arrow1, arrow2, arrow3;
	TextView full1, full2, full3;
	TextView brief1, brief2, brief3;
	CardView card1, card2, card3;
	int expanded = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    if (!new View(this).isInEditMode()) {

//		    Firebase.setAndroidContext(this);

		    arrow1 = (ImageButton) findViewById(R.id.arrow1);
		    arrow2 = (ImageButton) findViewById(R.id.arrow2);
		    arrow3 = (ImageButton) findViewById(R.id.arrow3);
		    full1 = (TextView) findViewById(R.id.full_description1);
		    full2 = (TextView) findViewById(R.id.full_description2);
		    full3 = (TextView) findViewById(R.id.full_description3);
		    brief1 = (TextView) findViewById(R.id.brief_description1);
		    brief2 = (TextView) findViewById(R.id.brief_description2);
		    brief3 = (TextView) findViewById(R.id.brief_description3);
		    card1 = (CardView) findViewById(R.id.card_view1);
		    card2 = (CardView) findViewById(R.id.card_view2);
		    card3 = (CardView) findViewById(R.id.card_view3);

		    // Default view
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
					    if (Build.VERSION.SDK_INT > 20) {
						    card1.animate().z(8).withEndAction(new Runnable() {
							    @Override
							    public void run() {
								    if (Build.VERSION.SDK_INT > 20)
									    card1.animate().z(2).start();
							    }
						    }).start();
					    }
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
					    if (Build.VERSION.SDK_INT > 20) {
						    card2.animate().z(8).withEndAction(new Runnable() {
							    @Override
							    public void run() {
								    if (Build.VERSION.SDK_INT > 20)
									    card2.animate().z(2).start();
							    }
						    }).start();
					    }
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
					    if (Build.VERSION.SDK_INT > 20) {
						    card3.animate().z(8).withEndAction(new Runnable() {
							    @Override
							    public void run() {
								    // TODO: remove unnecessary version check 
								    if (Build.VERSION.SDK_INT > 20)
									    card3.animate().z(2).start();
							    }
						    }).start();
					    }
					    flip(arrow3);
				    }
			    }
		    });

		    // Random events
		    card1.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				    switch (event.getAction()) {
					    case MotionEvent.ACTION_DOWN:
						    if (Build.VERSION.SDK_INT > 20) {
							    card1.animate().z(8).start();
						    }
						    break;
					    case MotionEvent.ACTION_UP:
						    if (Build.VERSION.SDK_INT > 20) {
							    card1.animate().z(2).start();
						    }
						    break;
				    }
				    return false;
			    }
		    });

		    // Calculator
		    card2.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				    switch (event.getAction()) {
					    case MotionEvent.ACTION_DOWN:
						    if (Build.VERSION.SDK_INT > 20) {
							    card2.animate().z(8).withEndAction(new Runnable() {
								    @Override
								    public void run() {
									    Intent i = new Intent(MainActivity.this, Calculator.class);
									    startActivity(i);
								    }
							    }).start();
						    }
						    break;
					    case MotionEvent.ACTION_UP:
						    if (Build.VERSION.SDK_INT > 20) {
							    card2.animate().z(2).start();
						    }
						    break;
				    }
				    return false;
			    }
		    });

		    // Help/About/FAQ
		    card3.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				    switch (event.getAction()) {
					    case MotionEvent.ACTION_DOWN:
						    if (Build.VERSION.SDK_INT > 20) {
							    card3.animate().z(8).start();
						    }
						    break;
					    case MotionEvent.ACTION_UP:
						    if (Build.VERSION.SDK_INT > 20) {
							    card3.animate().z(2).start();
						    }
						    break;
				    }
				    return false;
			    }
		    });

	    }
    }

	public void changeState (final View toExpand, final View toCollapse) {
		toExpand.setVisibility(View.VISIBLE);
		toCollapse.setVisibility(View.GONE);
	}

	public void flip(final View arrow) {
		arrow.animate().rotationBy(180).start();
	}

}
