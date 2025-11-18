package org.pneditor.petrinet.models.BEAUMONT_CHOUKI.adapters;

import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Node;

/**
 * Adapter class that bridges the BEAUMONT_CHOUKI Node model
 * with the PNEditor AbstractNode API.
 *
 * <p>This adapter follows the Adapter design pattern by extending
 * the PNEditor AbstractNode (the Target interface) while internally
 * delegating to a Node instance from the custom model (the Adaptee).</p>
 *
 * <p>Concrete subclasses such as {@code PlaceAdapter} and
 * {@code TransitionAdapter} must implement {@link #isPlace()} to
 * indicate their specific type.</p>
 */
public abstract class NodeAdapter extends AbstractNode {

    /**
     * The underlying BEAUMONT_CHOUKI model node being adapted.
     */
    protected final Node adaptee;

    /**
     * Creates a new adapter for a Node.
     *
     * @param adaptee The node from the custom model to adapt.
     * @param label   The label to be used by the PNEditor AbstractNode.
     */
    protected NodeAdapter(final Node adaptee, final String label) {
        super(label);
        this.adaptee = adaptee;
    }

    /**
     * Returns the underlying adapted node from the BEAUMONT_CHOUKI model.
     *
     * @return The adaptee node.
     */
    public Node getAdaptee() {
        return adaptee;
    }

    /**
     * Must be implemented by subclasses to indicate if this node
     * represents a Place or a Transition in the PNEditor environment.
     *
     * @return {@code true} if this is a Place, {@code false} otherwise.
     */
    @Override
    public abstract boolean isPlace();
}

