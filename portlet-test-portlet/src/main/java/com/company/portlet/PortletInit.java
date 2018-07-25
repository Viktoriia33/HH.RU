package com.company.portlet;

import com.company.data_base.DataBaseConnection;
import com.company.data_base.DataBaseHelper;
import com.company.data_base.DataBaseOperation;
import com.company.json.JsonParser;

import javax.portlet.*;
import java.io.IOException;

public class PortletInit extends GenericPortlet {

    private static String VIEW_JSP = "/view.jsp";

    @Override
    public void init(PortletConfig portletConfig) throws PortletException {
        super.init(portletConfig);
    }

    @Override
    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        response.setContentType(request.getResponseContentType());
        PortletContext context = getPortletConfig().getPortletContext();
        context.getRequestDispatcher(VIEW_JSP).include(request, response);
    }

    @Override
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, java.io.IOException {

        response.setRenderParameter("textBox", "textBox");

        JsonParser jsonParser = new JsonParser();

        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();
        dataBaseConnection.start();

        DataBaseOperation dataBaseOperation = new DataBaseOperation(jsonParser);

        dataBaseOperation.deleteTable();
        dataBaseOperation.createTable();
        dataBaseOperation.insertDataIntoDB();

        dataBaseConnection.stop();
    }
}