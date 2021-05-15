package com.example.android.explorationgpa.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.explorationgpa.data.ExplorationContract.SemesterGpaEntry;
import com.example.android.explorationgpa.data.ExplorationContract.CumulativeGpaEntry;



public class ExplorationProvider extends ContentProvider {


    public static final String LOG_TAG = ExplorationProvider.class.getSimpleName(); // class name.


    private SemesterGpaDbHelper mSemesterDbHelper; // get instance of the semester database.
    private CumulativeGpaDbHelper mCumulativeGpaDbHelper; // // get instance of the cumulative database.

    private static final int SEMESTER_GPA = 100; // URI pattern to all the semester_gpa table.
    private static final int SEMESTER_GPA_ID = 101; // URI pattern to single column in the semester_gpa table.

    private static final int CUMULATIVE_GPA = 200; // URI pattern to all the cumulative_gpa table.
    private static final int CUMULATIVE_GPA_ID = 201; // URI pattern to single column in the cumulative_gpa table.


    // initialize the UriMatcher.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // add the table paths and his pattern number in the uri matcher.
    static {
        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_SEMESTER_GPA, SEMESTER_GPA);
        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_SEMESTER_GPA + "/#", SEMESTER_GPA_ID);

        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_CUMULATIVE_GPA, CUMULATIVE_GPA);
        sUriMatcher.addURI(ExplorationContract.CONTENT_AUTHORITY, ExplorationContract.PATH_CUMULATIVE_GPA + "/#", CUMULATIVE_GPA_ID);

    }


    /**
     * initialize databases here.
     */
    @Override
    public boolean onCreate() {

        // initialize databases (semester - cumulative).
        mSemesterDbHelper = new SemesterGpaDbHelper(getContext());
        mCumulativeGpaDbHelper = new CumulativeGpaDbHelper(getContext());

        return false;

    }



    /**
     * Read from the database (all the database or a single row).
     *
     * @param uri uri for the path in the database.
     * @param projection specific column in the database.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     * @param sortOrder the order that the results must return with.
     *
     * @return Cursor contain the results after the reading from database finish.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // access to databases (semester - cumulative) to read the data from it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getReadableDatabase();
        SQLiteDatabase cumulativeDatabase = mCumulativeGpaDbHelper.getReadableDatabase();

        // for the return result from the database.
        Cursor cursor;

        // get the pattern that the uri equal.
        int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern.
        switch (match) {

            // access to semester database with no id.
            case SEMESTER_GPA:

                // setup the input inside the query function.
                cursor = semesterDatabase.query(SemesterGpaEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to semester database with id.
            case SEMESTER_GPA_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = SemesterGpaEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = semesterDatabase.query(SemesterGpaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            // access to cumulative database with no id.
            case CUMULATIVE_GPA:

                // setup the input inside the query function.
                cursor = cumulativeDatabase.query(CumulativeGpaEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);

                break;

            // access to cumulative database with id.
            case CUMULATIVE_GPA_ID:

                // setup the input inside the query function (after the WHERE word).
                selection = CumulativeGpaEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = cumulativeDatabase.query(CumulativeGpaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // return Cursor contain the data from the database.
        return cursor;
    }



    /**
     * Write inside the database to add a new data.
     *
     * @param uri uri for the database path.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    @Override
    public Uri insert( Uri uri, ContentValues values) {

        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern (no single uris by id).
        switch (match) {

            // semester_gpa table inside semester database.
            case SEMESTER_GPA:

                // execute helper method to insert the data inside the semester database.
                // return the uri that refer to the row place inside the semester database.
                return insertSemesterGpa(uri, values);


            // cumulative_gpa table inside cumulative database.
            case CUMULATIVE_GPA:

                // execute helper method to insert the data inside the cumulative database.
                // return the uri that refer to the row place inside the cumulative database.
                return insertCumulativeGpa(uri, values);

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    /**
     * (Helper Method to Insert Data Inside semester Database).
     * Insert data inside the semester_gpa table in semester database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertSemesterGpa(Uri uri, ContentValues values) {

        // sanity chick for the student name before enter it inside the database.
        String studentName = values.getAsString(SemesterGpaEntry.COLUMN_STUDENT_NAME);
        if (studentName == null) {
            throw new IllegalArgumentException("gpa item for semester requires a name");
        }

        // sanity chick for the student ID before enter it inside the database.
        Integer studentCode = values.getAsInteger(SemesterGpaEntry.COLUMN_STUDENT_ID);
        if (studentCode == null || studentCode < 0) {
            throw new IllegalArgumentException("gpa item for semester requires valid id");
        }

        // sanity chick for the semester number before enter it inside the database.
        Integer studentSemester = values.getAsInteger(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
        if (studentSemester == null || !SemesterGpaEntry.isValidSemester(studentSemester)) {
            throw new IllegalArgumentException("gpa item for semester requires valid semester number");
        }


        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = semesterDatabase.insert(SemesterGpaEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in semester database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }


    /**
     * (Helper Method to Insert Data Inside cumulative Database).
     * Insert data inside the cumulative_gpa in cumulative database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the path inside the database.
     * @param values contain the columns keys and its values.
     *
     * @return uri refer to the row place inside the database.
     */
    private Uri insertCumulativeGpa(Uri uri, ContentValues values) {

        // sanity chick for the student name before enter it inside the database.
        String studentName = values.getAsString(CumulativeGpaEntry.COLUMN_STUDENT_NAME);
        if (studentName == null) {
            throw new IllegalArgumentException("gpa item for cumulative requires a name");
        }

        // sanity chick for the student ID before enter it inside the database.
        Integer studentCode = values.getAsInteger(CumulativeGpaEntry.COLUMN_STUDENT_ID);
        if (studentCode == null || studentCode < 0) {
            throw new IllegalArgumentException("gpa item for cumulative requires valid id");
        }


        // access to the database to write inside it.
        SQLiteDatabase cumulativeDatabase = mCumulativeGpaDbHelper.getWritableDatabase();

        // insert the data inside the database and get the row number that the data inserted at.
        long id = cumulativeDatabase.insert(CumulativeGpaEntry.TABLE_NAME, null, values);

        // if the insertion failed show a Log(e) for that.
        // make the uri return value equal null in case the insertion failed.
        if (id == -1) {

            Log.e(LOG_TAG, "Failed to insert row in cumulative database for " + uri);

            return null;
        }

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri for the place that the data inserted inside database.
        return ContentUris.withAppendedId(uri, id);

    }



    /**
     * Write inside the database to update data inserted before.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // setup functions for every uri pattern.
        switch (match) {

            // semester_gpa database with no id.
            case SEMESTER_GPA:

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateSemesterGpa(uri, contentValues, selection, selectionArgs);

            // semester_gpa database with id.
            case SEMESTER_GPA_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = SemesterGpaEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // execute helper method to update the data inside the database.
                // return number of the rows that updated.
                return updateSemesterGpa(uri, contentValues, selection, selectionArgs);

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    /**
     * (Helper Method to Update Data Inside semester Database).
     * Insert data inside the semester gpa database.
     * Check the validation for the data inserted from the user.
     *
     * @param uri uri for the database path.
     * @param contentValues contain the columns keys and its values.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return uri refer to the row place inside the database.
     */
    private int updateSemesterGpa(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // sanity chick for the student name before update it inside the database.
        // check if the student name will update ot not.
        if (contentValues.containsKey(SemesterGpaEntry.COLUMN_STUDENT_NAME)) {

            String studentName = contentValues.getAsString(SemesterGpaEntry.COLUMN_STUDENT_NAME);
            if (studentName == null) {
                throw new IllegalArgumentException("gpa item requires a name");
            }
        }

        // sanity chick for the student ID before update it inside the database.
        // check if the student ID will update ot not.
        if (contentValues.containsKey(SemesterGpaEntry.COLUMN_STUDENT_ID)) {

            Integer studentCode = contentValues.getAsInteger(SemesterGpaEntry.COLUMN_STUDENT_ID);
            if (studentCode == null || studentCode < 0) {
                throw new IllegalArgumentException("gpa item requires valid id");

            }
        }

        // sanity chick for the semester number before update it inside the database.
        // check if the semester number will update ot not.
        if (contentValues.containsKey(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER)) {

            Integer studentLevel = contentValues.getAsInteger(SemesterGpaEntry.COLUMN_SEMESTER_NUMBER);
            if (studentLevel == null || !SemesterGpaEntry.isValidSemester(studentLevel)) {
                throw new IllegalArgumentException("gpa item requires valid semester number");
            }
        }

        // if there is not keys inside the ContentValues return from the functions early.
        // the number of the rows that updated in this case will equal zero.
        if (contentValues.size() == 0) {
            return 0;
        }

        // access to the database to write inside it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getWritableDatabase();

        // update the data inside the database and get the number of the rows that updated.
        int rowsUpdated = semesterDatabase.update(SemesterGpaEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify that there is changing happened in the database to sync changes to the network or activities.
        getContext().getContentResolver().notifyChange(uri, null);

        // return the number of the rows that updated.
        return rowsUpdated;
    }



    /**
     * delete data from the database (all the database or a single row).
     *
     * @param uri uri for the database path.
     * @param selection specific row in the database.
     * @param selectionArgs the value for the selection parameter above.
     *
     * @return number of the rows that deleted from the database.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // access to databases (semester - cumulative) to write inside it.
        SQLiteDatabase semesterDatabase = mSemesterDbHelper.getWritableDatabase();
        SQLiteDatabase cumulativeDatabase = mCumulativeGpaDbHelper.getWritableDatabase();


        // get the pattern that the uri equal.
        final int match = sUriMatcher.match(uri);

        // number of the rows that will be deleted
        int rowsDeleted;

        // setup functions for every uri pattern.
        switch (match) {

            // semester_gpa database with no id.
            case SEMESTER_GPA:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = semesterDatabase.delete(SemesterGpaEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case SEMESTER_GPA_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = SemesterGpaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = semesterDatabase.delete(SemesterGpaEntry.TABLE_NAME, selection, selectionArgs);
                break;



            // semester_gpa database with no id.
            case CUMULATIVE_GPA:

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = cumulativeDatabase.delete(CumulativeGpaEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // semester_gpa database with id.
            case CUMULATIVE_GPA_ID:

                // setup the input inside the update function (after the WHERE word).
                selection = CumulativeGpaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // delete the data from the database.
                // return number of the rows that deleted from the database.
                rowsDeleted = cumulativeDatabase.delete(CumulativeGpaEntry.TABLE_NAME, selection, selectionArgs);
                break;

            // to handle if the is no match for the uri inserted with the uri patterns.
            default:

                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // notify the network or the activity when there is changing happened inside the database.
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of the rows that deleted from the database.
        return rowsDeleted;
    }



    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case SEMESTER_GPA:
                return SemesterGpaEntry.CONTENT_LIST_TYPE;

            case SEMESTER_GPA_ID:
                return SemesterGpaEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}
