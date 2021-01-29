# AndroidDevTool

A library for easy debugging of the application's internal files, like: shared preferences files, SQLite databases and regular files.

## Usage
```java                    

Intent intent = new Intent("AndroidDevTool");
startActivity(intent);
```


## Features
### Shared Preferences
View, edit and delete the application's shared preferences files.
Clicking on one of the files will display this file's content.
There are 2 buttons for each file - one for editing the file, the other for deleting it.
<br>
<img src = "assets/sp.JPG" height = 600>
<img src = "assets/edit_sp.JPG" height = 600>
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
