package com.developer.zyumbik.goclayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

		    //Default view
		    full1.setVisibility(View.VISIBLE);
		    full2.setVisibility(View.GONE);
		    full3.setVisibility(View.GONE);
		    brief1.setVisibility(View.GONE);
		    brief2.setVisibility(View.VISIBLE);
		    brief3.setVisibility(View.VISIBLE);
		    //changeState(full1, brief1);
		    //changeState(brief2, full2);
		    //changeState(brief3, full3);

		    //Expand first
		    arrow1.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (expanded != 1) {
					    //Collapse currently expanded card
					    if (expanded == 2) {
						    //changeState(brief2, full2);
						    collapse(full2);
						    expand(brief2);
					    } else {
						    //changeState(brief3, full3);
						    collapse(full3);
						    expand(brief3);
					    }
					    expanded = 1;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    //changeState(full1, brief1);
					    collapse(brief1);
					    expand(full1);
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
						    //changeState(brief1, full1);
						    collapse(full1);
						    expand(brief1);
					    } else {
						    //changeState(brief3, full3);
						    collapse(full3);
						    expand(brief3);
					    }
					    expanded = 2;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    //changeState(full2, brief2);
					    collapse(brief2);
					    expand(full2);
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
						    //changeState(brief1, full1);
						    collapse(full1);
						    expand(brief1);
					    } else {
						    //changeState(brief2, full2);
						    collapse(full2);
						    expand(brief2);
					    }
					    expanded = 3;
					    arrow1.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow2.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24dp);
					    arrow3.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24dp);
					    //changeState(full2, brief2);
					    collapse(brief3);
					    expand(full3);
				    }
			    }
		    });

	    }

    }

	public void changeState (final View expand, final View collapse) {
		expand.measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

		final int targetHeight = expand.getMeasuredHeight();
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
				}
				expand.requestLayout();
			}
		};

		exp.setInterpolator(new AccelerateDecelerateInterpolator());
		exp.setDuration(500);

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

		col.setDuration(500);
		col.setInterpolator(new AccelerateDecelerateInterpolator());

		collapse.startAnimation(col);
		expand.startAnimation(exp);
	}

	public static void expand(final View v) {
		v.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();

		// Older versions of android (pre API 21) cancel animations for views with a height of 0.
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? FrameLayout.LayoutParams.WRAP_CONTENT
						: (int)(targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 20);
		v.startAnimation(a);
	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if(interpolatedTime == 1){
					v.setVisibility(View.GONE);
				}else{
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)*4);
		v.startAnimation(a);
	}
	
}
