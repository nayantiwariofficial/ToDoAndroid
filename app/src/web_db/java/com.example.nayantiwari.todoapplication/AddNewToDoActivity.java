package com.example.nayantiwari.todoapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nayantiwari.todoapplication.dto.ToDo;
import com.example.nayantiwari.todoapplication.utility.NetworkUtils;
import com.example.nayantiwari.todoapplication.utility.ToDoLoader;

import java.text.ParseException;
import java.util.Date;

import static com.example.nayantiwari.todoapplication.ShowToDoActivity.TODO_DATE_TIME;
import static com.example.nayantiwari.todoapplication.ShowToDoActivity.TODO_ITEM_ID;
import static com.example.nayantiwari.todoapplication.ShowToDoActivity.TODO_MODE;
import static com.example.nayantiwari.todoapplication.ShowToDoActivity.TODO_TITLE_KEY;

public class AddNewToDoActivity extends AppCompatActivity {

    private static final String TAG = "AddNewToDoActivity";
    LinearLayout timeAndDateInfoLinearLayout;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int min;
    private String selectedDate, selectedTime;

    private EditText titleEditText;
    private Switch aSwitch;
    private EditText mDate;
    private EditText mTime;

    private String mode;

    private long todoId;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_to_do);

        timeAndDateInfoLinearLayout = (LinearLayout) findViewById(R.id.time_and_date_ll);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab2);

        mDate = (EditText) findViewById(R.id.date_et);
        mTime = (EditText) findViewById(R.id.time_et);

        aSwitch = (Switch) findViewById(R.id.switch1);

        titleEditText = (EditText) findViewById(R.id.enter_todo_et);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title;
        Intent intent = getIntent();
        if (intent.hasExtra(TODO_MODE)) {
            mode = intent.getStringExtra(TODO_MODE);
            todoId = intent.getLongExtra(TODO_ITEM_ID, 0);
            if (mode.equals("edit")) {
                if (intent.hasExtra(TODO_TITLE_KEY)) {
                    title = intent.getStringExtra(TODO_TITLE_KEY);
                    titleEditText.setText(title);
                }

                if (intent.hasExtra(TODO_DATE_TIME)) {
                    String dateTime = intent.getStringExtra(TODO_DATE_TIME);
                    if (dateTime != null) {
                        Log.i(TAG, "onCreate: " + "sub-stringing date time " + dateTime);
                            DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZ yyyy");
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat time = new SimpleDateFormat("HH:mm:ss");
                        try {
                            Date d = df.parse(dateTime);
                            String dateString = date.format(d);
                            String timeString = time.format(d);
                            Log.i(TAG, "onCreate: " + "sub-stringing date time" + dateString);
                            Log.i(TAG, "onCreate: " + "sub-stringing date time" + timeString);
                            aSwitch.setChecked(true);
                            timeAndDateInfoLinearLayout.setVisibility(View.VISIBLE);

                            mDate.setText(dateString);
                            mTime.setText(timeString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        mDate.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "OnClickListener";

            @Override
            public void onClick(View v) {
//                hideKeyboard(enterToDoEditText);
//                hideKeyboard(mDate);
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewToDoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public static final String TAG = "DatePickerDialog";

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.i(TAG, "onDateSet: " + year + " " + (month + 1) + " " + dayOfMonth);

                        if (month < 10 && dayOfMonth > 10) {
                            selectedDate = "0" + (month + 1) + "/" + dayOfMonth + "/" + year;
                            selectedDate = year + "-" + "0" + (month + 1) + "-" + dayOfMonth;
                        } else if (dayOfMonth < 10 && month > 10) {
                            selectedDate = year + "-" + (month + 1) + "-0" + dayOfMonth;
                        } else if (dayOfMonth < 10 && month < 10) {
                            selectedDate = year + "-" + "0" + (month + 1) + "-0" + dayOfMonth;
                        } else
                            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        Log.i(TAG, "onDateSet: " + selectedDate);
                        mDate.setText(selectedDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hideKeyboard(mTime);
                Calendar calendar = Calendar.getInstance();

                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewToDoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public static final String TAG = "TimePickerDialog";

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                      /*  if (hourOfDay > 12) {
                            amPm = "PM";
                            hour = hourOfDay - 12;
                        } else {
                            amPm = "AM";
                            hour = hourOfDay;
                        }*/
                        Log.i(TAG, "onTimeSet: " + hour + ":" + minute);
                        if (minute < 10 && hour > 10) {
                            selectedTime = hour + ":0" + minute + ":00";
                        } else if (hour < 10 && minute > 10) {
                            selectedTime = "0" + hour + ":" + minute + ":00";
                        } else if (hour < 10 && minute < 10) {
                            selectedTime = "0" + hour + ":0" + minute + ":00";
                        } else
                            selectedTime = hour + ":" + minute + ":00";

                        mTime.setText(selectedTime);
                    }
                }, hour, min, false);
                timePickerDialog.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.getText().length() == 0) {
                    Toast.makeText(AddNewToDoActivity.this, "Please enter title for your ToDo", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (aSwitch.isChecked()) {
                        if (mDate.getText().length() == 0) {
                            if (mTime.getText().length() == 0) {
                                Toast.makeText(AddNewToDoActivity.this,
                                        "Please enter the date and time you want to set your reminder to",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(AddNewToDoActivity.this,
                                        "Please enter the date you want to set your reminder to",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (mTime.getText().length() == 0) {
                            if (mDate.getText().length() == 0) {
                                Toast.makeText(AddNewToDoActivity.this,
                                        "Please enter the date and time you want to set your reminder to",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(AddNewToDoActivity.this,
                                        "Please enter the time you want to set your reminder to",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        addNewToDo(titleEditText.getText().toString(),
                                mDate.getText().toString() + " " + mTime.getText().toString(),
                                1);
                    } else {
                        mDate.setText(null);
                        mTime.setText(null);
                        if (mode.equals("edit"))
                            addNewToDo(titleEditText.getText().toString(),
                                    mDate.getText().toString() + " " + mTime.getText().toString(),
                                    0);
                    }
                }

                Intent intent = new Intent(AddNewToDoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aSwitch.isChecked()) {
                    timeAndDateInfoLinearLayout.setVisibility(View.GONE);
                } else {
                    timeAndDateInfoLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addNewToDo(String title, String selectedDateAndTime, int checked) {
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setId(todoId);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = new Date();
        try {
            startDate = df.parse(selectedDateAndTime);
            String newDateString = df.format(startDate);
            Log.i(TAG, "addNewToDo: " + newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        toDo.setIsChecked(checked);
        if (checked == 1) {
            toDo.setDate(startDate);
        }

        if (!mode.equals("edit")) {
            String toDoUrl = NetworkUtils.buildUrl(todoId).toString();
            RequestType requestType = RequestType.POST;
            ToDoLoader toDoLoader = new ToDoLoader(this, toDoUrl, requestType, toDo);
            toDoLoader.forceLoad();
        } else {
            String toDoUrl = NetworkUtils.buildUrl(todoId).toString();
            RequestType requestType = RequestType.PUT;
            ToDoLoader toDoLoader = new ToDoLoader(this, toDoUrl, requestType, toDo);
            toDoLoader.forceLoad();
        }
    }
}
