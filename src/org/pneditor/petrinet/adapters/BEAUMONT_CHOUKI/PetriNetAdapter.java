package org.pneditor.petrinet.adapters.BEAUMONT_CHOUKI;

import java.util.HashMap;
import java.util.Map;
import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.PetriNET.PetriNet1;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Place;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Transition;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.Edge;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeEmpty;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.EdgeZero;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.WeightedEdgeIn;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges.WeightedEdgeOut;

/**
 * This class is the central adapter. It bridges the gap between the generic UI
 * (the "client") and the specific Petri Net implementation (the "adaptee").
 * It translates calls from the UI's `PetriNetInterface` into calls to the `PetriNet1` class.
 */
public class PetriNetAdapter extends PetriNetInterface {

    // A reference to your core Petri Net logic.
    private final PetriNet1 petriNet;

    // Maps to keep track of the relationship between the UI's adapter objects
    // and your internal model objects. This is crucial for translating calls.
    private final Map<AbstractNode, Object> adapterToInternalNode = new HashMap<>();
    private final Map<Object, AbstractNode> internalToAdapterNode = new HashMap<>();
    private final Map<AbstractArc, Edge> adapterToInternalEdge = new HashMap<>();

    public PetriNetAdapter() {
        super();
        this.petriNet = new PetriNet1();
    }

    @Override
    public AbstractPlace addPlace() {
        // 1. Call your logic to create a place.
        Place internalPlace = new Place(0); // Assuming new places start with 0 tokens.
        petriNet.getPlaces().add(internalPlace);

        // 2. Create a UI-compatible adapter for it.
        PlaceAdapter placeAdapter = new PlaceAdapter("", internalPlace);

        // 3. Store the mapping.
        adapterToInternalNode.put(placeAdapter, internalPlace);
        internalToAdapterNode.put(internalPlace, placeAdapter);

        // 4. Return the adapter to the UI.
        return placeAdapter;
    }

    @Override
    public AbstractTransition addTransition() {
        // Same pattern as addPlace...
        Transition internalTransition = new Transition();
        petriNet.getTransitions().add(internalTransition);
        TransitionAdapter transitionAdapter = new TransitionAdapter("", internalTransition);
        adapterToInternalNode.put(transitionAdapter, internalTransition);
        internalToAdapterNode.put(internalTransition, transitionAdapter);
        return transitionAdapter;
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
        // 1. Use the map to look up the internal objects.
        Object internalSource = adapterToInternalNode.get(source);
        Object internalDestination = adapterToInternalNode.get(destination);

        // 2. Create the correct internal edge based on the types.
        if (internalSource instanceof Place && internalDestination instanceof Transition) {
            WeightedEdgeIn internalEdge = new WeightedEdgeIn((Place) internalSource, (Transition) internalDestination, 1);
            petriNet.getEdges().add(internalEdge);

            // 3. Create the adapter for the arc and populate the edge map.
            ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);
            adapterToInternalEdge.put(arcAdapter, internalEdge);
            return arcAdapter;
        } else if (internalSource instanceof Transition && internalDestination instanceof Place) {
            WeightedEdgeOut internalEdge = new WeightedEdgeOut((Transition) internalSource, (Place) internalDestination, 1);
            petriNet.getEdges().add(internalEdge);

            ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);
            adapterToInternalEdge.put(arcAdapter, internalEdge);
            return arcAdapter;
        }
        throw new UnimplementedCaseException("Regular arc must be between a place and a transition.");
    }

    @Override
    public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
        // We will implement this later.
        throw new UnimplementedCaseException("addInhibitoryArc is not implemented yet.");
    }

    @Override
    public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
        // We will implement this later.
        throw new UnimplementedCaseException("addResetArc is not implemented yet.");
    }

    @Override
    public void removePlace(AbstractPlace place) {
        // We will implement this later.
    }

    @Override
    public void removeTransition(AbstractTransition transition) {
        // We will implement this later.
    }

    @Override
    public void removeArc(AbstractArc arc) {
        // We will implement this later.
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
        // We will implement this later.
        return false;
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
        // We will implement this later.
    }
}
