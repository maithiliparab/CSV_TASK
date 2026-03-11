package com.jsp.client.service;

import org.springframework.stereotype.Service;

import com.kibocommerce.sdk.catalogadministration.api.ProductAttributesApi;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.common.ApiException;


@Service
public class AttributeService {

	private final ProductAttributesApi api;

	public AttributeService(ProductAttributesApi api) {
		this.api = api;
	}

	public void createIfNotExists(CatalogAdminsAttribute attribute) {

		try {
			api.getAttribute(attribute.getAttributeCode(), null);
			System.out.println("Attribute already exists: " + attribute.getAttributeCode());
		}

		catch (ApiException e) {

			if (e.getCode() == 404) {
				try {
					api.addAttribute(attribute);
					System.out.println("Attribute created: " + attribute.getAttributeCode());
				} catch (ApiException ex) {
					System.err.println("Create error: " + ex.getResponseBody());
				}
			} else {
				System.err.println("Check error: " + e.getResponseBody());
			}
		}
	}
}