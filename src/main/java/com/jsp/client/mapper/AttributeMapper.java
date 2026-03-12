package com.jsp.client.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jsp.client.dto.MasterProductCsvDTO;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttributeLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttributeVocabularyValue;

@Component
public class AttributeMapper {

	public CatalogAdminsAttribute map(MasterProductCsvDTO csv) {

		CatalogAdminsAttribute attribute = new CatalogAdminsAttribute();
		
		attribute.setAdminName(csv.getAdministrationName());
		attribute.setAttributeCode(csv.getAttributeCode());
		attribute.setAttributeFQN("tenant~" + csv.getAttributeCode());
		attribute.setInputType(csv.getInputType());
		attribute.setDataType(csv.getDataType());
		attribute.setIsOption(parseBoolean(csv.getIsOption()));
		attribute.setIsProperty(parseBoolean(csv.getIsProperty()));
		attribute.setIsExtra(false);
		attribute.setValueType("Predefined");
		

		// VOCABULARY VALUES
		// if values are there in csv then create a list and set that values and pass it
		// in that list
		if (csv.getValues() != null && !csv.getValues().isEmpty()) {
			List<CatalogAdminsAttributeVocabularyValue> vocabularyList = new ArrayList<>();
			
			for (String value : csv.getValues().split(",")) {
				CatalogAdminsAttributeVocabularyValue vocab = new CatalogAdminsAttributeVocabularyValue();
				vocab.setValue(value.trim());
				vocabularyList.add(vocab);
			}

			attribute.setVocabularyValues(vocabularyList);
		}

		CatalogAdminsAttributeLocalizedContent content = new CatalogAdminsAttributeLocalizedContent();
		content.setLocaleCode(csv.getLocaleCode());
		content.setName(csv.getAttributeLabel());
		attribute.setContent(content);
		return attribute;
	}
	
	private Boolean parseBoolean(String value) {
		return value != null && value.trim().equalsIgnoreCase("true");
	}
}