# NYC Schools

## This branch contains work done after my 48 hour challenge that was completed on the weekend of (7/20/2019). In this branch I am working on some of the issues I didn't have time to address.

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

* This app is structured with a Clean MVP architecture, as can be seen in the rough sketch I have provided below; With all dependencies pointing inward towards the domain package.

* Dagger 2 was used for dependency injection.

* OkHttp was used as the web client.

* RxJava for transformation and supply of data.

* SQLite for caching all requests. Once the list of schools for a borough is downloaded the app will no longer hit the api for this borough again. Same with the SAT data for a school.

* There are also some unit tests to check that I am properly receiving and parsing the data from the api.


### What's left

<p>
    With more time there is obviously more things that I
    would have liked to add in but couldn't.
</p>

- [ ] Handling multiple screen sizes. (This app was developed using a pixel 1) No tablet resources were made.

- [x] Handling orientation change. As it stands when the user changes to landscape the state of the list is lost and thus reloads as new. With more time, this would be an issue I could definitely fix.

- [ ] Displaying more interesting data and creating more Activities. Along with more styling.

- [ ] Storage and security of auth token for API.

- [ ] More tests.

<p>
    I have tagged this project in four working stages of development, inorder
    to show the progression of the app.
<p/>

![Architecture drawing](https://res.cloudinary.com/dgrwnfwr2/image/upload/v1563769706/nycschoolsproject_cu492w.jpg)