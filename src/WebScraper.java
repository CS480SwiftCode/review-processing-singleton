import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class WebScraper
{
    // testing this class
    public static void main(String[] args) throws IOException
    {
        // url to yelp page or at least info from yelp on the project
        String url = "urlHere";

        ArrayList<String> reviews = retrieveReviews(url);

        TextAnalyzer analyzer = TextAnalyzer.getInstance();

        boolean[][] times = analyzer.analyze(reviews);

        System.out.printf("   %9s %9s %9s %9s %9s %9s %9s%n", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                          "Friday", "Saturday");

        for (int i = 0; i < 12; i++)
        {
            boolean[] ba = times[i];
            System.out.printf("%3d", i + 2);
            for (boolean b : ba)
            {
                System.out.printf("%9s ", b);
            }
            System.out.println();
        }

    }


    private static ArrayList<String> retrieveReviews(String url) throws IOException
    {
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();

        String[] reviews = doc.select("p[itemprop]").toString().split("\n");
        ArrayList<String> formattedReviews = new ArrayList<>();
        for (String s : reviews)
        {
            s = s.substring(36, s.length() - 4);
            s = s.replace("<br />", "");
            s = s.toLowerCase();
            formattedReviews.add(s);
        }

        return formattedReviews;
    }
}
