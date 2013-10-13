package com.pmease.commons.wicket.component.datatablesupport;

import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

import com.pmease.commons.wicket.ajaxlistener.ajaxloadingindicator.AjaxLoadingIndicator;
import com.pmease.commons.wicket.ajaxlistener.ajaxloadingoverlay.AjaxLoadingOverlay;

@SuppressWarnings("serial")
public class CustomAjaxNavigationToolbar extends AjaxNavigationToolbar {

	public CustomAjaxNavigationToolbar(DataTable<?, ?> dataTable) {
		super(dataTable);
	}

	@Override
	protected PagingNavigator newPagingNavigator(String navigatorId, DataTable<?, ?> table) {
		
		return new AjaxPagingNavigator(navigatorId, table) {

			@Override
			protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int increment) {
				return new AjaxPagingNavigationLink(id, pageable, increment) {

					@Override
					protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
						super.updateAjaxAttributes(attributes);

						attributes.getAjaxCallListeners().add(new AjaxLoadingOverlay());
						attributes.getAjaxCallListeners().add(new AjaxLoadingIndicator());
					}
					
				};
			}

			@Override
			protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
				return new AjaxPagingNavigationIncrementLink(id, pageable, increment) {

					@Override
					protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
						super.updateAjaxAttributes(attributes);

						attributes.getAjaxCallListeners().add(new AjaxLoadingOverlay());
						attributes.getAjaxCallListeners().add(new AjaxLoadingIndicator());
					}
					
				};
			}

			@Override
			protected PagingNavigation newNavigation(String id, 
					IPageable pageable, IPagingLabelProvider labelProvider) {
				PagingNavigation navigation = new AjaxPagingNavigation(id, pageable, labelProvider) {

					@Override
					protected Link<?> newPagingNavigationLink(String id, IPageable pageable, long pageIndex) {
						return new AjaxPagingNavigationLink(id, pageable, pageIndex) {

							@Override
							protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
								super.updateAjaxAttributes(attributes);
								attributes.getAjaxCallListeners().add(new AjaxLoadingOverlay());
								attributes.getAjaxCallListeners().add(new AjaxLoadingIndicator());
							}
							
						};
					}
					
				};
				navigation.setViewSize(5);
				return navigation;
			}
			
		};
	}

}