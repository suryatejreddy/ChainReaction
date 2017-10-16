package NonUIComponents;

import sample.Main;

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
//        for(int i=0;i<players.size();i++)
//        {
//            players.set(i, new Player(i, new ArrayList<Cell>(), true));
//            allPlayers.add(new Player(i,new ArrayList<Cell>(),true));
//        }
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
        System.out.println("Enter X and Y.");
        int x,y;
        x=scanner.nextInt();
        y=scanner.nextInt();
//        int x = 3;
//        int y = 3;
        Matrix gameMatrix=new Matrix(x, y);

        while(allPlayers.size() > 1)
        {
            Player curPlayer = allPlayers.peek();
            System.out.println("Chance of player with " + curPlayer.getPlayerColourByString());
            System.out.println("Enter coordiantes");
            int moveX = scanner.nextInt();
            int moveY = scanner.nextInt();

            Cell cellSelected = gameMatrix.getCellFromCoordinate(moveY,moveX);


            if (cellSelected.isCellOccupied())
            {
                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                if (curCellColor == curPlayer.getPlayerColour())
                { // check if player is adding to his color
                    //add ball function
                    cellSelected.addBall(curPlayer);
                    allPlayers.remove(curPlayer);
                }
                else
                {  //if not
                    //show error, wrong move
                    //we should not remove the player from the queue
                    System.out.println("can't put ball here.already occupied by "+cellSelected.getPlayerOccupiedBy().getPlayerColourByString()+" player.");
                    continue;
                }
            }
            else
            {
                cellSelected.addBall(curPlayer);
                allPlayers.remove(curPlayer);
            }

            for (Player randomPlayer : allPlayers)
            {  //update status for all players to check if they are alive or dead
                if (randomPlayer.hasTakenFirstMove())
                {
                    randomPlayer.checkPlayerStatus();
                    if (!randomPlayer.isAlive())
                    {
                        allPlayers.remove(randomPlayer);
                    }
                }
            }


            if (curPlayer.isAlive())
            {
                allPlayers.add(curPlayer);
            }

            gameMatrix.printMatrix();
        }

        System.out.println(allPlayers.peek() + " Won! Yipeee");
    }

    private void setPlayersDead()
    {
        //TODO write this function for a hashsets
//        for(Player p: this.getPlayers())
//        {
//            if(p.hasTakenFirstMove() && p.getCurrentOccupiedCells().size()==0)
//            {
//                p.setAlive(false);
//                players.remove(p);
//            }
//        }
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
        Scanner scanner = new Scanner(System.in);
        numberOfPlayers = scanner.nextInt();
        int i = 0 ;
        Game chainReactionGame = new Game();
        while(i<numberOfPlayers)
        {
            Player player = new Player(i, true);
            chainReactionGame.allPlayers.add(player); //adding the player to the game
            i ++;
        }
        chainReactionGame.playGame(scanner);
    }
}
