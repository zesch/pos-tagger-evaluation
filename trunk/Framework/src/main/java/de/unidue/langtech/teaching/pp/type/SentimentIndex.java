

/* First created by JCasGen Tue Jan 21 10:31:45 CET 2014 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu May 01 16:40:53 CEST 2014
 * XML source: C:/Users/Onur/workspace/Framework/src/main/resources/desc/type/SentimentType.xml
 * @generated */
public class SentimentIndex extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SentimentIndex.class);
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
  protected SentimentIndex() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SentimentIndex(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SentimentIndex(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SentimentIndex(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: beginIndex

  /** getter for beginIndex - gets 
   * @generated
   * @return value of the feature 
   */
  public int getBeginIndex() {
    if (SentimentIndex_Type.featOkTst && ((SentimentIndex_Type)jcasType).casFeat_beginIndex == null)
      jcasType.jcas.throwFeatMissing("beginIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    return jcasType.ll_cas.ll_getIntValue(addr, ((SentimentIndex_Type)jcasType).casFeatCode_beginIndex);}
    
  /** setter for beginIndex - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBeginIndex(int v) {
    if (SentimentIndex_Type.featOkTst && ((SentimentIndex_Type)jcasType).casFeat_beginIndex == null)
      jcasType.jcas.throwFeatMissing("beginIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    jcasType.ll_cas.ll_setIntValue(addr, ((SentimentIndex_Type)jcasType).casFeatCode_beginIndex, v);}    
   
    
  //*--------------*
  //* Feature: endIndex

  /** getter for endIndex - gets 
   * @generated
   * @return value of the feature 
   */
  public int getEndIndex() {
    if (SentimentIndex_Type.featOkTst && ((SentimentIndex_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    return jcasType.ll_cas.ll_getIntValue(addr, ((SentimentIndex_Type)jcasType).casFeatCode_endIndex);}
    
  /** setter for endIndex - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEndIndex(int v) {
    if (SentimentIndex_Type.featOkTst && ((SentimentIndex_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    jcasType.ll_cas.ll_setIntValue(addr, ((SentimentIndex_Type)jcasType).casFeatCode_endIndex, v);}    
  }

    