package com.google.appinventor.components.runtime;

import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.BestFitModel;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.LOBFValues;
import com.google.appinventor.components.common.LinearRegression;
import com.google.appinventor.components.common.OptionList;
import com.google.appinventor.components.common.StrokeStyle;
import com.google.appinventor.components.common.TrendlineCalculator;
import com.google.appinventor.components.runtime.util.ExponentialRegression;
import com.google.appinventor.components.runtime.util.HasTrendline;
import com.google.appinventor.components.runtime.util.LogarithmicRegression;
import com.google.appinventor.components.runtime.util.QuadraticRegression;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DesignerComponent(category = ComponentCategory.CHARTS, description = "A component that predicts a best fit model for a given data series.", iconName = "images/trendline.png", version = 1)
@UsesLibraries({"commons-math3.jar"})
public class Trendline implements ChartComponent, DataSourceChangeListener {
    private static final boolean DEBUG = false;
    private static final YailDictionary.KeyTransformer ENUM_KEY_TRANSFORMER = new YailDictionary.KeyTransformer() {
        public Object transform(Object key) {
            if (key instanceof OptionList) {
                return ((OptionList) key).toUnderlyingValue();
            }
            return key;
        }
    };
    private static final String LOG_TAG = Trendline.class.getSimpleName();
    /* access modifiers changed from: private */
    public ChartData2D chartData = null;
    private int color = 0;
    /* access modifiers changed from: private */
    public final Chart container;
    private TrendlineCalculator currentModel;
    private final DashPathEffect dashed;
    private DataModel<?> dataModel;
    private final float density;
    private final DashPathEffect dotted;
    private final ExponentialRegression exponentialRegression;
    private boolean extend = true;
    private boolean initialized;
    private Map<String, Object> lastResults;
    private final LogarithmicRegression logarithmicRegression;
    private double maxX;
    private double minX;
    private BestFitModel model = BestFitModel.Linear;
    private final QuadraticRegression quadraticRegression;
    private final LinearRegression regression;
    private StrokeStyle strokeStyle = StrokeStyle.Solid;
    /* access modifiers changed from: private */
    public double strokeWidth = 1.0d;
    /* access modifiers changed from: private */
    public boolean visible = true;

    public Trendline(Chart chartContainer) {
        LinearRegression linearRegression = new LinearRegression();
        this.regression = linearRegression;
        this.quadraticRegression = new QuadraticRegression();
        this.exponentialRegression = new ExponentialRegression();
        this.logarithmicRegression = new LogarithmicRegression();
        this.currentModel = linearRegression;
        this.lastResults = new HashMap();
        this.initialized = false;
        this.dataModel = null;
        this.minX = Double.POSITIVE_INFINITY;
        this.maxX = Double.NEGATIVE_INFINITY;
        float deviceDensity = chartContainer.$form().deviceDensity();
        this.density = deviceDensity;
        this.container = chartContainer;
        chartContainer.addDataComponent(this);
        this.dashed = new DashPathEffect(new float[]{deviceDensity * 10.0f, deviceDensity * 10.0f}, 0.0f);
        this.dotted = new DashPathEffect(new float[]{2.0f * deviceDensity, deviceDensity * 10.0f}, 0.0f);
    }

    public void Initialize() {
        this.initialized = true;
        if (this.dataModel == null) {
            initChartData();
        }
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.container.getDispatchDelegate();
    }

