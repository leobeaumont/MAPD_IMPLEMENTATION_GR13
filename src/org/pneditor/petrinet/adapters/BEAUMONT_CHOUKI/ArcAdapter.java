package org.pneditor.petrinet.adapters.BEAUMONT_CHOUKI;

import java.util.Map;
import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.Edge;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeIn;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeOut;
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

    private final Edge internalEdge;
    private final Map<Object, AbstractNode> internalToAdapterNodeMap;

    public ArcAdapter(Edge internalEdge, Map<Object, AbstractNode> internalToAdapterNodeMap) {
        this.internalEdge = internalEdge;
        this.internalToAdapterNodeMap = internalToAdapterNodeMap;
    }

    @Override
    public AbstractNode getSource() {
        if (internalEdge instanceof EdgeIn) {
            return internalToAdapterNodeMap.get(((EdgeIn) internalEdge).getOrigin());
        } else { // EdgeOut
            return internalToAdapterNodeMap.get(((EdgeOut) internalEdge).getOrigin());
        }
    }

    @Override
    public AbstractNode getDestination() {
        if (internalEdge instanceof EdgeIn) {
            return internalToAdapterNodeMap.get(((EdgeIn) internalEdge).getArrival());
        } else { // EdgeOut
            return internalToAdapterNodeMap.get(((EdgeOut) internalEdge).getArrival());
        }
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
        return 1; // Inhibitory and Reset arcs have an implicit weight of 1.
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
        // For other arc types (inhibitory, reset), we do nothing as they don't have a settable weight in our model.
    }
}
