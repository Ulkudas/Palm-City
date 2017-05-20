package palmcity;

import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Created by ulkudas on 11.05.2017.
 */
public class Monster
{
    public static final int NUMBER_OF_MONSTERS = 8;

    // power of monsters from 1 to 8
    private final static int[] strengths = {10, 15, 20, 15, 20, 15, 10, 5};

    // names of monsters from 1 to 8
    private final static String[] monsterNames = {"Monster 1","Monster 2","Monster 3","Monster 4",
                                                  "Monster 4","Monster 5","Monster 6", "Monster 8"};

    // descriptions of monster from 1 to 8
    private final static String[] descriptions = {"Cici Canavar","Iyi Canavar","Hos Canavar","Bos Canavar",
                                                  "Kotu Canavar","Guclu Canavar","Buyuk Canavar","Katledici Canavar"};

    private Image[] monsterImages;

    // which monster it is
    private int monsterIndex;

    // the position of the monster
    private int x, y;

    public Monster(int givenIndex, int x, int y)
    {
        monsterImages = new Image[8];

        for ( int i = 0; i < NUMBER_OF_MONSTERS; ++i)
            monsterImages[i] = new javafx.scene.image.Image(getClass().getResourceAsStream("images/monster" + i +".PNG"));

        monsterIndex = givenIndex;
        this.x = x;
        this.y = y;
    }

    public int getIndex(){ return monsterIndex; }

    public Point getPosition(){ return new Point(x,y); }

    public String getDescription(){ return descriptions[monsterIndex]; }

    public String getName(){ return monsterNames[monsterIndex];}

    public Image getImage(){ return monsterImages[monsterIndex]; }

    public int getStrength(){ return strengths[monsterIndex]; }
}