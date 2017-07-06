/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static main.CoveceNeLjutiSe.game;
import sprites.figures.Figure;

/**
 *
 * @author Aleksandar
 */
public class Utils
{
    public static final String colourNames[] = {"Red", "Green", "Blue", "Yellow"};
    public static final String figureNames[] = {"Classic", "Round", "Simple", "Square"};
    
    public static final Color playerColours[] = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    public static final Color fieldColours[] = {new Color(1, 0.25, 0.25, 1), new Color(0.25, 1, 0.25, 1), new Color(0.25, 0.25, 1, 1), new Color(1, 1, 0.5, 1)};
    public enum cameraMovement {LEFT, STATIC, RIGHT, UP, DOWN}
    public enum cameraZoom {IN, STATIC, OUT}
    
    public static double baseY = -3;
    public static double dimensions = 500; //Table dimensions
    public static double width = dimensions / 13; //Path width
    public static double radius = dimensions / 10; //Houses radius
    public static double translate = 3 * dimensions / 8; //Houses translation
    
    public static double fields[][] = {
        {width, 6 * width}, {0, 6 * width}, {-width, 6 * width}, {-width, 5 * width}, {-width, 4 * width}, {-width, 3 * width}, {-width, 2 * width}, {-2 * width, 2 * width}, {-2 * width, width}, {-3 * width, width}, {-4 * width, width}, {-5 * width, width},
        
        {-6 * width, width}, {-6 * width, 0}, {-6 * width, -width}, {-5 * width, -width}, {-4 * width, -width}, {-3 * width, -width}, {-2 * width, -width}, {-2 * width, -2 * width}, {-width, -2 * width}, {-width, -3 * width}, {-width, -4 * width}, {-width, -5 * width},
        
        {-width, -6 * width}, {0, -6 * width}, {width, -6 * width}, {width, -5 * width}, {width, -4 * width}, {width, -3 * width}, {width, -2 * width}, {2 * width, -2 * width}, {2 * width, -width}, {3 * width, -width}, {4 * width, -width}, {5 * width, -width},
        
        {6 * width, -width}, {6 * width, 0}, {6 * width, width}, {5 * width, width}, {4 * width, width}, {3 * width, width}, {2 * width, width}, {2 * width, 2 * width}, {width, 2 * width}, {width, 3 * width}, {width, 4 * width}, {width, 5 * width}
    };
    public static int start[] = {38, 2, 14, 26};
    
    public static double housesCenter[][] = {
        {translate, -translate},
        {-translate, -translate},
        {-translate, translate},
        {translate, translate}
    };
    
    public static double houses[][][] = {
        {{translate - radius / 4, -translate - radius / 4}, {translate - radius / 4, -translate + radius / 4}, {translate  + radius / 4, -translate + radius / 4}, {translate + radius / 4, -translate - radius / 4}},
        
        {{-translate - radius / 4, -translate - radius / 4}, {-translate - radius / 4, -translate + radius / 4}, {-translate  + radius / 4, -translate + radius / 4}, {-translate + radius / 4, -translate - radius / 4}},
        
        {{-translate - radius / 4, translate - radius / 4}, {-translate - radius / 4, translate + radius / 4}, {-translate  + radius / 4, translate + radius / 4}, {-translate + radius / 4, translate - radius / 4}},
        
        {{translate - radius / 4, translate - radius / 4}, {translate - radius / 4, translate + radius / 4}, {translate  + radius / 4, translate + radius / 4}, {translate + radius / 4, translate - radius / 4}}
    };
    
    public static double finishes[][][] = {
        {{5 * width, 0}, {4 * width, 0}, {3 * width, 0}, {2 * width, 0}},
        {{0, 5 * width}, {0, 4 * width}, {0, 3 * width}, {0, 2 * width}},
        {{-5 * width, 0}, {-4 * width, 0}, {-3 * width, 0}, {-2 * width, 0}},
        {{0, -5 * width}, {0, -4 * width}, {0, -3 * width}, {0, -2 * width}}
    };
    
    /*
        -1 = field is empty
        0-3 = player1 figures
        4-7 = player2 figures
        8-11 = player3 figures
        12-15 = player4 figures
    */
    public static int[] positions = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    
    public static Player[] players = {new Player(0), new Player(1), new Player(2), new Player(3)};
    
    public static class Player
    {
        public static boolean taken[] = {false, false, false, false};
        
        public boolean playing = false;
        private int index; 
        
        
        public int colour;
        public int figureType;
        
        public int house[] = {0, 1, 2, 3};
        public int finish[] = {-1, -1, -1, -1};
        
        public Figure[] figures = new Figure[4];
        public int[] positions = {-1, -1, -1, -1};  //-1 = home, 48-51 = finish
        
        public Player(int i)
        {
            index = i;
        }
        
