package org.pneditor.petrinet.adapters.BEAUMONT_CHOUKI;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.Edge;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeEmpty;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeZero;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.WeightedEdgeIn;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.WeightedEdgeOut;

/**
 * This class adapts the specific `Edge` implementations to the generic `AbstractArc`
 * that the UI understands. It holds a reference to the internal edge and translates
 * the UI's requests into calls on that object.
 */
public class ArcAdapter extends AbstractArc {

    private final AbstractNode source;
    private final AbstractNode destination;
    private final Edge internalEdge;

    public ArcAdapter(AbstractNode source, AbstractNode destination, Edge internalEdge) {
        this.source = source;
        this.destination = destination;
        this.internalEdge = internalEdge;
    }

    @Override
    public AbstractNode getSource() {
        return this.source;
    }

    @Override
    public AbstractNode getDestination() {
        return this.destination;
    }

    @Override
    public boolean isReset() {
        return this.internalEdge instanceof EdgeEmpty;
    }

    @Override
    public boolean isRegular() {
        return this.internalEdge instanceof WeightedEdgeIn || this.internalEdge instanceof WeightedEdgeOut;
    }

    @Override
    public boolean isInhibitory() {
        return this.internalEdge instanceof EdgeZero;
    }

    @Override
    public int getMultiplicity() throws ResetArcMultiplicityException {
        if (isReset()) {
            throw new ResetArcMultiplicityException();
        }
        if (this.internalEdge instanceof WeightedEdgeIn) {
            return ((WeightedEdgeIn) this.internalEdge).getWeight();
        }
        if (this.internalEdge instanceof WeightedEdgeOut) {
            return ((WeightedEdgeOut) this.internalEdge).getWeight();
        }
        if (this.internalEdge instanceof EdgeEmpty) {
            return 0; // In our implementation, inhibitory edges do not draw tokens from places.
        }
        return 1; // Other arcs have a weight of 1 by default.
    }

    @Override
    public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
        if (isReset()) {
            throw new ResetArcMultiplicityException();
        }
        if (this.internalEdge instanceof WeightedEdgeIn) {
            ((WeightedEdgeIn) this.internalEdge).setWeight(multiplicity);
        } else if (this.internalEdge instanceof WeightedEdgeOut) {
            ((WeightedEdgeOut) this.internalEdge).setWeight(multiplicity);
        }
        // For inhibitory arc, we do nothing as they don't have a settable weight in our model.
    }
}
