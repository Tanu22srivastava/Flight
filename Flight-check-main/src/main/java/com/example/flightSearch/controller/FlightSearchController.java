package com.example.flightSearch.controller;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate; // Or use HttpClient
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.flightSearch.model.Segment;
import com.example.flightSearch.model.FlightSearchRequest;
import com.example.flightSearch.model.FlightSearchResponse;
import com.example.flightSearch.model.FlightSearchResult;
import com.example.flightSearch.model.FlightSegment;
import com.fasterxml.jackson.databind.ObjectMapper; // For JSON handling
import com.example.flightSearch.model.FlightDetails;
import com.example.flightSearch.model.FlightPair;

import java.util.*;


@Controller
public class FlightSearchController {

        private final String apiUrl = "http://api.tektravels.com/BookingEngineService_Air/AirService.svc/rest/Search/";
        private final String endUserIp = "10.110.0.64"; /// Change this to your IP address
        private final String tokenId = "b4d48286-6660-495f-8e44-54b6be70d48f";
        private final ObjectMapper objectMapper = new ObjectMapper();
        
        private final HttpClient httpClient = HttpClient.newHttpClient();
        // private static final Set<String> INDIAN_AIRPORT_CODES = Set.of("DEL", "BOM", "MAA", "BLR", "HYD", "CCU", "AMD", "COK", "GOI", "PNQ");


        


        @GetMapping("/")
    public String home() {
        return "main";  // Returns the main.html page
    }



