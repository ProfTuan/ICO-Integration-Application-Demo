/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uth.sbmi.swrl.ico;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

/**
 *
 * @author mac
 */
public class Utilities {
    
    
    private static Utilities INSTANCE = null;
    
    public synchronized static Utilities getInstance(){
        
        if (INSTANCE == null) {
            INSTANCE = new Utilities();
        }
        return INSTANCE;
        
    }
    
    private Utilities() {
    }
    
    public String getLabelForClass(OWLClass owlclass){
        
        OntologyManager om = OntologyManager.getInstance();
        return this.getLabelForClass(owlclass, om.getOntology(), om.getFactory());
        
    }
    
    public String getLabelForProperty(OWLObjectProperty oproperty){
        OntologyManager om = OntologyManager.getInstance();
        return this.getLabelForProperty(oproperty, om.getOntology(), om.getFactory());
    }
    
    private String getLabelForProperty(OWLObjectProperty op, OWLOntology ontology, OWLDataFactory owlfactory){
        String labelResult = "";
        
        List<String> collect = EntitySearcher.getAnnotations(op, ontology,owlfactory.getRDFSLabel()).filter(p->p.getValue() instanceof OWLLiteral).map(a->(OWLLiteral)a.getValue()).map(l->l.getLiteral()).collect(Collectors.toList());
        
        return StringUtils.join(collect, " , ");
    }
    
    public String getLabelForIndividual(OWLNamedIndividual i){
        OntologyManager om = OntologyManager.getInstance();
        return this.getLabelForIndividual(i,om.getOntology(),om.getFactory());
    }
    
    public String getLabelForClass(OWLClass owlclass, OWLOntology ontology, OWLDataFactory owlfactory){
        
        String labelResult = "";

        List<String> collect = EntitySearcher.getAnnotations(owlclass, ontology, owlfactory.getRDFSLabel()).
                filter(p-> p.getValue() instanceof OWLLiteral ).
                map(a->(OWLLiteral)a.getValue()).
                map(l->l.getLiteral()).
                collect(Collectors.toList());

        return StringUtils.join(collect, " , ");
        
        
    }
    
    public String getLabelForIndividual(OWLNamedIndividual i, OWLOntology ontology, OWLDataFactory owlfactory){
        
        String labelResult = "";

        List <String> collect = EntitySearcher.getAnnotations(i, ontology, owlfactory.getRDFSLabel())
                .filter(p-> p.getValue() instanceof OWLLiteral)
                .map(a->(OWLLiteral)a.getValue())
                .map(l->l.getLiteral())
                .collect(Collectors.toList());

        return StringUtils.join(collect, " , ");
        
    }
    
    
    
    
    
    
}

