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
public class UseCaseThree {
    
    private OWLNamedIndividual team, nih, pi, org, research, biospecimen, research_process, tosharedata, tostoredata, authorize;

    //what was authorition restricted to? storage of data
    //what are the designated perimitted actors restricted to? storing urine, blood, saliva
    private static UseCaseThree INSTANCE = null;
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    final String base = "http://purl.obolibrary.org/obo/";

    public synchronized static UseCaseThree getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseThree();
        }

        return INSTANCE;
    }

    public void startReasoner() {
        OntologyManager om = OntologyManager.getInstance();
        om.rebuildReasoning();
    }

    public void addInformedConsentInstances() {

        //_tosharedata (ICO_0000228)
        //_tostoredata (ICO_0000060)
        //_authorize (ICO_0000046)
        //_agree > is about (IAO_0000136) > _authorize
        
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();

        /*tosharedata = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tosharedata"));
        OWLClass class_tosharedata = factory.getOWLClass(IRI.create(base + "ICO_0000228"));
        OWLClassAssertionAxiom share_ax = factory.getOWLClassAssertionAxiom(class_tosharedata, tosharedata);
        om.addAxiom(share_ax);
        //add label to instance for readability
        OWLLiteral literal_share = factory.getOWLLiteral("sharing data");
        OWLAnnotation owlAnnotationShare = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(
                OWLRDFVocabulary.RDFS_LABEL.getIRI()), literal_share);
        om.addAxiomLabelorComment(tosharedata, owlAnnotationShare);*/
        
        createIndividualInstance(tosharedata, "_tosharedata", "ICO_0000228", "sharing data");
        
        /*tostoredata = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tostoredata"));
        OWLClass class_tostoredata = factory.getOWLClass(IRI.create(base + "ICO_0000060"));
        OWLClassAssertionAxiom store_ax = factory.getOWLClassAssertionAxiom(class_tostoredata, tostoredata);
        om.addAxiom(store_ax);
        //add label to instance for readability
        OWLLiteral literal_store = factory.getOWLLiteral("storing data");
        OWLAnnotation owlAnnotationStore = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(
                OWLRDFVocabulary.RDFS_LABEL.getIRI()), literal_store);
        om.addAxiomLabelorComment(tostoredata, owlAnnotationStore);*/
        
        createIndividualInstance(tostoredata, "_tostoredata", "ICO_0000060", "storing data");

        authorize = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_authorize"));
        OWLClass class_authorize = factory.getOWLClass(IRI.create(base + "ICO_0000046"));
        OWLClassAssertionAxiom authorize_ax = factory.getOWLClassAssertionAxiom(class_authorize, authorize);
        om.addAxiom(authorize_ax);
        
        OWLObjectProperty is_about = om.getFactory().getOWLObjectProperty(base + "IAO_0000136");
        OWLNamedIndividual agree = om.getFactory().getOWLNamedIndividual(base + "ICO.owl#_agree");
        OWLObjectPropertyAssertionAxiom link_authorize = factory.getOWLObjectPropertyAssertionAxiom(is_about, agree, authorize);
        om.addAxiom(link_authorize);
        
        createIndividualInstance(team, "_team", "ICO_0000396", "research team involved in informed consent");
        
        createIndividualInstance(nih, "_nih", "ICO_0000381", "nih is the designated organization");
        
        createIndividualInstance(pi, "_pi", "ICO_0000382", "primary investigator responsbile for the study");
        
        createIndividualInstance(org, "_org", "ICO_0000395", "the pi organization");
        
        createIndividualInstance(research, "_research", "ICO_0000345", "the research purpose for which the PI and team are investigating");
        
        createIndividualInstance(biospecimen, "_biospecimen", "ICO_0000375", "biospecimen of the participant");
        
        createIndividualInstance(research_process, "_researchprocess", "ICO_0000339", "research activities for the participant's biospecimen");
        
        createIndividualInstance(research_process, "_researchprocess", "ICO_0000330", "research activities for the participant's biospecimen");
        
        createIndividualInstance(research_process, "_researchprocess", "ICO_0000336", "research activities for the participant's biospecimen");

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

    public void insertSubjectConsent() {

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

    public List<MutableTriple<String,String,String>> whatIsTheAuthorizationFor() {
        
        List<MutableTriple<String, String, String>> response = new ArrayList<MutableTriple<String, String, String>>();
        
        //get authorize instance and iteriate through its predicates and their objects
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();

        OWLNamedIndividual authorize = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_authorize"));

        //om.getOWLManager().get
        om.getOntology().objectPropertiesInSignature().forEach(p -> {

            if (!p.isOWLTopObjectProperty()) {
                
                OWLObjectProperty op = om.getFactory().getOWLObjectProperty(p.toStringID());
                
                Stream<OWLNamedIndividual> objectPropertyValues = om.getReasoner().objectPropertyValues(authorize, p);

                objectPropertyValues.forEach(oni -> {
                    
                    MutableTriple<String,String,String> triple_response = MutableTriple.of(
                            authorize.getIRI().getFragment(),
                            util.getLabelForProperty(op),
                            util.getLabelForIndividual(oni));
                    
                    response.add(triple_response);
                    
                    //System.out.println(" > " + util.getLabelForProperty(op) + " >" + util.getLabelForIndividual(oni));
                    
                    
                });
            }

        });
        
        
        return response;
    }

    public List<MutableTriple<String,String,String>> whatAreTheyRestrictedFor() {
        
        List<MutableTriple<String,String,String>> response = new ArrayList<MutableTriple<String,String,String>>();
        
        //get _tostoredata instance and iteriate through its predicateds and their objects
        Utilities util = Utilities.getInstance();
        OntologyManager om = OntologyManager.getInstance();
        OWLDataFactory factory = om.getFactory();
        
        OWLNamedIndividual storedata_instance = factory.getOWLNamedIndividual(IRI.create(base + "ICO.owl#_tostoredata"));
        
        
        om.getOntology().objectPropertiesInSignature().forEach(p-> { 
            
            if(!p.isOWLTopObjectProperty()){
                
                OWLObjectProperty op = om.getFactory().getOWLObjectProperty(p.toStringID());
                
                Stream<OWLNamedIndividual> objectPropertyValues = om.getReasoner().objectPropertyValues(storedata_instance, p);

                objectPropertyValues.forEach(oni -> {
                    
                     MutableTriple<String,String,String> triple_response = MutableTriple.of(
                     storedata_instance.getIRI().getFragment(),
                             util.getLabelForProperty(op),
                             oni.getIRI().getFragment()
                     );
                    
                    response.add(triple_response);
                    //System.out.println(" > " + util.getLabelForProperty(op) + " >" + oni.getIRI().getFragment());
                    
                    
                });
                
            }
        
        });
        
        return response;
    }

    private UseCaseThree() {
        
    }
}
