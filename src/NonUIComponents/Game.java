package NonUIComponents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game
{
    private ArrayList<Player> players;


    private Queue<Player> allPlayers;

    public Game()
    {
        players=new ArrayList<Player>();
        allPlayers = new LinkedList<Player>();
        for(int i=0;i<players.size();i++)
        {
            players.set(i, new Player(i, new ArrayList<Cell>(), true));
            allPlayers.add(new Player(i,new ArrayList<Cell>(),true));
        }
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public boolean isGameOver()
    {
        int countOfPlayersAlive=0;
        for(int i=0;i<players.size();i++)
        {
            if(players.get(i).isAlive())
                countOfPlayersAlive++;
        }
        return countOfPlayersAlive==1;
    }

    public Player findWinner()
    {
        Player winner=players.get(0);
        if(this.isGameOver())
        {
            for(int i=0;i<players.size();i++)
            {
                if(players.get(i).isAlive())
                {
                    winner=players.get(i);
                    break;
                }
            }
        }
        return winner;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    private void playGame(Scanner scanner)
    {
        int i = 0;
        System.out.println("Enter X and Y.");
        int x,y;
        x=scanner.nextInt();
        y=scanner.nextInt();
        x = 2;
        y = 2;
        Matrix gameMatrix=new Matrix(x, y);
        while(!isGameOver() && i<players.size())
        {
            Player curPlayer = allPlayers.remove();
            System.out.println("Chance of player with " + curPlayer.getPlayerColourByString());
            System.out.println("Enter coordiantes");
            int moveX = scanner.nextInt();
            int moveY = scanner.nextInt();
            Cell cellSelected = gameMatrix.getCellFromCoordinate(moveX,moveY);
            if (cellSelected.isCellOccupied()){
                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                if (curCellColor == curPlayer.getPlayerColour()){ // check if player is adding to his color
                    //add ball function
                }
                else{  //if not
                    //show error, wrong move
                }
            }
            else{
                //TODO
                //add ball function
            }


//            players.get(i).setTakenFirstMove(true);
//            System.out.println("Player "+(i+1)+"'s chance.");
//            System.out.println("Enter coordinates of matrix(0 based) to tap.");
//            Player currentPlayer= findCurrentPlayer(i);
//            int coordX, coordY;
//            coordX=scanner.nextInt();
//            coordY=scanner.nextInt();
//            Cell cellChosen=findChosenCell(coordX, coordY, gameMatrix);
//
//            if(!gameMatrix.checkIfCellIsFree(cellChosen))
//            {
//                System.out.println("Cell is already occupied by "+cellChosen.getPlayerOccupiedBy().getPlayerColourByString()+" player.");
//                continue;
//            }
//
//            players.get(i).makeMove(coordX, coordY, gameMatrix, this, i, cellChosen, currentPlayer);
//            setPlayersDead();
//            if(i==players.size()-1)
//                i=0;
//            else
//                i++;



        }
    }

    private void setPlayersDead()
    {
        for(Player p: this.getPlayers())
        {
            if(p.hasTakenFirstMove() && p.getCurrentOccupiedCells().size()==0)
            {
                p.setAlive(false);
                players.remove(p);
            }
        }
    }

    private Player findCurrentPlayer(int i)
    {
        return players.get(i);
    }

    private Cell findChosenCell(int coordX, int coordY, Matrix game)
    {
        return game.getCells().get(coordY).get(coordX);
    }

    public static void main(String[] args)
    {
        int numberOfPlayers;
        System.out.println("Enter number of players.");
        Scanner scanner=new Scanner(System.in);
        numberOfPlayers=scanner.nextInt();
        int i=0;
        Game chainReactionGame=new Game();
        while(i<numberOfPlayers)
        {
            Player player=new Player(i, new ArrayList<Cell>(), true);
            chainReactionGame.getPlayers().add(player);
            i++;
        }
        chainReactionGame.playGame(scanner);
    }
}
