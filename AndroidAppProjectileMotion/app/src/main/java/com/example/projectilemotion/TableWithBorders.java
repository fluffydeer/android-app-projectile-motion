//https://stackoverflow.com/questions/34772248/how-to-create-dynamic-tablelayout-in-runtime-with-custom-horizontal-and-vertica
package com.example.projectilemotion;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class TableWithBorders extends TableLayout {
    private Context mContext;
    private int mTextColor, mBorderColor;
    private int mTextViewBorderWidth, mTableBorderWidth;

    private String[][] mTableContent;
    private ArrayList<Double> firstColumn, secondColumn, thirdColumn;

    public TableWithBorders(Context context, String[][] tableContent) {
        super(context);
        mContext = context;
        mTableContent = tableContent;
        setUpTable();
        createTable();
    }

    public TableWithBorders(
            Context context, ArrayList<Double> firstColumn,
            ArrayList<Double> secondColumn, ArrayList<Double> thirdColumn) {
        super(context);
        this.firstColumn = firstColumn;
        this.secondColumn = secondColumn;
        this.thirdColumn = thirdColumn;
        mContext = context;
        setUpTable();
        createTableWithThreeColumns();
    }

    private void setUpTable(){
        mTextColor = 0xAAFFFFFF;
        mBorderColor = 0xAAFFFFFF;
        mTextViewBorderWidth = 0;
        mTableBorderWidth = mTextViewBorderWidth * 2;
    }

    private void createTable() {
        TableRow tableRow;

        setStretchAllColumns(true);
        setBackground(borderDrawable(mTableBorderWidth));
        setPadding(mTableBorderWidth, mTableBorderWidth, mTableBorderWidth, mTableBorderWidth);

        for (int currentRow = 0; currentRow < mTableContent.length; currentRow++) {
            tableRow = new TableRow(mContext);

            for (int currentColumn = 0; currentColumn < mTableContent[0].length; currentColumn++) {
                /*textView = new TextView(mContext);
                textView.setTextColor(mTextColor);
                textView.setBackground(borderDrawable(mTextViewBorderWidth));
                textView.setText(mTableContent[currentRow][currentColumn]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(17);
                textView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL)); //@font/archivo_black
                textView.setPadding(0, 6, 0, 6);*/
                tableRow.addView(createTextView(mContext, mTableContent[currentRow][currentColumn]));

            }
            addView(tableRow);
        }
    }


    private void createTableWithThreeColumns() {
        TableRow tableRow;
        setStretchAllColumns(true);
        setBackground(borderDrawable(mTableBorderWidth));
        setPadding(mTableBorderWidth, mTableBorderWidth, mTableBorderWidth, mTableBorderWidth);
        for (int i = 0; i < firstColumn.size(); i++) {
            tableRow = new TableRow(mContext);
            TextView textViewT = createTextView(mContext, "" + String.format(Locale.ENGLISH,"%.2f", firstColumn.get(i)));;
            TextView textViewX = createTextView(mContext, "" + String.format(Locale.ENGLISH,"%.2f", secondColumn.get(i)));
            TextView textViewY = createTextView(mContext, "" + String.format(Locale.ENGLISH,"%.2f", thirdColumn.get(i)));

            tableRow.addView(textViewT);
            tableRow.addView(textViewX);
            tableRow.addView(textViewY);

            addView(tableRow);
        }
    }

    private GradientDrawable borderDrawable(int borderWidth) {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setStroke(borderWidth, mBorderColor);
        return shapeDrawable;
    }

    private TextView createTextView(Context mContext, String text) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(mTextColor);
        //textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(17);
        textView.setBackground(borderDrawable(mTextViewBorderWidth));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 6, 0, 6);
        textView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL)); //@font/archivo_black
        return textView;
    }
}