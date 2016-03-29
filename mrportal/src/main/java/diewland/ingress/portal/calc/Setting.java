package diewland.ingress.portal.calc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Setting extends Activity {

	RadioButton rad_enl;
	RadioButton rad_res;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
	
		context = getApplicationContext();
		rad_enl = (RadioButton)findViewById(R.id.rad_enl);
		rad_res = (RadioButton)findViewById(R.id.rad_res);
		
		// load pref
		String factor = Prefs.getFactor(context);
		if(factor.equals(Prefs.VAL_ENLIGHTENDED)){
			rad_enl.setChecked(true);
		}
		else {
			rad_res.setChecked(true);
		}
		
		((Button)findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(rad_enl.isChecked()){ // enlightended
					Prefs.setFactor(context, Prefs.VAL_ENLIGHTENDED);
				}
				else { // resistance
					Prefs.setFactor(context, Prefs.VAL_RESISTANCE);
				}
				finish();
			}
		});
		
	}
	
}
