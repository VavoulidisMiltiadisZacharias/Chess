/*
 * Game class
 * 
 * Will act like a JFrame and represent everything within it
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends JFrame implements ActionListener
{
//Variables
	private int move; //A move is complete when both players played (start from 1)
	private int subMove; //Variable that indicates which player plays in EVERY Move
	private Board board;
	//private Player p1, p2; //The opponents
	private Tile[] tiles; //Helping variable to get the tiles from the board
 	private Tile startTile; //Start tile of a move
 	private Tile destinationTile; //Destination tile of a move
 	private int numberOfClicksDone = 0; //Helps with the e.getSource(), to define if it is the start or destination tile
 	private boolean isAllowedToMoveAnything = false; //Helps with the king move
 	int whiteKingPosition = 0;
 	int blackKingPosition = 0;
 	
 	int counter = 1; //Helping variable for checkmate message. Helps not to display 15 times the same message
 	
 	//JLabel that holds the moves' counter
 	JLabel moveLabel = new JLabel();
 	
 	//JLabel that inform that it is "white to move" or "black to move"
 	JLabel colorToMoveLabel = new JLabel();

//Constructor - Will use only one within this class cause.. i'm done with creating constructors
	Game()
	{
		//Set the basics of the JFrame
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setSize(1024, 1024);
		this.setSize(950, 950);
		this.setLayout(null);
		this.setResizable(true);
		
								/*GAME SET UP*/
		
		//Create the 2 opponents
		//p1 = new Player();
		//p2 = new Player();
		//p1.setId(1);
		//p1.setColour("White");
		//p2.setId(2);
		//p2.setColour("Black");
		
		//Create the 8x8 board
		board = new Board(8);
		
		//Place the board within the frame
		board.setBounds(0, 153, 930, 750);
		
		//Getting the tiles
		tiles = board.getTiles(); //Works properly (checked)
		
		for(int i = 0 ; i < board.getDimensions() * board.getDimensions() ; i++)
		{
			tiles[i].addActionListener(this); //Add the action listener interface
		}
		
		//Add the board within the frame in order to represent it
		this.add(board);
		this.setVisible(true);
		
		this.setIsAllowedToMoveAnything(false);
		
		//Set Move as 1
		this.setMove(1);
		this.setSubMove(1);
		
		//White plays first
		//this.setTurn("White");
		
		//Initializes the moveLabel
		moveLabel.setForeground(new Color(0xEA930E)); //Orange
		moveLabel.setBackground(new Color(0x4AF318)); //Light green
		
		moveLabel.setOpaque(true); //To display the background color
		moveLabel.setText("Move: " +this.getMove());
		moveLabel.setHorizontalAlignment(JLabel.CENTER); //Sets text at the center of the label
		moveLabel.setFont(new Font("MV Boli", Font.BOLD, 40));
		moveLabel.setBounds(0, 0, 347, 155); //Place the label within the frame 
		
		//Add it within the frame
		this.add(moveLabel);
		
		//Initialize the colorToMoveLabel
		colorToMoveLabel.setForeground(new Color(0xFFFFFF)); //White
		colorToMoveLabel.setBackground(new Color(0x000000)); //Black
		colorToMoveLabel.setOpaque(true); //To display the background color
		colorToMoveLabel.setText("White to move");
		colorToMoveLabel.setHorizontalAlignment(JLabel.CENTER); //Sets text at the center of the label
		colorToMoveLabel.setFont(new Font("MV Boli", Font.BOLD, 40));
		colorToMoveLabel.setBounds(348, 0, 580, 155); //Place the label within the frame
		
		//Add it within the frame
		this.add(colorToMoveLabel);
		
		System.out.println("Move: " + this.getMove());
		System.out.println("White to move");
		
	}
	
//Setters
	public void setMove(int move)
	{
		this.move = move;
	}
	
	public void setSubMove(int subMove)
	{
		this.subMove = subMove;
	}
	
	public void setNumberOfClicksDone(int numberOfClicksDone)
	{
		this.numberOfClicksDone = numberOfClicksDone;
	}
	
	public void setIsAllowedToMoveAnything(boolean isAllowedToMoveAnything)
	{
		
		this.isAllowedToMoveAnything = isAllowedToMoveAnything;
	}
	
