package com.example.android.explorationgpa;



public class SemesterInfo {


    public SemesterInfo() {
    }



    /**
     * take the number of the year and term and specify the exact semester the user want
     *
     * @param year the year which the user choose (o-1-2-3-4).
     * @param term the term which the user choose (1-2).
     *
     * @return the semester that the user want (1-2-3-4-5-6-7-8-9-10)
     */
    public static int getNumberOfSemester(int year, int term) {

        int semester = 0;

        if (year == 0 && term == 1) {
            semester = 1;
        } else if (year == 0 && term == 2) {
            semester = 2;
        } else if (year == 1 && term == 1) {
            semester = 3;
        } else if (year == 1 && term == 2) {
            semester = 4;
        } else if (year == 2 && term == 1) {
            semester = 5;
        } else if (year == 2 && term == 2) {
            semester = 6;
        } else if (year == 3 && term == 1) {
            semester = 7;
        } else if (year == 3 && term == 2) {
            semester = 8;
        } else if (year == 4 && term == 1) {
            semester = 9;
        } else if (year == 4 && term == 2) {
            semester = 10;
        }

        return semester;
    }



    /**
     * determine the semester by the year & term and get the resources id for subjects string for
     * that semester in an array.
     *
     * @param year the year which the user select (0-1-2-3-4-5).
     * @param term the term which the user select (1-2).
     * @return array with resources id for subjects string for the semester
     */
    public static int[] getSubjectsOfSemester(int year, int term) {

        int numOfSemester = getNumberOfSemester(year, term);// determine the semester (1:10)

        int[] semester = null;

            switch (numOfSemester) {
                case 1:
                    semester = getSubjectsForSemester_1();
                    break;
                case 2:
                    semester = getSubjectsForSemester_2();
                    break;
                case 3:
                    semester = getSubjectsForSemester_3();
                    break;
                case 4:
                    semester = getSubjectsForSemester_4();
                    break;
                case 5:
                    semester = getSubjectsForSemester_5();
                    break;
                case 6:
                    semester = getSubjectsForSemester_6();
                    break;
                case 7:
                    semester = getSubjectsForSemester_7();
                    break;
                case 8:
                    semester = getSubjectsForSemester_8();
                    break;
                case 9:
                    semester = getSubjectsForSemester_9();
                    break;
                case 10:
                    semester = getSubjectsForSemester_10();
                    break;

            }
        return semester;
    }



