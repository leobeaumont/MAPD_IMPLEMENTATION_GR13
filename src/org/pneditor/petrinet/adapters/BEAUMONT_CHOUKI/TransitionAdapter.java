package org.pneditor.petrinet.adapters.BEAUMONT_CHOUKI;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Transition;

/**
 * This class adapts the specific `Transition` implementation to the generic `AbstractTransition`
 * that the UI understands. Its primary role is to hold a reference to the internal transition object.
 */
public class TransitionAdapter extends AbstractTransition {

    private final Transition internalTransition;

    public TransitionAdapter(String label, Transition internalTransition) {
        super(label);
        this.internalTransition = internalTransition;
    }

    /**
     * Allows the main PetriNetAdapter to access the underlying model's transition object.
     * @return The internal Transition object.
     */
    public Transition getInternalTransition() { return internalTransition; }
}
