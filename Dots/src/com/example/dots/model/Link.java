package com.example.dots.model;

import java.util.HashMap;

import android.graphics.Path;

public class Link {
	
	private int dotN1;
	private int dotN2;
	private MyCircle dot1;
	private MyCircle dot2;
	
	public Link(int dotN1, int dotN2, HashMap<Integer, MyCircle> dotsMap) {
		this.dotN1 = dotN1;
		this.dotN2 = dotN2;
		
		dot1 = dotsMap.get(dotN1);
		dot2 = dotsMap.get(dotN2);
		
	}
	
	public Path getPath() {
		
		
		
		int x1 = dot1.getX();
		int y1 = dot1.getY();
		int x2 = dot2.getX();
		int y2 = dot2.getY();
		
		Path path = new Path();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.close();
		
		return path;
	}
	
	public int getDotN1() {
		return dotN1;
	}

	public int getDotN2() {
		return dotN2;
	}

	public MyCircle getDot1() {
		return dot1;
	}

	public MyCircle getDot2() {
		return dot2;
	}

	public int getKey() {
		return (String.valueOf(dotN1)).hashCode() * (String.valueOf(dotN2)).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Link other  = (Link) obj;
		if (getKey() != other.getKey())
			return false;
		return true;
	}
}
