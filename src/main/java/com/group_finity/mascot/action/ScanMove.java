package com.group_finity.mascot.action;

import com.group_finity.mascot.VShimejiApplication;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.animation.Animation;
import com.group_finity.mascot.exception.BehaviorInstantiationException;
import com.group_finity.mascot.exception.CantBeAliveException;
import com.group_finity.mascot.exception.LostGroundException;
import com.group_finity.mascot.exception.VariableException;
import com.group_finity.mascot.script.VariableMap;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kilkakon
 * @since 1.0.14
 */
public class ScanMove extends BorderedAction {
    private static final Logger log = Logger.getLogger(ScanMove.class.getName());

    public static final String PARAMETER_AFFORDANCE = "Affordance";

    private static final String DEFAULT_AFFORDANCE = "";

    public static final String PARAMETER_BEHAVIOUR = "Behaviour";

    private static final String DEFAULT_BEHAVIOUR = "";

    public static final String PARAMETER_TARGETBEHAVIOUR = "TargetBehaviour";

    private static final String DEFAULT_TARGETBEHAVIOUR = "";

    private WeakReference<Mascot> target;

    public ScanMove(ResourceBundle schema, final List<Animation> animations, final VariableMap context) {
        super(schema, animations, context);
    }

    @Override
    public boolean hasNext() throws VariableException {
        if (getMascot().getManager() == null) {
            return super.hasNext();
        }

        if (target == null) {
            target = getMascot().getManager().getMascotWithAffordance(getAffordance());
        }

        return super.hasNext() && target != null && target.get() != null && target.get().getAffordances().contains(getAffordance());
    }

    @Override
    protected void tick() throws LostGroundException, VariableException {
        super.tick();

        if (getBorder() != null && !getBorder().isOn(getMascot().getAnchor())) {
            log.log(Level.INFO, "Lost ground ({0}, {1})", new Object[]{getMascot(), this});
            throw new LostGroundException();
        }

        int targetX = target.get().getAnchor().x;
        int targetY = target.get().getAnchor().y;

        if (getMascot().getAnchor().x != targetX) {
            getMascot().setLookRight(getMascot().getAnchor().x < targetX);
        }
        boolean down = getMascot().getAnchor().y < targetY;

        getAnimation().next(getMascot(), getTime());

        if (getMascot().isLookRight() && getMascot().getAnchor().x >= targetX ||
                !getMascot().isLookRight() && getMascot().getAnchor().x <= targetX) {
            getMascot().setAnchor(new Point(targetX, getMascot().getAnchor().y));
        }
        if (down && getMascot().getAnchor().y >= targetY ||
                !down && getMascot().getAnchor().y <= targetY) {
            getMascot().setAnchor(new Point(getMascot().getAnchor().x, targetY));
        }

        boolean noMoveX = false;
        boolean noMoveY = false;

        if (getMascot().getAnchor().x == targetX) {
            noMoveX = true;
        }

        if (getMascot().getAnchor().y == targetY) {
            noMoveY = true;
        }

        if (noMoveX && noMoveY) {
            boolean setFirstBehavior = false;
            try {
                getMascot().setBehavior(VShimejiApplication.getInstance().getConfiguration(getMascot().getImageSet()).buildBehavior(getBehavior()));
                setFirstBehavior = true;
                Mascot targetMascot = target.get();
                if (targetMascot != null) {
                    targetMascot.setBehavior(VShimejiApplication.getInstance().getConfiguration(targetMascot.getImageSet()).buildBehavior(getTargetBehavior()));
                }
            } catch (final BehaviorInstantiationException | CantBeAliveException e) {
                log.log(Level.SEVERE, "Failed to set behavior to \"" + (setFirstBehavior ? getTargetBehavior() : getBehavior()) + "\" for mascot \"" + (setFirstBehavior ? target.get() : getMascot()) + "\"", e);
                VShimejiApplication.showError(VShimejiApplication.getInstance().getLanguageBundle().getString("FailedSetBehaviourErrorMessage") + "\n" + e.getMessage() + "\n" + VShimejiApplication.getInstance().getLanguageBundle().getString("SeeLogForDetails"));
            }
        }
    }

    private String getAffordance() throws VariableException {
        return eval(getSchema().getString(PARAMETER_AFFORDANCE), String.class, DEFAULT_AFFORDANCE);
    }

    private String getBehavior() throws VariableException {
        return eval(getSchema().getString(PARAMETER_BEHAVIOUR), String.class, DEFAULT_BEHAVIOUR);
    }

    private String getTargetBehavior() throws VariableException {
        return eval(getSchema().getString(PARAMETER_TARGETBEHAVIOUR), String.class, DEFAULT_TARGETBEHAVIOUR);
    }
}
