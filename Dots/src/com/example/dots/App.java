package com.example.dots;

import android.app.Application;

public class App extends Application {
	
	// Растояние между точками
	public static int step = 50;
	
	//Радиус точки
	public static final int radius = 10;
	
	// Начальые координаты точек
	public static int startX = 50;
	public static int startY = 50;
	
	public static int maxRows = 0;
	public static int maxColumns = 0;
}
