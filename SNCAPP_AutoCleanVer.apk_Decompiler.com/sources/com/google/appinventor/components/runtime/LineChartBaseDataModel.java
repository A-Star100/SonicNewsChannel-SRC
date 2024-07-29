package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.appinventor.components.common.LineType;
import com.google.appinventor.components.runtime.LineChartViewBase;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LineChartBaseDataModel<V extends LineChartViewBase<V>> extends PointChartDataModel<Entry, ILineDataSet, LineData, LineChart, V> {
    protected LineChartBaseDataModel(LineData data, V view) {
        this(data, view, new LineDataSet(new ArrayList(), ""));
    }

    protected LineChartBaseDataModel(LineData data, V view, ILineDataSet dataset) {
        super(data, view);
        this.dataset = dataset;
        this.data.addDataSet(dataset);
        setDefaultStylingProperties();
    }

    public void addEntryFromTuple(YailList tuple) {
        int index;
        Entry entry = getEntryFromTuple(tuple);
        if (entry != null) {
            int index2 = Collections.binarySearch(this.entries, entry, new EntryXComparator());
            if (index2 < 0) {
                index = (-index2) - 1;
            } else {
                int entryCount = this.entries.size();
                while (index2 < entryCount && ((Entry) this.entries.get(index2)).getX() == entry.getX()) {
                    index2++;
                }
                index = index2;
            }
            this.entries.add(index, entry);
            List<Integer> defaultColors = this.dataset.getCircleColors();
            defaultColors.add(index, Integer.valueOf(this.dataset.getColor()));
            this.dataset.setCircleColors(defaultColors);
        }
    }

    public void setColor(int argb) {
        super.setColor(argb);
        if (this.dataset instanceof LineDataSet) {
            this.dataset.setCircleColor(argb);
        }
    }

    public void setColors(List<Integer> colors) {
        super.setColors(colors);
        if (this.dataset instanceof LineDataSet) {
            this.dataset.setCircleColors(colors);
        }
    }

    /* access modifiers changed from: protected */
    public void setDefaultStylingProperties() {
        if (this.dataset instanceof LineDataSet) {
            this.dataset.setDrawCircleHole(false);
        }
    }

    public void setLineType(LineType type) {
        if (this.dataset instanceof LineDataSet) {
            switch (AnonymousClass1.$SwitchMap$com$google$appinventor$components$common$LineType[type.ordinal()]) {
                case 1:
                    this.dataset.setMode(LineDataSet.Mode.LINEAR);
                    return;
                case 2:
                    this.dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    return;
                case 3:
                    this.dataset.setMode(LineDataSet.Mode.STEPPED);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown line type: " + type);
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.LineChartBaseDataModel$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$LineType;

        static {
            int[] iArr = new int[LineType.values().length];
            $SwitchMap$com$google$appinventor$components$common$LineType = iArr;
            try {
                iArr[LineType.Linear.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$LineType[LineType.Curved.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$LineType[LineType.Stepped.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
