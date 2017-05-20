package palmcity;

/**
 * Created by Enes on 16.05.2017.
 */
import java.awt.Point;

public class GameLogic
{
    // weather conditions in the map (4x4)
    private Point[][] map;

    // whose turn is it?
    private int currentTurn;

    // which monster is showing up in this turn and where
    private Monster currentMonster, prevMonster;

    // name of the players
    public GameLogic()
    {
        map = null;
        currentTurn = 1;
        currentMonster = summonMonster();
        prevMonster = null;
    }


    public Monster summonMonster() {
        //if ( (int)(Math.random() * 100) % 2 == 1)
        //    return null;

        int randIndex = (int) (Math.random() * 8);
        int appearanceRisk = 16;
        int r1 = 1, r2 = 1, r3 = 1, r4 = 1;

        if (getTurn() == 1) {
            appearanceRisk = getMap()[0][0].y + getMap()[0][1].y +
                    getMap()[1][0].y + getMap()[1][1].y;

            r1 = getMap()[0][0].y;
            r2 = getMap()[0][1].y;
            r3 = getMap()[1][0].y;
            r4 = getMap()[1][1].y;
        }
        if (getTurn() == 2) {
            appearanceRisk = getMap()[0][2].y + getMap()[0][3].y +
                    getMap()[1][2].y + getMap()[1][3].y;

            r1 = getMap()[0][2].y;
            r2 = getMap()[0][3].y;
            r3 = getMap()[1][2].y;
            r4 = getMap()[1][3].y;
        }
        if (getTurn() == 3)
        {
            appearanceRisk = getMap()[2][2].y + getMap()[2][3].y +
                    getMap()[3][2].y + getMap()[3][3].y;

            r1 = getMap()[2][2].y;
            r2 = getMap()[2][3].y;
            r3 = getMap()[3][2].y;
            r4 = getMap()[3][3].y;
        }
        if ( getTurn() == 4)
        {
            appearanceRisk = getMap()[2][0].y + getMap()[2][1].y +
                    getMap()[3][0].y + getMap()[3][1].y;

            r1 = getMap()[2][0].y;
            r2 = getMap()[2][1].y;
            r3 = getMap()[3][0].y;
            r4 = getMap()[3][1].y;
        }
        if ( Math.random() < ((double)appearanceRisk/16.0))
            return null;

        else
        {
            int innerRegion = 1;

            int temp = (int)(Math.random()*(r1+r2+r3+r4));

            if ( temp < r1)
                innerRegion = 1;
            else if ( temp < r1+r2)
                innerRegion = 2;
            else if ( temp < r1+r2+r3)
                innerRegion = 3;
            else
                innerRegion = 4;

            int X = (int)(Math.random()*4);
            int Y = (int)(Math.random()*4);

            if ( innerRegion == 1 && !(X + Y == 0))
                return new Monster(randIndex, X, Y);

            else if ( innerRegion == 2 )
                return new Monster(randIndex, X + 4, Y);

            else if ( innerRegion == 3)
                return new Monster(randIndex, X, Y+4);

            else return new Monster(randIndex, X+4, Y+4);
        }

    }

    // to understand whose turn it is
    public int getTurn()
    {
        return currentTurn;
    }

    // returns the current monster
    public Monster getMonster()
    {
        return currentMonster;
    }

    public void nextTurn()
    {
        currentTurn = currentTurn + 1;

        if ( currentTurn == 5)
            currentTurn = 1;

        prevMonster = currentMonster;
        currentMonster = summonMonster();
    }

    public void prevTurn()
    {
        if ( prevMonster == null)
            return ;

        currentTurn = currentTurn - 1;

        if ( currentTurn == 0)
            currentTurn = 4;

        currentMonster = prevMonster;
    }

    public void createMap()
    {
        map = new Point[4][4];

        int[] counter = new int[4];
        boolean[][] powers = new boolean[4][4];

        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
            {
                int weather = (int)(Math.random()*4);

                while ( counter[weather] == 4)
                    weather = (int)(Math.random()*4);

                int power = (int)(Math.random()*4);

                while ( powers[weather][power] == true)
                    power = (int)(Math.random()*4);

                counter[weather]++;
                powers[weather][power] = true;

                map[i][j] = new Point(weather, power + 1);
            }
    }

    public Point[][] getMap()
    {
        if ( map == null)
            createMap();

        return map;
    }

}

