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

    // A reference to the core Petri Net.
    private final PetriNet1 petriNet;

    // Maps to keep track of the relationship between the UI's adapter objects and internal model objects.
    private final Map<AbstractNode, Object> adapterToInternalNode = new HashMap<>();
    private final Map<Object, AbstractNode> internalToAdapterNode = new HashMap<>();
    private final Map<AbstractArc, Edge> adapterToInternalEdge = new HashMap<>();

    public PetriNetAdapter() {
        super();
        this.petriNet = new PetriNet1();
    }

    @Override
    public AbstractPlace addPlace() {
        // Create a place.
        Place internalPlace = new Place(0);
        petriNet.getPlaces().add(internalPlace);

        // Create an adapter for it.
        PlaceAdapter placeAdapter = new PlaceAdapter("", internalPlace);

        // Map them together
        adapterToInternalNode.put(placeAdapter, internalPlace);
        internalToAdapterNode.put(internalPlace, placeAdapter);

        // Return the adapter
        return placeAdapter;
    }

    @Override
    public AbstractTransition addTransition() {
        // Create a transition
        Transition internalTransition = new Transition();
        petriNet.getTransitions().add(internalTransition);

        // Create an adapter
        TransitionAdapter transitionAdapter = new TransitionAdapter("", internalTransition);

        // Map them together
        adapterToInternalNode.put(transitionAdapter, internalTransition);
        internalToAdapterNode.put(internalTransition, transitionAdapter);

        // Return the adapter
        return transitionAdapter;
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
        // Get internal nodes.
        Object internalSource = adapterToInternalNode.get(source);
        Object internalDestination = adapterToInternalNode.get(destination);

        if (internalSource instanceof Place && internalDestination instanceof Transition) {
            // Create internal edge.
            WeightedEdgeIn internalEdge = new WeightedEdgeIn((Place) internalSource, (Transition) internalDestination, 1);
            petriNet.getEdges().add(internalEdge);

            // Create the adapter.
            ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);

            // Map them together.
            adapterToInternalEdge.put(arcAdapter, internalEdge);

            // Return the adapter.
            return arcAdapter;
        } else if (internalSource instanceof Transition && internalDestination instanceof Place) {
            // Create internal edge.
            WeightedEdgeOut internalEdge = new WeightedEdgeOut((Transition) internalSource, (Place) internalDestination, 1);
            petriNet.getEdges().add(internalEdge);

            // Create the adapter.
            ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);

            // Map them together.
            adapterToInternalEdge.put(arcAdapter, internalEdge);

            // Return the adapter.
            return arcAdapter;
        }
        throw new UnimplementedCaseException("Regular arc must be between a place and a transition.");
    }

    @Override
    public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition) {
        // Get internal place and transition.
        Place internalPlace = (Place) adapterToInternalNode.get(place);
        Transition internalTransition = (Transition) adapterToInternalNode.get(transition);

        // Create internal edge.
        EdgeZero internalEdge = new EdgeZero(internalPlace, internalTransition);

        // Create an adapter.
        ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);

        // Map them together.
        adapterToInternalEdge.put(arcAdapter, internalEdge);

        // Return the adapter.
        return arcAdapter;
    }

    @Override
    public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition) {
        // Get internal place and transition.
        Place internalPlace = (Place) adapterToInternalNode.get(place);
        Transition internalTransition = (Transition) adapterToInternalNode.get(transition);

        // Create internal edge.
        EdgeEmpty internalEdge = new EdgeEmpty(internalPlace, internalTransition);

        // Create an adapter.
        ArcAdapter arcAdapter = new ArcAdapter(internalEdge, internalToAdapterNode);

        // Map them together.
        adapterToInternalEdge.put(arcAdapter, internalEdge);

        // Return the adapter.
        return arcAdapter;
    }

    @Override
    public void removePlace(AbstractPlace place) {
        // Get internal place.
        Place internalPlace = (Place) adapterToInternalNode.get(place);

        if (internalPlace != null) {
            // Remove all connected arcs.
            for (AbstractArc arc : adapterToInternalEdge.keySet().toArray(new AbstractArc[0])) {
                if (arc.getSource().equals(place) || arc.getDestination().equals(place)) {
                    removeArc(arc);
                }
            }

            // Remove place from the internal model.
            petriNet.removePlace(internalPlace);

            // Clean up the adapter's maps.
            adapterToInternalNode.remove(place);
            internalToAdapterNode.remove(internalPlace);
        }
    }

    @Override
    public void removeTransition(AbstractTransition transition) {
        // Get internal transition.
        Transition internalTransition = (Transition) adapterToInternalNode.get(transition);

        if (internalTransition != null) {
            // Remove all connected arcs.
            for (AbstractArc arc : adapterToInternalEdge.keySet().toArray(new AbstractArc[0])) {
                if (arc.getSource().equals(transition) || arc.getDestination().equals(transition)) {
                    removeArc(arc);
                }
            }

            // Remove transition from internal model.
            petriNet.removeTransition(internalTransition);

            // Clear mappings
            adapterToInternalNode.remove(transition);
            internalToAdapterNode.remove(internalTransition);
        }
    }

    @Override
    public void removeArc(AbstractArc arc) {
        // Get internal edge.
        Edge internalEdge = adapterToInternalEdge.get(arc);

        if (internalEdge != null) {
            // Remove edge from internal model.
            petriNet.removeEdge(internalEdge);

            // Clean up the adapter's map.
            adapterToInternalEdge.remove(arc);
        }
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
        // Get internal transition.
        Transition internalTransition = (Transition) adapterToInternalNode.get(transition);

        if (internalTransition != null) {
            // Call to internal logic.
            return internalTransition.isDrawable();
        }
        return false; // If the transition doesn't exist, it can't be enabled.
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
        // Get internal transition.
        Transition internalTransition = (Transition) adapterToInternalNode.get(transition);

        if (internalTransition != null) {
            // Call to internal logic.
            petriNet.stepSimulation(internalTransition);
        }
    }
}
