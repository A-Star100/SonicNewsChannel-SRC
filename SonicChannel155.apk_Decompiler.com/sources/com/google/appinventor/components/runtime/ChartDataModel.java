package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.google.appinventor.components.runtime.ChartView;
import com.google.appinventor.components.runtime.DataModel;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ChartDataModel<E extends Entry, T extends IDataSet<E>, D extends ChartData<T>, C extends Chart<D>, V extends ChartView<E, T, D, C, V>> extends DataModel<E> {
    protected D data;
    protected T dataset;
    protected V view;

    /* access modifiers changed from: protected */
    public abstract int getTupleSize();

    protected ChartDataModel(D data2, V view2) {
        this.data = data2;
        this.view = view2;
        this.entries = new ArrayList();
    }

    public T getDataset() {
        return this.dataset;
    }

    public D getData() {
        return this.data;
    }

    public void setColor(int argb) {
        T t = this.dataset;
        if (t instanceof DataSet) {
            ((DataSet) t).setColor(argb);
        }
    }

    public void setColors(List<Integer> colors) {
        T t = this.dataset;
        if (t instanceof DataSet) {
            ((DataSet) t).setColors(colors);
        }
    }

    public void setLabel(String text) {
        getDataset().setLabel(text);
    }

    public void setElements(String elements) {
        int tupleSize = getTupleSize();
        String[] entries = elements.split(",");
        for (int i = tupleSize - 1; i < entries.length; i += tupleSize) {
            List<String> tupleEntries = new ArrayList<>();
            for (int j = tupleSize - 1; j >= 0; j--) {
                tupleEntries.add(entries[i - j]);
            }
            addEntryFromTuple(YailList.makeList((List) tupleEntries));
        }
    }

    public void removeEntryFromTuple(YailList tuple) {
        Entry entry = getEntryFromTuple(tuple);
        if (entry != null) {
            removeEntry(findEntryIndex(entry));
        }
    }

    public void removeEntry(int index) {
        if (index >= 0) {
            this.entries.remove(index);
        }
    }

    public boolean doesEntryExist(YailList tuple) {
        return findEntryIndex(getEntryFromTuple(tuple)) >= 0;
    }

    public YailList findEntriesByCriterion(String value, DataModel.EntryCriterion criterion) {
        List<YailList> entries = new ArrayList<>();
        for (Entry entry : this.entries) {
            if (isEntryCriterionSatisfied(entry, criterion, value)) {
                entries.add(getTupleFromEntry(entry));
            }
        }
        return YailList.makeList((List) entries);
    }

    public YailList getEntriesAsTuples() {
        return findEntriesByCriterion(Component.TYPEFACE_DEFAULT, DataModel.EntryCriterion.All);
    }

    /* access modifiers changed from: protected */
    public boolean isEntryCriterionSatisfied(Entry entry, DataModel.EntryCriterion criterion, String value) {
        boolean criterionSatisfied = true;
        switch (AnonymousClass1.$SwitchMap$com$google$appinventor$components$runtime$DataModel$EntryCriterion[criterion.ordinal()]) {
            case 1:
                return true;
            case 2:
                if (entry instanceof PieEntry) {
                    return ((PieEntry) entry).getLabel().equals(value);
                }
                try {
                    float xValue = Float.parseFloat(value);
                    float compareValue = entry.getX();
                    if (entry instanceof BarEntry) {
                        compareValue = (float) Math.floor((double) compareValue);
                    }
                    if (compareValue != xValue) {
                        criterionSatisfied = false;
                    }
                    return criterionSatisfied;
                } catch (NumberFormatException e) {
                    return false;
                }
            case 3:
                try {
                    if (entry.getY() != Float.parseFloat(value)) {
                        criterionSatisfied = false;
                    }
                    return criterionSatisfied;
                } catch (NumberFormatException e2) {
                    return false;
                }
            default:
                throw new IllegalArgumentException("Unknown criterion: " + criterion);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.ChartDataModel$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$runtime$DataModel$EntryCriterion;

        static {
            int[] iArr = new int[DataModel.EntryCriterion.values().length];
            $SwitchMap$com$google$appinventor$components$runtime$DataModel$EntryCriterion = iArr;
            try {
                iArr[DataModel.EntryCriterion.All.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$runtime$DataModel$EntryCriterion[DataModel.EntryCriterion.XValue.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$runtime$DataModel$EntryCriterion[DataModel.EntryCriterion.YValue.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public int findEntryIndex(Entry entry) {
        for (int i = 0; i < this.entries.size(); i++) {
            if (areEntriesEqual((Entry) this.entries.get(i), entry)) {
                return i;
            }
        }
        return -1;
    }

    public void clearEntries() {
        this.entries.clear();
    }

    public void addTimeEntry(YailList tuple) {
        if (this.entries.size() >= this.maximumTimeEntries) {
            this.entries.remove(0);
        }
        addEntryFromTuple(tuple);
    }

    public void setMaximumTimeEntries(int entries) {
        this.maximumTimeEntries = entries;
    }

    /* access modifiers changed from: protected */
    public void setDefaultStylingProperties() {
    }

    /* access modifiers changed from: protected */
    public String getDefaultValue(int index) {
        return new StringBuilder().append(index).toString();
    }

    /* access modifiers changed from: protected */
    public boolean areEntriesEqual(Entry e1, Entry e2) {
        return e1.equalTo(e2);
    }

    public List<E> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }
}
