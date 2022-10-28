import java.awt.*;
import java.util.*;

/*
 * The piece(s) of a chess board 
 * 
 * Each piece will be visually represented by a corresponding (to their type) imageIcon
 * 
 */

public class Piece 
{
//Variables
	private String type; //Pawn, Bishop, Queen etc...
	private int id; //Pawn1, Pawn2, Pawn3, etc..(id is the number. eg: 1,2,3)
	private int value; //The value of each piece (queen = 9, bishop = 3)
	private String colour; //Black or white
	private Image image ; //Image of the piece
	private int movesMade; //Counts how many moves the piece has made
	
	
	/*
	 * A variable that is used only for diagonal moves of multiple steps (bishops and queens). 
	 * It is specifically used to find if the starting position of (let’s say) a bishop that
	 * wants to move (let’s say) forward right is closest to the 7th rank or 7th column 
	 * in order not to throw ArrayOutOfBounds. This is because the diagonal move is like
	 * a staircase, so the number of tiles that it is allowed to move is equal to the minimum
	 * distance of the following two (measured in tiles): 
	 *    1) Its starting position till 7th rank, 
	 *    2) Its starting position till 7th column
	 */
	private int maxNumberOfPotentialDistance; 
	
	
	/*
	 * The following variable defines legality of the direction that a piece is set to move
	 *
	 * a) legal = true, and continue to set the value of maxAllowedDiastance
	 * b) illegal = false, return false
	 * 
	 * EXPLANATION: A move is illegal when the piece cannot move BY THE RULES towards that direction
	 * 				e.g The rook is not allowed to move diagonally...IT IS ILLEGAL
	 */
	private boolean isDirectionLegal;
	
	
	/* The following variables need to be explained !
	 *
	 * EXPLANATION: The following variable is in fact counters. It hold a value that represents 
	 * the maximum distance (in tiles) towards a specific direction that the piece is allowed to move.
	 * 
	 * CAREFUL: A white piece moves forward when it moves up the board
	 * 			A black piece moves forward when it moves down the board
	 */
	private int maxAllowedDistance;
	
	
	
//Constructors
	
	//Default constructor
	Piece()
	{
		this.type = "";
		this.id = 0;
		this.value = 0;
		this.colour = "";
		this.image = null;
		this.movesMade = 0;
		this.isDirectionLegal = false;
		this.maxAllowedDistance = 0;
		this.maxNumberOfPotentialDistance = 0;
		
	}
	
	//Setter constructor
	Piece(String type, int id, int value, String colour, Image image, int movesMade, boolean isDirectionLegal, int maxAllowedDistance, int maxNumberOfPotentialDistance)
	{
		this.type = type;
		this.id = id;
		this.value = value;
		this.colour = colour;
		this.image = image;
		this.movesMade = movesMade;
		this.isDirectionLegal = isDirectionLegal;
		this.maxAllowedDistance = maxAllowedDistance;
		this.maxNumberOfPotentialDistance = maxNumberOfPotentialDistance;
	}
	
	//Object (copy) constructor
	Piece(Piece p)
	{
		this.type = p.type;
		this.id = p.id;
		this.value = p.value;
		this.colour = p.colour;
		this.image = p.image;
		this.movesMade = p.movesMade;
		this.isDirectionLegal = p.isDirectionLegal;
		this.maxAllowedDistance = p.maxAllowedDistance;
		this.maxNumberOfPotentialDistance = p.maxNumberOfPotentialDistance;
	}
	
//Setters
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setId(int id) 
	{
		this.id = id; 
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
	public void setMovesMade(int movesMade)
	{
		this.movesMade = movesMade;
	}
	
	public void setIsDirectionLegal(boolean isDirectionLegal)
	{
		this.isDirectionLegal = isDirectionLegal;
	}
	
	public void setMaxNumberOfPotentialDistance(int maxNumberOfPotentialDistance)
	{
		this.maxNumberOfPotentialDistance = maxNumberOfPotentialDistance;
	}
	
	public void setMaxAllowedDistance(int maxAllowedDistance)
	{
		this.maxAllowedDistance = maxAllowedDistance;
	}
	
//Getters
	public String getType()
	{
		return this.type;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public String getColour()
	{
		return this.colour;
	}
	
	public Image getImage()
	{
		return this.image;
	}
	
	public int getMovesMade()
	{
		return this.movesMade;
	}
	
	public boolean getIsDirectionLegal()
	{
		return this.isDirectionLegal;
	}
	
	public int getMaxNumberOfPotentialDistance()
	{
		return this.maxNumberOfPotentialDistance;
	}
	
	public int getMaxAllowedDistance()
	{
		return this.maxAllowedDistance;
	}
}
