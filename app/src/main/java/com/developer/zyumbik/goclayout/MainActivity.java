package com.developer.zyumbik.goclayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.auth.FragmentAuthentication;
import com.developer.zyumbik.goclayout.system.AppClass;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private ImageButton arrow1, arrow2, arrow3;
	private TextView full1, full2, full3;
	private TextView brief1, brief2, brief3;
	private CardView card1, card2, card3;
	private int expanded = 1;

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

		    // Default state
		    changeState(full1, brief1);
		    changeState(brief2, full2);
		    flip(arrow2);
		    changeState(brief3, full3);
		    flip(arrow3);

		    arrow1.setOnClickListener(this);
		    arrow2.setOnClickListener(this);
		    arrow3.setOnClickListener(this);
		    card1.setOnClickListener(this);
		    card2.setOnClickListener(this);
		    card3.setOnClickListener(this);
	    }
    }

	private void changeState (final View toExpand, final View toCollapse) {
		toExpand.setVisibility(View.VISIBLE);
		toCollapse.setVisibility(View.GONE);
	}

	private void flip(final View arrow) {
		arrow.animate().rotationBy(180).start();
	}

	private void animateCard(final View v) {
		if (Build.VERSION.SDK_INT > 20) {
			v.animate().z(8).withEndAction(new Runnable() {
				@Override
				public void run() {
					// TODO: remove unnecessary version check
					if (Build.VERSION.SDK_INT > 20)
						v.animate().z(2).start();
				}
			}).start();
		}
	}

	private void startCalculator() {
		Intent i = new Intent(MainActivity.this, Calculator.class);
		startActivity(i);
	}

	private void startUserRandomEvents() {
		Intent i = new Intent(MainActivity.this, URandomEvents.class);
		startActivity(i);
	}

	private void startHelp() {
		Intent i = new Intent(MainActivity.this, Help.class);
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		// Random events
		if (v.equals(card1)) {
			if (Build.VERSION.SDK_INT > 20) {
				card1.animate().z(8).withEndAction(new Runnable() {
					@Override
					public void run() {
						startUserRandomEvents();
					}
				}).start();
			} else {
				startUserRandomEvents();
			}
		}

		// Calculator
		if(v.equals(card2)) {
			if (Build.VERSION.SDK_INT > 20) {
				card2.animate().z(8).withEndAction(new Runnable() {
					@Override
					public void run() {
						startCalculator();
					}
				}).start();
			} else {
				startCalculator();
			}
			return;
		}

		// Help/FAQ/About/Learn
		if (v.equals(card3)) {
			if (Build.VERSION.SDK_INT > 20) {
				card3.animate().z(8).withEndAction(new Runnable() {
					@Override
					public void run() {
						startHelp();
					}
				}).start();
			 } else {
				startHelp();
			}
		}

		if (v.equals(arrow1)) {
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
				animateCard(card1);
				flip(arrow1);
			}
		}

		if (v.equals(arrow2)) {
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
				animateCard(card2);
				flip(arrow2);
			}
		}

		if (v.equals(arrow3)) {
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
				animateCard(card3);
				flip(arrow3);
			}
		}

	}
}
