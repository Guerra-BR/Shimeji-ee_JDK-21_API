package com.group_finity.mascot.action;

import com.group_finity.mascot.VShimejiApplication;
import com.group_finity.mascot.animation.Animation;
import com.group_finity.mascot.exception.BehaviorInstantiationException;
import com.group_finity.mascot.exception.CantBeAliveException;
import com.group_finity.mascot.exception.LostGroundException;
import com.group_finity.mascot.exception.VariableException;
import com.group_finity.mascot.script.VariableMap;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kilkakon
 * @since 1.0.12
 */
public class Transform extends Animate {
    private static final Logger log = Logger.getLogger(Transform.class.getName());

    public static final String PARAMETER_TRANSFORMBEHAVIOUR = "TransformBehaviour";

    private static final String DEFAULT_TRANSFORMBEHAVIOUR = "";

    public static final String PARAMETER_TRANSFORMMASCOT = "TransformMascot";

    private static final String DEFAULT_TRANSFORMMASCOT = "";

    public Transform(ResourceBundle schema, final List<Animation> animations, final VariableMap context) {
        super(schema, animations, context);
    }

    @Override
    protected void tick() throws LostGroundException, VariableException {
        super.tick();

        if (getTime() == getAnimation().getDuration() - 1 && Boolean.parseBoolean(VShimejiApplication.getInstance().getProperties().getProperty("Transformation", "true"))) {
            transform();
        }
    }

    private void transform() throws VariableException {
        String childType = VShimejiApplication.getInstance().getConfiguration(getTransformMascot()) != null ? getTransformMascot() : getMascot().getImageSet();

        try {
            getMascot().setImageSet(childType);
            getMascot().setBehavior(VShimejiApplication.getInstance().getConfiguration(childType).buildBehavior(getTransformBehavior()));
        } catch (final BehaviorInstantiationException | CantBeAliveException e) {
            log.log(Level.SEVERE, "Failed to set behavior to \"" + getTransformBehavior() + "\" for mascot \"" + getMascot() + "\"", e);
            VShimejiApplication.showError(VShimejiApplication.getInstance().getLanguageBundle().getString("FailedSetBehaviourErrorMessage") + "\n" + e.getMessage() + "\n" + VShimejiApplication.getInstance().getLanguageBundle().getString("SeeLogForDetails"));
        }
    }

    private String getTransformBehavior() throws VariableException {
        return eval(getSchema().getString(PARAMETER_TRANSFORMBEHAVIOUR), String.class, DEFAULT_TRANSFORMBEHAVIOUR);
    }

    private String getTransformMascot() throws VariableException {
        return eval(getSchema().getString(PARAMETER_TRANSFORMMASCOT), String.class, DEFAULT_TRANSFORMMASCOT);
    }
}
