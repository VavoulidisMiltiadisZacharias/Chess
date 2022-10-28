/*
 * The chess board class
 * 
 * In fact it a JPanel
 * 
 * Within the board there will be the tiles and the pieces (maybe score, turn etc)
 * 
 */
//-----------------------------------------------------------------------------//
	/* STUFF TO DO
	 * 
	 * Create the board
	 * 
	 * Fill it with tiles
	 * 
	 * Fill it with pieces (At first the starting board)
	 * 
	 */
	//------------------------------------------------------------------------------//

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Board extends JPanel
{
	
//Variables
	private int dimensions; //dimensions = 8 (board = 8x8 square)
	private Tile tiles[]; //An array of tiles (will be 8x8 = 64 for the representation)
	private Piece pieces[]; //An array of pieces(will hold all the starting pieces - not the promoted ones)
	
	//Images to represent the Pieces
	private Image whiteRook;
	private Image whiteKnight;
	private Image whiteBishop;
	private Image whiteKing;
	private Image whiteQueen; 
	private Image whitePawn;
	private Image blackRook;
	private Image blackKnight;
	private Image blackBishop;
	private Image blackKing;
	private Image blackQueen; 
	private Image blackPawn;
	
	
	//Pop up windows when promotion happens
	JFrame whitePromotionFrame = new JFrame();
	JFrame blackPromotionFrame = new JFrame();
	
	//The buttons within the white pop up window for the promotion
	JButton whiteRookButton = new JButton();
	JButton whiteKnightButton = new JButton();
	JButton whiteBishopButton = new JButton();
	JButton whiteQueenButton = new JButton();
	
	//The buttons within the white pop up window for the promotion
	JButton blackRookButton = new JButton();
	JButton blackKnightButton = new JButton();
	JButton blackBishopButton = new JButton();
	JButton blackQueenButton = new JButton();
	
	/*
	 * The id of the promoted piece. Needs to be incremented after every promotion just to ensure that 
	 * two newly created pieces (after promotion) will NOT have the same variables but different. 
	 * In fact, this is the only variable that differentiates them (e.g if white promotes into two 
	 * rook the only variable that the two rooks will NOT have in common is the id, due to the 
	 * increment that it undergoes after every promotion)
	 */
	private int newPieceId = 3;
	
	
//Constructors
	
	//Default constructor
	Board()
	{
		this.dimensions = 0;
		
		tiles = new Tile[dimensions * dimensions];
		for(int i = 0; i < dimensions * dimensions; i++)
		{
			tiles[i] = new Tile();
		}
	}
	
	//Setter constructor - Call this for the starting board
	Board(int dimensions)
	{	
		this.setDimensions(dimensions); //Set the dimensions of the chess board
		this.setLayout(new GridLayout(getDimensions(),getDimensions())); //Set the layout to a "8x8 Board"
		
		//Allocate memory for 32 pieces
		pieces = new Piece[32];
		
		// Allocate memory for 64 (from 0 to 63) tiles 
		Tile[] tiles = new Tile[getDimensions()*getDimensions()];
	
		//Create the images as imageIcons to represent the pieces 
		whiteRook = new ImageIcon("White Rook.png").getImage();  
		whiteKnight = new ImageIcon("White knight.png").getImage();
		whiteBishop = new ImageIcon("White Bishop.png").getImage();
		whiteKing = new ImageIcon("White King.png").getImage();
		whiteQueen = new ImageIcon("White Queen.png").getImage();
		whitePawn = new ImageIcon("White Pawn.png").getImage();
		blackRook = new ImageIcon("Black Rook.png").getImage();
		blackKnight = new ImageIcon("Black knight.png").getImage();
		blackBishop = new ImageIcon("Black Bishop.png").getImage();
		blackKing = new ImageIcon("Black King.png").getImage();
		blackQueen = new ImageIcon("Black Queen.png").getImage();
		blackPawn = new ImageIcon("Black Pawn.png").getImage();	
		
		
		//Create the pieces
		this.createPieces(pieces);
		
		//Create the starting board
		this.createStartingBoard(tiles, pieces);
				
	}
	
	//Object (copy) constructor
	Board(Board b)
	{
		this.dimensions = b.dimensions ; 
		
		tiles = new Tile[dimensions * dimensions];
		for(int i = 0; i < dimensions * dimensions; i++)
		{
			b.tiles[i] = new Tile();
		}
	}
	
//Setters
	public void setDimensions(int dimensions)
	{
		this.dimensions = dimensions;
	}
	
	public void setTiles(Tile[] tiles)
	{
		this.tiles = tiles;
	}
	
//Getters
	public int getDimensions()
	{
		return this.dimensions;
	}
	
	public Tile[] getTiles()
	{
		return this.tiles;
	}
	

	
	
//Methods
	
	//Creates all the pieces 
	public void createPieces(Piece[] pieces)
	{	
		//Not sure if the following 3 lines are needed
		for(int i = 0 ; i < 4*getDimensions() ; i++)
		{
			pieces[i] = new Piece() ; 
		}
		
		//Set the characteristics 
		for(int i = 0 ; i < 4*getDimensions() ; i++)
		{
			if(i < 16) //Pawns
			{
				if(i < 8) //White pawns
				{
					pieces[i].setType("Pawn") ;
					pieces[i].setId(i+1) ;
					pieces[i].setValue(1) ;
					pieces[i].setColour("White") ;
					pieces[i].setImage(whitePawn);
					pieces[i].setMovesMade(0);
				}
				else //Black pawns
				{
					pieces[i].setType("Pawn") ;
					pieces[i].setId(2*getDimensions()-i) ;
					pieces[i].setValue(1) ;
					pieces[i].setColour("Black") ;
					pieces[i].setImage(blackPawn);
					pieces[i].setMovesMade(0);
				}
			}
			
			else if(i > 15 && i < 20) //Rooks
			{
				if(i < 18) //White rooks
				{
					if(i == 16) //Rook1
					{
						pieces[i].setType("Rook") ;
						pieces[i].setId(1) ;
						pieces[i].setValue(5) ;
						pieces[i].setColour("White") ;
						pieces[i].setImage(whiteRook);
						pieces[i].setMovesMade(0);
					}
					else //Rook2
					{
						pieces[i].setType("Rook") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(5);
						pieces[i].setColour("White");
						pieces[i].setImage(whiteRook);
						pieces[i].setMovesMade(0);
					}
				}
				else //Black rooks
				{
					if(i == 18) //Rook1
					{
						pieces[i].setType("Rook") ;
						pieces[i].setId(1) ;
						pieces[i].setValue(5);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackRook);
						pieces[i].setMovesMade(0);
					}
					else //Rook2
					{
						pieces[i].setType("Rook") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(5);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackRook);
						pieces[i].setMovesMade(0);
					}
				}	
			}
			else if(i > 19 && i < 24) //Knights
			{
				if(i < 22) //White Knights
				{
					if(i == 20) //Knight1
					{
						pieces[i].setType("Knight") ;
						pieces[i].setId(1) ;
						pieces[i].setValue(3);
						pieces[i].setColour("White");
						pieces[i].setImage(whiteKnight);
						pieces[i].setMovesMade(0);
					}
					else //Knight2
					{
						pieces[i].setType("Knight") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(3);
						pieces[i].setColour("White");
						pieces[i].setImage(whiteKnight);
						pieces[i].setMovesMade(0);
					}
				}
				else //Black knight
				{
					if(i == 22) //Knight1
					{
						pieces[i].setType("Knight") ;
						pieces[i].setId(1) ;
						pieces[i].setValue(3);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackKnight);
						pieces[i].setMovesMade(0);
					}
					else //Knight2
					{
						pieces[i].setType("Knight") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(3);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackKnight);
						pieces[i].setMovesMade(0);
					}
				}
			}
			else if(i > 23 && i < 28) //Bishops
			{
				if(i < 26) //White bishop
				{
					if(i == 24) //Bishop1
					{
						pieces[i].setType("Bishop") ;
						pieces[i].setId(1) ;
						pieces[i].setValue(3);
						pieces[i].setColour("White");
						pieces[i].setImage(whiteBishop);
						pieces[i].setMovesMade(0);
					}
					else //Bishop2
					{
						pieces[i].setType("Bishop") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(3);
						pieces[i].setColour("White");
						pieces[i].setImage(whiteBishop);
						pieces[i].setMovesMade(0);
					}
				}
				else //Black bishop
				{
					if(i == 26) //Bishop1
					{
						pieces[i].setType("Bishop") ;					
						pieces[i].setId(1) ;
						pieces[i].setValue(3);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackBishop);
						pieces[i].setMovesMade(0);
					}
					else //Bishop2
					{
						pieces[i].setType("Bishop") ;
						pieces[i].setId(2) ;
						pieces[i].setValue(3);
						pieces[i].setColour("Black");
						pieces[i].setImage(blackBishop);
						pieces[i].setMovesMade(0);
					}
				}
			}
			else if(i > 27 && i < 30) //Queens
			{
				if(i < 29) //White queen
				{
					pieces[i].setType("Queen") ;
					pieces[i].setId(1) ;
					pieces[i].setValue(9);
					pieces[i].setColour("White");
					pieces[i].setImage(whiteQueen);
					pieces[i].setMovesMade(0);
				}
				else //Black queen
				{
					pieces[i].setType("Queen") ;
					pieces[i].setId(1) ;
					pieces[i].setValue(3);
					pieces[i].setColour("Black");
					pieces[i].setImage(blackQueen);
					pieces[i].setMovesMade(0);
				}
			}
			else
			{
				if(i < 31) //White king
				{
					pieces[i].setType("King") ;
					pieces[i].setId(1) ;
					pieces[i].setValue(3);
					pieces[i].setColour("White");
					pieces[i].setImage(whiteKing);
					pieces[i].setMovesMade(0);
				}
				else  //Black king
				{
					pieces[i].setType("King") ;
					pieces[i].setId(1) ;
					pieces[i].setValue(3);
					pieces[i].setColour("Black");
					pieces[i].setImage(blackKing);
					pieces[i].setMovesMade(0);
				}
			}
		}
	}
	
	/*
	 * Creates starting board - Used only 1 time within the setter constructor
	 * to create the starting board. A combination of other functions in used to recreate (update) 
	 * the board after every move of each player 
	*/
	public void createStartingBoard(Tile[] tiles, Piece[] pieces)
	{
		//For each one of the tiles set their id and their corresponding colour
		for(int i = 0 ; i < getDimensions() ; i++)
		{
			for(int j = 0 ; j < getDimensions() ; j++)
			{
				tiles[j+getDimensions()*i] = new Tile(); //Create each tile
				tiles[j+getDimensions()*i].setId((j+getDimensions()*i)); //Set the id of each tile

				/* Open the following line to demonstrate tiles' ids */
				tiles[j+getDimensions()*i].setText(Integer.toString(tiles[j+getDimensions()*i].getId())); //Helping line
		
				//Set the corresponding colour for each tile
				if((i%2 == 0) && (j%2 == 0))
				{
					tiles[j+getDimensions()*i].setBackground(new Color(120,120,120)); //Black
					tiles[j+getDimensions()*i].setColour("Black");
				}
				
				else if((i%2 == 0) && (j%2 != 0))
				{
					tiles[j+getDimensions()*i].setBackground(new Color(255,255,255)); //White
					tiles[j+getDimensions()*i].setColour("White");
				}
					
				else if((i%2 != 0) && (j%2 == 0))
				{
					tiles[j+getDimensions()*i].setBackground(new Color(255,255,255));//White
					tiles[j+getDimensions()*i].setColour("White");
				}
					
				else
				{
					tiles[j+getDimensions()*i].setBackground(new Color(120,120,120));//Gray
					tiles[j+getDimensions()*i].setColour("Black");
				}
					
			}
		}
		
		//Configure some stuff and add all the tiles within the panel in a correct layout
		for(int i = 0 ; i < getDimensions() ; i++)
		{
			for(int j = 0 ; j < getDimensions() ; j++)
			{
				this.add(tiles[getDimensions()*getDimensions() - (i+1)*getDimensions() + j]); //"Correct" the layout, place the first tile bottom left 
				tiles[j*getDimensions()+i].setFocusable(false);
				tiles[j*getDimensions()+i].setVisible(true); //It is true by default (works fine without this line) but i deliberately wrïte it 
				
				//Set status for all tiles
				if((j*getDimensions()+i < 16) || (j*getDimensions()+i > 47))
					tiles[j*getDimensions()+i].setContainsPiece(true);
				else //Empty tiles
				{
					tiles[j*getDimensions()+i].setContainsPiece(false);
				}
				//tiles[i].addActionListener(this);
				this.setTiles(tiles);
			}
		}	
		
		//Set the pieces to the tiles (Add pieces within the correct tiles) - White pieces
		tiles[0].setPiece(pieces[16]); //White Rook
		tiles[1].setPiece(pieces[20]); //White Knight
		tiles[2].setPiece(pieces[24]); //White Bishop
		tiles[3].setPiece(pieces[28]); //White Queen
		tiles[4].setPiece(pieces[30]); //White King
		tiles[5].setPiece(pieces[25]); //White Bishop
		tiles[6].setPiece(pieces[21]); //White Knight
		tiles[7].setPiece(pieces[17]); //White Rook
		for(int i = 0 ; i < getDimensions(); i++)
		{
			tiles[i+getDimensions()].setPiece(pieces[i]); //White Pawns
		}
		//Set the pieces to the tiles (Add pieces within the correct tiles) - Black pieces
		tiles[63].setPiece(pieces[18]); //Black Rook
		tiles[62].setPiece(pieces[22]); //Black Knight
		tiles[61].setPiece(pieces[26]); //Black Bishop
		tiles[60].setPiece(pieces[31]); //Black King
		tiles[59].setPiece(pieces[29]); //Black Queen
		tiles[58].setPiece(pieces[27]); //Black Bishop
		tiles[57].setPiece(pieces[23]); //Black Knight
		tiles[56].setPiece(pieces[19]); //Black Rook
		tiles[48].setPiece(pieces[8]);  //Black Pawn
		tiles[49].setPiece(pieces[9]);  //Black Pawn
		tiles[50].setPiece(pieces[10]); //Black Pawn
		tiles[51].setPiece(pieces[11]); //Black Pawn
		tiles[52].setPiece(pieces[12]); //Black Pawn
		tiles[53].setPiece(pieces[13]); //Black Pawn
		tiles[54].setPiece(pieces[14]); //Black Pawn
		tiles[55].setPiece(pieces[15]); //Black Pawn
		
		//Set the icons to the tiles
		tiles[0].setIcon(new ImageIcon(pieces[16].getImage())); //White Rook
		tiles[1].setIcon(new ImageIcon(pieces[20].getImage())); //White Knight
		tiles[2].setIcon(new ImageIcon(pieces[24].getImage())); //White Bishop
		tiles[3].setIcon(new ImageIcon(pieces[28].getImage())); //White Queen
		tiles[4].setIcon(new ImageIcon(pieces[30].getImage())); //White King
		tiles[5].setIcon(new ImageIcon(pieces[25].getImage())); //White Bishop
		tiles[6].setIcon(new ImageIcon(pieces[21].getImage())); //White Knight
		tiles[7].setIcon(new ImageIcon(pieces[17].getImage())); //White Rook
		for(int i = 0 ; i < getDimensions() ; i++)
		{
			tiles[i+getDimensions()].setIcon(new ImageIcon(pieces[i].getImage())); //White Pawns
		}
		tiles[63].setIcon(new ImageIcon(pieces[18].getImage())); //Black Rook
		tiles[62].setIcon(new ImageIcon(pieces[22].getImage())); //Black Knight
		tiles[61].setIcon(new ImageIcon(pieces[26].getImage())); //Black Bishop
		tiles[60].setIcon(new ImageIcon(pieces[31].getImage())); //Black King
		tiles[59].setIcon(new ImageIcon(pieces[29].getImage())); //Black Queen
		tiles[58].setIcon(new ImageIcon(pieces[27].getImage())); //Black Bishop
		tiles[57].setIcon(new ImageIcon(pieces[23].getImage())); //Black Knight
		tiles[56].setIcon(new ImageIcon(pieces[19].getImage())); //Black Rook
		for(int i = 48 ; i < getDimensions()*getDimensions()-getDimensions() ; i++)
		{
			tiles[i].setIcon(new ImageIcon(pieces[8].getImage())); //Black Pawns
		}
		
		
		//Set tiles' threat status
		for(int i = 0 ; i < this.getDimensions()*this.getDimensions() ; i++)
		{
			//2nd and 5th row is all under threat
			if((i > 15 && i < 24))
			{
				tiles[i].setIsUnderThreatFromWhite(true);
			}
			else if((i > 39 && i < 48))
				tiles[i].setIsUnderThreatFromBlack(true);
			else
			{
				tiles[i].setIsUnderThreatFromWhite(false);
				tiles[i].setIsUnderThreatFromBlack(false);
			}
		}
	}
	
	/*
	 * Resets the "under threat" status of EVERY tile, after every move
	 * 
	 * @param1: tiles -> All the tiles
	 * 
	 */
	public void resetThreatStatusOfTiles(Tile[] tiles)
	{
		//Check for every tile
		for(int i = 0 ; i < this.getDimensions()*this.getDimensions() ; i++)
		{
			tiles[i].setIsUnderThreatFromWhite(false);
			tiles[i].setIsUnderThreatFromBlack(false);
		}
	}
	
	/*
	 * Checks if any pawn sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkPawnThreatStatusOfTiles(Tile tile)
	{
		//White pawns
		if(tile.getPiece().getColour() == "White") 
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) Zero and 7th row
			 * 2) Zero and 7th column
			 * 
			 */
			if(((tile.getId() > 7) && (tile.getId() < 56))
					&& ((tile.getId() % 8) != 0) && ((tile.getId() % 8) != 7))
			{
				/*
				 * Set forward right and forward left "under threat" status
				 * 
				 * EXPLANATION for forward right (same thinking on forward left)
				 * Any forward right tile is under threat REGARDLESS of what it contains 
				 * because, for example, if the black king is checked on C3 by any white 
				 * piece and on C4 there is a white piece BUT C4 is under threat by 
				 * another white piece then the black king MUST NOT BE ALLOWED TO MOVE 
				 * ON C4. 
				 * 
				 * So, for this reason it is set to "under threat" status anyways and i do not 
				 * care if there is any white/black piece inside it.
				 * 
				 */
				tiles[tile.getId()+9].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+7].setIsUnderThreatFromWhite(true);
			}
			
			/*
			 * Zero column
			 * 
			 * Exclude:
			 * 1) Zero element
			 * 2) Last element
			 * 
			 */
			else if( ((tile.getId() % 8) == 0)
					&& (tile.getId() != 0)
					&& (tile.getId()) != 56)
			{
				//Set forward right "under threat" status
				tiles[tile.getId()+9].setIsUnderThreatFromWhite(true);
			}
			
			/*
			 * 7th column
			 * 
			 * Exclude:
			 * 1) Zero element
			 * 2) Last element
			 * 
			 */
			else if( ((tile.getId() % 8) == 7)
					&& (tile.getId() != 7)
					&& (tile.getId()) != 63)
			{
				//Set forward left "under threat" status
				tiles[tile.getId()+7].setIsUnderThreatFromWhite(true);
			}
		}
		
		//Black pawns
		else if(tile.getPiece().getColour() == "Black")
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) 7th and zero row
			 * 2) 7th and zero column
			 * 
			 */
			if(((tile.getId() > 7) && (tile.getId() < 56))
					&& ((tile.getId() % 8) != 0) && ((tile.getId() % 8) != 7))
			{
				//Set forward right and forward left "under threat" status
				tiles[tile.getId()-9].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-7].setIsUnderThreatFromBlack(true);
			}
			
			/*
			 * 7th column
			 * 
			 * Exclude:
			 * 1) Last element
			 * 2) Zero element
			 * 
			 */
			else if( ((tile.getId() % 8) == 7)
					&& (tile.getId() != 63)
					&& (tile.getId()) != 7)
			{
				//Set forward right "under threat" status
				tiles[tile.getId()-9].setIsUnderThreatFromBlack(true);
			}
			
			/*
			 * Zero column
			 * 
			 * Exclude:
			 * 1) Last element
			 * 2) Zero element
			 * 
			 */
			else if( ((tile.getId() % 8) == 0)
					&& (tile.getId() != 56)
					&& (tile.getId()) != 0)
			{
				//Set forward left "under threat" status
				tiles[tile.getId()-7].setIsUnderThreatFromBlack(true);
			}
		}
	}
	
	/*
	 * Checks if any rook sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkRookThreatStatusOfTiles(Tile tile)
	{
		//White rooks
		if(tile.getPiece().getColour() == "White")
		{
			/* Forward
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() / 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
					
					/*
					 * If tile that is being checked contains opponent king, do not stop checking 
					 * (do not break). Instead, move on to check next tile towards that direction
					 * in order to e.g not allow opponent the king to move backwards to a tile
					 * that is under threat. 
					 */ 
					if(tiles[tile.getId()+(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Right
			 * 
			 * The quantity "(7-(tile.getId()%8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() % 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards
			 * 
			 * The quantity "tiles[j].getId()/8" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() / 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Left
			 * 
			 * The quantity "tiles[j].getId()%8" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() % 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
		
		//Black rooks
		else if(tile.getPiece().getColour() == "Black")
		{
			/* Forward
			 * 
			 * The quantity "tiles[j].getId()/8" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() / 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Right
			 * 
			 * The quantity "tiles[j].getId()%8" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() % 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() / 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Left
			 * 
			 * The quantity "(7-(tile.getId()%8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() % 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
	}
	
	/*
	 * Checks if any knight sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkKnightThreatStatusOfTiles(Tile tile)
	{
		//White knights
		if(tile.getPiece().getColour() == "White")
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) Zero and 1st and 6th and 7th row
			 * 2) Zero and 1st and 6th and 7th column
			 * 
			 */
			if(((tile.getId() > 17) && (tile.getId() < 46))
					&& ((tile.getId() % 8) != 0)
					&& ((tile.getId() % 8) != 1)
					&& ((tile.getId() % 8) != 6)
					&& ((tile.getId() % 8) != 7) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 * BackwardsLeft Long L
				 * BackwardsLeft Short L
				 *
				 */
				tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
			}
			
			//1st row
			else if(((tile.getId() / 8) == 1))
			{
				
				if(tile.getId() == 8)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);

				}
				
				else if(tile.getId() == 9)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);	
				}
				
				else if(tile.getId() == 14)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				else if(tile.getId() == 15)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				//10 - 13
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsRight Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);
				}
			}
			
			//Zero row
			else if(((tile.getId() / 8) == 0))
			{
				
				if(tile.getId() == 0)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);

				}
				
				else if(tile.getId() == 1)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
				}
				
				else if(tile.getId() == 6)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				}
				
				else if(tile.getId() == 7)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				}
				
				//2-5
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				}
			}
			
			//6th column ONLY from 22 to 46
			else if(((tile.getId() % 8) == 6)
					&& (tile.getId() >= 22) 
					&& (tile.getId() <= 46) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsRight Long L
				 * BackwardsLeft Long L
				 * BackwardsLeft Short L
				 *
				 */
				tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
			}
			
			//7th column ONLY from 23 to 47
			else if(  ((tile.getId() % 8) == 7)
					&& (tile.getId() >= 23) 
					&& (tile.getId() <= 47) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsLeft Long L
				 * BakcwardsLeft Short L
				 *
				 */
				tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
			}
			
			//6th row
			else if(((tile.getId() / 8) == 6))
			{
				
				if(tile.getId() == 55)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				else if(tile.getId() == 54)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Short L
					 * BackwardsRight Long L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				else if (tile.getId() == 49)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 *
					 */
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
				}
				
				else if (tile.getId() == 48)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
				}
				
				//50 - 53
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * ForwardLeft Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
			}
			
			//7th row
			else if(((tile.getId() / 8) == 7))
			{
				if(tile.getId() == 63)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				else if(tile.getId() == 62)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
				
				else if(tile.getId() == 57)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
				}
				
				else if(tile.getId() == 56)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
				}
				
				//58-61
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-10].setIsUnderThreatFromWhite(true);	
				}
			}
			
			//1st column ONLY from 17 to 41
			else if(((tile.getId() % 8) == 1)
					&& (tile.getId() >= 17) 
					&& (tile.getId() <= 41) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * ForwardLeft Long L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 * BackwardsLeft Long L
				 *
				 */
				tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-17].setIsUnderThreatFromWhite(true);
			}
			
			//Zero column ONLY from 16 to 40
			else if(((tile.getId() % 8) == 0)
					&& (tile.getId() >= 16) 
					&& (tile.getId() <= 40) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 *
				 */
				tiles[tile.getId()+17].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+10].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-15].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-6].setIsUnderThreatFromWhite(true);
			}
		}
		
		//Black knights
		else if(tile.getPiece().getColour() == "Black")
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) 7th and 6th and 1st and zero row
			 * 2) 7th and 6th and 1st and zero column
			 * 
			 */
			if(((tile.getId() < 46) && (tile.getId() > 17))
					&& ((tile.getId() % 8) != 7)
					&& ((tile.getId() % 8) != 6)
					&& ((tile.getId() % 8) != 1)
					&& ((tile.getId() % 8) != 0) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 * BackwardsLeft Long L
				 * BackwardsLeft Short L
				 *
				 */
				tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
			}
			
			//6th row
			else if(((tile.getId() / 8) == 6))
			{
				
				if(tile.getId() == 55)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);

				}
				
				else if(tile.getId() == 54)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);	
				}
				
				else if(tile.getId() == 49)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				else if(tile.getId() == 48)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				//50 - 53
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 * BackwardsRight Short L
					 * BackwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);
				}
			}
			
			//7th row
			else if(((tile.getId() / 8) == 7))
			{
				
				if(tile.getId() == 63)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);

				}
				
				else if(tile.getId() == 62)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
				}
				
				else if(tile.getId() == 57)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				}
				
				else if(tile.getId() == 56)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				}
				
				//58-61
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Long L
					 * ForwardRight Short L
					 * ForwardLeft Long L
					 * ForwardLeft Short L
					 *
					 */
					tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				}
			}
			
			//1st column ONLY from 41 to 17
			else if(((tile.getId() % 8) == 1)
					&& (tile.getId() <= 41) 
					&& (tile.getId() >= 17) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsRight Long L
				 * BackwardsLeft Long L
				 * BackwardsLeft Short L
				 *
				 */
				tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
			}
			
			//Zero column ONLY from 40 to 16
			else if(  ((tile.getId() % 8) == 0)
					&& (tile.getId() <= 40) 
					&& (tile.getId() >= 16) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardLeft Long L
				 * ForwardLeft Short L
				 * BackwardsLeft Long L
				 * BakcwardsLeft Short L
				 *
				 */
				tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
			}
			
			//1st row
			else if(((tile.getId() / 8) == 1))
			{
				
				if(tile.getId() == 8)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				else if(tile.getId() == 9)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardLeft Short L
					 * BackwardsRight Long L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				else if (tile.getId() == 14)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 *
					 */
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
				}
				
				else if (tile.getId() == 15)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
				}
				
				//10 - 13
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * ForwardRight Short L
					 * ForwardLeft Short L
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
			}
			
			//Zero row
			else if(((tile.getId() / 8) == 0))
			{
				if(tile.getId() == 0)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				else if(tile.getId() == 1)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
				
				else if(tile.getId() == 6)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
				}
				
				else if(tile.getId() == 7)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
				}
				
				//2-5
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * BackwardsRight Long L
					 * BackwardsRight Short L
					 * BackwardsLeft Long L
					 * BakcwardsLeft Short L
					 *
					 */
					tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+10].setIsUnderThreatFromBlack(true);	
				}
			}
			
			//6th column ONLY from 46 to 22
			else if(((tile.getId() % 8) == 6)
					&& (tile.getId() <= 22) 
					&& (tile.getId() >= 46) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * ForwardLeft Long L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 * BackwardsLeft Long L
				 *
				 */
				tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+17].setIsUnderThreatFromBlack(true);
			}
			
			//7th column ONLY from 47 to 23
			else if(((tile.getId() % 8) == 7)
					&& (tile.getId() <= 47) 
					&& (tile.getId() >= 23) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * ForwardRight Long L
				 * ForwardRight Short L
				 * BackwardsRight Long L
				 * BackwardsRight Short L
				 *
				 */
				tiles[tile.getId()-17].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-10].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+15].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+6].setIsUnderThreatFromBlack(true);
			}
		}
	}
	
	/*
	 * Checks if any bishop sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkBishopThreatStatusOfTiles(Tile tile)
	{
		//White Bishops
		if(tile.getPiece().getColour() == "White")
		{
			/* Forward right
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the 7th column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((7-(tile.getId()/8)) > (7-(tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					//System.out.println(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getId());
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward left
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((7-(tile.getId()/8)) > (tile.getId()%8))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards right
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the 7th column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((tile.getId()/8) > (7-(tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards left
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the zero column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((tile.getId()/8) > (tile.getId()%8))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j <  tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
		
		//Black Bishops
		else if(tile.getPiece().getColour() == "Black")
		{
			/* Forward right
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((tile.getId()/8)) > ((tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward left
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(7 - tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((tile.getId()/8)) > ((7-(tile.getId()%8))))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards right
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((7 - (tile.getId()/8))) > ((tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards left
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "7 - (tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((7 - (tile.getId()/8))) > ((7 - (tile.getId()%8))))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7 - (tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
	}
	
	/*
	 * Checks if any queen sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkQueenThreatStatusOfTiles(Tile tile)
	{
		//White Queen
		if(tile.getPiece().getColour() == "White")
		{
			/* Forward
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() / 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
					
					/*
					 * If tile that is being checked contains opponent king, do not stop checking 
					 * (do not break). Instead, move on to check next tile towards that direction
					 * in order to e.g not allow opponent the king to move backwards to a tile
					 * that is under threat. 
					 */ 
					if(tiles[tile.getId()+(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward right
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the 7th column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((7-(tile.getId()/8)) > (7-(tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Right
			 * 
			 * The quantity "(7-(tile.getId()%8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() % 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards right
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the 7th column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((tile.getId()/8) > (7-(tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards
			 * 
			 * The quantity "tiles[j].getId()/8" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() / 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards left
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from 
			 * the tile that the piece lies on, to the zero column
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((tile.getId()/8) > (tile.getId()%8))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j <  tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Left
			 * 
			 * The quantity "tiles[j].getId()%8" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() % 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromWhite(true);
					if(tiles[tile.getId()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward left
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if((7-(tile.getId()/8)) > (tile.getId()%8))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromWhite(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
		
		//Black Queen
		else if(tile.getPiece().getColour() == "Black")
		{
			/* Forward
			 * 
			 * The quantity "tiles[j].getId()/8" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() / 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward right
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((tile.getId()/8)) > ((tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Right
			 * 
			 * The quantity "tiles[j].getId()%8" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < tile.getId() % 8 ; j++)
			{
				if(tiles[tile.getId()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards right
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "(tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((7 - (tile.getId()/8))) > ((tile.getId()%8)))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()%8));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()-(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() / 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Backwards left
			 * 
			 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th row of the board 
			 * 
			 * The quantity "7 - (tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((7 - (tile.getId()/8))) > ((7 - (tile.getId()%8))))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7 - (tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()/8)));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()+(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Left
			 * 
			 * The quantity "(7-(tile.getId()%8))" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			for(int j = 0 ; j < (7 - (tile.getId() % 8)) ; j++)
			{
				if(tiles[tile.getId()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()+(j+1)].setIsUnderThreatFromBlack(true);
					if(tiles[tile.getId()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
			
			/* Forward left
			 * 
			 * The quantity "(tile.getId()/8)" is the number of tiles from
			 * the tile that the piece lies on, to the zero row of the board 
			 * 
			 * The quantity "(7 - tile.getId()%8)" is the number of tiles from
			 * the tile that the piece lies on, to the 7th column of the board 
			 * 
			 * Set the "under threat" status as true, till it gets blocked by any piece
			 * on its path. In fact, whenever it is blocked by any piece just set the 
			 * "under threat" status as true and then break (exit the loop) 
			 * 
			 */
			
			//Find max number of potential distance in order not to throw ArrayOutOfBounds
			if(((tile.getId()/8)) > ((7-(tile.getId()%8))))
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tile.getId()%8)));
			else
				tiles[tile.getId()].getPiece().setMaxNumberOfPotentialDistance((tile.getId()/8));
			
			for(int j = 0 ; j < tiles[tile.getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
			{
				if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getContainsPiece() == false)
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
				else
				{
					tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].setIsUnderThreatFromBlack(true);
					
					if(tiles[tile.getId()-(j+1)*this.getDimensions()+(j+1)].getPiece().getType() != "King")
						break;
					else
						continue;
				}
			}
		}
	}
	
	/*
	 * Checks if any king sets in "under threat" state any tile
	 * 
	 * @param1: tile -> The starting tile from which all the reachable ones will be checked
	 * 
	 */
	public void checkKingThreatStatusOfTiles(Tile tile)
	{
		//White King
		if(tile.getPiece().getColour() == "White")
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) Zero 7th row
			 * 2) Zero 7th column
			 * 
			 */
			if(((tile.getId() > 8) && (tile.getId() < 55))
					&& ((tile.getId() % 8) != 0)
					&& ((tile.getId() % 8) != 7) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * ForwardRight
				 * Right
				 * BackwardsRight
				 * Backwards
				 * BackwardsLeft
				 * Left
				 * ForwardLeft
				 *
				 */
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);	
			}
			
			
			//First row ONLY zero and 7th element
			else if(((tile.getId() / 8) == 1)
					&& ((tile.getId() == 8) || (tile.getId() == 15)))
			{
				//Zero element
				if(tile.getId() == 8)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
				}
				
				//7th element
				else if(tile.getId() == 15)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * Backwards
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);
				}
			}
			
			//Zero row
			else if((tile.getId() / 8) == 0)
			{
				//Zero element
				if(tile.getId() == 0)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
				}
				
				//7th element
				else if(tile.getId() == 7)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);	
				
				}
				
				//1-6
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);	
				
				}
			}
			
			//7th column ONLY 23 - 47
			else if(((tile.getId() % 8) == 7)
					&& (tile.getId() >= 23)
					&& tile.getId() <= 47)
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * Backwards
				 * BackwardsLeft
				 * Left
				 * ForwardLeft
				 *
				 */
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);
			}
			
			//6th row ONLY zero and 7th element
			else if(((tile.getId() / 8) == 6)
					&& ((tile.getId() == 55) || (tile.getId() == 48)))
			{
				//Zero element
				if(tile.getId() == 48)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
				}
				
				else if(tile.getId() == 55)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * Backwards
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromWhite(true);	
				}
			}
			
			//7th row
			else if((tile.getId() % 8) == 7)
			{
				//Zero element
				if(tile.getId() == 56)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
				}
				
				//7th element
				else if(tile.getId() == 63)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Backwards
					 * BackwardsLeft
					 * Left
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);	
				}
				
				//57 - 62
				else 
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Right
					 * BackwardsRight
					 * Backwards
					 * BackwardsLeft
					 * Left
					 *
					 */
					tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromWhite(true);
					tiles[tile.getId()-1].setIsUnderThreatFromWhite(true);
				}
			}
			
			//Zero column ONLY 16 - 40
			else if(((tile.getId() % 8) == 0)
					&& (tile.getId() >= 16)
					&& tile.getId() <= 40)
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * ForwardRight
				 * Right
				 * BackwardsRight
				 * Backwards

				 *
				 */
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromWhite(true);
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromWhite(true);
			}
		}
		
		//Black King
		if(tile.getPiece().getColour() == "Black")
		{
			/*
			 * Inner board
			 * 
			 * Exclude:
			 * 1) Zero 7th row
			 * 2) Zero 7th column
			 * 
			 */
			if(((tile.getId() > 8) && (tile.getId() < 55))
					&& ((tile.getId() % 8) != 0)
					&& ((tile.getId() % 8) != 7) )
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * ForwardRight
				 * Right
				 * BackwardsRight
				 * Backwards
				 * BackwardsLeft
				 * Left
				 * ForwardLeft
				 *
				 */
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
			}
			
			//6th row ONLY zero and 7th
			else if((tile.getId() / 8) == 6
					&& ((tile.getId() == 48) || (tile.getId() == 55)))
			{
				if(tile.getId() == 48)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * Backwards
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
				}
				else if(tile.getId() == 55)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
				}
			}
			
			//7th row
			else if((tile.getId() / 8) == 7)
			{
				//Zero element
				if(tile.getId() == 56)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
				}
				
				//7th element
				else if(tile.getId() == 63)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
				}
				
				//57 - 62
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
				}
			}
			
			//Zero column ONLY 40 - 16
			else if(((tile.getId() % 8) == 0)
					&& (tile.getId() <= 40)
					&& tile.getId() >= 16)
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * Backwards
				 * BackwardsLeft
				 * Left
				 * ForwardLeft
				 *
				 */
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
			}
			
			//1st row ONLY zero and 7th
			else if(((tile.getId() / 8) == 1)
					&& ((tile.getId() == 7) || (tile.getId() == 7)))
			{
				//Zero element
				if(tile.getId() == 7)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * Backwards
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()+1].setIsUnderThreatFromBlack(true);	
				}
				
				//7th element	
				else if(tile.getId() == 15)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Forward
					 * ForwardRight
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
				}	
			}
			
			//Zero row
			else if((tile.getId() / 8) == 0)
			{
				//Zero element
				if(tile.getId() == 0)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Backwards
					 * BackwardsLeft
					 * Left
					 * ForwardLeft
					 *
					 */
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
				}
				
				//7th element
				else if(tile.getId() == 7)
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Right
					 * BackwardsRight
					 * Backwards
					 *
					 */
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
				}
				
				//1-6
				else
				{
					/*
					 * Set "under threat" status for: 
					 * 
					 * Right
					 * BackwardsRight
					 * Backwards
					 * BackwardsLeft
					 * Left
					 *
					 */
					tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+this.getDimensions()+1].setIsUnderThreatFromBlack(true);
					tiles[tile.getId()+1].setIsUnderThreatFromBlack(true);
				}
			}
			
			//7th column ONLY 47 -23
			else if(((tile.getId() % 8) == 7)
					&& (tile.getId() <= 47)
					&& tile.getId() >= 23)
			{
				/*
				 * Set "under threat" status for: 
				 * 
				 * Forward
				 * ForwardRight
				 * Right
				 * BackwardsRight
				 * Backwards
				 *
				 */
				tiles[tile.getId()-this.getDimensions()].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-this.getDimensions()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()-1].setIsUnderThreatFromBlack(true);
				tiles[tile.getId()+this.getDimensions()].setIsUnderThreatFromBlack(true);
			}
		}
	}
	
	
	/*
	 * Checks all the tiles and sets their "under threat" status after reseted them after every move
	 * 
	 * @param1: tiles -> All the tiles
	 * 
	 */
	public void checkAllThreatStatusOfTiles(Tile[] tiles)
	{
		//Check for every tile
		for(int i = 0 ; i < this.getDimensions()*this.getDimensions() ; i++)
		{
			//Tile contains any piece
			if(tiles[i].getContainsPiece() == true)
			{
//Pawns		
				if(tiles[i].getPiece().getType() == "Pawn")
					this.checkPawnThreatStatusOfTiles(tiles[i]);
//Rooks		
				else if(tiles[i].getPiece().getType() == "Rook")
					this.checkRookThreatStatusOfTiles(tiles[i]);
//Knights
				else if(tiles[i].getPiece().getType() == "Knight")
					this.checkKnightThreatStatusOfTiles(tiles[i]);
//Bishops
				else if(tiles[i].getPiece().getType() == "Bishop")
					this.checkBishopThreatStatusOfTiles(tiles[i]);
//Queens
				else if(tiles[i].getPiece().getType() == "Queen")
					this.checkQueenThreatStatusOfTiles(tiles[i]);
//Kings			
				else if(tiles[i].getPiece().getType() == "King")
					this.checkKingThreatStatusOfTiles(tiles[i]);
			}
			
			//Tile does not contain any piece
			else
				continue;
		}
	}
	
	
	/*
	 * Checks if a pawn move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the pawn
	 * @param2: destinaltionTile -> The tile that the player wants the pawn to move
	 * 
	 */
	public void checkPawnValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White pawns
		if(startTile.getPiece().getColour() == "White") 
		{
			//Starting position
			if(startTile.getId() > 7 && startTile.getId() < 16)
			{
				//Check forward move - One tile
				if((destinationTile.getId() - startTile.getId()) == this.getDimensions())
				{
					//If destination tile is empty - Allow the move
					if(destinationTile.getContainsPiece() == false)
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
					else
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
				}
				
				//Check forwardRight move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == 9)
				{
					//If destination tile is empty or contains a white piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "White")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any black piece allow the move to capture it
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forwardLeft move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == 7)
				{
					//If destination tile is empty or contains a white piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "White")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any black piece allow the move
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forward move - Two tiles	
				else if(destinationTile.getId() - startTile.getId() == 2*this.getDimensions())
				{
					
					/*
					 * If there is:
					 *  
					 * 1) A white piece that blocks the path from startTile to destinationTile
					 *  OR
					 * 2) A white piece on destination tile
					 * then set the move as illegal
					 * 
					 * Both (above) expectations are reached using the following "for" loop
					 * 
					 */
					for(int i = 0 ; i < 2 ; i++)
					{
						//Clear path
						if(tiles[startTile.getId() + (i+1)*this.getDimensions()].getContainsPiece() == true)
						{
							startTile.getPiece().setIsDirectionLegal(false);
							break;
							//startTile.getPiece().setMaxAllowedDistance(2);
						}
						//Blocked path
						else
							startTile.getPiece().setIsDirectionLegal(true);
					}
				}
				
				//Check everywhere else move - Set it as illegal
				else
					startTile.getPiece().setIsDirectionLegal(false);
			}
			
			/*
			 * Random position
			 * 
			 * Just, do not take into consideration the edge tiles and the starting position's tiles
			 * 
			 * a) 7th row 
			 * b) First/Starting row
			 * c) Zero row
			 * 
			 */
			else if( (!(startTile.getId() > 55 && startTile.getId() < 64))
					&&  (!(startTile.getId() >=0 && startTile.getId() < 16)) )
			{	
				//Check forward move - One tile
				if((destinationTile.getId() - startTile.getId()) == this.getDimensions())
				{
					//If destination tile is empty - Allow the move
					if(destinationTile.getContainsPiece() == false)
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
					else
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
				}
				
				//Check forwardRight move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == 9)
				{
					//If destination tile is empty or contains a white piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "White")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any black piece allow the move to capture it
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forwardLeft move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == 7)
				{
					//If destination tile is empty or contains a white piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "White")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any black piece allow the move
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check everywhere else move - Set it as illegal
				else
					startTile.getPiece().setIsDirectionLegal(false);	
			}
		}
		
		//Black pawns
		else if(startTile.getPiece().getColour() == "Black")
		{
			//Starting position
			if(startTile.getId() > 47 && startTile.getId() < 56)
			{
				//Check forward move - One tile
				if((destinationTile.getId() - startTile.getId()) == (-1)*this.getDimensions())
				{
					//If destination tile is empty - Allow the move
					if(destinationTile.getContainsPiece() == false)
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
					else
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
				}
				
				//Check forwardRight move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == -9)
				{
					//If destination tile is empty or contains a black piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "Black")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any white piece allow the move to capture it
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forwardLeft move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == -7)
				{
					//If destination tile is empty or contains a black piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "Black")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any white piece allow the move
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forward move - Two tiles	
				else if(destinationTile.getId() - startTile.getId() == (-2)*this.getDimensions())
				{
					
					/*
					 * If there is:
					 *  
					 * 1) A black piece that blocks the path from startTile to destinationTile
					 *  OR
					 * 2) A black piece on destination tile
					 * then set the move as illegal
					 * 
					 * Both (above) expectations are reached using the following "for" loop
					 * 
					 */
					for(int i = 0 ; i < 2 ; i++)
					{
						//Clear path
						if(tiles[startTile.getId() - (i+1)*this.getDimensions()].getContainsPiece() == true)
						{
							startTile.getPiece().setIsDirectionLegal(false);
							break;
							//startTile.getPiece().setMaxAllowedDistance(2);
						}
						//Blocked path
						else
							startTile.getPiece().setIsDirectionLegal(true);
					}
				}
				
				//Check everywhere else move - Set it as illegal
				else
					startTile.getPiece().setIsDirectionLegal(false);
			}
			
			/*
			 * Random position
			 * 
			 * Just, do not take into consideration the edge tiles and the starting position's tiles
			 * 
			 * a) Zero row 
			 * b) 6th/Starting row
			 * c) 7th row
			 * 
			 * Disclaimer: The above info comes from blacks' perspective
			 * 
			 */
			else if((!(startTile.getId() >= 0 && startTile.getId() < 8))
					&&  (!(startTile.getId() > 47 && startTile.getId() < 64)) )
			{
				
				//Check forward move - One tile
				if((destinationTile.getId() - startTile.getId()) == (-1)*this.getDimensions())
				{
					//If destination tile is empty - Allow the move
					if(destinationTile.getContainsPiece() == false)
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
					else
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
				}
				
				//Check forwardRight move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == -9)
				{
					//If destination tile is empty or contains a black piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "Black")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any white piece allow the move to capture it
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check forwardLeft move to capture - One tile
				else if(destinationTile.getId() - startTile.getId() == -7)
				{
					//If destination tile is empty or contains a black piece
					if( (destinationTile.getContainsPiece() == false) || destinationTile.getPiece().getColour() == "Black")
					{
						//Set the move as illegal
						startTile.getPiece().setIsDirectionLegal(false);
					}
					//If destination tile contains any white piece allow the move
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
						//startTile.getPiece().setMaxAllowedDistance(1);
					}
				}
				
				//Check everywhere else move - Set it as illegal
				else
					startTile.getPiece().setIsDirectionLegal(false);
			}
		}
	}
	
	/*
	 * Checks if a rook move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the rook
	 * @param2: destinaltionTile -> The tile that the player wants the rook to move
	 * 
	 */
	public void checkRookValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White rooks
		if(startTile.getPiece().getColour() == "White")
		{
			/*
			 * Check forward move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
				
			}
			
			/*
			 * Check right move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*(destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check left move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (-1)*(destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
				startTile.getPiece().setIsDirectionLegal(false);
		}
		
		//Black rooks
		if(startTile.getPiece().getColour() == "Black")
		{
			/*
			 * Check forward move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*(destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check right move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (-1)*(destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check left move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
				startTile.getPiece().setIsDirectionLegal(false);
		}
	}
	
	
	
	/*
	 * Checks if a knight move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the knight
	 * @param2: destinaltionTile -> The tile that the player wants the knight to move
	 * 
	 */
	public void checkKnightValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White Knight
		if(startTile.getPiece().getColour() == "White")
		{
			/*
			 * Check forward right long L move
			 * 
			 * 1) (destinationTile - startTile) == 17
			 * 
			 */
			if((destinationTile.getId() - startTile.getId()) == 17)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward right short L move
			 * 
			 * 1) (destinationTile - startTile) == 10
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) == 10)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward left long L move
			 * 
			 * 1) (destinationTile - startTile) == 15
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) == 15)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward left short L move
			 * 
			 * 1) (destinationTile - startTile) == 6
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) == 6)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards right long L move
			 * 
			 * 1) ((-1)(destinationTile - startTile)) == 15
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 15)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards right short L move
			 * 
			 * 1) ((-1)(destinationTile - startTile)) == 6
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 6)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards left long L move
			 * 
			 * 1) ((-1)(destinationTile - startTile)) == 17
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 17)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards left short L move
			 * 
			 * 1) ((-1)(destinationTile - startTile)) == 10
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 10)
			{
				/*
				 * If there is any white piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a black piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "White")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			//Check elsewhere move
			else
			{
				startTile.getPiece().setIsDirectionLegal(false);
			}
		}
		
		//Black Knight
		else if(startTile.getPiece().getColour() == "Black")
		{
			/*
			 * Check forward right long L move
			 * 
			 * 1) (-1)*(destinationTile - startTile) == 17
			 * 
			 */
			if(((-1)*(destinationTile.getId() - startTile.getId())) == 17)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward right short L move
			 * 
			 * 1) (-1)*(destinationTile - startTile) == 10
			 * 
			 */
			else if(((-1)*(destinationTile.getId() - startTile.getId())) == 10)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward left long L move
			 * 
			 * 1) (-1)*(destinationTile - startTile) == 15
			 * 
			 */
			else if(((-1)*(destinationTile.getId() - startTile.getId())) == 15)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check forward left short L move
			 * 
			 * 1) (-1)*(destinationTile - startTile) == 6
			 * 
			 */
			else if(((-1)*(destinationTile.getId() - startTile.getId())) == 6)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards right long L move
			 * 
			 * 1) (destinationTile - startTile) == 15
			 * 
			 */
			else if( (destinationTile.getId() - startTile.getId()) == 15)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards right short L move
			 * 
			 * 1) ((destinationTile - startTile) == 6
			 * 
			 */
			else if( (destinationTile.getId() - startTile.getId()) == 6)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards left long L move
			 * 
			 * 1) (destinationTile - startTile) == 17
			 * 
			 */
			else if( (destinationTile.getId() - startTile.getId()) == 17)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			/*
			 * Check backwards left short L move
			 * 
			 * 1) (destinationTile - startTile) == 10
			 * 
			 */
			else if( (destinationTile.getId() - startTile.getId()) == 10)
			{
				/*
				 * If there is any black piece on destination tile, do not allow the move
				 * 
				 * But if there is no piece or there is a white piece allow the move 
				 * in order to just move or capture it
				 * 
				 */
				
				//Blocked path
				if(((destinationTile.getContainsPiece() == true) && 
						(destinationTile.getPiece().getColour() == "Black")))
				{
					startTile.getPiece().setIsDirectionLegal(false);
				}
				
				//Clear path
				else
					startTile.getPiece().setIsDirectionLegal(true);
			}
			
			//Check elsewhere move
			else
			{
				startTile.getPiece().setIsDirectionLegal(false);
			}
		}
	}
	
	/* 
	 * Checks if a bishop move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the bishop
	 * @param2: destinaltionTile -> The tile that the player wants the bishop to move
	 * 
	 */
	public void checkBishopValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White bishops
		if(startTile.getPiece().getColour() == "White")
		{
			/*
			 * Check forward right move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (destinationTile - startTile) % 9 == 0 -> Ensure right move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward left move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (destinationTile - startTile) % 7 == 0 -> Ensure left move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 7 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards right move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (-1)*(destinationTile - startTile) % 7 == 0 -> Ensure right move
			 * 3) destinationTile.getId() is NOT 0
			 * 						
			 * EXPLAINER of 3): If i do not exclude the tile with id=0, when i want to move the white
			 * bishop from 63->0 IT ENTERS HERE ("backwards right") and NOT ON "backwards left"
			 * cause expectations in both cases are fulfilled and because of the fact that i use a
			 * if/else_if/ structure IT DOES NOT ENTER "backwards left" cause it first enters HERE and
			 * then returns a value   
			 * 
			 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 7 == 0
					&& (destinationTile.getId() != 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+( ((-1)*((destinationTile.getId() - startTile.getId()))) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+( ((-1)*(destinationTile.getId() - startTile.getId())) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+( ((-1)*(destinationTile.getId() - startTile.getId())) / 8)) )
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards left move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (-1)*(destinationTile - startTile) % 9 == 0 -> Ensure left move
			 * 
			 * The quantity "((-1)*(destinationTile.getId() - startTile.getId())) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(-1)*((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < ((-1)*((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					System.out.println("i: " + i + "   tileId: " + tiles[(startTile.getId() - (i+1)*this.getDimensions()) - (i+1)].getId());
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
			{
				startTile.getPiece().setIsDirectionLegal(false);
			}
		}
		
		//Black Bishops
		else if(startTile.getPiece().getColour() == "Black")
		{
			/*
			 * Check forward right move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (-1)*(destinationTile - startTile) % 9 == 0 -> Ensure right move
			 * 
			 * The quantity "((-1)*(destinationTile.getId() - startTile.getId())) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(-1)*((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < ((-1)*((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions())-(i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward left move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (-1)*(destinationTile - startTile) % 7 == 0 -> Ensure left move
			 * 
			 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 7 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))
				 */
				for(int i = 0 ; i < (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions())+(i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))  )
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards right move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (destinationTile - startTile) % 7 == 0 -> Ensure right move
			 * 3) destinationTile.getId() is NOT 63
			 * 						
			 * EXPLAINER of 3): If i do not exclude the tile with id=63, when i want to move the black
			 * bishop from 63->0 IT ENTERS HERE ("backwards right") and NOT ON "backwards left"
			 * cause expectations in both cases are fulfilled and because of the fact that i use a
			 * if/else_if/ structure IT DOES NOT ENTER "backwards left" cause it first enters HERE and
			 * then returns a value
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 7 == 0
					&& (destinationTile.getId() != 63))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards left move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (destinationTile - startTile) % 9 == 0 -> Ensure left move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
			{
				startTile.getPiece().setIsDirectionLegal(false);
			}
		}
	}
	
	/*
	 * Checks if a queen move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the queen
	 * @param2: destinaltionTile -> The tile that the player wants the queen to move
	 * 
	 */
	public void checkQueenValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White Queen
		if(startTile.getPiece().getColour() == "White")
		{
			/*
			 * Check forward move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward right move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (destinationTile - startTile) % 9 == 0 -> Ensure right move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check right move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards right move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (-1)*(destinationTile - startTile) % 7 == 0 -> Ensure right move
			 * 3) destinationTile.getId() is NOT 0
			 * 						
			 * EXPLAINER of 3): If i do not exclude the tile with id=0, when i want to move the white
			 * bishop from 63->0 IT ENTERS HERE ("backwards right") and NOT ON "backwards left"
			 * cause expectations in both cases are fulfilled and because of the fact that i use a
			 * if/else_if/ structure IT DOES NOT ENTER "backwards left" cause it first enters HERE and
			 * then returns a value   
			 * 
			 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 7 == 0
					&& (destinationTile.getId() != 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+( ((-1)*((destinationTile.getId() - startTile.getId()))) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+( ((-1)*(destinationTile.getId() - startTile.getId())) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+( ((-1)*(destinationTile.getId() - startTile.getId())) / 8)) )
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*(destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards left move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (-1)*(destinationTile - startTile) % 9 == 0 -> Ensure left move
			 * 
			 * The quantity "((-1)*(destinationTile.getId() - startTile.getId())) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(-1)*((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < ((-1)*((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check left move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (-1)*(destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward left move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (destinationTile - startTile) % 7 == 0 -> Ensure left move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 7 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a white piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "White")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a black piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
			{
				startTile.getPiece().setIsDirectionLegal(false);
			}
		}
		
		//Black Queen
		else if(startTile.getPiece().getColour() == "Black")
		{
			/*
			 * Check forward move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*(destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward right move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (-1)*(destinationTile - startTile) % 9 == 0 -> Ensure right move
			 * 
			 * The quantity "((-1)*(destinationTile.getId() - startTile.getId())) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(-1)*((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < ((-1)*((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions())-(i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != ((-1)*((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check right move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) < 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) < 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (-1)*(destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() - (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (-1)*(destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards right move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (destinationTile - startTile) % 7 == 0 -> Ensure right move
			 * 3) destinationTile.getId() is NOT 63
			 * 						
			 * EXPLAINER of 3): If i do not exclude the tile with id=63, when i want to move the black
			 * bishop from 63->0 IT ENTERS HERE ("backwards right") and NOT ON "backwards left"
			 * cause expectations in both cases are fulfilled and because of the fact that i use a
			 * if/else_if/ structure IT DOES NOT ENTER "backwards left" cause it first enters HERE and
			 * then returns a value
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 7 == 0
					&& (destinationTile.getId() != 63))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+((destinationTile.getId() - startTile.getId()) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (1+((destinationTile.getId() - startTile.getId()) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) - (i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+((destinationTile.getId() - startTile.getId()) / 8)))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards move
			 * 
			 * 1) Same column
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() % 8) == (destinationTile.getId() % 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)*this.getDimensions()].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check backwards left move
			 * 
			 * 1) (destinationTile - startTile) > 0 -> Ensure backwards move
			 * 2) (destinationTile - startTile) % 9 == 0 -> Ensure left move
			 * 
			 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if((destinationTile.getId() - startTile.getId()) > 0
					&& (destinationTile.getId() - startTile.getId()) % 9 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId()) / 8" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) / 8 ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() + (i+1)*this.getDimensions()) + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != ((destinationTile.getId() - startTile.getId()) / 8))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check left move
			 * 
			 * 1) Same row
			 * 2) startTile is different than destinationTile
			 * 3) (destinationTile - startTile) > 0 
			 * 
			 */
			else if((startTile.getId() / 8) == (destinationTile.getId() / 8) 
					&& (startTile.getId() != destinationTile.getId()) 
					&& ((destinationTile.getId() - startTile.getId()) > 0))
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a white piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(destinationTile.getId() - startTile.getId())" is the 
				 * number of tiles from startTile to destinationTile
				 * 
				 */
				for(int i = 0 ; i < (destinationTile.getId() - startTile.getId()) ; i++)
				{
					
					//Blocked path
					if(tiles[startTile.getId() + (i+1)].getContainsPiece() == true)
					{	
						//If i am NOT on destinationTile
						if((i+1) != (destinationTile.getId() - startTile.getId()))
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			/*
			 * Check forward left move
			 * 
			 * 1) (-1)*(destinationTile - startTile) > 0 -> Ensure forward move
			 * 2) (-1)*(destinationTile - startTile) % 7 == 0 -> Ensure left move
			 * 
			 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
			 * number of tiles from startTile to destinationTile
			 * 
			 */
			else if( ((-1)*(destinationTile.getId() - startTile.getId())) > 0
					&& ( (-1)*(destinationTile.getId() - startTile.getId())) % 7 == 0 )
			{
				/*
				 * If there is:
				 *  
				 * Any piece that blocks the path from startTile to destinationTile
				 * then set the move as illegal
				 * 
				 * But if the path is clear and there is a black piece on destinationTile, 
				 * allow the move to capture it
				 * 
				 * The quantity "(1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))" is the 
				 * number of tiles from startTile to destinationTile
				 * (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))
				 */
				for(int i = 0 ; i < (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8)) ; i++)
				{
					
					//Blocked path
					if(tiles[(startTile.getId() - (i+1)*this.getDimensions())+(i+1)].getContainsPiece() == true)
					{
						//If i am NOT on destinationTile
						if((i+1) != (1+( (-1)*((destinationTile.getId() - startTile.getId())) / 8))  )
						{
							//If there is any piece blocking the path, set the move as illegal
							startTile.getPiece().setIsDirectionLegal(false);
							break;
						}
						
						//If i am on destinationTile
						else
						{
							//If there is a black piece, set the move as illegal
							if(destinationTile.getPiece().getColour() == "Black")
								startTile.getPiece().setIsDirectionLegal(false);
							//If there is a white piece or nothing allow the move (to capture or just move)
							else
								startTile.getPiece().setIsDirectionLegal(true);
						}
					}
					
					//Clear path
					else
					{
						startTile.getPiece().setIsDirectionLegal(true);
					}
				}
			}
			
			//Check elsewhere move
			else
				startTile.getPiece().setIsDirectionLegal(false);
		}
	}
	
	/*
	 * Checks if a king move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the king
	 * @param2: destinaltionTile -> The tile that the player wants the king to move
	 * 
	 */
	public void checkKingValidationMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//White King
		if(startTile.getPiece().getColour() == "White")
		{
			//Allow move only if destinationTile is NOT under threat from black
			if(destinationTile.getIsUnderThreatFromBlack() == false)
			{
				/*
				 * Check forward move
				 * 
				 * 1) (destinationTile - startTile) == 8 
				 * 
				 */
				if( (destinationTile.getId() - startTile.getId()) == 8)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check forward right move
				 * 
				 * 1) (destinationTile - startTile) == 9 
				 * 
				 */
				else if( (destinationTile.getId() - startTile.getId()) == 9)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check right move
				 * 
				 * 1) (destinationTile - startTile) == 1
				 * 2) destinationTile and startTile belong in the same row
				 * 
				 */
				else if( ((destinationTile.getId() - startTile.getId()) == 1)
						&& ((destinationTile.getId() / 8) == (startTile.getId() /8)) )
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards right move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 7 
				 * 
				 */
				else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 7)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 8 
				 * 
				 */
				else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 8)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards left move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 9 
				 * 
				 */
				else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 9)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check left move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 1
				 * 2) destinationTile and startTile belong in the same row
				 * 
				 */
				else if( ((-1)*((destinationTile.getId() - startTile.getId())) == 1)
						&& ((destinationTile.getId() / 8) == (startTile.getId() /8)) )
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check forward left move
				 * 
				 * 1) (destinationTile - startTile) == 7 
				 * 
				 */
				else if( (destinationTile.getId() - startTile.getId()) == 7)
				{
					/*
					 * If there is any white piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "White")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				//Check elsewhere move
				else
					startTile.getPiece().setIsDirectionLegal(false);
			}
			//If destinationTile is under threat from black set the move as illegal
			else 
				startTile.getPiece().setIsDirectionLegal(false);
		}
		
//Black King
		else if(startTile.getPiece().getColour() == "Black")
		{
			//Allow move only if destinationTile is NOT under threat from white
			if(destinationTile.getIsUnderThreatFromWhite() == false)
			{
				/*
				 * Check forward move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 8 
				 * 
				 */
				if( ((-1)*(destinationTile.getId() - startTile.getId())) == 8)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check forward right move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 9 
				 * 
				 */
				else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 9)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check right move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 1
				 * 2) destinationTile and startTile belong in the same row
				 * 
				 */
				else if( ((-1)*((destinationTile.getId() - startTile.getId())) == 1)
						&& ((destinationTile.getId() / 8) == (startTile.getId() /8)) )
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards right move
				 * 
				 * 1) (destinationTile - startTile) == 7 
				 * 
				 */
				else if( (destinationTile.getId() - startTile.getId()) == 7)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards move
				 * 
				 * 1) (destinationTile - startTile) == 8 
				 * 
				 */
				else if( (destinationTile.getId() - startTile.getId()) == 8)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check backwards left move
				 * 
				 * 1) (destinationTile - startTile) == 9 
				 * 
				 */
				else if( (destinationTile.getId() - startTile.getId()) == 9)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check left move
				 * 
				 * 1) (destinationTile - startTile) == 1
				 * 2) destinationTile and startTile belong in the same row
				 * 
				 */
				else if( ((destinationTile.getId() - startTile.getId()) == 1)
						&& ((destinationTile.getId() / 8) == (startTile.getId() /8)) )
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a white piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				/*
				 * Check forward left move
				 * 
				 * 1) (-1)*(destinationTile - startTile) == 7 
				 * 
				 */
				else if( ((-1)*(destinationTile.getId() - startTile.getId())) == 7)
				{
					/*
					 * If there is any black piece on destination tile, do not allow the move
					 * 
					 * But if there is no piece or there is a black piece allow the move 
					 * in order to just move or capture it
					 * 
					 */
					
					//Blocked path
					if(((destinationTile.getContainsPiece() == true) && 
							(destinationTile.getPiece().getColour() == "Black")))
					{
						startTile.getPiece().setIsDirectionLegal(false);
					}
					
					//Clear path
					else
						startTile.getPiece().setIsDirectionLegal(true);
				}
				
				//Check elsewhere move
				else
					startTile.getPiece().setIsDirectionLegal(false);	
			}
			
			//If destinationTile is under threat from white set the move as illegal
			else
				startTile.getPiece().setIsDirectionLegal(false);
		}
	}
	
	
	
	/*
	 * Checks if a move is legal and can be done
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the move
	 * @param2: destinaltionTile -> The tile that the player wants the piece to move
	 * 
	 * Returns TRUE if it is legal and false if it is illegal
	 */
	public boolean checkAllValidationOfMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
