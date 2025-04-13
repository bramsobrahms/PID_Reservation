package be.icc.Pid_Reservations_2024.RomeRss;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import java.util.*;

@Component
public class RssFeedView extends AbstractRssFeedView {

    @Autowired
    ShowService showService;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("Reservation RSS Feed");
        feed.setDescription("Reservation Rss Feed for this application");
        feed.setLink("http://localhost:8080");
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Item> feed = new ArrayList<>();
        List<Show> shows = showService.getAllShows();

        for (Show show : shows) {

            Description description = new Description();
            description.setValue("Le spectacle dure " + show.getDuration() + " minutes");

            Date create_in = show.getCreated_in();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(create_in);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            create_in = calendar.getTime();



            Item item = new Item();
            item.setTitle(show.getTitle());
            item.setAuthor(show.getLocation().getDesignation());
            item.setDescription(description);
            item.setPubDate(create_in);
            item.setLink("http://localhost:8080/show/" + show.getId());

            // Image
            String showImageUrl = show.getPosterUrl();
            if(showImageUrl == null || showImageUrl.isEmpty()) {
                showImageUrl = "https://cdn.futura-sciences.com/cdn-cgi/image/width=1920,quality=50,format=auto/sources/images/dossier/773/01-intro-773.jpg";
            }

            String imageHtml = "<img src=\"" + showImageUrl + "\" alt=\"" + show.getTitle() + "\" width=\"50\" height=\"50\" />";

            description.setValue(description.getValue() + " " + imageHtml);

            feed.add(item);
        }

        return feed;
    }

}
