package burndown.aether.com.burndown;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateSprint extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    EditText editDateFrom, editTimeFrom, editDateTo, editTimeTo, sprintNameEdit, sprintResnEdit, sprintDefnEdit;
    ImageButton pickDateFrom, pickTimeFrom, pickDateTo, pickTimeTo;
    Button addSubtask ;
    long dateTimeFrom, dateTimeTo;
    FloatingActionButton submitBtn;
    ArrayList<String> suggest;
    ScrollView scrollView;
    LinearLayout main;
    Date currentime;
    int subtaskCtr;
    int CURRENT_PICKED = 0;
    String dateFrom, dateTo;
    String timeFrom, timeTo;

    LocalDateTime dateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sprints);
        setTitle("Create Sprint");
        main = findViewById(R.id.main);
        subtaskCtr = 0;


        sprintNameEdit = findViewById(R.id.sprintNameEdit);
        sprintDefnEdit = findViewById(R.id.sprintDefnEdit);
        sprintResnEdit = findViewById(R.id.sprintResnEdit);
        currentime = Calendar.getInstance().getTime();
        scrollView = findViewById(R.id.scrollView);
        submitBtn = findViewById(R.id.submitBtn);
        editDateFrom= findViewById(R.id.editDateFrom);
        editDateTo = findViewById(R.id.editDateTo);
        editTimeFrom = findViewById(R.id.editTimeFrom);
        editTimeTo = findViewById(R.id.editTimeTo);
        pickDateFrom = findViewById(R.id.pickDateFrom);
        pickDateTo = findViewById(R.id.pickDateTo);
        pickTimeFrom = findViewById(R.id.pickTimeFrom);
        pickTimeTo = findViewById(R.id.pickTimeTo);
        addSubtask = findViewById(R.id.addSubtask);





        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MM dd yyyy HH:mm");
                if(dateFrom!= null && timeFrom!= null && dateTo!=null && timeTo!=null) {
                    String x = editDateFrom.getText().toString().replaceAll("/" ," ") + " " + editTimeFrom.getText().toString();
                    String y = editDateTo.getText().toString().replaceAll("/" ," ") + " " + editTimeTo.getText().toString();
                    try {

                        dateTimeFrom = new SimpleDateFormat("MM dd yyyy HH:mm").parse(x).getTime();
                        dateTimeTo = new SimpleDateFormat("MM dd yyyy HH:mm").parse(y).getTime();
                        Toast.makeText(CreateSprint.this,   "" +String.valueOf( dateTimeFrom  ) , Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(CreateSprint.this, ""+ e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }


                }
                if(checkValid()) {
                    submitBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                    submitBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                }
                else {
                    submitBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_24dp));
                    submitBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }

                handler.postDelayed(this, delay);
            }
        }, delay);




        final DatePickerDialog pickerDateFrom = new DatePickerDialog(this,CreateSprint.this ,1900+currentime.getYear(),currentime.getMonth(),currentime.getDay());

        final TimePickerDialog pickerTimeFrom = new TimePickerDialog(this, CreateSprint.this, currentime.getHours()-4 ,currentime.getMinutes(), false);
        final DatePickerDialog pickerDateTo = new DatePickerDialog(this,CreateSprint.this ,1900+currentime.getYear(),currentime.getMonth(),currentime.getDay());
        final TimePickerDialog pickerTimeTo = new TimePickerDialog(this, CreateSprint.this, currentime.getHours()-4 ,currentime.getMinutes(), false);
        editDateTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getApplicationContext(), ""+ String.valueOf(editDateTo.getCurrentTextColor()), Toast.LENGTH_SHORT).show();
                if( !TextUtils.isEmpty(editDateTo.getText()) && !TextUtils.isEmpty(editTimeTo.getText())&& !TextUtils.isEmpty(editDateFrom.getText()) && !TextUtils.isEmpty(editTimeFrom.getText())  && dateFrom.compareTo(dateTo) == 1)  editDateTo.setTextColor( getColor(R.color.red));
                else editDateTo.setTextColor( getColor(R.color.normalText));
            }
        });

        pickTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerTimeTo.show();
                CURRENT_PICKED = 1110;

            }
        });
        pickTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerTimeFrom.show();
                CURRENT_PICKED = 1011;
            }
        });
        pickDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateTo.show();
                CURRENT_PICKED = 1101;
            }
        });
        pickDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateFrom.show();
                CURRENT_PICKED = 0111;
            }
        });

        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View subtaskView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.subtask, null);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20,20,20,20);
                subtaskView.setLayoutParams(lp);
                main.addView(subtaskView);
                scrollView.fullScroll(View.FOCUS_DOWN);
                subtaskCtr+=1;

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = editDateFrom.getText().toString().replaceAll("/" ," ") + " " + editTimeFrom.getText().toString();
                String y = editDateTo.getText().toString().replaceAll("/" ," ") + " " + editTimeTo.getText().toString();
                try {

                    dateTimeFrom = new SimpleDateFormat("MM dd yyyy HH:mm").parse(x).getTime();
                    dateTimeTo = new SimpleDateFormat("MM dd yyyy HH:mm").parse(y).getTime();
                    Toast.makeText(CreateSprint.this,   "" +String.valueOf( dateTimeFrom  ) , Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(CreateSprint.this, ""+ e.getStackTrace(), Toast.LENGTH_SHORT).show();
                }

                if(checkValid()) {
                    submitBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                    submitBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                }
                else {
                    submitBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_24dp));
                    submitBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }




            }
        });









    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      //  editDateFrom.setText(String.valueOf(month )+"/" + String.valueOf(dayOfMonth) + "/" +String.valueOf(year));

       if (CURRENT_PICKED == 0111)
        {
            editDateFrom.setText(String.valueOf(month+1 )+"/" + String.valueOf(dayOfMonth) + "/" +String.valueOf(year));

        }
        if(CURRENT_PICKED == 1101)
        {
            editDateTo.setText(String.valueOf(month+1 )+"/" + String.valueOf(dayOfMonth) + "/" +String.valueOf(year));
        }


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
       // editTimeFrom.setText(String.valueOf(hourOfDay) + ":" +String.valueOf(minute));
       if (CURRENT_PICKED == 1011)
        {
            editTimeFrom.setText(String.valueOf(hourOfDay) + ":" +String.valueOf(minute));

        }
        if(CURRENT_PICKED == 1110)
        {
            editTimeTo.setText(String.valueOf(hourOfDay) + ":" +String.valueOf(minute));
        }

    }

    //editDateFrom, editTimeFrom, editDateTo, editTimeTo, sprintNameEdit, sprintResnEdit, sprintDefnEdit;
    public boolean checkValid() {
        if((TextUtils.isEmpty(editDateFrom.getText()) || TextUtils.isEmpty(editTimeFrom.getText()) || TextUtils.
                isEmpty(editDateTo.getText()) || TextUtils.isEmpty(editTimeTo.getText()) || TextUtils.
                isEmpty(sprintNameEdit.getText()) || TextUtils.isEmpty(sprintResnEdit.getText()) || TextUtils.
                isEmpty(sprintDefnEdit.getText())   ) && dateTimeTo-dateTimeFrom <=0   ) {



             return false;
        }
        else return true;

    }
}


