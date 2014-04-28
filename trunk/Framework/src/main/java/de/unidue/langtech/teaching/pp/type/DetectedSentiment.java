

/* First created by JCasGen Tue Dec 03 11:50:57 CET 2013 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Jan 21 10:31:45 CET 2014
 * XML source: C:/Users/Onur/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/SentimentType.xml
 * @generated */
public class DetectedSentiment extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DetectedSentiment.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DetectedSentiment() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DetectedSentiment(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DetectedSentiment(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DetectedSentiment(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: sentiment

  /** getter for sentiment - gets 
   * @generated */
  public String getSentiment() {
    if (DetectedSentiment_Type.featOkTst && ((DetectedSentiment_Type)jcasType).casFeat_sentiment == null)
      jcasType.jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.type.DetectedSentiment");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedSentiment_Type)jcasType).casFeatCode_sentiment);}
    
  /** setter for sentiment - sets  
   * @generated */
  public void setSentiment(String v) {
    if (DetectedSentiment_Type.featOkTst && ((DetectedSentiment_Type)jcasType).casFeat_sentiment == null)
      jcasType.jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.type.DetectedSentiment");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedSentiment_Type)jcasType).casFeatCode_sentiment, v);}    
  }

    