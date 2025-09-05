package ramith.basecal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Locale;

/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity {
    // Buttons (kept as individual fields because layout uses onClick names)
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnA, btnB, btnC, btnD, btnE, btnF;
    private Button btnAdd, btnSub, btnMul, btnDev, btnEqual, btnClear, btnConv;

    // Layout / UI
    private ConstraintLayout btnNumberLayout;
    private MaterialButtonToggleGroup btnToggleGroup;
    private MaterialCardView cardDestination, cardDisplay, cardSource;
    private RelativeLayout layoutCardDestination, layoutCardSource;
    private ConstraintLayout parent;
    private EditText txtDestination, txtDisplay, txtSource;
    private Spinner spinnerSource, spinnerDestination, spinnerDisplay;

    // Constants
    private static final int BASE_POS_BINARY = 0;
    private static final int BASE_POS_OCTAL = 1;
    private static final int BASE_POS_DECIMAL = 2;
    private static final int BASE_POS_HEX = 3;

    private static final int MAX_LEN_BINARY = 29;
    private static final int MAX_LEN_OCTAL = 9;
    private static final int MAX_LEN_DECIMAL = 9;
    private static final int MAX_LEN_HEX = 7;

    private static final String MAX_VAL_BINARY = "11111111111111111111111111111";
    private static final String MAX_VAL_OCTAL = "777777777";
    private static final String MAX_VAL_DECIMAL = "134217727";
    private static final String MAX_VAL_HEX = "7FFFFFF";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);
    // initialize fields and listeners
    init();
    cardSource.setChecked(true);

    // spinners were initialized in init(); set default and listeners
    spinnerSource.setSelection(BASE_POS_DECIMAL);
    setupSpinnerListeners();
    }

    private void animationVisible() {
        if (this.cardDestination.getVisibility() == View.GONE) {
            Slide slide = new Slide(Gravity.TOP);
            slide.setDuration(500L);
            slide.addTarget(R.id.cardDestination);
            TransitionManager.beginDelayedTransition(this.cardDestination);
            TransitionManager.beginDelayedTransition(this.cardSource);
            TransitionManager.beginDelayedTransition(this.btnToggleGroup);
            TransitionManager.beginDelayedTransition(this.cardDisplay);
            TransitionManager.beginDelayedTransition(this.btnNumberLayout);
            this.cardDestination.setVisibility(View.VISIBLE);
            return;
        }
        this.cardDestination.setVisibility(View.VISIBLE);
    }

    private void animationGone() {
        if (this.cardDestination.getVisibility() == View.VISIBLE) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setDuration(500L);
            slide.addTarget(R.id.cardDestination);
            TransitionManager.beginDelayedTransition(this.cardDestination);
            TransitionManager.beginDelayedTransition(this.cardSource);
            TransitionManager.beginDelayedTransition(this.btnToggleGroup);
            TransitionManager.beginDelayedTransition(this.cardDisplay);
            TransitionManager.beginDelayedTransition(this.btnNumberLayout);
            this.cardDestination.setVisibility(View.GONE);
            return;
        }
        this.cardDestination.setVisibility(View.GONE);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.about_menu) {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this, 2131820821);
            materialAlertDialogBuilder.setTitle((CharSequence) "About");
            materialAlertDialogBuilder.setMessage((CharSequence) "Designed and Developed by ramith_00\nSpecial Thanks to t_yehan, mr.kesh__\n\nMade with ❤️ in Sri Lanka 🇱🇰\n\nVersion: 2.0");
            materialAlertDialogBuilder.setPositiveButton((CharSequence) "Ok", new DialogInterface.OnClickListener() { // from class: ramith.basecal.MainActivity.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "   ^_^   ", Toast.LENGTH_SHORT).show();
                }
            });
            materialAlertDialogBuilder.setCancelable(false);
            materialAlertDialogBuilder.create().show();
        } else if (itemId == R.id.theme_menu) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, 2131820821);
            builder.setTitle((CharSequence) "Change Theme");
            String[] options = {"Light", "Dark", "System Default"};
            int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
            int checkedItem = (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO) ? 0 : (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES ? 1 : 2);
            final int[] selected = new int[]{checkedItem};
            builder.setSingleChoiceItems((CharSequence[]) options, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    selected[0] = which; // just record selection, don't apply yet
                }
            });
            builder.setPositiveButton((CharSequence) "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int whichButton) {
                    int sel = selected[0];
                    if (sel == 0) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else if (sel == 1) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    }
                }
            });
            builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
            builder.setCancelable(true);
            builder.create().show();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    public void init() {
        this.parent = (ConstraintLayout) findViewById(R.id.parent);
        this.btn0 = (Button) findViewById(R.id.btn0);
        this.btn1 = (Button) findViewById(R.id.btn1);
        this.btn2 = (Button) findViewById(R.id.btn2);
        this.btn3 = (Button) findViewById(R.id.btn3);
        this.btn4 = (Button) findViewById(R.id.btn4);
        this.btn5 = (Button) findViewById(R.id.btn5);
        this.btn6 = (Button) findViewById(R.id.btn6);
        this.btn7 = (Button) findViewById(R.id.btn7);
        this.btn8 = (Button) findViewById(R.id.btn8);
        this.btn9 = (Button) findViewById(R.id.btn9);
        this.btnA = (Button) findViewById(R.id.btnA);
        this.btnB = (Button) findViewById(R.id.btnB);
        this.btnC = (Button) findViewById(R.id.btnC);
        this.btnD = (Button) findViewById(R.id.btnD);
        this.btnE = (Button) findViewById(R.id.btnE);
        this.btnF = (Button) findViewById(R.id.btnF);
        this.btnEqual = (Button) findViewById(R.id.btnEqual);
        this.btnClear = (Button) findViewById(R.id.btnClear);
        this.btnConv = (Button) findViewById(R.id.btnConv);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnSub = (Button) findViewById(R.id.btnSub);
        this.btnMul = (Button) findViewById(R.id.btnMul);
        this.btnDev = (Button) findViewById(R.id.btnDev);
        this.cardSource = (MaterialCardView) findViewById(R.id.cardSource);
        this.cardDestination = (MaterialCardView) findViewById(R.id.cardDestination);
        this.cardDisplay = (MaterialCardView) findViewById(R.id.cardDisplay);
        this.btnToggleGroup = (MaterialButtonToggleGroup) findViewById(R.id.btnToggleGroup);
        this.layoutCardSource = (RelativeLayout) findViewById(R.id.layoutCardSource);
        this.layoutCardDestination = (RelativeLayout) findViewById(R.id.layoutCardDestination);
        this.txtDisplay = (EditText) findViewById(R.id.txtDisplay);
        this.btnNumberLayout = (ConstraintLayout) findViewById(R.id.btnInputLayout);
        this.txtSource = (EditText) findViewById(R.id.txtSource);
        this.txtDestination = (EditText) findViewById(R.id.txtDestination);
        this.spinnerSource = (Spinner) findViewById(R.id.spinnerSource);
        this.spinnerDestination = (Spinner) findViewById(R.id.spinnerDestination);
        this.spinnerDisplay = (Spinner) findViewById(R.id.spinnerDisplay);
    }

    private void setupSpinnerListeners() {
        // Focus change listeners
        spinnerSource.setOnFocusChangeListener((v, hasFocus) -> {
            if (!cardSource.isChecked()) {
                disableAllNumberButtons();
                btnEqual.setEnabled(true);
                return;
            }
            txtSource.getText().clear();
            updateButtonsForPosition(spinnerSource.getSelectedItemPosition(), txtSource);
        });

        spinnerDestination.setOnFocusChangeListener((v, hasFocus) -> {
            if (!cardDestination.isChecked()) {
                disableAllNumberButtons();
                btnEqual.setEnabled(true);
                return;
            }
            txtDestination.getText().clear();
            updateButtonsForPosition(spinnerDestination.getSelectedItemPosition(), txtDestination);
        });

        spinnerDisplay.setOnFocusChangeListener((v, hasFocus) -> txtDisplay.getText().clear());

        // Item selected listeners
        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!cardSource.isChecked()) {
                    disableAllNumberButtons();
                    btnEqual.setEnabled(true);
                    return;
                }
                txtSource.getText().clear();
                updateButtonsForPosition(position, txtSource);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!cardDestination.isChecked()) {
                    disableAllNumberButtons();
                    btnEqual.setEnabled(true);
                    return;
                }
                txtDestination.getText().clear();
                updateButtonsForPosition(position, txtDestination);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtDisplay.getText().clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateButtonsForPosition(int position, EditText target) {
        switch (position) {
            case BASE_POS_BINARY:
                target.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LEN_BINARY)});
                enableForBase(2);
                break;
            case BASE_POS_OCTAL:
                target.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LEN_OCTAL)});
                enableForBase(8);
                break;
            case BASE_POS_DECIMAL:
                target.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LEN_DECIMAL)});
                enableForBase(10);
                break;
            case BASE_POS_HEX:
                target.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LEN_HEX)});
                enableForBase(16);
                break;
            default:
                clear(null);
                btnEqual.setEnabled(false);
                break;
        }
    }

    private void enableForBase(int base) {
        // 0-1 always
        btn0.setEnabled(true);
        btn1.setEnabled(true);

        boolean octal = base >= 8;
        boolean decimal = base >= 10;
        boolean hex = base == 16;

        btn2.setEnabled(octal);
        btn3.setEnabled(octal);
        btn4.setEnabled(octal);
        btn5.setEnabled(octal);
        btn6.setEnabled(octal);
        btn7.setEnabled(octal);

        btn8.setEnabled(decimal);
        btn9.setEnabled(decimal);

        btnA.setEnabled(hex);
        btnB.setEnabled(hex);
        btnC.setEnabled(hex);
        btnD.setEnabled(hex);
        btnE.setEnabled(hex);
        btnF.setEnabled(hex);

        btnEqual.setEnabled(true);
    }

    private void disableAllNumberButtons() {
        btn0.setEnabled(false);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnE.setEnabled(false);
        btnF.setEnabled(false);
    }

    // Append a character to the source field and validate; roll back if it becomes invalid.
    private void updateSource(String ch) {
        if (txtSource == null) return;
        int start = txtSource.getText().length();
        txtSource.append(ch);
        try {
            if (isNotValidInput_Source()) {
                // remove appended chars
                int end = txtSource.getText().length();
                if (end > start) txtSource.getText().delete(start, end);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // defensive: remove appended chars on any failure
            int end2 = txtSource.getText().length();
            if (end2 > start) txtSource.getText().delete(start, end2);
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    // Append a character to the destination field and validate; roll back if it becomes invalid.
    private void updateDestination(String ch) {
        if (txtDestination == null) return;
        int start = txtDestination.getText().length();
        txtDestination.append(ch);
        try {
            if (isNotValidInput_Destination()) {
                int end = txtDestination.getText().length();
                if (end > start) txtDestination.getText().delete(start, end);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            int end2 = txtDestination.getText().length();
            if (end2 > start) txtDestination.getText().delete(start, end2);
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    public static String convert(String str, int i, int i2) {
        return Integer.toString(Integer.parseInt(str, i), i2);
    }

    public void cardSource(View view) {
        this.cardSource.setChecked(true);
        this.cardDestination.setChecked(false);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSource);
        int selectedItemPosition = spinner.getSelectedItemPosition();
        if (selectedItemPosition == 0) {
            this.txtSource.setFilters(new InputFilter[]{new InputFilter.LengthFilter(29)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(false);
            this.btn3.setEnabled(false);
            this.btn4.setEnabled(false);
            this.btn5.setEnabled(false);
            this.btn6.setEnabled(false);
            this.btn7.setEnabled(false);
            this.btn8.setEnabled(false);
            this.btn9.setEnabled(false);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
            return;
        }
        if (selectedItemPosition == 1) {
            this.txtSource.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(false);
            this.btn9.setEnabled(false);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
            return;
        }
        if (selectedItemPosition == 2) {
            this.txtSource.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(true);
            this.btn9.setEnabled(true);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
            return;
        }
        if (selectedItemPosition == 3) {
            this.txtSource.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(true);
            this.btn9.setEnabled(true);
            this.btnA.setEnabled(true);
            this.btnB.setEnabled(true);
            this.btnC.setEnabled(true);
            this.btnD.setEnabled(true);
            this.btnE.setEnabled(true);
            this.btnF.setEnabled(true);
            this.btnEqual.setEnabled(true);
            return;
        }
        this.btnEqual.setEnabled(false);
        spinner.setEnabled(false);
    }

    public void cardDestination(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerDestination);
        int selectedItemPosition = spinner.getSelectedItemPosition();
        if (selectedItemPosition == 0) {
            this.txtDestination.setFilters(new InputFilter[]{new InputFilter.LengthFilter(29)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(false);
            this.btn3.setEnabled(false);
            this.btn4.setEnabled(false);
            this.btn5.setEnabled(false);
            this.btn6.setEnabled(false);
            this.btn7.setEnabled(false);
            this.btn8.setEnabled(false);
            this.btn9.setEnabled(false);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
        } else if (selectedItemPosition == 1) {
            this.txtDestination.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(false);
            this.btn9.setEnabled(false);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
        } else if (selectedItemPosition == 2) {
            this.txtDestination.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(true);
            this.btn9.setEnabled(true);
            this.btnA.setEnabled(false);
            this.btnB.setEnabled(false);
            this.btnC.setEnabled(false);
            this.btnD.setEnabled(false);
            this.btnE.setEnabled(false);
            this.btnF.setEnabled(false);
            this.btnEqual.setEnabled(true);
        } else if (selectedItemPosition == 3) {
            this.txtDestination.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
            this.btn0.setEnabled(true);
            this.btn1.setEnabled(true);
            this.btn2.setEnabled(true);
            this.btn3.setEnabled(true);
            this.btn4.setEnabled(true);
            this.btn5.setEnabled(true);
            this.btn6.setEnabled(true);
            this.btn7.setEnabled(true);
            this.btn8.setEnabled(true);
            this.btn9.setEnabled(true);
            this.btnA.setEnabled(true);
            this.btnB.setEnabled(true);
            this.btnC.setEnabled(true);
            this.btnD.setEnabled(true);
            this.btnE.setEnabled(true);
            this.btnF.setEnabled(true);
            this.btnEqual.setEnabled(true);
        } else {
            this.btnEqual.setEnabled(false);
            spinner.setEnabled(false);
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnConv) {
            this.cardDestination.setChecked(false);
            this.cardDestination.setClickable(false);
        } else {
            this.cardDestination.setChecked(true);
            this.cardSource.setChecked(false);
        }
    }

    public void btnConv(View view) {
        animationGone();
        ((Spinner) findViewById(R.id.spinnerSource)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDestination)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDisplay)).setSelection(0);
        this.cardSource.setChecked(true);
        cardSource(view);
        clear(view);
    }

    public void btnAdd(View view) {
        animationVisible();
        ((Spinner) findViewById(R.id.spinnerSource)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDestination)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDisplay)).setSelection(2);
        this.cardSource.setChecked(true);
        cardSource(view);
        clear(view);
        this.cardDestination.setClickable(true);
        if (this.cardDestination.isChecked()) {
            this.cardDestination.setChecked(false);
        }
    }

    public void btnSub(View view) {
        ((Spinner) findViewById(R.id.spinnerSource)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDestination)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDisplay)).setSelection(2);
        animationVisible();
        clear(view);
        this.cardSource.setChecked(true);
        cardSource(view);
        this.cardDestination.setClickable(true);
        if (this.cardDestination.isChecked()) {
            this.cardDestination.setChecked(false);
        }
    }

    public void btnMul(View view) {
        ((Spinner) findViewById(R.id.spinnerSource)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDestination)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDisplay)).setSelection(2);
        animationVisible();
        clear(view);
        this.cardSource.setChecked(true);
        cardSource(view);
        this.cardDestination.setClickable(true);
        if (this.cardDestination.isChecked()) {
            this.cardDestination.setChecked(false);
        }
    }

    public void btnDev(View view) {
        ((Spinner) findViewById(R.id.spinnerSource)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDestination)).setSelection(2);
        ((Spinner) findViewById(R.id.spinnerDisplay)).setSelection(2);
        animationVisible();
        clear(view);
        this.cardSource.setChecked(true);
        cardSource(view);
        this.cardDestination.setClickable(true);
        if (this.cardDestination.isChecked()) {
            this.cardDestination.setChecked(false);
        }
    }

    public void btn0(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("0");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("0");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn1(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("1");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("1");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn2(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("2");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("2");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn3(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("3");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("3");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn4(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("4");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("4");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn5(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("5");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("5");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn6(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("6");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("6");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn7(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("7");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("7");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn8(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("8");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("8");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn9(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("9");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("9");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnA(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("A");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("A");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnB(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("B");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("B");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnC(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("C");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("C");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnD(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("D");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("D");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnE(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("E");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("E");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnF(View view) {
        if (this.cardSource.isChecked()) {
            updateSource("F");
        } else if (this.cardDestination.isChecked()) {
            updateDestination("F");
        } else {
            Toast.makeText(this, "Select Input Area First", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnClear(View view) {
        this.txtSource.getText().clear();
        this.txtDestination.getText().clear();
        this.txtDisplay.getText().clear();
    }

    public void clear(View view) {
        this.txtSource.getText().clear();
        this.txtDestination.getText().clear();
        this.txtDisplay.getText().clear();
    }

    public void btnEqual(View view) throws NumberFormatException {
        int i;
        int i2;
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSource);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerDestination);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinnerDisplay);
        int i3 = 16;
        if (spinner.getSelectedItemPosition() == 0) {
            i = 2;
        } else if (spinner.getSelectedItemPosition() == 1) {
            i = 8;
        } else {
            i = (spinner.getSelectedItemPosition() != 2 && spinner.getSelectedItemPosition() == 3) ? 16 : 10;
        }
        if (spinner2.getSelectedItemPosition() == 0) {
            i2 = 2;
        } else if (spinner2.getSelectedItemPosition() == 1) {
            i2 = 8;
        } else {
            i2 = (spinner2.getSelectedItemPosition() != 2 && spinner2.getSelectedItemPosition() == 3) ? 16 : 10;
        }
        if (spinner3.getSelectedItemPosition() == 0) {
            i3 = 2;
        } else if (spinner3.getSelectedItemPosition() == 1) {
            i3 = 8;
        } else if (spinner3.getSelectedItemPosition() == 2 || spinner3.getSelectedItemPosition() != 3) {
            i3 = 10;
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnConv) {
            if (this.txtSource.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter Some Values", Toast.LENGTH_SHORT).show();
                return;
            } else if (isNotValidInput_Source()) {
                clear(view);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
                return;
            } else {
                this.txtDisplay.setText(convert(this.txtSource.getText().toString(), i, i3).toUpperCase(Locale.ROOT));
                return;
            }
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnAdd) {
            if (this.txtSource.getText().toString().equals("") || this.txtDestination.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter Some Values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isNotValidInput_Source() || isNotValidInput_Destination()) {
                clear(view);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int i4 = Integer.parseInt(this.txtSource.getText().toString(), i) + Integer.parseInt(this.txtDestination.getText().toString(), i2);
            if (i4 > 134217727) {
                Toast.makeText(this, "Too Large Output", Toast.LENGTH_SHORT).show();
                return;
            } else {
                this.txtDisplay.setText(convert(String.valueOf(i4), 10, i3).toUpperCase(Locale.ROOT));
                return;
            }
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnSub) {
            if (this.txtSource.getText().toString().equals("") || this.txtDestination.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter Some Values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isNotValidInput_Source() || isNotValidInput_Destination()) {
                clear(view);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int i5 = Integer.parseInt(this.txtSource.getText().toString(), i) - Integer.parseInt(this.txtDestination.getText().toString(), i2);
            if (i5 < -134217727) {
                Toast.makeText(this, "Too Large Output", Toast.LENGTH_SHORT).show();
                return;
            } else {
                this.txtDisplay.setText(convert(String.valueOf(i5), 10, i3).toUpperCase(Locale.ROOT));
                return;
            }
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnMul) {
            if (this.txtSource.getText().toString().equals("") || this.txtDestination.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter Some Values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isNotValidInput_Source() || isNotValidInput_Destination()) {
                clear(view);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int i6 = Integer.parseInt(this.txtSource.getText().toString(), i) * Integer.parseInt(this.txtDestination.getText().toString(), i2);
            if (i6 > 134217727) {
                Toast.makeText(this, "Too Large Output", Toast.LENGTH_SHORT).show();
                return;
            } else {
                this.txtDisplay.setText(convert(String.valueOf(i6), 10, i3).toUpperCase(Locale.ROOT));
                return;
            }
        }
        if (this.btnToggleGroup.getCheckedButtonId() == R.id.btnDev) {
            if (this.txtSource.getText().toString().equals("") || this.txtDestination.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter Some Values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isNotValidInput_Source() || isNotValidInput_Destination()) {
                clear(view);
                Toast.makeText(this, "Too Large Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int i7 = Integer.parseInt(this.txtSource.getText().toString(), i);
            int i8 = Integer.parseInt(this.txtDestination.getText().toString(), i2);
            if (i8 == 0) {
                this.txtDisplay.setText("Can't divide by 0");
                return;
            } else if (i3 == 10) {
                this.txtDisplay.setText(String.valueOf(i7 / i8));
                return;
            } else {
                this.txtDisplay.setText(convert(String.valueOf(i7 / i8), 10, i3).toUpperCase(Locale.ROOT));
                return;
            }
        }
        Toast.makeText(this, "Please Select an Option", Toast.LENGTH_SHORT).show();
    }

    private boolean isNotValidInput_Source() {
        try {
            int base = getBaseFromSpinner(spinnerSource);
            int value = Integer.parseInt(txtSource.getText().toString(), base);
            switch (base) {
                case 2:
                    return value > Integer.parseInt(MAX_VAL_BINARY, 2);
                case 8:
                    return value > Integer.parseInt(MAX_VAL_OCTAL, 8);
                case 16:
                    return value > Integer.parseInt(MAX_VAL_HEX, 16);
                default:
                    return value > Integer.parseInt(MAX_VAL_DECIMAL);
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean isNotValidInput_Destination() {
        try {
            int base = getBaseFromSpinner(spinnerDestination);
            int value = Integer.parseInt(txtDestination.getText().toString(), base);
            switch (base) {
                case 2:
                    return value > Integer.parseInt(MAX_VAL_BINARY, 2);
                case 8:
                    return value > Integer.parseInt(MAX_VAL_OCTAL, 8);
                case 16:
                    return value > Integer.parseInt(MAX_VAL_HEX, 16);
                default:
                    return value > Integer.parseInt(MAX_VAL_DECIMAL);
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean isNotValidInput_Display(int value) {
        try {
            int displayBase = getBaseFromSpinner(spinnerDisplay);
            int converted = Integer.parseInt(convert(String.valueOf(value), 10, displayBase));
            switch (displayBase) {
                case 2:
                    return converted > Integer.parseInt(MAX_VAL_BINARY, 2);
                case 8:
                    return converted > Integer.parseInt(MAX_VAL_OCTAL, 8);
                case 16:
                    return converted > Integer.parseInt(MAX_VAL_HEX, 16);
                default:
                    return converted > Integer.parseInt(MAX_VAL_DECIMAL);
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

    // Return numeric base for a spinner selection (0=binary,1=octal,2=decimal,3=hex)
    private int getBaseFromSpinner(Spinner spinner) {
        if (spinner == null) return 10;
        int pos = spinner.getSelectedItemPosition();
        switch (pos) {
            case BASE_POS_BINARY: return 2;
            case BASE_POS_OCTAL: return 8;
            case BASE_POS_HEX: return 16;
            default: return 10;
        }
    }
}
