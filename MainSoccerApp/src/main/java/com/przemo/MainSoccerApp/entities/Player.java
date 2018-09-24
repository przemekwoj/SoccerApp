package com.przemo.MainSoccerApp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "players")
public class Player 
{
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name = "id")
		private String id = "1";
		
		@Column(name = "idmanager")
		private int idmanager;
		
		@Column(name = "position")
		private String position;
		
		@Column(name = "stamina")
		private int stamina;
		
		@Column(name = "power")
		private int power;
		//we are using this to connect entity with shape on screen
		@Column(name = "circle")
		private String circle;
		
		@Column(name = "name")
		private String name;
		
		@Column(name = "surname")
		private String surname;
		
		@Column(name = "path")
		private String path;
		
		@Column(name = "dateTrain")
		@Temporal(TemporalType.TIMESTAMP)
		private Date dateTrain;
		
		@Column(name = "canPlay")
		private boolean canPlay;


		public Player() {
		}

		public Player(int idmanager, String position, int stamina, int power,String circle,String name,String surname,String path,Date dateTrain ,boolean canPlay) {
			super();
			this.idmanager = idmanager;
			this.position = position;
			this.stamina = stamina;
			this.power = power;
			this.circle = circle;
			this.name = name;
			this.surname = surname;
			this.path = path;
			this.dateTrain = dateTrain;
			this.canPlay = canPlay;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getIdmanager() {
			return idmanager;
		}

		public void setIdmanager(int idmanager) {
			this.idmanager = idmanager;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public int getStamina() {
			return stamina;
		}

		public void setStamina(int stamina) {
			this.stamina = stamina;
		}

		public int getPower() {
			return power;
		}

		public void setPower(int power) {
			this.power = power;
		}
		public String getCircle() {
			return circle;
		}

		public void setCircle(String circle) {
			this.circle = circle;
		}

		public Date getDateTrain() {
			return dateTrain;
		}

		public void setDateTrain(Date dateTrain) {
			this.dateTrain = dateTrain;
		}

		public boolean isCanPlay() {
			return canPlay;
		}

		public void setCanPlay(boolean canPlay) {
			this.canPlay = canPlay;
		}
	
}
