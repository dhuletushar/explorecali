package com.example.ec;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.service.TourPackageService;
import com.example.ec.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@ComponentScan
@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

	@Value("${ec.importFile}")
	String jsonFileName;

	@Autowired
	private TourPackageService tourPackageService;

	@Autowired
	private TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		createTourPackages();
		long noOfTourPackage = tourPackageService.tourPackageCount();
		System.out.println("Tour Package Count"+noOfTourPackage);

		createTours("ExploreCalifornia.json");
		long noOfTour = tourService.tourCount();
		System.out.println("Tour count "+noOfTour);

	}

	private void createTourPackages(){
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");
	}

	private void createTours(String jsonFileName) throws IOException {
		TourFromFile.read(jsonFileName).forEach(tour -> {
			tourService.createTour(
					tour.getTitle(),
					tour.getDescription(),
					tour.getBlurb(),
					tour.getPrice(),
					tour.getLength(),
					tour.getBullets(),
					tour.getKeywords(),
					tour.getPackageType(),
					tour.getDifficulty(),
					tour.getRegion()
			);
		});
	}


	private static class TourFromFile {
		private String packageType, title, blurb, description, bullets, difficulty, length, price, region, keywords;

		static List<TourFromFile> read(String jsonFileName) throws IOException {
			return new ObjectMapper().readValue(new FileInputStream(jsonFileName), new TypeReference<List<TourFromFile>>() {
			});
		}

		public String getPackageType() {
			return packageType;
		}

		public String getTitle() {
			return title;
		}

		public String getBlurb() {
			return blurb;
		}

		public String getDescription() {
			return description;
		}

		public String getBullets() {
			return bullets;
		}

		public Difficulty getDifficulty() {
			return Difficulty.valueOf(difficulty);
		}

		public String getLength() {
			return length;
		}

		public Integer getPrice() {
			return Integer.parseInt(price);
		}

		public Region getRegion() {
			return Region.findByLabel(region);
		}

		public String getKeywords() {
			return keywords;
		}
	}
}
