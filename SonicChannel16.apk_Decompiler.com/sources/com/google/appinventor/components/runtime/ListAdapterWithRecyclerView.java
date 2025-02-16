package com.google.appinventor.components.runtime;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAdapterWithRecyclerView extends RecyclerView.Adapter<RvViewHolder> implements Filterable {
    private static final String LOG_TAG = "ListAdapterRecyclerView";
    private int backgroundColor;
    /* access modifiers changed from: private */
    public ClickListener clickListener;
    protected final ComponentContainer container;
    protected final Filter filter = new Filter() {
        /* access modifiers changed from: protected */
        public Filter.FilterResults performFiltering(CharSequence charSequence) {
            String filterQuery = charSequence.toString().toLowerCase();
            Filter.FilterResults results = new Filter.FilterResults();
            List<YailDictionary> filteredList = new ArrayList<>();
            if (filterQuery == null || filterQuery.length() == 0) {
                filteredList = new ArrayList<>(ListAdapterWithRecyclerView.this.items);
            } else {
                for (YailDictionary itemDict : ListAdapterWithRecyclerView.this.items) {
                    Object o = itemDict.get(Component.LISTVIEW_KEY_DESCRIPTION);
                    String filterString = itemDict.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString();
                    if (o != null) {
                        filterString = filterString + " " + o.toString().toLowerCase();
                    }
                    if (filterString.toLowerCase().contains(filterQuery)) {
                        filteredList.add(itemDict);
                    }
                }
            }
            results.count = filteredList.size();
            results.values = filteredList;
            return results;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ListAdapterWithRecyclerView.this.filterItems = (List) filterResults.values;
            for (int i = 0; i < ListAdapterWithRecyclerView.this.items.size(); i++) {
                if (ListAdapterWithRecyclerView.this.filterItems.size() <= 0 || !ListAdapterWithRecyclerView.this.filterItems.contains(ListAdapterWithRecyclerView.this.items.get(i))) {
                    ListAdapterWithRecyclerView.this.isVisible[i] = false;
                    if (ListAdapterWithRecyclerView.this.itemViews[i] != null) {
                        ListAdapterWithRecyclerView.this.itemViews[i].setVisibility(8);
                        ListAdapterWithRecyclerView.this.itemViews[i].getLayoutParams().height = 0;
                    }
                } else {
                    ListAdapterWithRecyclerView.this.isVisible[i] = true;
                    if (ListAdapterWithRecyclerView.this.itemViews[i] != null) {
                        ListAdapterWithRecyclerView.this.itemViews[i].setVisibility(0);
                        ListAdapterWithRecyclerView.this.itemViews[i].getLayoutParams().height = -2;
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public List<YailDictionary> filterItems;
    /* access modifiers changed from: private */
    public int idCard = 1;
    /* access modifiers changed from: private */
    public int idFirst = -1;
    /* access modifiers changed from: private */
    public int idImages = -1;
    /* access modifiers changed from: private */
    public int idSecond = -1;
    private int imageHeight;
    private int imageWidth;
    public boolean isSelected = false;
    public Boolean[] isVisible;
    /* access modifiers changed from: private */
    public CardView[] itemViews;
    /* access modifiers changed from: private */
    public List<YailDictionary> items;
    private int layoutType;
    /* access modifiers changed from: private */
    public boolean multiSelect;
    public Boolean[] selection;
    private int selectionColor;
    private int textDetailColor;
    private String textDetailFont;
    private float textDetailSize;
    private int textMainColor;
    private String textMainFont;
    private float textMainSize;

    public interface ClickListener {
        void onItemClick(int i, View view);
    }

    public ListAdapterWithRecyclerView(ComponentContainer container2, List<YailDictionary> items2, int textMainColor2, int textDetailColor2, float textMainSize2, float textDetailSize2, String textMainFont2, String textDetailFont2, int layoutType2, int backgroundColor2, int selectionColor2, int imageWidth2, int imageHeight2, boolean multiSelect2) {
        this.items = items2;
        this.container = container2;
        this.textMainSize = textMainSize2;
        this.textMainColor = textMainColor2;
        this.textDetailColor = textDetailColor2;
        this.textDetailSize = textDetailSize2;
        this.textMainFont = textMainFont2;
        this.textDetailFont = textDetailFont2;
        this.layoutType = layoutType2;
        this.backgroundColor = backgroundColor2;
        this.selectionColor = selectionColor2;
        this.imageHeight = imageHeight2;
        this.imageWidth = imageWidth2;
        this.itemViews = new CardView[items2.size()];
        this.multiSelect = multiSelect2;
        Boolean[] boolArr = new Boolean[items2.size()];
        this.selection = boolArr;
        Arrays.fill(boolArr, Boolean.FALSE);
        Boolean[] boolArr2 = new Boolean[items2.size()];
        this.isVisible = boolArr2;
        Arrays.fill(boolArr2, Boolean.TRUE);
    }

    public ListAdapterWithRecyclerView(ComponentContainer container2, List<String> stringItems, int textMainColor2, float textMainSize2, String textMainFont2, int backgroundColor2, int selectionColor2) {
        this.container = container2;
        this.textMainSize = textMainSize2;
        this.textMainColor = textMainColor2;
        this.textMainFont = textMainFont2;
        this.textDetailColor = textMainColor2;
        this.textDetailSize = 0.0f;
        this.textDetailFont = Component.TYPEFACE_DEFAULT;
        this.layoutType = 0;
        this.backgroundColor = backgroundColor2;
        this.selectionColor = selectionColor2;
        this.imageHeight = 0;
        this.imageWidth = 0;
        this.multiSelect = false;
        this.itemViews = new CardView[stringItems.size()];
        Boolean[] boolArr = new Boolean[stringItems.size()];
        this.selection = boolArr;
        Arrays.fill(boolArr, Boolean.FALSE);
        Boolean[] boolArr2 = new Boolean[stringItems.size()];
        this.isVisible = boolArr2;
        Arrays.fill(boolArr2, Boolean.TRUE);
        this.items = new ArrayList();
        for (String itemString : stringItems) {
            YailDictionary itemDict = new YailDictionary();
            itemDict.put(Component.LISTVIEW_KEY_MAIN_TEXT, itemString);
            this.items.add(itemDict);
        }
    }

    public void clearSelections() {
        Arrays.fill(this.selection, Boolean.FALSE);
        int i = 0;
        while (true) {
            CardView[] cardViewArr = this.itemViews;
            if (i < cardViewArr.length) {
                cardViewArr[i].setBackgroundColor(this.backgroundColor);
                i++;
            } else {
                return;
            }
        }
    }

    public void toggleSelection(int pos) {
        Arrays.fill(this.selection, Boolean.FALSE);
        int i = 0;
        while (true) {
            CardView[] cardViewArr = this.itemViews;
            if (i >= cardViewArr.length) {
                break;
            }
            CardView cardView = cardViewArr[i];
            if (cardView != null) {
                cardView.setBackgroundColor(this.backgroundColor);
            }
            i++;
        }
        if (pos >= 0) {
            this.selection[pos] = true;
            CardView cardView2 = this.itemViews[pos];
            if (cardView2 != null) {
                cardView2.setBackgroundColor(this.selectionColor);
            }
        }
    }

    public void changeSelections(int pos) {
        Boolean[] boolArr = this.selection;
        boolArr[pos] = Boolean.valueOf(!boolArr[pos].booleanValue());
        if (this.selection[pos].booleanValue()) {
            this.itemViews[pos].setBackgroundColor(this.selectionColor);
        } else {
            this.itemViews[pos].setBackgroundColor(this.backgroundColor);
        }
    }

    public boolean hasVisibleItems() {
        return Arrays.asList(this.isVisible).contains(true);
    }

    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = new CardView(this.container.$context());
        cardView.setUseCompatPadding(true);
        cardView.setContentPadding(10, 10, 10, 10);
        cardView.setPreventCornerOverlap(true);
        cardView.setCardElevation(2.1f);
        cardView.setRadius(0.0f);
        cardView.setMaxCardElevation(3.0f);
        cardView.setBackgroundColor(this.backgroundColor);
        cardView.setClickable(this.isSelected);
        int generateViewId = ViewCompat.generateViewId();
        this.idCard = generateViewId;
        cardView.setId(generateViewId);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(-1, -2);
        params1.setMargins(0, 0, 0, 0);
        ViewCompat.setElevation(cardView, 20.0f);
        TextView textViewFirst = new TextView(this.container.$context());
        int generateViewId2 = ViewCompat.generateViewId();
        this.idFirst = generateViewId2;
        textViewFirst.setId(generateViewId2);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams1.topMargin = 10;
        textViewFirst.setLayoutParams(layoutParams1);
        textViewFirst.setTextSize(this.textMainSize);
        textViewFirst.setTextColor(this.textMainColor);
        TextViewUtil.setFontTypeface(this.container.$form(), textViewFirst, this.textMainFont, false, false);
        LinearLayout linearLayout1 = new LinearLayout(this.container.$context());
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout1.setOrientation(0);
        int i = this.layoutType;
        if (i == 4 || i == 3) {
            ImageView imageView = new ImageView(this.container.$context());
            int generateViewId3 = ViewCompat.generateViewId();
            this.idImages = generateViewId3;
            imageView.setId(generateViewId3);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(this.imageWidth, this.imageHeight));
            linearLayout1.addView(imageView);
        }
        int i2 = this.layoutType;
        if (i2 == 0 || i2 == 3) {
            linearLayout1.addView(textViewFirst);
        } else {
            TextView textViewSecond = new TextView(this.container.$context());
            int generateViewId4 = ViewCompat.generateViewId();
            this.idSecond = generateViewId4;
            textViewSecond.setId(generateViewId4);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            textViewSecond.setTextSize(this.textDetailSize);
            TextViewUtil.setFontTypeface(this.container.$form(), textViewSecond, this.textDetailFont, false, false);
            textViewSecond.setTextColor(this.textDetailColor);
            int i3 = this.layoutType;
            if (i3 == 1 || i3 == 4) {
                layoutParams2.topMargin = 10;
                textViewSecond.setLayoutParams(layoutParams2);
                LinearLayout linearLayout2 = new LinearLayout(this.container.$context());
                linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 2.0f));
                linearLayout2.setOrientation(1);
                linearLayout2.addView(textViewFirst);
                linearLayout2.addView(textViewSecond);
                linearLayout1.addView(linearLayout2);
            } else if (i3 == 2) {
                layoutParams2.setMargins(50, 10, 0, 0);
                textViewSecond.setLayoutParams(layoutParams2);
                textViewSecond.setMaxLines(1);
                textViewSecond.setEllipsize((TextUtils.TruncateAt) null);
                linearLayout1.addView(textViewFirst);
                linearLayout1.addView(textViewSecond);
            }
        }
        cardView.setLayoutParams(params1);
        cardView.addView(linearLayout1);
        return new RvViewHolder(cardView);
    }

    public void onBindViewHolder(final RvViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                holder.onClick(v);
            }
        });
        YailDictionary dictItem = this.items.get(position);
        String first = dictItem.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString();
        String second = "";
        if (dictItem.containsKey(Component.LISTVIEW_KEY_DESCRIPTION)) {
            second = dictItem.get(Component.LISTVIEW_KEY_DESCRIPTION).toString();
        }
        int i = this.layoutType;
        if (i == 0) {
            holder.textViewFirst.setText(first);
        } else if (i == 1) {
            holder.textViewFirst.setText(first);
            holder.textViewSecond.setText(second);
        } else if (i == 2) {
            holder.textViewFirst.setText(first);
            holder.textViewSecond.setText(second);
        } else if (i == 3) {
            String imageName = dictItem.get(Component.LISTVIEW_KEY_IMAGE).toString();
            Drawable drawable = new BitmapDrawable();
            try {
                drawable = MediaUtil.getBitmapDrawable(this.container.$form(), imageName);
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "onBindViewHolder Unable to load image " + imageName + ": " + ioe.getMessage());
            }
            holder.textViewFirst.setText(first);
            ViewUtil.setImage(holder.imageVieww, drawable);
        } else if (i == 4) {
            String imageName2 = dictItem.get(Component.LISTVIEW_KEY_IMAGE).toString();
            Drawable drawable2 = new BitmapDrawable();
            try {
                drawable2 = MediaUtil.getBitmapDrawable(this.container.$form(), imageName2);
            } catch (IOException ioe2) {
                Log.e(LOG_TAG, "onBindViewHolder Unable to load image " + imageName2 + ": " + ioe2.getMessage());
            }
            holder.textViewFirst.setText(first);
            holder.textViewSecond.setText(second);
            ViewUtil.setImage(holder.imageVieww, drawable2);
        } else {
            Log.e(LOG_TAG, "onBindViewHolder Layout not recognized: " + i);
        }
        if (this.selection[position].booleanValue()) {
            holder.cardView.setBackgroundColor(this.selectionColor);
        } else {
            holder.cardView.setBackgroundColor(this.backgroundColor);
        }
        if (!this.isVisible[position].booleanValue()) {
            holder.cardView.setVisibility(8);
            holder.cardView.getLayoutParams().height = 0;
        } else {
            holder.cardView.setVisibility(0);
            holder.cardView.getLayoutParams().height = -2;
        }
        this.itemViews[position] = holder.cardView;
    }

    public int getItemCount() {
        return this.itemViews.length;
    }

    class RvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public ImageView imageVieww;
        public TextView textViewFirst;
        public TextView textViewSecond;

        public RvViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.cardView = view.findViewById(ListAdapterWithRecyclerView.this.idCard);
            this.textViewFirst = (TextView) view.findViewById(ListAdapterWithRecyclerView.this.idFirst);
            if (ListAdapterWithRecyclerView.this.idSecond != -1) {
                this.textViewSecond = (TextView) view.findViewById(ListAdapterWithRecyclerView.this.idSecond);
            }
            if (ListAdapterWithRecyclerView.this.idImages != -1) {
                this.imageVieww = (ImageView) view.findViewById(ListAdapterWithRecyclerView.this.idImages);
            }
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (ListAdapterWithRecyclerView.this.multiSelect) {
                ListAdapterWithRecyclerView.this.changeSelections(position);
            } else {
                ListAdapterWithRecyclerView.this.toggleSelection(position);
            }
            ListAdapterWithRecyclerView.this.clickListener.onItemClick(position, v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener2) {
        this.clickListener = clickListener2;
    }

    public String getSelectedItems() {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        int i = 0;
        while (true) {
            Boolean[] boolArr = this.selection;
            if (i >= boolArr.length) {
                return sb.toString();
            }
            if (boolArr[i].booleanValue()) {
                sb.append(sep);
                sb.append(this.items.get(i).get(Component.LISTVIEW_KEY_MAIN_TEXT).toString());
                sep = ",";
            }
            i++;
        }
    }

    public Filter getFilter() {
        return this.filter;
    }
}
