package com.google.appinventor.components.runtime.view;

import android.content.res.ColorStateList;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.util.ViewUtil;
import org.osmdroid.views.MapView;

public class ZoomControlView extends LinearLayout {
    private float density;
    /* access modifiers changed from: private */
    public final MapView parent;
    private final Button zoomIn;
    private final Button zoomOut;

    public ZoomControlView(MapView parent2) {
        super(parent2.getContext());
        this.density = parent2.getContext().getResources().getDisplayMetrics().density;
        this.parent = parent2;
        setOrientation(1);
        Button button = new Button(parent2.getContext());
        this.zoomIn = button;
        Button button2 = new Button(parent2.getContext());
        this.zoomOut = button2;
        initButton(button, "+");
        initButton(button2, "−");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZoomControlView.this.parent.getController().zoomIn();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZoomControlView.this.parent.getController().zoomOut();
            }
        });
        ViewUtil.setBackgroundDrawable(button, getZoomInDrawable(this.density));
        ViewUtil.setBackgroundDrawable(button2, getZoomOutDrawable(this.density));
        int[][] states = {new int[]{-16842910}, new int[]{16842910}};
        int[] colors = {Component.COLOR_LTGRAY, -16777216};
        button.setTextColor(new ColorStateList(states, colors));
        button2.setTextColor(new ColorStateList(states, colors));
        addView(button);
        addView(button2);
        float f = this.density;
        setPadding((int) (f * 12.0f), (int) (f * 12.0f), 0, 0);
        updateButtons();
    }

    public final void updateButtons() {
        this.zoomIn.setEnabled(this.parent.canZoomIn());
        this.zoomOut.setEnabled(this.parent.canZoomOut());
    }

    private void initButton(Button button, String text) {
        button.setText(text);
        button.setTextSize(22.0f);
        button.setPadding(0, 0, 0, 0);
        button.setWidth((int) (this.density * 30.0f));
        button.setHeight((int) (this.density * 30.0f));
        button.setSingleLine();
        button.setGravity(17);
    }

    private static Drawable getZoomInDrawable(float density2) {
        int R = (int) (4.0f * density2);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{(float) R, (float) R, (float) R, (float) R, 0.0f, 0.0f, 0.0f, 0.0f}, (RectF) null, (float[]) null));
        drawable.getPaint().setColor(-1);
        return drawable;
    }

    private static Drawable getZoomOutDrawable(float density2) {
        int R = (int) (4.0f * density2);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) R, (float) R, (float) R, (float) R}, (RectF) null, (float[]) null));
        drawable.getPaint().setColor(-1);
        return drawable;
    }
}
