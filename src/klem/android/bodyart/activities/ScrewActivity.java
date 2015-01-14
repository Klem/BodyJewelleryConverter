package klem.android.bodyart.activities;

import java.util.ArrayList;

import klem.android.bodyart.assets.AJewellery;
import klem.android.bodyart.assets.AScrew;
import klem.android.bodyart.assets.Ball;
import klem.android.bodyart.assets.Barbell;
import klem.android.bodyart.assets.MetaJewellery;
import klem.android.bodyart.assets.MetaScrew;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ScrewActivity extends Activity {

	private static final int ID_ADD = 1;
	private static final int ID_ACCEPT = 2;
	private static final int ID_UPLOAD = 3;
	public static final String VIEW_CONTAINER = null;
	private ScrewActivity ctx;
	private Resources res;
	private int thickBarProgress;
	private String[] thicknessMm;

	private SeekBar sizeBar;
	private int sizeBarProgress;
	private String[] sizeInch;
	private String[] sizeMm;
	private String selectedSizeInLabel;
	private String selectedSizeMmLabel;
	private TextView textViewSizeMm;
	private TextView textViewSizeIn;
	private ImageView iconSwitch;
	private ImageView iconMore;

	private FrameLayout viewContainer;
	private AJewellery jewellery = null;
	private final int MODE_BARBELL = 0;
	private final int MODE_RING = 1;
	private final int MODE_CURVED_BARBELL = 2;
	private final int currentMode = MODE_BARBELL;
	private ArrayList<MetaScrew> metaScrews;
	private ArrayList<AScrew> screws;
	private static String CURRENT_CLASS_NAME = "CURRENT_CLASS_NAME";
	private static Class CURRENT_CLASS_VALUE;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textview = new TextView(this);
		textview.setText("Screw");
		setContentView(textview);

		setContentView(R.layout.screw);
		viewContainer = (FrameLayout) findViewById(R.id.viewsContainer);
		viewContainer.setClickable(true);
		ctx = this;
		res = getResources();

		initSizeComponents();
		initIcons();
		initJewellery();

		displaySizeFromProgress(sizeBarProgress);

	}

	public class OnSizeChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			screws = (ArrayList<AScrew>) jewellery.getScrews();
			for (AScrew screw : screws) {
				screw.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			screws = (ArrayList<AScrew>) jewellery.getScrews();
			for (AScrew screw : screws) {
				screw.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			screws = (ArrayList<AScrew>) jewellery.getScrews();
			for (AScrew screw : screws) {
				screw.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
			}
		}

	}

	public class OnSwitchClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			metaScrews.clear();
			Intent intent = new Intent(ctx, JewelleryActivity.class);
			Toast.makeText(ctx, "JEWELLERY Mode", Toast.LENGTH_SHORT).show();
			intent.putExtra(MetaJewellery.EXTRA, jewellery.getBundle());

			for (AScrew screw : jewellery.getScrews()) {
				metaScrews.add(screw.getBundle());
			}

			intent.putExtra(MetaScrew.EXTRA, metaScrews);
			intent.putExtra(CURRENT_CLASS_NAME, CURRENT_CLASS_VALUE);
			startActivity(intent);
		}
	}

	private void displaySizeFromProgress(int progress) {
		selectedSizeInLabel = res.getString(R.string.size_in, sizeInch[progress]);
		selectedSizeMmLabel = res.getString(R.string.unit_mm, sizeMm[progress]);

		textViewSizeIn.setText(selectedSizeInLabel);
		textViewSizeMm.setText(selectedSizeMmLabel);
	}

	private void initSizeComponents() {
		sizeBar = (SeekBar) findViewById(R.id.size);
		sizeBarProgress = sizeBar.getProgress();
		sizeBar.setOnSeekBarChangeListener(new OnSizeChangeListener());
		sizeInch = res.getStringArray(R.array.ball_size_in);
		sizeMm = res.getStringArray(R.array.ball_size_mm);
		textViewSizeIn = (TextView) findViewById(R.id.size_in);
		textViewSizeMm = (TextView) findViewById(R.id.size_mm);
	}

	private void initIcons() {
		iconMore = (ImageView) findViewById(R.id.more_jewellery_icon);

		iconSwitch = (ImageView) findViewById(R.id.switch_mode);
		iconSwitch.setOnClickListener(new OnSwitchClickListener());
	}

	private void initJewellery() {

		CURRENT_CLASS_VALUE = (Class) getIntent().getSerializableExtra(CURRENT_CLASS_NAME);

		if (CURRENT_CLASS_VALUE == null) {
			CURRENT_CLASS_VALUE = Barbell.class;
		}

		MetaJewellery jb = (MetaJewellery) getIntent().getSerializableExtra(MetaJewellery.EXTRA);

		if (jb == null) {
			jb = new MetaJewellery(Float.valueOf(sizeMm[sizeBarProgress]),
					Float.valueOf(thicknessMm[thickBarProgress]), 100, 100, Color.RED);
		}

		jewellery = jb.createJewellery(ctx, CURRENT_CLASS_VALUE);
		jewellery.setViewContainer(viewContainer);

		metaScrews = (ArrayList<MetaScrew>) getIntent().getSerializableExtra(MetaScrew.EXTRA);

		for (MetaScrew sb : metaScrews) {
			AScrew screw = sb.createScrew(ctx, Ball.class);
			jewellery.addScrew(screw);
		}
	}
}