    public void onDataSourceValueChange(DataSource<?, ?> component, String key, Object newValue) {
        this.lastResults.clear();
        Object dataValue = component.getDataValue(null);
        if (dataValue instanceof List) {
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            this.minX = Double.POSITIVE_INFINITY;
            this.maxX = Double.NEGATIVE_INFINITY;
            for (Object next : (List) dataValue) {
                if (next instanceof Entry) {
                    Entry entry = (Entry) next;
                    double currentX = (double) entry.getX();
                    if (currentX < this.minX) {
                        this.minX = currentX;
                    }
                    if (currentX > this.maxX) {
                        this.maxX = currentX;
                    }
                    x.add(Double.valueOf(currentX));
                    y.add(Double.valueOf((double) entry.getY()));
                }
            }
            if (x.isEmpty()) {
                Log.w(LOG_TAG, "No entries in the data source");
            } else if (x.size() < 2) {
                Log.w(LOG_TAG, "Not enough entries in the data source");
            } else if (x.size() != y.size()) {
                Log.w(LOG_TAG, "Must have equal X and Y data points");
            } else {
                this.lastResults = this.currentModel.compute(x, y);
                if (this.initialized) {
                    final YailDictionary results = new YailDictionary(this.lastResults, ENUM_KEY_TRANSFORMER);
                    this.container.$form().runOnUiThread(new Runnable() {
                        public void run() {
                            Trendline.this.Updated(results);
                            if (Trendline.this.visible) {
                                Trendline.this.container.getChartView().getView().invalidate();
                            }
                        }
                    });
                }
            }
        }
    }

    public void onReceiveValue(RealTimeDataSource<?, ?> realTimeDataSource, String key, Object value) {
    }

