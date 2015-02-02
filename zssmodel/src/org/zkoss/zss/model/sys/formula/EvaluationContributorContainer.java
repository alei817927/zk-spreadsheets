package org.zkoss.zss.model.sys.formula;

/**
 * To contain {@link org.zkoss.zss.model.sys.formula.EvaluationContributor}
 * 
 * @author dennis
 * @since 3.5.0
 */
public interface EvaluationContributorContainer {
	public EvaluationContributor getEvaluationContributor();

	public void setEvaluationContributor(EvaluationContributor contributor);
}
