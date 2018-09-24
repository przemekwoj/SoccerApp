package com.przemo.MainSoccerApp.entities;

// we use this class to convert entity User to fit in our table 
public class TableUser
{
	int position;
	String name;
	int points;
	
	public TableUser(int position, String name, int points) {
		super();
		this.position = position;
		this.name = name;
		this.points = points;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
