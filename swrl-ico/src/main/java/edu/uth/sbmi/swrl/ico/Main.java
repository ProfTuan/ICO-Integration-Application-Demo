/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uth.sbmi.swrl.ico;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 *
 * @author tuan
 */
public class Main {
    
    public Main(){
        
    }
    
    public void initUseCase1Model(){
        InputStream ontologyStream = this.getClass().getClassLoader().getResourceAsStream("ico-statement-1.owl");
        
        OntologyManager om = OntologyManager.getInstance();
        
        om.addOntology(ontologyStream);
    }
    
    public void initUseCase2Model(){
        InputStream ontologyStream = this.getClass().getClassLoader().getResourceAsStream("ico-statement-2.owl");
        
        OntologyManager om = OntologyManager.getInstance();
        
        om.addOntology(ontologyStream);
    }
    
    public void initUseCaseModelFromPath(String path){
        OntologyManager om = OntologyManager.getInstance();
        om.addOntology(path);
    }
    
    public void runFirstUseCase(){
        OntologyManager om = OntologyManager.getInstance();
        
        //InputStream ontologyStream = this.getClass().getClassLoader().getResourceAsStream("ico-statement-1.owl");
        //om.addOntology(ontologyStream);
        om.addOntology("/Users/mac/Desktop/external-ico/ico-statement-1.owl");
        
        UseCaseOne u1 = UseCaseOne.getInstance();

        System.out.println("Adding Informed Consent Intances...");
        u1.addInformedConsentInstances();
        
        System.out.println("Adding the subject's consent...");
        u1.insertSubjectConsent();
        
        System.out.println("Staring the reasoner....");
        u1.startReasoner();
        
        System.out.println("\n* What was explained?");
        u1.whatWasExplained();
        
        System.out.println("\n* What did the participant give legal consent?");
        u1.didParticipantGiveLegalConsent();
        
        System.out.println("\n* Who explained the consent process?");
        u1.whoExplainedConsentProcess();
        
    }
    
    public void runSecondUseCase(){
        OntologyManager om = OntologyManager.getInstance();
        
        //InputStream ontologyStream = this.getClass().getClassLoader().getResourceAsStream("ico-statement-2.owl");
        //om.addOntology(ontologyStream);
        
        om.addOntology("/Users/mac/Desktop/external-ico/ico-statement-2.owl");
        
        UseCaseTwo u2 = UseCaseTwo.getInstance();
        
        System.out.println("\nAdding informed consent instances...");
        u2.addInformedConsentInstances();
        
        System.out.println("\nAdding the subject's informed consent...");
        u2.insertSubjectConsent();
        
        System.out.println("\nStartin reasoner...");
        u2.startReasoner();
        
        System.out.println("\nWhat is the subject responsibilities?");
        List<MutablePair<String, String>> response1 =u2.whatIsSubjectResponsibilities();
        
        for(MutablePair response : response1){
            System.out.println(response.getLeft() + " - " + response.getRight());
        }
        
        System.out.println("\nWho informed subject and their roles?");
        List<MutableTriple<String, String, String>> response2 = u2.whoInformedSubjectAndTheirRoles();
        
        for(MutableTriple response : response2){
            System.out.println(response.getLeft() + " - " + response.getMiddle() + " - " + response.getRight());
        }
        
    }
    
    public void runThirdUseCase(){
        OntologyManager om = OntologyManager.getInstance();
        
        om.addOntology("/Users/mac/Desktop/external-ico/ico-statement-3.owl");
        
        UseCaseThree u3 = UseCaseThree.getInstance();
        
        System.out.println("\nAdding informed consent instances...");
        u3.addInformedConsentInstances();
        
        System.out.println("\nAdding the subject's informed consent...");
        u3.insertSubjectConsent();
        
        System.out.println("\nStartin reasoner...");
        u3.startReasoner();
        
        
        System.out.println("\n* What is the authorization for?");
        List<MutableTriple<String, String, String>> response1 = u3.whatIsTheAuthorizationFor();
        
        for(MutableTriple response : response1){
            System.out.println(response.getLeft() + " > " + response.getMiddle() + " > " + response.getRight());
        }
        
        System.out.println("\n* What is restriction from the authorization?");
        List<MutableTriple<String, String, String>> response2 = u3.whatAreTheyRestrictedFor();
        
        for(MutableTriple response: response2){
            System.out.println(response.getLeft() + " > " + response.getMiddle() + " > " +response.getRight());
        }
        
    }
    
    public void runFourthUseCase(){
        
        OntologyManager om = OntologyManager.getInstance();
        om.addOntology("/Users/mac/Desktop/external-ico/ico-statement-4.owl");
        
        UseCaseFour u4 = UseCaseFour.getInstance();
        
        System.out.println("\nAdding informed consent instances...");
        u4.addInformedConsentInstances();
        
        System.out.println("\nAdding the subject's informed consent...");
        u4.insertSubjectConsent();
        
        System.out.println("\nStartin reasoner...");
        u4.startReasoner();
        
        System.out.println("\n* What authorization was given?");
        List<MutableTriple<String, String, String>> response1 = u4.whatAuthorizationIsGiven();
        
        for(MutableTriple response : response1){
            System.out.println(response.getLeft() + " > " + response.getMiddle() + " > " + response.getRight());
        }
        
        System.out.println("\n* What are the details of the given authorization?");
        List<MutableTriple<String, String, String>> response2 = u4.whatAreDetailsofAuthorization();
        
        for(MutableTriple response : response2){
            System.out.println(response.getLeft() + " > " + response.getMiddle() + " > " + response.getRight());
        }
        
    }
    
    public static void main(String[] args) {
        
       System.out.println("Running main\n\n");
        
       Main m = new Main();
        
       //m.runFirstUseCase();
       m.runSecondUseCase();
       //m.runThirdUseCase();
       //m.runFourthUseCase();
       System.out.println("\nFinished running main\n\n");
        
    }
    
}
