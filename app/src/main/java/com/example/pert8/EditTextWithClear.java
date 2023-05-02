package com.example.pert8;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.LocaleListCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable clearButtonImage;
    boolean isClearButtonClicked;

    private void init(){
        clearButtonImage = ResourcesCompat.getDrawable(
                getResources(), R.drawable.ic_clear_opoque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(getCompoundDrawablesRelative()[2] != null) {
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL){
                        //Untuk penggunaan right to left
                        float clearButtonEnd = clearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        isClearButtonClicked = false;
                        if (event.getX() < clearButtonEnd){
                            isClearButtonClicked = true;
                        }
                    }else{
                        float clearButtonStart =
                                (getWidth() - getPaddingEnd() - clearButtonImage.getIntrinsicWidth());

                        isClearButtonClicked = false;

                        if (event.getX() > clearButtonStart){
                            isClearButtonClicked = true;
                        }
                    }
                    //Untuk penggunaan left to right


                    if(isClearButtonClicked){
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.ic_clear_opoque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                    else{
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, null, null);
    }

    private void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, clearButtonImage, null);
    }

    private void showClearButtonStart(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                clearButtonImage, null, null, null);
    }
}
