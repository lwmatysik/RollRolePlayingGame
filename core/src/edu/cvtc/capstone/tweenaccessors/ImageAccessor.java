package edu.cvtc.capstone.tweenaccessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lance Matysik on 5/2/16.
 */
public class ImageAccessor implements TweenAccessor<Image> {

    public static final int ALPHA = 0;
    public static final int X = 1;
    public static final int Y = 2;
    public static final int XY = 3;

    @Override
    public int getValues(Image target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case X:
                returnValues[0] = target.getX();
                return 1;
            case Y:
                returnValues[0] = target.getY();
                return 1;
            case XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Image target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case X:
                target.setX(newValues[0]);
                break;
            case Y:
                target.setY(newValues[1]);
                break;
            case XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case ALPHA:
                target.setColor(
                        target.getColor().r,
                        target.getColor().g,
                        target.getColor().b,
                        newValues[0]
                );
                break;
            default:
                assert false;
        }
    }
}
