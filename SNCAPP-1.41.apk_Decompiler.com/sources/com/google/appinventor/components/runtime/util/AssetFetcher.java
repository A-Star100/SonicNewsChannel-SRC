package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;

public class AssetFetcher {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = AssetFetcher.class.getSimpleName();
    private static ExecutorService background = Executors.newSingleThreadExecutor();
    private static Context context = ReplForm.getActiveForm();
    private static HashDatabase db = new HashDatabase(context);
    private static volatile boolean inError = false;
    private static final Object semaphore = new Object();

    private AssetFetcher() {
    }

    public static void fetchAssets(final String cookieValue, final String projectId, final String uri, final String asset) {
        background.submit(new Runnable() {
            public void run() {
                String str = uri;
                String str2 = projectId;
                if (AssetFetcher.getFile(str + "/ode/download/file/" + str2 + "/" + asset, cookieValue, asset, 0) != null) {
                    RetValManager.assetTransferred(asset);
                }
            }
        });
    }

    public static void upgradeCompanion(final String cookieValue, final String inputUri) {
        background.submit(new Runnable() {
            public void run() {
                String[] parts = inputUri.split("/", 0);
                File assetFile = AssetFetcher.getFile(inputUri, cookieValue, parts[parts.length - 1], 0);
                if (assetFile != null) {
                    try {
                        Form form = Form.getActiveForm();
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(NougatUtil.getPackageUri(form, assetFile), "application/vnd.android.package-archive");
                        intent.setFlags(1);
                        form.startActivity(intent);
                    } catch (Exception e) {
                        Log.e(AssetFetcher.LOG_TAG, "ERROR_UNABLE_TO_GET", e);
                        RetValManager.sendError("Unable to Install new Companion Package.");
                    }
                }
            }
        });
    }

    public static void loadExtensions(String jsonString) {
        String str = LOG_TAG;
        Log.d(str, "loadExtensions called jsonString = " + jsonString);
        try {
            ReplForm form = (ReplForm) Form.getActiveForm();
            JSONArray array = new JSONArray(jsonString);
            List<String> extensionsToLoad = new ArrayList<>();
            if (array.length() == 0) {
                Log.d(str, "loadExtensions: No Extensions");
                RetValManager.extensionsLoaded();
                return;
            }
            int i = 0;
            while (i < array.length()) {
                String extensionName = array.optString(i);
                if (extensionName != null) {
                    Log.d(LOG_TAG, "loadExtensions, extensionName = " + extensionName);
                    extensionsToLoad.add(extensionName);
                    i++;
                } else {
                    Log.e(LOG_TAG, "extensionName was null");
                    return;
                }
            }
            try {
                form.loadComponents(extensionsToLoad);
                RetValManager.extensionsLoaded();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in form.loadComponents", e);
            }
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "JSON Exception parsing extension string", e2);
        }
    }

    /* access modifiers changed from: private */
    public static File getFile(String fileName, String cookieValue, String asset, int depth) {
        BufferedOutputStream out;
        int responseCode;
        final String str = fileName;
        String str2 = cookieValue;
        String str3 = asset;
        int i = depth;
        Form form = Form.getActiveForm();
        if (i > 1) {
            synchronized (semaphore) {
                if (inError) {
                    return null;
                }
                inError = true;
                form.runOnUiThread(new Runnable() {
                    public void run() {
                        RuntimeErrorAlert.alert(Form.getActiveForm(), "Unable to load file: " + str, "Error!", "End Application");
                    }
                });
                return null;
            }
        }
        int responseCode2 = 0;
        File outFile = new File(QUtil.getReplAssetPath(form, true), str3.substring("assets/".length()));
        String str4 = LOG_TAG;
        Log.d(str4, "target file = " + outFile);
        String fileHash = null;
        boolean error = false;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(str).openConnection();
            if (connection != null) {
                connection.addRequestProperty("Cookie", "AppInventor = " + str2);
                HashFile hashFile = db.getHashFile(str3);
                if (hashFile != null) {
                    try {
                        connection.addRequestProperty("If-None-Match", hashFile.getHash());
                    } catch (Exception e) {
                        e = e;
                        Form form2 = form;
                    }
                }
                connection.setRequestMethod("GET");
                int responseCode3 = connection.getResponseCode();
                try {
                    Log.d(str4, "asset = " + str3 + " responseCode = " + responseCode3);
                    File parentOutFile = outFile.getParentFile();
                    fileHash = connection.getHeaderField("ETag");
                    if (responseCode3 == 304) {
                        return outFile;
                    }
                    if (!parentOutFile.exists()) {
                        if (!parentOutFile.mkdirs()) {
                            throw new IOException("Unable to create assets directory " + parentOutFile);
                        }
                    }
                    BufferedInputStream in = new BufferedInputStream(connection.getInputStream(), 4096);
                    Form form3 = form;
                    try {
                        out = new BufferedOutputStream(new FileOutputStream(outFile), 4096);
                        while (true) {
                            try {
                                int b = in.read();
                                if (b == -1) {
                                    break;
                                }
                                out.write(b);
                            } catch (IOException e2) {
                                responseCode = responseCode3;
                                Log.e(LOG_TAG, "copying assets", e2);
                                error = true;
                                try {
                                    out.close();
                                } catch (Exception e3) {
                                    e = e3;
                                    int i2 = responseCode;
                                    Log.e(LOG_TAG, "Exception while fetching " + str, e);
                                    return getFile(str, str2, str3, i + 1);
                                }
                            } catch (Throwable th) {
                                th = th;
                                out.close();
                                throw th;
                            }
                        }
                        out.flush();
                    } catch (Exception e4) {
                        e = e4;
                        int i3 = responseCode3;
                        Log.e(LOG_TAG, "Exception while fetching " + str, e);
                        return getFile(str, str2, str3, i + 1);
                    }
                    try {
                        out.close();
                        responseCode = responseCode3;
                        connection.disconnect();
                        responseCode2 = responseCode;
                    } catch (Exception e5) {
                        e = e5;
                        Log.e(LOG_TAG, "Exception while fetching " + str, e);
                        return getFile(str, str2, str3, i + 1);
                    }
                } catch (Exception e6) {
                    e = e6;
                    Form form4 = form;
                    int i4 = responseCode3;
                    Log.e(LOG_TAG, "Exception while fetching " + str, e);
                    return getFile(str, str2, str3, i + 1);
                }
            } else {
                error = true;
            }
            if (error) {
                return getFile(str, str2, str3, i + 1);
            }
            if (responseCode2 != 200) {
                return null;
            }
            HashFile file = new HashFile(str3, fileHash, new Date());
            if (db.getHashFile(str3) == null) {
                db.insertHashFile(file);
            } else {
                db.updateHashFile(file);
            }
            return outFile;
        } catch (Exception e7) {
            e = e7;
            Form form5 = form;
            Log.e(LOG_TAG, "Exception while fetching " + str, e);
            return getFile(str, str2, str3, i + 1);
        }
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        int length = hash.length;
        for (int i = 0; i < length; i++) {
            formatter.format("%02x", new Object[]{Byte.valueOf(hash[i])});
        }
        return formatter.toString();
    }
}
