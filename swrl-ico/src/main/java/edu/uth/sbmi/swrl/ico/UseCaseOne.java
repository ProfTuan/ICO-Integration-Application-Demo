/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uth.sbmi.swrl.ico;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 *
 * @author tuan
 */
public class UseCaseOne {
    
    private OWLNamedIndividual explained;
    private OWLNamedIndividual _data, _collect, _research, _org, _pi, _nih, _team, _allofus;
    
     private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    
    private static UseCaseOne INSTANCE = null;
    
    //NodeSet<OWLNamedIndividual> response;
    
    public synchronized static UseCaseOne getInstance(){
        if (INSTANCE == null){
            INSTANCE = new UseCaseOne();
        }
        
        return INSTANCE;
    }
    
    public void startReasoner(){
        OntologyManager om = OntologyManager.getInstance();
        om.rebuildReasoning();
    }
    
    public void addInformedConsentInstances(){
        //OntologyManager om = OntologyManager.getInstance();
        //OWLDataFactory factory = om.getFactory();
        //String base = "http://purl.obolibrary.org/obo/";
        //PrefixManager prefix_manager = new DefaultPrefixManager(base);
        

        //add _explained - 'explaining to participant candidate in informed consent process' ICO_0000154
        
        createIndividualInstance(explained, "_explained", "ICO_0000154", "explained to participant");
        
        /*
        explained = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_explained"));
        OWLClass expain_pc_icp = factory.getOWLClass(IRI.create(base + "ICO_0000154"));
        
        OWLAnnotation explained_label = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral("explained to participant", "en"));
        om.addAxiomLabelorComment(explained, explained_label);
        
        OWLClassAssertionAxiom e_cax = factory.getOWLClassAssertionAxiom(expain_pc_icp, explained);
        om.addAxiom(e_cax);
        */
        
        //add team - team involved in process #ICO_0000396
        
        createIndividualInstance(_team, "_team", "ICO_0000396", "research team involved in informed consent");
        
        
        //add nih (the institution) - the institution over the informed consent and conducting the study
        
        createIndividualInstance(_nih, "_nih", "ICO_0000381", "nih is the designated organization");
        
        
        // add pi (primary invesitgator responsible for the study)
        
        createIndividualInstance(_pi, "_pi", "ICO_0000382", "primary investigator responsbile for the study");
        
        // add org (organization that the primary investigator is associated with the study)
        
        createIndividualInstance(_org, "_org", "ICO_0000395", "the pi organization");
        
        // add reserach - the research purpose
        
        createIndividualInstance(_research, "_research", "ICO_0000345", "the research purpose for which the PI and team are investigating");
        
        // add collect - collection of data
        
        createIndividualInstance(_collect, "_collect", "ICO_0000332", "the research involves collecting data");
        
        // add data - health information (specifically about the patient)
        
        createIndividualInstance(_data, "_data", "ICO_0000370", "health information of the participants");
        
        createIndividualInstance(_allofus, "_allofus", "ICO_0000395", "All of US research entity");
        
       
        
    }
    
    private void createIndividualInstance(OWLNamedIndividual individual, String identifier, String class_id, String label){
        
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        String base = "http://purl.obolibrary.org/obo/";
        
        individual = factory.getOWLNamedIndividual(IRI.create(base +"ICO.owl#" + identifier));
        OWLClass owlclass = factory.getOWLClass(IRI.create(base + class_id));
        
        OWLAnnotation i_label = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral(label, "en"));
        om.addAxiomLabelorComment(individual, i_label);
        
