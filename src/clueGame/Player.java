package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This is the player superclass generating the overall framework for players.
 * 
 * 
 */

public class Player {
	
	private String playerName;
	protected int row;
	protected int column;
	private Color color;
	private Vector<Card> hand;
	private Vector<Card> unSeenWeapons;
	private Vector<Card> unSeenPeople;
	private Vector<Card> unSeenRooms;
	//private ControlPanel controlPanel = new ControlPanel();
	
	/**
	 * 
	 * 
	 * @param playerName
	 * @param row
	 * @param column
	 * @param color
	 * Default Constructor
	 * 
	 */
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		this.hand = new Vector<Card>();
		unSeenWeapons = new Vector<Card>();
		unSeenPeople = new Vector<Card>();
		unSeenRooms = new Vector<Card>();
	
	}

	
//Getters and Setters
	
	public void setUnSeenWeapons(Vector<Card> seenWeapons) {
		this.unSeenWeapons = seenWeapons;
	}


	public void setUnSeenPeople(Vector<Card> seenPeople) {
		this.unSeenPeople = seenPeople;
	}


	public void setUnSeenRooms(Vector<Card> seenRooms) {
		this.unSeenRooms = seenRooms;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	public void addCard(Card card) {
		hand.add(card);
		seeCard(card);
	}
	
	public Vector<Card> getHand() {

		return hand;
	}
	
	public void setLocation(BoardCell cell) {
		column = cell.getColumn();
		row = cell.getRow();
	}
	
	
//Disprove Suggestion method
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public void seeCard(Card card) {
		if(card.getCardType() == CardType.PERSON) {
			unSeenPeople.remove(card);
		}
		else if(card.getCardType() == CardType.ROOM) {
			unSeenRooms.remove(card);
		}
		else if(card.getCardType() == CardType.WEAPON) {
			unSeenWeapons.remove(card);
		}
	}
	
	
	
	public Vector<Card> getUnSeenWeapons() {
		return unSeenWeapons;
	}


	public Vector<Card> getUnSeenPeople() {
		return unSeenPeople;
	}


	public Vector<Card> getUnSeenRooms() {
		return unSeenRooms;
	}


	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*32, row*32, 32, 32);
		g.setColor(Color.black);
		g.drawOval(column*32, row*32, 32, 32);
	}
	
	

}
