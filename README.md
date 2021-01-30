# AndroidDevTool

[![](https://jitpack.io/v/alonlubinski/AndroidDevTool.svg)](https://jitpack.io/#alonlubinski/AndroidDevTool)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Android library for easy debugging of the application's internal files, like: shared preferences files, SQLite databases and regular files.

## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
	implementation 'com.github.alonlubinski:AndroidDevTool:1.00.02'
}
```

## Usage
```java                    

Intent intent = new Intent(getApplicationContext(), AndroidDevToolActivity.class);
startActivity(intent);
```

Additionally, the theme field in the manifest file should not include an action bar. Please use something like that:
```java
android:theme="@style/Theme.AppCompat.Light.NoActionBar"
```


## Features
### Shared Preferences
View, edit and delete the application's shared preferences files.
Clicking on one of the files will display this file's content.
There are 2 buttons for each file - one for editing the file, the other for deleting it.
<br>
<img src = "assets/sp.JPG" height = 600>
<img src = "assets/sp_edit.JPG" height = 600>
<br><br>
### Databases
View the application's internal databases.
Clicking on one of the databases will open a list of all the tables that are stored in that database.
Clicking on one of the tables will display this table data.
<br>
<img src = "assets/db.JPG" height = 600>
<img src = "assets/table_db.JPG" height = 600>
<br><br>
### Files
View the folders and files in the application's internal storage.
Clicking on one of the folders will display this folder content.
Clicking on one of the files will display this file's information.
<br>
<img src = "assets/files.JPG" height = 600>
<img src = "assets/file_info.JPG" height = 600>
<img src = "assets/folder_content.JPG" height = 600>
<br><br>

## Credit
Icons by Icons8.

## License

    Copyright 2021 Alon Lubinski

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
