package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ChartType;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.CHARTS, description = "A component that allows visualizing data", version = 3)
@UsesLibraries(libraries = "mpandroidchart.jar")
public class Chart extends AndroidViewComponent implements ComponentContainer, OnInitializeListener {
    private int backgroundColor;
    private ChartView<?, ?, ?, ?, ?> chartView;
    private final List<ChartComponent> dataComponents;
    private String description;
    private boolean gridEnabled;
    private YailList labels;
    private boolean legendEnabled;
    private int pieRadius;
    private int tick = 1;
    private ChartType type;
    private final RelativeLayout view;
    private boolean zeroX;
    private boolean zeroY;

    public Chart(ComponentContainer container) {
        super(container);
        this.view = new RelativeLayout(container.$context());
        container.$add(this);
        this.dataComponents = new ArrayList();
        Type(ChartType.Line);
        Width(176);
        Height(144);
        BackgroundColor(0);
        Description("");
        PieRadius(100);
        LegendEnabled(true);
        GridEnabled(true);
        Labels(new YailList());
        XFromZero(false);
        YFromZero(false);
        $form().registerForOnInitialize(this);
    }

    public View getView() {
        return this.view;
    }

    public Activity $context() {
        return this.container.$context();
    }

    public Form $form() {
        return this.container.$form();
    }

    public void $add(AndroidViewComponent component) {
        throw new UnsupportedOperationException("ChartBase.$add() called");
    }

    public void setChildWidth(AndroidViewComponent component, int width) {
        throw new UnsupportedOperationException("ChartBase.setChildWidth called");
    }

    public void setChildHeight(AndroidViewComponent component, int height) {
        throw new UnsupportedOperationException("ChartBase.setChildHeight called");
    }

