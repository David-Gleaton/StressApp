package com.example.stressapp;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import static android.content.ContentValues.TAG;

/*
Team DJ
Database Structure heavily inspired by Zybooks
Handles creation, build, update and generation
 */


public class Database extends SQLiteOpenHelper {

    //Variables
    private static final String DATABASE_NAME = "stressinfo.db";
    private static final int VERSION = 1;
    private static final int MaxMonth = 32;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    //Database class for eating
    private static final class EatTable {
        private static final String TABLE = "eat";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_CALORIES = "calories";
    }

    //Database class for sleeping
    private static final class SleepTable{
        private static final String TABLE = "sleep";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_TOTALSLEEP = "totalsleep";


    }

    //Database class for ExcerciseTable
    private static final class ExcerciseTable {
        private static final String TABLE = "exercise";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_TOTALEXCERCISE = "totalexercise";
    }

    //Database class for SocialTable
    private static final class SocialTable {
        private static final String TABLE = "social";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_TOTALSOCIAL = "totalsocial";
        private static final String COL_UNIQUENUM = "people";
    }
    //Database class for FinanceTable
    private static final class FinanceTable {
        private static final String TABLE = "finance";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_MONEY = "money";
    }
    //Database class for MoodTable
    private static final class MoodTable {
        private static final String TABLE = "mood";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_MOOD = "mood";
    }



    //Pre:
    //Post: Creates all database tables and filles them with 0s
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create EatTable
        db.execSQL("create table " + EatTable.TABLE + " (" +
                EatTable.COL_ID + " integer primary key autoincrement, " +
                EatTable.COL_DATE + " text, " +
                EatTable.COL_CALORIES + " integer)");
        //Create SleepTable
        db.execSQL("create table " + SleepTable.TABLE + " (" +
                SleepTable.COL_ID + " integer primary key autoincrement, " +
                SleepTable.COL_DATE + " text, " +
                SleepTable.COL_TOTALSLEEP + " double)");
        //Create ExerciseTable
        db.execSQL("create table " + ExcerciseTable.TABLE + " (" +
                ExcerciseTable.COL_ID + " integer primary key autoincrement, " +
                ExcerciseTable.COL_DATE + " text, " +
                ExcerciseTable.COL_TOTALEXCERCISE + " double)");
        //Create SocialTable
        db.execSQL("create table " + SocialTable.TABLE + " (" +
                SocialTable.COL_ID + " integer primary key autoincrement, " +
                SocialTable.COL_DATE + " text, " +
                SocialTable.COL_TOTALSOCIAL + " double, " +
                SocialTable.COL_UNIQUENUM + " integer)");
        //Create FinanceTable
        db.execSQL("create table " + FinanceTable.TABLE + " (" +
                FinanceTable.COL_ID + " integer primary key autoincrement, " +
                FinanceTable.COL_DATE + " text, " +
                FinanceTable.COL_MONEY + " double)");
        //Create MoodTable
        db.execSQL("create table " + MoodTable.TABLE + " (" +
                MoodTable.COL_ID + " integer primary key autoincrement, " +
                MoodTable.COL_DATE + " text, " +
                MoodTable.COL_MOOD + " integer)");

