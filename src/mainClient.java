/**
 * Created by Korvin on 10.09.2014.
 */

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class mainClient {
    public static void main(String[] args)
            throws AuthenticationException, MalformedURLException, IOException, ServiceException {

        SpreadsheetService service =
                new SpreadsheetService("MySpreadsheetIntegration-v1");

        // TODO: Authorize the service object for a specific user (see other sections)

        // Define the URL to request.  This should never change.
        URL SPREADSHEET_FEED_URL = new URL(
                "https://docs.google.com/spreadsheets/d/17CMTgDHGuVWasW6FNvLrgP31fjmbbzcGLqaPGtSm8qo/edit#gid=0");

        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
                SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        if (spreadsheets.size() == 0) {
            // TODO: There were no spreadsheets, act accordingly.
        }

        // TODO: Choose a spreadsheet more intelligently based on your
        // app's needs.
        SpreadsheetEntry spreadsheet = spreadsheets.get(0);
        System.out.println(spreadsheet.getTitle().getPlainText());

        // Get the first worksheet of the first spreadsheet.
        // TODO: Choose a worksheet more intelligently based on your
        // app's needs.
        WorksheetFeed worksheetFeed = service.getFeed(
                spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(0);

        // Fetch the cell feed of the worksheet.
        URL cellFeedUrl = worksheet.getCellFeedUrl();
        CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

        // Iterate through each cell, updating its value if necessary.
        // TODO: Update cell values more intelligently.
        for (CellEntry cell : feed.getEntries()) {
            if (cell.getTitle().getPlainText().equals("A1")) {
                cell.changeInputValueLocal("200");
                cell.update();
            } else if (cell.getTitle().getPlainText().equals("B1")) {
                cell.changeInputValueLocal("=SUM(A1, 200)");
                cell.update();
            }
        }
    }
}


