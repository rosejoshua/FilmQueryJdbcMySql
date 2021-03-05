package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {	
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();

	}

	private void launch() {
		boolean run = true;		
		Scanner input = new Scanner(System.in);

		while(run) {
			run = startUserInterface(input);
		}

		input.close();
	}

	private boolean startUserInterface(Scanner input) {
		String choice = "0";
		int parsedInt;
		printMenu();
		System.out.println("Enter your menu choice (1-3): ");
		choice = input.next();
		try {
			parsedInt = Integer.parseInt(choice);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input!");
			return true;
		}
		
		if (parsedInt != 1 && parsedInt != 2 && parsedInt != 3) {
			System.out.println("Invalid input!");
			return true;
		}
		
		switch (parsedInt) {
		case 1:
			printMenuFilmId();
			System.out.println("Your film choice: ");
			choice = input.next();
			
			try {
				parsedInt = Integer.parseInt(choice);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!");
				return true;
			}
			
			if (parsedInt < 1 || parsedInt > 1000) {
				System.out.println("Film not found, returning to Main Menu");
				return true;
			}
			Film film = this.db.findFilmById(parsedInt);
			printFilm(film);
			break;
			
		case 2:
			printMenuFilmSearch();
			System.out.println("Your keyword: ");
			choice = "";
			choice = input.next();									
			
			List<Film> filmList = this.db.findFilmsByKeyword(choice);
			
			if(filmList.size()==0) {
				System.out.println("No results found, returning to Main Menu");
				return true;
			}
			
			for (Film film2 : filmList) {
				printFilm(film2);				
			}
			break;
			
		case 3:
			System.out.println("Goodbye!");
			return false;

		default:
		}
		return true;
		
		
	}
	
	private void printMenu() {
		System.out.println("+----------------------------------------------+");
		System.out.println("|        *** FILM QUERY APPLICATION ***        |");
		System.out.println("+-----------+----------------------------------+");
		System.out.println("|  Option:  |             Result:              |");
		System.out.println("+-----------+----------------------------------+");
		System.out.println("|     1     | Look up a film by ID number      |");
		System.out.println("+-----------+----------------------------------+");
		System.out.println("|     2     | Look up a film by search keyword |");
		System.out.println("+-----------+----------------------------------+");
		System.out.println("|     3     | Quit                             |");
		System.out.println("+-----------+----------------------------------+");
	}
	private void printMenuFilmId() {
		System.out.println("+----------------------------------------------+");
		System.out.println("|        *** FILM QUERY APPLICATION ***        |");
		System.out.println("+----------------------------------------------+");
		System.out.println("| Film ID Lookup - Enter a film ID (1-1000):   |");
		System.out.println("+----------------------------------------------+");
	}
	private void printMenuFilmSearch() {
		System.out.println("+----------------------------------------------+");
		System.out.println("|        *** FILM QUERY APPLICATION ***        |");
		System.out.println("+----------------------------------------------+");
		System.out.println("| Film Lookup - Enter a search keyword:        |");
		System.out.println("+----------------------------------------------+");
	}
	private void printFilm(Film film) {
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.println("|   TITLE:    |  " + film.getTitle());
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.println("|    YEAR:    |  " + film.getReleaseYear());
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.println("|   RATING:   |  " + film.getRating());
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.println("|  LANGUAGE:  |  " + film.getLanguage());
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.println("| DESCRIPTION:|  " + film.getDescription());
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
		System.out.print("|    CAST:    |  ");
		int count = 0;
		for (Actor actor : new ArrayList<>(this.db.findActorsByFilmId(film.getId()))) {
			if(count!=0) {
				System.out.print(", ");
			}
			System.out.print(actor.getFirstName() + " ");
			System.out.print(actor.getLastName());
			count++;
		}
		System.out.println();
		System.out.println("+-------------+--------------------------------------"
				+ "---------------------------------------------------------------------------------"
				+ "---------------------------------------------------------------------------------");
	}

}
