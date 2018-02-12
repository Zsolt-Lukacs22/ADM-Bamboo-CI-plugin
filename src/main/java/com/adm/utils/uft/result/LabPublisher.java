package com.adm.utils.uft.result;

import com.adm.utils.uft.XPathUtils;
import com.adm.utils.uft.sdk.Client;
import com.adm.utils.uft.sdk.Logger;
import com.adm.utils.uft.sdk.Response;
import com.adm.utils.uft.sdk.request.GetLabRunEntityTestSetRunsRequest;
import com.adm.utils.uft.sdk.request.GetRequest;

public class LabPublisher extends Publisher {

    public LabPublisher(Client client, String entityId, String runId) {

        super(client, entityId, runId);
    }

    @Override
    protected String getEntityName(String nameSuffix, Logger logger) {

        String ret = "Unnamed Entity";
        try {
            Response response = getEntityName(nameSuffix);
            if (response.isOk() && !response.toString().equals("")) {
                ret = XPathUtils.getAttributeValue(response.toString(), "name");
            } else {
                Throwable failure = response.getFailure();
                logger.log(String.format(
                        "Failed to get Entity name. Exception: %s",
                        failure == null ? "null" : failure.getMessage()));
            }
        } catch (Throwable e) {
            logger.log("Failed to get Entity name");
        }

        return ret;
    }

    @Override
    protected GetRequest getRunEntityTestSetRunsRequest(Client client, String runId) {

        return new GetLabRunEntityTestSetRunsRequest(_client, _runId);
    }
}

