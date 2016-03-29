package diewland.ingress.portal.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Context context;
	
	// data
	Integer[] resos = { 0, 0, 0, 0, 0, 0, 0, 0 };

	// visual
    TextView distance;
	TextView level;
	TextView visual;
    LinearLayout group_res;
    LinearLayout group_mod;

	// ids
    Integer[] tab_ids =  {
                            R.id.tab_reso,
                            R.id.tab_mod,
                         };
	Integer[] font_ids = {
                            R.id.distance,
							R.id.level,
							R.id.visual,
						 };
	Integer[] slot_ids = {
							R.id.slot1,
					        R.id.slot2,
					        R.id.slot3,
					        R.id.slot4,
					        R.id.slot5,
					        R.id.slot6,
					        R.id.slot7,
							R.id.slot8
						 };
	Integer[] reso_ids = {
							R.id.reso1,
					        R.id.reso2,
					        R.id.reso3,
					        R.id.reso4,
					        R.id.reso5,
					        R.id.reso6,
					        R.id.reso7,
							R.id.reso8,
							R.id.del,
						 };
    Integer[] mod_ids =  {
                            R.id.mod1,
                            R.id.mod2,
                            R.id.mod3,
                            R.id.mod4,
                         };

    TextView link_text;

    // mods
    String mod_types[] =    {
                                "None",
                                "Portal Shield [C]",
                                "Portal Shield [R]",
                                "Portal Shield [VR]",
                                "AXA Shield [VR]",
                                "Force Amplifier [R]",
                                "Link Amplifier [R]",
								"Softbank Ultra Link [R]",
                                "Link Amplifier [VR]",
                                "Multi Hack [C]",
                                "Multi Hack [R]",
                                "Multi Hack [VR]",
                                "Heat Sink [C]",
                                "Heat Sink [R]",
                                "Heat Sink [VR]",
                                "Turret [R]",
                            };

	// colors
	Integer[] reso_colors =  {
								Color.rgb(254, 206, 90), // LV 1
								Color.rgb(255, 166, 48), // LV 2
								Color.rgb(255, 115, 21), // LV 3
								Color.rgb(228, 0, 0), 	 // LV 4
								Color.rgb(253, 41, 146), // LV 5
								Color.rgb(235, 38, 205), // LV 6
								Color.rgb(193, 36, 224), // LV 7
								Color.rgb(150, 39, 244), // LV 8
								Color.rgb(0, 0, 0), 	 // DEL
							};
    static Integer[] mod_colors =  {
                                Color.rgb(51, 255, 204), // common
                                Color.rgb(150, 39, 244), // rare
                                Color.rgb(235, 38, 205), // very rare
                            };

	// style
	static int COLOR;
    static Typeface tf;
	int COLOR_ENLIGHTENDED = Color.rgb(40, 244, 40);
	int COLOR_RESISTANCE   = Color.rgb(0, 194, 255);

	// ================== MAIN =====================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initial object
		context = getApplicationContext();
        distance = (TextView)findViewById(R.id.distance);
		level = (TextView)findViewById(R.id.level);
		visual = (TextView)findViewById(R.id.visual);
        link_text = (TextView)findViewById(R.id.links);

		// initial screen
		disableAllToggle();
		updateVisual();
        initializeTab();

		// load configurations
		loadTheme();
		
		// style
		((LinearLayout)findViewById(R.id.root)).setBackgroundColor(Color.BLACK);
		tf = Typeface.createFromAsset(getAssets(),"fonts/UBUNTUMONO-R.TTF");
		for (int font_id : font_ids) {
			TextView t = (TextView)findViewById(font_id);
			t.setTypeface(tf);
			t.setTextColor(COLOR);
		}
		for(int i = 0; i< reso_ids.length; i++){
			((Button)findViewById(reso_ids[i])).setTextColor(reso_colors[i]);
		}

        // tabs
        for(int i = 0; i< tab_ids.length; i++){
            ((Button)findViewById(tab_ids[i])).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.tab_reso:
                            updateVisual();
                            ((Button)findViewById(R.id.tab_reso)).setTextColor(Color.YELLOW);
                            ((Button)findViewById(R.id.tab_mod)).setTextColor(Color.BLACK);
                            ((LinearLayout)findViewById(R.id.group_reso)).setVisibility(View.VISIBLE);
                            ((LinearLayout)findViewById(R.id.group_mod)).setVisibility(View.GONE);
                            break;
                        case R.id.tab_mod:
                            updateVisualMod();
                            ((Button)findViewById(R.id.tab_reso)).setTextColor(Color.BLACK);
                            ((Button)findViewById(R.id.tab_mod)).setTextColor(Color.YELLOW);
                            ((LinearLayout)findViewById(R.id.group_reso)).setVisibility(View.GONE);
                            ((LinearLayout)findViewById(R.id.group_mod)).setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });
        }

		// toggle buttons
		for(int i = 0; i< slot_ids.length; i++){
			((ToggleButton)findViewById(slot_ids[i])).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					switch(v.getId()){
						case R.id.slot1:
							resos[0] = 0;
							break;
						case R.id.slot2:
							resos[1] = 0;
							break;
						case R.id.slot3:
							resos[2] = 0;
							break;
						case R.id.slot4:
							resos[3] = 0;
							break;
						case R.id.slot5:
							resos[4] = 0;
							break;
						case R.id.slot6:
							resos[5] = 0;
							break;
						case R.id.slot7:
							resos[6] = 0;
							break;
						case R.id.slot8:
							resos[7] = 0;
							break;
					}
					updateVisual();
					updateToggle();
				}
			});
		}
		
		// resonator pads
		for(int i = 0; i< reso_ids.length; i++){
			((Button)findViewById(reso_ids[i])).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					switch(v.getId()){
						case R.id.reso1:
							push(resos, 1);
							break;
						case R.id.reso2:
							push(resos, 2);
							break;
						case R.id.reso3:
							push(resos, 3);
							break;
						case R.id.reso4:
							push(resos, 4);
							break;
						case R.id.reso5:
							push(resos, 5);
							break;
						case R.id.reso6:
							push(resos, 6);
							break;
						case R.id.reso7:
							push(resos, 7);
							break;
						case R.id.reso8:
							push(resos, 8);
							break;
					}
					updateVisual();
					updateToggle();
				}
			});
		}
		
		// delete button
		((Button)findViewById(R.id.del)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop(resos);
				updateVisual();
				updateToggle();
			}
		});

        // initialize mod spinners
        // ModAdapter<String> modAdapter = new ModAdapter<String>(this, mod_types);
        ArrayAdapter<String> modAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mod_types);
        modAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i = 0; i< mod_ids.length; i++){
            Spinner spn = (Spinner)findViewById(mod_ids[i]);
                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        TextView tv = (TextView) parent.getChildAt(0);
                        tv.setTextColor(getRareColor(tv.getText().toString()));
                        updateVisualMod();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // nothing
                    }
                });
                spn.setAdapter(modAdapter);
        }

        link_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateVisualMod();
                    return true;
                }
                return false;
            }
        });

	}

    // ================== CUSTOM CLASS =====================================================

    /*
    static class ModAdapter<T> extends ArrayAdapter<T>
    {
        public ModAdapter(Context ctx, T [] objects)
        {
            super(ctx, android.R.layout.simple_spinner_item, objects);
        }

        //other constructors

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);
            //we know that simple_spinner_item has android.R.id.text1 TextView:
            TextView text = (TextView)view.findViewById(android.R.id.text1);
            text.setTextColor(getRareColor(text.getText().toString()));
            text.setBackgroundColor(Color.BLACK);
            return view;
        }
    }
    */

	// ================== UTILITY =====================================================

    private static int getRareColor(String v){
        if(v.indexOf("[C]") > -1){
            return mod_colors[0];
        }
        else if(v.indexOf("[R]") > -1){
            return mod_colors[1];
        }
        else if(v.indexOf("[VR]") > -1){
            return mod_colors[2];
        }
        return Color.WHITE;
    }

    private void initializeTab(){
        ((LinearLayout)findViewById(R.id.group_mod)).setVisibility(View.GONE);
        ((Button)findViewById(R.id.tab_reso)).setTextColor(Color.YELLOW);
    }

	private void loadTheme(){
		// load configurations
		String factor = Prefs.getFactor(context);
		if(factor.equals(Prefs.VAL_ENLIGHTENDED)){
			COLOR = COLOR_ENLIGHTENDED;
		}
		else {
			COLOR = COLOR_RESISTANCE;
		}
		// reload color
		for (int font_id : font_ids) {
			TextView t = (TextView)findViewById(font_id);
			t.setTextColor(COLOR);
		}
	}
	
	private void push(Integer[] resos, int reso){
		for(int i=0; i<resos.length; i++){
			if(resos[i] == 0){
				resos[i] = reso;
				return;
			}
		}
	}
	
	private void pop(Integer[] resos){
		for(int i=resos.length-1; i>=0; i--){
			if(resos[i] != 0){
				resos[i] = 0;
				return;
			}
		}
	}
	
	private String genRawdata(){
		StringBuffer raw = new StringBuffer();
		for(int r : resos){
			if(r != 0){
				for(int i=1; i<=r; i++){
					raw.append(r);
				}
			}
		}
		return raw.toString();
	}

    private void updateDistance(){
        // get sum
        Integer sum = 0;
        for(int r : resos){ sum += r; }

        // resos
        Double avg_lv = new Double(sum) / 8;
        Double calc_dist = 160 * Math.pow(avg_lv, 4) / 1000;
        // mod
        ArrayList<Double> la = new ArrayList<Double>();
        for(int i = 0; i< mod_ids.length; i++){
            Spinner spn = (Spinner)findViewById(mod_ids[i]);
            Object vo = spn.getSelectedItem();
            if(vo != null){
                if(vo.toString().equals("Link Amplifier [R]")){ la.add(2.0); }
				if(vo.toString().equals("Softbank Ultra Link [R]")){ la.add(5.0); }
                else if(vo.toString().equals("Link Amplifier [VR]")){ la.add(7.0); }
            }
        }
	    // calc linkamp
		Collections.sort(la, new Comparator<Double>() {
            public int compare(Double a, Double b) { return b.compareTo(a); } // sort max to min
        });
        double range_rate = 1.0;
		for(int i=1; i<=la.size(); i++){
			if(i == 1){ // 1x for first
				range_rate = la.get(i-1);
			} else if(i == 2){ // 0.25x for second
				range_rate += la.get(i-1)/4;
			} else { // 0.125x for third n forth
				range_rate += la.get(i-1)/8;
			}
		}
        calc_dist *= range_rate;

        distance.setText(String.format("%.2f", calc_dist) + " km");
    }

	private void updateVisual(){
		// level
        Integer sum = 0;
        for(int r : resos){ sum += r; }
		int lv = sum / 8;
		if(lv == 0){ lv = 1; } // default level is 1
		level.setText("LV " + lv);

        // distance
        updateDistance();

        // table
		String raw = genRawdata();
		StringBuffer sb = new StringBuffer();
		sb.append("    1 2 3 4 5 6 7 8 \n");
		for(int i = 1; i<= 8; i++){
			sb.append("L" + i + " |");
			for(int j=1; j<=8; j++){
				if(raw.length() > 0){
					String x = raw.substring(0, 1);
					raw = raw.substring(1);
					sb.append(x + "|");
				}
				else {
					sb.append(" |");
				}
			}
			if(i != 8){ sb.append("\n"); }
		}
		// sb.append("\n");
        visual.setGravity(Gravity.CENTER);
		visual.setText(sb.toString());
	}

    private String to_min_unit(Double s){
        Double m = (s / 60);
        Double new_s = s % 60;
        return String.format("%d m %.2f s", m.intValue(), new_s);
    }

    private void updateVisualMod(){
        int shield = 0;
        int hack_time = 4;
        Double cool_down = 300.0;
        String dmg = "0";
        String attk_freq = "0";
        double hit_bonus = 0.0;
        ArrayList<Integer> mh = new ArrayList<Integer>();
        ArrayList<Double> hs = new ArrayList<Double>();
   		for(int i = 0; i< mod_ids.length; i++){
            Spinner spn = (Spinner)findViewById(mod_ids[i]);
            String v = spn.getSelectedItem().toString();
            if(v.equals("Portal Shield [C]")){ shield += 30; }
            else if(v.equals("Portal Shield [R]")){ shield += 40; }
            else if(v.equals("Portal Shield [VR]")){ shield += 60; }
            else if(v.equals("AXA Shield [VR]")){ shield += 70; }
            else if(v.equals("Heat Sink [C]")){ hs.add(0.2); }
            else if(v.equals("Heat Sink [R]")){ hs.add(0.5); }
            else if(v.equals("Heat Sink [VR]")){ hs.add(0.7); }
            else if(v.equals("Multi Hack [C]")){ mh.add(4); }
            else if(v.equals("Multi Hack [R]")){ mh.add(8); }
            else if(v.equals("Multi Hack [VR]")){ mh.add(12); }
            else if(v.equals("Force Amplifier [R]")){
                if(dmg.equals("0")){ dmg = "2"; }
                else if(dmg.equals("2")){ dmg = "2.5"; }
                else if(dmg.equals("2.5")){ dmg = "2.75"; }
                else if(dmg.equals("2.75")){ dmg = "3.0"; }
            }
            else if(v.equals("Turret [R]")){
                hit_bonus += 30;
                if(attk_freq.equals("0")){ attk_freq = "2"; }
                else if(attk_freq.equals("2")){ attk_freq = "2.5"; }
                else if(attk_freq.equals("2.5")){ attk_freq = "2.75"; }
                else if(attk_freq.equals("2.75")){ attk_freq = "3.0"; }
            }
        }
   	    // calc multi-hack
		Collections.sort(mh,new Comparator<Integer>() {
            public int compare(Integer a, Integer b) { return b - a; } // sort max to min
        });
		for(int i=0; i<mh.size(); i++){
			hack_time += i == 0 ? mh.get(0) : mh.get(i)/2;
		}
   		
	    // calc heatsink
		Collections.sort(hs,new Comparator<Double>() {
            public int compare(Double a, Double b) { return b.compareTo(a); } // sort max to min
        });
		for(int i=0; i<hs.size(); i++){
			cool_down *= i == 0 ? 1-hs.get(0) : 1-(hs.get(i)/2);
		}

        // calc migration from links
        int links = 0;
        try {
            links = Integer.parseInt(link_text.getText().toString());
        }
        catch(Exception e){
            link_text.setText("0");
        }
        shield += Math.round(400/9*Math.atan(links/Math.E));

        Double burn = hack_time * cool_down;
        Double hpm = 60 / cool_down;

        StringBuffer sb = new StringBuffer();
        sb.append("mitigation  : +" + shield + "\n");
        sb.append("damage      : +" + dmg + "x\n");
        sb.append("attk freq   : +" + attk_freq + "x\n");
        sb.append(String.format("hit bonus   : +%.2f%%\n", hit_bonus));
        sb.append("hack times  : " + hack_time + "\n");
        sb.append("cool down   : " + to_min_unit(cool_down) + "\n");
        sb.append("burn in     : " + to_min_unit(burn) + "\n");
        sb.append(String.format("1 minute    : %.2f hacks\n", hpm));
        visual.setGravity(Gravity.LEFT);
        visual.setText(sb);

        updateDistance();
    }

	private void updateToggle(){
		for(int i=0; i<8; i++){
			ToggleButton tg =(ToggleButton)findViewById(slot_ids[i]);
			if(resos[i] > 0){
				tg.setTextOn("L" + resos[i].toString());
				tg.setTextColor(reso_colors[resos[i]-1]);
				tg.setChecked(true);
				tg.setEnabled(true);
			}
			else {
				tg.setTextOn("");
				tg.setChecked(false);
				tg.setEnabled(false);
			}
		}
	}
	
	private void disableAllToggle(){
		for (int slot_id : slot_ids) {
			((ToggleButton)findViewById(slot_id)).setEnabled(false);
		}
	}

	// ================== MENU =====================================================
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId())
        {
        	case R.id.menu_settings:
        		Intent i = new Intent(this, Setting.class);
        		startActivity(i); 
        }
		
		return super.onOptionsItemSelected(item);
	}
	
	// ================== EVENT =====================================================
	
	@Override
	protected void onResume() {
		loadTheme();
		super.onResume();
	}
	
}
