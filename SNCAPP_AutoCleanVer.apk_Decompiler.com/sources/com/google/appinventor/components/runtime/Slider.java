package com.google.appinventor.components.runtime;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.SdkLevel;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "A Slider is a progress bar that adds a draggable thumb. You can touch the thumb and drag left or right to set the slider thumb position. As the Slider thumb is dragged, it will trigger the PositionChanged event, reporting the position of the Slider thumb. The reported position of the Slider thumb can be used to dynamically update another component attribute, such as the font size of a TextBox or the radius of a Ball.", iconName = "images/slider.png", version = 2)
public class Slider extends AndroidViewComponent implements SeekBar.OnSeekBarChangeListener {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "Slider";
    private static final int initialLeftColor = -14336;
    private static final String initialLeftColorString = "&HFFFFC800";
    private static final int initialRightColor = -7829368;
    private static final String initialRightColorString = "&HFF888888";
    private int leftColor;
    private float maxValue;
    private float minValue;
    public final boolean referenceGetThumb;
    private int rightColor;
    /* access modifiers changed from: private */
    public final SeekBar seekbar;
    /* access modifiers changed from: private */
    public boolean thumbEnabled;
    private float thumbPosition;

    private class SeekBarHelper {
        private SeekBarHelper() {
        }

        public void getThumb(int alpha) {
            Slider.this.seekbar.getThumb().mutate().setAlpha(alpha);
        }
    }

    public Slider(ComponentContainer container) {
        super(container);
        this.referenceGetThumb = SdkLevel.getLevel() >= 16;
        SeekBar seekBar = new SeekBar(container.$context());
        this.seekbar = seekBar;
        if (SdkLevel.getLevel() >= 21) {
            seekBar.setSplitTrack(false);
        }
        this.leftColor = -14336;
        this.rightColor = -7829368;
        setSliderColors();
        container.$add(this);
        this.minValue = 10.0f;
        this.maxValue = 50.0f;
        this.thumbPosition = 30.0f;
        this.thumbEnabled = true;
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(100);
        setSeekbarPosition();
    }

    private void setSliderColors() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.seekbar.setProgressTintList(ColorStateList.valueOf(this.leftColor));
            if (Build.VERSION.SDK_INT >= 22 || !(this.seekbar.getProgressDrawable() instanceof StateListDrawable)) {
                this.seekbar.setProgressBackgroundTintList(ColorStateList.valueOf(this.rightColor));
                this.seekbar.setProgressBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                return;
            }
            StateListDrawable drawable = (StateListDrawable) this.seekbar.getProgressDrawable();
            if (drawable.getCurrent() instanceof LayerDrawable) {
                Drawable background = ((LayerDrawable) drawable.getCurrent()).findDrawableByLayerId(16908288);
                background.setTintList(ColorStateList.valueOf(this.rightColor));
                background.setTintMode(PorterDuff.Mode.MULTIPLY);
                return;
            }
            return;
        }
        LayerDrawable fullBar = (LayerDrawable) this.seekbar.getProgressDrawable();
        fullBar.setColorFilter(this.rightColor, PorterDuff.Mode.SRC);
        fullBar.findDrawableByLayerId(16908301).setColorFilter(this.leftColor, PorterDuff.Mode.SRC);
    }

    private void setSeekbarPosition() {
        float f = this.thumbPosition;
        float f2 = this.minValue;
        this.seekbar.setProgress((int) (((f - f2) / (this.maxValue - f2)) * 100.0f));
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(description = "Sets whether or not to display the slider thumb.", userVisible = true)
    public void ThumbEnabled(boolean enabled) {
        this.thumbEnabled = enabled;
        int alpha = enabled ? 255 : 0;
        if (this.referenceGetThumb) {
            new SeekBarHelper().getThumb(alpha);
        }
        this.seekbar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return !Slider.this.thumbEnabled;
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns whether or not the slider thumb is being be shown", userVisible = true)
    public boolean ThumbEnabled() {
        return this.thumbEnabled;
    }

    @DesignerProperty(defaultValue = "30.0", editorType = "float")
    @SimpleProperty(description = "Sets the position of the slider thumb. If this value is greater than MaxValue, then it will be set to same value as MaxValue. If this value is less than MinValue, then it will be set to same value as MinValue.", userVisible = true)
    public void ThumbPosition(float position) {
        this.thumbPosition = Math.max(Math.min(position, this.maxValue), this.minValue);
        setSeekbarPosition();
        PositionChanged(this.thumbPosition);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the position of slider thumb", userVisible = true)
    public float ThumbPosition() {
        return this.thumbPosition;
    }

    @DesignerProperty(defaultValue = "10.0", editorType = "float")
    @SimpleProperty(description = "Sets the minimum value of slider.  Changing the minimum value also resets Thumbposition to be halfway between the (new) minimum and the maximum. If the new minimum is greater than the current maximum, then minimum and maximum will both be set to this value.  Setting MinValue resets the thumb position to halfway between MinValue and MaxValue and signals the PositionChanged event.", userVisible = true)
    public void MinValue(float value) {
        this.minValue = value;
        float max = Math.max(value, this.maxValue);
        this.maxValue = max;
        ThumbPosition((this.minValue + max) / 2.0f);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the value of slider min value.", userVisible = true)
    public float MinValue() {
        return this.minValue;
    }

    @DesignerProperty(defaultValue = "50.0", editorType = "float")
    @SimpleProperty(description = "Sets the maximum value of slider.  Changing the maximum value also resets Thumbposition to be halfway between the minimum and the (new) maximum. If the new maximum is less than the current minimum, then minimum and maximum will both be set to this value.  Setting MaxValue resets the thumb position to halfway between MinValue and MaxValue and signals the PositionChanged event.", userVisible = true)
    public void MaxValue(float value) {
        this.maxValue = value;
        float min = Math.min(value, this.minValue);
        this.minValue = min;
        ThumbPosition((min + this.maxValue) / 2.0f);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the slider max value.", userVisible = true)
    public float MaxValue() {
        return this.maxValue;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color of slider to the left of the thumb.")
    public int ColorLeft() {
        return this.leftColor;
    }

    @DesignerProperty(defaultValue = "&HFFFFC800", editorType = "color")
    @SimpleProperty
    public void ColorLeft(int argb) {
        this.leftColor = argb;
        setSliderColors();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color of slider to the right of the thumb.")
    public int ColorRight() {
        return this.rightColor;
    }

    @DesignerProperty(defaultValue = "&HFF888888", editorType = "color")
    @SimpleProperty
    public void ColorRight(int argb) {
        this.rightColor = argb;
        setSliderColors();
    }

    public View getView() {
        return this.seekbar;
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float f = this.maxValue;
        float f2 = this.minValue;
        float f3 = (((f - f2) * ((float) progress)) / 100.0f) + f2;
        this.thumbPosition = f3;
        PositionChanged(f3);
    }

    @SimpleEvent
    public void PositionChanged(float thumbPosition2) {
        EventDispatcher.dispatchEvent(this, "PositionChanged", Float.valueOf(thumbPosition2));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public int Height() {
        return getView().getHeight();
    }

    public void Height(int height) {
        this.container.setChildHeight(this, height);
    }
}
