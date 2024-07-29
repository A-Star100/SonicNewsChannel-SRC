package com.google.appinventor.components.runtime.util;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.WebViewer;

public class HoneycombWebViewClient extends FroyoWebViewClient<WebViewer> {
    private static final String ASSET_PREFIX = "file:///appinventor_asset/";
    private static final String TAG = HoneycombWebViewClient.class.getSimpleName();

    public HoneycombWebViewClient(boolean followLinks, boolean ignoreErrors, Form form, WebViewer component) {
        super(followLinks, ignoreErrors, form, component);
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.startsWith("http://localhost/") || url.startsWith(ASSET_PREFIX)) {
            return handleAppRequest(url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.d(TAG, "scheme = " + request.getUrl().getScheme());
        if ("localhost".equals(request.getUrl().getAuthority()) || request.getUrl().toString().startsWith(ASSET_PREFIX)) {
            return handleAppRequest(request.getUrl().toString());
        }
        return super.shouldInterceptRequest(view, request);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008d A[Catch:{ IOException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x009c A[Catch:{ IOException -> 0x00a2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.webkit.WebResourceResponse handleAppRequest(java.lang.String r17) {
        /*
            r16 = this;
            r1 = r17
            java.lang.String r2 = "utf-8"
            java.lang.String r0 = "file:///appinventor_asset/"
            boolean r3 = r1.startsWith(r0)
            if (r3 == 0) goto L_0x0016
            int r0 = r0.length()
            java.lang.String r0 = r1.substring(r0)
            r3 = r0
            goto L_0x0023
        L_0x0016:
            java.lang.String r0 = "//localhost/"
            int r0 = r1.indexOf(r0)
            int r0 = r0 + 12
            java.lang.String r0 = r1.substring(r0)
            r3 = r0
        L_0x0023:
            r4 = 21
            java.lang.String r0 = TAG     // Catch:{ IOException -> 0x00a2 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a2 }
            r5.<init>()     // Catch:{ IOException -> 0x00a2 }
            java.lang.String r6 = "webviewer requested path = "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x00a2 }
            java.lang.StringBuilder r5 = r5.append(r3)     // Catch:{ IOException -> 0x00a2 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x00a2 }
            android.util.Log.i(r0, r5)     // Catch:{ IOException -> 0x00a2 }
            com.google.appinventor.components.runtime.Form r5 = r16.getForm()     // Catch:{ IOException -> 0x00a2 }
            java.io.InputStream r5 = r5.openAsset(r3)     // Catch:{ IOException -> 0x00a2 }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ IOException -> 0x00a2 }
            r6.<init>()     // Catch:{ IOException -> 0x00a2 }
            r13 = r6
            java.lang.String r6 = "Access-Control-Allow-Origin"
            java.lang.String r7 = "localhost"
            r13.put(r6, r7)     // Catch:{ IOException -> 0x00a2 }
            java.net.FileNameMap r6 = java.net.URLConnection.getFileNameMap()     // Catch:{ IOException -> 0x00a2 }
            java.lang.String r6 = r6.getContentTypeFor(r3)     // Catch:{ IOException -> 0x00a2 }
            r14 = r6
            r6 = r2
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a2 }
            r7.<init>()     // Catch:{ IOException -> 0x00a2 }
            java.lang.String r8 = "Mime type = "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x00a2 }
            java.lang.StringBuilder r7 = r7.append(r14)     // Catch:{ IOException -> 0x00a2 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x00a2 }
            android.util.Log.i(r0, r7)     // Catch:{ IOException -> 0x00a2 }
            if (r14 == 0) goto L_0x0087
            java.lang.String r0 = "text/"
            boolean r0 = r14.startsWith(r0)     // Catch:{ IOException -> 0x00a2 }
            if (r0 != 0) goto L_0x0085
            java.lang.String r0 = "application/javascript"
            boolean r0 = r14.equals(r0)     // Catch:{ IOException -> 0x00a2 }
            if (r0 != 0) goto L_0x0085
            goto L_0x0087
        L_0x0085:
            r0 = r6
            goto L_0x0089
        L_0x0087:
            r6 = 0
            r0 = r6
        L_0x0089:
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ IOException -> 0x00a2 }
            if (r6 < r4) goto L_0x009c
            android.webkit.WebResourceResponse r15 = new android.webkit.WebResourceResponse     // Catch:{ IOException -> 0x00a2 }
            r9 = 200(0xc8, float:2.8E-43)
            java.lang.String r10 = "OK"
            r6 = r15
            r7 = r14
            r8 = r0
            r11 = r13
            r12 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12)     // Catch:{ IOException -> 0x00a2 }
            return r15
        L_0x009c:
            android.webkit.WebResourceResponse r6 = new android.webkit.WebResourceResponse     // Catch:{ IOException -> 0x00a2 }
            r6.<init>(r14, r0, r5)     // Catch:{ IOException -> 0x00a2 }
            return r6
        L_0x00a2:
            r0 = move-exception
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream
            java.lang.String r6 = "404 Not Found"
            byte[] r6 = r6.getBytes()
            r5.<init>(r6)
            int r6 = android.os.Build.VERSION.SDK_INT
            if (r6 < r4) goto L_0x00c3
            android.webkit.WebResourceResponse r2 = new android.webkit.WebResourceResponse
            r10 = 404(0x194, float:5.66E-43)
            r12 = 0
            java.lang.String r8 = "text/plain"
            java.lang.String r9 = "utf-8"
            java.lang.String r11 = "Not Found"
            r7 = r2
            r13 = r5
            r7.<init>(r8, r9, r10, r11, r12, r13)
            return r2
        L_0x00c3:
            android.webkit.WebResourceResponse r4 = new android.webkit.WebResourceResponse
            java.lang.String r6 = "text/plain"
            r4.<init>(r6, r2, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.HoneycombWebViewClient.handleAppRequest(java.lang.String):android.webkit.WebResourceResponse");
    }
}