        //Have 0th values for the month

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String dateTop = formatDate.format(date);
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(EatTable.COL_DATE, datePlace);
            values.put(EatTable.COL_CALORIES, 0);
            long success = db.insert(EatTable.TABLE, null, values);

        }

        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(SleepTable.COL_DATE, datePlace);
            values.put(SleepTable.COL_TOTALSLEEP, 0);
            long success = db.insert(SleepTable.TABLE, null, values);
        }

        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(ExcerciseTable.COL_DATE, datePlace);
            values.put(ExcerciseTable.COL_TOTALEXCERCISE, 0);
            long success = db.insert(ExcerciseTable.TABLE, null, values);
        }


        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(SocialTable.COL_DATE, datePlace);
            values.put(SocialTable.COL_TOTALSOCIAL, 0);
            values.put(SocialTable.COL_UNIQUENUM, 0);
            long success = db.insert(SocialTable.TABLE, null, values);

        }
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(FinanceTable.COL_DATE, datePlace);
            values.put(FinanceTable.COL_MONEY, 0);
            long success = db.insert(FinanceTable.TABLE, null, values);
        }
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(MoodTable.COL_DATE, datePlace);
            values.put(MoodTable.COL_MOOD, 0);
            long success = db.insert(MoodTable.TABLE, null, values);
        }

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        //Drop Tables if necessary
        db.execSQL("drop table if exists " + EatTable.TABLE);
        db.execSQL("drop table if exists " + SleepTable.TABLE);
        db.execSQL("drop table if exists " + ExcerciseTable.TABLE);
        db.execSQL("drop table if exists " + SocialTable.TABLE);
        db.execSQL("drop table if exists " + FinanceTable.TABLE);
        db.execSQL("drop table if exists " + MoodTable.TABLE);
        onCreate(db);
    }

    /*
    Before you go further, know that all addX, BuildXData, and returnX all follow the same order
     */



    //pre: int passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addEat(int calories){

        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        //Get date for input
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        //See if there is an input alreay
        String sqlCheck = "select * from " + EatTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;

        if (cursorCheck.moveToFirst()){
            removeid = cursorCheck.getLong(0);
        }
        cursorCheck.close();

        db = getWritableDatabase();

        //Overwrite Repeat if needed, else place new entry into db
        if(removeid != -1){
            ContentValues replace = new ContentValues();
            replace.put(EatTable.COL_CALORIES, calories);
            db.update(EatTable.TABLE,replace, EatTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else {

            ContentValues values = new ContentValues();
            values.put(EatTable.COL_DATE, formatDate.format(date));
            values.put(EatTable.COL_CALORIES, calories);


            long Id = db.insert(EatTable.TABLE, null, values);


            return Id;
        }
    }

    //pre:
    //post: builds random data into db
    public void BuildEatData(){

        SQLiteDatabase db = getReadableDatabase();

        //Makes month string, and makes random data based on a low of 100 and a high of 20000 calories
        String dateTop = "2021-04";
        Random r = new Random();
        int low = 100;
        int high = 20000;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomcalorie = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(EatTable.COL_DATE, datePlace);
            values.put(EatTable.COL_CALORIES, randomcalorie);
            db.update(EatTable.TABLE,values, EatTable.COL_DATE + " = ?", new String[]{datePlace});

        }

    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnEat(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][2];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + EatTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String calorie = cursor.getString(2);
                returnMonth[i][0] = time;
                returnMonth[i][1] = calorie;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;

    }


    //All Sleep Functions
    //pre: double passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addSleep(double TotalSleep){
        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String sqlCheck = "select * from " + SleepTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;
        if (cursorCheck.moveToFirst()){
            removeid = cursorCheck.getLong(0);
        }
        cursorCheck.close();


        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SleepTable.COL_DATE, formatDate.format(date));
        values.put(SleepTable.COL_TOTALSLEEP, TotalSleep);




        if(removeid != -1){
            db.update(SleepTable.TABLE,values, SleepTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else {

            long Id = db.insert(SleepTable.TABLE, null, values);


            return Id;
        }



    }

    //pre:
    //post: builds random data into db
    public void BuildSleepData(){

        SQLiteDatabase db = getReadableDatabase();


        String dateTop = "2021-04";
        Random r = new Random();
        int low = 4;
        int high = 12;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomsleep = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(SleepTable.COL_DATE, datePlace);
            values.put(SleepTable.COL_TOTALSLEEP, randomsleep);
            db.update(SleepTable.TABLE,values, SleepTable.COL_DATE + " = ?", new String[]{datePlace});
        }
    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnSleep(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][2];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + SleepTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String TotalSleep = cursor.getString(2);
                returnMonth[i][0] = time;
                returnMonth[i][1] = TotalSleep;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;

    }

    //pre: double passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addExcercise(double adjustedExcercise){
        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String sqlCheck = "select * from " + ExcerciseTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;
        if (cursorCheck.moveToFirst()){

            removeid = cursorCheck.getLong(0);
        }



        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExcerciseTable.COL_DATE, formatDate.format(date));
        values.put(ExcerciseTable.COL_TOTALEXCERCISE, adjustedExcercise);

        if(removeid != -1){
            db.update(ExcerciseTable.TABLE,values, ExcerciseTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else{
            long Id = db.insert(ExcerciseTable.TABLE, null, values);

            return Id;
        }






    }

    //pre:
    //post: builds random data into db
    public void BuildExcerciseData(){

        SQLiteDatabase db = getReadableDatabase();


        String dateTop = "2021-04";
        Random r = new Random();
        int low = 0;
        int high = 6;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomexcercise = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(ExcerciseTable.COL_DATE, datePlace);
            values.put(ExcerciseTable.COL_TOTALEXCERCISE, randomexcercise);
            db.update(ExcerciseTable.TABLE,values, ExcerciseTable.COL_DATE + " = ?", new String[]{datePlace});
        }

    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnExcercise(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][2];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + ExcerciseTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String TotalExcercise = cursor.getString(2);
                returnMonth[i][0] = time;
                returnMonth[i][1] = TotalExcercise;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;
    }


    //pre: double and an int passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addSocial(double adjustedSocial, int numppl){
        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String sqlCheck = "select * from " + SocialTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;
        if (cursorCheck.moveToFirst()){
            removeid = cursorCheck.getLong(0);
        }



        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SocialTable.COL_DATE, formatDate.format(date));
        values.put(SocialTable.COL_TOTALSOCIAL, adjustedSocial);
        values.put(SocialTable.COL_UNIQUENUM, numppl);



        if(removeid != -1){
            db.update(SocialTable.TABLE,values, SocialTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else{
            long Id = db.insert(SocialTable.TABLE, null, values);

            return Id;
        }



    }

    //pre:
    //post: builds random data into db
    public void BuildSocialData(){

        SQLiteDatabase db = getReadableDatabase();


        String dateTop = "2021-04";
        Random r = new Random();
        int low = 0;
        int high = 8;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomSocial = r.nextInt(high-low)+low;
            int randomUnique = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(SocialTable.COL_DATE, datePlace);
            values.put(SocialTable.COL_TOTALSOCIAL, randomSocial);
            values.put(SocialTable.COL_UNIQUENUM, randomUnique);
            db.update(SocialTable.TABLE,values, SocialTable.COL_DATE + " = ?", new String[]{datePlace});
        }

    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnSocial(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][3];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + SocialTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String TotalSocial = cursor.getString(2);
                String UniqueSocial = cursor.getString(3);
                returnMonth[i][0] = time;
                returnMonth[i][1] = TotalSocial;
                returnMonth[i][2] = UniqueSocial;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;

    }

    //pre: double passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addFinance(double money){
        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String sqlCheck = "select * from " + FinanceTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;
        if (cursorCheck.moveToFirst()){

            removeid = cursorCheck.getLong(0);
        }



        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FinanceTable.COL_DATE, formatDate.format(date));
        values.put(FinanceTable.COL_MONEY, money);


        if(removeid != -1){
            db.update(FinanceTable.TABLE,values, FinanceTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else{
            long Id = db.insert(FinanceTable.TABLE, null, values);
            return Id;
        }






    }

    //pre:
    //post: builds random data into db
    public void BuildFinanceData(){

        SQLiteDatabase db = getReadableDatabase();


        String dateTop = "2021-04";
        Random r = new Random();
        int low = 0;
        int high = 2000;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomFinance = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(FinanceTable.COL_DATE, datePlace);
            values.put(FinanceTable.COL_MONEY, randomFinance);
            db.update(FinanceTable.TABLE,values, FinanceTable.COL_DATE + " = ?", new String[]{datePlace});
        }

    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnFinance(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][2];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + FinanceTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String TotalFinance = cursor.getString(2);
                returnMonth[i][0] = time;
                returnMonth[i][1] = TotalFinance;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;
    }


    //pre: int passed in
    //post: writes passed in data to db, add new entry or updates an existing one if need be
    public long addMood(int mood){
        //Check for repeats and delete so only one record per day
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String sqlCheck = "select * from " + MoodTable.TABLE + " where date = ? ";
        Cursor cursorCheck = db.rawQuery(sqlCheck, new String[]{ formatDate.format(date) });
        long removeid = -1;
        if (cursorCheck.moveToFirst()){

            removeid = cursorCheck.getLong(0);
        }



        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MoodTable.COL_DATE, formatDate.format(date));
        values.put(MoodTable.COL_MOOD, mood);

        if(removeid != -1){
            db.update(MoodTable.TABLE,values, MoodTable.COL_ID + " = ?", new String[]{Long.toString(removeid)});
            return 1;
        }else{
            long Id = db.insert(MoodTable.TABLE, null, values);

            return Id;
        }






    }

    //pre:
    //post: builds random data into db
    public void BuildMoodData(){

        SQLiteDatabase db = getReadableDatabase();


        String dateTop = "2021-04";
        Random r = new Random();
        int low = 0;
        int high = 3;
        for(int i = 1; i<MaxMonth; i++){
            ContentValues values = new ContentValues();
            int randomMood = r.nextInt(high-low)+low;
            String datePlace = dateTop;
            if(i < 10){
                datePlace += "-0"+i;
            }else{
                datePlace += "-"+i;
            }
            values.put(MoodTable.COL_DATE, datePlace);
            values.put(MoodTable.COL_MOOD, randomMood);
            db.update(MoodTable.TABLE,values, MoodTable.COL_DATE + " = ?", new String[]{datePlace});
        }

    }

    //Pre:
    //post: Returns all data in a month from database in a 2d array
    public String[][] returnMood(){
        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();

        String[][] returnMonth = new String[MaxMonth][2];
        String search = formatDate.format(date) + "%";

        String sql = "select * from " + MoodTable.TABLE + " where date LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{ search });

        int i = 1;
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(1);
                String TotalMood = cursor.getString(2);
                returnMonth[i][0] = time;
                returnMonth[i][1] = TotalMood;
                i++;
                if(i >=MaxMonth){
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();



        return returnMonth;
    }



}