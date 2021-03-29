package reddit_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reddit_bot.reddit.RedditSubmitterService;

@Component
public class ScheduledTasks {

    @Autowired
    RedditSubmitterService redditSubmitterService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    /*
    @Scheduled(cron = "0 45 6 1/1 * ?")
    public void updateFeeds(){
        linkService.updateFeeds();
    }*/

    @Scheduled(cron = "0 0 7 1/1 * ?")
    public void send(){
        redditSubmitterService.send();
    }

}
