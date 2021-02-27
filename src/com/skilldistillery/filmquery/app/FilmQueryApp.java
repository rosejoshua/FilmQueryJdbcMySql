package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;

public class FilmQueryApp {	
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
		System.out.println("TEST: HARDCODED FIND FILM ID 500");
		System.out.println(app.db.findFilmById(500));
//		System.out.println("TEST: HARDCODED FIND ACTOR ID 5");
//		System.out.println(app.db.findActorById(5));
//		System.out.println("TEST: HARDCODED FIND ACTOR LIST FILM ID 5");
//		System.out.println(app.db.findActorsByFilmId(5));
	}

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//  }

	private void launch() {
		
		
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

	}

}