//Pawns
		if((startTile.getPiece().getType() == "Pawn"))
			this.checkPawnValidationMove(tiles, startTile, destinationTile);
//Rooks
		else if(startTile.getPiece().getType() == "Rook")
			this.checkRookValidationMove(tiles, startTile, destinationTile);
//Knight
		else if(startTile.getPiece().getType() == "Knight")
				this.checkKnightValidationMove(tiles, startTile, destinationTile);
//Bishops
		else if(startTile.getPiece().getType() == "Bishop")
			this.checkBishopValidationMove(tiles, startTile, destinationTile);
//Queens
		else if(startTile.getPiece().getType() == "Queen")
			this.checkQueenValidationMove(tiles, startTile, destinationTile);
//Kings
		else if(startTile.getPiece().getType() == "King")
			this.checkKingValidationMove(tiles, startTile, destinationTile);
		
		
		/* Return correct value
		 * 
		 * If move is legal return true, else return false
		 * 
		 */
		if(startTile.getPiece().getIsDirectionLegal() == false)
			return false;
			else 
				return true;
	}
	
	
	//Finds and returns the image of the corresponding piece within the selected tile
	public Image findImage(Tile tile)
	{	
		return tile.getPiece().getImage();
	}
	
	/*
	 * Just helps to check if checkmate has happened. In fact, demonstrates a background move to
	 * check if king is still in check after the move or not.
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the move
	 * @param2: destinaltionTile -> The tile that the player wants the piece to move
	 * 
	 * @return: False if king is NOT checkmated, but has an escape road or check can be blocked
	 * 			True, if king still in check despite the move
	 */
	public boolean helpingMoveForCheckmate(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//Check first legality of the move
		if(this.checkAllValidationOfMove(tiles, startTile, destinationTile) == true)
		{
			//First of all create a copy of startTile and destinationTile
			Tile copyDestinationTile = new Tile(destinationTile);
			Tile copyStartTile = new Tile(startTile);
			
			//Then add data to the destination tile and update the status
			destinationTile.setPiece(startTile.getPiece());
			destinationTile.setContainsPiece(true);
					
			//Then remove data from the start tile and update the status
			startTile.setPiece(null);
			startTile.setContainsPiece(false);
			
			//Reset "under threat" status of tiles
			this.resetThreatStatusOfTiles(tiles);
			
			//Check "under threat" status of tiles
			this.checkAllThreatStatusOfTiles(tiles);
			
			//Check if same colored king is in check and if yes, then return false
			for(int j = 0 ; j < this.getDimensions()*this.getDimensions(); j++)
			{
				if(destinationTile.getPiece().getColour() == "White")
				{
					//If white king is still under threat from any black piece
					if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromBlack() == true))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return true;
					}
					//If white king is no more under threat from any black piece
					else if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromBlack() == false))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return false;
					}
				}
				else if(destinationTile.getPiece().getColour() == "Black")
				{
					//If black king is still under threat from any white piece
					if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromWhite() == true))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return true;
					}
					
					//If black king is no more under threat from any white piece
					else if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromWhite() == false))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return false;
					}
				}
			}
			
			//Compiler error without it...does not change anything at all
			return true;
		}
		
		//Move cannot take place so king is still in check, return true
		else
			return true;
	}
	
	/* 
	 * Checks if checkmate has happened.
	 * 
	 * @param1: tiles -> All the tiles
	 * 
	 * @return: true if checkmate has happened, else false
	 */
	public boolean checkForCheckmate(Tile[] tiles)
	{
		//Private variables
		int whiteKingPosition = -1;
		int blackKingPosition = -1;
		
		//Reset "under threat" status of tiles
		this.resetThreatStatusOfTiles(tiles);
		
		//Check "under threat" status of tiles
		this.checkAllThreatStatusOfTiles(tiles);
		
		/*
		 * If any king is under threat from any opponent's piece, check if he's checkmated
		 */
		
		//Find on which tile does the white king lies on
		for(int i = 0; i < this.getDimensions()*this.getDimensions(); i++)
		{
			//White king's tile
			if((tiles[i].getContainsPiece() == true)
					&& (tiles[i].getPiece().getType() == "King")
					&& (tiles[i].getPiece().getColour() == "White"))
			{
				//Store white kings's position
				whiteKingPosition = tiles[i].getId();
			}
		}
		
		//Find on which tile does the black king lies on
		for(int i = 0; i < this.getDimensions()*this.getDimensions(); i++)
		{
			//Black king's tile
			if((tiles[i].getContainsPiece() == true)
					&& (tiles[i].getPiece().getType() == "King")
					&& (tiles[i].getPiece().getColour() == "Black"))
					
			{
				//Store white kings's position
				blackKingPosition = tiles[i].getId();
			}
		}
		
		/*
		 * If white king is under threat from any black piece then check if he's checkmated
		 */
		
		//Check for checkmate
		if(tiles[whiteKingPosition].getIsUnderThreatFromBlack() == true)
		{
			//Check all tiles one by one
			for(int i = 0; i < this.getDimensions()*this.getDimensions(); i++)
			{
				//If tile contains any piece
				if(tiles[i].getContainsPiece() == true)
				{
					//If piece is white
					if(tiles[i].getPiece().getColour() == "White")
					{
						if(tiles[i].getPiece().getType() == "Pawn")
						{
							//Inner board pawns - Starting position
							if(tiles[i].getId() > 8 && tiles[i].getId() < 15)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Inner board - Random position
							 * 
							 * Just, do not take into consideration the edge tiles and the starting position's tiles
							 * a) Zero column
							 * b) 7th row 
							 * c) 7th Column
							 * d) First/Starting row
							 * e) Zero row
							 * 
							 */
							if((tiles[i].getId() % 8 != 0) && (!(tiles[i].getId() > 55 && tiles[i].getId() < 64))
									&&  (tiles[i].getId() % 8 != 7) 
									&&  (!(tiles[i].getId() >=0 && tiles[i].getId() < 16)) )
							{
								//System.out.println("\nInner\n");
								
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}	
							}
							
							/*
							 * Edge board - Starting position
							 */
							//Further left piece
							if(tiles[i].getId() == 8)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							//Further right piece
							if(tiles[i].getId() == 15)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}	
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							//Edge board - Random position
							
							/*
							 * Edge board - Random position - Case 1
							 * 
							 * Take into consideration all the edge tiles of zero column
							 *  but zero's and first's rows
							 * 
							 */
							if((tiles[i].getId() % 8 == 0) && (tiles[i].getId() != 0) && (tiles[i].getId() != 8))
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Edge board - Random position - Case 2
							 * 
							 * Take into consideration all the edge tiles of 7th column
							 *  but zero's and first's rows
							 * 
							 */
							if((tiles[i].getId() % 8 == 7) && (tiles[i].getId() != 7) && (tiles[i].getId() != 15))
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}	
							}
						}
						
						/*
						 * Find how many moves are there till the max row/column towards the desired
						 * direction and iteratively move it towards that direction by j steps. 
						 */
						else if(tiles[i].getPiece().getType() == "Rook")
						{
							/*
							 * Forward move
							 * 
							 * The quantity "7 - tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check forward move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Right move
							 * 
							 * The quantity "7 - tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Backwards move
							 * 
							 * The quantity "tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check backwards move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Left move
							 * 
							 * The quantity "tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == false)
								{
									return false;
								}
							}
						}
						
						/*
						 * Check where the knight lies on and then act
						 */
						else if(tiles[i].getPiece().getType() == "Knight")
						{
							/*
							 * Inner board
							 * 
							 * Exclude:
							 * 1) Zero and 1st and 6th and 7th row
							 * 2) Zero and 1st and 6th and 7th column
							 * 
							 */
							if(((tiles[i].getId() > 17) && (tiles[i].getId() < 46))
									&& ((tiles[i].getId() % 8) != 0)
									&& ((tiles[i].getId() % 8) != 1)
									&& ((tiles[i].getId() % 8) != 6)
									&& ((tiles[i].getId() % 8) != 7) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
							}
							
							//1st row
							else if(((tiles[i].getId() / 8) == 1))
							{
								
								if(tiles[i].getId() == 8)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 9)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 14)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}	
								}
								
								else if(tiles[i].getId() == 15)
								{
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}	
								}
								
								//10 - 13
								else
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}	
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}	
								}
							}
							
							//Zero row
							else if(((tiles[i].getId() / 8) == 0))
							{
								if(tiles[i].getId() == 0)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 1)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 6)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 7) 
								{
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								//2-5
								else
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
							}
							
							//6th column ONLY from 22 to 46
							else if(((tiles[i].getId() % 8) == 6)
									&& (tiles[i].getId() >= 22) 
									&& (tiles[i].getId() <= 46) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
							}
							
							//7th column ONLY from 23 to 47
							else if(  ((tiles[i].getId() % 8) == 7)
									&& (tiles[i].getId() >= 23) 
									&& (tiles[i].getId() <= 47) )
							{
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
							}
							
							//6th row
							else if(((tiles[i].getId() / 8) == 6))
							{
								
								if(tiles[i].getId() == 55)
								{
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 54)
								{
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
								
								else if (tiles[i].getId() == 49)
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
								}
								
								else if (tiles[i].getId() == 48)
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								//50 - 53
								else
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
							}
							
							//7th row
							else if(((tiles[i].getId() / 8) == 7))
							{
								if(tiles[i].getId() == 63)
								{
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 62)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 57)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 56)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								//58-61
								else
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
							}
							
							//1st column ONLY from 17 to 41
							else if(((tiles[i].getId() % 8) == 1)
									&& (tiles[i].getId() >= 17) 
									&& (tiles[i].getId() <= 41) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
							}
							
							//Zero column ONLY from 16 to 40
							else if(((tiles[i].getId() % 8) == 0)
									&& (tiles[i].getId() >= 16) 
									&& (tiles[i].getId() <= 40) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
							}
						}
						
						/* Find how many moves are there till the max row/column towards the desired
						 * direction and iteratively move it towards that direction by j steps. 
						 */
						else if(tiles[i].getPiece().getType() == "Bishop")
						{
							/* Forward right
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the 7th column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((7-(tiles[i].getId()/8)) > (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if((7-(tiles[i].getId()/8)) <= (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Forward left
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((7-(tiles[i].getId()/8)) > (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((7-(tiles[i].getId()/8)) <= (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards right
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the 7th column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((tiles[i].getId()/8) > (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if(((tiles[i].getId()/8) <= (7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards left
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the zero column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((tiles[i].getId()/8) > (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((tiles[i].getId()/8) <= (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j <  tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
						}
						else if(tiles[i].getPiece().getType() == "Queen")
						{
							/* Forward move
							 * 
							 * The quantity "7 - tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check forward move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/* Forward right 
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the 7th column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((7-(tiles[i].getId()/8)) > (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if((7-(tiles[i].getId()/8)) <= (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Right move
							 * 
							 * The quantity "7 - tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards right
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "7-(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the 7th column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((tiles[i].getId()/8) > (7-(tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if(((tiles[i].getId()/8) <= (7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Backwards move
							 * 
							 * The quantity "tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check backwards move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/* Backwards left
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from 
							 * the tile that the piece lies on, to the zero column
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((tiles[i].getId()/8) > (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((tiles[i].getId()/8) <= (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j <  tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Left move
							 * 
							 * The quantity "tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Forward left
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if((7-(tiles[i].getId()/8)) > (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((7-(tiles[i].getId()/8)) <= (tiles[i].getId()%8))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}	
						}
						else if(tiles[i].getPiece().getType() == "King")
						{
							/*
							 * Inner board
							 * 
							 * Exclude:
							 * 1) Zero 7th row
							 * 2) Zero 7th column
							 * 
							 */
							if(((tiles[i].getId() > 8) && (tiles[i].getId() < 55))
									&& ((tiles[i].getId() % 8) != 0)
									&& ((tiles[i].getId() % 8) != 7))
							{
								/* Check forward move 
								 *
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/* Check forward right move 
								 *
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check right move
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards right
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwards left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}
							}
							
							//First row ONLY zero and 7th element
							else if(((tiles[i].getId() / 8) == 1)
									&& ((tiles[i].getId() == 8) || (tiles[i].getId() == 15)))
							{
								//Zero element
								if(tiles[i].getId() == 8)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
								}
								
								//7th element
								else if(tiles[i].getId() == 15)
								{

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
								
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
								}
							}
							
							//Zero row
							else if((tiles[i].getId() / 8) == 0)
							{
								//Zero element
								if(tiles[i].getId() == 0)
								{

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
								}
								
								//7th element
								else if(tiles[i].getId() == 7)
								{	

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
								}
								
								//1-6
								else
								{

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
								}
							}
							
							//7th column ONLY 23 - 47
							else if(((tiles[i].getId() % 8) == 7)
									&& (tiles[i].getId() >= 23)
									&& tiles[i].getId() <= 47)
							{

								/* 
								 * Check forward move 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}
							}
							
							//6th row ONLY zero and 7th element
							else if(((tiles[i].getId() / 8) == 6)
									&& ((tiles[i].getId() == 55) || (tiles[i].getId() == 48)))
							{
								//Zero element
								if(tiles[i].getId() == 48)
								{

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 55)
								{

									/* 
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
								}
							}
							
							//7th row
							else if((tiles[i].getId() % 8) == 7)
							{
								//Zero element
								if(tiles[i].getId() == 56)
								{
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
								}
								
								//7th element
								else if(tiles[i].getId() == 63)
								{
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
								}
								
								//57 - 62
								else 
								{
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
								}
							}
							
							//Zero column ONLY 16 - 40
							else if(((tiles[i].getId() % 8) == 0)
									&& (tiles[i].getId() >= 16)
									&& tiles[i].getId() <= 40)
							{
								/* 
								 * Check forward move 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/* Check forward right move 
								 *
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check right move
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards right
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
							}
						}
					}
					
					//If tile contains any black piece
					else
					{
						continue;
					}
				}
				
				//If tile contains no piece
				else
					continue;
			}
			
			/*
			 * White king is checkmated if none of the above "checks" has returned false 
			 * (Cannot be blocked and there is not escape road)
			 *
			 */
			return true;
		}
		
		
		/*
		 * If black king is under threat from any white piece then check if he's checkmated
		 */
		
		//Check for checkmate
		else if(tiles[blackKingPosition].getIsUnderThreatFromWhite() == true)
		{
			//Check all tiles one by one
			for(int i = 0; i < this.getDimensions()*this.getDimensions(); i++)
			{
				//If tile contains any piece
				if(tiles[i].getContainsPiece() == true)
				{
					//If piece is black
					if(tiles[i].getPiece().getColour() == "Black")
					{
						if(tiles[i].getPiece().getType() == "Pawn")
						{
							//Inner board pawns - Starting position
							if(tiles[i].getId() > 48 && tiles[i].getId() < 55)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Inner board - Random position
							 * 
							 * Just, do not take into consideration the edge tiles and the starting position's tiles
							 * a) 7th column
							 * b) Zero row 
							 * c) 1st Column
							 * d) 6th/Starting row
							 * e) 7th row
							 * 
							 * Disclaimer: The above info comes from blacks' perspective
							 * 
							 */
							if((tiles[i].getId() % 8 != 7) && (!(tiles[i].getId() >= 0 && tiles[i].getId() < 8))
									&&  (tiles[i].getId() % 8 != 0) 
									&&  (!(tiles[i].getId() > 47 && tiles[i].getId() < 64)) )
							{
								//System.out.println("\nInner\n");
								
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}	
							}
							
							/*
							 * Edge board - Starting position
							 */
							//Further left piece
							if(tiles[i].getId() == 55)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							//Further right piece
							if(tiles[i].getId() == 48)
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}	
								
								/*
								 * Check forward move to capture - Two tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-2*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							//Edge board - Random position
							
							/*
							 * Edge board - Random position - Case 1
							 * 
							 * Take into consideration all the edge tiles of 7th column
							 *  but 7's and 6's rows
							 * 
							 */
							if((tiles[i].getId() % 8 == 7) && (tiles[i].getId() != 63) && (tiles[i].getId() != 55))
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardRight move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Edge board - Random position - Case 2
							 * 
							 * Take into consideration all the edge tiles of zero column
							 *  but 6's and 7's rows
							 * 
							 */
			
							if((tiles[i].getId() % 8 == 0) && (tiles[i].getId() != 48) && (tiles[i].getId() != 56))
							{
								/*
								 * Check forward move - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check forwardLeft move to capture - One tile
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}	
							}
						}
						
						/*
						 * Find how many moves are there till the max row/column towards the desired
						 * direction and iteratively move it towards that direction by j steps. 
						 */
						else if(tiles[i].getPiece().getType() == "Rook")
						{
							/*
							 * Forward move
							 * 
							 * The quantity "tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check forward move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Right move
							 * 
							 * The quantity "tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Backwards move
							 * 
							 * The quantity "7 - tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check backwards move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Left move
							 * 
							 * The quantity "7 - tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == false)
								{
									return false;
								}
							}
						}
						
						/*
						 * Check where the knight lies on and then act
						 */
						else if(tiles[i].getPiece().getType() == "Knight")
						{
							/*
							 * Inner board
							 * 
							 * Exclude:
							 * 1) 7th and 6th and 1st and zero row
							 * 2) 7th and 6th and 1st and zero column
							 * 
							 */
							if(((tiles[i].getId() < 46) && (tiles[i].getId() > 17))
									&& ((tiles[i].getId() % 8) != 7)
									&& ((tiles[i].getId() % 8) != 6)
									&& ((tiles[i].getId() % 8) != 1)
									&& ((tiles[i].getId() % 8) != 0) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
							}
							
							//6th row
							else if(((tiles[i].getId() / 8) == 6))
							{
								
								if(tiles[i].getId() == 55)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 54)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 49)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 48)
								{	
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								//50 - 53
								else
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
							}
							
							//7th row
							else if(((tiles[i].getId() / 8) == 7))
							{
								
								if(tiles[i].getId() == 63)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 62)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 57)
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 56)
								{
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
								
								//58-61
								else
								{
									/*
									 * Check ForwardRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
								}
							}
							
							//1st column ONLY from 41 to 17
							else if(((tiles[i].getId() % 8) == 1)
									&& (tiles[i].getId() <= 41) 
									&& (tiles[i].getId() >= 17) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
							}
							
							//Zero column ONLY from 40 to 16
							else if(((tiles[i].getId() % 8) == 0)
									&& (tiles[i].getId() <= 40) 
									&& (tiles[i].getId() >= 16) )
							{
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
								{
									return false;
								}
							}
							
							//1st row
							else if(((tiles[i].getId() / 8) == 1))
							{
								
								if(tiles[i].getId() == 8)
								{
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 9)
								{
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if (tiles[i].getId() == 14)
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
								}
								
								else if (tiles[i].getId() == 15)
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								//10 - 13
								else
								{
									/*
									 * Check ForwardRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
									{
										return false;
									}
									
									/*
									 * Check ForwardLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
							}
							
							//Zero row
							else if(((tiles[i].getId() / 8) == 0))
							{
								if(tiles[i].getId() == 0)
								{
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 1)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 6)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 7)
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
								}
								
								//2-5
								else
								{
									/*
									 * Check BackwardsRight Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsRight Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Long L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
									{
										return false;
									}
									
									/*
									 * Check BackwardsLeft Short L
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+10]) == false)
									{
										return false;
									}
								}
							}
							
							//6th column ONLY from 46 to 22
							else if(((tiles[i].getId() % 8) == 6)
									&& (tiles[i].getId() <= 22) 
									&& (tiles[i].getId() >= 46) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsLeft Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+17]) == false)
								{
									return false;
								}
							}
							
							//7th column ONLY from 47 to 23
							else if(((tiles[i].getId() % 8) == 7)
									&& (tiles[i].getId() <= 47) 
									&& (tiles[i].getId() >= 23) )
							{
								/*
								 * Check ForwardRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-17]) == false)
								{
									return false;
								}
								
								/*
								 * Check ForwardRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-10]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Long L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+15]) == false)
								{
									return false;
								}
								
								/*
								 * Check BackwardsRight Short L
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+6]) == false)
								{
									return false;
								}
							}
						}
						else if(tiles[i].getPiece().getType() == "Bishop")
						{
							/* Forward right
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((tiles[i].getId()/8)) > ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((((tiles[i].getId()/8)) <= ((tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Forward left
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(7 - tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((tiles[i].getId()/8)) > ((7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if(((tiles[i].getId()/8)) <= ((7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards right
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((7 - (tiles[i].getId()/8))) > ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if(((7 - (tiles[i].getId()/8))) <= ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards left
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "7 - (tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((7 - (tiles[i].getId()/8))) > ((7 - (tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7 - (tiles[i].getId()%8)));
							if(((7 - (tiles[i].getId()/8))) <= ((7 - (tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
						}
						else if(tiles[i].getPiece().getType() == "Queen")
						{
							/* Forward move
							 * 
							 * The quantity "tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check forward move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/* Forward right
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((tiles[i].getId()/8)) > ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if((((tiles[i].getId()/8)) <= ((tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Right move
							 * 
							 * The quantity "tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 */
							for(int j = 0 ; j < (tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Backwards right
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "(tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((7 - (tiles[i].getId()/8))) > ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()%8));
							if(((7 - (tiles[i].getId()/8))) <= ((tiles[i].getId()%8)))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards right move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()-(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Backwards move
							 * 
							 * The quantity "7 - tiles[i].getId()/8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()/8) ; j++)
							{
								/*
								 * Check backwards move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()]) == false)
								{
									return false;
								}
							}
							
							/* Backwards left
							 * 
							 * The quantity "(7-(tile.getId()/8))" is the number of tiles from
							 * the tile that the piece lies on, to the 7th row of the board 
							 * 
							 * The quantity "7 - (tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((7 - (tiles[i].getId()/8))) > ((7 - (tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7 - (tiles[i].getId()%8)));
							if(((7 - (tiles[i].getId()/8))) <= ((7 - (tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()/8)));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check backwards left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/*
							 * Left move
							 * 
							 * The quantity "7 - tiles[i].getId()%8" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 */
							for(int j = 0 ; j < (7 - tiles[i].getId()%8) ; j++)
							{
								/*
								 * Check left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+(j+1)]) == false)
								{
									return false;
								}
							}
							
							/* Forward left
							 * 
							 * The quantity "(tile.getId()/8)" is the number of tiles from
							 * the tile that the piece lies on, to the zero row of the board 
							 * 
							 * The quantity "(7 - tile.getId()%8)" is the number of tiles from
							 * the tile that the piece lies on, to the 7th column of the board 
							 * 
							 * Set the "under threat" status as true, till it gets blocked by any piece
							 * on its path. In fact, whenever it is blocked by any piece just set the 
							 * "under threat" status as true and then break (exit the loop) 
							 * 
							 */
							
							//Find max number of potential distance in order not to throw ArrayOutOfBounds
							if(((tiles[i].getId()/8)) > ((7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((7-(tiles[i].getId()%8)));
							if(((tiles[i].getId()/8)) <= ((7-(tiles[i].getId()%8))))
								tiles[tiles[i].getId()].getPiece().setMaxNumberOfPotentialDistance((tiles[i].getId()/8));
							
							for(int j = 0 ; j < tiles[tiles[i].getId()].getPiece().getMaxNumberOfPotentialDistance() ; j++)
							{
								/*
								 * Check forward left move - j tiles
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-(j+1)*this.getDimensions()+(j+1)]) == false)
								{
									return false;
								}
							}
							
						}
						else if(tiles[i].getPiece().getType() == "King")
						{
							/*
							 * Inner board
							 * 
							 * Exclude:
							 * 1) Zero 7th row
							 * 2) Zero 7th column
							 * 
							 */
							if(((tiles[i].getId() > 8) && (tiles[i].getId() < 55))
									&& ((tiles[i].getId() % 8) != 0)
									&& ((tiles[i].getId() % 8) != 7))
							{
								/* 
								 * Check forward move 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/* Check forward right move 
								 *
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check right move
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards right
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}
							}
							
							//6th row ONLY zero and 7th
							else if((tiles[i].getId() / 8) == 6
									&& ((tiles[i].getId() == 48) || (tiles[i].getId() == 55)))
							{
								if(tiles[i].getId() == 48)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
								}
								
								else if(tiles[i].getId() == 55)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
								}
							}
							
							//7th row
							else if((tiles[i].getId() / 8) == 7)
							{
								//Zero element
								if(tiles[i].getId() == 56)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
								}
								
								//7th element
								else if(tiles[i].getId() == 63)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
								}
								
								//57 - 62
								else
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
								}
							}
							
							//Zero column ONLY 40 - 16
							else if(((tiles[i].getId() % 8) == 0)
									&& (tiles[i].getId() <= 40)
									&& tiles[i].getId() >= 16)
							{
								/*
								 * Check forward move 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
								{
									return false;
								}
								
								/*
								 * Check forward left
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
								{
									return false;
								}
							}
							
							//1st row ONLY zero and 7th
							else if(((tiles[i].getId() / 8) == 1)
									&& ((tiles[i].getId() == 7) || (tiles[i].getId() == 7)))
							{
								//Zero element
								if(tiles[i].getId() == 7)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
								}
								
								//7th element	
								else if(tiles[i].getId() == 15)
								{
									/*
									 * Check forward move 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
									{
										return false;
									}
									
									/* Check forward right move 
									 *
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
								}	
							}
							
							//Zero row
							else if((tiles[i].getId() / 8) == 0)
							{
								//Zero element
								if(tiles[i].getId() == 0)
								{
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check forward left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()+1]) == false)
									{
										return false;
									}
								}
								
								//7th element
								else if(tiles[i].getId() == 7)
								{
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
								}
								
								//1-6
								else
								{
									/*
									 * Check right move
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards right
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards 
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
									{
										return false;
									}
									
									/*
									 * Check backwards left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()+1]) == false)
									{
										return false;
									}
									
									/*
									 * Check left
									 */
									//Still king's in check - Move on to check if any next piece blocks it
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == true)
									{
										;
									}
									//There is an escape road. Return false to signify that there is NO checkmate
									if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+1]) == false)
									{
										return false;
									}
								}
							}
							
							//7th column ONLY 47 -23
							else if(((tiles[i].getId() % 8) == 7)
									&& (tiles[i].getId() <= 47)
									&& tiles[i].getId() >= 23)
							{
								/*
								 * Check forward move 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()]) == false)
								{
									return false;
								}
								
								/* Check forward right move 
								 *
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check right move
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards right
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()-1]) == false)
								{
									return false;
								}
								
								/*
								 * Check backwards 
								 */
								//Still king's in check - Move on to check if any next piece blocks it
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == true)
								{
									;
								}
								//There is an escape road. Return false to signify that there is NO checkmate
								if(this.helpingMoveForCheckmate(tiles, tiles[i], tiles[i+this.getDimensions()]) == false)
								{
									return false;
								}
							}
						}
					}
					
					//If tile contains any white piece
					else
					{
						continue;
					}
				}
				
				//If tile contains no piece
				else
					continue;
			}
			
			/*
			 * Black king is checkmated if none of the above "checks" has returned false 
			 * (Cannot be blocked and there is not escape road)
			 *
			 */
			return true;
		}
		
		
		//Else if none of the kings is under attack, there is no checkmate
		else
			return false;
	}
	
	/*
	 * Checks if castling is possible and if it is, then do it
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the king
	 * @param2: destinaltionTile -> The tile that the player wants to move his king to
	 * 
	 */
	public boolean checkForCastling(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		//If start tile contains a piece
		if(startTile.getContainsPiece() == true)
		{
			//If start tile contains any king and has done NO moves yet
			if(startTile.getPiece().getType() == "King"
				&& (startTile.getPiece().getMovesMade() == 0))
			{
				
				//Checks white castling first
				if((startTile.getPiece().getColour() == "White")
					&&(startTile.getId() == 4))
				{
					//Reset the "under threat" status of tiles
					this.resetThreatStatusOfTiles(tiles);
					
					//Set the "under threat" status of tiles
					this.checkAllThreatStatusOfTiles(tiles);
					
					//Long castling
					if((tiles[1].getContainsPiece() == false)
						&& (tiles[2].getContainsPiece() == false)
						&& (tiles[3].getContainsPiece() == false)
						&& (tiles[0].getContainsPiece() == true)
						&& (tiles[0].getPiece().getType() == "Rook")
						&& (tiles[0].getPiece().getColour() == "White")
						&& (tiles[0].getPiece().getMovesMade() == 0)
						&& (tiles[0].getIsUnderThreatFromBlack() == false)
						&& (tiles[1].getIsUnderThreatFromBlack() == false)
						&& (tiles[2].getIsUnderThreatFromBlack() == false)
						&& (tiles[3].getIsUnderThreatFromBlack() == false)
						&& (tiles[4].getIsUnderThreatFromBlack() == false)
						&& (destinationTile.getId() == 2))
					{
						//Move the king to his destinationTile
						destinationTile.setPiece(startTile.getPiece());
						destinationTile.setContainsPiece(true);
						destinationTile.setIcon(new ImageIcon(findImage(startTile)));
						
						//Remove king from startTile
						startTile.setPiece(null);
						startTile.setContainsPiece(false);
						startTile.setIcon(null);
						
						//Move the rook to its destinationTile
						tiles[3].setPiece(tiles[0].getPiece());
						tiles[3].setContainsPiece(true);
						tiles[3].setIcon(new ImageIcon(findImage(tiles[0])));
						
						//Remove rook from his starting tile
						tiles[0].setPiece(null);
						tiles[0].setContainsPiece(false);
						tiles[0].setIcon(null);
						
						return true;
					}
					
					//Short castling
					else if((tiles[5].getContainsPiece() == false)
							&& (tiles[6].getContainsPiece() == false)
							&& (tiles[7].getContainsPiece() == true)
							&& (tiles[7].getPiece().getType() == "Rook")
							&& (tiles[7].getPiece().getColour() == "White")
							&& (tiles[7].getPiece().getMovesMade() == 0)
							&& (tiles[4].getIsUnderThreatFromBlack() == false)
							&& (tiles[5].getIsUnderThreatFromBlack() == false)
							&& (tiles[6].getIsUnderThreatFromBlack() == false)
							&& (tiles[7].getIsUnderThreatFromBlack() == false)
							&& (destinationTile.getId() == 6))
					{
						//Move the king to his destinationTile
						destinationTile.setPiece(startTile.getPiece());
						destinationTile.setContainsPiece(true);
						destinationTile.setIcon(new ImageIcon(findImage(startTile)));
						
						//Remove king from startTile
						startTile.setPiece(null);
						startTile.setContainsPiece(false);
						startTile.setIcon(null);
						
						//Move the rook to its destinationTile
						tiles[5].setPiece(tiles[7].getPiece());
						tiles[5].setContainsPiece(true);
						tiles[5].setIcon(new ImageIcon(findImage(tiles[7])));
						
						//Remove rook from his starting tile
						tiles[7].setPiece(null);
						tiles[7].setContainsPiece(false);
						tiles[7].setIcon(null);
						
						return true;
					}
					
				}
				
				//Check black castling
				else if((startTile.getPiece().getColour() == "Black")
						&&(startTile.getId() == 60))
				{
					//Reset the "under threat" status of tiles
					this.resetThreatStatusOfTiles(tiles);
					
					//Set the "under threat" status of tiles
					this.checkAllThreatStatusOfTiles(tiles);
					
					//Long castling
					if((tiles[57].getContainsPiece() == false)
						&& (tiles[58].getContainsPiece() == false)
						&& (tiles[59].getContainsPiece() == false)
						&& (tiles[56].getContainsPiece() == true)
						&& (tiles[56].getPiece().getType() == "Rook")
						&& (tiles[56].getPiece().getColour() == "Black")
						&& (tiles[56].getPiece().getMovesMade() == 0)
						&& (tiles[56].getIsUnderThreatFromWhite() == false)
						&& (tiles[57].getIsUnderThreatFromWhite() == false)
						&& (tiles[58].getIsUnderThreatFromWhite() == false)
						&& (tiles[59].getIsUnderThreatFromWhite() == false)
						&& (tiles[60].getIsUnderThreatFromWhite() == false)
						&& (destinationTile.getId() == 58))
					{
						//Move the king to his destinationTile
						destinationTile.setPiece(startTile.getPiece());
						destinationTile.setContainsPiece(true);
						destinationTile.setIcon(new ImageIcon(findImage(startTile)));
						
						//Remove king from startTile
						startTile.setPiece(null);
						startTile.setContainsPiece(false);
						startTile.setIcon(null);
						
						//Move the rook to its destinationTile
						tiles[59].setPiece(tiles[56].getPiece());
						tiles[59].setContainsPiece(true);
						tiles[59].setIcon(new ImageIcon(findImage(tiles[56])));
						
						//Remove rook from his starting tile
						tiles[56].setPiece(null);
						tiles[56].setContainsPiece(false);
						tiles[56].setIcon(null);
						
						return true;
					}
					
					//Short castling
					else if((tiles[61].getContainsPiece() == false)
							&& (tiles[62].getContainsPiece() == false)
							&& (tiles[63].getContainsPiece() == true)
							&& (tiles[63].getPiece().getType() == "Rook")
							&& (tiles[63].getPiece().getColour() == "Black")
							&& (tiles[63].getPiece().getMovesMade() == 0)
							&& (tiles[60].getIsUnderThreatFromWhite() == false)
							&& (tiles[61].getIsUnderThreatFromWhite() == false)
							&& (tiles[62].getIsUnderThreatFromWhite() == false)
							&& (tiles[63].getIsUnderThreatFromWhite() == false)
							&& (destinationTile.getId() == 62))
					{
						//Move the king to his destinationTile
						destinationTile.setPiece(startTile.getPiece());
						destinationTile.setContainsPiece(true);
						destinationTile.setIcon(new ImageIcon(findImage(startTile)));
						
						//Remove king from startTile
						startTile.setPiece(null);
						startTile.setContainsPiece(false);
						startTile.setIcon(null);
						
						//Move the rook to its destinationTile
						tiles[61].setPiece(tiles[63].getPiece());
						tiles[61].setContainsPiece(true);
						tiles[61].setIcon(new ImageIcon(findImage(tiles[63])));
						
						//Remove rook from his starting tile
						tiles[63].setPiece(null);
						tiles[63].setContainsPiece(false);
						tiles[63].setIcon(null);
						
						return true;
					}
				}
			}
			/*
			 * If any of the requirements is not fulfilled then return false
			 *  to signify no castling is possible
			 */
			return false;
		}
		
		//StartTile does not contain any piece
		else
			return false;
	}
	
	/*
	 * Takes back any move (if it is illegal)
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the move
	 * @param2: destinaltionTile -> The tile that the player wants the piece to move
	 * @param3: copyStartTile -> A copy of the start tile
	 * @param2: copyDestinaltionTile -> A copy of the destination tile
	 * 
	 */
	public void takeMoveBack(Tile[] tiles, Tile startTile, Tile destinationTile, Tile copyStartTile, Tile copyDestinationTile)
	{
		/*
		 * Check whether copyDestinationTile contains opponent's piece or nothing 
		 */
		
		//Contains nothing
		if(copyDestinationTile.getContainsPiece() == false)
		{
			//At first return data to the startTile tile and update the status
			startTile.setPiece(copyStartTile.getPiece());
			startTile.setContainsPiece(true);
			/*	
			System.out.println(startTile.getPiece().getType());
			System.out.println(copyDestinationTile.getPiece().getType());
			
			
			System.out.println("Move's taken back -> New data");
			System.out.println(startTile.getContainsPiece());
			System.out.println(startTile.getPiece().getType());
			 */	
			//Then remove data from the destination tile and update the status
			destinationTile.setPiece(null);
			destinationTile.setContainsPiece(false);
		}
		
		
		//Contains opponent's piece
		else
			if(copyDestinationTile.getPiece().getColour() != copyStartTile.getPiece().getColour())
			{
				//At first return data to the startTile tile and update the status
				startTile.setPiece(copyStartTile.getPiece());
				startTile.setContainsPiece(true);
				
				//Then return the captured piece's data to the destination tile and update the status
				destinationTile.setPiece(copyDestinationTile.getPiece());
				destinationTile.setContainsPiece(true);
			}
		
		
	}
	
	/*
	 * Makes the move (updates the board) at the background regardless of their legality.
	 * The move is visualized ONLY if the move is legal
	 * 
	 * @param1: tiles -> All the tiles
	 * @param1: startTile -> The starting tile of the move
	 * @param2: destinaltionTile -> The tile that the player wants the piece to move
	 * 
	 * @return: true if move is legal, else false
	 * 
	 */
	public boolean makeMove(Tile[] tiles, Tile startTile, Tile destinationTile)
	{
		/*
		 * If a move is at first legal, make it in the background but do not visualize it.
		 * Check if the same colored king is in check after the move. If not, then the move is
		 * from all aspects legal, so visualize it. BUT, if the king is in check the piece was pinned so it could
		 * not move and the move was illegal.In this case take it back
		 *  
		 */
		
		//Check first legality of the move
		if(this.checkAllValidationOfMove(tiles, startTile, destinationTile) == true)
		{
			//First of all create a copy of startTile and destinationTile
			Tile copyDestinationTile = new Tile(destinationTile);
			Tile copyStartTile = new Tile(startTile);
			
			//Then add data to the destination tile and update the status
			destinationTile.setPiece(startTile.getPiece());
			destinationTile.setContainsPiece(true);
					
			//Then remove data from the start tile and update the status
			startTile.setPiece(null);
			startTile.setContainsPiece(false);
			
			//Reset "under threat" status of tiles
			this.resetThreatStatusOfTiles(tiles);
			
			//Check "under threat" status of tiles
			this.checkAllThreatStatusOfTiles(tiles);
			
			//Check if same colored king is in check and if yes, then return false
			for(int j = 0 ; j < this.getDimensions()*this.getDimensions(); j++)
			{
				if(destinationTile.getPiece().getColour() == "White")
				{
					//If white king is under threat from any black piece
					if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromBlack() == true))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return false;
					}
				}
				else if(destinationTile.getPiece().getColour() == "Black")
				{
					//If black king is under threat from any white piece
					if((tiles[j].getContainsPiece() == true)
						&& (tiles[j].getPiece().getType() == "King")
						&& (tiles[j].getPiece().getColour() == destinationTile.getPiece().getColour())
						&& (tiles[j].getIsUnderThreatFromWhite() == true))
					{
						//Take the move back
						this.takeMoveBack(tiles, startTile, destinationTile, copyStartTile, copyDestinationTile);
						return false;
					}
				}
			}
			
			
			/*
			 * If move is from all aspects legal, visualize it and return true
			 */
			
			//Visualize it
			destinationTile.setIcon(new ImageIcon(findImage(copyStartTile)));
			startTile.setIcon(null);
			return true;
		}
		
		//If first legality test is not passed, return false
		else
			return false;
	}
	
	/*
	 * Promotes any pawn to a player's choice new piece
	 * 
	 * 
	 * @param1: destinaltionTile -> The tile that the pawn has reached in order to be promoted 
	 * 
	 * @return: true if move is legal, else false
	 * 
	 */
	public void checkForPromotion(Tile destinationTile)
	{
		/* 
		 * Check for whites promotion
		 * 
		 */
		//If any white pawn has reached 7th row (rank)
		if(((destinationTile.getId() / 8) == 7)
			&& (destinationTile.getContainsPiece() == true)
			&& (destinationTile.getPiece().getColour() == "White")
			&& (destinationTile.getPiece().getType() == "Pawn"))
		{
			System.out.println("White to Promote");
			
			whitePromotionFrame.setSize(800, 200);
			whitePromotionFrame.setLayout(new GridLayout(1,4));
			
			//Set image icon the corresponding button
			whiteRookButton.setIcon(new ImageIcon(whiteRook));
			whiteKnightButton.setIcon(new ImageIcon(whiteKnight));
			whiteBishopButton.setIcon(new ImageIcon(whiteBishop));
			whiteQueenButton.setIcon(new ImageIcon(whiteQueen));
			
			//Add the button to the pop up frame
			whitePromotionFrame.add(whiteRookButton);
			whitePromotionFrame.add(whiteKnightButton);
			whitePromotionFrame.add(whiteBishopButton);
			whitePromotionFrame.add(whiteQueenButton);
			
			whitePromotionFrame.setVisible(true);
		}
		
		/* 
		 * Check for blacks promotion
		 * 
		 */
		//If any black pawn has reached zero row (rank)
		else if(((destinationTile.getId() / 8) == 0)
			&& (destinationTile.getContainsPiece() == true)
			&& (destinationTile.getPiece().getColour() == "Black")
			&& (destinationTile.getPiece().getType() == "Pawn"))
		{
			System.out.println("Black to promote");
			
			blackPromotionFrame.setSize(800, 200);
			blackPromotionFrame.setLayout(new GridLayout(1,4));
			
			//Set image icon the corresponding button
			blackRookButton.setIcon(new ImageIcon(blackRook));
			blackKnightButton.setIcon(new ImageIcon(blackKnight));
			blackBishopButton.setIcon(new ImageIcon(blackBishop));
			blackQueenButton.setIcon(new ImageIcon(blackQueen));
			
			//Add the button to the pop up frame
			blackPromotionFrame.add(blackRookButton);
			blackPromotionFrame.add(blackKnightButton);
			blackPromotionFrame.add(blackBishopButton);
			blackPromotionFrame.add(blackQueenButton);
			
			blackPromotionFrame.setVisible(true);
		}
	}

	//Helps to use the ActionListener of game class
	// https://stackoverflow.com/questions/19104014/add-an-actionlistener-to-a-jbutton-from-another-class
	public void setButtonsActionListener(ActionListener listener)
	{
		whiteRookButton.addActionListener(listener);
		whiteKnightButton.addActionListener(listener);
		whiteBishopButton.addActionListener(listener);
		whiteQueenButton.addActionListener(listener);
		
		blackRookButton.addActionListener(listener);
		blackKnightButton.addActionListener(listener);
		blackBishopButton.addActionListener(listener);
		blackQueenButton.addActionListener(listener);	
	}
	
	//Makes the promotion
	public void makePromotion(Tile destinationTile, String newPieceType)
	{
		//White promotion
		if(destinationTile.getPiece().getColour() == "White")
		{
			if(newPieceType == "Rook")
			{
				//Create a new rook
				Piece newRook = new Piece();
				
				newRook.setType("Rook");
				newRook.setId(newPieceId);
				newRook.setValue(5);
				newRook.setColour("White");
				newRook.setImage(whiteRook);
				newPieceId++;
				
				//Add the newly created rook to the promotion tile
				destinationTile.setPiece(newRook);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(whiteRook));
			}
			
			else if(newPieceType == "Knight")
			{
				//Create a new knight
				Piece newKnight = new Piece();
				
				newKnight.setType("Knight");
				newKnight.setId(newPieceId);
				newKnight.setValue(3);
				newKnight.setColour("White");
				newKnight.setImage(whiteKnight);
				newPieceId++;
				
				//Add the newly created knight to the promotion tile
				destinationTile.setPiece(newKnight);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(whiteKnight));
			}
			
			else if(newPieceType == "Bishop")
			{
				//Create a new bishop
				Piece newBishop = new Piece();
				
				newBishop.setType("Bishop") ;
				newBishop.setId(newPieceId) ;
				newBishop.setValue(3) ;
				newBishop.setColour("White") ;
				newBishop.setImage(whiteBishop);
				newPieceId++;
				
				//Add the newly created bishop to the promotion tile
				destinationTile.setPiece(newBishop);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(whiteBishop));
			}
			
			else if(newPieceType == "Queen")
			{
				//Create a new queen
				Piece newQueen = new Piece();
				
				newQueen.setType("Queen") ;
				newQueen.setId(newPieceId) ;
				newQueen.setValue(9) ;
				newQueen.setColour("White") ;
				newQueen.setImage(whiteQueen);
				newPieceId++;
				
				//Add the newly created queen to the promotion tile
				destinationTile.setPiece(newQueen);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(whiteQueen));
			}
		}
		
		//Black promotion
		else if(destinationTile.getPiece().getColour() == "Black")
		{
			if(newPieceType == "Rook")
			{
				//Create a new rook
				Piece newRook = new Piece();
				
				newRook.setType("Rook");
				newRook.setId(newPieceId);
				newRook.setValue(5);
				newRook.setColour("Black");
				newRook.setImage(blackRook);
				newPieceId++;
				
				//Add the newly created rook to the promotion tile
				destinationTile.setPiece(newRook);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(blackRook));
			}
			
			else if(newPieceType == "Knight")
			{
				//Create a new knight
				Piece newKnight = new Piece();
				
				newKnight.setType("Knight");
				newKnight.setId(newPieceId);
				newKnight.setValue(3);
				newKnight.setColour("Black");
				newKnight.setImage(blackKnight);
				newPieceId++;
				
				//Add the newly created knight to the promotion tile
				destinationTile.setPiece(newKnight);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(blackKnight));
			}
			
			else if(newPieceType == "Bishop")
			{
				//Create a new bishop
				Piece newBishop = new Piece();
				
				newBishop.setType("Bishop") ;
				newBishop.setId(newPieceId) ;
				newBishop.setValue(3) ;
				newBishop.setColour("Black") ;
				newBishop.setImage(blackBishop);
				newPieceId++;
				
				//Add the newly created bishop to the promotion tile
				destinationTile.setPiece(newBishop);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(blackBishop));
			}
			
			else if(newPieceType == "Queen")
			{
				//Create a new queen
				Piece newQueen = new Piece();
				
				newQueen.setType("Queen") ;
				newQueen.setId(newPieceId) ;
				newQueen.setValue(9) ;
				newQueen.setColour("Black") ;
				newQueen.setImage(blackQueen);
				newPieceId++;
				
				//Add the newly created queen to the promotion tile
				destinationTile.setPiece(newQueen);
				destinationTile.setContainsPiece(true);
				destinationTile.setIcon(new ImageIcon(blackQueen));
			}
		}
		
	}
		   
	
}

