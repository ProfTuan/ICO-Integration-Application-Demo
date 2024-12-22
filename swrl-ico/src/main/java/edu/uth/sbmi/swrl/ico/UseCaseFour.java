/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uth.sbmi.swrl.ico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
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
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 *
 * @author tuan
 */
public class UseCaseFour {
    
    private OWLNamedIndividual team, nih, pi, org, authorize, research, research_process, biospecimen, tousedata;
    
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    
    private static UseCaseFour INSTANCE = null;
    
    final String base = "http://purl.obolibrary.org/obo/";
    
    public synchronized static UseCaseFour getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UseCaseFour();
        }
        
        return INSTANCE;
    }
    
    public void addInformedConsentInstances(){
        //_tousedata (ICO_0000116)
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        /*tousedata = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tousedata"));
        OWLClass class_tousedata = factory.getOWLClass(IRI.create(base + "ICO_0000116"));
        OWLClassAssertionAxiom use_ax = factory.getOWLClassAssertionAxiom(class_tousedata, tousedata);
        om.addAxiom(use_ax);*/
        
        createIndividualInstance(tousedata, "_tousedata", "ICO_0000116", "activity to use the participant research data");
        
        createIndividualInstance(team, "_team", "ICO_0000396", "research team involved in informed consent");
        
        createIndividualInstance(nih, "_nih", "ICO_0000381", "nih is the designated organization");
        
        createIndividualInstance(pi, "_pi", "ICO_0000382", "primary investigator responsbile for the study");
        
        createIndividualInstance(org, "_org", "ICO_0000395", "the pi organization");
        
        authorize = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_authorize"));
        OWLClass class_authorize = factory.getOWLClass(IRI.create(base + "ICO_0000046"));
        OWLClassAssertionAxiom authorize_ax = factory.getOWLClassAssertionAxiom(class_authorize, authorize);
        om.addAxiom(authorize_ax);
        
        OWLObjectProperty is_about = om.getFactory().getOWLObjectProperty(base + "IAO_0000136");
        OWLNamedIndividual agree = om.getFactory().getOWLNamedIndividual(base + "ICO.owl#_agree");
        OWLObjectPropertyAssertionAxiom link_authorize = factory.getOWLObjectPropertyAssertionAxiom(is_about, agree, authorize);
        om.addAxiom(link_authorize);
        
        createIndividualInstance(research, "_research", "ICO_0000345", "the research purpose for which the PI and team are investigating");
        
        createIndividualInstance(research_process, "_researchprocess", "ICO_0000336", "research activities for the participant's biospecimen");
        
        createIndividualInstance(biospecimen, "_biospecimen", "ICO_0000375", "biospecimen of the participant");
        
    }
    
    private void createIndividualInstance(OWLNamedIndividual individual, String individual_id, String class_id, String label){
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        individual = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#" + individual_id));
        OWLClass owlclass = factory.getOWLClass(IRI.create(base + class_id));
        
        OWLAnnotation i_label = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(
                OWLRDFVocabulary.RDFS_LABEL.getIRI()), factory.getOWLLiteral(label, "en"));
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
    
    public List<MutableTriple<String,String,String>> whatAuthorizationIsGiven(){
        
        List<MutableTriple<String,String,String>> response = new ArrayList<MutableTriple<String,String,String>>();
        
        //authorize's inferred connection to usedata -- _authorize > is restricted to > _tousedata
        
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        OWLNamedIndividual authorize = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_authorize"));
        
        om.getOntology().objectPropertiesInSignature().forEach(p->{
            
            if(!p.isOWLTopObjectProperty()){
                OWLObjectProperty op = om.getFactory().getOWLObjectProperty(p.toStringID());
                
                Stream<OWLNamedIndividual> objectPropertyValues = om.getReasoner().objectPropertyValues(authorize, p);
                
                objectPropertyValues.forEach(oni -> {
                    
                    MutableTriple<String, String, String> triple_response =
                            MutableTriple.of(
                                    authorize.getIRI().getFragment(),
                                    util.getLabelForProperty(op),
                                    oni.getIRI().getFragment());
                    
                    response.add(triple_response);
                    
                    //System.out.println(" > " + util.getLabelForProperty(op) + " >" + oni.getIRI().getFragment());  
                });
                
            }
        
        });
        
        return response;
        
    }
    
    public List<MutableTriple<String,String,String>> whatAreDetailsofAuthorization(){
        List<MutableTriple<String,String,String>> response = new ArrayList<MutableTriple<String,String,String>>();
        
        //get _tousedata and iteriate throught its predicates and their objects
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        OWLNamedIndividual usedata = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tousedata"));
        
        om.getOntology().objectPropertiesInSignature().forEach(p -> {
            
            if(!p.isOWLTopObjectProperty()){
                OWLObjectProperty op = om.getFactory().getOWLObjectProperty(p.toStringID());
                
                Stream<OWLNamedIndividual> objectPropertyValues = om.getReasoner().objectPropertyValues(usedata, p);
                
                objectPropertyValues.forEach(oni -> {
                    
                    MutableTriple<String, String, String> triple_response =
                            MutableTriple.of(
                                    usedata.getIRI().getFragment(),
                                    util.getLabelForProperty(op),
                                    oni.getIRI().getFragment());
                    
                    response.add(triple_response);
                    
                    //System.out.println(" > " + util.getLabelForProperty(op) + " >" + oni.getIRI().getFragment());  
                });
            }
            
        });
        
        return response;
        
    }
    
    public void startReasoner(){
        OntologyManager om = OntologyManager.getInstance();
        om.rebuildReasoning();
    }
    
    private UseCaseFour(){
        
    }
}