    @DesignerProperty(editorType = "component:com.google.appinventor.component.runtime.ChartData2D")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The data series for which to compute the line of best fit.")
    public void ChartData(ChartData2D chartData2) {
        ChartData2D chartData2D = this.chartData;
        if (chartData2D != null) {
            chartData2D.removeDataSourceChangeListener(this);
        }
        this.chartData = chartData2;
        if (chartData2 != null) {
            chartData2.addDataSourceChangeListener(this);
        }
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color of the line of best fit.")
    public void Color(int color2) {
        this.color = color2;
        if (this.initialized) {
            this.container.refresh();
        }
    }

    @SimpleProperty
    public int Color() {
        return this.color;
    }

    @SimpleProperty
    public double CorrelationCoefficient() {
        return resultOrNan((Double) this.lastResults.get("correlation coefficient"));
    }

    @SimpleProperty
    public double ExponentialBase() {
        return resultOrNan((Double) this.lastResults.get("b"));
    }

    @SimpleProperty
    public double ExponentialCoefficient() {
        return resultOrNan((Double) this.lastResults.get("a"));
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether to extend the line of best fit beyond the data.")
    public void Extend(boolean extend2) {
        this.extend = extend2;
        if (this.initialized) {
            this.container.refresh();
        }
    }

    @SimpleProperty
    public boolean Extend() {
        return this.extend;
    }

    @SimpleProperty
    public double LinearCoefficient() {
        return resultOrNan((Double) this.lastResults.get("slope"));
    }

    @SimpleProperty
    public double LogarithmCoefficient() {
        return resultOrNan((Double) this.lastResults.get("b"));
    }

    @SimpleProperty
    public double LogarithmConstant() {
        return resultOrNan((Double) this.lastResults.get("a"));
    }

    @DesignerProperty(defaultValue = "Linear", editorType = "best_fit_model")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The model to use for the line of best fit.")
    public void Model(BestFitModel model2) {
        this.model = model2;
        switch (AnonymousClass3.$SwitchMap$com$google$appinventor$components$common$BestFitModel[model2.ordinal()]) {
            case 1:
                this.currentModel = this.regression;
                break;
            case 2:
                this.currentModel = this.quadraticRegression;
                break;
            case 3:
                this.currentModel = this.exponentialRegression;
                break;
            case 4:
                this.currentModel = this.logarithmicRegression;
                break;
            default:
                throw new IllegalArgumentException("Unknown model: " + model2);
        }
        if (this.initialized) {
            this.container.refresh();
        }
    }

    @SimpleProperty
    public BestFitModel Model() {
        return this.model;
    }

    @SimpleProperty
    public List<Double> Predictions() {
        Object value = this.lastResults.get("predictions");
        if (value instanceof List) {
            return (List) value;
        }
        return new ArrayList();
    }

    @SimpleProperty
    public double QuadraticCoefficient() {
        return resultOrZero((Double) this.lastResults.get("x^2"));
    }

    @SimpleProperty
    public YailDictionary Results() {
        return new YailDictionary(this.lastResults, ENUM_KEY_TRANSFORMER);
    }

    @SimpleProperty
    public double RSquared() {
        return resultOrNan((Double) this.lastResults.get("r^2"));
    }

    @DesignerProperty(defaultValue = "1", editorType = "stroke_style")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The style of the best fit line.")
    public void StrokeStyle(StrokeStyle strokeStyle2) {
        this.strokeStyle = strokeStyle2;
        if (this.initialized) {
            this.container.refresh();
        }
    }

    public void StrokeStyle(int value) {
        StrokeStyle strokeStyle2 = StrokeStyle.fromUnderlyingValue(Integer.valueOf(value));
        if (strokeStyle2 != null) {
            StrokeStyle(strokeStyle2);
        }
    }

    public void StrokeStyle(String value) {
        try {
            StrokeStyle(Integer.parseInt(value));
        } catch (NumberFormatException e) {
        }
    }

    @SimpleProperty
    public StrokeStyle StrokeStyle() {
        return this.strokeStyle;
    }

    @DesignerProperty(defaultValue = "1.0", editorType = "non_negative_float")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The width of the best fit line.")
    public void StrokeWidth(double strokeWidth2) {
        this.strokeWidth = strokeWidth2;
        if (this.initialized) {
            this.container.refresh();
        }
    }

    @SimpleProperty
    public double StrokeWidth() {
        return this.strokeWidth;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Visible(boolean visible2) {
        this.visible = visible2;
        if (this.initialized) {
            this.container.refresh();
        }
    }

    @SimpleProperty
    public boolean Visible() {
        return this.visible;
    }

    @SimpleProperty
    public Object XIntercepts() {
        Object result = this.lastResults.get("Xintercepts");
        return result == null ? Double.valueOf(Double.NaN) : result;
    }

    @SimpleProperty
    public double YIntercept() {
        if (this.lastResults.containsKey("Yintercept")) {
            return ((Double) this.lastResults.get("Yintercept")).doubleValue();
        }
        if (this.lastResults.containsKey("intercept")) {
            return ((Double) this.lastResults.get("intercept")).doubleValue();
        }
        return Double.NaN;
    }

    @SimpleFunction
    public void DisconnectFromChartData() {
        ChartData2D chartData2D = this.chartData;
        if (chartData2D != null) {
            chartData2D.removeDataSourceChangeListener(this);
        }
        this.lastResults.clear();
        this.container.refresh();
    }

    @SimpleFunction
    public Object GetResultValue(@Options(LOBFValues.class) String value) {
        if (this.lastResults.containsKey(value)) {
            return this.lastResults.get(value);
        }
        return Double.valueOf(Double.NaN);
    }

    @SimpleEvent
    public void Updated(YailDictionary results) {
        EventDispatcher.dispatchEvent(this, "Updated", results);
    }

    public void initChartData() {
        Log.d(LOG_TAG, "initChartData view is " + this.container.getChartView());
        if (this.container.getChartView() instanceof ScatterChartView) {
            this.dataModel = new ScatterChartBestFitModel();
        } else if (this.container.getChartView() instanceof PointChartView) {
            this.dataModel = new LineChartBestFitModel();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Trendline$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$BestFitModel;
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$StrokeStyle;

        static {
            int[] iArr = new int[StrokeStyle.values().length];
            $SwitchMap$com$google$appinventor$components$common$StrokeStyle = iArr;
            try {
                iArr[StrokeStyle.Dashed.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$StrokeStyle[StrokeStyle.Dotted.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$StrokeStyle[StrokeStyle.Solid.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[BestFitModel.values().length];
            $SwitchMap$com$google$appinventor$components$common$BestFitModel = iArr2;
            try {
                iArr2[BestFitModel.Linear.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$BestFitModel[BestFitModel.Quadratic.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$BestFitModel[BestFitModel.Exponential.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$BestFitModel[BestFitModel.Logarithmic.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: private */
    public DashPathEffect getDashPathEffect() {
        switch (AnonymousClass3.$SwitchMap$com$google$appinventor$components$common$StrokeStyle[this.strokeStyle.ordinal()]) {
            case 1:
                return this.dashed;
            case 2:
                return this.dotted;
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public float[] getPoints(float xMin, float xMax, int viewWidth) {
        int strokeStep;
        if (!this.initialized || this.chartData == null) {
            return new float[0];
        }
        if (!this.extend) {
            xMin = Math.max(xMin, (float) this.minX);
            xMax = Math.min(xMax, (float) this.maxX);
        }
        switch (AnonymousClass3.$SwitchMap$com$google$appinventor$components$common$StrokeStyle[this.strokeStyle.ordinal()]) {
            case 1:
                strokeStep = 20;
                break;
            case 2:
                strokeStep = 12;
                break;
            default:
                strokeStep = 1;
                break;
        }
        return this.currentModel.computePoints(this.lastResults, xMin, xMax, viewWidth, (int) Math.ceil((double) (((float) viewWidth) / (this.density * ((float) strokeStep)))));
    }

    /* access modifiers changed from: private */
    public int getColor() {
        ChartData2D chartData2D;
        int color2 = this.color;
        if (color2 != 0 || (chartData2D = this.chartData) == null) {
            return color2;
        }
        int color3 = chartData2D.Color();
        return (16777215 & color3) | ((((color3 >> 24) & 255) / 2) << 24);
    }

    private static double resultOrNan(Double value) {
        if (value == null) {
            return Double.NaN;
        }
        return value.doubleValue();
    }

    private static double resultOrZero(Double value) {
        if (value == null) {
            return 0.0d;
        }
        return value.doubleValue();
    }

    private class ScatterChartBestFitDataSet extends ScatterDataSet implements HasTrendline<Entry> {
        public ScatterChartBestFitDataSet() {
            super(new ArrayList(), "Best Fit");
        }

        public float[] getPoints(float xMin, float xMax, int viewWidth) {
            return Trendline.this.getPoints(xMin, xMax, viewWidth);
        }

        public int getColor() {
            return Trendline.this.getColor();
        }

        public DashPathEffect getDashPathEffect() {
            return Trendline.this.getDashPathEffect();
        }

        public float getLineWidth() {
            return ((float) Trendline.this.strokeWidth) * Trendline.this.container.$form().deviceDensity();
        }

        public boolean isVisible() {
            return Trendline.this.visible;
        }

        public String toString() {
            if (Trendline.this.chartData != null) {
                return ((ChartDataModel) Trendline.this.chartData.dataModel).dataset.toString();
            }
            return Trendline.super.toString();
        }
    }

    private class ScatterChartBestFitModel extends ScatterChartDataModel {
        public ScatterChartBestFitModel() {
            super((ScatterData) Trendline.this.container.getChartView().data, (ScatterChartView) Trendline.this.container.getChartView(), new ScatterChartBestFitDataSet());
        }
    }

    private class LineChartBestFitDataSet extends LineDataSet implements HasTrendline<Entry> {
        public LineChartBestFitDataSet() {
            super(new ArrayList(), "Best Fit");
        }

        public float[] getPoints(float xMin, float xMax, int viewWidth) {
            return Trendline.this.getPoints(xMin, xMax, viewWidth);
        }

        public int getColor() {
            return Trendline.this.getColor();
        }

        public DashPathEffect getDashPathEffect() {
            return Trendline.this.getDashPathEffect();
        }

        public float getLineWidth() {
            return ((float) Trendline.this.strokeWidth) * Trendline.this.container.$form().deviceDensity();
        }

        public boolean isVisible() {
            return Trendline.this.visible;
        }

        public String toString() {
            if (Trendline.this.chartData != null) {
                return ((ChartDataModel) Trendline.this.chartData.dataModel).dataset.toString();
            }
            return Trendline.super.toString();
        }
    }

    private class LineChartBestFitModel extends LineChartDataModel {
        public LineChartBestFitModel() {
            super((LineData) Trendline.this.container.getChartView().data, (LineChartView) Trendline.this.container.getChartView(), new LineChartBestFitDataSet());
        }
    }
}
