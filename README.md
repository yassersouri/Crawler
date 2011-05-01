General Info
============
This is a java eclipse project that retrieves news articles from [Mehrnews](http://www.mehrnews.ir/)'s website.
While doing so we saw that the output generated contains characters like `&nbsp;`, `&zwnj;` and `&quot;`. So we built it the functionality to remove these characters and replace them with appropriate persian characters.

Usage
======

Using the crawler
-----------------
You can either compile from source or use the built in jar file in [jar directory](https://github.com/yassersouri/Crawler/tree/master/jar).

The crawler uses a sqlite database for visited pages, and a directory for saving logs and news articles.
The default database address is ["D:\crawler\crawler.db"](https://github.com/yassersouri/Crawler/blob/master/src/Main.java#L11). Also the default saving path is ["D:\crawler"](https://github.com/yassersouri/Crawler/blob/master/src/Main.java#L12).

For our own reasons we wanted the crawler to only download news articles from ["01:00 AM"](https://github.com/yassersouri/Crawler/blob/master/src/Main.java#L15) until ["06:00 AM"](https://github.com/yassersouri/Crawler/blob/master/src/Main.java#L16). These times are the defaults, but you can pass them as arguments. If you want to totally disable the time constrains, you must edit the ["timeIsOK()"](https://github.com/yassersouri/Crawler/blob/master/src/Main.java#L220) function to always return true.

##Running the crawler
	java -jar crawler.jar 01:00 06:00
By running the above command when in Crawler\jar directory, you can run the crawler. Notice that the first argument is the _start time_ and the second is the _finish time_. Also both of them are optional. After running the crawler, it will prompt you to choose your database file and saving path. You can press `esc` and use the default pathes.(mentioned above)

##dependencies
==============
The only dependency is Java JDK 1.6. Other libraries are included.