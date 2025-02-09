# NYC Schools
<p>
    Original done for an interview coding challenge, I now use 
    this project to practice using technologies in Android.
</p>

## User overview
<p>
    This project is a small Android Application which displays
    SAT score data for HighSchools in NYC. The app initially
    loads a list of the five boroughs which the user can click
    to expand the list, which then displays the schools
    located in that borough. The user can then select a school
    from the list to load the average 2012 SAT scores for that
    school. They will also find a clickable link to the
    school's website.
</p>

## Technical Details

* This app is structured with a MVVM architecture.

* Dagger 2 was used for dependency injection.

* OkHttp was used as the web client.

* RxJava for transformation and supply of data.

* SQLite for caching all requests. Once the list of schools for a borough is downloaded the app will no longer hit the api for this borough again. Same with the SAT data for a school.

* There are also some unit tests to check that I am properly receiving and parsing the data from the api.

<p>
    I have tagged this project in working stages of development, inorder
    to show the progression of the app.
<p/>
