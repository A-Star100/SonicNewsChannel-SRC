package com.google.appinventor.components.runtime;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

@DesignerComponent(category = ComponentCategory.EXPERIMENTAL, description = "A Non-visible component for communicating with an AI chat bot. This component currently communicates with a proxy run by MIT which in turn uses OpenAI's ChatGPT API. This component is considered experimental.", iconName = "images/chatbot.png", nonVisible = true, version = 3)
@UsesLibraries(libraries = "protobuf-java-3.0.0.jar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class ChatBot extends AndroidNonvisibleComponent {
    public static final String CHATBOT_SERVICE_URL = "https://chatbot.appinventor.mit.edu/chat/v1";
    private static final String COMODO_ROOT = "-----BEGIN CERTIFICATE-----\nMIIENjCCAx6gAwIBAgIBATANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJTRTEU\nMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFkZFRydXN0IEV4dGVybmFs\nIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBFeHRlcm5hbCBDQSBSb290\nMB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFowbzELMAkGA1UEBhMCU0Ux\nFDASBgNVBAoTC0FkZFRydXN0IEFCMSYwJAYDVQQLEx1BZGRUcnVzdCBFeHRlcm5h\nbCBUVFAgTmV0d29yazEiMCAGA1UEAxMZQWRkVHJ1c3QgRXh0ZXJuYWwgQ0EgUm9v\ndDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALf3GjPm8gAELTngTlvt\nH7xsD821+iO2zt6bETOXpClMfZOfvUq8k+0DGuOPz+VtUFrWlymUWoCwSXrbLpX9\nuMq/NzgtHj6RQa1wVsfwTz/oMp50ysiQVOnGXw94nZpAPA6sYapeFI+eh6FqUNzX\nmk6vBbOmcZSccbNQYArHE504B4YCqOmoaSYYkKtMsE8jqzpPhNjfzp/haW+710LX\na0Tkx63ubUFfclpxCDezeWWkWaCUN/cALw3CknLa0Dhy2xSoRcRdKn23tNbE7qzN\nE0S3ySvdQwAl+mG5aWpYIxG3pzOPVnVZ9c0p10a3CitlttNCbxWyuHv77+ldU9U0\nWicCAwEAAaOB3DCB2TAdBgNVHQ4EFgQUrb2YejS0Jvf6xCZU7wO94CTLVBowCwYD\nVR0PBAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wgZkGA1UdIwSBkTCBjoAUrb2YejS0\nJvf6xCZU7wO94CTLVBqhc6RxMG8xCzAJBgNVBAYTAlNFMRQwEgYDVQQKEwtBZGRU\ncnVzdCBBQjEmMCQGA1UECxMdQWRkVHJ1c3QgRXh0ZXJuYWwgVFRQIE5ldHdvcmsx\nIjAgBgNVBAMTGUFkZFRydXN0IEV4dGVybmFsIENBIFJvb3SCAQEwDQYJKoZIhvcN\nAQEFBQADggEBALCb4IUlwtYj4g+WBpKdQZic2YR5gdkeWxQHIzZlj7DYd7usQWxH\nYINRsPkyPef89iYTx4AWpb9a/IfPeHmJIZriTAcKhjW88t5RxNKWt9x+Tu5w/Rw5\n6wwCURQtjr0W4MHfRnXnJK3s9EK0hZNwEGe6nQY1ShjTK3rMUUKhemPR5ruhxSvC\nNr4TDea9Y355e6cJDUCrat2PisP29owaQgVR1EX1n6diIWgVIEM8med8vSTYqZEX\nc4g/VhsxOBi0cQ+azcgOno4uG+GMmIPLHzHxREzGBHNJdmAPx/i9F4BrLunMTA5a\nmnkPIAou1Z5jJh5VkpTYghdae9C8x49OhgQ=\n-----END CERTIFICATE-----\n";
    private static final String COMODO_USRTRUST = "-----BEGIN CERTIFICATE-----\nMIIFdzCCBF+gAwIBAgIQE+oocFv07O0MNmMJgGFDNjANBgkqhkiG9w0BAQwFADBv\nMQswCQYDVQQGEwJTRTEUMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFk\nZFRydXN0IEV4dGVybmFsIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBF\neHRlcm5hbCBDQSBSb290MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFow\ngYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5MRQwEgYDVQQHEwtK\nZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBOZXR3b3JrMS4wLAYD\nVQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIICIjAN\nBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sIs9CsVw127c0n00yt\nUINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnGvDoZtF+mvX2do2NC\ntnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQIjy8/hPwhxR79uQf\njtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfbIWax1Jt4A8BQOujM\n8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0tyA9yn8iNK5+O2hm\nAUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97Exwzf4TKuzJM7UXiV\nZ4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNVicQNwZNUMBkTrNN9\nN6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5D9kCnusSTJV882sF\nqV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJWBp/kjbmUZIO8yZ9\nHE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ5lhCLkMaTLTwJUdZ\n+gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzGKAgEJTm4Diup8kyX\nHAc/DVL17e8vgg8CAwEAAaOB9DCB8TAfBgNVHSMEGDAWgBStvZh6NLQm9/rEJlTv\nA73gJMtUGjAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rIDZsswDgYDVR0PAQH/\nBAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAGBgRVHSAAMEQGA1Ud\nHwQ9MDswOaA3oDWGM2h0dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9BZGRUcnVzdEV4\ndGVybmFsQ0FSb290LmNybDA1BggrBgEFBQcBAQQpMCcwJQYIKwYBBQUHMAGGGWh0\ndHA6Ly9vY3NwLnVzZXJ0cnVzdC5jb20wDQYJKoZIhvcNAQEMBQADggEBAJNl9jeD\nlQ9ew4IcH9Z35zyKwKoJ8OkLJvHgwmp1ocd5yblSYMgpEg7wrQPWCcR23+WmgZWn\nRtqCV6mVksW2jwMibDN3wXsyF24HzloUQToFJBv2FAY7qCUkDrvMKnXduXBBP3zQ\nYzYhBx9G/2CkkeFnvN4ffhkUyWNnkepnB2u0j4vAbkN9w6GAbLIevFOFfdyQoaS8\nLe9Gclc1Bb+7RrtubTeZtv8jkpHGbkD4jylW6l/VXxRTrPBPYer3IsynVgviuDQf\nJtl7GQVoP7o81DgGotPmjw7jtHFtQELFhLRAlSv0ZaBIefYdgWOWnU914Ph85I6p\n0fKtirOMxyHNwu8=\n-----END CERTIFICATE-----\n";
    private static final boolean DEBUG = false;
    private static final String ISRG_ROOT_X1 = "-----BEGIN CERTIFICATE-----\nMIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw\nTzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh\ncmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4\nWhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu\nZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY\nMTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc\nh77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+\n0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U\nA5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW\nT8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH\nB5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC\nB5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv\nKBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn\nOlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn\njh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw\nqHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI\nrU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV\nHRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq\nhkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL\nubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ\n3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK\nNFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5\nORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur\nTkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC\njNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc\noyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq\n4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA\nmRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d\nemyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=\n-----END CERTIFICATE-----\n";
    private static final String LOG_TAG = "ChatBot";
    private static final String MIT_CA = "-----BEGIN CERTIFICATE-----\nMIIFXjCCBEagAwIBAgIJAMLfrRWIaHLbMA0GCSqGSIb3DQEBCwUAMIHPMQswCQYD\nVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJyaWRnZTEuMCwGA1UE\nChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5vbG9neTEZMBcGA1UE\nCxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWREQiBDZXJ0aWZpY2F0\nZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkqhkiG9w0BCQEWC2pp\nc0BtaXQuZWR1MB4XDTE3MTIyMjIyMzkyOVoXDTI3MTIyMDIyMzkyOVowgc8xCzAJ\nBgNVBAYTAlVTMQswCQYDVQQIEwJNQTESMBAGA1UEBxMJQ2FtYnJpZGdlMS4wLAYD\nVQQKEyVNYXNzYWNodXNldHRzIEluc3RpdHV0ZSBvZiBUZWNobm9sb2d5MRkwFwYD\nVQQLExBNSVQgQXBwIEludmVudG9yMSYwJAYDVQQDEx1DbG91ZERCIENlcnRpZmlj\nYXRlIEF1dGhvcml0eTEQMA4GA1UEKRMHRWFzeVJTQTEaMBgGCSqGSIb3DQEJARYL\namlzQG1pdC5lZHUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDHzI3D\nFobNDv2HTWlDdedmbxZIJYSqWlzdRJC3oVJgCubdAs46WJRqUxDRWft9UpYGMKkw\nmYN8mdPby2m5OJagdVIZgnguB71zIQkC8yMzd94FC3gldX5m7R014D/0fkpzvsSt\n6fsNectJT0k7gPELOH6t4u6AUbvIsEX0nNyRWsmA/ucXCsDBwXyBJxfOKIQ9tDI4\n/WfcKk9JDpeMF7RP0CIOtlAPotKIaPoY1W3eMIi/0riOt5vTFsB8pxhxAVy0cfGX\niHukdrAkAJixTgkyS7wzk22xOeXVnRIzAMGK5xHMDw/HRQGTrUGfIXHENV3u+3Ae\nL5/ZoQwyZTixmQNzAgMBAAGjggE5MIIBNTAdBgNVHQ4EFgQUZfMKQXqtC5UJGFrZ\ngZE1nmlx+t8wggEEBgNVHSMEgfwwgfmAFGXzCkF6rQuVCRha2YGRNZ5pcfrfoYHV\npIHSMIHPMQswCQYDVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJy\naWRnZTEuMCwGA1UEChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5v\nbG9neTEZMBcGA1UECxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWRE\nQiBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkq\nhkiG9w0BCQEWC2ppc0BtaXQuZWR1ggkAwt+tFYhoctswDAYDVR0TBAUwAwEB/zAN\nBgkqhkiG9w0BAQsFAAOCAQEAIkKr3eIvwZO6a1Jsh3qXwveVnrqwxYvLw2IhTwNT\n/P6C5jbRnzUuDuzg5sEIpbBo/Bp3qIp7G5cdVOkIrqO7uCp6Kyc7d9lPsEe/cbF4\naNwNmdWroRN1y0tuMU6+z7frd5pOeAZP9E/DM/0Uaz4yVzwnlvZUttaLymyMhH54\nisGQKbAqHDFtKZvb6DxsHzrO2YgeaBAtjeVhPWiv8BhzbOo9+hhZvYHYtoM2W+Ze\nDHuvv0v+qouphftDKVBp16N8Pk5WgabTXzV6VcNee92iwbWYDEv06+S3AF/q2TBe\nxxXtAa5ywbp6IRF37QuQChcYnOx7zIylYI1PIENfQFC2BA==\n-----END CERTIFICATE-----\n";
    private String apiKey;
    private String model = "";
    private String provider = "chatgpt";
    private int size = 256;
    private SSLSocketFactory sslSockFactory = null;
    private String system = "";
    private String token;
    /* access modifiers changed from: private */
    public String uuid = "";

    public ChatBot(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Reset the current conversation, Chat bot will forget any previous conversation when responding in the future.")
    public void ResetConversation() {
        this.uuid = "";
    }

    @SimpleFunction(description = "Ask a question of the Chat Bot. Successive calls will remember information from earlier in the conversation. Use the \"ResetConversation\" function to reset for a new conversation.")
    public void Converse(final String question) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                ChatBot chatBot = ChatBot.this;
                chatBot.performRequest(chatBot.uuid, question, (Bitmap) null);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7 A[Catch:{ all -> 0x0118, Exception -> 0x011f }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00af A[Catch:{ all -> 0x0118, Exception -> 0x011f }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b8 A[Catch:{ all -> 0x0118, Exception -> 0x011f }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bf A[Catch:{ all -> 0x0118, Exception -> 0x011f }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d8 A[SYNTHETIC, Splitter:B:38:0x00d8] */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void performRequest(java.lang.String r17, java.lang.String r18, android.graphics.Bitmap r19) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r19
            r0 = 0
            if (r3 == 0) goto L_0x001f
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>()
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.PNG
            r6 = 100
            r3.compress(r5, r6, r4)
            byte[] r5 = r4.toByteArray()
            com.google.protobuf.ByteString r0 = com.google.protobuf.ByteString.copyFrom(r5)
            r4 = r0
            goto L_0x0020
        L_0x001f:
            r4 = r0
        L_0x0020:
            r5 = 0
            r16.ensureSslSockFactory()
            r6 = -1
            r7 = 1
            java.lang.String r0 = "ChatBot"
            java.lang.String r8 = r1.apiKey     // Catch:{ Exception -> 0x0121 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0121 }
            r9.<init>()     // Catch:{ Exception -> 0x0121 }
            java.lang.String r10 = "performRequest: apiKey = "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0121 }
            java.lang.StringBuilder r8 = r9.append(r8)     // Catch:{ Exception -> 0x0121 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0121 }
            android.util.Log.d(r0, r8)     // Catch:{ Exception -> 0x0121 }
            java.lang.String r0 = r1.token     // Catch:{ Exception -> 0x0121 }
            java.lang.String r8 = ""
            if (r0 == 0) goto L_0x0063
            boolean r0 = r0.equals(r8)     // Catch:{ Exception -> 0x0121 }
            if (r0 != 0) goto L_0x0063
            java.lang.String r0 = r1.token     // Catch:{ Exception -> 0x0121 }
            r9 = 0
            java.lang.String r0 = r0.substring(r9, r7)     // Catch:{ Exception -> 0x0121 }
            java.lang.String r9 = "%"
            boolean r0 = r0.equals(r9)     // Catch:{ Exception -> 0x0121 }
            if (r0 == 0) goto L_0x0063
            java.lang.String r0 = r1.token     // Catch:{ Exception -> 0x0121 }
            java.lang.String r0 = r0.substring(r7)     // Catch:{ Exception -> 0x0121 }
            r9 = r0
            goto L_0x0066
        L_0x0063:
            java.lang.String r0 = r1.token     // Catch:{ Exception -> 0x0121 }
            r9 = r0
        L_0x0066:
            byte[] r0 = com.google.appinventor.components.runtime.util.Base58Util.decode(r9)     // Catch:{ Exception -> 0x0121 }
            r10 = r0
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$token r0 = com.google.appinventor.components.runtime.chatbot.ChatBotToken.token.parseFrom((byte[]) r10)     // Catch:{ Exception -> 0x0121 }
            r11 = r0
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r0 = com.google.appinventor.components.runtime.chatbot.ChatBotToken.request.newBuilder()     // Catch:{ Exception -> 0x0121 }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r0 = r0.setToken((com.google.appinventor.components.runtime.chatbot.ChatBotToken.token) r11)     // Catch:{ Exception -> 0x0121 }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r0 = r0.setUuid(r2)     // Catch:{ Exception -> 0x0121 }
            java.lang.String r12 = r1.provider     // Catch:{ Exception -> 0x0121 }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r0 = r0.setProvider(r12)     // Catch:{ Exception -> 0x0121 }
            r12 = r18
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r0 = r0.setQuestion(r12)     // Catch:{ Exception -> 0x011f }
            java.lang.String r13 = r1.system     // Catch:{ Exception -> 0x011f }
            boolean r13 = r13.equals(r8)     // Catch:{ Exception -> 0x011f }
            if (r13 != 0) goto L_0x009d
            boolean r13 = r2.equals(r8)     // Catch:{ Exception -> 0x011f }
            if (r13 == 0) goto L_0x009d
            java.lang.String r13 = r1.system     // Catch:{ Exception -> 0x011f }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r13 = r0.setSystem(r13)     // Catch:{ Exception -> 0x011f }
            r0 = r13
        L_0x009d:
            java.lang.String r13 = r1.apiKey     // Catch:{ Exception -> 0x011f }
            if (r13 == 0) goto L_0x00af
            boolean r8 = r13.equals(r8)     // Catch:{ Exception -> 0x011f }
            if (r8 != 0) goto L_0x00af
            java.lang.String r8 = r1.apiKey     // Catch:{ Exception -> 0x011f }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request$Builder r8 = r0.setApikey(r8)     // Catch:{ Exception -> 0x011f }
            r0 = r8
            goto L_0x00b0
        L_0x00af:
            r8 = r0
        L_0x00b0:
            java.lang.String r0 = r1.model     // Catch:{ Exception -> 0x011f }
            boolean r0 = r0.isEmpty()     // Catch:{ Exception -> 0x011f }
            if (r0 != 0) goto L_0x00bd
            java.lang.String r0 = r1.model     // Catch:{ Exception -> 0x011f }
            r8.setModel(r0)     // Catch:{ Exception -> 0x011f }
        L_0x00bd:
            if (r4 == 0) goto L_0x00c2
            r8.setInputimage(r4)     // Catch:{ Exception -> 0x011f }
        L_0x00c2:
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$request r0 = r8.build()     // Catch:{ Exception -> 0x011f }
            r13 = r0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x011f }
            java.lang.String r14 = "https://chatbot.appinventor.mit.edu/chat/v1"
            r0.<init>(r14)     // Catch:{ Exception -> 0x011f }
            r14 = r0
            java.net.URLConnection r0 = r14.openConnection()     // Catch:{ Exception -> 0x011f }
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0     // Catch:{ Exception -> 0x011f }
            r5 = r0
            if (r5 == 0) goto L_0x011e
            javax.net.ssl.SSLSocketFactory r0 = r1.sslSockFactory     // Catch:{ all -> 0x0118 }
            r5.setSSLSocketFactory(r0)     // Catch:{ all -> 0x0118 }
            java.lang.String r0 = "POST"
            r5.setRequestMethod(r0)     // Catch:{ all -> 0x0118 }
            r5.setDoOutput(r7)     // Catch:{ all -> 0x0118 }
            java.io.OutputStream r0 = r5.getOutputStream()     // Catch:{ all -> 0x0118 }
            r13.writeTo(r0)     // Catch:{ all -> 0x0118 }
            int r0 = r5.getResponseCode()     // Catch:{ all -> 0x0118 }
            r6 = r0
            java.io.InputStream r0 = r5.getInputStream()     // Catch:{ all -> 0x0118 }
            com.google.appinventor.components.runtime.chatbot.ChatBotToken$response r0 = com.google.appinventor.components.runtime.chatbot.ChatBotToken.response.parseFrom((java.io.InputStream) r0)     // Catch:{ all -> 0x0118 }
            r15 = 200(0xc8, float:2.8E-43)
            if (r6 != r15) goto L_0x010b
            java.lang.String r15 = r0.getAnswer()     // Catch:{ all -> 0x0118 }
            java.lang.String r7 = r0.getUuid()     // Catch:{ all -> 0x0118 }
            r1.uuid = r7     // Catch:{ all -> 0x0118 }
            r1.GotResponse(r15)     // Catch:{ all -> 0x0118 }
            goto L_0x0114
        L_0x010b:
            r7 = 1
            java.lang.String r15 = getResponseContent(r5, r7)     // Catch:{ all -> 0x0118 }
            r7 = r15
            r1.ErrorOccurred(r6, r7)     // Catch:{ all -> 0x0118 }
        L_0x0114:
            r5.disconnect()     // Catch:{ Exception -> 0x011f }
            goto L_0x011e
        L_0x0118:
            r0 = move-exception
            r5.disconnect()     // Catch:{ Exception -> 0x011f }
            throw r0     // Catch:{ Exception -> 0x011f }
        L_0x011e:
            goto L_0x0143
        L_0x011f:
            r0 = move-exception
            goto L_0x0124
        L_0x0121:
            r0 = move-exception
            r12 = r18
        L_0x0124:
            r7 = r0
            r7.printStackTrace()
            boolean r0 = r7 instanceof java.io.FileNotFoundException
            if (r0 == 0) goto L_0x013e
            if (r5 == 0) goto L_0x013e
            r8 = 1
            java.lang.String r0 = getResponseContent(r5, r8)     // Catch:{ IOException -> 0x0134 }
            goto L_0x013a
        L_0x0134:
            r0 = move-exception
            r8 = r0
            r0 = r8
            java.lang.String r8 = "Error Fetching from ChatBot"
            r0 = r8
        L_0x013a:
            r1.ErrorOccurred(r6, r0)
            goto L_0x0143
        L_0x013e:
            java.lang.String r0 = "Error talking to ChatBot proxy"
            r1.ErrorOccurred(r6, r0)
        L_0x0143:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.ChatBot.performRequest(java.lang.String, java.lang.String, android.graphics.Bitmap):void");
    }

    @SimpleFunction(description = "Ask a question of the Chat Bot using an Image. Successive calls will remember information from earlier in the conversation. Use the \"ResetConversation\" function to reset for a new conversation.")
    public void ConverseWithImage(final String question, Object source) {
        try {
            final Bitmap bitmap = loadImage(source);
            if (bitmap != null) {
                AsynchUtil.runAsynchronously(new Runnable() {
                    public void run() {
                        ChatBot chatBot = ChatBot.this;
                        chatBot.performRequest(chatBot.uuid, question, bitmap);
                    }
                });
            } else {
                this.form.androidUIHandler.postDelayed(new Runnable() {
                    public void run() {
                        ChatBot.this.ErrorOccurred(-1, "Invalid input to ChatBot");
                    }
                }, 0);
                throw new StopBlocksExecution();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to read image", e);
        }
    }

    @SimpleEvent(description = "Event fired when the Chat Bot answers a question.")
    public void GotResponse(final String responseText) {
        this.form.runOnUiThread(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(ChatBot.this, "GotResponse", responseText);
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The \"System\" value given to ChatGPT. It is used to set the tone of a conversation. For example: \"You are a funny person.\"", userVisible = true)
    public String System() {
        return this.system;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "The \"System\" value given to ChatGPT. It is used to set the tone of a conversation. For example: \"You are a funny person.\"", userVisible = true)
    public void System(String system2) {
        this.system = system2;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.ADVANCED, description = "The MIT Access token to use. MIT App Inventor will automatically fill this value in. You should not need to change it.", userVisible = true)
    public void Token(String token2) {
        this.token = token2;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "A ChatGPT API Key. If provided, it will be used instead of the embedded APIKEY in the ChatBot proxy server")
    public void ApiKey(String apikey) {
        this.apiKey = apikey;
    }

    @SimpleProperty
    public String ApiKey() {
        return this.apiKey;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the name of the provider to use. See https://appinv.us/chatbot for the current list of supported providers.", userVisible = true)
    public String Provider() {
        return this.provider;
    }

    @DesignerProperty(defaultValue = "chatgpt", editorType = "string")
    @SimpleProperty(description = "Set the name of the provider to use. See https://appinv.us/chatbot for the current list of supported providers.", userVisible = true)
    public void Provider(String provider2) {
        this.provider = provider2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the name of the model to use. See https://appinv.us/chatbot for the current list of supported models. Leaving this blank will result in the default model set by the provider being used", userVisible = true)
    public String Model() {
        return this.model;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "Set the name of the model to use. See https://appinv.us/chatbot for the current list of supported models. Leaving this blank will result in the default model set by the provider being used", userVisible = true)
    public void Model(String model2) {
        this.model = model2;
    }

    private static String getResponseContent(HttpsURLConnection connection, boolean error) throws IOException {
        InputStreamReader reader;
        StringBuilder sb;
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        if (error) {
            reader = new InputStreamReader(connection.getErrorStream(), encoding);
        } else {
            reader = new InputStreamReader(connection.getInputStream(), encoding);
        }
        try {
            int contentLength = connection.getContentLength();
            if (contentLength != -1) {
                sb = new StringBuilder(contentLength);
            } else {
                sb = new StringBuilder();
            }
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                int read2 = read;
                if (read == -1) {
                    return sb.toString();
                }
                sb.append(buf, 0, read2);
            }
        } finally {
            reader.close();
        }
    }

    @SimpleEvent
    public void ErrorOccurred(final int responseCode, final String responseText) {
        this.form.runOnUiThread(new Runnable() {
            public void run() {
                if (!EventDispatcher.dispatchEvent(ChatBot.this, "ErrorOccurred", Integer.valueOf(responseCode), responseText)) {
                    ChatBot.this.form.dispatchErrorOccurredEvent(ChatBot.this, "ErrorOccurred", ErrorMessages.ERROR_CHATBOT_ERROR, Integer.valueOf(responseCode), responseText);
                }
            }
        });
    }

    private Bitmap loadImage(Object source) throws IOException {
        Bitmap bitmap;
        Log.d(LOG_TAG, "loadImage source = " + source);
        if (source instanceof Canvas) {
            bitmap = ((Canvas) source).getBitmap();
        } else if (source instanceof Image) {
            bitmap = ((BitmapDrawable) ((Image) source).getView().getBackground()).getBitmap();
        } else {
            bitmap = MediaUtil.getBitmapDrawable(this.form, source.toString()).getBitmap();
        }
        if (bitmap == null) {
            return bitmap;
        }
        if (bitmap.getWidth() == this.size && bitmap.getHeight() == this.size) {
            return bitmap;
        }
        int i = this.size;
        return Bitmap.createScaledBitmap(bitmap, i, i, false);
    }

    private synchronized void ensureSslSockFactory() {
        if (this.sslSockFactory == null) {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream caInput = new ByteArrayInputStream(COMODO_ROOT.getBytes("UTF-8"));
                Certificate ca = cf.generateCertificate(caInput);
                caInput.close();
                ByteArrayInputStream caInput2 = new ByteArrayInputStream(COMODO_USRTRUST.getBytes("UTF-8"));
                Certificate inter = cf.generateCertificate(caInput2);
                caInput2.close();
                ByteArrayInputStream caInput3 = new ByteArrayInputStream(MIT_CA.getBytes("UTF-8"));
                Certificate mitca = cf.generateCertificate(caInput3);
                caInput3.close();
                ByteArrayInputStream caInput4 = new ByteArrayInputStream(ISRG_ROOT_X1.getBytes("UTF-8"));
                Certificate isrg = cf.generateCertificate(caInput4);
                caInput4.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load((InputStream) null, (char[]) null);
                int count = 1;
                X509Certificate[] systemCertificates = getSystemCertificates();
                int length = systemCertificates.length;
                for (int i = 0; i < length; i++) {
                    keyStore.setCertificateEntry("root" + count, systemCertificates[i]);
                    count++;
                }
                keyStore.setCertificateEntry("comodo", ca);
                keyStore.setCertificateEntry("inter", inter);
                keyStore.setCertificateEntry("mitca", mitca);
                keyStore.setCertificateEntry("isrg", isrg);
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init((KeyManager[]) null, tmf.getTrustManagers(), (SecureRandom) null);
                this.sslSockFactory = ctx.getSocketFactory();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not setup SSL Trust Store for ChatBot", e);
                throw new YailRuntimeError("Could Not setup SSL Trust Store for ChatBot: ", e.getMessage());
            }
        }
    }

    private X509Certificate[] getSystemCertificates() {
        try {
            TrustManagerFactory otmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = null;
            otmf.init((KeyStore) null);
            return ((X509TrustManager) otmf.getTrustManagers()[0]).getAcceptedIssuers();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Getting System Certificates", e);
            return new X509Certificate[0];
        }
    }
}
