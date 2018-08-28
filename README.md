# <img src="/app/src/main/res/mipmap-mdpi/ic_launcher.png" width="40" height="40" alt="HearthCards"> HearthCards
 Project made as Capstone Project at Udacity Android Nanodegree.

__________

### Project Features

* All collectible cards from **Hearthstone**
* Cards details and images with different languages
* Copyable card details
* Widget with random card flavor which changes every 24h
* Offline after first downloading data
* No need to update app after new game patch, just launch the app and redownload the data
__________

### Screenshots

<img src="/screenshots/01.png" width="280" height="591" alt="First screenshot"> <img src="/screenshots/02.png" width="280" height="591" alt="Second screenshot"> <img src="/screenshots/03.png" width="280" height="591" alt="Third screenshot">

__________

## How to compile?

To compile the project you need to add **Firebase config file**, **Hearthstone API key** and **AdMob ids**. More information below.

### Firebase
You have to add your app to Firebase console. You can do it [here](https://console.firebase.google.com/). Then open Project Settings and download your **google-services.json** file and put it into `[PROJECT_NAME]/app/google-services.json`.

### API and AdMob 

#### API Key
You have to add your own **Hearthstone API key**. You can get it from [here](http://hearthstoneapi.com/).

#### AdMob Ids
You have to add your own **AdMob app id** and **AdMob banner ad unit id**. You can get it from [here](https://www.google.com/admob/), delete the ads from the app or use these test values:
```
ADMOB_HEARTHCARDS_ID="ca-app-pub-3940256099942544~3347511713"
ADMOB_HEARTHCARDS_BOTTOM_BANNER_ID="ca-app-pub-3940256099942544/6300978111"
```

#### Usage
Go to `[USER_HOME]/.gradle/gradle.properties` or to `[PROJECT_NAME]/gradle.properties`. If you do not have `gradle.properties` file, you have to create one.

Then add the following lines:
```
HEARTHSTONE_API_KEY="key"
ADMOB_HEARTHCARDS_ID="app_id"
ADMOB_HEARTHCARDS_BOTTOM_BANNER_ID="banner_ad_unit_id"
```