    @GetMapping("/flight-search")
    public String showSearchForm(Model model) {
        FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment());
        flightSearchRequest.setSegments(segments);
        model.addAttribute("flightSearchRequest", flightSearchRequest);
        return "flight-search-form";
    }

    @PostMapping("/flight-search")
    public String processSearch(@ModelAttribute FlightSearchRequest flightSearchRequest, Model model) throws Exception {
        flightSearchRequest.setEndUserIp(endUserIp);
        flightSearchRequest.setTokenId(tokenId);

        int journeyType = flightSearchRequest.getJourneyType();
        if (journeyType < 1 || journeyType > 5) {
            model.addAttribute("errorMessage", "Invalid Journey Type. Please enter a number between 1 and 5.");
            return "flight-search-form";
        }

        List<Segment> segments = new ArrayList<>();
        if (flightSearchRequest.getSegments() != null) {
            for (Segment segmentFromForm : flightSearchRequest.getSegments()) {
                if (segmentFromForm != null && (segmentFromForm.getOrigin() != null || segmentFromForm.getDestination() != null)) {
                    Segment segment = new Segment();
                    segment.setOrigin(segmentFromForm.getOrigin());
                    segment.setDestination(segmentFromForm.getDestination());
                    int cabinClass = segmentFromForm.getFlightCabinClass();
                    if (cabinClass < 1 || cabinClass > 6) {
                        model.addAttribute("errorMessage", "Invalid Cabin Class. Please select a value between 1 and 6.");
                        return "flight-search-form";
                    }
                    segment.setFlightCabinClass(cabinClass);
                    segment.setDirectFlight(segmentFromForm.isDirectFlight());
                    segment.setPreferredDepartureTime(segmentFromForm.getPreferredDepartureTime());
                    segment.setPreferredArrivalTime(segmentFromForm.getPreferredArrivalTime());
                    segments.add(segment);
                }
            }
        }
        flightSearchRequest.setSegments(segments);

        String jsonRequest = objectMapper.writeValueAsString(flightSearchRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
        String responseBody = response.body();

        if (responseBody != null) {
            if (responseBody.startsWith("\uFEFF")) {
                responseBody = responseBody.substring(1);
            }

            try {
                FlightSearchResponse flightSearchResponse = objectMapper.readValue(responseBody, FlightSearchResponse.class);
                List<FlightSearchResult> flightSearchResults = new ArrayList<>();

                if (flightSearchResponse != null && flightSearchResponse.getResponse() != null && flightSearchResponse.getResponse().getResults() != null) {
                    for (List<FlightDetails> flightDetailsList : flightSearchResponse.getResponse().getResults()) {
                        if (flightDetailsList != null) {
                            for (FlightDetails flightDetails : flightDetailsList) {
                                if (flightDetails != null && flightDetails.getFare() != null &&
                                        flightDetails.getSegments() != null && !flightDetails.getSegments().isEmpty()) {

                                    List<List<FlightSegment>> allSegments = flightDetails.getSegments();
                                    FlightSearchResult result = createFlightSearchResult(flightDetails, allSegments, flightSearchRequest);
                                    if (result != null) {
                                        flightSearchResults.add(result);
                                    }
                                }
                            }
                        }
                    }
                }

                model.addAttribute("flightSearchResults", flightSearchResults);
                return "flight-search-oneway-result";

            } catch (com.fasterxml.jackson.core.JsonParseException e) {
                System.err.println("JSON Parsing Error: " + e.getMessage());
                e.printStackTrace();
                model.addAttribute("errorMessage", "Error parsing flight data. Please try again.");
                return "flight-search-oneway-result";
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
                model.addAttribute("errorMessage", "An error occurred. Please try again.");
                return "flight-search-results";
            }

        } else {
            model.addAttribute("errorMessage", "No response from the API.");
            return "flight-search-oneway-result";
        }
    }

private FlightSearchResult createFlightSearchResult(FlightDetails flightDetails, List<List<FlightSegment>> allSegments, FlightSearchRequest flightSearchRequest) {
    if (allSegments == null || allSegments.isEmpty() || allSegments.get(0) == null || allSegments.get(0).isEmpty()) {
        return null;
    }

    FlightSearchResult result = new FlightSearchResult();
    List<FlightSegment> firstSegmentList = allSegments.get(0);
    FlightSegment firstSegment = firstSegmentList.get(0);

    // Validate essential fields
    if (firstSegment.getOrigin() == null || firstSegment.getOrigin().getAirport() == null ||
            firstSegment.getDestination() == null || firstSegment.getDestination().getAirport() == null ||
            firstSegment.getAirline() == null) {
        return null;
    }

    // Set departure and arrival times
    result.setDepTime(LocalDateTime.parse(firstSegment.getOrigin().getDepTime()));
    result.setArrTime(LocalDateTime.parse(firstSegment.getDestination().getArrTime()));

    // Set origin details
    result.setOrigin(firstSegment.getOrigin().getAirport().getAirportCode());
    result.setOriginTerminal(firstSegment.getOrigin().getAirport().getTerminal());

    // Set airline details
    result.setAirlineName(firstSegment.getAirline().getAirlineName());
    result.setAirlineCode(firstSegment.getAirline().getAirlineCode());
    result.setFlightNumber(firstSegment.getAirline().getFlightNumber());
    result.setFareBreakdown(firstSegment.getFareBreakdown());
    result.setNoOfSeatAvailable(firstSegment.getNoOfSeatAvailable());  
    result.setSupplierFareClass(firstSegment.getSupplierFareClass());
    // result.setRefundable(false);
    result.setRefundable(flightDetails.isRefundable());
    result.setResultFareType(flightDetails.getResultFareType());
    result.setFreeMealAvailable(flightDetails.isFreeMealAvailable());
    result.setAirlineRemark(flightDetails.getAirlineRemark());


    // Check if it's a direct flight or has stops within the first leg
    if (firstSegmentList.size() == 1) { // Direct Flight
        result.setDestination(firstSegment.getDestination().getAirport().getAirportCode());
        result.setDestinationTerminal(firstSegment.getDestination().getAirport().getTerminal());
        result.setDuration(firstSegment.getDuration());
    } else { // One or more stops
        // One-stop airport is the destination of the first segment in the leg
        result.setOneStopAirport(firstSegment.getDestination().getAirport().getAirportCode());
        
        // Destination is the last segment's destination
        FlightSegment lastSegment = firstSegmentList.get(firstSegmentList.size() - 1);
        result.setDestination(lastSegment.getDestination().getAirport().getAirportCode());
        result.setDestinationTerminal(lastSegment.getDestination().getAirport().getTerminal());
        
        // Calculate total duration
        int totalDuration = firstSegmentList.stream().mapToInt(FlightSegment::getDuration).sum();
        result.setDuration(totalDuration);
        
        // Update arrival time to the last segment's arrival
        result.setArrTime(LocalDateTime.parse(lastSegment.getDestination().getArrTime()));
    }

    // Set base fare
    if (flightDetails != null && flightDetails.getFare() != null) {
        result.setBaseFare(String.valueOf(flightDetails.getFare().getPublishedFare()));
        result.setPublishedFare(String.valueOf(flightDetails.getFare().getPublishedFare()));
        result.setOfferedFare(String.valueOf(flightDetails.getFare().getOfferedFare()));
    }

    return result;
}

    @GetMapping("/flight-search-return")
    public String returnSearchForm(Model model) {
        FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment());
        segments.add(new Segment());
        flightSearchRequest.setSegments(segments);
        model.addAttribute("flightSearchRequest", flightSearchRequest);
        return "flight-search-form-return";
    }
    
    @PostMapping("/flight-search-return")
