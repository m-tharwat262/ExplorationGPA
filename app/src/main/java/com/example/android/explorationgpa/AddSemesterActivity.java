package com.example.android.explorationgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddSemesterActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddSemesterActivity.class.getSimpleName(); // activity name.

    private TextView nameTextView; // for the basic info part in the layout.
    private TextView idTextView; // for the basic info part in the layout.
    private TextView semesterTextView; // for the basic info part in the layout.

    private LinearLayout linearForTotalGpa; // the linear that display the (the total gap) statement.
    private Button doneButton; // fot the button which calculate gpa.

    int yearNumber; // (0-1-2-3-4-5)
    int termNumber; // (1-2)

    private SemesterAdapter semesterAdapter; // the adapter which display the semester subjects in the listView.

    private static final int MODE_FIRST_OPEN = 0; // first time the user open the activity to add a new semester.
    private static final int MODE_DISPLAY_TOTAL_GPA = 1; // mode that display the total gpa and prevent the user to edit anything.
    private int mode = MODE_FIRST_OPEN; // the basic mode that use across the all activity functions.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        // get the specific view from the xml layout.
        nameTextView = (TextView) findViewById(R.id.activity_add_semester_name);
        idTextView = (TextView) findViewById(R.id.activity_add_semester_id);
        semesterTextView = (TextView) findViewById(R.id.activity_add_semester_number_of_semester);

        linearForTotalGpa = (LinearLayout) findViewById(R.id.activity_add_semester_linear_for_total_gpa);
        doneButton = (Button) findViewById(R.id.activity_add_semester_button_done);

        // get the information which the intent (from infoActivity) that open that activity.
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("student_name"); // for student name.
        int studentId = intent.getIntExtra("student_id",0); // for student id.
        yearNumber = intent.getIntExtra("year_number", 0); // for number of the year.
        termNumber = intent.getIntExtra("term_number", 0); // for number of the term.
        Log.i(LOG_TAG, "intent come from InfoActivity contain : " + studentName + "  " + studentId + " " + yearNumber + " " + termNumber);



        // show the user basic data on the top of the layout.
        setupTheBasicInfo(studentName, studentId, yearNumber, termNumber);

        // show the year and term name in the layout.
        setupTheYearAndTerm(yearNumber, termNumber);

        // fix the overlap between the views in the layout.
        setupTheLayoutShadows();

        // start the first mode for the activity.
        startMode0();

        // control the clicks on the done buttons.
        setupDoneButtonFunctions();


    }



    /**
     * show the user basic data on the top of the layout which include the student name & id and
     * number of the semester that he want calculate the gpa in.
     *
     * @param studentName the student name which comes from the InfoActivity.
     * @param studentId the student id which comes from the InfoActivity.
     * @param yearNumber the number of the year which comes from the InfoActivity.
     * @param termNumber the number of the term comes from the InfoActivity.
     */
    private void setupTheBasicInfo(String studentName, int studentId, int yearNumber, int termNumber) {

        // get the exact number of the semester by the year and term which the user choose
        int semester = SemesterInfo.getNumberOfSemester(yearNumber, termNumber);

        // write the user information in the top part of the layout (basic info part)
        nameTextView.setText(String.valueOf(studentName));
        idTextView.setText(String.valueOf(studentId));
        semesterTextView.setText(String.valueOf(semester));

    }


    /**
     * display the exact year and term name in the layout.
     *
     * @param yearNumber number of the year that the user want calculate the gpa in.
     * @param termNumber number of the term that the user want calculate the gpa in.
     */
    private void setupTheYearAndTerm(int yearNumber, int termNumber) {

        int yearNameResourceId;
        int termNameResourceId;

        // to get the resource id for the year name by the know the year number.
        switch (yearNumber) {
            case 1:
                yearNameResourceId = R.string.year_one;
                break;
            case 2:
                yearNameResourceId = R.string.year_two;
                break;
            case 3:
                yearNameResourceId = R.string.year_three;
                break;
            case 4:
                yearNameResourceId = R.string.year_four;
                break;
            default:
                yearNameResourceId = R.string.year_zero;

        }

        // display the year name in the layout.
        String yearName = getString(yearNameResourceId);
        TextView yearNameTextView = (TextView) findViewById(R.id.activity_add_semester_year_name);
        yearNameTextView.setText(yearName);


        // to get the resource id for the term name by the know the term number.
        if (termNumber == 1) {
            termNameResourceId = R.string.term_first;
        } else {
            termNameResourceId = R.string.term_second;
        }

        // display the term name inside bracts in the layout.
        String termName = getString(termNameResourceId);
        String termNameInBracts = " (" + termName + ")";
        TextView termNameTextView = (TextView) findViewById(R.id.activity_add_semester_term_name);
        termNameTextView.setText(termNameInBracts);

    }


    /**
     * Fix the overlap between the views (the basic info part & done button) and other views in the
     * layout by know the height of the views and make shadows behind it.
     */
    private void setupTheLayoutShadows() {

        // determine linear that contain the basic info part.
        final LinearLayout basicInfoLayout = findViewById(R.id.activity_add_semester_linear_for_student_info);

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForBasicInfoLayout = basicInfoLayout.getViewTreeObserver();
        observerForBasicInfoLayout.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // setup the height of the shadow to be equal the height of the basic info part.
                int heightOfBasicInfoLayout = basicInfoLayout.getHeight();
                View shadowViewOfBasicInfo = findViewById(R.id.activity_add_semester_shadow_for_linear_basic_info);
                ViewGroup.LayoutParams paramsForBasicInfoLayout =  shadowViewOfBasicInfo.getLayoutParams();
                paramsForBasicInfoLayout.height = heightOfBasicInfoLayout;
                shadowViewOfBasicInfo.setLayoutParams(paramsForBasicInfoLayout);

            }
        });


        // determine done button in the activity layout.
        final Button doneButton = (Button) findViewById(R.id.activity_add_semester_button_done);

        // to get the height after the activity layout inflated.
        ViewTreeObserver observerForButton = doneButton.getViewTreeObserver();
        observerForButton.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // setup the height of the shadow to be equal the height of the done button.
                int heightOfDoneButtonHeight = doneButton.getHeight();
                View shadowViewOfDoneButton = findViewById(R.id.activity_add_semester_shadow_for_done_button);
                ViewGroup.LayoutParams paramsForDoneButton =  shadowViewOfDoneButton.getLayoutParams();
                paramsForDoneButton.height = heightOfDoneButtonHeight;
                shadowViewOfDoneButton.setLayoutParams(paramsForDoneButton);

            }
        });


    }


    /**
     * (for add mode).
     * for the first time the user open the activity to add a new semester.
     */
    private void startMode0() {

        Log.i(LOG_TAG, "the AddSemesterActivity mode is   :   0");

        // get ArrayList of the SubjectObjects that contain the info about the semester subject.
        ArrayList<SubjectObject> subjectObjects = getArrayListOfSubjectsObjects(yearNumber, termNumber);

        // setup the ListView that display the semester subject info.
        semesterAdapter = new SemesterAdapter(this, subjectObjects);
        ListView listView = (ListView) findViewById(R.id.activity_add_semester_list_view);
        listView.setAdapter(semesterAdapter);

        // no need to show the total gpa statement at that mode.
        linearForTotalGpa.setVisibility(View.GONE);
        // to display word DONE on the done button.
        doneButton.setText(R.string.button_done);

    }


    /**
     * get an ArrayList of SubjectObjects that include subject info of any semester by know the
     * year and term number.
     *
     * @param year number of the year that the user choose.
     * @param term number of the term that the user choose.
     *
     * @return ArrayList of SubjectObjects for the semester.
     */
    private ArrayList<SubjectObject> getArrayListOfSubjectsObjects(int year, int term) {

        // to get the resources ids for the subjects name.
        int[] ids = SemesterInfo.getSubjectsOfSemester(year, term);

        // initialise the ArrayList of the SubjectObjects.
        ArrayList<SubjectObject> subjectObjects = new ArrayList<>();

        // add the resources ids to the subjects name to the ArrayList.
        for (int i = 0 ; i < ids.length ; i++) {
            subjectObjects.add(new SubjectObject(ids[i]));
        }

        return subjectObjects;
    }


    /**
     * Control all functions that the done button must execute and calculate the
     * Switch between modes like (add - edit)
     */
    private void setupDoneButtonFunctions() {

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // switch between mods.
                if (mode == MODE_FIRST_OPEN) {

                    // change mode from (0) to (1) across all the activity functions.
                    mode = MODE_DISPLAY_TOTAL_GPA;

                    // to execute the functions at the mode 1.
                    startMode1();
                } else if (mode == MODE_DISPLAY_TOTAL_GPA) {

                    // TODO: setup the next click on the button.

                }

            }
        });

    }


    private void startMode1() {

        Log.i(LOG_TAG, "the AddSemesterActivity mode is   :   1");

        // TODO: setup the mode functions.

    }


}