        OWLClassAssertionAxiom i_cax = factory.getOWLClassAssertionAxiom(owlclass, individual);
        om.addAxiom(i_cax);
        
        
    }
    
    public void insertSubjectConsent(){
        
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        String base = "http://purl.obolibrary.org/obo/";
        PrefixManager prefix_manager = new DefaultPrefixManager(base);
        
        //add http://purl.obolibrary.org/obo/ICO.owl#_I (http://purl.obolibrary.org/obo/ICO_0000398)
        OWLIndividual I = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_I"));
        OWLClass I_class = factory.getOWLClass(IRI.create(base + "ICO_0000398"));
        
        OWLClassAssertionAxiom i_cax = factory.getOWLClassAssertionAxiom(I_class, I);
        OWLLiteral literal = factory.getOWLLiteral("subject has given consent");
        OWLAnnotation owlAnnotation = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()), literal);
        
        //om.addAxiomLabel(I, owlAnnotation);
        om.addAxiom(i_cax, owlAnnotation);
        
    }
    
    public List<MutablePair<String,String>> whoExplainedConsentProcess(){
        List<MutablePair<String,String>> response = new ArrayList<MutablePair<String,String>>();
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        //explained = om.getFactory().getOWLNamedIndividual("http://purl.obolibrary.org/obo/ICO.owl#_explained");
        
        OWLObjectProperty hasParticipant = om.getFactory().getOWLObjectProperty("http://purl.obolibrary.org/obo/RO_0000057");
        
        NodeSet<OWLNamedIndividual> objectPropertyValues = om.getReasoner().getObjectPropertyValues(explained, hasParticipant);
        
        for(Node<OWLNamedIndividual> o: objectPropertyValues){
            o.getEntitiesMinus(explained).forEach(b->{
                //System.out.println("INFERRED instance data: " + renderer.render(b));
                MutablePair<String, String> instance_pair = new MutablePair<String,String>();
                instance_pair.setLeft(renderer.render(b));
                instance_pair.setRight(util.getLabelForIndividual(b));
                response.add(instance_pair);
            });
        }
        
        return response;
    }
    
    public List<MutablePair<String, String>> didParticipantGiveLegalConsent(){
        
        List<MutablePair<String,String>> response = new ArrayList<MutablePair<String,String>>();
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        
        OWLNamedIndividual participant = om.getFactory().getOWLNamedIndividual("http://purl.obolibrary.org/obo/ICO.owl#_I");
        OWLObjectProperty participatesIn = om.getFactory().getOWLObjectProperty("http://purl.obolibrary.org/obo/RO_0000056");
        
        NodeSet<OWLNamedIndividual> reason_response = om.getReasoner().getObjectPropertyValues(participant, 
                participatesIn);
        
        for(Node<OWLNamedIndividual> o: reason_response){
            o.getEntitiesMinus(participant).forEach(b->{
                //System.out.println("INFERRED instance data:  " + renderer.render(b));
                MutablePair<String, String> instance_pair = new MutablePair<String,String>();
                instance_pair.setLeft(renderer.render(b));
                instance_pair.setRight(util.getLabelForIndividual(b));
                response.add(instance_pair);
            });
        }
        
        /*System.out.println("Did participant give legal consent?");
        for(MutablePair<String,String> mp : response){
            System.out.println(mp.getLeft() + " - " + mp.getRight());
        }*/
        
        
        return response;
    }
    
    public List<MutablePair<String, String>> whatWasExplained(){
        
        List<MutablePair<String, String>> explainations = new ArrayList<MutablePair<String, String>>();
        
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        explained = om.getFactory().getOWLNamedIndividual("http://purl.obolibrary.org/obo/ICO.owl#_explained");
        
        Set<Object> assertedClasses = EntitySearcher.getTypes(explained, om.getOntology()).collect(Collectors.toSet());
        
        for(Object o: assertedClasses){
            MutablePair<String, String> class_pair = new MutablePair<String,String>();
            class_pair.setLeft(
                    ((OWLClass)o).asOWLClass().getIRI().getFragment()
            );
            class_pair.setRight(util.getLabelForClass((OWLClass)o));
            explainations.add(class_pair);
        }
        
        for(OWLClass exTypes : om.getReasoner().getTypes(explained, true).getFlattened()){
            boolean asserted = assertedClasses.contains(exTypes);
            
            MutablePair<String,String> class_pair = new MutablePair<String, String>();
            class_pair.setLeft(renderer.render(exTypes));
            class_pair.setRight(util.getLabelForClass(exTypes));
            explainations.add(class_pair);
            
        }
        
        /*
        System.out.println("What was explained?");
        for(MutablePair<String,String> mp : explainations){
            System.out.println(mp.getLeft() + " - " + mp.getRight());
        }
        */
        return explainations;
        
    }
    
    private UseCaseOne(){
        
    }
    
}