public String processSearchResult(@ModelAttribute FlightSearchRequest flightSearchRequest, Model model) throws Exception {
    flightSearchRequest.setEndUserIp(endUserIp);
    flightSearchRequest.setTokenId(tokenId);
    System.out.println("Inside /flight-search-return controller");

    int journeyType=2;

    // Validate Journey Type
    // if (flightSearchRequest.getJourneyType() < 1 || flightSearchRequest.getJourneyType() > 5) {
    //     model.addAttribute("errorMessage", "Invalid Journey Type. Please enter a number between 1 and 5.");
    //     return "flight-search-form";
    // }

    // **Fix: Ensure segments are structured properly**
    List<Segment> segments = new ArrayList<>();
    
    if (flightSearchRequest.getSegments() != null) {
        for (Segment segmentFromForm : flightSearchRequest.getSegments()) {
            if (segmentFromForm != null && segmentFromForm.getOrigin() != null && segmentFromForm.getDestination() != null) {
                Segment segment = new Segment();
                segment.setOrigin(segmentFromForm.getOrigin());
                segment.setDestination(segmentFromForm.getDestination());
                
                // Validate Cabin Class
                int cabinClass = segmentFromForm.getFlightCabinClass();
                if (cabinClass < 1 || cabinClass > 6) {
                    model.addAttribute("errorMessage", "Invalid Cabin Class. Please select a value between 1 and 6.");
                    return "flight-search-results";
                }

                segment.setFlightCabinClass(cabinClass);
                segment.setDirectFlight(segmentFromForm.isDirectFlight());
                segment.setOneStopFlight(segmentFromForm.isOneStopFlight());
                segment.setPreferredDepartureTime(segmentFromForm.getPreferredDepartureTime());
                segment.setPreferredArrivalTime(segmentFromForm.getPreferredArrivalTime());

                segments.add(segment);
            }
        }
    }

    System.out.println("Inside /flight-search-return controller - Segments Processed");

    // **Fix: Construct API request correctly**
    Map<String, Object> apiRequest = new HashMap<>();
    apiRequest.put("EndUserIp", flightSearchRequest.getEndUserIp());
    apiRequest.put("TokenId", flightSearchRequest.getTokenId());
    apiRequest.put("AdultCount", flightSearchRequest.getAdultCount());
    apiRequest.put("ChildCount", flightSearchRequest.getChildCount());
    apiRequest.put("InfantCount", flightSearchRequest.getInfantCount());
    apiRequest.put("DirectFlight", flightSearchRequest.isDirectFlight());
    apiRequest.put("OneStopFlight", flightSearchRequest.isOneStopFlight());
    apiRequest.put("JourneyType", journeyType);  // **Hardcoded to 2**
    apiRequest.put("PreferredAirlines", flightSearchRequest.getPreferredAirlines());
    apiRequest.put("Sources", flightSearchRequest.getSources());

    // **Fix: Use correct segment structure**
    apiRequest.put("Segments", segments);  

    // Convert to JSON
    String jsonRequest = objectMapper.writeValueAsString(apiRequest);
    // System.out.println("JSON Request: " + jsonRequest);
    System.out.println("andr hu mai!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!2323!");

    // **Fix: Send API request safely**
    HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(apiUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    String responseBody = response.body();

    // **Fix: Handle Unexpected API Responses**
    if (responseBody == null || responseBody.trim().isEmpty()) {
        model.addAttribute("errorMessage", "No response from the API.");
        return "flight-search-results";
    }
    
    if (!responseBody.trim().startsWith("{") && !responseBody.trim().startsWith("[")) {
        model.addAttribute("errorMessage", "Invalid response from the API. Please try again.");
        return "flight-search-results";
    }

    // **Fix: Remove BOM if present**
    if (responseBody.startsWith("\uFEFF")) {
        responseBody = responseBody.substring(1);
    }

    try {
        FlightSearchResponse flightSearchResponse = objectMapper.readValue(responseBody, FlightSearchResponse.class);
        List<FlightSearchResult> flightSearchResults = new ArrayList<>();

        if (flightSearchResponse != null && flightSearchResponse.getResponse() != null && flightSearchResponse.getResponse().getResults() != null) {
            for (List<FlightDetails> flightDetailsList : flightSearchResponse.getResponse().getResults()) {
                for (FlightDetails flightDetails : flightDetailsList) {
                    FlightSearchResult result = createFlightSearchResult(flightDetails, flightDetails.getSegments(), flightSearchRequest);
                    if (result != null) {
                        flightSearchResults.add(result);
                        System.out.println("andr hu mai!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                }
            }
        }

        // System.out.println("Raw Response: " + responseBody);
        model.addAttribute("flightSearchResults", flightSearchResults);
        return "flight-search-results";

    } catch (com.fasterxml.jackson.core.JsonParseException e) {
        System.err.println("JSON Parsing Error: " + e.getMessage());
        model.addAttribute("errorMessage", "Error parsing flight data. Please try again.");
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        model.addAttribute("errorMessage", "An error occurred. Please try again.");
    }

    return "flight-search-results";
}

@GetMapping("/international/return-form")
    public String showInternationalReturnForm(Model model) {
        // Initialize request with 2 segments (outbound + return)
        FlightSearchRequest request = new FlightSearchRequest();
        List<Segment> segments = Arrays.asList(new Segment(), new Segment());
        request.setSegments(segments);
        model.addAttribute("flightSearchRequest", request);
        return "international-return-form";
    }

    @PostMapping("/international/return-form")
    public String processInternationalReturn(
            @ModelAttribute FlightSearchRequest flightSearchRequest,
            Model model) throws Exception {
        
        // 1. Set API credentials
        flightSearchRequest.setEndUserIp(endUserIp);
        flightSearchRequest.setTokenId(tokenId);
        flightSearchRequest.setJourneyType(2); // Force return journey

        // 2. Validate segments
    List<Segment> segments = flightSearchRequest.getSegments();
    if (segments == null || segments.size() < 2 || 
        segments.get(0).getOrigin().isEmpty() || segments.get(0).getDestination().isEmpty() ||
        segments.get(1).getOrigin().isEmpty() || segments.get(1).getDestination().isEmpty()) {
        
        model.addAttribute("errorMessage", "Both outbound and return segments must have valid origin and destination.");
        return "international-return-form";
    }

        // 3. Prepare API request
        String jsonRequest = objectMapper.writeValueAsString(flightSearchRequest);
        System.out.println("json request: "+ jsonRequest);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();

        // 4. Execute API call
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        String responseBody = response.body();

        // System.out.println("API Response: " + responseBody);


        // 5. Process response
        try {
            System.out.println("try me enter");
            FlightSearchResponse apiResponse = objectMapper.readValue(responseBody, FlightSearchResponse.class);
            List<FlightSearchResult> results = new ArrayList<>();

            if (apiResponse != null && apiResponse.getResponse() != null) {
                for (List<FlightDetails> flightList : apiResponse.getResponse().getResults()) {
                    for (FlightDetails details : flightList) {
                        processInternationalFlight(details, results);
                    }
                }
            }
            System.out.println("phle if se bahar");

            // 6. Separate outbound/return flights
            List<FlightSearchResult> outboundFlights = results.stream()
                .filter(f -> f.getTripIndicator() == 1)
                .toList();

            List<FlightSearchResult> returnFlights = results.stream()
                .filter(f -> f.getTripIndicator() == 2)
                .toList();
            System.out.println("Outbound Flights: " + outboundFlights.size());
            System.out.println("Return Flights: " + returnFlights.size());
                

            // model.addAttribute("outboundFlights", outboundFlights);
            // model.addAttribute("returnFlights", returnFlights);
            // System.out.println("end");
            // Create a merged list of flights
List<FlightSearchResult> mergedFlights = new ArrayList<>();

int maxPairs = Math.max(outboundFlights.size(), returnFlights.size());
for (int i = 0; i < maxPairs; i++) {
    if (i < outboundFlights.size()) {
        mergedFlights.add(outboundFlights.get(i));
    }
    if (i < returnFlights.size()) {
        mergedFlights.add(returnFlights.get(i));
    }
}

// Pass the merged list to the frontend
model.addAttribute("mergedFlights", mergedFlights);
    

            return "international-search-results";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error processing international flights: " + e.getMessage());
            return "international-search-results";
        }
    }

    // private void processInternationalFlight(FlightDetails flightDetails, List<FlightSearchResult> results) {
    //     List<List<FlightSegment>> allSegments = flightDetails.getSegments();
        
    //     // Process each trip (outbound=0, return=1)
    //     System.out.println("process ke ander");
    //     for (int tripIndex = 0; tripIndex < allSegments.size(); tripIndex++) {
    //         List<FlightSegment> tripSegments = allSegments.get(tripIndex);
            
            
    //         for (FlightSegment segment : tripSegments) {
    //             FlightSearchResult result = new FlightSearchResult();
                
    //             // Set basic flight info
    //             result.setTripIndicator(tripIndex + 1); // 1=Outbound, 2=Return
    //             result.setOrigin(segment.getOrigin().getAirport().getAirportCode());
    //             result.setOriginTerminal(segment.getOrigin().getAirport().getTerminal());
    //             result.setDestination(segment.getDestination().getAirport().getAirportCode());
    //             result.setDestinationTerminal(segment.getDestination().getAirport().getTerminal());
    //             // result.setOneStop(isOneStop);
                
    //             // Set timing information
    //             result.setDepTime(LocalDateTime.parse(segment.getOrigin().getDepTime()));
    //             result.setArrTime(LocalDateTime.parse(segment.getDestination().getArrTime()));
    //             result.setDuration(segment.getDuration());
                
    //             // Set airline details
    //             result.setAirlineName(segment.getAirline().getAirlineName());
    //             result.setAirlineCode(segment.getAirline().getAirlineCode());
    //             result.setFlightNumber(segment.getAirline().getFlightNumber());
                
    //             // Set fare from parent FlightDetails
    //             if (flightDetails.getFare() != null) {
    //                 result.setBaseFare(String.valueOf(flightDetails.getFare().getPublishedFare()));
    //             }

    //             results.add(result);
    //             System.out.println("result se bahar aagyi hu");
    //         }
    //     }
    // }
    private void processInternationalFlight(FlightDetails flightDetails, List<FlightSearchResult> results) {
        List<List<FlightSegment>> allSegments = flightDetails.getSegments();
        
        for (int tripIndex = 0; tripIndex < allSegments.size(); tripIndex++) {
            List<FlightSegment> tripSegments = allSegments.get(tripIndex);
            if (tripSegments.isEmpty()) continue;
    
            FlightSearchResult result = new FlightSearchResult();
            
            // Set common fields
            result.setTripIndicator(tripIndex + 1);
            
            // First segment details
            FlightSegment firstSegment = tripSegments.get(0);
            result.setOrigin(firstSegment.getOrigin().getAirport().getAirportCode());
            result.setOriginTerminal(firstSegment.getOrigin().getAirport().getTerminal());
            result.setDepTime(LocalDateTime.parse(firstSegment.getOrigin().getDepTime()));
    
            // Last segment details
            FlightSegment lastSegment = tripSegments.get(tripSegments.size() - 1);
            result.setDestination(lastSegment.getDestination().getAirport().getAirportCode());
            result.setDestinationTerminal(lastSegment.getDestination().getAirport().getTerminal());
            result.setArrTime(LocalDateTime.parse(lastSegment.getDestination().getArrTime()));
            result.setNoOfSeatAvailable(firstSegment.getNoOfSeatAvailable());  
            result.setSupplierFareClass(firstSegment.getSupplierFareClass());
            result.setRefundable(flightDetails.isRefundable());
            result.setResultFareType(flightDetails.getResultFareType());
            result.setFreeMealAvailable(flightDetails.isFreeMealAvailable());
            result.setAirlineRemark(flightDetails.getAirlineRemark());
    
            // Calculate duration and handle one-stop
            int totalDuration = 0;
            StringBuilder flightNumbers = new StringBuilder();
            String oneStop = "";
    
            for (FlightSegment segment : tripSegments) {
                totalDuration += segment.getDuration();
                
                if (flightNumbers.length() > 0) flightNumbers.append(", ");
                flightNumbers.append(segment.getAirline().getFlightNumber());
                
                // Set one-stop airport from intermediate segments
                if (segment != firstSegment) {
                    oneStop = segment.getOrigin().getAirport().getAirportCode();
                }
            }
    
            // Set calculated fields
            result.setDuration(totalDuration);
            result.setFlightNumber(flightNumbers.toString());
            result.setOneStopAirport(oneStop);
            result.setAirlineName(firstSegment.getAirline().getAirlineName());
            result.setAirlineCode(firstSegment.getAirline().getAirlineCode());
    
            // Set fare
            if (flightDetails.getFare() != null) {
                result.setBaseFare(String.valueOf(flightDetails.getFare().getBaseFare()));
                result.setPublishedFare(String.valueOf(flightDetails.getFare().getPublishedFare()));
                result.setOfferedFare(String.valueOf(flightDetails.getFare().getOfferedFare()));

            }
    
            results.add(result);
        }
    }



}