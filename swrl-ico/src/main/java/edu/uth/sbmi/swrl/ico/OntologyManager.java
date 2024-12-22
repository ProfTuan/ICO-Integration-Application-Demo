package edu.uth.sbmi.swrl.ico;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Hello world!
 *
 */
public class OntologyManager
{
    private Utilities util;
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    
    private OWLOntologyManager ontologyManager;
    private  OWLOntology ontology;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner hermit;
    private ShortFormProvider shortFormProvider;
    private  OWLDataFactory owlfactory;
    
    private static OntologyManager INSTANCE = null;
    
    private String path2Ontology = "";
    
    private InputStream ontology_stream = null; 
    
    public synchronized static OntologyManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OntologyManager();
        }
        return INSTANCE;
    }
    
    private OntologyManager (){
        
    }
    
    public OWLOntologyManager getOWLManager(){
        return this.ontologyManager;
    }
    
    public int axiomCount(){
        return ontology.getLogicalAxiomCount();
    }
    
    public void rebuildReasoning(){
        hermit = reasonerFactory.createReasoner(ontology);
        hermit.precomputableInferenceTypes();
        owlfactory = ontologyManager.getOWLDataFactory();
    }
    
    
    public void addAxiom(OWLClassAssertionAxiom axiom, OWLAnnotation label){
        this.ontologyManager.addAxiom(ontology, axiom);
        //OWLAxiom axiom = factory.getOWLAnnotationAssertionAxiom(newIndividual.asOWLNamedIndividual().getIRI(), label);
        
        
        
        //System.out.println(ontology.toString());
    }
    
    public void addAxiom(OWLObjectPropertyAssertionAxiom axiom){
        this.ontologyManager.addAxiom(ontology, axiom);
        
    }
    
    public void addAxiom(OWLClassAssertionAxiom axiom){
        this.ontologyManager.addAxiom(ontology, axiom);
        
       
        
        
    }
    
    public OWLObjectRenderer getRenderer(){
        return renderer;
    }
    
    public void addOntology(String ontologyPath){
        
        initOntologyManagers(ontologyPath);
    }
    
    public void addOntology(InputStream is){
        initOntologyManagers(is);
    }
    
    public OWLDataFactory getFactory(){
        return ontologyManager.getOWLDataFactory();
    }
    private void initOntologyManagers(InputStream ontologyStream){
        
        this.ontology_stream = ontologyStream;
        
        if(util == null) util = Utilities.getInstance();
        
        ontologyManager = OWLManager.createOWLOntologyManager();
        try {
            //ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(pathOntology));
            ontology = ontologyManager.loadOntologyFromOntologyDocument(this.ontology_stream);
            
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OntologyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        reasonerFactory = new Reasoner.ReasonerFactory();
        hermit = reasonerFactory.createReasoner(ontology);
        hermit.precomputeInferences();
        shortFormProvider = new SimpleShortFormProvider();
        owlfactory = ontologyManager.getOWLDataFactory();
    }
    
    private void initOntologyManagers(String pathOntology){
        
        if(util == null) util = Utilities.getInstance();
        
        ontologyManager = OWLManager.createOWLOntologyManager();
        try {
            ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(pathOntology));
            
            
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OntologyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        reasonerFactory = new ReasonerFactory();
        hermit = reasonerFactory.createReasoner(ontology);
        hermit.precomputeInferences();
        shortFormProvider = new SimpleShortFormProvider();
        owlfactory = ontologyManager.getOWLDataFactory();
    }
    
    public OWLOntology getOntology(){
        return this.ontology;
    }
    
    public OWLReasoner getReasoner(){
        return this.hermit;
    }
    
  

    public void addAxiomLabelorComment(OWLIndividual I, OWLAnnotation owlAnnotation) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        OWLAxiom axiom = owlfactory.getOWLAnnotationAssertionAxiom(I.asOWLNamedIndividual().getIRI(), owlAnnotation);
        this.ontologyManager.applyChange(new AddAxiom(ontology, axiom));
    }
    
    
}
