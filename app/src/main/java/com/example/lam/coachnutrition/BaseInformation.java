package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class BaseInformation {

    public static final String AUTHORITY = "com.projet.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri CONTENT_URI_FOOD = BASE_CONTENT_URI.buildUpon()
            .appendPath(FoodEntry.TABLE_FOOD).build();

    public static final Uri CONTENT_URI_FOOD_CATEGORY = BASE_CONTENT_URI.buildUpon()
            .appendPath(FoodCategoryEntry.TABLE_FOOD_CATEGORY).build();

    public static final Uri CONTENT_URI_MEAL = BASE_CONTENT_URI.buildUpon()
            .appendPath(MealEntry.TABLE_MEAL).build();

    public static final int FOOD_CODE = 1, FOOD_CATEGORY_CODE = 2, MEAL_CODE = 3;

    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, FoodEntry.TABLE_FOOD, FOOD_CODE);
        uriMatcher.addURI(AUTHORITY, FoodCategoryEntry.TABLE_FOOD_CATEGORY, FOOD_CATEGORY_CODE);
        uriMatcher.addURI(AUTHORITY, MealEntry.TABLE_MEAL, MEAL_CODE);
    }

    public static final class FoodEntry implements BaseColumns {

        //Table name
        public static final String TABLE_FOOD = "food";

        //Columns
        public static final String _ID = "_id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_COLORIE = "calorie";
        public final static String COLUMN_CATEGORY = "category";

    }

    public static final class FoodCategoryEntry implements BaseColumns {

        //Table name
        public static final String TABLE_FOOD_CATEGORY = "food_category";

        //Columns
        public static final String _ID = "_id";
        public final static String COLUMN_CODE = "code";
        public final static String COLUMN_CODE_FOOD = "code_food";
        public final static String COLUMN_NAME = "name";

    }

    public static final class MealEntry implements BaseColumns {

        //Table name
        public static final String TABLE_MEAL = "meal";

        //Columns
        public static final String _ID = "_id";
        public final static String COLUMN_CODE = "code";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_FOOD = "food";
        public final static String COLUMN_WEIGHT = "weight";
        public final static String COLUMN_TIMESTAMP = "timestamp";

    }
}