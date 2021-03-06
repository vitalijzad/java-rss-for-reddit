package reddit_bot.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="links")
public class Link implements java.io.Serializable {

    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.TABLE)
    private long id;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="feed_id")
    private Feed feed;
    @Column(name="title", nullable=false)
    private String title;
    @Column(name="url", nullable=false)
    private String url;
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name="publication_date", nullable=false)
    private LocalDateTime publicationDate;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="link")
    private Set<LinkSending> linkSendings = new HashSet<LinkSending>(0);

    public Link() {
    }


    public Link(long id, String title, String url, LocalDateTime publicationDate) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.publicationDate = publicationDate;
    }

    public Link(long id, String title, String url, LocalDateTime publicationDate, Feed feed) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.publicationDate = publicationDate;
        this.feed = feed;
    }

    public Link(long id, Feed feed, String title, String url, LocalDateTime publicationDate, Set<LinkSending> linkSendings) {
        this.id = id;
        this.feed = feed;
        this.title = title;
        this.url = url;
        this.publicationDate = publicationDate;
        this.linkSendings = linkSendings;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Feed getFeed() {
        return this.feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }



    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public LocalDateTime getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }


    public Set<LinkSending> getLinkSendings() {
        return this.linkSendings;
    }

    public void setLinkSendings(Set<LinkSending> linkSendings) {
        this.linkSendings = linkSendings;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", feed=" + feed +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}


