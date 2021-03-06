package com.gitplex.server.web.editable.date;

import java.util.Date;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import com.gitplex.server.web.editable.BeanContext;
import com.gitplex.server.web.editable.EditSupport;
import com.gitplex.server.web.editable.NotDefinedLabel;
import com.gitplex.server.web.editable.PropertyContext;
import com.gitplex.server.web.editable.PropertyDescriptor;
import com.gitplex.server.web.editable.PropertyEditor;
import com.gitplex.server.web.editable.PropertyViewer;

@SuppressWarnings("serial")
public class DateEditSupport implements EditSupport {

	public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";
	
	@Override
	public BeanContext<?> getBeanEditContext(Class<?> beanClass, Set<String> excludeProperties) {
		return null;
	}

	@Override
	public PropertyContext<?> getPropertyEditContext(Class<?> beanClass, String propertyName) {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(beanClass, propertyName);

		Class<?> propertyClass = propertyDescriptor.getPropertyGetter().getReturnType();
		if (propertyClass == Date.class) {
			return new PropertyContext<Date>(propertyDescriptor) {

				@Override
				public PropertyViewer renderForView(String componentId, final IModel<Date> model) {
					return new PropertyViewer(componentId, this) {

						@Override
						protected Component newContent(String id, PropertyDescriptor propertyDescriptor) {
							if (model.getObject() != null) {
								return new Label(id, model.getObject());
							} else {
								return new NotDefinedLabel(id);
							}
						}
						
					};
				}

				@Override
				public PropertyEditor<Date> renderForEdit(String componentId, IModel<Date> model) {
					return new DatePropertyEditor(componentId, this, model);
				}
				
			};
		} else {
			return null;
		}
		
	}

}
