package be.icc.Pid_Reservations_2024.RomeRss;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.rometools.rome.feed.rss.*;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

            String duration = "Le spectacle dure " + show.getDuration() + " minutes";

            Date create_in = show.getCreated_in();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(create_in);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            create_in = calendar.getTime();

            Description images = new Description();

            Item item = new Item();
            item.setTitle(show.getTitle());
            item.setComments(duration);
            item.setPubDate(create_in);
            item.setDescription(images);
            item.setLink("http://localhost:8080/show/" + show.getId());

            // Image
            String showImageUrl = show.getPosterUrl();
            if (showImageUrl == null || showImageUrl.isEmpty()) {
                showImageUrl = "https://cdn.futura-sciences.com/cdn-cgi/image/width=1920,quality=50,format=auto/sources/images/dossier/773/01-intro-773.jpg";
            }

            String imageHtml = "<img src=\"" + showImageUrl + "\" alt=\"" + show.getTitle() + "\" width=\"50\" height=\"50\" />";

            images.setValue(images.getValue() + " " + imageHtml);

            List<Element> foreignKeys = new ArrayList<>();

            for (Representation representation : show.getRepresentations()) {

                Element dateRepresentation = new Element("DateDeRepresentation", Namespace.NO_NAMESPACE);
                dateRepresentation.setText(representation.getSchedule().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                Element salle = new Element("Salle", Namespace.NO_NAMESPACE);
                salle.setText(representation.getLocations().getDesignation());

                foreignKeys.add(salle);
                foreignKeys.add(dateRepresentation);

            }

            item.setForeignMarkup(foreignKeys);
            feed.add(item);
        }

        return feed;
    }

}
