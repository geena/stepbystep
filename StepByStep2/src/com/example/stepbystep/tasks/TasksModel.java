package com.example.stepbystep.tasks;

import com.example.stepbystep.dataservice.Task;
import com.paypal.android.base.commons.patterns.events.EventType;
import com.paypal.android.base.commons.patterns.mvc.model.DefaultModel;
import com.paypal.android.base.commons.patterns.mvc.model.IModel;
import com.paypal.android.base.commons.patterns.mvc.model.PropertyKeys;

public class TasksModel extends DefaultModel implements IModel
{

    /*
    *  Property key values
    */
	enum Properties implements PropertyKeys
    {
		TASK
    }
    /*
     *  View events keys
     */
	enum TasksActions implements EventType
    {
		TASK_SELECTED
    }
	
	enum InvoiceTypeDisplayed
	{
	}

	void setSelectedTask(Task task){
		setValue(Properties.TASK, task);
	}

}
