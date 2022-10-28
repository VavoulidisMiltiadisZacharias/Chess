/*
 * The player(s) of the chess board
 * 
 * There are 2 players (opponents) trying to beat one another
 * 
 */

public class Player 
{
//Variables
	private int id; //the id of the player (1 or 2)
	private String colour; //the pieces' colour of the player (White or Black)

//Constructors
	
	//Default Constructor
	Player()
	{
		this.id = 0;
		this.colour = "";
	}
	
	//Setter constructor
	Player(int id, String colour)
	{
		this.id = id;
		this.colour = colour;
	}
	
	//Object (copy) constructor
	Player(Player p)
	{
		this.id = p.id;
		this.colour = p.colour;
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
	
//Getters
	public int getId()
	{
		return this.id;
	}
	
	public String getColour()
	{
		return this.colour;
	}
//Methods
	
}
