/*
 * The tile(s) of the chess board
 * 
 * Each tile will be visually represented by a unique JButton
 * 
 */

import javax.swing.*;

public class Tile extends JButton
{
	/* STUFF TO DO:
	 * 
	 * Paint all of them with the correct color (brown or black)
	 * 
	 * Check whether there is a piece within the button
	 * 
	 * Add the corresponding image icon
	 */
	
//Variables
	private int id; //The id of the tile (1 for first, 2 for seconds and so on)
	private String colour; //The colour of the tile depending on its id (position)
	private boolean containsPiece; //containsPiece = true if contains any piece, else containsPiece = false
	private Piece piece; //The piece within the tile
	private boolean isUnderThreatFromWhite; // true if tile is under threat from white piece else false
	private boolean isUnderThreatFromBlack; // true if tile is under threat from black piece else false
	
//Constructors
	
	//Default constructor
	Tile()
	{
		this.id = 0;
		this.colour = "";
		this.containsPiece = false;
		this.piece = null;
		this.isUnderThreatFromWhite = false;
		this.isUnderThreatFromBlack = false;
	}
	
	//Setter constructor
	Tile(int id, String colour, boolean containsPiece, Piece piece, boolean isUnderThreatFromWhite, boolean isUnderThreatFromBlack)
	{
		this.id = id;
		this.colour = colour;
		this.containsPiece = containsPiece;
		this.piece = piece;
		this.isUnderThreatFromWhite = isUnderThreatFromWhite;
		this.isUnderThreatFromBlack = isUnderThreatFromBlack;
	}
	
	//Object (copy) constructor
	Tile(Tile t)
	{
		this.id = t.id;
		this.colour = t.colour;
		this.containsPiece = t.containsPiece;
		this.piece = t.piece;
		this.isUnderThreatFromWhite = t.isUnderThreatFromWhite;
		this.isUnderThreatFromBlack = t.isUnderThreatFromBlack;
	}
	
//Setters
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	public void setContainsPiece(boolean containsPiece)
	{
		this.containsPiece = containsPiece;
	}
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	
	public void setIsUnderThreatFromWhite(boolean isUnderThreatFromWhite)
	{
		this.isUnderThreatFromWhite = isUnderThreatFromWhite;
	}
	
	public void setIsUnderThreatFromBlack(boolean isUnderThreatFromBlack)
	{
		this.isUnderThreatFromBlack = isUnderThreatFromBlack;
	}
	
//Getters
	public int getId()
	{
		return this.id;
	}
	
	public String getColour()
	{
		return this.colour;
	}
	
	public boolean getContainsPiece()
	{
		return this.containsPiece;
	}
	
	public Piece getPiece()
	{
		return this.piece;
	}
	
	public boolean getIsUnderThreatFromWhite()
	{
		return this.isUnderThreatFromWhite;
	}
	
	public boolean getIsUnderThreatFromBlack()
	{
		return this.isUnderThreatFromBlack;
	}	
}
