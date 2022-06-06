package testnationalrailapi;

import com.thalesgroup.rtti._2015_11_27.ldb.types.ArrayOfServiceLocations;
import com.thalesgroup.rtti._2015_11_27.ldb.types.ServiceLocation;
import com.thalesgroup.rtti._2017_10_01.ldb.GetBoardRequestParams;
import com.thalesgroup.rtti._2017_10_01.ldb.StationBoardResponseType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ArrayOfServiceItems;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ServiceItem;
import com.thalesgroup.rtti._2017_10_01.ldb.types.StationBoard;
import java.util.List;

/**
 * Sample solution code to pretty-print a departure board based on data returned by the National
 * Rail Enquiries service.
 *
 * @author TM352 module team August 2019
 */
public class Main
{
    /**
     * @param args the command line arguments (none)
     */
    public static void main(String[] args)
    {
        //Construct parameters for our web service operation
        GetBoardRequestParams params = new GetBoardRequestParams();

        //There are two required parameters to be set
        params.setCrs("MKC"); //Milton Keynes Central (or another CRS)
        params.setNumRows(15); //how many results we want
        //params.setFilterCrs("EUS"); //optionally restrict destinations

        //call the web service with our desired parameters
        StationBoardResponseType departureBoard = getDepartureBoard(params);

        //extract a List<ServiceItem> from the result and process it
        StationBoard sb = departureBoard.getGetStationBoardResult();

        ArrayOfServiceItems trainServices = sb.getTrainServices();

        displayDepartures(trainServices.getService());
    }

    /**
     * Display a list of departures as you might do on a Departures Board at a train station. The
     * List of ServiceItems is sorted by departure time already. The logic of this method assumes we
     * are dealing with departures and assumes some formatting (so isn't particularly robust).
     *
     * @param services a List of departure ServiceItems we can display information about
     */
    private static void displayDepartures(List<ServiceItem> services)
    {
        System.out.println("Data from National Rail Enquiries");
        System.out.println("\t\tDEPARTURES BOARD");

        for (ServiceItem s : services)
        {
            ArrayOfServiceLocations destination = s.getDestination();
            List<ServiceLocation> locations = destination.getLocation();

            StringBuilder destinations = new StringBuilder();

            //There could be more than one location. If so, this code adds commas between them            
            if (locations.size() >= 1)
            {
                int i = 0;
                while (i < locations.size() - 1) //if one location, doesn't execute
                {
                    destinations.append(locations.get(i).getLocationName()).append(", ");
                    i++;
                }
                destinations.append(locations.get(i).getLocationName());
            }

            String std = s.getStd(); //scheduled departure time
            String etd = s.getEtd(); //expected departure time (could be delayed)
            String platform = s.getPlatform(); //could be null

            //Time and destination(s) print out
            System.out.printf("\t%s \t%s%n", std, destinations.toString());

            if (platform == null)
            {
                platform = " - ";
            }
            else
            {
                platform = "Platform " + platform;
            }

            if (!etd.equals("On time") && !etd.equals("Cancelled"))
            {
                etd = "Expected " + etd;
            }

            //Print out platform and etd, which might indicate cancelled or on time
            System.out.printf("\t\t%s \t%s", platform, etd);

            //If cancelled, why was that?
            Boolean isCancelled = s.isIsCancelled();

            if (isCancelled != null && isCancelled)
            {
                //reason substring
                int start = s.getCancelReason().indexOf("because of ") + "because of ".length();
                System.out.printf(" (%s)", s.getCancelReason().substring(start));
            }

            System.out.println("\n");
        }
    }

    /**
     * Most of this method was generated for us by NetBeans
     *
     * @param parameters Parameters for this method call. We are required to set the crs and the
     * numRows fields.
     * @return a StationBoardResponseType
     */
    private static StationBoardResponseType getDepartureBoard(
            com.thalesgroup.rtti._2017_10_01.ldb.GetBoardRequestParams parameters)
    {
        com.thalesgroup.rtti._2017_10_01.ldb.Ldb service = new com.thalesgroup.rtti._2017_10_01.ldb.Ldb();
        com.thalesgroup.rtti._2017_10_01.ldb.LDBServiceSoap port = service.getLDBServiceSoap();

        //It is necessary for us to add a handler so that the soap headers can be correctly configured
        SoapHandler.setHandler(port);
        
        return port.getDepartureBoard(parameters);
    }

}
