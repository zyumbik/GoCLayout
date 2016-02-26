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
	CardView card1, card2, card3;
	int expanded = 1;
	int[][] cardHeight = new int[3][2];

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
		    card1 = (CardView) findViewById(R.id.card_view1);
		    card2 = (CardView) findViewById(R.id.card_view2);
		    card3 = (CardView) findViewById(R.id.card_view3);

		    //Full (0 index), Brief (1 index) heights
		    final int[][] textHeight = {
				    {full1.getLayout().getHeight(), full2.getLayout().getHeight(), full3.getLayout().getHeight()},
				    {brief1.getLayout().getHeight(), brief2.getLayout().getHeight(), brief3.getLayout().getHeight()}
		    };

		    for (int i = 0; i < 2; i++) {
			    for (int j = 0; j < 3; j++) {
				    Log.d(UI_MODE_SERVICE, "Height " + i + j + ": " + textHeight[i][j]);
			    }
		    }

//		    //Put cards' heights to array
//		    //Only brief descriptions visible
//		    full1.setVisibility(View.GONE);
//		    full2.setVisibility(View.GONE);
//		    full3.setVisibility(View.GONE);
//
//		    cardHeight[0][0] = card1.getLayoutParams().height;
//		    cardHeight[0][1] = card2.getLayoutParams().height;
//		    cardHeight[0][2] = card3.getLayoutParams().height;
//
//		    //Get cards' heights when only full description is visible
//		    full1.setVisibility(View.VISIBLE);
//		    full2.setVisibility(View.VISIBLE);
//		    full3.setVisibility(View.VISIBLE);
//		    brief1.setVisibility(View.GONE);
//		    brief2.setVisibility(View.GONE);
//		    brief3.setVisibility(View.GONE);
//
//		    cardHeight[1][0] = card1.getLayoutParams().height;
//		    cardHeight[1][1] = card2.getLayoutParams().height;
//		    cardHeight[1][2] = card3.getLayoutParams().height;

		    changeState(full1, textHeight[0][0], brief1);
		    changeState(brief2, textHeight[1][1], full2);
		    changeState(brief3, textHeight[1][2], full3);

		    //Expand first
		    arrow1.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 1) {
					    //Collapse currently expanded card
					    if (expanded == 2) {
						    changeState(brief2, textHeight[1][1], full2);
					    } else {
						    changeState(brief3, textHeight[1][2], full3);
					    }
					    expanded = 1;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    changeState(full1, textHeight[0][0], brief1);
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
						    changeState(brief1, textHeight[1][0], full1);
					    } else {
						    changeState(brief3, textHeight[1][2], full3);
					    }
					    expanded = 2;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    changeState(full2, textHeight[0][1], brief2);
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
						    changeState(brief1, textHeight[1][0], full1);
					    } else {
						    changeState(brief2, textHeight[1][1], full2);
					    }
					    expanded = 3;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    changeState(full3, textHeight[0][2], brief3);
				    }
			    }
		    });

	    }
    }

	public void changeState (final View expand, final int targetHeight ,final View collapse) {
		
		final int currentHeight = collapse.getMeasuredHeight();

		expand.getLayoutParams().height = 1;
		expand.setVisibility(View.VISIBLE);

		Animation exp = new Animation() {
			@Override
			public boolean willChangeBounds() {
				return true;
			}

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					expand.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
				}
				else {
					expand.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
					expand.requestLayout();
				}
			}
		};

		Animation col = new Animation() {
			@Override
			public boolean willChangeBounds() {
				return true;
			}

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					collapse.setVisibility(View.GONE);
				} else {
					collapse.getLayoutParams().height = (int) (currentHeight * (1 - interpolatedTime));
					collapse.requestLayout();
				}
			}
		};

		exp.setInterpolator(new AccelerateDecelerateInterpolator());
		col.setInterpolator(new AccelerateDecelerateInterpolator());
		exp.setDuration(targetHeight * 10);
		col.setDuration(currentHeight * 10);

		collapse.startAnimation(col);
		expand.startAnimation(exp);
	}

}
