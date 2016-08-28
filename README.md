# Java RSS to Reddit

A project to read XML feeds and publish them on reddit.

It does not flood, because you can configure the number of links to post daily for every subreddit.

It posts links from each feed at most once per subreddit.

It also has a Quartz scheduler.

WARNING: you have to use this version of JRAW: https://github.com/vitalijzad/JRAW otherwise, it won't compile.

## Similar projects:

https://github.com/Ferocit/RedditRssBot

https://github.com/eyalgo/rss-reader