    /**
     * determine the semester by the year & term and get the hours for the subjects in that semester
     * in an array.
     *
     * @param year the year which the user select (0-1-2-3-4-5).
     * @param term the term which the user select (1-2).
     * @return array with hours for the subjects in the semester
     */
    public static int[] getHoursForSemester(int year, int term) {

        int numOfSemester = getNumberOfSemester(year, term); // determine the semester (1:10)

        int[] hours = null;

        switch (numOfSemester) {
            case 1:
                hours = getHoursForSemester_1();
                break;
            case 2:
                hours = getHoursForSemester_2();
                break;
            case 3:
                hours = getHoursForSemester_3();
                break;
            case 4:
                hours = getHoursForSemester_4();
                break;
            case 5:
                hours = getHoursForSemester_5();
                break;
            case 6:
                hours = getHoursForSemester_6();
                break;
            case 7:
                hours = getHoursForSemester_7();
                break;
            case 8:
                hours = getHoursForSemester_8();
                break;
            case 9:
                hours = getHoursForSemester_9();
                break;
            case 10:
                hours = getHoursForSemester_10();
                break;
        }

        return hours;
    }




    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (1)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_1() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_1_subject_1;
        subjects[1] = R.string.semester_1_subject_2;
        subjects[2] = R.string.semester_1_subject_3;
        subjects[3] = R.string.semester_1_subject_4;
        subjects[4] = R.string.semester_1_subject_5;
        subjects[5] = R.string.semester_1_subject_6;
        subjects[6] = R.string.semester_1_subject_7;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (2)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_2() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_2_subject_1;
        subjects[1] = R.string.semester_2_subject_2;
        subjects[2] = R.string.semester_2_subject_3;
        subjects[3] = R.string.semester_2_subject_4;
        subjects[4] = R.string.semester_2_subject_5;
        subjects[5] = R.string.semester_2_subject_6;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (3)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_3() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_3_subject_1;
        subjects[1] = R.string.semester_3_subject_2;
        subjects[2] = R.string.semester_3_subject_3;
        subjects[3] = R.string.semester_3_subject_4;
        subjects[4] = R.string.semester_3_subject_5;
        subjects[5] = R.string.semester_3_subject_6;
        subjects[6] = R.string.semester_3_subject_7;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (4)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_4() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_4_subject_1;
        subjects[1] = R.string.semester_4_subject_2;
        subjects[2] = R.string.semester_4_subject_3;
        subjects[3] = R.string.semester_4_subject_4;
        subjects[4] = R.string.semester_4_subject_5;
        subjects[5] = R.string.semester_4_subject_6;
        subjects[6] = R.string.semester_4_subject_7;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (5)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_5() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_5_subject_1;
        subjects[1] = R.string.semester_5_subject_2;
        subjects[2] = R.string.semester_5_subject_3;
        subjects[3] = R.string.semester_5_subject_4;
        subjects[4] = R.string.semester_5_subject_5;
        subjects[5] = R.string.semester_5_subject_6;
        subjects[6] = R.string.semester_5_subject_7;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (6)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_6() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_6_subject_1;
        subjects[1] = R.string.semester_6_subject_2;
        subjects[2] = R.string.semester_6_subject_3;
        subjects[3] = R.string.semester_6_subject_4;
        subjects[4] = R.string.semester_6_subject_5;
        subjects[5] = R.string.semester_6_subject_6;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (7)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_7() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_7_subject_1;
        subjects[1] = R.string.semester_7_subject_2;
        subjects[2] = R.string.semester_7_subject_3;
        subjects[3] = R.string.semester_7_subject_4;
        subjects[4] = R.string.semester_7_subject_5;
        subjects[5] = R.string.semester_7_subject_6;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (8)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_8() {

        int[] subjects = new int[6];
        subjects[0] = R.string.semester_8_subject_1;
        subjects[1] = R.string.semester_8_subject_2;
        subjects[2] = R.string.semester_8_subject_3;
        subjects[3] = R.string.semester_8_subject_4;
        subjects[4] = R.string.semester_8_subject_5;
        subjects[5] = R.string.semester_8_subject_6;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (9)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_9() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_9_subject_1;
        subjects[1] = R.string.semester_9_subject_2;
        subjects[2] = R.string.semester_9_subject_3;
        subjects[3] = R.string.semester_9_subject_4;
        subjects[4] = R.string.semester_9_subject_5;
        subjects[5] = R.string.semester_9_subject_6;
        subjects[6] = R.string.semester_9_subject_7;

        return subjects;
    }

    /**
     * helper method in the class to setup the array to contain resources id for the subjects
     * string for semester (10)
     *
     * @return array with resources id for the subjects string
     */
    private static int[] getSubjectsForSemester_10() {

        int[] subjects = new int[7];
        subjects[0] = R.string.semester_10_subject_1;
        subjects[1] = R.string.semester_10_subject_2;
        subjects[2] = R.string.semester_10_subject_3;
        subjects[3] = R.string.semester_10_subject_4;
        subjects[4] = R.string.semester_10_subject_5;
        subjects[5] = R.string.semester_10_subject_6;
        subjects[6] = R.string.semester_10_subject_7;

        return subjects;
    }



    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (1)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_1() {

        int[] hours = new int[7];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 2;
        hours[4] = 2;
        hours[5] = 3;
        hours[6] = 3;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (2)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_2() {

        int[] hours = new int[6];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 3;
        hours[4] = 2;
        hours[5] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (3)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_3() {

        int[] hours = new int[7];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 2;
        hours[3] = 3;
        hours[4] = 2;
        hours[5] = 3;
        hours[6] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (4)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_4() {

        int[] hours = new int[7];
        hours[0] = 2;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 3;
        hours[4] = 3;
        hours[5] = 3;
        hours[6] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (5)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_5() {

        int[] hours = new int[7];
        hours[0] = 2;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 3;
        hours[4] = 3;
        hours[5] = 3;
        hours[6] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (6)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_6() {

        int[] hours = new int[6];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 2;
        hours[4] = 3;
        hours[5] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (7)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_7() {

        int[] hours = new int[6];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 2;
        hours[4] = 3;
        hours[5] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (8)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_8() {

        int[] hours = new int[6];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 2;
        hours[4] = 3;
        hours[5] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (9)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_9() {

        int[] hours = new int[7];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 3;
        hours[4] = 3;
        hours[5] = 2;
        hours[6] = 2;

        return hours;
    }

    /**
     * helper method in the class to setup the array to contain hours for the subjects in the
     * semester (10)
     *
     * @return array with hours for subjects
     */
    private static int[] getHoursForSemester_10() {

        int[] hours = new int[7];
        hours[0] = 3;
        hours[1] = 3;
        hours[2] = 3;
        hours[3] = 3;
        hours[4] = 3;
        hours[5] = 2;
        hours[6] = 2;

        return hours;
    }


}