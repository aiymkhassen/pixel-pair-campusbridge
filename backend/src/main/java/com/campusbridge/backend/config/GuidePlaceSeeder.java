package com.campusbridge.backend.config;

import com.campusbridge.backend.entity.GuidePlace;
import com.campusbridge.backend.repository.GuidePlaceRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GuidePlaceSeeder implements CommandLineRunner {

    private final GuidePlaceRepository repository;

    public GuidePlaceSeeder(GuidePlaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        List<GuidePlace> places = new ArrayList<>();

        places.add(place(
            "SDU University",
            "Campus",
            "Official university website for academic information and student services.",
            "1/1 Abylai Khan Avenue, Kaskelen",
            "https://sdu.edu.kz/en/",
            map("SDU University Kaskelen"),
            null
        ));

        places.add(place(
            "SDU Library",
            "Campus",
            "Find books, academic resources and study materials.",
            "SDU University",
            "https://library.sdu.edu.kz/",
            map("SDU University Library Kaskelen"),
            null
        ));

        places.add(place(
            "SDU Cafeteria",
            "Campus",
            "Ask campus staff about available meals and cafeteria opening hours.",
            "SDU University",
            null,
            null,
            null
        ));

        places.add(place(
            "SDU Student House",
            "Campus",
            "University accommodation information for students.",
            "SDU University",
            "https://sdu.edu.kz/en/",
            null,
            null
        ));

        places.add(place(
            "SDU Life",
            "Events",
            "Discover university events, student clubs and campus activities.",
            "SDU University",
            "https://life.sdu.edu.kz/en",
            null,
            null
        ));

        places.add(place(
            "Halal Food",
            "Food",
            "Discover halal food options in Kaskelen. Confirm halal certification with the restaurant.",
            "Kaskelen, Kazakhstan",
            null,
            map("Halal restaurants Kaskelen"),
            null
        ));

        places.add(place(
            "Affordable Restaurants",
            "Food",
            "Explore restaurants and cafes suitable for student budgets.",
            "Kaskelen, Kazakhstan",
            null,
            map("Restaurants Kaskelen"),
            null
        ));

        places.add(place(
            "Coffee Shops",
            "Food",
            "Find cafes for meeting friends or studying.",
            "Kaskelen, Kazakhstan",
            null,
            map("Coffee shops Kaskelen"),
            null
        ));

        places.add(place(
            "Bus Stops",
            "Transportation",
            "Find nearby bus stops and check available routes.",
            "Kaskelen, Kazakhstan",
            null,
            map("Bus stops near SDU University"),
            null
        ));

        places.add(place(
            "Taxi Services",
            "Transportation",
            "Find local taxi services for travelling around the city.",
            "Kaskelen, Kazakhstan",
            null,
            map("Taxi services Kaskelen"),
            null
        ));

        places.add(place(
            "Supermarkets",
            "Shopping",
            "Find groceries and everyday essentials.",
            "Kaskelen, Kazakhstan",
            null,
            map("Supermarkets Kaskelen"),
            null
        ));

        places.add(place(
            "Stationery Shops",
            "Shopping",
            "Find notebooks, pens and university supplies.",
            "Kaskelen, Kazakhstan",
            null,
            map("Stationery shops Kaskelen"),
            null
        ));

        places.add(place(
            "Pharmacies",
            "Shopping",
            "Find nearby pharmacies.",
            "Kaskelen, Kazakhstan",
            null,
            map("Pharmacies Kaskelen"),
            null
        ));

        places.add(place(
            "Emergency Services",
            "Emergency",
            "Unified emergency number in Kazakhstan.",
            "Kazakhstan",
            null,
            null,
            "112"
        ));

        places.add(place(
            "Police",
            "Emergency",
            "Contact the police in an emergency.",
            "Kazakhstan",
            null,
            null,
            "102"
        ));

        places.add(place(
            "Ambulance",
            "Emergency",
            "Request emergency medical assistance.",
            "Kazakhstan",
            null,
            null,
            "103"
        ));

        places.add(place(
            "Hospitals",
            "Emergency",
            "Find hospitals and medical facilities.",
            "Kaskelen, Kazakhstan",
            null,
            map("Hospitals Kaskelen"),
            null
        ));

        repository.saveAll(places);

        System.out.println(
            "Student Guide: " + places.size()
            + " entries added successfully."
        );
    }

    private GuidePlace place(
            String name,
            String category,
            String description,
            String address,
            String websiteUrl,
            String mapUrl,
            String contactNumber) {

        GuidePlace place = new GuidePlace();

        place.setName(name);
        place.setCategory(category);
        place.setDescription(description);
        place.setAddress(address);
        place.setWebsiteUrl(websiteUrl);
        place.setMapUrl(mapUrl);
        place.setContactNumber(contactNumber);

        return place;
    }

    private String map(String query) {

        return "https://www.google.com/maps/search/?api=1&query="
            + URLEncoder.encode(query, StandardCharsets.UTF_8);
    }
}

