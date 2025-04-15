package be.icc.Pid_Reservations_2024.RomeRss;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.rometools.rome.feed.rss.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class generates an RSS feed for all the shows in the system.
 * It uses the ROME library to build the feed.
 * Each show is represented as an RSS item with basic details,
 * including title, duration, date, image, and its representations.
 */
@Component
public class RssFeedView extends AbstractRssFeedView {

    @Autowired
    ShowService showService;

    /**
     * Set the general information (metadata) for the RSS feed.
     * This includes the title, description, and main link of the feed.
     *
     * @param model   the data model
     * @param feed    the RSS feed channel
     * @param request the HTTP request
     */
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("Reservation RSS Feed");
        feed.setDescription("Reservation Rss Feed for this application");
        feed.setLink("http://localhost:8080");
    }

    /**
     * Build the list of RSS feed items.
     * Each item represents a show, including its title, duration, image,
     * date of creation (plus one day), and details about its representations.
     *
     * @param model    the data model
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return a list of RSS items
     * @throws Exception in case of errors while building the feed
     */
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Item> feed = new ArrayList<>();
        List<Show> shows = showService.getAllShows();

        for (Show show : shows) {

            // Build a description with show duration
            String duration = "Le spectacle dure " + show.getDuration() + " minutes";

            // Get the show creation date and add 1 day
            Date create_in = show.getCreated_in();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(create_in);
            create_in = calendar.getTime();

            // Description part (can include HTML)
            Description images = new Description();

            Item item = new Item();
            item.setTitle(show.getTitle());
            item.setComments(duration);
            item.setAuthor(show.getLocation().getDesignation());
            item.setPubDate(create_in);
            item.setDescription(images);
            item.setLink("http://localhost:8080/show/" + show.getId());

            // Add image (poster URL or default image)
            String showImageUrl = show.getPosterUrl();
            if (showImageUrl == null || showImageUrl.isEmpty()) {
                showImageUrl = "https://cdn.futura-sciences.com/cdn-cgi/image/width=1920,quality=50,format=auto/sources/images/dossier/773/01-intro-773.jpg";
            }

            String imageHtml = "<img src=\"" + showImageUrl + "\" alt=\"" + show.getTitle() + "\" width=\"50\" height=\"50\" />";

            images.setValue(imageHtml);


            // Create foreign markup (custom XML elements)
            List<Element> foreignKeys = new ArrayList<>();

            for (Representation representation : show.getRepresentations()) {

                // Custom element for representation date
                Element dateRepresentation = new Element("DateDeRepresentation", Namespace.NO_NAMESPACE);
                dateRepresentation.setText(representation.getSchedule().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                // Custom element for location
                Element salle = new Element("Salle", Namespace.NO_NAMESPACE);
                salle.setText(representation.getLocations().getDesignation());

                // Add both elements to the item
                foreignKeys.add(salle);
                foreignKeys.add(dateRepresentation);

//                item.setForeignMarkup(foreignKeys);
//                feed.add(item);
            }

            // Set the custom XML elements to the RSS item
            item.setForeignMarkup(foreignKeys);

            // Add the item to the feed
            feed.add(item);
        }

        return feed;
    }

}