//Getters	
	public int getMove()
	{
		return this.move;
	}
	
	public int getSubMove() 
	{
		return this.subMove;
	}
	
	public int getNumberOfClicksDone()
	{
		return this.numberOfClicksDone;
	}
	
	public boolean getIsAllowedToMoveAnything()
	{
		return this.isAllowedToMoveAnything;
	}
	
	
	
//Methods
	
	/*
	 * Configures the text (number of move to be played) and adds the "moveLabel" label within the frame
	 */
	public void updateMoveLabel()
	{
		moveLabel.setText("Move: " +this.getMove());
		this.add(moveLabel);
	}
	
	/*
	 * Configures and adds the “colorToMoveLabel” label within the frame.
	 */
	public void updateColorToMoveLabel() 
	{	
		//White to move
		if(this.getSubMove() == 1)
		{
			colorToMoveLabel.setForeground(new Color(0xFFFFFF)); //White
			colorToMoveLabel.setBackground(new Color(0x000000)); //Black
			
			colorToMoveLabel.setOpaque(true); //To display the background color
			colorToMoveLabel.setText("White to move");
		}
		
		//Black to move
		else
		{
			colorToMoveLabel.setForeground(new Color(0x000000)); //Black
			colorToMoveLabel.setBackground(new Color(0xFFFFFF)); //White
			
			colorToMoveLabel.setOpaque(true); //To display the background color
			colorToMoveLabel.setText("Black to move");
		}
		
		//Add the colorToMoveLabel (JLabel) within the frame
		this.add(colorToMoveLabel);
			
	}
	
	
	/*
	 * A pop up window appears on the screen to inform the players that checkmate has happened
	 */
	public void informForCheckmate()
	{
		//Create a JLabel with the message "CHECKMATE"
		JLabel checkmateLabel = new JLabel();
		checkmateLabel.setText("CHECKMATE !!!");
		checkmateLabel.setHorizontalAlignment(JLabel.CENTER);
		checkmateLabel.setVerticalAlignment(JLabel.CENTER);
		checkmateLabel.setForeground(new Color(0xFF0000));
		checkmateLabel.setFont(new Font("MV Boli", Font.BOLD, 60));
		
		
		//Create the pop up frame to inform the players
		JFrame checkmateFrame = new JFrame();
		checkmateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkmateFrame.setSize(600, 300);
		checkmateFrame.setVisible(true);
		
		//Add the label within the frame
		checkmateFrame.add(checkmateLabel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(int i = 0; i < board.getDimensions()*board.getDimensions(); i++)
		{
			//Click on a tile
			if(e.getSource() == tiles[i])
			{
				//Start tile is king's tile if he is under threat, else it is whichever tile is clicked
				if(this.getNumberOfClicksDone() == 0)
				{
					startTile = tiles[i];
					
					//White to move
					if(this.getSubMove() == 1)
					{
						//If start tile contains any white piece
						if((startTile.getContainsPiece() == true)
								&& (startTile.getPiece().getColour() == "White"))
						{
							this.setNumberOfClicksDone(1); //Go to destination tile
						}
						
						//If start tile contains black piece or nothing
						else
						{
							this.setNumberOfClicksDone(0); //White still to make move
							this.setSubMove(1); //White still to make move
						}
					}
						
					//Black to move
					else if(this.getSubMove() == 2)
					{
						//If start tile contains any black piece
						if((startTile.getContainsPiece() == true)
								&& (startTile.getPiece().getColour() == "Black"))
						{
							this.setNumberOfClicksDone(1); //Go to destination tile
						}
						
						//If start tile contains white piece or nothing
						else
						{
							this.setNumberOfClicksDone(0); //Black still to make move
							this.setSubMove(2); //Black still to make move
						}
					}	
				}
				
				//Destination tile
				else if(this.getNumberOfClicksDone() == 1)
				{
					destinationTile = tiles[i];
					
					//Check for castling here - Use "if" statement to overcome the next step
					if(board.checkForCastling(tiles, startTile, destinationTile))
					{
						board.resetThreatStatusOfTiles(tiles);
						board.checkAllThreatStatusOfTiles(tiles);
						
						/*
						* If checkmate has happened, the game ends and a pop up window appears
						* to inform the players as such
						*
						*/
						if(board.checkForCheckmate(tiles) == true)
						{
							informForCheckmate();
							System.out.println("CHECKMATE");
						}
						
						//No checkmate has happened...continue the game with the next move
						else
						{
							//If whites' move is valid let black make the next move
							if(this.getSubMove() == 1)
							{
								//Increase moves made by the piece by one
								destinationTile.getPiece().setMovesMade(destinationTile.getPiece().getMovesMade()+1);
								this.setNumberOfClicksDone(0);
								this.setSubMove(2);
								System.out.println("Black to move");
								
								this.updateColorToMoveLabel();
							}
							
							//If blacks' move is valid let white make the next move and update the Move
							else
							{
								//Increase moves made by the piece by one
								destinationTile.getPiece().setMovesMade(destinationTile.getPiece().getMovesMade()+1);
								this.setNumberOfClicksDone(0);
								this.setSubMove(1);
								this.setMove(this.getMove()+1);
								
								this.updateMoveLabel();
								
								System.out.println();
								System.out.println("Move: " +this.getMove());
								System.out.println("White to move");
								
								this.updateColorToMoveLabel();
							}	
						}
					}
					
					//If move is valid from every aspect
					else if(board.makeMove(tiles, startTile, destinationTile) == true)
					{
						
						//Check if any pawn's promotion is possible
						board.checkForPromotion(destinationTile);
						
						board.setButtonsActionListener(new ActionListener()
						{
						        @Override
								public void actionPerformed(ActionEvent e)
						        {
						           //White promote
						           if(e.getSource() == board.whiteRookButton)
						           {
						        	   //Close the promotion frame
						        	   board.whitePromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Rook");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.whiteKnightButton)
						           {
						        	   //Close the promotion frame
						        	   board.whitePromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Knight");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.whiteBishopButton)
						           {
						        	   //Close the promotion frame
						        	   board.whitePromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Bishop");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.whiteQueenButton)
						           {
						        	   //Close the promotion frame
						        	   board.whitePromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Queen");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           
						           //Black promote
						           else if(e.getSource() == board.blackRookButton)
						           {
						        	   //Close the promotion frame
						        	   board.blackPromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Rook");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.blackKnightButton)
						           {
						        	   //Close the promotion frame
						        	   board.blackPromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Knight");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.blackBishopButton)
						           {
						        	   //Close the promotion frame
						        	   board.blackPromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Bishop");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						           else if(e.getSource() == board.blackQueenButton)
						           {
						        	   //Close the promotion frame
						        	   board.blackPromotionFrame.dispose();
						        	   
						        	   board.makePromotion(destinationTile, "Queen");
						        	   
						        	   if((board.checkForCheckmate(tiles) == true)
						        			   && (counter == 1))
										{
						        		   informForCheckmate();
						        		   System.out.println(counter);
						        		   counter++;
						        		   System.out.println("CHECKMATE");
										}
						           }
						        }
						});
						
						/*
						* If checkmate has happened, the game ends and a pop up window appears
						* to inform the players as such
						*
						*/
						if(board.checkForCheckmate(tiles) == true)
						{
							informForCheckmate();
							System.out.println("CHECKMATE");
						}
						
						//No checkmate has happened...continue the game with the next move
						else
						{
							//If whites' move is valid let black make the next move
							if(this.getSubMove() == 1)
							{
								//Increase moves made by the piece by one
								destinationTile.getPiece().setMovesMade(destinationTile.getPiece().getMovesMade()+1);
								this.setNumberOfClicksDone(0);
								this.setSubMove(2);
								System.out.println("Black to move");
								
								this.updateColorToMoveLabel();
							}
							
							//If blacks' move is valid let white make the next move and update the Move
							else
							{
								//Increase moves made by the piece by one
								destinationTile.getPiece().setMovesMade(destinationTile.getPiece().getMovesMade()+1);
								this.setNumberOfClicksDone(0);
								this.setSubMove(1);
								this.setMove(this.getMove()+1);
								
								this.updateMoveLabel();
								
								System.out.println();
								System.out.println("Move: " +this.getMove());
								System.out.println("White to move");
								
								this.updateColorToMoveLabel();
							}	
						}
					}
					
					//If move is invalid play another move (same coloured pieces to move)
					else
					{
						this.setNumberOfClicksDone(0);
						System.out.println("Invalid move");
					}
				}
			}		
		}	
	}
}
