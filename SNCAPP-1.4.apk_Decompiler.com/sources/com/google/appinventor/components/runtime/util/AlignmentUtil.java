package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.HorizontalAlignment;
import com.google.appinventor.components.common.VerticalAlignment;
import com.google.appinventor.components.runtime.LinearLayout;

public class AlignmentUtil {
    LinearLayout viewLayout;

    public AlignmentUtil(LinearLayout viewLayout2) {
        this.viewLayout = viewLayout2;
    }

    public void setHorizontalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case 1:
                this.viewLayout.setHorizontalGravity(3);
                return;
            case 2:
                this.viewLayout.setHorizontalGravity(5);
                return;
            case 3:
                this.viewLayout.setHorizontalGravity(1);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setHorizontalAlignment: " + alignment);
        }
    }

    public void setHorizontalAlignment(HorizontalAlignment alignment) {
        switch (AnonymousClass1.$SwitchMap$com$google$appinventor$components$common$HorizontalAlignment[alignment.ordinal()]) {
            case 1:
                this.viewLayout.setHorizontalGravity(3);
                return;
            case 2:
                this.viewLayout.setHorizontalGravity(1);
                return;
            case 3:
                this.viewLayout.setHorizontalGravity(5);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setHorizontalAlignment: " + alignment);
        }
    }

    public void setVerticalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case 1:
                this.viewLayout.setVerticalGravity(48);
                return;
            case 2:
                this.viewLayout.setVerticalGravity(16);
                return;
            case 3:
                this.viewLayout.setVerticalGravity(80);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setVerticalAlignment: " + alignment);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.AlignmentUtil$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$HorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$google$appinventor$components$common$VerticalAlignment;

        static {
            int[] iArr = new int[VerticalAlignment.values().length];
            $SwitchMap$com$google$appinventor$components$common$VerticalAlignment = iArr;
            try {
                iArr[VerticalAlignment.Top.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$VerticalAlignment[VerticalAlignment.Center.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$VerticalAlignment[VerticalAlignment.Bottom.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[HorizontalAlignment.values().length];
            $SwitchMap$com$google$appinventor$components$common$HorizontalAlignment = iArr2;
            try {
                iArr2[HorizontalAlignment.Left.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$HorizontalAlignment[HorizontalAlignment.Center.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$appinventor$components$common$HorizontalAlignment[HorizontalAlignment.Right.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public void setVerticalAlignment(VerticalAlignment alignment) {
        switch (AnonymousClass1.$SwitchMap$com$google$appinventor$components$common$VerticalAlignment[alignment.ordinal()]) {
            case 1:
                this.viewLayout.setVerticalGravity(48);
                return;
            case 2:
                this.viewLayout.setVerticalGravity(16);
                return;
            case 3:
                this.viewLayout.setVerticalGravity(80);
                return;
            default:
                throw new IllegalArgumentException("Bad value to setVerticalAlignment: " + alignment);
        }
    }
}
