package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.androidmanifest.ActionElement;
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement;
import com.google.appinventor.components.annotations.androidmanifest.IntentFilterElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@DesignerComponent(category = ComponentCategory.INTERNAL, description = "A non-visible component that enables communication with <a href=\"http://www.twitter.com\" target=\"_blank\">Twitter</a>. Once a user has logged into their Twitter account (and the authorization has been confirmed successful by the <code>IsAuthorized</code> event), many more operations are available:<ul><li> Searching Twitter for tweets or labels (<code>SearchTwitter</code>)</li>\n<li> Sending a Tweet (<code>Tweet</code>)     </li>\n<li> Sending a Tweet with an Image (<code>TweetWithImage</code>)     </li>\n<li> Directing a message to a specific user      (<code>DirectMessage</code>)</li>\n <li> Receiving the most recent messages directed to the logged-in user      (<code>RequestDirectMessages</code>)</li>\n <li> Following a specific user (<code>Follow</code>)</li>\n<li> Ceasing to follow a specific user (<code>StopFollowing</code>)</li>\n<li> Getting a list of users following the logged-in user      (<code>RequestFollowers</code>)</li>\n <li> Getting the most recent messages of users followed by the      logged-in user (<code>RequestFriendTimeline</code>)</li>\n <li> Getting the most recent mentions of the logged-in user      (<code>RequestMentions</code>)</li></ul></p>\n <p>You must obtain a Consumer Key and Consumer Secret for Twitter authorization  specific to your app from http://twitter.com/oauth_clients/new", iconName = "images/twitter.png", nonVisible = true, version = 4)
@UsesLibraries(libraries = "twitter4j.jar,twitter4jmedia.jar")
@SimpleObject
@UsesActivities(activities = {@ActivityElement(configChanges = "orientation|keyboardHidden", intentFilters = {@IntentFilterElement(actionElements = {@ActionElement(name = "android.intent.action.MAIN")})}, name = "com.google.appinventor.components.runtime.WebViewActivity", screenOrientation = "behind")})
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class Twitter extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String ACCESS_SECRET_TAG = "TwitterOauthAccessSecret";
    private static final String ACCESS_TOKEN_TAG = "TwitterOauthAccessToken";
    private static final String CALLBACK_URL = "appinventor://twitter";
    private static final String MAX_CHARACTERS = "160";
    private static final String MAX_MENTIONS_RETURNED = "20";
    private static final String URL_HOST = "twitter";
    /* access modifiers changed from: private */
    public static final String WEBVIEW_ACTIVITY_CLASS = WebViewActivity.class.getName();
    private String TwitPic_API_Key = "";
    /* access modifiers changed from: private */
    public AccessToken accessToken;
    private String consumerKey = "";
    private String consumerSecret = "";
    /* access modifiers changed from: private */
    public final ComponentContainer container;
    /* access modifiers changed from: private */
    public final List<String> directMessages;
    /* access modifiers changed from: private */
    public final List<String> followers;
    /* access modifiers changed from: private */
    public final Handler handler;
    /* access modifiers changed from: private */
    public final List<String> mentions;
    /* access modifiers changed from: private */
    public final int requestCode;
    /* access modifiers changed from: private */
    public RequestToken requestToken;
    /* access modifiers changed from: private */
    public final List<String> searchResults;
    private final SharedPreferences sharedPreferences;
    /* access modifiers changed from: private */
    public final List<List<String>> timeline;
    /* access modifiers changed from: private */
    public twitter4j.Twitter twitter;
    /* access modifiers changed from: private */
    public String userName = "";

    public Twitter(ComponentContainer container2) {
        super(container2.$form());
        this.container = container2;
        this.handler = new Handler();
        this.mentions = new ArrayList();
        this.followers = new ArrayList();
        this.timeline = new ArrayList();
        this.directMessages = new ArrayList();
        this.searchResults = new ArrayList();
        this.sharedPreferences = container2.$context().getSharedPreferences("Twitter", 0);
        this.accessToken = retrieveAccessToken();
        this.requestCode = this.form.registerForActivityResult(this);
    }

    @SimpleFunction(description = "Twitter's API no longer supports login via username and password. Use the Authorize call instead.", userVisible = false)
    public void Login(String username, String password) {
        this.form.dispatchErrorOccurredEvent(this, "Login", ErrorMessages.ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION, new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The user name of the authorized user. Empty if there is no authorized user.")
    public String Username() {
        return this.userName;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerKey() {
        return this.consumerKey;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The the consumer key to be used when authorizing with Twitter via OAuth.")
    public void ConsumerKey(String consumerKey2) {
        this.consumerKey = consumerKey2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerSecret() {
        return this.consumerSecret;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "The consumer secret to be used when authorizing with Twitter via OAuth")
    public void ConsumerSecret(String consumerSecret2) {
        this.consumerSecret = consumerSecret2;
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String TwitPic_API_Key() {
        return this.TwitPic_API_Key;
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The API Key for image uploading, provided by TwitPic.")
    public void TwitPic_API_Key(String TwitPic_API_Key2) {
        this.TwitPic_API_Key = TwitPic_API_Key2;
    }

    @SimpleEvent(description = "This event is raised after the program calls <code>Authorize</code> if the authorization was successful.  It is also called after a call to <code>CheckAuthorized</code> if we already have a valid access token. After this event has been raised, any other method for this component can be called.")
    public void IsAuthorized() {
        EventDispatcher.dispatchEvent(this, "IsAuthorized", new Object[0]);
    }

    @SimpleFunction(description = "Redirects user to login to Twitter via the Web browser using the OAuth protocol if we don't already have authorization.")
    public void Authorize() {
        if (this.consumerKey.length() == 0 || this.consumerSecret.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, new Object[0]);
            return;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        final String myConsumerKey = this.consumerKey;
        final String myConsumerSecret = this.consumerSecret;
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                if (Twitter.this.checkAccessToken(myConsumerKey, myConsumerSecret)) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                    return;
                }
                try {
                    Twitter.this.twitter.setOAuthConsumer(myConsumerKey, myConsumerSecret);
                    RequestToken newRequestToken = Twitter.this.twitter.getOAuthRequestToken(Twitter.CALLBACK_URL);
                    String authURL = newRequestToken.getAuthorizationURL();
                    Twitter.this.requestToken = newRequestToken;
                    Intent browserIntent = new Intent("android.intent.action.MAIN", Uri.parse(authURL));
                    browserIntent.setClassName(Twitter.this.container.$context(), Twitter.WEBVIEW_ACTIVITY_CLASS);
                    Twitter.this.container.$context().startActivityForResult(browserIntent, Twitter.this.requestCode);
                } catch (TwitterException e) {
                    Log.i("Twitter", "Got exception: " + e.getMessage());
                    e.printStackTrace();
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_EXCEPTION, e.getMessage());
                    Twitter.this.DeAuthorize();
                } catch (IllegalStateException e2) {
                    Log.e("Twitter", "OAuthConsumer was already set: launch IsAuthorized()");
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                }
            }
        });
    }

    @SimpleFunction(description = "Checks whether we already have access, and if so, causes IsAuthorized event handler to be called.")
    public void CheckAuthorized() {
        final String myConsumerKey = this.consumerKey;
        final String myConsumerSecret = this.consumerSecret;
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                if (Twitter.this.checkAccessToken(myConsumerKey, myConsumerSecret)) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                }
            }
        });
    }

    public void resultReturned(int requestCode2, int resultCode, Intent data) {
        Log.i("Twitter", "Got result " + resultCode);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Log.i("Twitter", "Intent URI: " + uri.toString());
                final String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                if (this.twitter == null) {
                    Log.e("Twitter", "twitter field is unexpectedly null");
                    this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, "internal error: can't access Twitter library");
                    new RuntimeException().printStackTrace();
                }
                if (this.requestToken == null || oauthVerifier == null || oauthVerifier.length() == 0) {
                    this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED, new Object[0]);
                    deAuthorize();
                    return;
                }
                AsynchUtil.runAsynchronously(new Runnable() {
                    public void run() {
                        try {
                            AccessToken resultAccessToken = Twitter.this.twitter.getOAuthAccessToken(Twitter.this.requestToken, oauthVerifier);
                            Twitter.this.accessToken = resultAccessToken;
                            Twitter twitter = Twitter.this;
                            twitter.userName = twitter.accessToken.getScreenName();
                            Twitter.this.saveAccessToken(resultAccessToken);
                            Twitter.this.handler.post(new Runnable() {
                                public void run() {
                                    Twitter.this.IsAuthorized();
                                }
                            });
                        } catch (TwitterException e) {
                            Log.e("Twitter", "Got exception: " + e.getMessage());
                            e.printStackTrace();
                            Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, e.getMessage());
                            Twitter.this.deAuthorize();
                        }
                    }
                });
                return;
            }
            Log.e("Twitter", "uri returned from WebView activity was unexpectedly null");
            deAuthorize();
            return;
        }
        Log.e("Twitter", "intent returned from WebView activity was unexpectedly null");
        deAuthorize();
    }

    /* access modifiers changed from: private */
    public void saveAccessToken(AccessToken accessToken2) {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        if (accessToken2 == null) {
            sharedPrefsEditor.remove(ACCESS_TOKEN_TAG);
            sharedPrefsEditor.remove(ACCESS_SECRET_TAG);
        } else {
            sharedPrefsEditor.putString(ACCESS_TOKEN_TAG, accessToken2.getToken());
            sharedPrefsEditor.putString(ACCESS_SECRET_TAG, accessToken2.getTokenSecret());
        }
        sharedPrefsEditor.commit();
    }

    private AccessToken retrieveAccessToken() {
        String token = this.sharedPreferences.getString(ACCESS_TOKEN_TAG, "");
        String secret = this.sharedPreferences.getString(ACCESS_SECRET_TAG, "");
        if (token.length() == 0 || secret.length() == 0) {
            return null;
        }
        return new AccessToken(token, secret);
    }

    @SimpleFunction(description = "Removes Twitter authorization from this running app instance")
    public void DeAuthorize() {
        deAuthorize();
    }

    /* access modifiers changed from: private */
    public void deAuthorize() {
        this.requestToken = null;
        this.accessToken = null;
        this.userName = "";
        twitter4j.Twitter oldTwitter = this.twitter;
        this.twitter = null;
        saveAccessToken((AccessToken) null);
        if (oldTwitter != null) {
            oldTwitter.setOAuthAccessToken((AccessToken) null);
        }
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text, which will be trimmed if it exceeds 160 characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void Tweet(final String status) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    Twitter.this.twitter.updateStatus(status);
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
                }
            }
        });
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text and a path to the image to be uploaded, which will be trimmed if it exceeds 160 characters. If an image is not found or invalid, only the text will be tweeted.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void TweetWithImage(final String status, final String imagePath) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    String cleanImagePath = imagePath;
                    if (cleanImagePath.startsWith("file://")) {
                        cleanImagePath = imagePath.replace("file://", "");
                    }
                    File imageFilePath = new File(cleanImagePath);
                    if (imageFilePath.exists()) {
                        StatusUpdate theTweet = new StatusUpdate(status);
                        theTweet.setMedia(imageFilePath);
                        Twitter.this.twitter.updateStatus(theTweet);
                        return;
                    }
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", 315, new Object[0]);
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
                }
            }
        });
    }

    @SimpleFunction(description = "Requests the 20 most recent mentions of the logged-in user.  When the mentions have been retrieved, the system will raise the <code>MentionsReceived</code> event and set the <code>Mentions</code> property to the list of mentions.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestMentions() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<Status> replies = Collections.emptyList();

            public void run() {
                Handler r0;
                AnonymousClass1 r1;
                try {
                    this.replies = Twitter.this.twitter.getMentionsTimeline();
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.mentions.clear();
                            for (Status status : AnonymousClass6.this.replies) {
                                List r2 = Twitter.this.mentions;
                                String screenName = status.getUser().getScreenName();
                                r2.add(screenName + " " + status.getText());
                            }
                            Twitter.this.MentionsReceived(Twitter.this.mentions);
                        }
                    };
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, e.getMessage());
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.mentions.clear();
                            for (Status status : AnonymousClass6.this.replies) {
                                List r2 = Twitter.this.mentions;
                                String screenName = status.getUser().getScreenName();
                                r2.add(screenName + " " + status.getText());
                            }
                            Twitter.this.MentionsReceived(Twitter.this.mentions);
                        }
                    };
                } catch (Throwable th) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.mentions.clear();
                            for (Status status : AnonymousClass6.this.replies) {
                                List r2 = Twitter.this.mentions;
                                String screenName = status.getUser().getScreenName();
                                r2.add(screenName + " " + status.getText());
                            }
                            Twitter.this.MentionsReceived(Twitter.this.mentions);
                        }
                    });
                    throw th;
                }
                r0.post(r1);
            }
        });
    }

    @SimpleEvent(description = "This event is raised when the mentions of the logged-in user requested through <code>RequestMentions</code> have been retrieved.  A list of the mentions can then be found in the <code>mentions</code> parameter or the <code>Mentions</code> property.")
    public void MentionsReceived(List<String> mentions2) {
        EventDispatcher.dispatchEvent(this, "MentionsReceived", mentions2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of mentions of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestMentions</code> method.</li> <li> Wait for the <code>MentionsReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of mentions (and will maintain its value until any subsequent calls to <code>RequestMentions</code>).")
    public List<String> Mentions() {
        return this.mentions;
    }

    @SimpleFunction
    public void RequestFollowers() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<User> friends = new ArrayList();

            public void run() {
                Handler r0;
                AnonymousClass1 r1;
                try {
                    for (long id : Twitter.this.twitter.getFollowersIDs(-1).getIDs()) {
                        this.friends.add(Twitter.this.twitter.showUser(id));
                    }
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.followers.clear();
                            for (User user : AnonymousClass7.this.friends) {
                                Twitter.this.followers.add(user.getName());
                            }
                            Twitter.this.FollowersReceived(Twitter.this.followers);
                        }
                    };
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, e.getMessage());
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.followers.clear();
                            for (User user : AnonymousClass7.this.friends) {
                                Twitter.this.followers.add(user.getName());
                            }
                            Twitter.this.FollowersReceived(Twitter.this.followers);
                        }
                    };
                } catch (Throwable th) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.followers.clear();
                            for (User user : AnonymousClass7.this.friends) {
                                Twitter.this.followers.add(user.getName());
                            }
                            Twitter.this.FollowersReceived(Twitter.this.followers);
                        }
                    });
                    throw th;
                }
                r0.post(r1);
            }
        });
    }

    @SimpleEvent(description = "This event is raised when all of the followers of the logged-in user requested through <code>RequestFollowers</code> have been retrieved. A list of the followers can then be found in the <code>followers</code> parameter or the <code>Followers</code> property.")
    public void FollowersReceived(List<String> followers2) {
        EventDispatcher.dispatchEvent(this, "FollowersReceived", followers2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the followers of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestFollowers</code> method.</li> <li> Wait for the <code>FollowersReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of followers (and maintain its value until any subsequent call to <code>RequestFollowers</code>).")
    public List<String> Followers() {
        return this.followers;
    }

    @SimpleFunction(description = "Requests the 20 most recent direct messages sent to the logged-in user.  When the messages have been retrieved, the system will raise the <code>DirectMessagesReceived</code> event and set the <code>DirectMessages</code> property to the list of messages.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestDirectMessages() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<DirectMessage> messages = Collections.emptyList();

            public void run() {
                Handler r0;
                AnonymousClass1 r1;
                try {
                    this.messages = Twitter.this.twitter.getDirectMessages();
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.directMessages.clear();
                            for (DirectMessage message : AnonymousClass8.this.messages) {
                                List r2 = Twitter.this.directMessages;
                                String senderScreenName = message.getSenderScreenName();
                                r2.add(senderScreenName + " " + message.getText());
                            }
                            Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                        }
                    };
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, e.getMessage());
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.directMessages.clear();
                            for (DirectMessage message : AnonymousClass8.this.messages) {
                                List r2 = Twitter.this.directMessages;
                                String senderScreenName = message.getSenderScreenName();
                                r2.add(senderScreenName + " " + message.getText());
                            }
                            Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                        }
                    };
                } catch (Throwable th) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.directMessages.clear();
                            for (DirectMessage message : AnonymousClass8.this.messages) {
                                List r2 = Twitter.this.directMessages;
                                String senderScreenName = message.getSenderScreenName();
                                r2.add(senderScreenName + " " + message.getText());
                            }
                            Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                        }
                    });
                    throw th;
                }
                r0.post(r1);
            }
        });
    }

    @SimpleEvent(description = "This event is raised when the recent messages requested through <code>RequestDirectMessages</code> have been retrieved. A list of the messages can then be found in the <code>messages</code> parameter or the <code>Messages</code> property.")
    public void DirectMessagesReceived(List<String> messages) {
        EventDispatcher.dispatchEvent(this, "DirectMessagesReceived", messages);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the most recent messages mentioning the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>Authorized</code> event.</li> <li> Call the <code>RequestDirectMessages</code> method.</li> <li> Wait for the <code>DirectMessagesReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of direct messages retrieved (and maintain that value until any subsequent call to <code>RequestDirectMessages</code>).")
    public List<String> DirectMessages() {
        return this.directMessages;
    }

    @SimpleFunction(description = "This sends a direct (private) message to the specified user.  The message will be trimmed if it exceeds 160characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void DirectMessage(final String user, final String message) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    Twitter.this.twitter.sendDirectMessage(user, message);
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, e.getMessage());
                }
            }
        });
    }

    @SimpleFunction
    public void Follow(final String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    Twitter.this.twitter.createFriendship(user);
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, e.getMessage());
                }
            }
        });
    }

    @SimpleFunction
    public void StopFollowing(final String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    Twitter.this.twitter.destroyFriendship(user);
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, e.getMessage());
                }
            }
        });
    }

    @SimpleFunction
    public void RequestFriendTimeline() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<Status> messages = Collections.emptyList();

            public void run() {
                Handler r0;
                AnonymousClass1 r1;
                try {
                    this.messages = Twitter.this.twitter.getHomeTimeline();
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.timeline.clear();
                            for (Status message : AnonymousClass12.this.messages) {
                                List<String> status = new ArrayList<>();
                                status.add(message.getUser().getScreenName());
                                status.add(message.getText());
                                Twitter.this.timeline.add(status);
                            }
                            Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                        }
                    };
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, e.getMessage());
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.timeline.clear();
                            for (Status message : AnonymousClass12.this.messages) {
                                List<String> status = new ArrayList<>();
                                status.add(message.getUser().getScreenName());
                                status.add(message.getText());
                                Twitter.this.timeline.add(status);
                            }
                            Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                        }
                    };
                } catch (Throwable th) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.timeline.clear();
                            for (Status message : AnonymousClass12.this.messages) {
                                List<String> status = new ArrayList<>();
                                status.add(message.getUser().getScreenName());
                                status.add(message.getText());
                                Twitter.this.timeline.add(status);
                            }
                            Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                        }
                    });
                    throw th;
                }
                r0.post(r1);
            }
        });
    }

    @SimpleEvent(description = "This event is raised when the messages requested through <code>RequestFriendTimeline</code> have been retrieved. The <code>timeline</code> parameter and the <code>Timeline</code> property will contain a list of lists, where each sub-list contains a status update of the form (username message)")
    public void FriendTimelineReceived(List<List<String>> timeline2) {
        EventDispatcher.dispatchEvent(this, "FriendTimelineReceived", timeline2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains the 20 most recent messages of users being followed.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Specify users to follow with one or more calls to the <code>Follow</code> method.</li> <li> Call the <code>RequestFriendTimeline</code> method.</li> <li> Wait for the <code>FriendTimelineReceived</code> event.</li> </ol>\nThe value of this property will then be set to the list of messages (and maintain its value until any subsequent call to <code>RequestFriendTimeline</code>.")
    public List<List<String>> FriendTimeline() {
        return this.timeline;
    }

    @SimpleFunction(description = "This searches Twitter for the given String query.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void SearchTwitter(final String query) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<Status> tweets = Collections.emptyList();

            public void run() {
                Handler r0;
                AnonymousClass1 r1;
                try {
                    this.tweets = Twitter.this.twitter.search(new Query(query)).getTweets();
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.searchResults.clear();
                            for (Status tweet : AnonymousClass13.this.tweets) {
                                List r2 = Twitter.this.searchResults;
                                String name = tweet.getUser().getName();
                                r2.add(name + " " + tweet.getText());
                            }
                            Twitter.this.SearchSuccessful(Twitter.this.searchResults);
                        }
                    };
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, e.getMessage());
                    r0 = Twitter.this.handler;
                    r1 = new Runnable() {
                        public void run() {
                            Twitter.this.searchResults.clear();
                            for (Status tweet : AnonymousClass13.this.tweets) {
                                List r2 = Twitter.this.searchResults;
                                String name = tweet.getUser().getName();
                                r2.add(name + " " + tweet.getText());
                            }
                            Twitter.this.SearchSuccessful(Twitter.this.searchResults);
                        }
                    };
                } catch (Throwable th) {
                    Twitter.this.handler.post(new Runnable() {
                        public void run() {
                            Twitter.this.searchResults.clear();
                            for (Status tweet : AnonymousClass13.this.tweets) {
                                List r2 = Twitter.this.searchResults;
                                String name = tweet.getUser().getName();
                                r2.add(name + " " + tweet.getText());
                            }
                            Twitter.this.SearchSuccessful(Twitter.this.searchResults);
                        }
                    });
                    throw th;
                }
                r0.post(r1);
            }
        });
    }

    @SimpleEvent(description = "This event is raised when the results of the search requested through <code>SearchSuccessful</code> have been retrieved. A list of the results can then be found in the <code>results</code> parameter or the <code>Results</code> property.")
    public void SearchSuccessful(List<String> searchResults2) {
        EventDispatcher.dispatchEvent(this, "SearchSuccessful", searchResults2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property, which is initially empty, is set to a list of search results after the program: <ol><li>Calls the <code>SearchTwitter</code> method.</li> <li>Waits for the <code>SearchSuccessful</code> event.</li></ol>\nThe value of the property will then be the same as the parameter to <code>SearchSuccessful</code>.  Note that it is not necessary to call the <code>Authorize</code> method before calling <code>SearchTwitter</code>.")
    public List<String> SearchResults() {
        return this.searchResults;
    }

    /* access modifiers changed from: private */
    public boolean checkAccessToken(String myConsumerKey, String myConsumerSecret) {
        AccessToken retrieveAccessToken = retrieveAccessToken();
        this.accessToken = retrieveAccessToken;
        if (retrieveAccessToken == null) {
            return false;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        try {
            this.twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
            this.twitter.setOAuthAccessToken(this.accessToken);
        } catch (IllegalStateException e) {
        }
        if (this.userName.trim().length() != 0) {
            return true;
        }
        try {
            this.userName = this.twitter.verifyCredentials().getScreenName();
            return true;
        } catch (TwitterException e2) {
            deAuthorize();
            return false;
        }
    }
}
