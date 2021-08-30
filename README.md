# About Repo App

Repo App is an simple Android application, which can fetch info about github repositories. It is written in Kotlin using MVVM, Clean Architecture and Single Activity Pattern.

### Repo App has 3 pages, allowing users to:
1. Search for other users' github repositories displayed as an infinite scrollable list
2. View repository details and star favorite ones, saving them in a local database
3. View starred repositories

### Getting Started

As usual, get started by cloning the project to your local machine:

```
$ git@github.com:LevanGelashvili/Repository-App.git
```
Simply open and run the project in Android Studio

### Android Version Targeting

Repo App's minimum SDK support is Android API 23 (Marshmallow)

### Technologies Used

* Coroutines
* Retrofit2 / OkHttp / Moshi
* Hilt
* Jetpack Navigation
* Jetpack Room

### Api Used
<https://api.github.com/>