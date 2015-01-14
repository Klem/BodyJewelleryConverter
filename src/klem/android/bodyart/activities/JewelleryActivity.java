package klem.android.bodyart.activities;

import java.util.ArrayList;

import klem.android.bodyart.assets.AJewellery;
import klem.android.bodyart.assets.AScrew;
import klem.android.bodyart.assets.Ball;
import klem.android.bodyart.assets.Barbell;
import klem.android.bodyart.assets.CurvedBarbell;
import klem.android.bodyart.assets.MetaJewellery;
import klem.android.bodyart.assets.MetaScrew;
import klem.android.bodyart.assets.Ring;
import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
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

public class JewelleryActivity extends Activity {
	private static final int SCREW_COLOR = Color.GRAY;
	private JewelleryActivity ctx;
	private Resources res;
	private SeekBar thickBar;
	private int thickBarProgress;
	private String[] thicknessGa;
	private String[] thicknessMm;
	private String selectedThicknessGaLabel;
	private String selectedThicknessMmLabel;
	private TextView textViewThicknessMm;
	private TextView textViewThicknessGa;

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
	private QuickAction mQuickAction = null;
	private AScrew topScrew = null;
	private AScrew botScrew = null;
	private AScrew centerScrew = null;
	private ArrayList<MetaScrew> metaScrews = null;
	private final ArrayList<AScrew> screws = new ArrayList<AScrew>();
	private static String CURRENT_CLASS_NAME = "CURRENT_CLASS_NAME";
	private static Class CURRENT_CLASS_VALUE;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jewellery);
		viewContainer = (FrameLayout) findViewById(R.id.viewsContainer);
		viewContainer.setClickable(true);
		ctx = this;
		res = getResources();

		initThicknessComponents();
		initSizeComponents();
		initIcons();
		initQuickMenu();
		initJewellery();

		displayThicknessFromProgress(thickBarProgress);
		displaySizeFromProgress(sizeBarProgress);

		selectedThicknessGaLabel = res.getString(R.string.unit_ga, thicknessGa[thickBarProgress]);
		selectedThicknessMmLabel = res.getString(R.string.unit_mm, thicknessMm[thickBarProgress]);

	}

	public void setBarbellMode() {
		viewContainer.removeAllViews();
		CURRENT_CLASS_VALUE = Barbell.class;

		// gather data from existing jewellery
		MetaJewellery jb = jewellery.getBundle();
		float size = jewellery.getScrews().get(0).getSize();
		jewellery.removeScrews();

		// create new one from bundle
		jewellery = jb.createJewellery(ctx, CURRENT_CLASS_VALUE);
		jewellery.setViewContainer(viewContainer);

		MetaScrew sb = new MetaScrew(size, AScrew.ALIGN_TOP, jewellery.getStartPoint().x, jewellery.getStartPoint().y,
				SCREW_COLOR);
		topScrew = new Ball(ctx, sb);

		sb = new MetaScrew(size, AScrew.ALIGN_BOTTOM, jewellery.getEndPoint().x, jewellery.getEndPoint().y, SCREW_COLOR);
		botScrew = sb.createScrew(ctx, Ball.class);

		jewellery.addScrew(topScrew);
		jewellery.addScrew(botScrew);

	}

	public void setRingMode() {
		viewContainer.removeAllViews();
		CURRENT_CLASS_VALUE = Ring.class;

		MetaJewellery jb = jewellery.getBundle();
		float size = jewellery.getScrews().get(0).getSize();

		jewellery.removeScrews();

		jewellery = jb.createJewellery(ctx, CURRENT_CLASS_VALUE);
		jewellery.setViewContainer(viewContainer);

		MetaScrew sb = new MetaScrew(size, AScrew.ALIGN_CENTER, jewellery.getStartPoint().x,
				jewellery.getStartPoint().y, SCREW_COLOR);

		centerScrew = sb.createScrew(ctx, Ball.class);
		jewellery.addScrew(centerScrew);

	}

	public void setCurvedBarbellMode() {
		viewContainer.removeAllViews();
		CURRENT_CLASS_VALUE = CurvedBarbell.class;

		MetaJewellery jb = jewellery.getBundle();
		float size = jewellery.getScrews().get(0).getSize();
		jewellery.removeScrews();

		jewellery = jb.createJewellery(ctx, CURRENT_CLASS_VALUE);
		jewellery.setViewContainer(viewContainer);

		MetaScrew sbTop = new MetaScrew(size, AScrew.ALIGN_TOP, jewellery.getStartPoint().x,
				jewellery.getStartPoint().y, SCREW_COLOR);
		topScrew = sbTop.createScrew(ctx, Ball.class);

		MetaScrew sbBottom = new MetaScrew(size, AScrew.ALIGN_BOTTOM, jewellery.getEndPoint().x,
				jewellery.getEndPoint().y, SCREW_COLOR);
		botScrew = sbBottom.createScrew(ctx, Ball.class);

		jewellery.addScrew(topScrew);
		jewellery.addScrew(botScrew);
	}

	public class OnThicknessChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			thickBarProgress = seekBar.getProgress();
			displayThicknessFromProgress(thickBarProgress);
			jewellery.setThickness(Float.valueOf(thicknessMm[thickBarProgress]));

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			thickBarProgress = seekBar.getProgress();
			displayThicknessFromProgress(thickBarProgress);
			jewellery.setThickness(Float.valueOf(thicknessMm[thickBarProgress]));

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			thickBarProgress = seekBar.getProgress();
			displayThicknessFromProgress(thickBarProgress);
			jewellery.setThickness(Float.valueOf(thicknessMm[thickBarProgress]));
		}

	}

	private void displayThicknessFromProgress(int progress) {
		selectedThicknessGaLabel = res.getString(R.string.unit_ga, thicknessGa[progress]);
		selectedThicknessMmLabel = res.getString(R.string.unit_mm, thicknessMm[progress]);

		textViewThicknessGa.setText(selectedThicknessGaLabel);
		textViewThicknessMm.setText(selectedThicknessMmLabel);

	}

	public class OnSizeChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			jewellery.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			jewellery.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			sizeBarProgress = seekBar.getProgress();
			displaySizeFromProgress(sizeBarProgress);
			jewellery.setSize(Float.valueOf(sizeMm[sizeBarProgress]));
		}

	}

	public class OnMoreClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			mQuickAction.show(v);
		}
	}

	public class OnSwitchClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			metaScrews.clear();
			Intent intent = new Intent(ctx, ScrewActivity.class);
			Toast.makeText(ctx, "SCREW Mode", Toast.LENGTH_SHORT).show();
			intent.putExtra(MetaJewellery.EXTRA, jewellery.getBundle());

			for (AScrew screw : jewellery.getScrews()) {
				metaScrews.add(screw.getBundle());
			}

			intent.putExtra(MetaScrew.EXTRA, metaScrews);
			intent.putExtra(JewelleryActivity.CURRENT_CLASS_NAME, JewelleryActivity.CURRENT_CLASS_VALUE);

			startActivity(intent);
		}
	}

	private void displaySizeFromProgress(int progress) {
		selectedSizeInLabel = res.getString(R.string.size_in, sizeInch[progress]);
		selectedSizeMmLabel = res.getString(R.string.unit_mm, sizeMm[progress]);
		textViewSizeIn.setText(selectedSizeInLabel);
		textViewSizeMm.setText(selectedSizeMmLabel);
	}

	private void initThicknessComponents() {
		thickBar = (SeekBar) findViewById(R.id.thickness);
		thickBarProgress = thickBar.getProgress();
		thickBar.setOnSeekBarChangeListener(new OnThicknessChangeListener());
		thicknessGa = res.getStringArray(R.array.thickness_ga);
		thicknessMm = res.getStringArray(R.array.thickness_mm);
		textViewThicknessGa = (TextView) findViewById(R.id.unit_ga);
		textViewThicknessMm = (TextView) findViewById(R.id.unit_mm);
	}

	private void initSizeComponents() {
		sizeBar = (SeekBar) findViewById(R.id.size);
		sizeBarProgress = sizeBar.getProgress();
		sizeBar.setOnSeekBarChangeListener(new OnSizeChangeListener());
		sizeInch = res.getStringArray(R.array.size_in);
		sizeMm = res.getStringArray(R.array.size_mm);
		textViewSizeIn = (TextView) findViewById(R.id.size_in);
		textViewSizeMm = (TextView) findViewById(R.id.size_mm);
	}

	private void initIcons() {
		iconMore = (ImageView) findViewById(R.id.more_jewellery_icon);
		iconMore.setOnClickListener(new OnMoreClickListener());

		iconSwitch = (ImageView) findViewById(R.id.switch_mode);
		iconSwitch.setOnClickListener(new OnSwitchClickListener());
	}

	private void initQuickMenu() {

		ActionItem barbellItem = new ActionItem(MODE_BARBELL, "", res.getDrawable(R.drawable.ic_menu_barbell_full));
		ActionItem ringItem = new ActionItem(MODE_RING, "", res.getDrawable(R.drawable.ic_menu_ring_full));
		ActionItem curvedBarbellItem = new ActionItem(MODE_CURVED_BARBELL, "",
				res.getDrawable(R.drawable.ic_menu_curved_barbell_full));

		mQuickAction = new QuickAction(this);
		mQuickAction.addActionItem(barbellItem);
		mQuickAction.addActionItem(ringItem);
		mQuickAction.addActionItem(curvedBarbellItem);
		mQuickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);

		// setup the action item click listener
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {

				if (actionId == MODE_BARBELL) {
					setBarbellMode();
					Toast.makeText(ctx, "Barbell", Toast.LENGTH_SHORT).show();
				}
				if (actionId == MODE_RING) {
					setRingMode();
					Toast.makeText(ctx, "Ring", Toast.LENGTH_SHORT).show();
				}
				if (actionId == MODE_CURVED_BARBELL) {
					setCurvedBarbellMode();
					Toast.makeText(ctx, "Curved barbell", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private void initJewellery() {

		CURRENT_CLASS_VALUE = (Class) getIntent().getSerializableExtra(JewelleryActivity.CURRENT_CLASS_NAME);

		if (CURRENT_CLASS_VALUE == null) {
			CURRENT_CLASS_VALUE = Barbell.class;
		}

		MetaJewellery jb = (MetaJewellery) getIntent().getSerializableExtra(MetaJewellery.EXTRA);
		metaScrews = (ArrayList<MetaScrew>) getIntent().getSerializableExtra(MetaScrew.EXTRA);

		if (jb == null) {
			jb = new MetaJewellery(Float.valueOf(sizeMm[sizeBarProgress]),
					Float.valueOf(thicknessMm[thickBarProgress]), 100, 100, Color.RED);
		}

		jewellery = jb.createJewellery(ctx, JewelleryActivity.CURRENT_CLASS_VALUE);
		jewellery.setViewContainer(viewContainer);

		if (metaScrews == null) {
			metaScrews = new ArrayList<MetaScrew>();
			metaScrews.add(new MetaScrew(3, AScrew.ALIGN_TOP, jewellery.getStartPoint().x, jewellery.getStartPoint().y,
					SCREW_COLOR));
			metaScrews.add(new MetaScrew(3, AScrew.ALIGN_BOTTOM, jewellery.getEndPoint().x, jewellery.getEndPoint().y,
					SCREW_COLOR));
		}

		for (MetaScrew sb : metaScrews) {
			AScrew screw = sb.createScrew(ctx, Ball.class);
			jewellery.addScrew(screw);
		}
	}
}
