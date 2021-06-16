/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.flowable.bpmn.converter.util.BpmnXMLUtil;
import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ErrorEventDefinition;
import org.flowable.bpmn.model.Event;

/**
 * @author Tijs Rademakers
 */
public class ErrorEventDefinitionParser extends BaseChildElementParser {

    @Override
    public String getElementName() {
        return ELEMENT_EVENT_ERRORDEFINITION;
    }

    @Override
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
        if (!(parentElement instanceof Event)) {
            return;
        }

        ErrorEventDefinition eventDefinition = new ErrorEventDefinition();
        BpmnXMLUtil.addXMLLocation(eventDefinition, xtr);
        eventDefinition.setErrorCode(xtr.getAttributeValue(null, ATTRIBUTE_ERROR_REF));
        eventDefinition.setErrorVariableName(xtr.getAttributeValue(FLOWABLE_EXTENSIONS_NAMESPACE, ATTRIBUTE_ERROR_VARIABLE_NAME));

        eventDefinition.setErrorVariableTransient(
                Boolean.parseBoolean(xtr.getAttributeValue(FLOWABLE_EXTENSIONS_NAMESPACE, ATTRIBUTE_ERROR_VARIABLE_TRANSIENT)));

        eventDefinition.setErrorVariableLocalScope(
                Boolean.parseBoolean(xtr.getAttributeValue(FLOWABLE_EXTENSIONS_NAMESPACE, ATTRIBUTE_ERROR_VARIABLE_LOCAL_SCOPE)));

        BpmnXMLUtil.parseChildElements(ELEMENT_EVENT_ERRORDEFINITION, eventDefinition, xtr, model);

        ((Event) parentElement).getEventDefinitions().add(eventDefinition);
    }
}
