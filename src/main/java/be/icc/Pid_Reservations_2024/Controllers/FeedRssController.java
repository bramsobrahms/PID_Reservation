package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController()
public class FeedRssController {

    @Autowired
    private ShowService showService;


    @GetMapping(path = "/rss")
    public Channel rss() {
        Channel channel = new Channel();
        channel.setFeedType("rss_2.0");
        channel.setTitle("Feed Rss Shows");
        channel.setDescription("With RSS Feed, you will always be up to date on new shows with notifications");
        channel.setLink("http://localhost:8080/shows");
        channel.setGenerator("Always up to date");

        Image image = new Image();
        image.setUrl("https://howtodoinjava.com/wp-content/uploads/2015/05/howtodoinjava_logo-55696c1cv1_site_icon-32x32.png");
        image.setTitle("HowToDoInJava Feed");
        image.setHeight(32);
        image.setWidth(32);
        channel.setImage(image);

        Date postDate = new Date();
        channel.setPubDate(postDate);

        List<Item> items = new ArrayList<>();
        for( Show show : showService.getAllShows() ) {
            Item item = new Item();
            item.setAuthor(show.getLocation().getDesignation());
            item.setLink("http://localhost:8080/show/"+show.getId());
            item.setTitle(show.getTitle());
            item.setUri("http://localhost:8080/show/"+show.getId());
            item.setComments("Le spectacle dure: "+ show.getDuration()+ " minutes");


            StringBuilder descriptionText = new StringBuilder();
            if( show.getPosterUrl() == null ) {
                show.setPosterUrl("https://cdn.shopify.com/s/files/1/0553/0442/1412/files/rideau_rouge_theatre_2048x2048.png?v=1691589090");
            }
            descriptionText.append("<img src='")
                    .append(show.getPosterUrl())
                    .append("' alt="+ show.getTitle() +"style='width:200px;height:auto;'/><br/>");

            for (Representation r : show.getRepresentations()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String schedule = r.getSchedule().format(formatter);
                String designation = r.getShow().getLocation().getDesignation().trim();
                descriptionText.append("Le ").append(schedule)
                        .append(" à ").append(designation)
                        .append("\n");
            }

            Description descr = new Description();
            descr.setValue(descriptionText.toString());
            item.setDescription(descr);

            item.setPubDate(postDate);

            channel.setItems(Collections.singletonList(item));

            items.add(item);
            channel.setItems(items);

        }
        //Like more Entries here about different new topics
        return channel;
    }
}
