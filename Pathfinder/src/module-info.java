module Pathfinder {
	exports application;
	exports application.view;
	exports application.controller;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	opens application.controller to javafx.fxml;
}