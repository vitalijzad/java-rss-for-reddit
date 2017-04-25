package reddit_bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reddit_bot.dto.SubredditDTO;
import reddit_bot.entity.Feed;
import reddit_bot.entity.FeedSubreddit;
import reddit_bot.entity.Link;
import reddit_bot.entity.Subreddit;
import reddit_bot.reddit.RedditSubmitterService;
import reddit_bot.repository.FeedSubredditRepository;
import reddit_bot.repository.FeedsRepository;
import reddit_bot.repository.LinkRepository;
import reddit_bot.repository.SubredditRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SubredditService {

    private final static Logger logger = LoggerFactory.getLogger(SubredditService.class);

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    FeedsRepository feedsRepository;

    @Autowired
    LinkSendingService linkSendingService;

    @Autowired
    RedditSubmitterService redditSubmitterService;

    @Autowired
    FeedSubredditRepository feedSubredditRepository;

    @Autowired
    SubredditRepository subredditRepository;

    public synchronized void sendLinks(Subreddit subreddit){
        Set<Long> feedsSoFar = linkSendingService.feedsSentRecently(subreddit);

        Iterable<Link> links = findLinksToSend(subreddit, feedsSoFar);

        int sentSoFar = linkSendingService.countLinksSentRecently(subreddit);
        logger.info(String.format("%s links sent so far for subreddit %s", sentSoFar, subreddit.getName()));

        if(sentSoFar >= subreddit.getDailyQuota()){
            logger.info(String.format(
                    "Daily quota of %s links has been reached for subreddit %s, skipping.",
                    subreddit.getDailyQuota(), subreddit.getName()));
            return;
        }

        for(Link link : links){

            if(feedsSoFar.contains(link.getFeed().getId())){
                continue;
            }

            if(sentSoFar < subreddit.getDailyQuota()) {
                feedsSoFar.add(link.getFeed().getId());

                FeedSubreddit feedSubreddit = feedSubredditRepository.getFeedSubreddit(subreddit, link.getFeed());
                redditSubmitterService.submitLink(subreddit, link, feedSubreddit.getFlair());
                sentSoFar++;
            }else{
                break;
            }
        }
    }

    public Iterable<Link> findLinksToSend(Subreddit subreddit, Set<Long> feedsSoFar){
        Iterable<Feed> feeds = feedsRepository.findBySubreddit(subreddit);
        List<Long> ids = new ArrayList<Long>();
        for(Feed feed : feeds){
            if(!feedsSoFar.contains(feed.getId())) {
                ids.add(feed.getId());
            }
        }

        if(ids.size() == 0){
            return new ArrayList<Link>();
        }

        return linkRepository.findByFeedIds(ids, subreddit);
    }

    public Iterable<SubredditDTO> subreddits(){
        List<SubredditDTO> dtos = new ArrayList<>();
        for(Subreddit subreddit : subredditRepository.findAll()){
            SubredditDTO dto = new SubredditDTO(subreddit);
            dtos.add(dto);
        }
        return dtos;
    }

}