        public boolean markAvailable(int diceRoll)
        {
            boolean res = false;
            for (int i = 0; i < 4; ++i)
            {
                int pos = positions[i];
                if (pos != -1 && pos < 48)
                {
                    if (positions[i] < Utils.start[index])
                        pos = positions[i] + 48 - Utils.start[index];
                    else
                        pos = positions[i] - Utils.start[index];
                }
                if (positions[i] == -1)
                {
                    if (diceRoll == 6 && (Utils.positions[Utils.start[index]] == -1 || Utils.positions[Utils.start[index]] / 4 != index))
                    {
                        figures[i].mark();
                        figures[i].changeColours(Utils.fieldColours[colour]);
                        res = true;
                    }
                }
                else if (pos < 48 - diceRoll)
                {
                    if (Utils.positions[(positions[i] + diceRoll) % 48] == -1 || Utils.positions[(positions[i] + diceRoll) % 48] / 4 != index)
                    {
                        figures[i].mark();
                        figures[i].changeColours(Utils.fieldColours[colour]);
                        res = true;
                    }
                }
                else
                {
                    if (pos + diceRoll < 52 && finish[(pos + diceRoll) - 48] == -1)
                    {
                        figures[i].mark();
                        figures[i].changeColours(Utils.fieldColours[colour]);
                        res = true;
                    }    
                }
            }
            return res;
        }
        
        public void moveToHouse(int figure)
        {
            if (positions[figure] == -1)
                return;
            for (int i = 0; i < 4; ++i)
                if (house[i] == -1)
                {
                    figures[figure].move(Utils.houses[index][figure][0], Utils.houses[index][figure][1], false);
                    house[i] = figure;
                    Utils.positions[positions[figure]] = -1;
                    positions[figure] = -1;
                    return;
                }
        }
        
        public void moveFromHouse(int figure)
        {
            for (int i = 0; i < 4; ++i)
                if (house[i] == figure)
                {
                    figures[figure].move(Utils.fields[Utils.start[index]][0], -Utils.fields[Utils.start[index]][1], true);
                    house[i] = -1;
                    Utils.positions[Utils.start[index]] = 4 * index + figure;
                    positions[figure] = Utils.start[index];
                    return;
                }
        }
        
        public void moveToDest(int diceRoll, int figure)
        {
            int pos = 0;
                if (positions[figure] < Utils.start[index])
                    pos = positions[figure] + 48 - Utils.start[index];
                else
                    pos = positions[figure] - Utils.start[index];
            if (pos < 48)
                Utils.positions[positions[figure]] = -1;
            else
                finish[positions[figure] - 48] = -1;
            SequentialTransition animation = new SequentialTransition();
            TranslateTransition goUp = new TranslateTransition(Duration.seconds(0.5), figures[figure]);
            goUp.setByY(-Utils.width);
            goUp.setInterpolator(Interpolator.LINEAR);
            animation.getChildren().add(goUp);
            
            TranslateTransition up = new TranslateTransition(Duration.seconds(0.25), figures[figure]);
            up.setByY(-20);
            up.setInterpolator(Interpolator.EASE_OUT);
            TranslateTransition down = new TranslateTransition(Duration.seconds(0.25), figures[figure]);
            down.setByY(20);
            down.setInterpolator(Interpolator.EASE_IN);
            
            SequentialTransition vertical = new SequentialTransition(up, down);
            
            for(int i = 0; i < diceRoll; ++i)
            {
                ++pos;
                
                TranslateTransition horizontal = new TranslateTransition(Duration.seconds(0.5), figures[figure]);
                if (pos < 48)
                {
                    positions[figure] = (Utils.start[index] + pos) % 48;
                    horizontal.setToX(Utils.fields[positions[figure]][0]);
                    horizontal.setToZ(-Utils.fields[positions[figure]][1]);
                }
                else
                {
                    positions[figure] = pos;
                    horizontal.setToX(Utils.finishes[index][pos - 48][0]);
                    horizontal.setToZ(-Utils.finishes[index][pos- 48][1]);
                }
                horizontal.setInterpolator(Interpolator.LINEAR);
                ParallelTransition hop = new ParallelTransition(horizontal, vertical);
                animation.getChildren().add(hop);
            }
            
            if (pos < 48)
                Utils.positions[positions[figure]] = 4 * index + figure;
            
            TranslateTransition goDown = new TranslateTransition(Duration.seconds(0.5), figures[figure]);
            goDown.setByY(Utils.width);
            goDown.setInterpolator(Interpolator.LINEAR);
            animation.getChildren().add(goDown);
            
            animation.setOnFinished(e -> game.onFigureMoved());
            animation.play();
        }
        
        public void unMarkAll()
        {
            for (int i = 0; i < 4; ++i)
            {
                figures[i].unMark();
                figures[i].changeColours(Utils.playerColours[colour]);
            }
        }
        
        public boolean getStarted()
        {
            for (int i = 0; i < 4; ++i)
                if (house[i] == -1)
                    return true;
            return false;
        }
        
        public boolean isFinished()
        {
            for (int i = 0; i < 4; ++i)
                if (finish[i] == -1)
                    return false;
            return true;
        }
        
        public static int getNextFocus(int current)
        {
            if (current == -1)
                current = 0;
            for (int i = 0; i < 16; ++i)
            {
                int index = (i + current) % 16;
                if (Utils.players[index / 4].positions[index % 4] != -1)
                    return index;
            }
            return -1;
        }
        
        public static Bounds getFigureBounds(int figure)
        {
            Figure f = Utils.players[figure / 4].figures[figure % 4];
            return f.getBoundsInParent();
        }
    }
    
    public static boolean rolled;
}
