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
import org.apache.commons.lang3.tuple.MutableTriple;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 *
 * @author tuan
 */
public class UseCaseTwo {
    
    
    private OWLNamedIndividual to_follow_med, to_communicate, team, nih, org, pi, allofus, disclosure, therapy, phi, adjust_care;
    
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    
    private static UseCaseTwo INSTANCE = null;
    
    final String base = "http://purl.obolibrary.org/obo/";
    
    public synchronized static UseCaseTwo getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UseCaseTwo();
        }
        
        return INSTANCE;
    }
    
    public void addInformedConsentInstances(){
        //sample
        //_tofollowmed (ICO_0000110)
        
        /*OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        to_follow_med = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tofollowmed"));
        OWLClass class_tofollowmed = factory.getOWLClass(IRI.create(base + "ICO_0000110"));
        
        OWLClassAssertionAxiom i_c = factory.getOWLClassAssertionAxiom(class_tofollowmed, to_follow_med);
        om.addAxiom(i_c);*/
        
        createIndividualInstance(to_follow_med, "_tofollowmed", "ICO_0000110", "participant agrees to seek out medical treatment");
        
        createIndividualInstance(to_communicate, "_tocommunicate", "ICO_0000269", "information communicated");
        
        createIndividualInstance(team, "_team", "ICO_0000396", "research team involved in informed consent");
        
        createIndividualInstance(nih, "_nih", "ICO_0000381", "nih is the designated organization");
        
        createIndividualInstance(org, "_org", "ICO_0000395", "the pi organization");
        
        createIndividualInstance(pi, "_pi", "ICO_0000382", "primary investigator responsbile for the study");
        
        createIndividualInstance(allofus, "_allofus", "ICO_0000395", "All of US research entity");
        
        //designated process of disclsoure ICO_0000334
        createIndividualInstance(disclosure, "_disclosure", "ICO_0000334", "disclosing to the patient");
        
        createIndividualInstance(therapy, "_therapy", "ICO_0000354", "the treatment for the patient");
        
        createIndividualInstance(phi, "_phi", "ICO_0000370", "personal health information of the patient");
        
        createIndividualInstance(adjust_care, "_adjustcare", "ICO_0000365", "directive to seek care");
        
    }
    
    private void createIndividualInstance(OWLNamedIndividual individual, String individual_id, String class_id, String label){
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        individual = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#" + individual_id));
        OWLClass owlclass = factory.getOWLClass(IRI.create(base + class_id));
        
        OWLAnnotation i_label = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral(label, "en"));
        om.addAxiomLabelorComment(individual, i_label);
        
        OWLClassAssertionAxiom i_cax = factory.getOWLClassAssertionAxiom(owlclass, individual);
        om.addAxiom(i_cax);
    }
    
    public void insertSubjectConsent(){
//link agreement to consenter's variable/instance        
// _agree > is about (IAO_0000136) > I (ICO_0000398)
        
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        OWLNamedIndividual I = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_I"));
        OWLClass class_I = factory.getOWLClass(IRI.create(base + "ICO_0000398"));
        
        OWLClassAssertionAxiom i_ax = factory.getOWLClassAssertionAxiom(class_I, I);
        OWLLiteral literal = factory.getOWLLiteral("subject has given consent");
        OWLAnnotation owlAnnotation = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(
                OWLRDFVocabulary.RDFS_LABEL.getIRI()), literal);
        
        om.addAxiom(i_ax, owlAnnotation);
        
        OWLObjectProperty is_about = om.getFactory().getOWLObjectProperty(base + "IAO_0000136");
        OWLNamedIndividual agree = om.getFactory().getOWLNamedIndividual(base + "ICO.owl#_agree");
        OWLObjectPropertyAssertionAxiom op = factory.getOWLObjectPropertyAssertionAxiom(is_about, agree, I);
        
        om.addAxiom(op);
        
    }
    
    
    public void startReasoner(){
        OntologyManager om = OntologyManager.getInstance();
        om.rebuildReasoning();
    }
    
    public  List<MutablePair<String,String>> whatIsSubjectResponsibilities(){
        List<MutablePair<String,String>> response = new ArrayList<MutablePair<String,String>>();
        
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        
        OWLNamedIndividual I = om.getFactory().getOWLNamedIndividual("http://purl.obolibrary.org/obo/ICO.owl#_I");
        OWLObjectProperty particpantsIn = om.getFactory().getOWLObjectProperty("http://purl.obolibrary.org/obo/RO_0000056");
        
        NodeSet<OWLNamedIndividual> answers = om.getReasoner().getObjectPropertyValues(I, particpantsIn);
        
        for(Node<OWLNamedIndividual> o: answers){
            o.getEntitiesMinus(I).forEach(b->{
                
                Set<Object> assertedClasses = EntitySearcher.getTypes(b, om.getOntology()).collect(Collectors.toSet());
                
                StringBuilder sb = new StringBuilder();
                
                assertedClasses.forEach(cl->{
                    sb.append(" " +util.getLabelForClass((OWLClass)cl));
                });
                
                MutablePair<String,String> class_pair = new MutablePair<String, String>();
                class_pair.setLeft(renderer.render(b));
                class_pair.setRight(sb.toString());
                response.add(class_pair);
                
                
                
            });
        }
        
        return response;
    }
    
    public List<MutableTriple<String,String,String>> whoInformedSubjectAndTheirRoles(){
        
        List<MutableTriple<String,String,String>> response = new ArrayList<MutableTriple<String,String,String>>();
        
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        OWLObjectProperty hasParticipant =om.getFactory().getOWLObjectProperty("http://purl.obolibrary.org/obo/RO_0000057");
        OWLNamedIndividual toInform = om.getFactory().getOWLNamedIndividual("http://purl.obolibrary.org/obo/ICO.owl#_toinform");
        
        NodeSet<OWLNamedIndividual> reason_data = om.getReasoner().getObjectPropertyValues(toInform, hasParticipant);
        
        for(Node<OWLNamedIndividual> o: reason_data){
            o.getEntitiesMinus(toInform).forEach(b->{
                Set<Object> assertedClasses = EntitySearcher.getTypes(b, om.getOntology()).collect(Collectors.toSet());
                
                //MutableTriple<String,String,String> t_response = new MutableTriple<String,String,String>();
                
                //get iri fragment and label
                //System.out.println("INFERRED instance data: " + renderer.render(b) + " - " + util.getLabelForIndividual(b) );
                
                StringBuilder sb = new StringBuilder();
                assertedClasses.forEach(cl->{
                    sb.append(util.getLabelForClass((OWLClass)cl));
                    //System.out.println("\t The role is "  + util.getLabelForClass((OWLClass)cl));
                });
                //System.out.println("--------");
                
                MutableTriple<String,String,String> triple_response = MutableTriple.of(renderer.render(b), util.getLabelForIndividual(b), sb.toString());
                
                response.add(triple_response);
                
            });
        }
        
        
        return response;
        
    }
    
    private UseCaseTwo(){
        
    }
    
}
