/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.input.validation.mgt.model.validators;

import org.wso2.carbon.identity.input.validation.mgt.exceptions.InputValidationMgtClientException;
import org.wso2.carbon.identity.input.validation.mgt.model.ValidationContext;

import java.util.Map;

import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.MAX_LENGTH;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.MIN_LENGTH;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.PERIOD;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.ErrorMessages.ERROR_VALIDATION_MAX_SPECIAL_CHR_LENGTH_MISMATCH;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.ErrorMessages.ERROR_VALIDATION_MIN_SPECIAL_CHR_LENGTH_MISMATCH;

/**
 * Special character validator.
 */
public class SpecialCharacterValidator extends AbstractRulesValidator {

    @Override
    public boolean validate(ValidationContext context) throws InputValidationMgtClientException {

        int countSpecial = 0;
        String value = context.getValue();
        String field = context.getField();
        Map<String, String> attributesMap = context.getProperties();

        for (int i = 0; i < value.length(); i++) {
            if (value.substring(i, i + 1).matches("[^A-Za-z0-9]")) {
                countSpecial++;
            }

        }
        if (attributesMap.containsKey(field + PERIOD + MIN_LENGTH)) {
            int min = Integer.parseInt(attributesMap.get(field + PERIOD + MIN_LENGTH));
            if (countSpecial < min) {
                throw new InputValidationMgtClientException(ERROR_VALIDATION_MIN_SPECIAL_CHR_LENGTH_MISMATCH.getCode(),
                        ERROR_VALIDATION_MIN_SPECIAL_CHR_LENGTH_MISMATCH.getMessage(),
                        String.format(ERROR_VALIDATION_MIN_SPECIAL_CHR_LENGTH_MISMATCH.getDescription(), field, min));
            }
        }
        if (attributesMap.containsKey(field + PERIOD + MAX_LENGTH)) {
            int max = Integer.parseInt(attributesMap.get(field + PERIOD + MAX_LENGTH));
            if (countSpecial > max) {
                throw new InputValidationMgtClientException(ERROR_VALIDATION_MAX_SPECIAL_CHR_LENGTH_MISMATCH.getCode(),
                        ERROR_VALIDATION_MAX_SPECIAL_CHR_LENGTH_MISMATCH.getMessage(),
                        String.format(ERROR_VALIDATION_MAX_SPECIAL_CHR_LENGTH_MISMATCH.getDescription(), field, max));
            }
        }
        return true;
    }
}
