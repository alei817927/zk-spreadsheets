/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.zkoss.poi.ss.formula.functions;

import org.zkoss.poi.ss.formula.eval.BlankEval;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.MissingArgEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;

/**
 * Common interface for all implementations of Excel built-in functions.
 *
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public interface Function {

	/**
	 * @param args the evaluated function arguments.  Empty values are represented with
	 * {@link org.zkoss.poi.ss.formula.eval.BlankEval} or {@link org.zkoss.poi.ss.formula.eval.MissingArgEval}, never <code>null</code>.
	 * @param srcRowIndex row index of the cell containing the formula under evaluation
	 * @param srcColumnIndex column index of the cell containing the formula under evaluation
	 * @return The evaluated result, possibly an {@link org.zkoss.poi.ss.formula.eval.ErrorEval}, never <code>null</code>.
	 * <b>Note</b> - Excel uses the error code <i>#NUM!</i> instead of IEEE <i>NaN</i>, so when
	 * numeric functions evaluate to {@link Double#NaN} be sure to translate the result to {@link
	 * org.zkoss.poi.ss.formula.eval.ErrorEval#NUM_ERROR}.
	 */
	ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex);
}
