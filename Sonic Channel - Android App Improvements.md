**Sonic Channel**  
**Android App Improvements**

Ever since Version 1.0 was released in June of 2024, the Sonic Channel Android App has gone through massive improvements. In this documentation, we’re gonna document the history of the app, from Version 1.0, all the way to Version 1.7, the final version of the app.

**Version 1.0**  
Version 1.0 was the first version of the app, and a new way to experience Sonic Channel on your Android device outside of your web browser app.

It came with a rather bare-bones UI, with just a switch that could be flicked to go to home. A bug came with the switch component that was fixed in later releases of the app. This bug meant that even if you disabled the switch, you would go back home.

And worst of all, you couldn’t navigate through browser history like you can in later releases, so it would obviously be annoying for many users.

**Version 1.1**  
Version 1.1 didn’t really fix anything other than a bug with the app. Because I simply forgot what was changed, I can’t tell you about this version in detail. However I do know that not much changed.

**Version 1.2**  
Version 1.2 again didn’t change much. All core functionalities of the app were the same, with no obvious changes. Maybe another bug fix or two.

**Version 1.3**  
Version 1.3 did something rather convenient for users, it fixed the bug regarding the switch component. However this version did something rather nasty. It closed the whole app if you ever got outside the news website by any means. A patch needed to be issued to remove this badly-thought-out feature.

**Version 1.4**  
Thinking an iOS app would be available (even though there isn’t, there is still a rather easy way to run the app on iOS, view the guide [here](https://docs.google.com/document/d/1fgwrqEuuWo6Kuv1NomYp334UXKJdI82XCsFaRmh5X6o/edit?usp=sharing)), the URL of the mobile version of the website was changed, so users couldn’t notice that the app was never properly intended for iOS devices. The end of the URL was changed from “snc-apk” to “snc-mobile”.

This version was badly coded, so even though you could still visit the site, it was impossible to navigate. A patch was immediately issued.

**Version 1.5**  
This was the most patched version of the app. The “Write HTML” feature in this version didn’t work properly on phones with low storage. The testing device I had was on very low storage, and unless I dug through my closets, I wouldn’t be able to find older devices of mine I kept with more storage. I thought this was a problem with my code. Turns out it was a problem with phones on very low storage (I wasted a lot of time, but then again I didn’t because I wasn’t very busy at the time).

**Version 1.6**  
Finally, the “Write HTML” feature was finalized and only had one issue with phones running with some storage. The issue was that if you installed the app, created an HTML, uninstalled, then reinstalled the app, it won’t be able to create an HTML unless you delete the one you created before you uninstalled, then reinstalled the app.

This was the last version to remain soundless, because in buggy test releases featuring SFX of 1.6, and the final, working version, 1.7, SFX was added and is from a royalty-free SFX website.

**Version 1.7 (Regular and AutoClean)**  
OK, Version 1.7 added some UI changes regarding colors, but the AutoClean version automatically cleaned cache/cookies from the WebViewer component for how long you specified in minutes. Before a patch for the AutoClean release, it was always set to 5 minutes, and could never be changed without digging into the app itself. 

The patch made it changeable.

**The End**