    public List<Component> getChildren() {
        return new ArrayList(this.dataComponents);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Specifies the chart's type (area, bar, pie, scatter), which determines how to visualize the data.")
    public ChartType Type() {
        return this.type;
    }

    @DesignerProperty(defaultValue = "0", editorType = "chart_type")
    public void Type(ChartType type2) {
        boolean chartViewExists = this.chartView != null;
        ChartView<?, ?, ?, ?, ?> newChartView = createChartViewFromType(type2);
        if (chartViewExists) {
            this.view.removeView(this.chartView.getView());
        }
        this.type = type2;
        this.chartView = newChartView;
        this.view.addView(newChartView.getView(), 0);
        if (chartViewExists) {
            reinitializeChart();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Chart$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$ChartType;

        static {
            int[] iArr = new int[ChartType.values().length];
            $SwitchMap$com$google$appinventor$components$common$ChartType = iArr;
            try {
                iArr[ChartType.Line.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$ChartType[ChartType.Scatter.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$ChartType[ChartType.Area.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$ChartType[ChartType.Bar.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$ChartType[ChartType.Pie.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private ChartView<?, ?, ?, ?, ?> createChartViewFromType(ChartType type2) {
        switch (AnonymousClass1.$SwitchMap$com$google$appinventor$components$common$ChartType[type2.ordinal()]) {
            case 1:
                return new LineChartView(this);
            case 2:
                return new ScatterChartView(this);
            case 3:
                return new AreaChartView(this);
            case 4:
                return new BarChartView(this);
            case 5:
                return new PieChartView(this);
            default:
                throw new IllegalArgumentException("Invalid Chart type specified: " + type2);
        }
    }

    private void reinitializeChart() {
        for (ChartComponent dataComponent : this.dataComponents) {
            dataComponent.initChartData();
        }
        Description(this.description);
        BackgroundColor(this.backgroundColor);
        LegendEnabled(this.legendEnabled);
        GridEnabled(this.gridEnabled);
        Labels(this.labels);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Description() {
        return this.description;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void Description(String text) {
        this.description = text;
        this.chartView.setDescription(text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty
    public void BackgroundColor(int argb) {
        if (argb == 0) {
            argb = $form().isDarkTheme() ? -16777216 : -1;
        }
        this.backgroundColor = argb;
        this.chartView.setBackgroundColor(argb);
    }

    @DesignerProperty(defaultValue = "100", editorType = "chart_pie_radius")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Sets the Pie Radius of a Pie Chart from 0% to 100%, where the percentage indicates the percentage of the hole fill. 100% means that a full Pie Chart is drawn, while values closer to 0% correspond to hollow Pie Charts.", userVisible = false)
    public void PieRadius(int percent) {
        this.pieRadius = percent;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof PieChartView) {
            ((PieChartView) chartView2).setPieRadius(percent);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean LegendEnabled() {
        return this.legendEnabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void LegendEnabled(boolean enabled) {
        this.legendEnabled = enabled;
        this.chartView.setLegendEnabled(enabled);
        this.view.invalidate();
        this.chartView.refresh();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean GridEnabled() {
        return this.gridEnabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void GridEnabled(boolean enabled) {
        this.gridEnabled = enabled;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).setGridEnabled(enabled);
            this.view.invalidate();
            this.chartView.refresh();
        }
    }

    @SimpleProperty
    public YailList Labels() {
        return this.labels;
    }

    @SimpleProperty(description = "Changes the Chart's X axis labels to the specified List of Strings,  provided that the Chart Type is set to a Chart with an Axis (applies to Area, Bar, Line, Scatter Charts). The labels are applied in order, starting from the smallest x value on the Chart, and continuing in order. If a label is not specified for an x value, a default value is used (the x value of the axis tick at that location).")
    public void Labels(YailList labels2) {
        this.labels = labels2;
        if (this.chartView instanceof AxisChartView) {
            List<String> stringLabels = new ArrayList<>();
            for (int i = 0; i < labels2.size(); i++) {
                stringLabels.add(labels2.getString(i));
            }
            ((AxisChartView) this.chartView).setLabels(stringLabels);
        }
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public void LabelsFromString(String labels2) {
        Labels(ElementsUtil.elementsFromString(labels2));
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void XFromZero(boolean zero) {
        this.zeroX = zero;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).setXMinimum(zero);
        }
    }

    @SimpleProperty
    public boolean XFromZero() {
        return this.zeroX;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void YFromZero(boolean zero) {
        this.zeroY = zero;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).setYMinimum(zero);
        }
    }

    @SimpleProperty
    public boolean YFromZero() {
        return this.zeroY;
    }

    @SimpleFunction
    public void ExtendDomainToInclude(double x) {
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            double[] bounds = ((AxisChartView) chartView2).getXBounds();
            if (x < bounds[0]) {
                ((AxisChartView) this.chartView).setXBounds(x, bounds[1]);
            } else if (x > bounds[1]) {
                ((AxisChartView) this.chartView).setXBounds(bounds[0], x);
            } else {
                return;
            }
            this.chartView.refresh();
        }
    }

    @SimpleFunction
    public void ExtendRangeToInclude(double y) {
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            double[] bounds = ((AxisChartView) chartView2).getYBounds();
            if (y < bounds[0]) {
                ((AxisChartView) this.chartView).setYBounds(y, bounds[1]);
            } else if (y > bounds[1]) {
                ((AxisChartView) this.chartView).setYBounds(bounds[0], y);
            } else {
                return;
            }
            this.chartView.refresh();
        }
    }

    @SimpleFunction
    public void ResetAxes() {
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).resetAxes();
            refresh();
        }
    }

    @SimpleFunction
    public void SetDomain(double minimum, double maximum) {
        this.zeroX = minimum == 0.0d;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).setXBounds(minimum, maximum);
            refresh();
        }
    }

    @SimpleFunction
    public void SetRange(double minimum, double maximum) {
        this.zeroY = minimum == 0.0d;
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof AxisChartView) {
            ((AxisChartView) chartView2).setYBounds(minimum, maximum);
            refresh();
        }
    }

    @SimpleEvent
    public void EntryClick(Component series, Object x, double y) {
        EventDispatcher.dispatchEvent(this, "EntryClick", series, x, Double.valueOf(y));
    }

    public ChartDataModel<?, ?, ?, ?, ?> createChartModel() {
        return this.chartView.createChartModel();
    }

    public void refresh() {
        this.chartView.refresh();
    }

    public ChartView<?, ?, ?, ?, ?> getChartView() {
        return this.chartView;
    }

    public void addDataComponent(ChartComponent dataComponent) {
        this.dataComponents.add(dataComponent);
    }

    public void onInitialize() {
        ChartView<?, ?, ?, ?, ?> chartView2 = this.chartView;
        if (chartView2 instanceof PieChartView) {
            ((PieChartView) chartView2).setPieRadius(this.pieRadius);
            this.chartView.refresh();
        }
    }

    public int getSyncedTValue(int dataSeriesT) {
        int returnValue;
        if (this.tick - dataSeriesT > 1) {
            returnValue = this.tick;
        } else {
            returnValue = dataSeriesT;
        }
        this.tick = returnValue + 1;
        return returnValue;
    }
}
