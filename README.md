# Hearthstone Cards

![](/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

### Project Features

* All collectible cards from **Hearthstone**
* Cards details and images with different languages
* Copyable card details
* Widget with random card flavor which changes every 24h
* Offline after first downloading data
* No need to update app after new game patch, just launch the app and redownload the data

### Screenshots

<img src="/screenshots/01.jpg" width="280" height="591" alt="First screenshot"> <img src="/screenshots/02.jpg" width="280" height="591" alt="Second screenshot"> <img src="/screenshots/03.jpg" width="280" height="591" alt="Third screenshot">

## How to compile?

To compile the project you need to add **Firebase config file** and **Hearthstone API key**. More information below.

### Firebase
You have to add your app to Firebase console. You can do it [here](https://console.firebase.google.com/). Then open Project Settings and download your **google-services.json** file and put it into `[PROJECT_NAME]/app/google-services.json`.

### API Key
You have to add your own **Hearthstone API key**. You can get it from [here](http://hearthstoneapi.com/).

#### Usage
Go to `[USER_HOME]/.gradle/gradle.properties` or to `[PROJECT_NAME]/gradle.properties`. If you do not have `gradle.properties` file, you have to create one.

Then add the following lines:
```
HEARTHSTONE_API_KEY="key"
```

## License
```
 Copyright 2018 Wojciech Kryg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
